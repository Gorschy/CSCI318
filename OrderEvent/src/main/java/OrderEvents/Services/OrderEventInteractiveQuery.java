package OrderEvent;

import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderEventInteractiveQuery {

    private final InteractiveQueryService interactiveQueryService;

    //@Autowired
    public OrderEventInteractiveQuery(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public long getOrderEvent(Long orderEventString) {
        if (orderEventStore().get(orderEventString) != null) {
            return orderEventStore().get(orderEventString);
        } else {
            throw new NoSuchElementException(); //TODO: should use a customised exception.
        }
    }

    public List<Long> getOrderEventList() {
        List<Long> orderEventList = new ArrayList<>();
        KeyValueIterator<Long, Long> all = orderEventStore().all();
        while (all.hasNext()) {
            Long next = all.next().key;
            orderEventList.add(next);
        }
        return orderEventList;
    }

    public List<Long> getOrderListByProductId(Long orderEventString) {
        List<Long> orderEventList = new ArrayList<>();
        KeyValueIterator<Long, OrderEvent> all = orderEventStore().all();
        while (all.hasNext()) {
            OrderEvent order_event = all.next().value;
            Long product_id = order_event.getProdID();
                Long product_quantity = order_event.getQuantity();
            if (order_event.equals(orderEventString)){
                orderEventList.add(product_id);
            }
        }
        return orderEventList;
    }


    private ReadOnlyKeyValueStore<Long, Long> orderStore() {
        return this.interactiveQueryService.getQueryableStore(OrderEventStreamProcessing.ORDER_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }


    private ReadOnlyKeyValueStore<Long, OrderEvent> orderEventStore() {
        return this.interactiveQueryService.getQueryableStore(OrderEventStreamProcessing.ORDEREVENT_STATE_STORE,
                QueryableStoreTypes.keyValueStore());
    }

}
