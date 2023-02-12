package delivery;

import java.util.LinkedList;
import java.util.List;

public class Restaurant {
    private String name, category;
    private List<Integer> ratings;
    private List<Dish> dishes;

    public Restaurant(String name, String category) {
        this.name = name;
        this.category = category;
        ratings = new LinkedList<>();
        dishes = new LinkedList<>();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Integer> getRatings() {
        return ratings;
    }
}
