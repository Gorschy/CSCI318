package OrderEntity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class OrderEvent {
    private Long custId;
    private Long prodId;
    private Long quantity;
    private double price;

    OrderEvent() {}
    OrderEvent(
        Long custId,
        Long prodId,
        Long quantity,
        double price) {
            this.custId = custId;
            this.prodId = prodId;
            this.quantity = quantity;
            this.price = price;
        }

    // Getters
    public Long getCustID() {
        return this.custId;
    }
    public Long getProdID() {
        return this.prodId;
    }
    public Long getQuantity() {
        return this.quantity;
    }
    public double getPrice() {
        return this.price;
    }

    // Setters
    public void setCustID(Long custId) {
        this.custId = custId;
    }
    public void setProdID(Long prodId) {
        this.prodId = prodId;
    }
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    //Overrides
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Customer))
            return false;
            OrderEvent order = (OrderEvent) o;
        return Objects.equals(this.custId, order.custId) &&
               Objects.equals(this.prodId, order.prodId) &&
               Objects.equals(this.quantity, order.quantity) &&
               Objects.equals(this.price, order.price);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.custId, this.prodId, this.quantity, this.price);
    }
    @Override
    public String toString() {
        return "OrderEvent{" + "customer id='" + this.custId + '\'' + ", product id='" + this.prodId + '\'' + ", quantity='" + this.quantity + '\'' + ", price='" + this.price + '}';
    }

}