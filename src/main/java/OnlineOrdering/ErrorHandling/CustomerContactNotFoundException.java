package OnlineOrdering;

class CustomerContactNotFoundException extends RuntimeException {

    CustomerContactNotFoundException(Long id) {
    super("Could not find customer contact with following id " + id);
  }
}