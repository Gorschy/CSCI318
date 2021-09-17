package OnlineOrdering;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
class Product {
    private @Id @GeneratedValue long id;
    private String productCategory;
    private String name;
    private double price;
    private int stockQuantity;




    //constructors
    Product() {}
    
    Product(String productCategory, String name, double price, int stockQuantity) {
    	this.productCategory = productCategory;
    	this.name = name;
    	this.price = price;
    	this.stockQuantity = stockQuantity;
    }

    //getter methods
    public Long getId() {
        return this.id;
    }
    
    public String getProductCategory() {
    	return this.productCategory;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public double getPrice() {
    	return this.price;
    }
    
    public int getStockQuantity() {
    	return this.stockQuantity;
    }
   

    //setter methods
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setProductCategory(String productCategory) {
    	this.productCategory = productCategory;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public void setPrice (double price) {
    	this.price = price;
    }
    
    public void setStockQuantity (int stockQuantity) {
    	this.stockQuantity = stockQuantity;
    }
    
    
    //overrides
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        Product product = (Product) o;
        return Objects.equals(this.id, product.id) &&
               Objects.equals(this.productCategory, product.productCategory) &&
               Objects.equals(this.name, product.name) &&
               Objects.equals(this.price, product.price) &&
               Objects.equals(this.stockQuantity, product.stockQuantity);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.productCategory, this.name, this.price, this.stockQuantity);
    }
    @Override
    public String toString() {
        return "Product{" + "id=" + this.id + ", Product Category='" + this.productCategory + '\'' + ", name='" + this.name + '\'' + ", price='" + this.price  + '\'' + ", Stock Quantity='" + this.stockQuantity + '\''  + '}';
        }
}