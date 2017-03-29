import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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

public class UserSearchFrame extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JTable resultTable;
	private JMenuBar menuBar;
	private JMenuItem mntmBack;
	private JButton btnSearch;
	
    private DefaultTableModel model;
    
    Object[] columnNames = {"Results"};

	
	public void setResults(ArrayList<String> results){
		DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
		int numRows = model.getRowCount();
		for(int i = 0; i < numRows; i++){
			model.removeRow(0);
		}
		model.addRow(new Object[]{"Results"});

		for(String result : results){
			model.addRow(new Object[]{result});
		}
		System.out.println("Rowcount is now: "+model.getRowCount());
		resultTable.setModel(model);
		
	}


	public void setBackListener(ActionListener al){
		mntmBack.addActionListener(al);
	}
	
	public void setSearchListener(ActionListener al){
		usernameField.addActionListener(al);
		btnSearch.addActionListener(al);
	}
	
	public String getUsername(){
		return usernameField.getText();
	}
	/**
	 * Create the frame.
	 */
	public UserSearchFrame() {
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
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel usernameLabel = new JLabel("Enter a username:");
		GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
		gbc_usernameLabel.anchor = GridBagConstraints.EAST;
		gbc_usernameLabel.insets = new Insets(10, 10, 10, 10);
		gbc_usernameLabel.gridx = 0;
		gbc_usernameLabel.gridy = 0;
		contentPane.add(usernameLabel, gbc_usernameLabel);
		
		usernameField = new JTextField();
		GridBagConstraints gbc_usernameField = new GridBagConstraints();
		gbc_usernameField.insets = new Insets(10, 10, 10, 10);
		gbc_usernameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameField.gridx = 1;
		gbc_usernameField.gridy = 0;
		contentPane.add(usernameField, gbc_usernameField);
		usernameField.setColumns(10);
		
		btnSearch = new JButton("Search");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 10, 0, 10);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 1;
		contentPane.add(btnSearch, gbc_btnSearch);

		
		model = new DefaultTableModel(columnNames, 0);
		model.addRow(new Object[]{"Results"});
		resultTable = new JTable(model);

		GridBagConstraints gbc_resultTable = new GridBagConstraints();
		gbc_resultTable.insets = new Insets(10, 10, 10, 10);
		gbc_resultTable.fill = GridBagConstraints.BOTH;
		gbc_resultTable.gridx = 1;
		gbc_resultTable.gridy = 2;
		contentPane.add(resultTable, gbc_resultTable);
	}

}
