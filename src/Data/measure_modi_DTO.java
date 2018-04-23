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
public class measure_modi_DTO {
    
    /**
     * Changes the sensor_id reference by providing a sensor_id already created in the sensor table 
     * @param sensor_id_ref
     * @return String
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String Change_sensor_ref(String sensor_id_ref) throws SQLException, ClassNotFoundException
    {
        //try to connect to jdbc and create user
        try {
            // create a mysql database connection
            Class.forName(Conn.DRIVER); //Conn is our connection file
            Connection conn = DriverManager.getConnection //Connection is a built in SQL class
                    (
                            Conn.DATABASE,
                            Conn.USER,
                            Conn.PASS
                    );
            // the mysql insert statement, adding a person into person table
            String query = "INSERT INTO measurement (ID_SENSOR_REF) VALUES (?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, sensor_id_ref);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "sensor_id_ref_changed";
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
    
    public static String Pull_all_measurements(String sensor_id_ref) throws SQLException, ClassNotFoundException
    {
        //TODO implement
        
        return "";
    }
}