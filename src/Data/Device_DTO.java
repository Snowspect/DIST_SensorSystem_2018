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
        public static String createDevice(
                String device_name, 
                String owner,  
                String lastActive,
                String created_date
                                         ) throws SQLException, ClassNotFoundException //get question from database
        {
        /**
         * lastActive/created_date could be a date object, which a mySql database also can hold
         */
        //try to connect to jdbc and create user
        try
        {
            // create a mysql database connection
            Class.forName(Conn.DRIVER); //Conn is our connection file
            Connection conn = DriverManager.getConnection //Connection is a built in SQL class
                    (
                            Conn.DATABASE,
                            Conn.USER,
                            Conn.PASS
                    );
            // the mysql insert statement, adding a person into person table
            String query = "INSERT INTO device (NAME,OWNER,LASTACTIVE_DATE,CREATEDATE) VALUES (?,?,?,?)";
            
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            PreparedStatement dw = conn.prepareStatement(query);
            preparedStmt.setString(1, device_name);
            preparedStmt.setString(2, owner);
            preparedStmt.setString(3, lastActive);
            preparedStmt.setString(4, created_date);

            // execute the preparedstatement
            preparedStmt.execute();
            
            //close connection
            conn.close();

            //never displayed
            return "device_created";
        } catch (SQLException e)
        {
            System.err.println("Got an sql exception!");
            System.err.println(e.getMessage());
            return e.getMessage();
        } catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return e.getMessage();
        }
    }
     /**
     * pulls entire device information based on device ID
     */
    public static List<String> pull_device(int device_ID) throws SQLException, ClassNotFoundException
    {
        ArrayList<String> tmp = new ArrayList<String>();
        try {
            Class.forName(Conn.DRIVER);
            Connection conn = DriverManager.getConnection(
                    Conn.DATABASE,
                    Conn.USER,
                    Conn.PASS
            );

            String query = "SELECT * FROM device WHERE ID_DEVICE = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, device_ID);

            ResultSet rs = preparedStmt.executeQuery();
            
            while (rs.next()) {
                tmp.add(rs.getInt("ID_DEVICE") + "");
                tmp.add(rs.getString("NAME"));
                tmp.add(rs.getString("OWNER"));
                tmp.add(rs.getString("LASTACTIVE_DATE"));
                tmp.add(rs.getString("CREATED_DATE"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }
    
    public static List<Device_DAO> pull_all_devices(String device_owner) throws SQLException, ClassNotFoundException
    {
        ArrayList<Device_DAO> tmp = new ArrayList<>();
        try {
            Class.forName(Conn.DRIVER);
            Connection conn = DriverManager.getConnection(
                    Conn.DATABASE,
                    Conn.USER,
                    Conn.PASS
            );

            String query = "SELECT * FROM device WHERE OWNER = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, device_owner);

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
                Device_DAO tx = new Device_DAO();
                tx.id_Device = rs.getInt("ID_DEVICE");
                tx.created_Date = rs.getInt("CREATED_DATE");
                tx.device_Name = rs.getString("NAME");
                tx.device_Owner = rs.getString("OWNER");
                tx.last_Active_Date = rs.getInt("LASTACTIVE_DATE");
                tmp.add(tx);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return tmp;
    }   
    
    public static String delete_device(int device_ID) throws SQLException, ClassNotFoundException
    {
        
        //TODO FIX SO THAT IT DELETES A DEVICE
        try {
            Class.forName(Conn.DRIVER);
            Connection conn = DriverManager.getConnection(
                    Conn.DATABASE,
                    Conn.USER,
                    Conn.PASS
            );

            String query = " WHERE ID_DEVICE = ?";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, device_ID);

            ResultSet rs = preparedStmt.executeQuery();
            
            while (rs.next()) {
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("database error");
        }
        return "device deleted";
    }
}