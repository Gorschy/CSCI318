/*
 * - Product.java (Product class, modelled after the Customer.java, stored in the models file)
- ProductModelAssembler.java (Same as the CustomerModelAssembler.java, replace customer with product)
- ProductRepository.java (Same as CustomerRepository.java, replace customer with product)
- ProductController.java (Same as CustomerController.java, replace customer with product)
- ProductNotFoundException.java, and ProductNotFoundAdvice.java (same as the Customer error handelling stuffs)
 * 
 * 
 */




package OnlineOrdering;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
class Customer {
    private @Id @GeneratedValue long id;
    private String companyName;
    private String address;
    private String country;

    @OneToOne
    private Contact contact;

    //constructors
    Customer() {}
    Customer(String companyName, String address, String country) {
        this.companyName = companyName;
        this.address = address;
        this.country = country;
    }

    //getter methods
    public Long getId() {
        return this.id;
    }
    public String getCompanyName() {
        return this.companyName;
    }
    public String getAddress() {
        return this.address;
    }
    public String getCountry() {
        return this.country;
    }
    public Contact getContact() {
        return this.contact;
    }

    //setter methods
    public void setId(Long id) {
        this.id = id;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    //overrides
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Customer))
            return false;
        Customer customer = (Customer) o;
        return Objects.equals(this.id, customer.id) &&
               Objects.equals(this.companyName, customer.companyName) &&
               Objects.equals(this.address, customer.address) &&
               Objects.equals(this.country, customer.country) &&
               Objects.equals(this.contact, customer.contact);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.companyName, this.address, this.country, this.contact);
    }
    @Override
    public String toString() {
        return "Customer{" + "id=" + this.id + ", company name='" + this.companyName + '\'' + ", address='" + this.address + '\'' + ", country='" + this.country  + '\'' + ", contact='" + this.contact + '\''  + '}';
        }
}