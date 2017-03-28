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
import javax.swing.JTextField;
import javax.swing.JTable;

public class UserSearchFrame extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JTable resultTable;


	/**
	 * Create the frame.
	 */
	public UserSearchFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
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
		
		resultTable = new JTable();
		DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
		model.addRow(new Object[]{"Username", "Role"});

		GridBagConstraints gbc_resultTable = new GridBagConstraints();
		gbc_resultTable.insets = new Insets(10, 10, 10, 10);
		gbc_resultTable.fill = GridBagConstraints.BOTH;
		gbc_resultTable.gridx = 1;
		gbc_resultTable.gridy = 1;
		contentPane.add(resultTable, gbc_resultTable);
	}

}
