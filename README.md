# ClinicManagementSystem
CS304 Project Team 2

Connect to oracle using <br />
Username: ora_u7b8@ug <br />
Password: a34334110 <br />

Add the two zip files in your Eclipse project under Project Properties > Java Build Path > Libraries > Add External JARS <br />
Before running the project, set up an ssh tunnel using preferred SSH tool using: <br/>
ssh -l \<your cs Id\> -L localhost:1522:dbhost.ugrad.cs.ubc.ca:1522 remote.ugrad.cs.ubc.ca


1. (1 point) Selection and projection query: pick one query of this category and provide an interface for the user to specify the selection condition and the attributes to be returned.  <br />
2. (1 point) Join query: pick one query of this category and provide an interface for the user to choose this query. <br />
3. (1 point) Division query: pick one query of this category and provide an interface for the user to choose this query. <br />
4. (1 point) Aggregation query: pick one query that requires the use of aggregation (min, max, average, or count are all fine). <br />
5. (2 points) Nested aggregation with group-by: pick one query that finds the average for each group and then finds either the minimum or maximum across all those averages. Provide an interface for the user to specify whether the minimum or maximum is requested. <br />
6. (1 point) Delete operation: implement a cascade-on-delete situation. Provide an interface for the user to specify some input for the deletion operation.  Some input values would fail the cascade specification but others would successfully follow the cascade specification. Provide an interface for the user to display the relation instance after the operation. <br />
7. (1 point) Update operation: implement a constraint using the check statement. Provide an interface for the user to specify some input for the update operation. Some input values would successfully satisfy a constraint while others would fail. Provide an interface for the user to display the relation relation after the operation.Note that MySQL 5.5 does not support the Check constraint. If your team is using MySQL 5.5, keep the check in the SQL statement, but actually do the check at the application level (and comment it in the code). <br />
8. (2 points)  Graphical user interface: create an easy to use GUI  that allows the users to execute all the above operations and transactions. Use the menu facility provided by the graphics environment you use to define appropriate menus and submenus. Make sure to design your interface in such a way that all the error messages appear in separate pop-up boxes, or in a designated area of the main window, so that they do not interfere with the other activities. Whenever user input is requested, make sure that there is sufficient type checking. <br />
9. (3 points) Extra features: Create unique features that your application supports. You can really be creative about this. Some examples are using Bootstrap to prettify your UI, implementing Triggers and Privileges in the database, using any cloud-based databases. See the marking scheme for more examples and the breakdown. <br />
