/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Bruger
 */
@WebService 
public interface TcpInterface {
    
    @WebMethod public void oploadData( int sensorID, int data );
    @WebMethod public String [] get_Sensor_Info(int sensor_id);
    @WebMethod public void sensorConf( int sensor_Type, int pin_Num, int sensor_ID);
    @WebMethod public int [] get_Device_Sensors(int device_id);
    
}
