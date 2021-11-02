package Product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository prodRepository;
    @Autowired
    private ProductDetailRepository prodDetailRepository;

    // find all products in the system and return a list of products
    List<Product> all() {
        return prodRepository.findAll();
    }

    // Find a specific product by id
    Product one(Long id) {
        return prodRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    // Create a new product
    Product newProduct(Product newProduct) {
        return prodRepository.save(newProduct);
    }

    // Update an existing product
    Product replaceProduct(Long id, Product newProduct) {
        return prodRepository.findById(id).map(product -> {
            product.setProductCategory(newProduct.getProductCategory());
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            product.setStockQuantity(newProduct.getStockQuantity());
            product.setProductDetail(newProduct.getProductDetail());
            return prodRepository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(id);
            return prodRepository.save(newProduct);
        });
    }

    // Delete a product entity based on id
    void deleteProduct(Long id) {
        prodRepository.deleteById(id);
    }

    //join a product detail object to a product object
    Product joinProductDetail(Long prodId, Long prodDetailId) {
        Product product = prodRepository.findById(prodId).orElseThrow(() -> new ProductNotFoundException(prodId));
        ProductDetail productDetail = prodDetailRepository.findById(prodDetailId).orElseThrow(() -> new ProductDetailNotFoundException(prodDetailId));

        product.setProductDetail(productDetail);
        return prodRepository.save(product);
    }
}