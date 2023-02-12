package fit;

import java.util.*;


public class Fit {
    
    public static int MONDAY    = 1;
    public static int TUESDAY   = 2;
    public static int WEDNESDAY = 3;
    public static int THURSDAY  = 4;
    public static int FRIDAY    = 5;
    public static int SATURDAY  = 6;
    public static int SUNDAY    = 7;

    Map<String, Gym> gyms;
    List<String> customers;
    int numGyms;
    int numCust;
    
	public Fit() {
		gyms = new HashMap<>();
		customers = new ArrayList<>();
		numCust = 0;
		numGyms = 0;
	}
	// R1 
	
	public void addGymn (String name) throws FitException {
		if(gyms.get(name) != null)
			throw new FitException();
		gyms.put(name, new Gym(name));
		numGyms++;
	}
	
	public int getNumGymns() {
		return numGyms;
	}
	
	//R2

	public void addLessons (String gymnname, 
	                        String activity, 
	                        int maxattendees, 
	                        String slots, 
	                        String ...allowedinstructors) throws FitException{
	    
		if(gyms.get(gymnname) == null)
			throw new FitException();
		String[] less = slots.split(",");
		for(String s: less){
			String[] slot = s.split("\\.");
			int day = Integer.parseInt(slot[0]) - 1;
			int time = Integer.parseInt(slot[1]) - 8;
			if(day < 0 || day > 6 || time < 0 || time > 12)
				throw new FitException();
			if(gyms.get(gymnname).getWeek().get(day).get(time).isProvide())
				throw new FitException();
			gyms.get(gymnname).getWeek().get(day).get(time).setProvide(true);
			gyms.get(gymnname).getWeek().get(day).get(time).setSpecialty(activity);
			gyms.get(gymnname).getWeek().get(day).get(time).setMaxParticipants(maxattendees);
			gyms.get(gymnname).getWeek().get(day).get(time).setAllowedInstructors(new HashSet<>(Arrays.asList(allowedinstructors)));
		}
	}
	
	//R3
	public int addCustomer(String name) {
		numCust++;
		customers.add(name);
		return numCust;
	}
	
	public String getCustomer (int customerid) throws FitException{
		int code = customerid - 1;
		if(code < 0 || code >= customers.size())
			throw new FitException();
	    return customers.get(code);
	}
	
	//R4
	
	public void placeReservation(int customerid, String gymnname, int day, int slot) throws FitException{
		getCustomer(customerid);
		if(gyms.get(gymnname) == null || day < 1 || day > 7 || slot < 8 || slot > 20)
			throw new FitException();
		int max = gyms.get(gymnname).getWeek().get(day - 1).get(slot - 8).getMaxParticipants();
		if(!gyms.get(gymnname).getWeek().get(day - 1).get(slot - 8).isProvide())
			throw new FitException();
		if(gyms.get(gymnname).getWeek().get(day - 1).get(slot - 8).getParticipants().size() >= max)
			throw new FitException();
		if(!gyms.get(gymnname).getWeek().get(day - 1).get(slot - 8).getParticipants().add(customerid))
			throw new FitException();
	}
	
	public int getNumLessons(int customerid) {
		int numLes = 0;
		for(Gym g : gyms.values()){
			for(List<Lesson> d: g.getWeek()){
				for(Lesson l: d){
					if(l.getParticipants().contains(customerid))
						numLes++;
				}
			}
		}
		return numLes;
	}
	
	
	//R5
	
	public void addLessonGiven (String gymnname, int day, int slot, String instructor) throws FitException{
		if(gyms.get(gymnname) == null || day < 1 || day > 7 || slot < 8 || slot > 20)
			throw new FitException();
		if(!gyms.get(gymnname).getWeek().get(day - 1).get(slot - 8).getAllowedInstructors().contains(instructor))
			throw new FitException();
		gyms.get(gymnname).getWeek().get(day - 1).get(slot - 8).setInstructor(instructor);
	}
	
	public int getNumLessonsGiven (String gymnname, String instructor) throws FitException {
	    int numLess = 0;
	    if(gyms.get(gymnname) == null)
	    	throw new FitException();
	    for(List<Lesson> d: gyms.get(gymnname).getWeek()){
	    	for (Lesson l: d){
	    		if(l.getInstructor().equals(instructor))
	    			numLess++;
			}
		}
		return numLess;
	}
	//R6
	
	public String mostActiveGymn() {
		Map<String, Integer> m = totalLessonsPerGymn();
		int max = 0;
		String name = "";
		for(String s: m.keySet()){
			if(m.get(s) > max){
				max = m.get(s);
				name = s;
			}
		}
		return name;
	}
	
	public Map<String, Integer> totalLessonsPerGymn() {
		Map<String, Integer> m = new HashMap<>();
		for(Gym g: gyms.values()){
			int num = 0;
			for(List<Lesson> d: g.getWeek()){
				for(Lesson l: d){
					if(l.isProvide())
						num++;
				}
			}
			m.put(g.getName(), num);
		}
		return m;
	}
	
	public SortedMap<Integer, List<String>> slotsPerNofParticipants(String gymnname) throws FitException{
	    SortedMap<Integer, List<String>> m = new TreeMap<>(Comparator.naturalOrder());
		Map<String, Integer> m2 = new HashMap<>();
	    for(int d = 0; d < 7; d++){
			for(int s = 0; s < 13; s++){
				int num = gyms.get(gymnname).getWeek().get(d).get(s).getParticipants().size();
				Integer day = d + 1;
				Integer slot = s + 8;
				String str = day.toString()+"."+slot.toString();
				if(gyms.get(gymnname).getWeek().get(d).get(s).isProvide())
					m2.put(str, num);
			}
		}
	    for(String s: m2.keySet()){
	    	if(m.get(m2.get(s)) == null)
	    		m.put(m2.get(s), new LinkedList<>());
	    	m.get(m2.get(s)).add(s);
		}
		return m;
	}
	

	
	
	
	


}
