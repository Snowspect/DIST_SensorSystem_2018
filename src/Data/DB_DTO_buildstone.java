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
public class DB_DTO_buildstone {

    public static String createX(String y, String x) throws SQLException, ClassNotFoundException //get question from database
    {
        //try to connect to jdbc and create user
        try {
            // create a mysql database connection
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
            // the mysql insert statement, adding a person into person table
            String query = "INSERT INTO x (y,x....) VALUES (?,?..)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, y);
            preparedStmt.setString(2, x);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "x_completed";
        } catch (SQLException e) {
            System.err.println("Got an sql exception!");
            System.err.println(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }
}


/** TODO
 * CREATE METHODS THAT PULL OUT DEVICE ID.
 * CREATE METHODS THAT SELECT DEVICE ID. (possibly same as above)?
 * 
 * CREATE METHODS SIMILAR FOR SENSOR ID
 * CREATE METHODS TO PULL DATA IN GENERAL FOR NEEDED ITEMS.
 * 
 * CREATE METHOD THAT CREATES A DATE OBJECT to be transfered to different SQL tables.
 */