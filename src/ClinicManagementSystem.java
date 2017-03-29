
// We need to import the java.sql package to use JDBC
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JFrame;


/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table
 */

// must have ssh active in terminal:  ssh -l l3a9 -L localhost:1522:dbhost.ugrad.cs.ubc.ca:1522 remote.ugrad.cs.ubc.ca
public class ClinicManagementSystem
{


	private Connection con;

	private LoginFrame loginFrame;



	public enum userTypes {
		DOCTOR, RECEPTIONIST, UNDEFINED
	}
	private String foundUser = "";
	private userTypes userType;

	private MenuFrame menuFrame;

	private UserSearchFrame userSearchFrame;
	private AppointmentForm appointmentForm;
	private PatientReceptionistFrame patientInfo;

	private ArrayList<String> foundUsers;






	/*
     * constructs login window and loads JDBC driver
     */
	public ClinicManagementSystem()
	{

		try
		{
			// Load the Oracle JDBC driver
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			System.exit(-1);
		}
		connect("ora_u7b8", "a34334110");
		loginFrame = new LoginFrame();
		loginFrame.setLoginListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(authenticate(loginFrame.getUsername(), loginFrame.getPassword())){
					loginFrame.dispose();
					showMenu();
				}

			}
		});
		loginFrame.pack();
		loginFrame.setVisible(true);
		foundUsers = new ArrayList<String>();
	}


	private void showMenu(){
		System.out.println("Show menu....\n");
		menuFrame = new MenuFrame();
		menuFrame.setSelectionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch(menuFrame.getSelection()){
					case(0):
						break;
					case(1):
						menuFrame.dispose();
						showUserSearchWindow();
						break;
					case(2):
						menuFrame.dispose();
						showAppointmentWindow();
						//show appointments...
						break;
					case(3):
						menuFrame.dispose();
						showPatientInfo();
						break;
					case(4):
						break;
					case(5):
						break;
					default:
						break;
				}


			}
		});
		menuFrame.pack();
		menuFrame.setVisible(true);
	}

	private void showUserSearchWindow(){
		System.out.println("Show user search window\n");
		userSearchFrame = new UserSearchFrame();
		userSearchFrame.setBackListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Back button presssed\n");
				userSearchFrame.dispose();
				showMenu();
			}
		});
		userSearchFrame.setSearchListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchUser(userSearchFrame.getUsername());
				userSearchFrame.setResults(foundUsers);
				userSearchFrame.repaint();
			}
		});
		userSearchFrame.setVisible(true);

	}

	private void showAppointmentWindow() {
		System.out.println("Show appointment window...\n");
		appointmentForm = new AppointmentForm();
		appointmentForm.setBackListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Back button presssed\n");
				appointmentForm.dispose();
				showMenu();
			}
		});
		appointmentForm.setVisible(true);
		appointmentForm.setTable(con);

	}
	private void showPatientInfo() {
		System.out.println("Show patient window...\n");
		patientInfo = new PatientReceptionistFrame();
		patientInfo.setBackListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Back button presssed\n");
				patientInfo.dispose();
				showMenu();
			}
		});
		patientInfo.setVisible(true);

	}


	private void searchUser(String username){
		System.out.println("Search for username: "+username+"\n");
		foundUsers = new ArrayList<String>();
		PreparedStatement ps;
		try{
			ps = con.prepareStatement("SELECT USERNAME FROM USERS WHERE " +
					"USERNAME like ?");
			ps.setString(1, username);
			ResultSet rs;

			rs = ps.executeQuery();
			while(rs.next()){
				System.out.println("Next result\n");
				String foundUser = rs.getString("USERNAME");
				foundUsers.add(foundUser);
			}

			System.out.println("Foundusers: "+ foundUsers);
			ps.close();

		}
		catch(SQLException ex)
		{
			System.out.println("Message: "+ex.getMessage());
		}
	}

	/*
     * connects to Oracle database named ug using user supplied username and password
     */
	private void connect(String username, String password)
	{
		//String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
		String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";
		try
		{
			con = DriverManager.getConnection(connectURL,username,password);

			System.out.println("\nConnected to Oracle!");
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}
	}

	/*
     * Authenticate the user
     */
	public boolean authenticate(String username, String password){
		System.out.println("Username: "+ username + ", Password: "+ password);
		int userID;
		String userName;
		ResultSet rs;
		PreparedStatement ps;
		try{
			ps = con.prepareStatement("SELECT * FROM USERS WHERE " +
					"USERNAME = ? AND \"PASSWORD\" = ?");
			ps.setString(1, username);
			ps.setString(2, password);

			rs = ps.executeQuery();
			rs.next();
			userID = rs.getInt("userID");
			System.out.println("UserID was: "+userID+"\n");
			userName = rs.getString(2);
			System.out.println("UserName was: "+userName+"\n");
			ps.close();

			if(userID == 0){
				System.out.println("Wrong username or password\n");
				return false;
			}

		}
		catch(SQLException ex)
		{
			System.out.println("Message: "+ex.getMessage());
			return false;
		}
		System.out.println("User authenticated. Welcome "+userName+"!\n");

		return true;
	}

	public static void main(String args[]){
		ClinicManagementSystem b = new ClinicManagementSystem();
	}

}
