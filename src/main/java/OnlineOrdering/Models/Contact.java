package OnlineOrdering;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
class Contact {

  private @Id @GeneratedValue Long id;
  private String name;
  private long phone;
  private String email;
  private String position;

  Contact() {}

  Contact(String name, long phone, String email, String position) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.position = position;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public long getPhone() {
    return this.phone;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPosition() {
    return this.position;
  }


  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPhone(long phone) {
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
    if (!(o instanceof Contact))
      return false;
      Contact contact = (Contact) o;
    return Objects.equals(this.id, contact.id)
        && Objects.equals(this.name, contact.name)
        && Objects.equals(this.phone, contact.phone) 
        && Objects.equals(this.email, contact.email) 
        && Objects.equals(this.position, contact.position) ;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.phone, this.email, this.position);
  }

  @Override
  public String toString() {
    return "Contact{" + "id='" + this.id + '\'' + "name='" + this.name + '\'' + ", phone='" + this.phone + '\'' +  ", email='" + this.email + '\'' + ", position='" + this.position + '\'' +'}';
  }
}