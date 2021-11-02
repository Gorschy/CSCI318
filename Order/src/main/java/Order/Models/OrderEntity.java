package OrderEntity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class OrderEntity {
    private @Id @GeneratedValue Long id;
    private Long custId;
    private Long prodId;
    private Long quantity;

    OrderEntity(){}
    OrderEntity(Long custId, Long prodId, Long quantity) {
        this.custId = custId;
        this.prodId = prodId;
        this.quantity = quantity;
    }

    // getters
    public Long getId (){
        return this.id;
    }
    public Long getCustId (){
        return this.custId;
    }
    public Long getProdId (){
        return this.prodId;
    }
    public Long getQuantity (){
        return this.quantity;
    }
    
    // setters
    public void setId (Long id){
        this.id = id;
    }
    public void setCustId (Long custId){
        this.custId = custId;
    }
    public void setProdId (Long prodId){
        this.prodId = prodId;
    }
    public void setQuantity (Long quantity){
        this.quantity = quantity;
    }

    // overrides
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Customer))
            return false;
        OrderEntity order = (OrderEntity) o;
        return Objects.equals(this.id, order.id) &&
               Objects.equals(this.custId, order.custId) &&
               Objects.equals(this.prodId, order.prodId) &&
               Objects.equals(this.quantity, order.quantity);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.custId, this.prodId, this.quantity);
    }
    @Override
    public String toString() {
        return "OrderEntity{" + "id=" + this.id + ", customer id='" + this.custId + '\'' + ", product id='" + this.prodId + '\'' + ", quantity='" + this.quantity + '}';
    }
}