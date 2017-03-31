package Pages;

import Database.Appointment;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Hayley on 2017-03-28.
 */
public class AppointmentPage {
    private Connection con;
    private final DateFormat df = new SimpleDateFormat("dd/mm/yyyy");

    

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
    public void addAppointment(String appointmentDate, Number appointmentTime,int roomNumber, String reason, int patientID){
        PreparedStatement ps;
        ResultSet rs;

        try {
            Random random = new Random();
            int appointmentID = random.nextInt();
            java.util.Date date;
            java.sql.Date sqlDate;
            try {
                date=df.parse(appointmentDate);
                sqlDate = new java.sql.Date(date.getTime());
                
              }
              catch (Exception e) {     
                System.out.println(e.toString() + ", " + appointmentDate);
                return;
              }
            String SQL = "INSERT INTO Appointments VALUES (?,?,?,?,?,?,?)";

            ps = con.prepareStatement(SQL);
            ps.setInt(1, appointmentID);
            ps.setDate(2, sqlDate);
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
    public void deleteAppointment(String appointmentDate, Number appointmentTime, int patientID){
        PreparedStatement ps;
        ResultSet rs;

        try {
        	 java.util.Date date;
             java.sql.Date sqlDate;
             try {
                 date=df.parse(appointmentDate);
                 sqlDate = new java.sql.Date(date.getTime());
                 
               }
               catch (Exception e) {     
                 System.out.println(e.toString() + ", " + appointmentDate);
                 return;
               }
            String sql = "DELETE FROM Appointments  WHERE " + "AppointmentDate = ? AND AppointmentTime = ? AND FK_PatientID = ? ";
            ps = con.prepareStatement(sql);

            ps.setDate(1, sqlDate);
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
