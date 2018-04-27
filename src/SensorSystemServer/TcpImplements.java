/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import static Data.Measurement_DTO.createMeasurement;
import Data.Sensor_DAO;
import static Data.Sensor_DTO.sensor_Pull_Sensor;
import Data.Sensor_modi_DTO;
import static Data.Sensor_modi_DTO.sensor_Pull_Related_SensorIDs;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;

/**
 *
 * @author Patrick Green Knudsen
 */
@WebService(endpointInterface = "SensorSystemServer.TcpInterface")
public class TcpImplements implements TcpInterface{
    
    public void oploadData(int sensor_ID, String data){
        Date d = new Date();
        
        try {
            createMeasurement(sensor_ID, data ,d.getTime());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TcpImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<String> get_Sensor_Info(int sensor_id) 
    {
        List<String> lt = new ArrayList<>();
        try {
            lt = sensor_Pull_Sensor(sensor_id);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lt;
    }
    
    public List<Integer> get_Device_Sensors(int device_id){
        List<Integer> ret = new ArrayList<>();
        try {
            ret = sensor_Pull_Related_SensorIDs(device_id);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TcpImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public void sensorConf(int sensor_Type, int pin_Num, int sensor_ID){
        
        try {
            String t = "";
            String p = pin_Num+"";
        
            if(sensor_Type == 0) t = "ANALOG";
            if(sensor_Type == 1) t = "DIGITAL";
        
            Sensor_modi_DTO.sensor_Change_Sensortype(t, sensor_ID);
            Sensor_modi_DTO.sensor_Change_Pin(p, sensor_ID);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(TcpImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
