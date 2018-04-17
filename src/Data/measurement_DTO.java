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
public class measurement_DTO {

    /**
     * Creates a measurement row in the measurement table
     * @param id_measurement
     * @param id_sensor_ref
     * @param data
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String createMeasurement(
            String id_measurement,
            String id_sensor_ref, 
            String data
                                     ) throws SQLException, ClassNotFoundException //get question from database
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
            String query = "INSERT INTO device (ID_MEASUREMENT,ID_SENSOR_REF,DATA,LAST_RECORDED_DATE) VALUES (?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, id_measurement);
            preparedStmt.setString(2, id_sensor_ref);
            preparedStmt.setString(3, data);

            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "measurement_created";
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
