/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService 
/**
 *
 * @author Bruger
 */
public interface SensorSystemInterface {
    
    @WebMethod public void opdateActiveUsers();
    /*
    * validates toke
    */
    @WebMethod public int validatToken(int token);
    
    /*
    *   User login
    *   @param user     -- username
    *   @param password -- password 
    */
    @WebMethod public int login(String username, String password);
    
    /*
    *   Create Sensor
    *   @param user         -- user id
    *   @param name         -- Sensor name
    *   @param id_device    -- id of the device sensor is connected to
    *   @param sensorType   -- Type, Either put ANALOG or DIGITAL
    *   @param pin          -- pin number
    */
    @WebMethod public String create_Sensor(
            int token, 
            String name, 
            int id_device, 
            String sensorType, 
            String pin);

    /*
    *   Create Device
    *   @param user -- user id
    *   @param name -- Device name
    */    
    @WebMethod public String create_Device(int token, String name, int external_id, String owner);
    
    //@WebMethod public String delete_Sensor(int token, int sensor_id);
    @WebMethod public String delete_Device(int token, int device_id);
    
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
    @WebMethod public String set_Sensor_Info(int token,
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
    @WebMethod public String set_Device_Info(int token,
                                            int device_id, 
                                            String owner, 
                                            String name);
   
    /*
    *   Get sensor info
    *   @param user             -- user id
    *   @param sensor_id        -- id of the sensor
    */ 
    @WebMethod public String [] get_Sensor_Info(int token, int sensor_id);
    
    /*
    *   Get device info
    *   @param user             -- user id
    *   @param device_id        -- id of the device
    */ 
    @WebMethod public String [] get_Device_Info(int token, int device_id);
    
    @WebMethod public int [] get_Devices_ID(int token, String owner);
    
    @WebMethod public int [] get_Sensors_ID(int token, int device_id);
    
    /*
    *   Get all IDs owned by user
    *   @param user --user id
    */
    @WebMethod public String [] get_ids(int token, String owner);
    
    @WebMethod public String [] get_Sensor_Data(int token, int sensor_id);
    
    @WebMethod public String [] get_Sensor_Data_Within_Dates(
                                            int token,
                                            int sensor_id, 
                                            Date older, 
                                            Date newer); 
    
    @WebMethod public String [] get_Measurement_Data(int token, int sensor_id);
    
    @WebMethod public String [] get_Measurement_Data_Within_Dates(
                                            int token,
                                            int sensor_id, 
                                            Date older, 
                                            Date newer); 
}
