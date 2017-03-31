package Database;// We need to import the java.sql package to use JDBC

// for reading from the command line


public class Patient{

    private int patientID;
    private String firstName;
    private String lastName;
    private String gender;

    public Patient( int patientID, String firstName, String lastName, String gender){
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.patientID = patientID;
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