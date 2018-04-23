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

/**
 * Class to hold methods for modifying DB tables
 *
 * @author Team_effort
 */
public class Device_modi_DTO {

    /**
     * Changes owner
     *
     * @param owner
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static String Change_owner(String owner) throws SQLException, ClassNotFoundException {
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
            String query = "INSERT INTO device (OWNER) VALUES (?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, owner);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "owner_changed";
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
     * changes lastActive Date
     *
     * @param lastActive_Date
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static String Change_lastActive(String lastActive_Date) throws SQLException, ClassNotFoundException {
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
            String query = "INSERT INTO device (LASTACTIVE_DATE) VALUES (?)";

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
     * Changes device name
     *
     * @param device_name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static String Change_Name(String device_name) throws SQLException, ClassNotFoundException {
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
            String query = "INSERT INTO device (NAME) VALUES (?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, device_name);
            // execute the preparedstatement
            preparedStmt.execute();

            //close connection
            conn.close();

            //never displaed
            return "device_name_changed";
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
     * pulls deviceID
     */
    public static int Pull_deviceID(String device_Name) throws SQLException, ClassNotFoundException {
        int tmp = 0;
        try {
            Class.forName(Conn.DRIVER);
            Connection conn = DriverManager.getConnection(
                    Conn.DATABASE,
                    Conn.USER,
                    Conn.PASS
            );

            String query = "SELECT ID_DEVICE FROM device WHERE NAME = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, device_Name);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                tmp = rs.getInt("ID_DEVICE");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }
    /**
     * pulls device Name
     */
    public static String Pull_deviceName(int device_ID) throws SQLException, ClassNotFoundException
    {
        String tmp = "";
        try {
            Class.forName(Conn.DRIVER);
            Connection conn = DriverManager.getConnection(
                    Conn.DATABASE,
                    Conn.USER,
                    Conn.PASS
            );

            String query = "SELECT NAME FROM device WHERE ID_DEVICE = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, device_ID);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                tmp = rs.getString("NAME");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }
    
    /**
     * pulls owner string
     */
    public static String Pull_owner(int device_ID) throws SQLException, ClassNotFoundException
    {
        String tmp = "";
        try {
            Class.forName(Conn.DRIVER);
            Connection conn = DriverManager.getConnection(
                    Conn.DATABASE,
                    Conn.USER,
                    Conn.PASS
            );

            String query = "SELECT ID_DEVICE FROM device WHERE NAME = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, device_ID);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                tmp = rs.getString("OWNER");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }
}
