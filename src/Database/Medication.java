package Database;

/**
 * Created by Hayley on 2017-03-28.
 */


public class Medication {
private String medicationName;

public Medication( String medicationName){
    this.medicationName = medicationName;
}

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
}
