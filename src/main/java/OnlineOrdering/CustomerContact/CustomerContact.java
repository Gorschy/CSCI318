package OnlineOrdering;

import java.util.Objects;
import javax.persistence.Entity;


@Entity
class CustomerContact {

  private String name;
  private String phone;
  private String email;
  private String position;

  CustomerContact() {}

  CustomerContact(String name, String phone, String email, String position) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.position = position;
  }

  public String getName() {
    return this.name;
  }

  public String getPhone() {
    return this.phone;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPosition() {
    return this.position;
  }


  public void setName(String name) {
    this.name = name;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof CustomerContact))
      return false;
    CustomerContact customerContact = (CustomerContact) o;
    return Objects.equals(this.name, customerContact.name)
        && Objects.equals(this.phone, customerContact.phone) && Objects.equals(this.email, customerContact.email) 
        && Objects.equals(this.position, customerContact.position) ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.phone, this.email, this.position);
  }

  @Override
  public String toString() {
    return "CustomerContact{" + "name='" + this.name + '\'' + ", phone='" + this.phone + '\'' +  ", email='" + this.email + '\'' + ", position='" + this.position + '\'' +'}';
  }
}