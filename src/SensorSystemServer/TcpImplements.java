/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import static Data.Measurement_DTO.createMeasurement;
import Data.Sensor_modi_DTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruger
 */
public class TcpImplements implements TcpInterface{
    
    public void oploadData(int sensor_ID, String data){
        Date d = new Date();
        
        try {
            createMeasurement(sensor_ID, data ,d.getTime());
        } catch (SQLException ex) {
            Logger.getLogger(TcpImplements.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TcpImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void oploadDataStream(int sensor_ID, ArrayList<String> data){
        
    }
    
    public void sensorConf(int sensor_Type, int pin_Num, int sensor_ID){
        
        try {
            String t = "";
            String p = pin_Num+"";
        
            if(sensor_Type == 0) t = "ANALOG";
            if(sensor_Type == 1) t = "DIGITAL";
        
            Sensor_modi_DTO.sensor_Change_Sensortype(t, sensor_ID);
            Sensor_modi_DTO.sensor_Change_Pin(p, sensor_ID);
        } catch (SQLException ex) {
            Logger.getLogger(TcpImplements.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TcpImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
