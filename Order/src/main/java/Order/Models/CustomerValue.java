package OrderEntity;

import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

public class CustomerValue {
    private List<Product> products;
    private double value;

    CustomerValue(){}
    CustomerValue(List<Product> products, double value){
        this.products = products;
        this.value = value;
    }

    //getters
    public List<Product> getProducts() {
        return this.products;
    }
    public double getValue() {
        return this.value;
    }

    //setters
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public void setValue(double value) {
        this.value = value;
    }
}