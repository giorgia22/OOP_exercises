package it.polito.oop.futsal;

import java.util.HashMap;
import java.util.Map;

public class Field {
    private Fields.Features feat;
    private Map<String, Integer> reservations;

    public Field(Fields.Features feat) {
        this.feat = feat;
        reservations = new HashMap<>();
    }

    public Map<String, Integer> getReservations() {
        return reservations;
    }

    public void setReservations(Map<String, Integer> reservations) {
        this.reservations = reservations;
    }

    public Fields.Features getFeat() {
        return feat;
    }

    public void setFeat(Fields.Features feat) {
        this.feat = feat;
    }
}
