package Pages;

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
    public void updatePatient(int patientID, String firstName, String lastName, Number aptHouseNumber,String street, String city, String postalCode, String province, Number phoneNumber, String notes){
        ResultSet rs;
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        PreparedStatement ps;

        try{

            ps = con.prepareStatement("update Patients set FirstName = ?,  LastName = ? where PatientID = ?");
            ps.setString(1,firstName);
            ps.setString(2,lastName);
            ps.setInt(3,patientID);
            ps.executeUpdate();
            ps.close();

            ps = con.prepareStatement("update Contacts set aptHouseNumber = ?, Street = ?, City = ?, PostalCode = ?, Province = ?, PhoneNumber = ?, Notes = ?  where FK_PatientID = ?");
            ps.setInt(1, (Integer) aptHouseNumber);
            ps.setString(2,street);
            ps.setString(3,city);
            ps.setString(4, postalCode);
            ps.setString(5,province);
            ps.setInt(6, (Integer) phoneNumber);
            ps.setString(7, notes);
            ps.setInt(8, patientID);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }
    }

    //Adds patient + Contact Info to database
    public void addPatient(int patientID, String firstName, String lastName, String gender, String InsuranceProviderName, int PatientPrescriptionID,  Number aptHouseNumber,String street, String city, String postalCode, String province, Number phoneNumber, String notes){
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = con.prepareStatement("SELECT * FROM InsuranceProviders  WHERE " + "InsuranceProviderName = ?");
            ps.setString(1, InsuranceProviderName);
            rs = ps.executeQuery();
            int insuranceProviderID = rs.getInt("InsuranceProviderID");
            ps.close();

            String SQL = "INSERT INTO Patients VALUES (?,?,?,?,?,?)";

            ps = con.prepareStatement(SQL);
            ps.setInt(1, patientID);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, gender);
            ps.setInt(5, insuranceProviderID);
            ps.setInt(6, PatientPrescriptionID);

            ps.executeUpdate();
            ps.close();

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
            ps.close();
        }
        catch (SQLException ex){
            System.out.println("Message: "+ex.getMessage());

        }
    }

    //Delete patient + Contact Info to database
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
