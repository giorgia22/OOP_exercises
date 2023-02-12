package it.polito.oop.futsal;

import java.util.*;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
    List<Field> fields = new ArrayList<>();
    List<Associate> associates = new ArrayList<>();
    String openTime, closingTime = "24:00";
    int countAss = 0;

    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Features features = (Features) o;
            return indoor == features.indoor && heating == features.heating && ac == features.ac;
        }
    }
    
    public void defineFields(Features... features) throws FutsalException {
        for(Features f: features){
            if(!f.indoor && (f.heating || f.ac))
                throw new FutsalException();
            fields.add(new Field(f));
        }
    }
    
    public long countFields() {
        return fields.size();
    }

    public long countIndoor() {
        int i = 0;
        for(Field f: fields){
            if(f.getFeat().indoor)
                i++;
        }
        return i;
    }
    
    public String getOpeningTime() {
        return openTime;
    }
    
    public void setOpeningTime(String time) {
        openTime = time;
    }
    
    public String getClosingTime() {
        return closingTime;
    }
    
    public void setClosingTime(String time) {
        closingTime = time;
    }

    public int newAssociate(String first, String last, String mobile) {
        Associate a = new Associate(first, last, mobile);
        associates.add(a);
        countAss++;
        return countAss-1;
    }

    public Associate getAssociate(int id) throws FutsalException{
        try{
            associates.get(id);
        }catch (IndexOutOfBoundsException e){
            throw new FutsalException();
        }
        return associates.get(id);
    }
    public String getFirst(int partyId) throws FutsalException {
        return getAssociate(partyId).getName();
    }
    
    public String getLast(int associate) throws FutsalException {
        return getAssociate(associate).getSurname();
    }
    
    public String getPhone(int associate) throws FutsalException {
        return getAssociate(associate).getCell();
    }
    
    public int countAssociates() {
        return associates.size();
    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
        getAssociate(associate);
        try{
            fields.get(field-1);
        }catch (IndexOutOfBoundsException e){
            throw new FutsalException();
        }
        if(getMinutes(time) != getMinutes(openTime))
            throw new FutsalException();
        associates.get(associate).setReserv(1);
        if(isBooked(field, time))
            throw new FutsalException();
        fields.get(field - 1).getReservations().put(time, associate);
    }

    public int getMinutes(String time){
        String[] s = time.split(":");
        return Integer.parseInt(s[1]);
    }

    public int getHours(String time){
        String[] s = time.split(":");
        return Integer.parseInt(s[0]);
    }

    public boolean isBooked(int field, String time) {
        if(fields.get(field-1).getReservations().get(time) == null)
            return false;
        return true;
    }
    

    public int getOccupation(int field) {
        return fields.get(field-1).getReservations().size();
    }
    
    
    public List<FieldOption> findOptions(String time, Features required){
        List<FieldOption> l = new LinkedList<>();
        for(Field f1:fields){
            int i = 1;
            if(required.indoor)
                if(!f1.getFeat().indoor)
                    i = 0;
            if(required.heating)
                if(!f1.getFeat().heating)
                    i = 0;
            if(required.ac)
                if(!f1.getFeat().ac)
                    i = 0;
            if(i==1 && f1.getReservations().get(time) == null){
                FieldOption f = new FieldOption() {
                    @Override
                    public int getField() {
                        return fields.indexOf(f1)+1;
                    }

                    @Override
                    public int getOccupation() {
                        return Fields.this.getOccupation(getField());
                    }
                };
                l.add(f);
            }
        }
        l.sort(Comparator.comparing(FieldOption::getOccupation).thenComparing(FieldOption::getField));
        return l;
    }
    
    public long countServedAssociates() {
        int i = 0;
        for(Associate a : associates){
            if(a.getReserv() == 1)
                i++;
        }
        return i;
    }
    
    public Map<Integer,Long> fieldTurnover() {
        Map<Integer, Long> m = new HashMap<>();
        for(Field f : fields){
            int index = fields.indexOf(f);
            m.put(index+1, (long) fields.get(index).getReservations().size());
        }
        return m;
    }
    
    public double occupation() {
        int totRes = 0;
        int nBlocks = getHours(closingTime)-getHours(openTime);
        if(getMinutes(closingTime) < getMinutes(openTime))
            nBlocks--;
        for(Field f : fields){
            totRes += f.getReservations().size();
        }
        return (((double) totRes)/(((double) nBlocks)*fields.size()));
    }
    
}
