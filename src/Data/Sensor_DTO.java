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
public class Sensor_DTO {

    public static String createSensor(
            String id_sensor, 
            String id_device, 
            String sensorType, 
            String pin, 
            String lastActive,
            String updateTime_minutes
                                     ) throws SQLException, ClassNotFoundException //get question from database
    {
        /**
         * 
         * hold
         */
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
            String query = "INSERT INTO device (ID_SENSOR,ID_DEVICE_REF,SENSORTYPE,PIN,LASTACTIVE_DATE,UPDATETIME_MIN) VALUES (?,?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, id_sensor);
            preparedStmt.setString(2, id_device);
            preparedStmt.setString(3, sensorType);
            preparedStmt.setString(4, pin);
            preparedStmt.setString(5, lastActive);
            preparedStmt.setString(6, updateTime_minutes);

            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "sensor_created";
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
