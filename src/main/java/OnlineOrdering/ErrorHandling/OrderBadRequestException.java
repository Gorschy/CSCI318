package OnlineOrdering;

class OrderBadRequestException extends RuntimeException {

    OrderBadRequestException(String message) {
    super(message);
  }
}