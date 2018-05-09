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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tooth
 */
public class Sensor_modi_DTO {
    /**
     * Changes the device reference id, which has to exists within the device table
     * @param device_id_ref
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Device_Ref(String device_id_ref, int id_Sensor_ref) throws SQLException, ClassNotFoundException //get question from database
    {
        //try to connect to jdbc and create user
        
            // create a mysql database connection
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
            // the mysql insert statement, adding a person into person table
            String query = "UPDATE sensor SET ID_DEVICE_REF = ? WHERE ID_SENSOR = ?";
            
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, device_id_ref);
            preparedStmt.setInt(2, id_Sensor_ref);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "device_id_ref_changed";
    }

    /**
     * Changes the date it was configured
     * @param sensortype
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Sensortype(String sensortype, int id_Sensor_ref) throws SQLException, ClassNotFoundException 
    {
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        // the mysql insert statement, adding a person into person table
        String query = "UPDATE sensor SET SENSORTYPE = ? WHERE ID_SENSOR = ?";
        
        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, sensortype);
        preparedStmt.setInt(2, id_Sensor_ref);
        // execute the preparedstatement
        preparedStmt.execute();

        //close connection
        conn.close();

        //never displaed
        return "sensortype_changed";
    }

    /**
     * Changes the pin it is registered to
     * @param pin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Pin(String pin, int id_Sensor_ref) throws SQLException, ClassNotFoundException 
    {
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
        // the mysql insert statement, adding a person into person table
        String query = "UPDATE sensor SET PIN = ? WHERE ID_SENSOR = ?";
        
        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, pin);
        preparedStmt.setInt(2, id_Sensor_ref);
        // execute the preparedstatement
        preparedStmt.execute();

        //close connection
        conn.close();

        //never displaed
        return "pin_changed";
    }

    /**
     * changes lastActive Date of the sensor - takes a date in the format: as argument.
     * @param lastActive_Date
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_LastActive(Timestamp lastActive_Date, int id_Sensor_ref) throws SQLException, ClassNotFoundException 
    {
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        // the mysql insert statement, adding a person into person table
        String query = "UPDATE sensor SET LASTACTIVE_DATE = ? WHERE ID_SENSOR = ?";
        
        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setTimestamp(1, lastActive_Date);
        preparedStmt.setInt(2, id_Sensor_ref);
        // execute the preparedstatement
        preparedStmt.execute();
        
        //close connection
        conn.close();

        //never displaed
        return "lastActive_Date_changed";
    }

    /**
     * Changes the IP associated with the sensor
     * @param updatetime_Minutes
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Updatetime(String updatetime_Minutes, int id_Sensor_ref) throws SQLException, ClassNotFoundException 
    {
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        // the mysql insert statement, adding a person into person table
        String query = "UPDATE sensor SET UPDATETIME_MINUTES = ? WHERE ID_SENSOR = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, updatetime_Minutes);
        preparedStmt.setInt(2, id_Sensor_ref);
        // execute the preparedstatement
        preparedStmt.execute();

        //close connection
        conn.close();

        //never displaed
        return "updatetime_changed";
    }

    /**
     * Changes the name of the sensor
     * @param sensor_name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Name(String sensor_name, int id_Sensor_ref) throws SQLException, ClassNotFoundException 
    {
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        // the mysql insert statement, adding a person into person table
        String query = "UPDATE sensor SET NAME = ? WHERE ID_SENSOR = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, sensor_name);
        preparedStmt.setInt(2, id_Sensor_ref);
        // execute the preparedstatement
        preparedStmt.execute();

        //close connection
        conn.close();

        //never displaed
        return "sensor_name_changed";
    }

     /**
     * pulls all related sensors to a device
     */
    public static List<Integer> sensor_Pull_Related_SensorIDs(int device_ID_ref) throws SQLException, ClassNotFoundException
    {
        ArrayList<Integer> tmp = new ArrayList<>();
        
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        String query = "SELECT ID_SENSOR FROM sensor WHERE ID_DEVICE_REF = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, device_ID_ref);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            tmp.add(rs.getInt("ID_SENSOR"));
        }
        return tmp;
    }
}