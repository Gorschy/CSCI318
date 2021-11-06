package OrderEntity;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
class Product {
    private @Id @GeneratedValue long prodId;
    private String productCategory;
    private String name;
    private double price;
    private int stockQuantity;
 
    @OneToOne
    private ProductDetail productDetail;

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
        return this.prodId;
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

    public ProductDetail getProductDetail() {
        return this.productDetail;
    }
   

    //setter methods
    public void setId(Long prodId) {
        this.prodId = prodId;
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
    
    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
    
    //overrides
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Product))
            return false;
        Product product = (Product) o;
        return Objects.equals(this.prodId, product.prodId) &&
               Objects.equals(this.productCategory, product.productCategory) &&
               Objects.equals(this.name, product.name) &&
               Objects.equals(this.price, product.price) &&
               Objects.equals(this.stockQuantity, product.stockQuantity);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.prodId, this.productCategory, this.name, this.price, this.stockQuantity);
    }
    @Override
    public String toString() {
        return "Product{" + "id=" + this.prodId + ", Product Category='" + this.productCategory + '\'' + ", name='" + this.name + '\'' + ", price='" + this.price  + '\'' + ", Stock Quantity='" + this.stockQuantity + '\''  + '}';
        }
}