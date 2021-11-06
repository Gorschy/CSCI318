package OrderEntity;

import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;
public class OrderedQuantity {
    private Product product;
    private Long quantity;
    
    OrderedQuantity(){}
    OrderedQuantity(Product product, Long quantity){
        this.product = product;
        this.quantity = quantity;
    }

    //getters
    public Product getProduct() {
        return this.product;
    }
    public Long getQuantity() {
        return this.quantity;
    }

    //setters
    public void setProduct(Product product){
        this.product = product;
    }
    public void setQuantity(Long quantity){
        this.quantity = quantity;
    }
    
}