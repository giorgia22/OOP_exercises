package timesheet;

import java.net.Inet4Address;
import java.util.*;

public class Timesheet {

	List<Integer> holidays = new LinkedList<>();
	int firstDay = 1;
	int count = 0;
	int countProfiles = 0;
	int countWorkers = 0;
	Map<String, Project> projects = new TreeMap<>();
	List<Worker> workers = new ArrayList<>();
	List<int[]> profiles = new ArrayList<>();

	// R1
	public void setHolidays(int... holidays) {
		if(count == 0){
			for(int i : holidays){
				if(i > 0 && i <= 365)
					this.holidays.add(i);
			}
		}
		count++;
	}
	
	public boolean isHoliday(int day) {
		return holidays.contains(day);
	}
	
	public void setFirstWeekDay(int weekDay) throws TimesheetException {
		if(weekDay < 0 || weekDay > 6)
			throw new TimesheetException();
		firstDay = weekDay;
	}
	
	public int getWeekDay(int day) throws TimesheetException {
	    if(day < 1)
	    	throw new TimesheetException();
		return (day-1+firstDay)%7;
	}
	
	// R2
	public void createProject(String projectName, int maxHours) throws TimesheetException {
		if(maxHours < 0)
			throw new TimesheetException();
		if(projects.get(projectName) == null)
			projects.put(projectName, new Project(projectName, 0));
		projects.get(projectName).setMaxHours(maxHours);
	}
	
	public List<String> getProjects() {
		List<Project> l = new LinkedList<>(projects.values());
		List<String > l2 = new LinkedList<>();
		l.sort(Comparator.comparing(Project::getMaxHours).reversed().thenComparing(Project::getName));
        for(Project p: l){
        	l2.add(p.getName()+": "+ p.getMaxHours());
		}
		return l2;
	}
	
	public void createActivity(String projectName, String activityName) throws TimesheetException {
		if(projects.get(projectName) == null)
			throw new TimesheetException();
		projects.get(projectName).getActivities().put(activityName, new Activity(activityName));
	}
	
	public void closeActivity(String projectName, String activityName) throws TimesheetException {
		if(projects.get(projectName) == null || projects.get(projectName).getActivities().get(activityName) == null)
			throw new TimesheetException();
		projects.get(projectName).getActivities().get(activityName).setCompleted(true);
	}
	
	public List<String> getOpenActivities(String projectName) throws TimesheetException {
		List<String> l = new LinkedList<>();
		if(projects.get(projectName) == null)
			throw new TimesheetException();
		for(Activity a: projects.get(projectName).getActivities().values()){
			if(!a.isCompleted())
				l.add(a.getName());
		}
		l.sort(null);
		return l;
	}

	// R3
	public String createProfile(int... workHours) throws TimesheetException {
        if(workHours.length != 7)
        	throw new TimesheetException();
        profiles.add(workHours);
        countProfiles++;
		return String.valueOf(countProfiles - 1);
	}
	
	public String getProfile(String profileID) throws TimesheetException {
        try{
        	Integer.parseInt(profileID);
		}catch(NumberFormatException e){
        	throw new TimesheetException();
		}
		if(profiles.get(Integer.parseInt(profileID)) == null)
        	throw new TimesheetException();
		int[] prof = profiles.get(Integer.parseInt(profileID));
        String str = "Sun: "+String.valueOf(prof[0])+"; Mon: "+String.valueOf(prof[1])
				+"; Tue: "+String.valueOf(prof[2])+"; Wed: "+String.valueOf(prof[3])
				+"; Thu: "+String.valueOf(prof[4])+"; Fri: "+String.valueOf(prof[5])
				+"; Sat: "+String.valueOf(prof[6]);
		return str;
	}
	
	public String createWorker(String name, String surname, String profileID) throws TimesheetException {
		try{
			Integer.parseInt(profileID);
		}catch(NumberFormatException e){
			throw new TimesheetException();
		}
		if(profiles.get(Integer.parseInt(profileID)) == null)
			throw new TimesheetException();
		workers.add(new Worker(name, surname, Integer.parseInt(profileID)));
		countWorkers++;
		return String.valueOf(countWorkers - 1);
	}
	
