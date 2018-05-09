/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

/**
 *
 * @author tooth
 */
public class Measurement_DTO {

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
            int id_sensor_ref, 
            int data,
            Timestamp created_timestamp
                                     ) throws SQLException, ClassNotFoundException //get question from database
    {
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
        // the mysql insert statement, adding a person into person table
        String query = "INSERT INTO measurement (ID_SENSOR_REF,DATA,DATA_DATE_CREATED) VALUES (?,?,?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, id_sensor_ref);
        preparedStmt.setInt(2, data);
        preparedStmt.setTimestamp(3, created_timestamp);

        // execute the preparedstatement
        preparedStmt.execute();

        //close connection
        conn.close();

        //never displaed
        return "measurement_created";
    }
    
    public static List<String> pull_all_data(int sensor_id_ref) throws SQLException, ClassNotFoundException
    {
        ArrayList<String> tmp = new ArrayList<>();
        
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        String query = "SELECT * FROM measurement WHERE ID_SENSOR_REF = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, sensor_id_ref);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            tmp.add(rs.getString("DATA"));
        }
        return tmp;
    }
        public static String measurement_Delete_measurement(int measurement_ID) throws SQLException, ClassNotFoundException
    { 
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        String query = "DELETE FROM measurement WHERE ID_MEASUREMENT = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, measurement_ID);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
        }
        return "measurement deleted";
    }
}