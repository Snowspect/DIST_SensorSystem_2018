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
public class Device_DTO
{
    /**
     * Creates a row in the table device with the given parameters.
     * device_id will increment itself and that id will be returned and given to user.
     * @param device_name
     * @param owner
     * @param ip
     * @param configured
     * @param configured_date --will be pulled from current date
     * @param lastActive --will be pulled from current date
     * @param created_date --will be pulled from current date
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
        public static String createDevice(
                String device_name, 
                String owner,  
                String lastActive, 
                String created_date
                                         ) throws SQLException, ClassNotFoundException //get question from database
        {
        /**
         * lastActive/created_date could be a date object, which a mySql database also can hold
         */
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
            String query = "INSERT INTO device (NAME,OWNER,LASTACTIVE_DATE,CREATEDATE) VALUES (?,?,?,?)";
            
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            PreparedStatement dw = conn.prepareStatement(query);
            preparedStmt.setString(1, device_name);
            preparedStmt.setString(2, owner);
            preparedStmt.setString(3, lastActive);
            preparedStmt.setString(4, created_date);

            // execute the preparedstatement
            preparedStmt.execute();
            
            //close connection
            conn.close();

            //never displayed
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
}