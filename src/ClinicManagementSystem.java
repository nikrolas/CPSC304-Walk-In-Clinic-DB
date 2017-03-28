
// We need to import the java.sql package to use JDBC
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JFrame;


/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table 
 */ 

// must have ssh active in terminal:  ssh -l <UNIX id> -L localhost:1522:dbhost.ugrad.cs.ubc.ca:1522 remote.ugrad.cs.ubc.ca
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
					case(1):
						//showUserSearchWindow()..
						break;
					case(2):
						//show medications... 
						break;
					case(3):
						//join medication and patients...
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

   
    
    private void getUsers(String username){
    	System.out.println("Username: "+ username + "\n");
    	String user = "";
    	ResultSet rs;
    	PreparedStatement ps; 
    	try{
    		ps = con.prepareStatement("SELECT USERNAME FROM USERS WHERE " +
     			   "USERNAME like ?");
    		ps.setString(1, username);
    		
    		rs = ps.executeQuery();
    		rs.next();
    		user = rs.getString("USERNAME");
    		if(user != null){
    			System.out.println("Set user string\n");
    			foundUser = user;
    		}
    		else{
    			foundUser = "No matching users";
    		}
    		System.out.println("UserName was: "+user+"\n");
        	ps.close();
        	  
    	}
    	catch(SQLException ex)
    	{
    		System.out.println("Message: "+ex.getMessage());
    		
    	}
		System.out.println("User found: "+user+"!\n");
    }
    
    public static void main(String args[]){
    	ClinicManagementSystem b = new ClinicManagementSystem();
    }

}

