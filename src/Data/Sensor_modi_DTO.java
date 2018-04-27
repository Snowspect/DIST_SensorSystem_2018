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
public class Sensor_modi_DTO {
    /**
     * Changes the device reference id, which has to exists within the device table
     * @param device_id_ref
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Device_Ref(String device_id_ref) throws SQLException, ClassNotFoundException //get question from database
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
            String query = "INSERT INTO sensor (ID_DEVICE_REF) VALUES (?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, device_id_ref);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "device_id_ref_changed";
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
     * Changes the date it was configured
     * @param sensortype
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Sensortype(String sensortype, int sensor_id_ref) throws SQLException, ClassNotFoundException {
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
            String query = "INSERT INTO sensor (SENSORTYPE) VALUES (?) WHERE ID_SENSOR = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, sensortype);
            preparedStmt.setInt(2, sensor_id_ref);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "sensortype_changed";
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
     * Changes the pin it is registered to
     * @param pin
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Pin(String pin, int sensor_id_ref) throws SQLException, ClassNotFoundException {
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
            String query = "INSERT INTO sensor (PIN) VALUES (?) WHERE ID_SENSOR = ?";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, pin);
            preparedStmt.setInt(2, sensor_id_ref);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "pin_changed";
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
     * changes lastActive Date of the sensor - takes a date in the format: as argument.
     * @param lastActive_Date
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_LastActive(String lastActive_Date) throws SQLException, ClassNotFoundException {
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
            String query = "INSERT INTO sensor (LASTACTIVE_DATE) VALUES (?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, lastActive_Date);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "lastActive_Date_changed";
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
     * Changes the IP associated with the sensor
     * @param updatetime_Minutes
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Updatetime(String updatetime_Minutes) throws SQLException, ClassNotFoundException {
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
            String query = "INSERT INTO sensor (UPDATETIME_MINUTES) VALUES (?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, updatetime_Minutes);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "updatetime_changed";
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
     * Changes the name of the sensor
     * @param sensor_name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static String sensor_Change_Name(String sensor_name) throws SQLException, ClassNotFoundException {
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
            String query = "INSERT INTO sensor (NAME) VALUES (?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, sensor_name);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "sensor_name_changed";
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
     * pulls all related sensors to a device
     */
    public static List<Integer> sensor_Pull_Related_SensorIDs(int device_ID_ref) throws SQLException, ClassNotFoundException
    {
        ArrayList<Integer> tmp = new ArrayList<>();
        try {
            Class.forName(Conn.DRIVER);
            Connection conn = DriverManager.getConnection(
                    Conn.DATABASE,
                    Conn.USER,
                    Conn.PASS
            );

            String query = "SELECT * FROM sensor WHERE ID_DEVICE_REF = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, device_ID_ref);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                tmp.add(rs.getInt("ID_SENSOR"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }
}