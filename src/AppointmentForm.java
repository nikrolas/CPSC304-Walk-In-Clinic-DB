import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nikrolas on 2017-03-28.
 */
public class AppointmentForm extends JFrame {
    private JTextField fnameField;
    private JTextField lnameField;
    private JTextField dateField;
    private JTextField timeField;
    private JTable appointmentTable;
    private JButton mntmBack;
    private JPanel panelMain;
    private JButton add;
    private JButton delete;
    private DefaultTableModel model;
    private ArrayList<String> foundAppointments;
    private String fname;
    Object[] columnNames = {"First Name", "Last Name", "Date", "Time", "Room Number", "Reason"};


    public void setBackListener(ActionListener al){
        mntmBack.addActionListener(al);
    }
    public void setTable(Connection con){
        System.out.print("filling up table..");
        PreparedStatement ps;
        DefaultTableModel model = new DefaultTableModel(0,6);
        model.addRow(columnNames);

        try {
            ps = con.prepareStatement("select firstname, lastname, appointmentdate, appointmenttime , roomnumber, reason from appointments a, patients p where p.patientid = a.fk_patientid");
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

    public AppointmentForm() {
        setContentPane(panelMain);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        fnameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print(fnameField.getText());

            }
        });
        lnameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print(lnameField.getText());
            }
        });
        dateField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print(dateField.getText());

            }
        });
        timeField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print(timeField.getText());
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Add button pressed..");
                System.out.print(fnameField.getText());
                System.out.print(lnameField.getText());
                System.out.print(dateField.getText());
                System.out.print(timeField.getText());
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Delete button pressed...");
                System.out.print(fnameField.getText());
                System.out.print(lnameField.getText());
                System.out.print(dateField.getText());
                System.out.print(timeField.getText());
            }
        });
        setVisible(true);

    }
}