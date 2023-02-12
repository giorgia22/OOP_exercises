package sports;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Product {
    String name, category, activity;
    List<Rating> ratings;

    public Product(String name, String activity, String category) {
        this.name = name;
        this.category = category;
        this.activity = activity;
        this.ratings = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getActivity() {
        return activity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void addRating(String user, int stars, String comment){
        ratings.add(new Rating(user, comment, stars));
    }

    public List<Rating> getRatings(){
        return ratings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
