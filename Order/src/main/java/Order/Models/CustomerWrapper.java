package OrderEntity;

import java.util.List;
import java.util.ArrayList;

public class CustomerWrapper {
    private List<Customer> customers;

    public CustomerWrapper() {
        customers = new ArrayList<>();
    }

    // standard constructor and getter/setter
    public List<Customer> getCustomers() {
        return this.customers;
    }
    
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}