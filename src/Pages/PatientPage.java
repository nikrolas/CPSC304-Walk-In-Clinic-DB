package Pages;

import Database.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Hayley on 2017-03-28.
 */
public class PatientPage {
    private Connection con;


    // For doctors + Receptionists all patients
    public ArrayList<Patient> getPatients(){
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        try{
            ps = con.prepareStatement("SELECT * FROM PATIENTS p");
            rs = ps.executeQuery();
            while(rs.next()){
                Patient patient = new Patient(rs.getString("firstName"),rs.getString("lastName"), rs.getString("gender"));
                patientList.add(patient);
                ps.close();
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
    public ArrayList<Patient> getPatient(String firstName, String lastName){
        ResultSet rs;
        ArrayList<Patient> patientList = new ArrayList<Patient>();
        PreparedStatement ps;

        try{
            ps = con.prepareStatement("SELECT * FROM PATIENTS p, WHERE " + "p.firstName = ? AND p.lastName = ?");
            ps.setString(1,firstName);
            ps.setString(2,lastName);
            rs = ps.executeQuery();

            while(rs.next()){
               Patient patient = new Patient(rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"));
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




}
