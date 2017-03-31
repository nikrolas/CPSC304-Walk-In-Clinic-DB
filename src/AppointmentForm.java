import Pages.AppointmentPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nikrolas on 2017-03-28.
 */
public class AppointmentForm extends JFrame {
    private JTextField fnameField;
    private JTextField dateField;
    private JTextField timeField;
    private JTable appointmentTable;
    private JButton mntmBack;
    private JPanel panelMain;
    private JButton confirm;
    private JComboBox options;
    private JTextField roomField;
    private JTextField reasonField;
    private Connection conn; //Established connection initially with setup table so just use this variable for the connection
    private String dropdownSelected;
    private AppointmentPage appointmentpage;
    Object[] columnNames = {"First Name", "Last Name", "Date", "Time", "Room Number", "Reason"};
    //Specify Date format



    public void setBackListener(ActionListener al){
        mntmBack.addActionListener(al);
    }
    public void setTable(){
        System.out.print("filling up table..");
        PreparedStatement ps;
        DefaultTableModel model = new DefaultTableModel(0,6);
        model.addRow(columnNames);
        //Set up connection

        try {
            ps = conn.prepareStatement("select firstname, lastname, appointmentdate, appointmenttime , roomnumber, reason from appointments a, patients p where p.patientid = a.fk_patientid");
            ResultSet rs;
            rs = ps.executeQuery();
            while(rs.next()) {
                System.out.println("Next result\n");
                String fname = rs.getString(1);
                String lname = rs.getString(2);
                String date = rs.getString(3);
                String time = rs.getString(4);
                String room = rs.getString(5);
                String reason = rs.getString(6);
                model.addRow(new Object[]{fname,lname,date,time,room,reason});
            }
            System.out.println("Finished adding tables");
            ps.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }
        appointmentTable.setModel(model);

    }

    public AppointmentForm(Connection con) {
        conn = con;
        setContentPane(panelMain);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creating new appointment form
        appointmentpage = new AppointmentPage(conn);

        //Inserting into combobox
        options.insertItemAt("Add",0);
        options.insertItemAt("Delete",1);


        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Confirm pressed ...");

                if(dropdownSelected == "Add") {
                    //TODO Add query SQL
                    appointmentpage.addAppointment(dateField.getText(),Integer.parseInt(timeField.getText()),Integer.parseInt(roomField.getText()), reasonField.getText(), Integer.parseInt(fnameField.getText()));
                    System.out.print("filling up add table..");
                    PreparedStatement ps;
                    DefaultTableModel model = new DefaultTableModel(0,6);
                    model.addRow(columnNames);
                    //Set up connection

                    try {
                        ps = conn.prepareStatement("select firstname, lastname, appointmentdate, appointmenttime , roomnumber, reason from appointments a, patients p where p.patientid = a.fk_patientid");
                        ResultSet rs;
                        rs = ps.executeQuery();
                        while(rs.next()) {
                            System.out.println("Next result\n");
                            String fname = rs.getString(1);
                            String lname = rs.getString(2);
                            String date = rs.getString(3);
                            String time = rs.getString(4);
                            String room = rs.getString(5);
                            String reason = rs.getString(6);
                            model.addRow(new Object[]{fname,lname,date,time,room,reason});
                        }
                        System.out.println("Finished adding tables");
                        ps.close();
                    }
                    catch(SQLException ex)
                    {
                        System.out.println("Message: "+ex.getMessage());
                    }
                    appointmentTable.setModel(model);
                }
                else if(dropdownSelected == "Delete") {
                    //TODO Delete query SQL

                    appointmentpage.deleteAppointment(dateField.getText(),Integer.parseInt(timeField.getText()),Integer.parseInt(fnameField.getText()));

                    System.out.print("filling up Delete table..");
                    PreparedStatement ps;
                    DefaultTableModel model = new DefaultTableModel(0,6);
                    model.addRow(columnNames);
                    //Set up connection

                    try {
                        ps = conn.prepareStatement("select firstname, lastname, appointmentdate, appointmenttime , roomnumber, reason from appointments a, patients p where p.patientid = a.fk_patientid");
                        ResultSet rs;
                        rs = ps.executeQuery();
                        while(rs.next()) {
                            System.out.println("Next result\n");
                            String fname = rs.getString(1);
                            String lname = rs.getString(2);
                            String date = rs.getString(3);
                            String time = rs.getString(4);
                            String room = rs.getString(5);
                            String reason = rs.getString(6);
                            model.addRow(new Object[]{fname,lname,date,time,room,reason});
                        }
                        System.out.println("Finished adding tables");
                        ps.close();
                    }
                    catch(SQLException ex)
                    {
                        System.out.println("Message: "+ex.getMessage());
                    }
                    appointmentTable.setModel(model);                }
            }
        });
        options.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                dropdownSelected = (String) cb.getSelectedItem();
            }
        });
        setVisible(true);

    }
}