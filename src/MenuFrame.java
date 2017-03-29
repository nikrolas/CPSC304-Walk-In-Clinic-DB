import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame {

	private JPanel contentPane;
	
	private JComboBox comboBox;

	public void setSelectionListener(ActionListener al) {
		//TODO: add listener to combo box
		comboBox.addActionListener(al);
	}
	
	public int getSelection(){
		return comboBox.getSelectedIndex();
	}

	/**
	 * Create the frame.
	 */
	public MenuFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblSelectASection = new JLabel("Select a section");
		GridBagConstraints gbc_lblSelectASection = new GridBagConstraints();
		gbc_lblSelectASection.insets = new Insets(0, 0, 0, 5);
		gbc_lblSelectASection.anchor = GridBagConstraints.EAST;
		gbc_lblSelectASection.gridx = 1;
		gbc_lblSelectASection.gridy = 1;
		contentPane.add(lblSelectASection, gbc_lblSelectASection);
		
		String[] options = {"--------","Patient Search", "Appointments", "Patient Info", "Insurance Providers", "Etc.."};

		comboBox = new JComboBox<Object>(options);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 1;
		contentPane.add(comboBox, gbc_comboBox);
	}

}