	public String getWorker(String workerID) throws TimesheetException {
		try{
			Integer.parseInt(workerID);
		}catch(NumberFormatException e){
			throw new TimesheetException();
		}
		if(workers.get(Integer.parseInt(workerID)) == null)
			throw new TimesheetException();
		Worker w = workers.get(Integer.parseInt(workerID));
		String str = w.getName()+" "+w.getSurname()+" ("+getProfile(String.valueOf(w.getProfileCode()))+")";
		return str;
	}
	
	// R4
	public void addReport(String workerID, String projectName, String activityName, int day, int workedHours) throws TimesheetException {
		try{
			Integer.parseInt(workerID);
		}catch(NumberFormatException e){
			throw new TimesheetException();
		}
		if(workers.get(Integer.parseInt(workerID)) == null || day < 1 || day > 365 || workedHours < 0)
			throw new TimesheetException();
		Worker w =workers.get(Integer.parseInt(workerID));
		int h = workedHours + workers.get(Integer.parseInt(workerID)).getHours()[day-1];
		workers.get(Integer.parseInt(workerID)).getHours()[day-1] = h;
		if(profiles.get(w.getProfileCode())[getWeekDay(day)] < h)
			throw new TimesheetException();
		if(projects.get(projectName) == null || projects.get(projectName).getActivities().get(activityName) == null)
			throw new TimesheetException();
		if(projects.get(projectName).getActivities().get(activityName).isCompleted())
			throw new TimesheetException();
		projects.get(projectName).setAllHours(workedHours);
		if(projects.get(projectName).getAllHours() > projects.get(projectName).getMaxHours())
			throw new TimesheetException();
		projects.get(projectName).getActivities().get(activityName).setHours(workedHours);
		if(workers.get(Integer.parseInt(workerID)).getProjects().get(projectName) == null)
			workers.get(Integer.parseInt(workerID)).getProjects().put(projectName, new LinkedList<>());
		if(!workers.get(Integer.parseInt(workerID)).getProjects().get(projectName).contains(activityName))
			workers.get(Integer.parseInt(workerID)).getProjects().get(projectName).add(activityName);
		if(holidays.contains(day))
			throw new TimesheetException();
	}
	
	public int getProjectHours(String projectName) throws TimesheetException {
        if(projects.get(projectName) == null)
        	throw new TimesheetException();
		return projects.get(projectName).getAllHours();
	}
	
	public int getWorkedHoursPerDay(String workerID, int day) throws TimesheetException {
        try{
            Integer.parseInt(workerID);
        }catch(NumberFormatException e){
            throw new TimesheetException();
        }
        if(day < 1 || workers.get(Integer.parseInt(workerID)) == null)
        	throw new TimesheetException();
		return workers.get(Integer.parseInt(workerID)).getHours()[day - 1];
	}
	
	// R5
	public Map<String, Integer> countActivitiesPerWorker() {
		Map<String, Integer> m = new HashMap<>();
		for(int i = 0; i < workers.size(); i++){
			int act = 0;
			for(String p: workers.get(i).getProjects().keySet())
				act += workers.get(i).getProjects().get(p).size();
			m.put(String.valueOf(i), act);
		}
        return m;
	}
	
	public Map<String, Integer> getRemainingHoursPerProject() {
		Map<String, Integer> m = new HashMap<>();
		for(Project p:projects.values()){
			m.put(p.getName(), p.getMaxHours()-p.getAllHours());
		}
        return m;
	}
	
	public Map<String, Map<String, Integer>> getHoursPerActivityPerProject() {
        Map<String, Map<String, Integer>> m = new HashMap<>();
        for(Project p: projects.values()){
        	m.put(p.getName(), new HashMap<>());
        	for(Activity a: p.getActivities().values()){
        		m.get(p.getName()).put(a.getName(), a.getHours());
			}
		}
		return m;
	}
}
