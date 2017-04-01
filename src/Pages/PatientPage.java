package Pages;

import Database.Contact;
import Database.Patient;
import Database.PatientContact;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Hayley on 2017-03-28.
 */
public class PatientPage {
    private Connection con;
    
    public PatientPage(Connection con){
    	this.con = con;
    }

    // For doctors + Receptionists all patients
    public ArrayList<Patient> getPatients(){
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        try{
            ps = con.prepareStatement("SELECT * FROM PATIENTS");
            rs = ps.executeQuery();
            while(rs.next()){
                Patient patient = new Patient(rs.getInt("PatientID"),rs.getString("firstName"),rs.getString("lastName"), rs.getString("gender"));
                patientList.add(patient);
            }
            ps.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
            return null;
        }
        return patientList;
    }

    // For doctors + Receptionists get specific patient by Name, may get multiple
    // Selection and projection query
    public ArrayList<Patient> getPatient(int patientID){
        ResultSet rs;
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        PreparedStatement ps;

        try{
        		ps = con.prepareStatement("SELECT * FROM PATIENTS p WHERE " + "p.PatientID = ?");
        		ps.setInt(1, patientID);

            rs = ps.executeQuery();

            while(rs.next()){
               Patient patient = new Patient(rs.getInt("PatientID"),rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"));
               patientList.add(patient);
            }
            ps.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
            return null;
        }
        return patientList;

    }

    //Update Patient and Contact Info
    // Update operation
    public void updatePatient(int patientID, String firstName, String lastName, String gender, Number aptHouseNumber,String street, String city, String postalCode, String province, Long phoneNumber, String notes, String insuranceProviderName){
        PreparedStatement ps;
        ResultSet rs;
        int insuranceProviderID = 0;
        try{
            if (!insuranceProviderName.equals("")) {
                ps = con.prepareStatement("SELECT * FROM InsuranceProviders  WHERE " + "InsuranceProviderName = ?");
                ps.setString(1, insuranceProviderName);
                rs = ps.executeQuery();
                rs.next();
                insuranceProviderID = rs.getInt("InsuranceProviderID");
            }

            ps = con.prepareStatement("SELECT * FROM Patients WHERE  PatientID = ?");
            ps.setInt(1, patientID);
            rs = ps.executeQuery();
            rs.next();
            Patient oldPatient = new Patient(rs.getInt("PatientID"), rs.getString("FirstName"), rs.getString("LastName"),rs.getString("Gender"));

            if (insuranceProviderName.equals("")) {
                insuranceProviderID = rs.getInt("FK_InsuranceProviderID");
            }
            if (firstName.equals("")){
                firstName = oldPatient.getFirstName();
            }
            if (lastName.equals("")){
                lastName = oldPatient.getLastName();
            }
            if (gender.equals("")){
                gender = oldPatient.getGender();
            }

            ps = con.prepareStatement("update Patients set FirstName = ?,  LastName = ?, Gender = ?, FK_InsuranceProviderID = ? where PatientID = ?");
            ps.setString(1,firstName);
            ps.setString(2,lastName);
            ps.setString(3, gender);
            ps.setInt(4,insuranceProviderID);
            ps.setInt(5,patientID);
            ps.executeUpdate();
            ps.close();

        	//Get the old contact info
        	ps = con.prepareStatement("SELECT * FROM CONTACTS WHERE FK_PatientID = ?");
        	ps.setInt(1, patientID);
        	rs = ps.executeQuery();
        	rs.next();
        	Contact oldContact = new Contact(rs.getInt("aptHouseNumber"), rs.getString("Street"),rs.getString("City"), rs.getString("postalCode"), rs.getString("province"), rs.getLong("PhoneNumber"), rs.getString("Notes"));
        	int contactID = rs.getInt("ContactID");

        	//Fill in any missing contact info
        	if(aptHouseNumber.equals(0)){
        		aptHouseNumber = oldContact.getAptHouseNumber();
        	}
        	if(street.equals("")){
        		street = oldContact.getStreet();
        	}
        	if(city.equals("")){
        		city = oldContact.getCity();
        	}
        	if(postalCode.equals("")){
        		postalCode = oldContact.getPostalCode();
        	}
        	if(province.equals("")){
        		province = oldContact.getProvince();
        	}
        	if(phoneNumber == 0){
        		//phoneNumber = oldContact.getPhoneNumber();
        	}
        	if(notes.equals("")){
        		notes = oldContact.getNotes();
        	}
            ps = con.prepareStatement("update Contacts set aptHouseNumber = ?, Street = ?, City = ?, PostalCode = ?, Province = ?, PhoneNumber = ?, Notes = ?  where ContactID = ?");
            ps.setInt(1, (Integer) aptHouseNumber);
            ps.setString(2,street);
            ps.setString(3,city);
            ps.setString(4, postalCode);
            ps.setString(5,province);
            ps.setLong(6,  phoneNumber);
            ps.setString(7, notes);
            ps.setInt(8, contactID);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }
    }

    /* Select PatientID FROM Patients P Where not EXISTS (SELECT AppointmentID from Appointments A where A.FK_PatientID = P.PatientID) Minus (select PatientID FROM Patients P  Minus (SELECT FK_PatientID from contacts))

      */
    public ArrayList<PatientContact>  contactNewPatients(){
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Integer> patientIds = new ArrayList<Integer>();
        ArrayList<PatientContact> patientContactList = new ArrayList<PatientContact>();
        try{
            ps = con.prepareStatement("Select PatientID FROM Patients P Where not EXISTS (SELECT AppointmentID from Appointments A where A.FK_PatientID = P.PatientID) Minus (select PatientID FROM Patients P  Minus (SELECT FK_PatientID from contacts))");
            rs = ps.executeQuery();
            while(rs.next()){
               int patientId = rs.getInt("PatientID");
                patientIds.add(patientId);
            }
            ps.close();

            for (Integer patientId : patientIds) {
                ps = con.prepareStatement("SELECT * FROM Patients, Contacts WHERE PatientID = ? AND FK_PatientID = PatientID");
                ps.setInt(1, patientId);
                rs = ps.executeQuery();
                rs.next();
                PatientContact patientContact = new PatientContact(rs.getInt("PatientID"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"), rs.getLong("PhoneNumber"));
                patientContactList.add(patientContact);
                ps.close();
            }
        }
        catch(SQLException ex) {
            System.out.println("Message: "+ex.getMessage());
            return null;
        }
        return patientContactList;

    }


}
