package bean;

import java.util.ArrayList;
import java.util.Objects;

public class Order {
    private int id;
    private User user;
    private int cost;
    private ArrayList<Dish> dishes;

    public Order() {
        id = -1;
        user = new User();
        cost = 0;
        dishes = new ArrayList<>();
    }

    public Order(int id, User user, int cost, ArrayList<Dish> dishes) {
        this.id = id;
        this.user = user;
        this.cost = cost;
        this.dishes = dishes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                cost == order.cost &&
                Objects.equals(user, order.user) &&
                Objects.equals(dishes, order.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, cost, dishes);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", cost=" + cost +
                ", dishes=" + dishes +
                '}';
    }
}
