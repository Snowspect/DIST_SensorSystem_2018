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
public class Sensor_DTO {
    
    /**
     * Creates a sensor in the sensor table within the DB. Also the sensor id is automatically created by the DB.
     * @param name
     * @param id_device
     * @param sensorType
     * @param pin
     * @param lastActive
     * @param updateTime_minutes
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_CreateSensor( 
            String name,    
            int id_device, 
            String sensorType, 
            String pin,
            Timestamp created_date,
            Timestamp lastActive,
            String updateTime_minutes
                                     ) throws SQLException, ClassNotFoundException //get question from database
    {
        /**
         * 
         * hold
         */
        //try to connect to jdbc and create user
        
            // create a mysql database connection
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file

            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING); //
              String query = "INSERT INTO sensor (NAME,ID_DEVICE_REF,SENSORTYPE,PIN,CREATED_DATE,LASTACTIVE_DATE,UPDATETIME_MINUTES) VALUES (?,?,?,?,?,?,?)";          
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setInt(2, id_device);
            preparedStmt.setString(3, sensorType);
            preparedStmt.setString(4, pin);
            preparedStmt.setTimestamp(5, created_date);
            preparedStmt.setTimestamp(6, lastActive);
            preparedStmt.setString(7, updateTime_minutes);

            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "sensor_created";
    }
    
    /**
     * pulls entire sensor information based on sensor ID
     */
    public static List<String> sensor_Pull_Sensor(int sensor_ID) throws SQLException, ClassNotFoundException
    {
        ArrayList<String> tmp = new ArrayList<String>();

        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        String query = "SELECT * FROM sensor WHERE ID_SENSOR = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, sensor_ID);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            tmp.add(rs.getInt("ID_SENSOR") + "");
            tmp.add(rs.getString("NAME"));
            tmp.add(rs.getString("ID_DEVICE_REF"));
            tmp.add(rs.getString("SENSORTYPE"));
            tmp.add(rs.getInt("PIN") + "");
            tmp.add(rs.getString("UPDATETIME_MINUTES"));
            tmp.add(rs.getString("LASTACTIVE_DATE"));
            tmp.add(rs.getString("CREATED_DATE"));
        }
        
        return tmp;
    }
    
    /**
     * Pulls all sensors related to a specific devise.
     * @param device_ID_Ref
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String[] sensor_Pull_All_Sensors(int device_ID_Ref) throws SQLException, ClassNotFoundException
    {
        ArrayList<Sensor_DAO> tmp = new ArrayList<>();
        String[] x = null;
        
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        String query = "SELECT * FROM sensor WHERE ID_DEVICE_REF = ?";

<<<<<<< HEAD
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, device_ID_Ref);

            ResultSet rs = preparedStmt.executeQuery();
            
            while (rs.next()) {
                Sensor_DAO tx = new Sensor_DAO();
                tx.sensor_ID = rs.getInt("ID_SENSOR");
                tx.name = rs.getString("NAME");
                tx.device_Ref_ID = rs.getInt("ID_DEVICE_REF");
                tx.sensorType = rs.getString("SENSORTYPE");
                tx.pin = rs.getInt("PIN");
                tx.created_Date = rs.getString("CREATED_DATE");
                tx.lastActive_Date = rs.getString("LASTACTIVE_DATE");           
                tx.updateTime_Minutes = rs.getInt("UPDATETIME_MINUTES");
                
                tmp.add(tx);
            }
            //converts DAO array to string array
            x = new String[tmp.size()];
            int i = 0;
            for (Sensor_DAO sensor_DAO : tmp) {
                x[i] = sensor_DAO.toString();
                i++;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
=======
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, device_ID_Ref);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            Sensor_DAO tx = new Sensor_DAO();
            tx.sensor_ID = rs.getInt("ID_SENSOR");
            tx.name = rs.getString("NAME");
            tx.device_Ref_ID = rs.getInt("ID_DEVICE_REF");
            tx.sensorType = rs.getString("SENSORTYPE");
            tx.pin = rs.getInt("PIN");
            tx.created_Date = rs.getString("CREATED_DATE");
            tx.lastActive_Date = rs.getString("LASTACTIVE_DATE");                
            tx.updateTime_Minutes = rs.getInt("UPDATETIME_MINUTES");

            tmp.add(tx);
        }
        //converts DAO array to string array
        x = new String[tmp.size()];
        int i = 0;
        for (Sensor_DAO sensor_DAO : tmp) {
            x[i] = sensor_DAO.toString();
            i++;
>>>>>>> dfb2d287e2a7a66f4291d9d0d04f351a708e72de
        }
        return x;
    }
}