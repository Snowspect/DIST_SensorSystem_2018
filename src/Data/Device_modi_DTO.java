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


/**
 * Class to hold methods for modifying DB tables
 *
 * @author Team_effort
 */
public class Device_modi_DTO {
// gjh
    /**
     * Changes owner with a device_id
     *
     * @param owner the owner id to be changed: currently int return value from javabog.dk
     * @param device_id the id of the device you wish to change.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static String device_Change_Owner(String owner, int device_id) throws SQLException, ClassNotFoundException 
    {
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
        // the mysql insert statement, adding a person into person table
        String query = "UPDATE device SET OWNER = ? WHERE ID_DEVICE = ?";
        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, owner);
        preparedStmt.setInt(2, device_id);
        // execute the preparedstatement
        preparedStmt.execute();

        //close connection
        conn.close();

        //never displaed
        return "owner_changed";
    }

    /**
     * changes lastActive Date
     *
     * @param lastActive_Date
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static String device_Change_LastActive(Timestamp lastActive_Date, int device_id) throws SQLException, ClassNotFoundException 
    {
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
        // the mysql insert statement, adding a person into person table
        String query = "UPDATE device SET LASTACTIVE_DATE = ? WHERE DEVICE_ID = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setTimestamp(1, lastActive_Date);
        preparedStmt.setInt(2, device_id);
        // execute the preparedstatement
        preparedStmt.execute();

        //close connection
        conn.close();

        //never displaed
        return "lastActive_Date_changed";
    }

    /**
     * Changes device name
     *
     * @param device_name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static String device_Change_Name(String device_name, int device_id) throws SQLException, ClassNotFoundException 
    {
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
        // the mysql insert statement, adding a person into person table
        String query = "UPDATE device SET NAME = ? WHERE ID_DEVICE = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, device_name);
        preparedStmt.setInt(2, device_id);
        // execute the preparedstatement
        preparedStmt.execute();

        //close connection
        conn.close();

        //never displaed
        return "device_name_changed";
        }
    

    /**
     * pulls deviceID
     * Not neccesary but could be implemented.
     */
    public static int device_Pull_DeviceID(String device_Name) throws SQLException, ClassNotFoundException 
    {
        int tmp = 0;
        
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        String query = "SELECT ID_DEVICE FROM device WHERE NAME = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, device_Name);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            tmp = rs.getInt("ID_DEVICE");
        }
        
        return tmp;
    }

    /**
     * pulls device Name
     */
    public static String device_Pull_DeviceName(int device_ID) throws SQLException, ClassNotFoundException 
    {
        String tmp = "";
       
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        String query = "SELECT NAME FROM device WHERE ID_DEVICE = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, device_ID);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            tmp = rs.getString("NAME");
        }
        return tmp;
    }

    /**
     * pulls owner string
     */
    public static String device_Pull_Owner(int device_ID) throws SQLException, ClassNotFoundException 
    {
        String tmp = "";
        
            Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
            Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

            String query = "SELECT OWNER FROM device WHERE ID_DEVICE = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, device_ID);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                tmp = rs.getString("OWNER");
            }
        return tmp;
    }

    public static ArrayList<Integer> device_Pull_all_device_ids(String owner) throws ClassNotFoundException, SQLException 
    {
        ArrayList<Integer> tmp = new ArrayList<>();
        
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        String query = "SELECT ID_DEVICE FROM device WHERE OWNER = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, owner); //owner

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            tmp.add(rs.getInt("ID_DEVICE"));
        }
        
        return tmp;
    }
}