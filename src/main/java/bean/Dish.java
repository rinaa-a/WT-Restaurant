package bean;

import bean.enums.Category;

import java.util.Objects;

public class Dish {
    private int id;
    private String name;
    private int cost;
    private Category category;
    private int count;

    public Dish() {
        id = -1;
        name = "name";
        cost = 0;
        category = Category.NONE;
        count = 0;
    }

    public Dish(int id, String name, int cost, Category category, int count) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.category = category;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id == dish.id &&
                cost == dish.cost &&
                count == dish.count &&
                Objects.equals(name, dish.name) &&
                category == dish.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, category, count);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", category=" + category +
                ", count=" + count +
                '}';
    }
}
