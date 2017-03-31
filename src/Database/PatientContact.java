package Database;

/**
 * Created by Hayley on 2017-03-31.
 */
public class PatientContact {

    private int patientID;
    private String firstName;
    private String lastName;
    private String gender;
    private Long phoneNumber;

    public PatientContact(int patientID, String firstName, String lastName, String gender, Long phoneNumber){
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.patientID = patientID;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPatientID(){
        return patientID;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPatientID(int patientID){
        this.patientID = patientID;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
