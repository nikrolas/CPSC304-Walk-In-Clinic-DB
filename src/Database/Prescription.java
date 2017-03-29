package Database;// We need to import the java.sql package to use JDBC

// for reading from the command line

// for dates in Prescription
import java.util.Date;


public class Prescription{
    int doseage;
    Date medStartDate;
    Date medEndDate;
    String genericOK;


    public Prescription( int doseage, Date medStartDate, Date medEndDate, String genericOK){
        this.doseage = doseage;
        this.medStartDate = medStartDate;
        this.medEndDate = medEndDate;
        this.genericOK = genericOK;
    }

    public String getGenericOK() {
        return genericOK;
    }

    public Date getMedEndDate() {
        return medEndDate;
    }

    public Date getMedStartDate() {
        return medStartDate;
    }

    public int getDoseage() {
        return doseage;
    }

    public void setDoseage(int doseage) {
        this.doseage = doseage;
    }

    public void setGenericOK(String genericOK) {
        this.genericOK = genericOK;
    }

    public void setMedEndDate(Date medEndDate) {
        this.medEndDate = medEndDate;
    }

    public void setMedStartDate(Date medStartDate) {
        this.medStartDate = medStartDate;
    }

}

