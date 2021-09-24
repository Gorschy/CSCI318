package OnlineOrdering.ErrorHandling;

public class ProductDetailNotFoundException extends RuntimeException {

  public ProductDetailNotFoundException(Long id) {
    super("Could not find product detail with following id " + id);
  }
}