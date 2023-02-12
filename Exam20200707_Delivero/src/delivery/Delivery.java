package delivery;

import java.util.*;


public class Delivery {
	Map<String, Restaurant> restaurants = new TreeMap<>();
	Set<String> categories = new TreeSet<>();
	List<Order> orders = new LinkedList<>();

	// R1
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		if(categories.contains(category) == true)
			throw new DeliveryException();
		categories.add(category);
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return new LinkedList<>(categories);
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
		if(categories.contains(category) == false)
			throw new DeliveryException();
		if(restaurants.get(name) == null)
			restaurants.put(name, new Restaurant(name, category));
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
		List<String> l = new LinkedList<>();
		for(Restaurant r : restaurants.values()){
			if(r.getCategory().equals(category))
				l.add(r.getName());
		}
        return l;
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
	 	Dish d = new Dish(name, price);
		if(restaurants.get(restaurantName).getDishes().contains(d) == true)
			throw new DeliveryException();
		restaurants.get(restaurantName).getDishes().add(d);
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
		Map<String, List<String>> m = new HashMap<>();
		for(Restaurant r : restaurants.values()){
			int i = 0;
			for(Dish d : r.getDishes()){
				if(d.getPrice()>=minPrice && d.getPrice()<= maxPrice) {
					i++;
					if (i == 1)
						m.put(r.getName(), new LinkedList<>());
					m.get(r.getName()).add(d.getName());
				}
			}
		}
		return m;
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
		List<String> l = new LinkedList<>();
		if(restaurants.get(restaurantName) != null)
			for (Dish d : restaurants.get(restaurantName).getDishes()) {
				l.add(d.getName());
			}
		l.sort(null);
		return l;
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
		Set<String> s = new HashSet<>();
		for(Restaurant r : restaurants.values()){
			if(r.getCategory().equals(category)) {
				for (Dish d : r.getDishes()) {
					s.add(d.getName());
				}
			}
		}
        return new LinkedList<>(s);
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
		Order o = new Order(dishNames, quantities, customerName, restaurantName, deliveryTime, deliveryDistance);
		orders.add(o);
	    return orders.indexOf(o) + 1;
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		int i = 0;
		List<Integer> l = new LinkedList<>();
		for(Order o : orders){
			i++;
			if(o.getTime() == deliveryTime && o.getDistance() <= maxDistance && o.isAssigned()==false){
				o.setAssigned(true);
				l.add(i);
			}
			if(l.size() == maxOrders)
				break;
		}
		return l;
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
		int i = 0;
		for(Order o : orders){
			if(o.isAssigned() == false)
				i++;
		}
        return i;
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		if(rating >= 0 && rating <= 5)
			restaurants.get(restaurantName).getRatings().add(rating);
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
		Map<Double, List<String>> m = new TreeMap<>(Comparator.reverseOrder());
		List<String> l = new LinkedList<>();
		for(Restaurant r : restaurants.values()){
			if(r.getRatings().size() != 0) {
				double avg = 0.0;
				for (int i : r.getRatings()) {
					avg += i;
				}
				if(avg == 0.0 && m.get(0.0) == null)
					m.put(0.0, new LinkedList<>());
				if(avg == 0.0)
					m.get(0.0).add(r.getName());
				else{
					avg = avg / r.getRatings().size();
					if(m.get(avg) == null)
						m.put(avg, new LinkedList<>());
					m.get(avg).add(r.getName());
				}
			}
		}
		for(Double d : m.keySet()){
			for(String s : m.get(d)){
				l.add(s);
			}
		}
        return l;
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
		Map<String, Long> m = new LinkedHashMap<>();
		for(String c: categories){
			long i = 0;
			for(Order o:orders){
				if(restaurants.get(o.getRestaurant()).getCategory().equals(c)) {
					i++;
				}
			}
			m.put(c, i);
		}
        return m;
	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
        return restaurantsAverageRating().get(0);
	}
}
