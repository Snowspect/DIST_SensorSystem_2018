/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import static Data.Measurement_DTO.createMeasurement;
import static Data.Sensor_DTO.sensor_Pull_Sensor;
import Data.Sensor_modi_DTO;
import static Data.Sensor_modi_DTO.sensor_Pull_Related_SensorIDs;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    
    private static final Logger LOGGER = Logger.getLogger( ServerMain.class.getName() );
    
    public void oploadData(int sensor_ID, int data){
        Date d = new Date();
        Timestamp tp = new Timestamp(d.getTime());
        
        try {
            createMeasurement(sensor_ID, data ,tp);
        } catch (SQLException | ClassNotFoundException ex) {
           LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
    }
    public String [] get_Sensor_Info(int sensor_id) 
    {
        List<String> lt = new ArrayList<>();
        String[] al = new String[0];
        try {
            lt = sensor_Pull_Sensor(sensor_id);
            if(lt.isEmpty()) return null;
            al = new String[lt.size()];
            
            for (int i = 0; i < lt.size(); i++) {
                al[i] = lt.get(i);
            }
            if (al.length == 0) return null;
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
            return null;
        }
        return al;
    }
    
    public int [] get_Device_Sensors(int device_id){
         int [] ret = new int [0];
        try {
            ArrayList<Integer> tmp = (ArrayList<Integer>) sensor_Pull_Related_SensorIDs(device_id);
            if(tmp.isEmpty()) return null;
            ret = new int [tmp.size()];
            for (int i = 0; i < tmp.size(); i++) 
            {
                ret[i] = tmp.get(i);
            }
            if(ret.length == 0) return null;
            
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
            return null;
        }
        return ret;
    }
    
    public void sensorConf(int sensor_Type, int pin_Num, int sensor_ID){
        try {
            String t = "";
            String p = pin_Num+"";
        
            if(sensor_Type == 0) t = "DIGITAL";
            if(sensor_Type == 1) t = "ANALOG";
        
            Sensor_modi_DTO.sensor_Change_Sensortype(t, sensor_ID);
            Sensor_modi_DTO.sensor_Change_Pin(p, sensor_ID);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
    }
}
