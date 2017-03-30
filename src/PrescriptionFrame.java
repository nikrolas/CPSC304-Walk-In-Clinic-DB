import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

public class PrescriptionFrame extends JFrame {

	private JPanel contentPane;
	JMenuItem mntmBack;
	private JLabel lblEnterFirstName;

	public void setBackListener(ActionListener al){
		mntmBack.addActionListener(al);
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
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblEnterFirstName = new JLabel("Enter First Name:");
		GridBagConstraints gbc_lblEnterFirstName = new GridBagConstraints();
		gbc_lblEnterFirstName.gridx = 0;
		gbc_lblEnterFirstName.gridy = 0;
		contentPane.add(lblEnterFirstName, gbc_lblEnterFirstName);
	}

}
