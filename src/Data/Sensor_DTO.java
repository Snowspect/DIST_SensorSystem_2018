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
        try {
            // create a mysql database connection
            //Class.forName(Conn.DRIVER); //Conn is our connection file
            //Connection conn = DriverManager.getConnection //Connection is a built in SQL class
             /*       (
                            Conn.DATABASE,
                            Conn.USER,
                            Conn.PASS
                    ); */
            // the mysql insert statement, adding a person into person table
//            String query = "INSERT INTO device (NAME,ID_DEVICE_REF,SENSORTYPE,PIN,CREATED_DATE,LASTACTIVE_DATE,UPDATETIME_MIN) VALUES (?,?,?,?,?,?,?)";

                        // create a mysql database connection
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            //Conn.DRIVER;
            /*Connection conn = DriverManager.getConnection //Connection is a built in SQL class
                    (
                            Conn.DATABASE,
                            Conn.USER,
                            Conn.PASS
                    );*/
            Connection conn = DriverManager.getConnection("jdbc:mariadb://159.89.134.40:3306/sensorsystem?user=Dist&password=*A4B6157319038724E3560894F7F932C8886EBFCF"); //
              String query = "INSERT INTO device (NAME,ID_DEVICE_REF,SENSORTYPE,PIN,CREATED_DATE,LASTACTIVE_DATE,UPDATETIME_MIN) VALUES (?,?,?,?,?,?,?)";          
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setString(2, id_device);
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
    public static List<String> sensor_Pull_Sensor(int sensor_ID) throws SQLException, ClassNotFoundException
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
    public static List<Sensor_DAO> sensor_Pull_All_Sensors(String device_ID_Ref) throws SQLException, ClassNotFoundException
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
