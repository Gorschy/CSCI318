package OrderEntity;

class OrderNotFoundException extends RuntimeException {

  OrderNotFoundException(Long id) {
    super("Could not find order with following id " + id);
  }
}