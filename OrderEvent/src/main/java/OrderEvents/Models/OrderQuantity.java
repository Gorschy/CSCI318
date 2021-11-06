package OrderEvent;

public class OrderQuantity {
    private String name;
    private long quantity;

    public OrderQuantity() {
    }

    public OrderQuantity(String name, long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderQuantity{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}