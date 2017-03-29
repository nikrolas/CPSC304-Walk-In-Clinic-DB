
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

import java.awt.*;
import java.awt.event.*;

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
<<<<<<< HEAD
    	
    	//set the user type 
    	//userType = ...
    	
    	return authenticatedUser;
    }




	/*
     * event handler for login window
     */ 
    public void actionPerformed(ActionEvent e) 
    {
	if ( authenticate(usernameField.getText(), String.valueOf(passwordField.getPassword())) )
	{
	  // if the username and password are valid, 
	  // remove the login window and display a text menu 
	  login.dispose();
      showMenu();     
	}
	else
	{
	  loginAttempts++;
	  
	  if (loginAttempts >= 3)
	  {
	      login.dispose();
	      System.exit(-1);
	  }
	  else
	  {
	      // clear the password
	      passwordField.setText("");
	  }
	}             
                    
    }


    /*
     * displays simple text interface
     */ 
    private void showLogin()
    {
		JLabel usernameLabel = new JLabel("Enter username: ");
	    JLabel passwordLabel = new JLabel("Enter password: ");

	    usernameField = new JTextField(10);
	    passwordField = new JPasswordField(10);
	    passwordField.setEchoChar('*');
	    
	    JButton loginButton = new JButton("Log In");

		login = new JFrame("Login");
		
	    JPanel contentPane = new JPanel();
	    login.setContentPane(contentPane);
	
	
	    // layout components using the GridBag layout manager
	
	    GridBagLayout gb = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();
	
	    contentPane.setLayout(gb);
	    contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	    // place the username label 
	    c.gridwidth = GridBagConstraints.RELATIVE;
	    c.insets = new Insets(10, 10, 5, 0);
	    gb.setConstraints(usernameLabel, c);
	    contentPane.add(usernameLabel);

		// place the text field for the username 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(usernameField, c);
		contentPane.add(usernameField);
		
		// place password label
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(0, 10, 10, 0);
		gb.setConstraints(passwordLabel, c);
		contentPane.add(passwordLabel);
		
		// place the password field 
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 0, 10, 10);
		gb.setConstraints(passwordField, c);
		contentPane.add(passwordField);
		
		// place the login button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(loginButton, c);
		contentPane.add(loginButton);

	    // register password field and OK button with action event handler
	    passwordField.addActionListener(this);
	    loginButton.addActionListener(this);
	    
	    // anonymous inner class for closing the window
	    login.addWindowListener(new WindowAdapter() 
	    {
			public void windowClosing(WindowEvent e) 
			{ 
			  System.exit(0); 
			}
	    });
	    //End of menu stuff
	
	    // size the window to obtain a best fit for the components
	    login.pack();
	
	    // center the frame
	    Dimension d = login.getToolkit().getScreenSize();
	    Rectangle r = login.getBounds();
	    login.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
	
	    // make the window visible
	    login.setVisible(true);
	    
	    // place the cursor in the text field for the username
	    usernameField.requestFocus();		
		
    }
    /*
     * Show the main menu depending on the usertype
     */
    private void showMenu(){
    	//Define the menu frame
		menu = new JFrame("Clinic Management System");
		
		
		//Make the menu visible
	    menu.setVisible(true);

    }


    /*
     * inserts a branch
     */ 
    private void insertBranch()
    {
	int                bid;
	String             bname;
	String             baddr;
	String             bcity;
	int                bphone;
	PreparedStatement  ps;
	  
	try
	{
	  ps = con.prepareStatement("INSERT INTO branch VALUES (?,?,?,?,?)");
	
	  System.out.print("\nBranch ID: ");
	  bid = Integer.parseInt(in.readLine());
	  ps.setInt(1, bid);

	  System.out.print("\nBranch Name: ");
	  bname = in.readLine();
	  ps.setString(2, bname);

	  System.out.print("\nBranch Address: ");
	  baddr = in.readLine();
	  
	  if (baddr.length() == 0)
          {
	      ps.setString(3, null);
	  }
	  else
	  {
	      ps.setString(3, baddr);
	  }
	 
	  System.out.print("\nBranch City: ");
	  bcity = in.readLine();
	  ps.setString(4, bcity);

	  System.out.print("\nBranch Phone: ");
	  String phoneTemp = in.readLine();
	  if (phoneTemp.length() == 0)
	  {
	      ps.setNull(5, java.sql.Types.INTEGER);
	  }
	  else
	  {
	      bphone = Integer.parseInt(phoneTemp);
	      ps.setInt(5, bphone);
	  }

	  ps.executeUpdate();

	  // commit work 
	  con.commit();

	  ps.close();
	}
	catch (IOException e)
	{
	    System.out.println("IOException!");
	}
	catch (SQLException ex)
	{
	    System.out.println("Message: " + ex.getMessage());
	    try 
	    {
		// undo the insert
		con.rollback();	
	    }
	    catch (SQLException ex2)
	    {
		System.out.println("Message: " + ex2.getMessage());
		System.exit(-1);
	    }
	}
    }


    /*
     * deletes a branch
     */ 
    private void deleteBranch()
    {
	int                bid;
	PreparedStatement  ps;
	  
	try
	{
	  ps = con.prepareStatement("DELETE FROM branch WHERE branch_id = ?");
	
	  System.out.print("\nBranch ID: ");
	  bid = Integer.parseInt(in.readLine());
	  ps.setInt(1, bid);

	  int rowCount = ps.executeUpdate();

	  if (rowCount == 0)
	  {
	      System.out.println("\nBranch " + bid + " does not exist!");
	  }

	  con.commit();

	  ps.close();
	}
	catch (IOException e)
	{
	    System.out.println("IOException!");
	}
	catch (SQLException ex)
	{
	    System.out.println("Message: " + ex.getMessage());

            try 
	    {
		con.rollback();	
	    }
	    catch (SQLException ex2)
	    {
		System.out.println("Message: " + ex2.getMessage());
		System.exit(-1);
	    }
	}
=======
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
>>>>>>> 08b6d6d0fa6ca13f7db9aa60cdc2e20b25ca829a
    }
    
    public static void main(String args[]){
    	ClinicManagementSystem b = new ClinicManagementSystem();
    }

}

