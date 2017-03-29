import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nikrolas on 2017-03-29.
 */
public class PatientReceptionistFrame extends JFrame {
    private JTable patientTable;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JComboBox comboBox1;
    private JButton submitButton;
    private JButton mntmBack;
    private JPanel panelMain;
    String[] options = {"Search","Add","Delete"};

    public PatientReceptionistFrame() {
        setContentPane(panelMain);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        comboBox1.addItem("Search");
        comboBox1.addItem("Add");
        comboBox1.addItem("Delete");

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        setVisible(true);

    }

    public void setBackListener(ActionListener al){
        mntmBack.addActionListener(al);
    }

}
