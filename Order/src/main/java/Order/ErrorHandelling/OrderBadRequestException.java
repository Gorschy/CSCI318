package OrderEntity;

class OrderBadRequestException extends RuntimeException {

    OrderBadRequestException(String message) {
    super(message);
  }
}