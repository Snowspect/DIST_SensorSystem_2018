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
            String data,
            long created_timestamp
                                     ) throws SQLException, ClassNotFoundException //get question from database
    {
        //try to connect to jdbc and create user
        try {
            // create a mysql database connection
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
            // the mysql insert statement, adding a person into person table
            String query = "INSERT INTO device (ID_MEASUREMENT,ID_SENSOR_REF,DATA) VALUES (?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id_sensor_ref);
            preparedStmt.setString(2, data);
            preparedStmt.setLong(3, created_timestamp);

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
    
    public static List<String> pull_all_data(int sensor_id_ref) throws SQLException, ClassNotFoundException
    {
        ArrayList<String> tmp = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

            String query = "SELECT * FROM measurement WHERE ID_SENSOR_REF = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, sensor_id_ref);

            ResultSet rs = preparedStmt.executeQuery();
            
            /**
             * if the current implementation doesn't work, switch to:
             * ResultSetMetaData metadata = rs.getMetaData();
             * int columnCount = metadata.getColumnCont();
             * 
             * while(rs.next())
             * {
             * String row = "";
             * for (int i = 1; i <= columnCount; i++)
             * {
             * row += rs.getString(i) + ", ";
             * }
             * tmp.add(row);
             * }
             */
            
            while (rs.next()) {
                tmp.add(rs.getString("DATA"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }
}
