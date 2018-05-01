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
public class Measure_modi_DTO {
    
    /**
     * Changes the sensor_id reference by providing a sensor_id already created in the sensor table 
     * @param sensor_id_ref
     * @return String
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String change_sensor_ref(String sensor_id_ref) throws SQLException, ClassNotFoundException
    {
        //try to connect to jdbc and create user
        try {
            // create a mysql database connection
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
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
    
    public static List<Measurement_DAO> pull_all_measurements(int sensor_id_ref) throws SQLException, ClassNotFoundException
    {
        ArrayList<Measurement_DAO> tmp = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

            String query = "SELECT * FROM measurement WHERE ID_SENSOR_REF = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, sensor_id_ref);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                Measurement_DAO tx = new Measurement_DAO();
                tx.Data_created_date = rs.getInt("DATA_DATE_CREATED");
                tx.data = rs.getString("DATA");
                tx.id_measurement = rs.getInt("ID_MEASUREMENT");
                tx.sensor_id_ref = rs.getInt("ID_SENSOR_REF");
                tmp.add(tx);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }
    
    public static List<Measurement_DAO> pull_measurements_within_dates(long timestampOne, long timestampTwo, int sensor_id_ref) throws SQLException, ClassNotFoundException
    {
        ArrayList<Measurement_DAO> tmp = new ArrayList<>();
         try {
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

            String query = "SELECT * FROM measurement WHERE DATA_DATE_CREATED >= ? AND DATA_DATE_CREATED <= ? AND ID_SENSOR_REF = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setLong(1, timestampOne);
            preparedStmt.setLong(2, timestampTwo);
            preparedStmt.setInt(3, sensor_id_ref);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                Measurement_DAO tx = new Measurement_DAO();
                tx.Data_created_date = rs.getInt("DATA_DATE_CREATED");
                tx.data = rs.getString("DATA");
                tx.id_measurement = rs.getInt("ID_MEASUREMENT");
                tx.sensor_id_ref = rs.getInt("ID_SENSOR_REF");
                tmp.add(tx);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }
    
    public static Measurement_DAO pull_measurement(int measurement_id) throws SQLException, ClassNotFoundException
    {
        Measurement_DAO tmp = new Measurement_DAO();
        
        try {
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

            String query = "SELECT * FROM measurement WHERE ID_MEASUREMENT = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, measurement_id);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                tmp.Data_created_date = rs.getInt("DATA_DATE_CREATED");
                tmp.data = rs.getString("DATA");
                tmp.id_measurement = rs.getInt("ID_MEASUREMENT");
                tmp.sensor_id_ref = rs.getInt("ID_SENSOR_REF");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        
        return tmp;
    }
        
    public static List<String> pull_data_within_dates(long timestampOne, long timestampTwo, int sensor_id_ref) throws SQLException, ClassNotFoundException
    {
        ArrayList<String> tmp = new ArrayList<>();
        try {
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

            String query = "SELECT DATA FROM measurement WHERE DATA_DATE_CREATED >= ? AND DATA_DATE_CREATED <= ? AND ID_SENSOR_REF = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setLong(1, timestampOne);
            preparedStmt.setLong(2, timestampTwo);
            preparedStmt.setInt(3, sensor_id_ref);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                tmp.add(rs.getString("DATA"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }        
        return tmp;
    }
}