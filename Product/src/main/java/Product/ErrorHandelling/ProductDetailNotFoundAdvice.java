package Product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ProductDetailNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(ProductDetailNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String productDetailNotFoundHandler(ProductDetailNotFoundException ex) {
    return ex.getMessage();
  }
}