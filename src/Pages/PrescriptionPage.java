package Pages;

import Database.Patient;
import Database.Prescription;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Hayley on 2017-03-28.
 */
public class PrescriptionPage {

    private Connection con;


    // For doctors get specific patient's prescriptions;
    // Inner Join
    public ArrayList<Prescription> getPatientPrescriptions(String firstName, String lastName){
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Prescription> prescriptonList = new ArrayList<Prescription>();
        int patientID = 0;

        try {
            ps = con.prepareStatement("SELECT * FROM PATIENTS INNER JOIN Prescriptions ON PatientID = FK_PatientPrescriptionID INNER JOIN Medications ON FK_MedicationID = MedicationID WHERE " + "FirstName = ? AND LastName = ?");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            rs = ps.executeQuery();

            while (rs.next()) {
                Prescription prescription = new Prescription(rs.getInt("Doseage"),  rs.getDate("MedStartDate"), rs.getDate("MedEndDate"), rs.getString("GenericOK"));
                prescriptonList.add(prescription);
                patientID = rs.getInt("PatientID");
            }
            ps.close();
            getTotalPrescriptions(patientID);
        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }
        return prescriptonList;
    }

    //Get Number of Prescriptions for Patient
    // Aggregation query
    private int getTotalPrescriptions(int patientID){
        ResultSet rs;
        PreparedStatement ps;
        int totalPrescriptions = 0;
        try {
            ps = con.prepareStatement("SELECT COUNT (*) as Total FROM PATIENTS INNER JOIN Prescriptions ON PatientID = FK_PatientPrescriptionID INNER JOIN Medications ON FK_MedicationID = MedicationID WHERE " + "PatientID = ? ");
            ps.setInt(1, patientID);
            rs = ps.executeQuery();
            totalPrescriptions = rs.getInt("Total");
            ps.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }

        return totalPrescriptions;

    }

    //Gets Min or Max Prescriptions for all Patients
    // Nested aggregation with group-by
    public String getMaxorMinMedications(String maxMin){
        ResultSet rs;
        PreparedStatement ps ;
        String medication = null;

        try {
            if (maxMin.equals("min")){
            ps = con.prepareStatement("SELECT  MIN(AVG(Doseage)) FROM Prescriptions GROUP BY FK_MedicationID");
            }
            if(maxMin.equals("max")){
                ps = con.prepareStatement("SELECT  MAX(AVG(Doseage)) FROM Prescriptions GROUP BY FK_MedicationID");
            }
            else{
                throw new SQLException("Didn't receive Max or Min");
            }
            rs = ps.executeQuery();
            medication = rs.getString("MedicationName");
            ps.close();

        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }

        return medication;

    }






    }








