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
	private JTextField firstNameField;
	private JTable resultTable;
	private JMenuBar menuBar;
	private JMenuItem mntmBack;
	private JButton btnSearch;
	
    private DefaultTableModel model;
    
    Object[] columnNames = {"Results"};
    private JTextField lastNameField;
    private JLabel lastNameLabel;

	
	public void setResults(ArrayList<Patient> patients){
		DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
		int numRows = model.getRowCount();
		for(int i = 0; i < numRows; i++){
			model.removeRow(0);
		}
		model.addRow(new Object[]{"Results"});

		for(Patient patient : patients){
			String row = patient.getLastName()+", "+patient.getFirstName()+" "+patient.getGender();
			model.addRow(new Object[]{row});
		}
		System.out.println("Rowcount is now: "+model.getRowCount());
		resultTable.setModel(model);
		
	}


	public void setBackListener(ActionListener al){
		mntmBack.addActionListener(al);
	}
	
	public void setSearchListener(ActionListener al){
		firstNameField.addActionListener(al);
		btnSearch.addActionListener(al);
	}
	
	public String getFirstName(){
		System.out.println("firstname: "+firstNameField.getText());
		return firstNameField.getText();
	}
	
	public String getLastName(){
		System.out.println("lastname: "+lastNameField.getText());
		return lastNameField.getText();
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
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel firstNameLabel = new JLabel("Enter patient first name:");
		GridBagConstraints gbc_firstNameLabel = new GridBagConstraints();
		gbc_firstNameLabel.anchor = GridBagConstraints.EAST;
		gbc_firstNameLabel.insets = new Insets(10, 10, 10, 10);
		gbc_firstNameLabel.gridx = 0;
		gbc_firstNameLabel.gridy = 0;
		contentPane.add(firstNameLabel, gbc_firstNameLabel);
		
		firstNameField = new JTextField();
		GridBagConstraints gbc_firstNameField = new GridBagConstraints();
		gbc_firstNameField.insets = new Insets(10, 10, 10, 10);
		gbc_firstNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_firstNameField.gridx = 1;
		gbc_firstNameField.gridy = 0;
		contentPane.add(firstNameField, gbc_firstNameField);
		firstNameField.setColumns(10);

		
		model = new DefaultTableModel(columnNames, 0);
		model.addRow(new Object[]{"Results"});
		
		lastNameLabel = new JLabel("Enter patient last name:");
		GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
		gbc_lastNameLabel.insets = new Insets(10, 10, 10, 10);
		gbc_lastNameLabel.anchor = GridBagConstraints.EAST;
		gbc_lastNameLabel.gridx = 0;
		gbc_lastNameLabel.gridy = 1;
		contentPane.add(lastNameLabel, gbc_lastNameLabel);
		
		lastNameField = new JTextField();
		GridBagConstraints gbc_lastNameField = new GridBagConstraints();
		gbc_lastNameField.insets = new Insets(10, 10, 10, 10);
		gbc_lastNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lastNameField.gridx = 1;
		gbc_lastNameField.gridy = 1;
		contentPane.add(lastNameField, gbc_lastNameField);
		lastNameField.setColumns(10);
		
		btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 10, 5, 10);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 2;
		contentPane.add(btnSearch, gbc_btnSearch);
		resultTable = new JTable(model);

		GridBagConstraints gbc_resultTable = new GridBagConstraints();
		gbc_resultTable.insets = new Insets(10, 10, 10, 10);
		gbc_resultTable.fill = GridBagConstraints.BOTH;
		gbc_resultTable.gridx = 1;
		gbc_resultTable.gridy = 3;
		contentPane.add(resultTable, gbc_resultTable);
	}

}
