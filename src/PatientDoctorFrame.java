import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Database.Patient;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;

public class PatientDoctorFrame extends JFrame {

	private JPanel contentPane;
	private JTextField patientIDField;
	private JTable resultTable;
	private JMenuBar menuBar;
	private JMenuItem mntmBack;
	private JButton btnSearch;
	
    private DefaultTableModel model;
    
    Object[] columnNames = {"Patient ID", "Last Name", "First Name", "Gender"};

	
	public void setResults(ArrayList<Patient> patients){
		DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
		int numRows = model.getRowCount();
		for(int i = 0; i < numRows; i++){
			model.removeRow(0);
		}
		model.addRow(new Object[]{"Patient ID","Last Name","First Name","Gender"});

		for(Patient patient : patients){
			//String row = patient.getLastName()+", "+patient.getFirstName()+" "+patient.getGender();
			model.addRow(new Object[]{patient.getPatientID(),patient.getLastName(), patient.getFirstName(), patient.getGender()});
		}
		System.out.println("Rowcount is now: "+model.getRowCount());
		resultTable.setModel(model);
		
	}


	public void setBackListener(ActionListener al){
		mntmBack.addActionListener(al);
	}
	
	public void setSearchListener(ActionListener al){
		patientIDField.addActionListener(al);
		btnSearch.addActionListener(al);
	}
	
	
	public String getPatientID(){
		return patientIDField.getText();
	}
	/**
	 * Create the frame.
	 */
	public PatientDoctorFrame() {
		System.out.println("Making new user search frame\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mntmBack = new JMenuItem("Back");
		menuBar.add(mntmBack);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel firstNameLabel = new JLabel("Enter patient ID:");
		GridBagConstraints gbc_firstNameLabel = new GridBagConstraints();
		gbc_firstNameLabel.anchor = GridBagConstraints.EAST;
		gbc_firstNameLabel.insets = new Insets(10, 10, 10, 10);
		gbc_firstNameLabel.gridx = 0;
		gbc_firstNameLabel.gridy = 0;
		contentPane.add(firstNameLabel, gbc_firstNameLabel);
		
		patientIDField = new JTextField();
		GridBagConstraints gbc_patientIDField = new GridBagConstraints();
		gbc_patientIDField.insets = new Insets(10, 10, 10, 10);
		gbc_patientIDField.fill = GridBagConstraints.HORIZONTAL;
		gbc_patientIDField.gridx = 1;
		gbc_patientIDField.gridy = 0;
		contentPane.add(patientIDField, gbc_patientIDField);
		patientIDField.setColumns(10);
		
		model = new DefaultTableModel(columnNames, 0);
		model.addRow(new Object[]{"Patient ID","Last Name","First Name","Gender"});
		
		btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 10, 5, 10);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 1;
		contentPane.add(btnSearch, gbc_btnSearch);
		resultTable = new JTable(model);		
		GridBagConstraints gbc_resultTable = new GridBagConstraints();
		gbc_resultTable.gridwidth = 3;
		gbc_resultTable.insets = new Insets(10, 10, 10, 10);
		gbc_resultTable.fill = GridBagConstraints.BOTH;
		gbc_resultTable.gridx = 0;
		gbc_resultTable.gridy = 3;
		contentPane.add(resultTable, gbc_resultTable);
	}


}
