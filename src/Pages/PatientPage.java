package Pages;

import Database.Contact;
import Database.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

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
        	if(aptHouseNumber.equals(-1)){
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
        	if(phoneNumber == -1){
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

    //Adds patient + Contact Info to database
    public void addPatient(int patientID, String firstName, String lastName, String gender, String InsuranceProviderName, Number aptHouseNumber,String street, String city, String postalCode, String province, Number phoneNumber, String notes){
        PreparedStatement ps;
        ResultSet rs;

        try {

            /*
            ps = con.prepareStatement("SELECT * FROM InsuranceProviders  WHERE " + "InsuranceProviderName = ?");
            ps.setString(1, InsuranceProviderName);
            rs = ps.executeQuery();
            rs.next();
            int insuranceProviderID = rs.getInt("InsuranceProviderID");
            */

            String SQL = "INSERT INTO Patients VALUES (?,?,?,?,?)";

            ps = con.prepareStatement(SQL);
            ps.setInt(1, patientID);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, gender);
            ps.setInt(5, 1);
            ps.executeUpdate();
/*
            Random random = new Random();
            int contactID = random.nextInt();
            SQL = "INSERT INTO Contacts VALUES (?,?,?,?,?,?,?,?,?)";
            ps = con.prepareStatement(SQL);

            ps.setInt(1, contactID);
            ps.setInt(2, patientID);
            ps.setInt(3, (Integer) aptHouseNumber);
            ps.setString(4, street);
            ps.setString(5, city);
            ps.setString(6, postalCode);
            ps.setString(7, province);
            ps.setInt(8, (Integer) phoneNumber);
            ps.setString(9,  notes);
            ps.executeUpdate();

            */
            ps.close();
        }
        catch (SQLException ex){
            System.out.println("Message: "+ex.getMessage());
        }
    }

    //Delete patient + Contact Info from the database
    public void deletePatient(int patientID){
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = "DELETE FROM Patients  WHERE " + "PatientID = ?";
            ps = con.prepareStatement(sql);

            ps.setInt(1,  patientID);
            ps.executeUpdate();
            ps.close();

            sql = "DELETE FROM Contacts WHERE " + "FK_PatientID = ?" ;
            ps = con.prepareStatement(sql);

            ps.setInt(1,  patientID);
            ps.executeUpdate();
            ps.close();

        }
        catch(SQLException ex){
            System.out.println("Message: "+ex.getMessage());
        }
    }


}
