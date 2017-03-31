
// We need to import the java.sql package to use JDBC
import Database.Patient;
import Database.Prescription;
import Pages.PatientPage;
import Pages.PrescriptionPage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


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

	private PatientDoctorFrame patientDoctorFrame;
	//private PatientUpdateFrame patientUpdateFrame;
	private PatientPage patientPage;
	
	private PrescriptionFrame prescriptionFrame;
	private PrescriptionPage prescriptionPage;
	
	private AppointmentForm appointmentForm;
	
	private PatientReceptionistFrame patientInfo;

	private ArrayList<Patient> foundPatients;
	
	private ArrayList<Prescription> foundPrescriptions;
	






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
						showPatientSearch();
						break;
					case(2):
						menuFrame.dispose();
						showAppointmentWindow();
						break;
					case(3):
						menuFrame.dispose();
						showPatientInfo();
						break;
					case(4):
						menuFrame.dispose();
						showPrescriptionFrame();
						break;
					case(5):
						menuFrame.dispose();
						//showPatientUpdate();
						break;
					default:
						break;
				}


			}
		});
		menuFrame.pack();
		menuFrame.setVisible(true);
	}
	
	/*
	 * Show the prescription page
	 */
	private void showPrescriptionFrame(){
		System.out.println("Show prescription window\n");
		prescriptionPage = new PrescriptionPage(con);
		
		prescriptionFrame = new PrescriptionFrame();
		prescriptionFrame.setBackListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				prescriptionFrame.dispose();
				showMenu();
			}
		});
		prescriptionFrame.setSearchListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!prescriptionFrame.getPatientID().equals("")){
					int patientID = Integer.parseInt(prescriptionFrame.getPatientID());
					int totalPrescriptions = prescriptionPage.getTotalPrescriptions(patientID);
					foundPrescriptions = prescriptionPage.getPatientPrescriptions(patientID);
					prescriptionFrame.setResults(foundPrescriptions, totalPrescriptions);
					prescriptionFrame.repaint();
				}
				else{
					//nothing
				}
			}
		});
		prescriptionFrame.setSelectionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String result = "";
				switch(prescriptionFrame.getSelection()){
				case(1):
					result = prescriptionPage.getMaxorMinMedications("max");
					break;
				case(2):
					result = prescriptionPage.getMaxorMinMedications("min");
					break;
				default:
					break;
				}
				System.out.println("Max or min was: "+ result);
				prescriptionFrame.setMaxMinPrescription(result);
				prescriptionFrame.repaint();
			}
				
			
		});
		
		prescriptionFrame.listAllPrescriptions(con);
		prescriptionFrame.setVisible(true);
	}

	/*
	 * Show the patient search/update page
	 */
	private void showPatientSearch(){
		System.out.println("Show patient window\n");
		patientPage = new PatientPage(con);
		patientDoctorFrame = new PatientDoctorFrame();
		patientDoctorFrame.setBackListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				patientDoctorFrame.dispose();
				showMenu();
			}
		});
		patientDoctorFrame.setSearchListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(patientDoctorFrame.getPatientID().matches(".*[a-zA-Z]+.*")){
					return;
				}
				else if(!patientDoctorFrame.getPatientID().equals("")){
					int patientID = Integer.parseInt(patientDoctorFrame.getPatientID());	
					
					System.out.println("Searching for a patient\n");
					foundPatients = patientPage.getPatient(patientID);
					
					
				}
				if(patientDoctorFrame.getPatientID().matches("^[a-zA-Z]+$")){
					
				}
				else{
					foundPatients = patientPage.getPatients();
				}
				patientDoctorFrame.setResults(foundPatients);
				patientDoctorFrame.repaint();
			}
		});
		
		patientDoctorFrame.setVisible(true);

	}

	/*
	 * Show the appointment window
	 */
	private void showAppointmentWindow() {
		System.out.println("Show appointment window...\n");
		appointmentForm = new AppointmentForm(con);
		appointmentForm.setBackListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Back button presssed\n");
				appointmentForm.dispose();
				showMenu();
			}
		});
		appointmentForm.setTable();
        appointmentForm.setVisible(true);


    }
	private void showPatientInfo() {
		System.out.println("Show patient window...\n");
		patientInfo = new PatientReceptionistFrame(con);
		patientInfo.setBackListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Back button presssed\n");
				patientInfo.dispose();
				showMenu();
			}
		});
		patientInfo.setTable();
		patientInfo.setVisible(true);

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
			System.exit(0);
		}
	}

	/*
     * Authenticate the user
     */
	public boolean authenticate(String username, String password){
		System.out.println("Username: "+ username + ", Password: "+ password);
		String userName;
		ResultSet rs;
		int userID;
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
