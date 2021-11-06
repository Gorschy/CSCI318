package OrderEvent;

/* This class computes a stream of brand quantities
 * and creates state stores for interactive queries.
 */

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class OrderEventStreamProcessing {

    public final static String ORDER_STATE_STORE = "order-store";
    public final static String ORDEREVENT_STATE_STORE = "orderEvent-store";

    @Bean
    public Function<KStream<?, OrderEvent>, KStream<String, OrderQuantity>> process() {
        return inputStream -> {
            inputStream.map((k, v) -> {
                Long product_id = v.getProdID();
                Long customer_id = v.getCustID();
                Long quantity = v.getQuantity();
                double price = v.getPrice();
                OrderEvent order_event = new OrderEvent();
                order_event.setProdID(product_id);
                order_event.setCustID(customer_id);
                order_event.setQuantity(quantity);
                order_event.setPrice(price);
                Long new_key = product_id;
                return KeyValue.pair(new_key, order_event);
            }).toTable(
                    Materialized.<String, OrderEvent, KeyValueStore<Bytes, byte[]>>as(ORDER_STATE_STORE).
                            withKeySerde(Serdes.String()).
                            // a custom value serde for this state store
                            withValueSerde(orderEventSerde())
            );

            KTable<String, Long> orderKTable = inputStream.
                    mapValues(OrderEvent::getProdID).
                    groupBy((keyIgnored, value) -> value).
                    count(
                            Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as(ORDEREVENT_STATE_STORE).
                                    withKeySerde(Serdes.String()).
                                    withValueSerde(Serdes.Long())
                    );

            KStream<String, OrderQuantity> orderQuantityStream = orderKTable.
                    toStream().
                    map((k, v) -> KeyValue.pair(k, new OrderQuantity(k, v)));
            // use the following code for testing
            orderQuantityStream.print(Printed.<String, OrderQuantity>toSysOut().withLabel("Console Output"));

            return orderQuantityStream;
        };
    }


    // Can compare the following configuration properties with those defined in application.yml
    public Serde<OrderEvent> orderEventSerde() {
        final JsonSerde<OrderEvent> orderEventJsonSerde = new JsonSerde<>();
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "OrderEvent.Models.OrderEvent");
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        orderEventJsonSerde.configure(configProps, false);
        return orderEventJsonSerde;
    }
}