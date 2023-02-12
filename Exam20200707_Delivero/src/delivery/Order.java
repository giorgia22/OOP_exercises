package delivery;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String dishes[];
    private int quantities[];
    private String customer, restaurant;
    private int time, distance;
    private boolean assigned;

    public Order(String[] dishes, int[] quantities, String customer, String restaurant, int time, int distance) {
        this.dishes = dishes;
        this.quantities = quantities;
        this.customer = customer;
        this.restaurant = restaurant;
        this.time = time;
        this.distance = distance;
        assigned = false;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public String[] getDishes() {
        return dishes;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
