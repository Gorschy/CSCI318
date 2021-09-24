package OnlineOrdering.Models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class OrderEnt { //can't use name OrderEnt as it is resevered for other things in the spring framework, see: https://stackoverflow.com/questions/24060498/org-hibernate-exception-sqlgrammarexception-could-not-prepare-statement
    private @Id @GeneratedValue long orderId;
    private int quantity;

    @OneToOne
    private Customer customer;
    @OneToOne
    private Product product;

    //constructors
    public OrderEnt() {}//blank constructor as all variables should be defined AFTER order object is created

    //getter methods
    public Long getId() {
        return this.orderId;
    }
    public int getQuantity() {
        return this.quantity;
    }
    public Customer getCustomer() {
        return this.customer;
    }
    public Product getProduct() {
        return this.product;
    }

    //setter methods
    public void setId(Long orderId) {
        this.orderId = orderId;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setProduct(Product product) {
        this.product = product;
    }

    //overrides
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if(!(o instanceof OrderEnt))
            return false;
        OrderEnt order = (OrderEnt) o;
        return Objects.equals(this.orderId, order.orderId) &&
               Objects.equals(this.quantity, order.quantity) &&
               Objects.equals(this.customer, order.customer) &&
               Objects.equals(this.product, order.product);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.orderId, this.quantity, this.customer, this.product);
    }
    @Override
    public String toString() {
        return "OrderEnt{ id=" + this.orderId + ", quantity=" + this.quantity + ", customer=" + this.customer + ", product=" + this.product + "}";
    }
}