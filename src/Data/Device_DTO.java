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
public class Device_DTO
{
    /**
     * Creates a row in the table device with the given parameters.
     * device_id will increment itself and that id will be returned and given to user.
     * @param device_name
     * @param owner
     * @param ip
     * @param configured
     * @param configured_date --will be pulled from current date
     * @param lastActive --will be pulled from current date
     * @param created_date --will be pulled from current date
     * @return 
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
        public static String device_CreateDevice(
                int external_id,
                String device_name, 
                String owner,  
                Timestamp lastActive,
                Timestamp created_date
                                         ) throws SQLException, ClassNotFoundException //get question from database
        {
        /**
         * lastActive/created_date could be a date object, which a mySql database also can hold
         */
        //try to connect to jdbc and create user
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
        // the mysql insert statement, adding a person into person table
        String query = "INSERT INTO device (EXTERNAL_ID,NAME,OWNER,LASTACTIVE_DATE,CREATED_DATE) VALUES (?,?,?,?,?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        PreparedStatement dw = conn.prepareStatement(query);
        preparedStmt.setInt(1, external_id);
        preparedStmt.setString(2, device_name);
        preparedStmt.setString(3, owner);
        preparedStmt.setTimestamp(4, lastActive);
        preparedStmt.setTimestamp(5, created_date);


        // execute the preparedstatement
        preparedStmt.execute();

        //close connection
        conn.close();

        //never displayed
        return "device_created";

    }
        
     /**
     * pulls entire device information based on device ID
     */
    public static List<String> device_Pull_Device(int device_ID) throws SQLException, ClassNotFoundException
    {
        ArrayList<String> tmp = new ArrayList<String>();
        
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
        String query = "SELECT * FROM device WHERE ID_DEVICE = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, device_ID);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            tmp.add(rs.getInt("ID_DEVICE") + "");
            tmp.add(rs.getInt("EXTERNAL_ID") + "");
            tmp.add(rs.getString("NAME"));
            tmp.add(rs.getString("OWNER"));
            tmp.add(rs.getString("LASTACTIVE_DATE"));
            tmp.add(rs.getString("CREATED_DATE"));
        }
        return tmp;
    }
    
    public static int device_getID_with_ExternalID(int external_id) throws SQLException, ClassNotFoundException
    {
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
        String query = "SELECT ID_DEVICE FROM device WHERE EXTERNAL_ID = ?";   
        
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, external_id);
        
        int x = 0;
        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            x = rs.getInt("ID_DEVICE");
        }
        return x;
    }
    
    public static String[] device_Pull_All_Devices(String device_owner) throws SQLException, ClassNotFoundException
    {
        String[] x = null;
        ArrayList<Device_DAO> tmp = new ArrayList<>();
        
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);
        String query = "SELECT * FROM device WHERE OWNER = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, device_owner);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
            Device_DAO tx = new Device_DAO();
            tx.id_Device = rs.getInt("ID_DEVICE");
            tx.external_id = rs.getInt("EXTERNAL_ID");
            tx.created_Date = rs.getString("CREATED_DATE");
            tx.device_Name = rs.getString("NAME");
            tx.device_Owner = rs.getString("OWNER");
            tx.last_Active_Date = rs.getString("LASTACTIVE_DATE");
            tmp.add(tx);
        }
        //converts DAO array to string array
        x = new String[tmp.size()];
        int i = 0;
        for (Device_DAO device_DAO : tmp) {
            x[i] = device_DAO.toString();
            i++;
        }

        return x;
    }   
    
    public static String device_Delete_Device(int device_ID) throws SQLException, ClassNotFoundException
    { 
        // create a mysql database connection
        Class.forName("org.mariadb.jdbc.Driver"); //Conn is our connection file
        Connection conn = DriverManager.getConnection(Conn.CONNECTION_STRING);

        String query = "DELETE FROM device WHERE ID_DEVICE = ?";

        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, device_ID);

        ResultSet rs = preparedStmt.executeQuery();

        while (rs.next()) {
        }
        return "Device deleted";
    }
}