import Pages.PatientPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nikrolas on 2017-03-29.
 */
public class PatientReceptionistFrame extends JFrame {
    private JTable patientTable;
    private JTextField fnameField;
    private JTextField lnameField;
    private JTextField aptNumberField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField postalField;
    private JTextField provinceField;
    private JTextField phoneNumField;
    private JTextField notesField;
    private JComboBox optionDropdown;
    private JButton submitButton;
    private JButton mntmBack;
    private JPanel panelMain;
    private JTextField insuranceField;
    private JTextField patientidField;
    private JTextField genderField;
    private String dropdownSelected;
    private Connection conn;
    private PatientPage patientpage;
    private Integer apt;
    private Long number;
    Object[] columnNames = {"First Name", "Last Name", "Gender","Apt/House Number", "Street", "City", "PostalCode","Province","Phone","Insurance","Notes"};

    public void setBackListener(ActionListener al){
        mntmBack.addActionListener(al);
    }

    public void setTable(){
        System.out.print("setting up table..");
        PreparedStatement ps;
        DefaultTableModel model = new DefaultTableModel(0,11);
        model.addRow(columnNames);

        try {
            ps = conn.prepareStatement("select firstname, lastname, gender, apthousenumber, street , city, postalcode, province, phonenumber,insuranceprovidername, notes from contacts c, patients p, insuranceproviders i where p.patientid = c.fk_patientid AND p.fk_insuranceproviderid = i.insuranceproviderid");
            ResultSet rs;
            rs = ps.executeQuery();
            while(rs.next()) {
                System.out.println("Next result\n");
                String fname = rs.getString(1);
                String lname = rs.getString(2);
                String gender = rs.getString(3);
                String number = rs.getString(4);
                String street = rs.getString(5);
                String city = rs.getString(6);
                String post = rs.getString(7);
                String province = rs.getString(8);
                String phone = rs.getString(9);
                String insurance = rs.getString(10);
                String notes = rs.getString(11);
                model.addRow(new Object[]{fname,lname,gender,number,street,city,post,province,phone,insurance,notes});
            }
            System.out.println("Finished adding tables");
            ps.close();
        }

        catch(SQLException ex)
        {
            System.out.println("Message: "+ex.getMessage());
        }
        patientTable.setModel(model);

    }

    public PatientReceptionistFrame(Connection con) {
        conn = con;
        setContentPane(panelMain);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creating new appointment form
        patientpage = new PatientPage(conn);

        //Creating Dropdown
        optionDropdown.insertItemAt("Modify",0);

        //Setting up table
        DefaultTableModel model = new DefaultTableModel(0,10);
        model.addRow(columnNames);
        patientTable.setModel(model);

        optionDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                dropdownSelected = (String) cb.getSelectedItem();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("Submit pressed ...");
                System.out.print(fnameField.getText());
                System.out.print(lnameField.getText());
                System.out.print(aptNumberField.getText());
                System.out.print(streetField.getText());
                System.out.print(cityField.getText());
                System.out.print(postalField.getText());
                System.out.print(provinceField.getText());
                System.out.print(phoneNumField.getText());
                System.out.print(insuranceField.getText());
                System.out.print(notesField.getText());

                if(aptNumberField.getText().equals("") ) {
                    apt = 0;
                }
                else{
                    apt = Integer.parseInt(aptNumberField.getText());
                }
                if(phoneNumField.getText().equals("")) {
                    number = (long)0;
                }
                else{
                    number = Long.parseLong(phoneNumField.getText());
                }

                if(dropdownSelected == "Modify") {
                    //TODO Modify query SQL
                    patientpage.updatePatient(Integer.parseInt(patientidField.getText()),fnameField.getText(),lnameField.getText(), genderField.getText(),apt,streetField.getText(),cityField.getText(),postalField.getText(),provinceField.getText(), number,notesField.getText(),insuranceField.getText());
                    System.out.print("filling up Modify table..");



                    PreparedStatement ps;
                    DefaultTableModel model = new DefaultTableModel(0,11);
                    model.addRow(columnNames);

                    try {
                        ps = conn.prepareStatement("select firstname, lastname, gender, apthousenumber, street , city, postalcode, province, phonenumber,insuranceprovidername, notes from contacts c, patients p, insuranceproviders i where p.patientid = c.fk_patientid AND p.fk_insuranceproviderid = i.insuranceproviderid");
                        ResultSet rs;
                        rs = ps.executeQuery();
                        while(rs.next()) {
                            System.out.println("Next result\n");
                            String fname = rs.getString(1);
                            String lname = rs.getString(2);
                            String gender = rs.getString(3);
                            String number = rs.getString(4);
                            String street = rs.getString(5);
                            String city = rs.getString(6);
                            String post = rs.getString(7);
                            String province = rs.getString(8);
                            String phone = rs.getString(9);
                            String insurance = rs.getString(10);
                            String notes = rs.getString(11);
                            model.addRow(new Object[]{fname,lname,gender,number,street,city,post,province,phone,insurance,notes});
                        }
                        System.out.println("Finished adding tables");
                        ps.close();
                    }
                    catch(SQLException ex)
                    {
                        System.out.println("Message: "+ex.getMessage());
                    }
                    patientTable.setModel(model);
                }
            }
        });

        setVisible(true);

    }
}
