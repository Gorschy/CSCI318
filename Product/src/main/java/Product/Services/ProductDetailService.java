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
public class ProductDetailService {
    @Autowired
    private ProductDetailRepository prodDetailRepository;

    // find all product deatils in the system and return a list of product deatils
    List<ProductDetail> all() {
        return prodDetailRepository.findAll();
    }

    // Find a specific product deatil by id
    ProductDetail one(Long id) {
        return prodDetailRepository.findById(id).orElseThrow(() -> new ProductDetailNotFoundException(id));
    }

    // Create a new product deatil
    ProductDetail newProductDetail(ProductDetail newProductDetail) {
        return prodDetailRepository.save(newProductDetail);
    }

    // Update an existing product deatil
    ProductDetail replaceProductDetail(Long id, ProductDetail newProductDetail) {
        return prodDetailRepository.findById(id).map(productDetail -> {
            productDetail.setDescription(newProductDetail.getDescription());
            productDetail.setComment(newProductDetail.getComment());
            return prodDetailRepository.save(productDetail);
        }).orElseGet(() -> {
            newProductDetail.setId(id);
            return prodDetailRepository.save(newProductDetail);
        });
    }

    // Delete a product deatil entity based on id
    void deleteProductDetail(Long id) {
        prodDetailRepository.deleteById(id);
    }
}