package sports;
import java.util.*;



/**
 * Facade class for the research evaluation system
 *
 */
public class Sports {
    List<String> activitiesList = new LinkedList<>();
    List<String> categories = new LinkedList<>();
    Map<String, List<String>> catForAct = new TreeMap<>(Comparator.naturalOrder());
    List<Product> products = new LinkedList<>();
    int numCat = 0;
    //R1
    /**
     * Define the activities types treated in the portal.
     * The method can be invoked multiple times to add different activities.
     * 
     * @param actvities names of the activities
     * @throws SportsException thrown if no activity is provided
     */
    public void defineActivities (String... activities) throws SportsException {
        if(activities.length == 0)
            throw new SportsException("");
        for(String a : activities){
            activitiesList.add(a);
            catForAct.put(a, new LinkedList<>());
        }
    }

    /**
     * Retrieves the names of the defined activities.
     * 
     * @return activities names sorted alphabetically
     */
    public List<String> getActivities() {
        activitiesList.sort(null);
        return activitiesList;
    }

    /**
     * Add a new category of sport products and the linked activities
     * 
     * @param name name of the new category
     * @param activities reference activities for the category
     * @throws SportsException thrown if any of the specified activity does not exist
     */
    public void addCategory(String name, String... linkedActivities) throws SportsException {
        if(activitiesList.containsAll(Arrays.asList(linkedActivities)) == false)
            throw new SportsException("");
        categories.add(name);
        numCat++;
        for (String a: linkedActivities) {
            catForAct.get(a).add(name);
        }
    }

    /**
     * Retrieves number of categories.
     * 
     * @return categories count
     */
    public int countCategories() {
        return numCat;
    }

    /**
     * Retrieves all the categories linked to a given activity.
     * 
     * @param activity the activity of interest
     * @return list of categories (sorted alphabetically)
     */
    public List<String> getCategoriesForActivity(String activity) {
        List<String> l = new LinkedList<>();
        if(!activitiesList.contains(activity))
            return l;
        catForAct.get(activity).sort(null);
        return catForAct.get(activity);
    }

    //R2
    /**
     * Add a research group and the relative disciplines.
     * 
     * @param name name of the research group
     * @param disciplines list of disciplines
     * @throws SportsException thrown in case of duplicate name
     */
    public void addProduct(String name, String activityName, String categoryName) throws SportsException {
        Product p = new Product(name, activityName, categoryName);
        if(products.contains(p) == true)
            throw new SportsException("");
        products.add(p);
    }

    /**
     * Retrieves the list of products for a given category.
     * The list is sorted alphabetically.
     * 
     * @param categoryName name of the category
     * @return list of products
     */
    public List<String> getProductsForCategory(String categoryName){
        List<String> lProd = new LinkedList<>();
        for (Product p:products) {
            if(p.getCategory().equals(categoryName))
                lProd.add(p.getName());
        }
        lProd.sort(null);
        return lProd;
    }

    /**
     * Retrieves the list of products for a given activity.
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @return list of products
     */
    public List<String> getProductsForActivity(String activityName){
        List<String> lProd = new LinkedList<>();
        for (Product p:products) {
            if(p.getActivity().equals(activityName))
                lProd.add(p.getName());
        }
        lProd.sort(null);
        return lProd;
    }

    /**
     * Retrieves the list of products for a given activity and a set of categories
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @param categoryNames names of the categories
     * @return list of products
     */
    public List<String> getProducts(String activityName, String... categoryNames){
        List<String> lProd = new LinkedList<>();
        for (Product p:products) {
            int i = 0;
            for(String s : categoryNames)
                if(s.equals(p.getCategory()))
                    i = 1;
            if(p.getActivity().equals(activityName) && i == 1)
                lProd.add(p.getName());
        }
        lProd.sort(null);
        return lProd;
    }

