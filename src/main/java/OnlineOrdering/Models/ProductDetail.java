package OnlineOrdering.Models;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class ProductDetail {

  private @Id @GeneratedValue Long productDetailId;
  private String description;
  private String comment;

  public ProductDetail() {}

  public ProductDetail(String description, String comment) {
    this.description = description;
    this.comment = comment;
  }

  public Long getId() {
    return this.productDetailId;
  }

  public String getDescription() {
    return this.description;
  }

  public String getComment() {
    return this.comment;
  }


  public void setId(Long productDetailId) {
    this.productDetailId = productDetailId;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof ProductDetail))
      return false;
      ProductDetail productDetail = (ProductDetail) o;
    return Objects.equals(this.productDetailId, productDetail.productDetailId)
        && Objects.equals(this.description, productDetail.description)
        && Objects.equals(this.comment, productDetail.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.productDetailId, this.description, this.comment);
  }

  @Override
  public String toString() {
    return "ProductDetail{" + "id='" + this.productDetailId + '\'' + "description='" + this.description + '\'' + ", comment='" + this.comment + '\'' +'}';
  }
}