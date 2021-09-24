package OnlineOrdering.ErrorHandling;

public class ContactNotFoundException extends RuntimeException {

  public ContactNotFoundException(Long id) {
    super("Could not find customer contact with following id " + id);
  }
}