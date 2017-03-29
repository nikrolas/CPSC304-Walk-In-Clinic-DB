
// We need to import the java.sql package to use JDBC
import java.sql.*;

// for reading from the command line
import java.io.*;

// for the login window
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/*
 * This class implements a graphical login window and a simple text
 * interface for interacting with the branch table
 */

// must have ssh active in terminal:  ssh -l <UNIX id> -L localhost:1522:dbhost.ugrad.cs.ubc.ca:1522 remote.ugrad.cs.ubc.ca
public class ClinicManagementSystem implements ActionListener
{
	// command line reader
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private Connection con;

	// user is allowed 3 login attempts
	private int loginAttempts = 0;

	// components of the login window
	private JFrame login;
	private JTextField usernameField;
	private JPasswordField passwordField;

	private JFrame menu;


	public enum userTypes {
		DOCTOR, RECEPTIONIST, UNDEFINED
	}

	private userTypes userType;


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

		if ( connect("ora_u7b8", "a34334110") )
		{
			// if connection is succesfull show the login screen
			// remove the login window and display a text menu
			showLogin();
		}
	}


	/*
     * connects to Oracle database named ug using user supplied username and password
     */
	private boolean connect(String username, String password)
	{
		//String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug";
		String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";
		try
		{
			con = DriverManager.getConnection(connectURL,username,password);

			System.out.println("\nConnected to Oracle!");
			return true;
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
			return false;
		}
	}

	/*
     * Authenticate the user
     */
	private boolean authenticate(String username, String password){
		System.out.println("Username: "+ username + ", Password: "+ password);
		int userID;
		String userName;
		ResultSet rs;
		PreparedStatement ps;
		try{
			ps = con.prepareStatement("SELECT * FROM USERS u WHERE " +
					"u.USERNAME = ? AND u.PASSWORD = ?");
			ps.setString(1,username);
			ps.setString(2,password);

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
			//TODO: Once roleIDs are added, set the user type.
			/*switch(roleID){
				case 0:
					return false;
				case 1:
					userType = userTypes.RECEPTIONIST;
					break;
				case 2:
					userType = userTypes.DOCTOR;
					break;
				default:
					userType = userTypes.UNDEFINED;
					return false;
			}*/

		}
		catch(SQLException ex)
		{
			System.out.println("Message: "+ex.getMessage());
			return false;
		}
		System.out.println("User authenticated. Welcome "+userName+"!\n");

		return true;
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

		JLabel dateLabel = new JLabel("Enter date to search appointments: ");
		JTextField dateField = new JTextField(10);
		dateField.setText("dd/mm/yyyy");
		JButton searchButton = new JButton("Search");


		//Define the menu frame
		menu = new JFrame("Clinic Management System");

		JPanel contentPane = new JPanel();
		menu.setContentPane(contentPane);

		// layout components using the GridBag layout manager

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		contentPane.setLayout(gb);
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// place the date label
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(10, 10, 5, 0);
		gb.setConstraints(dateLabel, c);
		contentPane.add(dateLabel);

		// place the text field for the date
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 0, 5, 10);
		gb.setConstraints(dateField, c);
		contentPane.add(dateField);

		// place the login button
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(5, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		gb.setConstraints(searchButton, c);
		contentPane.add(searchButton);

		// center the frame
		Dimension d = menu.getToolkit().getScreenSize();
		Rectangle r = menu.getBounds();
		menu.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );


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
		menu.pack();

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
	}


	/*
     * updates the name of a branch
     */
	private void updateBranch()
	{
		int                bid;
		String             bname;
		PreparedStatement  ps;

		try
		{
			ps = con.prepareStatement("UPDATE branch SET branch_name = ? WHERE branch_id = ?");

			System.out.print("\nBranch ID: ");
			bid = Integer.parseInt(in.readLine());
			ps.setInt(2, bid);

			System.out.print("\nBranch Name: ");
			bname = in.readLine();
			ps.setString(1, bname);

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
	}


	/*
     * display information about branches
     */
	private void showBranch()
	{
		String     bid;
		String     bname;
		String     baddr;
		String     bcity;
		String     bphone;
		Statement  stmt;
		ResultSet  rs;

		try
		{
			stmt = con.createStatement();

			rs = stmt.executeQuery("SELECT * FROM branch");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it

				System.out.printf("%-15s", rsmd.getColumnName(i+1));
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle
				// as a string

				// simplified output formatting; truncation may occur

				bid = rs.getString("branch_id");
				System.out.printf("%-10.10s", bid);

				bname = rs.getString("branch_name");
				System.out.printf("%-20.20s", bname);

				baddr = rs.getString("branch_addr");
				if (rs.wasNull())
				{
					System.out.printf("%-20.20s", " ");
				}
				else
				{
					System.out.printf("%-20.20s", baddr);
				}

				bcity = rs.getString("branch_city");
				System.out.printf("%-15.15s", bcity);

				bphone = rs.getString("branch_phone");
				if (rs.wasNull())
				{
					System.out.printf("%-15.15s\n", " ");
				}
				else
				{
					System.out.printf("%-15.15s\n", bphone);
				}
			}

			// close the statement;
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}
	}


	public static void main(String args[])
	{
		ClinicManagementSystem b = new ClinicManagementSystem();
	}
}
