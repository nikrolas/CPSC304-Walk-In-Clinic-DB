package Database;

import java.util.Date;

/**
 * Created by Hayley on 2017-03-28.
 */
public class Appointment {
   // private int appointmentID;
    private Date appointmentDate;
    private Number appointmentTime;
    private int roomNumber;
    private String reason;
   // private int fk_patientID;
  //  private int fk_UserID;

   public Appointment( Date appointmentDate, Number appointmentTime, int roomNumber, String reason){
       //this.appointmentID = appointmentID;
       this.appointmentDate = appointmentDate;
       this.appointmentTime = appointmentTime;
       this.roomNumber = roomNumber;
       this.reason = reason;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Number getAppointmentTime() {
        return appointmentTime;
    }

    public String getReason() {
        return reason;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(Number appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }


}
