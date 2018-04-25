/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import UserInterface.Device;
import UserInterface.Sensor;
import java.util.ArrayList;
import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService 
/**
 *
 * @author Bruger
 */
public interface SensorSystemInterface {
    
    @WebMethod public int login(String user, String password);
    
    @WebMethod public String set_Sensor_Info(int sensor_id, int device_id_ref, int type, int pin, String name);
    @WebMethod public String set_Device_Info(int device_id, String owner, String name);
    
    @WebMethod public Sensor get_Sensor_Info(int sensor_id);
    @WebMethod public Device get_Device_Info(int device_id);
    
    @WebMethod public void delete_Sensor(int sensor_id);
    @WebMethod public void delete_Device(int device_id);
    
    @WebMethod public ArrayList<Device> get_Devices(int owner);
    @WebMethod public void delete_Devices(int owner);
    
    @WebMethod public ArrayList<String> get_All_Sensor_Data(int sensor_id);
    @WebMethod public ArrayList<String> get_All_Sensor_Data_Within_Dates(int sensor_id, Date older, Date newer); 
}
