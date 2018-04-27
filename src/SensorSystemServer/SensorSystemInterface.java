/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService 
/**
 *
 * @author Bruger
 */
public interface SensorSystemInterface {
    /*
    *   User login
    *   @param user     -- username
    *   @param password -- password 
    */
    @WebMethod public int login(String user, String password);
    
    /*
    *   Create Sensor
    *   @param user         -- user id
    *   @param name         -- Sensor name
    *   @param id_device    -- id of the device sensor is connected to
    *   @param sensorType   -- Type, Either put ANALOG or DIGITAL
    *   @param pin          -- pin number
    */
    @WebMethod public String create_Sensor(int user, 
                                    String name, 
                                    int id_device, 
                                    String sensorType, 
                                    String pin);
    
    /*
    *   Create Device
    *   @param user -- user id
    *   @param name -- Device name
    */    
    @WebMethod public String create_Device(int user, String name);
    
    /*
    *   Change Sensor info, pull sensor info and the change what need and use 
    *   this to send it back to database
    *   @param user             -- user id
    *   @param sensor_id        -- id of the Sensor
    *   @param device_id_ref    -- Change which device sensor is connected to
    *   @param type             -- Change type, put ANALOG or DIGITAL
    *   @param pin              -- Change pin
    *   @param name             -- Change name
    */
    @WebMethod public String set_Sensor_Info(int user,
                                            int sensor_id, 
                                            int device_id_ref, 
                                            int type, 
                                            int pin, 
                                            String name);
    
    /*
    *   Change Device info, pull sensor info and the change what need and use 
    *   this to send it back to database
    *   @param user             -- user id
    *   @param device_id        -- id of the device
    *   @param device_id_ref    -- Change which device sensor is connected to
    *   @param type             -- Change type, put ANALOG or DIGITAL
    *   @param pin              -- Change pin
    *   @param name             -- Change name
    */
    @WebMethod public String set_Device_Info(int user,
                                            int device_id, 
                                            String owner, 
                                            String name);
   
    /*
    *   Get sensor info
    *   @param user             -- user id
    *   @param sensor_id        -- id of the sensor
    */ 
    @WebMethod public List<String> get_Sensor_Info(int user, int sensor_id);
    
    /*
    *   Get device info
    *   @param user             -- user id
    *   @param device_id        -- id of the device
    */ 
    @WebMethod public List<String> get_Device_Info(int user, int device_id);
    
    @WebMethod public List<Integer> get_Devices_ID(int user);
    
    @WebMethod public List<Integer> get_Sensors_ID(int user, int device_id);
    
    /*
    *   Get all IDs owned by user
    *   @param user --user id
    */
    @WebMethod public ArrayList<ArrayList<Integer>> get_ids(int user);
    
    @WebMethod public List<String> get_Sensor_Data(int user, int sensor_id);
    
    @WebMethod public List<String> get_Sensor_Data_Within_Dates(
                                            int user,
                                            int sensor_id, 
                                            Date older, 
                                            Date newer); 
}