    //    //R3
    /**
     * Add a new product rating
     * 
     * @param productName name of the product
     * @param userName name of the user submitting the rating
     * @param numStars score of the rating in stars
     * @param comment comment for the rating
     * @throws SportsException thrown numStars is not correct
     */
    public void addRating(String productName, String userName, int numStars, String comment) throws SportsException {
        if(numStars<0 || numStars>5)
            throw new SportsException("");
        for(Product p: products){
            if(p.getName().equals(productName)) {
                p.addRating(userName, numStars, comment);
                break;
            }
        }
    }



    /**
     * Retrieves the ratings for the given product.
     * The ratings are sorted by descending number of stars.
     * 
     * @param productName name of the product
     * @return list of ratings sorted by stars
     */
    public List<String> getRatingsForProduct(String productName) {
        List<Rating> l1;
        List<String> l2 = new LinkedList<>();
        for(Product p: products){
            if(p.getName().equals(productName)) {
                l1 = p.getRatings();
                l1.sort(Comparator.comparing(Rating::getStars).reversed());
                for(Rating s:l1){
                    l2.add(s.getStars().toString() + " : " + s.getComment());
                }
                return l2;
            }
        }
        return l2;
    }


    //R4
    /**
     * Returns the average number of stars of the rating for the given product.
     * 
     * 
     * @param productName name of the product
     * @return average rating
     */
    public double getStarsOfProduct (String productName) {
        List<Rating> l1;
        double avg = 0.0;
        for(Product p: products){
            if(p.getName().equals(productName)) {
                l1 = p.getRatings();
                for(Rating s:l1){
                    avg += s.getStars();
                }
                if(avg == 0.0)
                    return avg;
                return avg/(l1.size());
            }
        }
        return avg;
    }

    /**
     * Computes the overall average stars of all ratings
     *  
     * @return average stars
     */
    public double averageStars() {
        List<Rating> l1;
        double avg = 0.0;
        int numR = 0;
        for(Product p: products){
            l1 = p.getRatings();
            for(Rating s:l1){
                avg += s.getStars();
                numR++;
            }
        }
        if(avg == 0.0)
            return avg;
        return avg/numR;
    }

    //R5 Statistiche
    /**
     * For each activity return the average stars of the entered ratings.
     * 
     * Activity names are sorted alphabetically.
     * 
     * @return the map associating activity name to average stars
     */
    public SortedMap<String, Double> starsPerActivity() {
        SortedMap<String, Double> m = new TreeMap<>(Comparator.naturalOrder());
        for(String a : activitiesList){
            int num = 0;
            double avg = 0.0;
            for(Product p: products){
                if(p.getActivity().equals(a)){
                    for(Rating r : p.getRatings()){
                        num++;
                        avg += r.getStars();
                    }
                }
            }
            if(num != 0){
                if(avg == 0.0)
                    m.put(a, 0.0);
                m.put(a, avg/num);
            }
        }
        return m;
    }

    /**
     * For each average star rating returns a list of
     * the products that have such score.
     * 
     * Ratings are sorted in descending order.
     * 
     * @return the map linking the average stars to the list of products
     */
    public SortedMap<Double, List<String>> getProductsPerStars () {
        Map<String, Double> m1 = new HashMap<>();
        SortedMap<Double, List<String>> m2 = new TreeMap<>(Comparator.reverseOrder());
        for(Product p: products){
            int num = 0;
            Double avg = 0.0;
            for(Rating r : p.getRatings()){
                num++;
                avg += r.getStars();
            }
            if(num != 0){
                if(avg == 0.0)
                    m1.put(p.getName(), 0.0);
                m1.put(p.getName(), avg/num);
            }
        }
        for(String s : m1.keySet()){
            Double key = m1.get(s);
            if(m2.get(key) == null){
                m2.put(key, new LinkedList<>());
            }
            m2.get(key).add(s);
        }
        for(Double d : m2.keySet()){
            m2.get(d).sort(null);
        }
        return m2;
    }

}