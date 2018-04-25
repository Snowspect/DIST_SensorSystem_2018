/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

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
    
    @WebMethod public int login(String user, String password);
    
    @WebMethod public String set_Sensor_Info(int sensor_id, 
                                            int device_id_ref, 
                                            int type, 
                                            int pin, 
                                            String name);
    
    @WebMethod public String set_Device_Info(int device_id, 
                                            String owner, 
                                            String name);
    
    
    @WebMethod public String create_Sensor(String name, String id_device, String sensorType, String pin);
    @WebMethod public String create_Device(String name, String owner);
    
    @WebMethod public List<String> get_Sensor_Info(int sensor_id);
    @WebMethod public List<String> get_Device_Info(int device_id);
    
    @WebMethod public List<String> get_Sensors_ID(int device_ID_Ref);
    @WebMethod public List<String> get_Devices_ID(String owner);
    
    @WebMethod public List<String> get_Sensor_Data(int sensor_id);
    @WebMethod public List<String> get_Device_Data(String device_ID_Ref);
    @WebMethod public List<String> get_Sensor_Data_Within_Dates(
                                            int sensor_id, 
                                            Date older, 
                                            Date newer); 
}
