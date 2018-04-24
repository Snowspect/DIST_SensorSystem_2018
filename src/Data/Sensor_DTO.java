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
    public static String createSensor( 
            String name,    
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
            String query = "INSERT INTO device (NAME,ID_DEVICE_REF,SENSORTYPE,PIN,LASTACTIVE_DATE,UPDATETIME_MIN) VALUES (?,?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
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
         /**
     * pulls entire sensor information based on sensor ID
     */
    public static List<String> pull_sensor(int sensor_ID) throws SQLException, ClassNotFoundException
    {
        ArrayList<String> tmp = new ArrayList<String>();
        try {
            Class.forName(Conn.DRIVER);
            Connection conn = DriverManager.getConnection(
                    Conn.DATABASE,
                    Conn.USER,
                    Conn.PASS
            );

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
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
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
    public static List<Sensor_DAO> pull_all_sensors(String device_ID_Ref) throws SQLException, ClassNotFoundException
    {
        ArrayList<Sensor_DAO> tmp = new ArrayList<>();
        try {
            Class.forName(Conn.DRIVER);
            Connection conn = DriverManager.getConnection(
                    Conn.DATABASE,
                    Conn.USER,
                    Conn.PASS
            );

            String query = "SELECT * FROM sensor WHERE OWNER = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, device_ID_Ref);

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
                Sensor_DAO tx = new Sensor_DAO();
                tx.created_Date = rs.getString("CREATED_DATE");
                tx.device_Ref_ID = rs.getInt("ID_DEVICE_REF");
                tx.name = rs.getString("NAME");
                tx.pin = rs.getInt("PIN");
                tx.lastActive_Date = rs.getString("LASTACTIVE_DATE");
                tx.sensorType = rs.getString("SENSORTYPE");
                tx.updateTime_Minutes = rs.getInt("UPDATETIME_MINUTES");
                tx.sensor_ID = rs.getInt("ID_SENSOR");
                tmp.add(tx);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }   
}
