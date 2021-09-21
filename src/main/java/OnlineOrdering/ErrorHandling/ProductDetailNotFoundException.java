package OnlineOrdering;

class ProductDetailNotFoundException extends RuntimeException {

  ProductDetailNotFoundException(Long id) {
    super("Could not find product detail with following id " + id);
  }
}