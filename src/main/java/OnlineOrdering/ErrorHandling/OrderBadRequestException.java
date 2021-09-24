package OnlineOrdering.ErrorHandling;

public class OrderBadRequestException extends RuntimeException {

  public OrderBadRequestException(String message) {
    super(message);
  }
}