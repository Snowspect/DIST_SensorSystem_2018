/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author tooth
 */
public class Conn 
{
    static final String USER = "root"; //dan    
    static final String PASS = ""; //itElektronik2019!     
    static final String DRIVER = "com.mysql.jdbc.Driver"; //com.mysql.jdbc.Driver       
    static final String DATABASE = "jdbc:mysql://localhost:3306/question2"; //jdbc:mysql://128.76.255.24:25800/question    //jdbc:mysql://localhost:3306/question2´    
}

        public static String createDevice(String y, String x) throws SQLException, ClassNotFoundException //get question from database
        {
        //try to connect to jdbc and create user
        try
        {
            // create a mysql database connection
            Class.forName(Conn.DRIVER); //Conn is our connection file
            Connection conn = DriverManager.getConnection //Connection is a built in SQL class
                    (
                            Conn.DATABASE,
                            Conn.USER,
                            Conn.PASS
                    );
            // the mysql insert statement, adding a person into person table
            String query = "INSERT INTO device (y,x....) VALUES (?,?..)";
            
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, y);
            preparedStmt.setString(2, x);
            // execute the preparedstatement
            preparedStmt.execute();
            
            //close connection
            conn.close();

            //never displaed
            return "device_created";
        } catch (SQLException e)
        {
            System.err.println("Got an sql exception!");
            System.err.println(e.getMessage());
            return e.getMessage();
        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }