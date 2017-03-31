package Pages;

import Database.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Hayley on 2017-03-28.
 */
/*
SELECT cname
        FROM Customer C
        WHERE NOT EXISTS
        ((SELECT I.iid
        FROM Item I)
        EXCEPT
        (SELECT R.iid
        FROM Order R
        WHERE R.cid=C.cid))

Select PatientID FROM Patients P WHERE  EXISTS (SELECT A.AppointmentID FROM Appointments A) EXCEPT
SELECT PhoneNumber FROM Contact  WHERE PatientID = FK_PatientID;

Select PatientID FROM Patients P Where EXISTS ((SELECT AppointmentID from Appointments A where A.FK_PatientID = P.PatientID) Minus (SELECT FK_PatientID FROM Contacts));

Select PatientID FROM Patients P Where EXISTS ((SELECT AppointmentID from Appointments A where A.FK_PatientID = P.PatientID) Minus (SELECT c.PhoneNumber FROM Contacts c WHERE PatientID = c.FK_PatientID AND ))

Select PatientID FROM Patients P Where Not EXISTS ((SELECT AppointmentID from Appointments A where A.FK_PatientID = P.PatientID) Minus (SELECT c.PhoneNumber FROM Contacts c WHERE PatientID = c.FK_PatientID)) // Add a not exists

        */


public class AppointmentPage {
    private Connection con;
    private int userID;

    // Get Appointments by Time, Date and Patient Name
    public ArrayList<Appointment> getAppointmentsbyDoctor(Date appointmentDate, Number appointmentTime, int patientID){
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();

        try {
            ps = con.prepareStatement("SELECT  a.AppointmentDate, a.AppointmentTime, a.RoomNumber, a.Reason, p.FirstName, p.LastName FROM Appointments a,Patients p  WHERE " + "p.PatientID = ? AND a.AppointmentDate = ? AND a.AppointmentTime = ? AND p.PatientID = a.FK_PatientID");
            ps.setInt(1, patientID);
            ps.setDate(2, (java.sql.Date) appointmentDate);
            ps.setInt(3, (Integer) appointmentTime);

            rs = ps.executeQuery();


            while (rs.next()) {
                Appointment appointment = new Appointment(rs.getDate("AppointmentDate"), rs.getInt("AppointmentTime"), rs.getInt("RoomNumber"), rs.getString("Reason"));
                appointmentList.add(appointment);
            }
            ps.close();
        }

        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }
        return appointmentList;


    }

    //Insert Appointment into Database by Time, Date and Patient Name
    public void addAppointment(Date appointmentDate, Number appointmentTime,int roomNumber, String reason, int patientID){
        PreparedStatement ps;
        ResultSet rs;

        try {
            Random random = new Random();
            int appointmentID = random.nextInt();
            String SQL = "INSERT INTO Appointments VALUES (?,?,?,?,?,?,?)";

            ps = con.prepareStatement(SQL);
            ps.setInt(1, appointmentID);
            ps.setDate(2, (java.sql.Date) appointmentDate);
            ps.setInt(3, (Integer) appointmentTime);
            ps.setInt(4,roomNumber);
            ps.setString(5, reason);
            ps.setInt(6, patientID);
            ps.setInt(7, 1); //TODO: this should User that is logged on, should be available globally but not right now
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException ex){
            System.out.println("Message: "+ex.getMessage());

        }
    }

    //Delete Appointment from Database by Time, Date and Patient Name
    //  Delete operation:
    public void deleteAppointment(Date appointmentDate, Number appointmentTime, int patientID){
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = "DELETE FROM Appointments  WHERE " + "AppointmentDate = ? AND AppointmentTime = ? AND FK_PatientID = ? ";
            ps = con.prepareStatement(sql);

            ps.setDate(1, (java.sql.Date) appointmentDate);
            ps.setInt(2,(Integer) appointmentTime );
            ps.setInt(3,patientID);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException ex){
            System.out.println("Message: "+ex.getMessage());
        }
    }






}
