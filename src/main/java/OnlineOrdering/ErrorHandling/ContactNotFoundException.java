package OnlineOrdering;

class ContactNotFoundException extends RuntimeException {

  ContactNotFoundException(Long id) {
    super("Could not find customer contact with following id " + id);
  }
}