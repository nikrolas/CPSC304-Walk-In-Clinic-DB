import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Database.Prescription;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JButton;

public class PrescriptionFrame extends JFrame {

	private JPanel contentPane;
	private JMenuItem mntmBack;
	private JLabel patientIDLabel;
	private JTextField patientIDField;
	private JTable table;
	private JLabel lblTotalPrescriptions;
	
    private DefaultTableModel model;
    
    Object[] columnNames = {"Doseage", "Start Date", "End Date", "Generic OK"};
    private JLabel totalPrescriptions;
    private JTextField totalPrescriptionField;
    private JButton btnSearch;

    public void setTotalPrescriptions(int totalPrescriptions){
    	totalPrescriptionField.setText(Integer.toString(totalPrescriptions));
    }

    
    public void setResults(ArrayList<Prescription> prescriptions, int totalPrescriptions){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int numRows = model.getRowCount();
		for(int i = 0; i < numRows; i++){
			model.removeRow(0);
		}
		model.addRow(new Object[]{"Doseage", "Start Date", "End Date", "Generic OK"});

		for(Prescription prescription : prescriptions){
			model.addRow(new Object[]{prescription.getDoseage(), prescription.getMedStartDate(), prescription.getMedEndDate(), prescription.getGenericOK()});
		}
		System.out.println("Rowcount is now: "+model.getRowCount());
		table.setModel(model);
		totalPrescriptionField.setText( Integer.toString( totalPrescriptions ) );
		
	}

	public void setBackListener(ActionListener al){
		mntmBack.addActionListener(al);
	}
	
	public void setSearchListener(ActionListener al){
		btnSearch.addActionListener(al);
		patientIDField.addActionListener(al);
	}
	
	public String getPatientID(){
		return patientIDField.getText();
	}
	
	
	
	
	/**
	 * Create the frame.
	 */
	public PrescriptionFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mntmBack = new JMenuItem("Back");
		menuBar.add(mntmBack);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		patientIDLabel = new JLabel("Enter Patient ID:");
		GridBagConstraints gbc_patientIDLabel = new GridBagConstraints();
		gbc_patientIDLabel.anchor = GridBagConstraints.EAST;
		gbc_patientIDLabel.insets = new Insets(10, 10, 10, 10);
		gbc_patientIDLabel.gridx = 0;
		gbc_patientIDLabel.gridy = 0;
		contentPane.add(patientIDLabel, gbc_patientIDLabel);
		
		patientIDField = new JTextField();
		GridBagConstraints gbc_patientIDField = new GridBagConstraints();
		gbc_patientIDField.insets = new Insets(10, 10, 10, 10);
		gbc_patientIDField.fill = GridBagConstraints.HORIZONTAL;
		gbc_patientIDField.gridx = 1;
		gbc_patientIDField.gridy = 0;
		contentPane.add(patientIDField, gbc_patientIDField);
		patientIDField.setColumns(10);
		
		model = new DefaultTableModel(columnNames, 0);
		model.addRow(new Object[]{"Doseage", "Start Date", "End Date", "Generic OK"});
		
		lblTotalPrescriptions = new JLabel("Total Prescriptions");
		GridBagConstraints gbc_lblTotalPrescriptions = new GridBagConstraints();
		gbc_lblTotalPrescriptions.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalPrescriptions.gridx = 0;
		gbc_lblTotalPrescriptions.gridy = 2;
		contentPane.add(lblTotalPrescriptions, gbc_lblTotalPrescriptions);
		
		btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 0);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 2;
		contentPane.add(btnSearch, gbc_btnSearch);
		
		totalPrescriptions = new JLabel("");
		GridBagConstraints gbc_totalPrescriptions = new GridBagConstraints();
		gbc_totalPrescriptions.insets = new Insets(0, 0, 5, 0);
		gbc_totalPrescriptions.gridx = 1;
		gbc_totalPrescriptions.gridy = 3;
		contentPane.add(totalPrescriptions, gbc_totalPrescriptions);
		
		totalPrescriptionField = new JTextField();
		totalPrescriptionField.setEditable(false);
		totalPrescriptionField.setText("0");
		GridBagConstraints gbc_totalPrescriptionField = new GridBagConstraints();
		gbc_totalPrescriptionField.anchor = GridBagConstraints.NORTH;
		gbc_totalPrescriptionField.insets = new Insets(0, 0, 0, 5);
		gbc_totalPrescriptionField.fill = GridBagConstraints.HORIZONTAL;
		gbc_totalPrescriptionField.gridx = 0;
		gbc_totalPrescriptionField.gridy = 4;
		contentPane.add(totalPrescriptionField, gbc_totalPrescriptionField);
		totalPrescriptionField.setColumns(10);
		table = new JTable(model);		
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 1;
		gbc_table.gridy = 4;
		contentPane.add(table, gbc_table);
	}

}
