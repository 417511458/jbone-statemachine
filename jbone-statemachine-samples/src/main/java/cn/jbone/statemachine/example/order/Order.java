package cn.jbone.statemachine.example.order;

public class Order {
    private String id;
    private OrderStates state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderStates getState() {
        return state;
    }

    public void setState(OrderStates state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", state=" + state +
                '}';
    }
}
