/* Michael Bruns | CS 4620 | Homework 2
* 
* 
* JDBC program that uses SQL and MS Access
* to create an output that prints out the pets
* underneat its' owner
* 
*/

package msjavaaccessdb;

import java.sql.*;
import java.util.*;

public class MSjavaAccessDB {
    
    /** Creates a new instance of databaseApplication */
    public MSjavaAccessDB() {
    }
    
    static String nameOfJdbcOdbcDriver =
                "sun.jdbc.odbc.JdbcOdbcDriver";
    
    static int oldID;
    static String dataBaseNameDSN = "jdbc:odbc:myDataSource";
    static String userName = "";
    static String passwordForUser = "";

    static Connection myConnectionRequest = null;
    static Statement myStatementObject = null;
    static ResultSet myResultTuples = null;
    static ResultSetMetaData myResultTuplesMetaData = null;

    // SQL statement to be executed 
    static String queryToBeExecuted = "select * from Owner O, Pet P "
            + "where O.OwnerID = P.Owner_ID order by O.OwnerID";

    public static void main(String args[])
                        throws ClassNotFoundException  {

      try {

        //Identify the driver to use
        Class.forName(nameOfJdbcOdbcDriver);

        //Attempt a connection to database...
        Connection myConnectionRequest =
                DriverManager.getConnection(
                        dataBaseNameDSN, userName, passwordForUser);

        //Create a statement object, use its method to execute query
        Statement  myStatementObject =
                myConnectionRequest.createStatement();

        //Use statement object method to execute a query.
        //Hold results in a resutl set...like a cursor
        ResultSet myResultTuples = myStatementObject.executeQuery
                                        (queryToBeExecuted);                 

        //Call metadata to get the number of attributes
        myResultTuplesMetaData = myResultTuples.getMetaData();
        int numberOfAttributes =
                myResultTuplesMetaData.getColumnCount();
        System.out.println(Integer.toString(numberOfAttributes));

        //Prints each owner and has a sub-list of all their pets       
        while(myResultTuples.next()){
                
            //Owner table data
            int ownerID = myResultTuples.getInt("OwnerID");
            String firstName = myResultTuples.getString("FirstName");
            String lastName = myResultTuples.getString("LastName");
            String phoneNum = myResultTuples.getString("PhoneNumber");
            String state = myResultTuples.getString("State");   
            
            //Pet table data
            int petID = myResultTuples.getInt("PetID");
            String petName = myResultTuples.getString("Name");
            String speice = myResultTuples.getString("Speice");
            String gender = myResultTuples.getString("Gender");
            String dob = myResultTuples.getString("DOB");
            int owner_ID = myResultTuples.getInt("Owner_ID");
            
            
            if(ownerID != oldID){  //Print owner info and their first pet
                
                oldID = ownerID; 
                
                System.out.println("ID " + ownerID + " " + "Name " 
                        + firstName + " " + lastName + " " 
                        + " Phone " + phoneNum + " State " + state);               
                    
                System.out.println("\t PET ID " + petID + " " + "Name " 
                    + petName + " " + " Speice " + speice + " Gender " + gender
                        + " DOB " + dob);                                                             
            }
            else{ // Print any extra pets an owner has
                System.out.println("\t PET ID " + petID + " " + "Name " 
                    + petName + " " + " Speice " + speice + " Gender " + gender
                        + " DOB " + dob);  
            }
                                          
        } // end while

      }  // end of try block

      //handle exceptions 
      catch (SQLException sqlError) {
        System.out.println("Unexpected exception : " +
                sqlError.toString() + ", sqlstate = " +
                sqlError.getSQLState());
        sqlError.printStackTrace();
      }

    }  // end of main method

}  // end of the class
