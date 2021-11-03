package OrderEntity;


import java.util.List;
import java.util.ArrayList;

public class ProductWrapper {
    private List<Product> products;

    public ProductWrapper() {
        products = new ArrayList<>();
    }

    // standard constructor and getter/setter
    public List<Product> getProducts() {
        return this.products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}