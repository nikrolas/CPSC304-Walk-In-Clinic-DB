package Pages;

import Database.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Hayley on 2017-03-28.
 */
public class AppointmentPage {
    private Connection con;

    // Get Appointments by Time, Date and Patient Name
    public ArrayList<Appointment> getAppointmentsbyDoctor(Date appointmentDate, Number appointmentTime, String firstName, String lastName){
        ResultSet rs;
        PreparedStatement ps;
        ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();

        try {
            ps = con.prepareStatement("SELECT  a.AppointmentDate, a.AppointmentTime, a.RoomNumber, a.Reason, p.FirstName, p.LastName FROM Appointments a,Patients p  WHERE " + "p.FirstName = ? AND p.LastName = ? AND a.AppointmentDate = ? AND a.AppointmentTime = ? AND p.PatientID = a.FK_PatientID");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setDate(3, (java.sql.Date) appointmentDate);
            ps.setInt(4, (Integer) appointmentTime);

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
    public void addAppointment(Date appointmentDate, Number appointmentTime,int roomNumber, String reason, String firstName, String lastName){
        PreparedStatement ps;
        ResultSet rs;

        try {

            ps = con.prepareStatement("SELECT * FROM Patients p WHERE " + "p.FirstName = ? AND p.LastName = ?");
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            rs = ps.executeQuery();
            int fk_patientID = rs.getInt("FK_PatientID");
            ps.close();


            Random random = new Random();
            int appointmentID = random.nextInt();
            String SQL = "INSERT INTO Appointments VALUES (?,?,?,?,?,?,?)";

            ps = con.prepareStatement(SQL);
            ps.setInt(1, appointmentID);
            ps.setDate(2, (java.sql.Date) appointmentDate);
            ps.setInt(3, (Integer) appointmentTime);
            ps.setInt(4,roomNumber);
            ps.setString(5, reason);
            ps.setInt(6, fk_patientID);
            ps.setInt(7, 1); //TODO: this should User that is logged on, should be available globally but not right now
            ps.executeUpdate();
            ps.close();

        }
        catch (SQLException ex){
            System.out.println("Message: "+ex.getMessage());

        }
    }

    //Delete Appointment from Database by Time, Date and Patient Name
    // could Swap for Inner Join
    public void deleteAppointment(Date appointmentDate, Number appointmentTime, String firstName, String lastName){
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = "DELETE FROM Appointments  WHERE " + "AppointmentDate = ? AND AppointmentTime = ? AND FK_PatientID IN (select PatientID from Patients where FirstName = ? AND LastName = ?) ";
            ps = con.prepareStatement(sql);

            ps.setDate(1, (java.sql.Date) appointmentDate);
            ps.setInt(2,(Integer) appointmentTime );
            ps.setString(3,firstName);
            ps.setString(4, lastName);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException ex){
            System.out.println("Message: "+ex.getMessage());
        }
    }






}
