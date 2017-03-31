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


    public PrescriptionPage(Connection con){
    	this.con = con;
    }

    // For doctors get specific patient's prescriptions;
    // Inner Join
    public ArrayList<Prescription> getPatientPrescriptions(int patientID){
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Prescription> prescriptonList = new ArrayList<Prescription>();

        try {
            ps = con.prepareStatement("SELECT * FROM PATIENTS pat INNER JOIN  Patients_Prescriptions patpre ON patpre.FK_PatientID = pat.PatientID INNER JOIN Prescriptions pre ON pre.PrescriptionID = patpre.FK_PrescriptionID INNER JOIN Medications med ON med.MedicationID = pre.FK_MedicationID Where PatientID =?");
            ps.setInt(1, patientID);
            rs = ps.executeQuery();
            while (rs.next()) {
            	System.out.println("Next?\n");
                Prescription prescription = new Prescription(rs.getInt("Doseage"),  rs.getDate("MedStartDate"), rs.getDate("MedEndDate"), rs.getString("GenericOK"), rs.getInt("FK_MedicationID"));
                prescriptonList.add(prescription);
                patientID = rs.getInt("PatientID");
            }
            System.out.println("Result set depleted\n");
            ps.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }
        return prescriptonList;
    }

    //Get Number of Prescriptions for Patient
    // Aggregation query
    public int getTotalPrescriptions(int patientID){
        ResultSet rs;
        PreparedStatement ps;
        int totalPrescriptions = 0;
        try {
            ps = con.prepareStatement("SELECT COUNT (*) as Total FROM PATIENTS pat INNER JOIN  Patients_Prescriptions patpre ON patpre.FK_PatientID = pat.PatientID INNER JOIN Prescriptions pre ON pre.PrescriptionID = patpre.FK_PrescriptionID INNER JOIN Medications med ON med.MedicationID = pre.FK_MedicationID Where PatientID =?");
            ps.setInt(1, patientID);
            rs = ps.executeQuery();
            rs.next();
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
        String medication = "";

        try {
            if (maxMin.equals("min")){
            ps = con.prepareStatement("SELECT  MIN(AVG(Doseage)) FROM Prescriptions GROUP BY FK_MedicationID");
            }

            else if(maxMin.equals("max")){
                ps = con.prepareStatement("SELECT  MAX(AVG(Doseage)) FROM Prescriptions GROUP BY FK_MedicationID");           
            }
            else{
                throw new SQLException("Didn't receive Max or Min");
            }
            rs = ps.executeQuery();
            rs.next();
            medication = rs.getString(1);

            ps.close();

        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }

        return medication;

    }






    }









