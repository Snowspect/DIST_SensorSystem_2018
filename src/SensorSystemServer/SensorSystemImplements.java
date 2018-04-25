/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import Data.Device_DAO;
import static Data.Device_DTO.*;
import static Data.Device_modi_DTO.*;

import Data.Measurement_DAO;
import static Data.Measure_modi_DTO.*;
import static Data.Measurement_DTO.*;

import Data.Sensor_DAO;
import static Data.Sensor_DTO.*;
import static Data.Sensor_modi_DTO.*;

import UserInterface.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bruger
 */
@WebService(endpointInterface = "SensorSystemServer.SensorSystemInterface")
public class SensorSystemImplements implements SensorSystemInterface 
{
    HashMap<Integer, User> activeUsers;
    
    public int login(String user, String password) {
        try {
            System.out.println("Login: " + user);
            URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
            QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
            Service service = Service.create(url, qname);
            Useradmin ba = service.getPort(Useradmin.class);

            User b = ba.hentBruger(user, password);
            String id_string = b.adgangskode + ":" + b.campusnetId + ":" + b.studeretning;
            int id_code = id_string.hashCode();
            activeUsers.put(id_code, b);
            return id_code;

        } catch (Exception ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public String create_Sensor(String name, String id_device, String sensorType, String pin){
        String ret = "";
        try {
            Date dt = new Date();
            ret = sensor_CreateSensor(name, id_device, sensorType, pin, dt.toString(), dt.toString());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }
    
    public String create_Device(String name, String owner){
        String ret = "";
        try {
            Date dt = new Date();
            ret = device_CreateDevice(name, owner, dt.toString(), dt.toString());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    /*
    * Change Sensor info, pull sensor info and the change what need and use this to send it back to database
    */ 
    public String set_Sensor_Info(int sensor_id, int device_id_ref, int type, int pin, String name){
        String status = "Something went wrong";
        try {
            //Change device its connected to
            String dir = device_id_ref+"";
            String tmp = sensor_Change_Device_Ref(dir);
            status= "Owner: " + tmp;
            
            //Change sensor type
            String tp = "";
            if (type == 0) tp = "ANALOG";
            else if (type == 1) tp = "DIGITAL";
            tmp = sensor_Change_Sensortype( tp, sensor_id);
            status = status + " Sensor: " + tmp;
            
            //Change pin
            String pn = pin+"";
            tmp = sensor_Change_Pin(pn, sensor_id);
            status = status + " Pin: " + tmp;
            
            //Change name
            tmp = sensor_Change_Name(name);
            status = status + " Name: " + tmp; 
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    
    public String set_Device_Info(int device_id, String owner, String name){
        String status = "Something went wrong";
        try {
            String tmp = device_Change_Owner(owner);
            status = "Owner: " + tmp;
            
            tmp = device_Change_Name(name);
            status = status +" Name: "+ tmp;
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public List<String> get_Sensor_Info(int sensor_id){
        List<String> lt = new ArrayList<>();
        try {
            lt = sensor_Pull_Sensor(sensor_id);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lt;
    }
    
    public List<String> get_Device_Info(int device_id){
        List<String> lt = new ArrayList<>();
        try {
            lt = device_Pull_Device(device_id);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lt;
    }

    public List<String> get_Devices_ID(String owner){
        List<String> ret = new ArrayList<>();
        try {
            List<Device_DAO> devices = new ArrayList<>();
            devices = device_Pull_All_Devices(owner);
            
            for(Device_DAO dd : devices){
                String dvc = dd.id_Device+"";
                ret.add(dvc);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }
    
    public String get_Sensors_ID(int device_ID_Ref){
        String ret = "";
        try {
            ret = sensor_Pull_Related_SensorIDs(device_ID_Ref);
        } catch (SQLException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public List<String> get_Sensor_Data(int sensor_id){
        List<String> ret = new ArrayList<>();
        try {
            ret = pull_all_data(sensor_id);
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public List<String> get_Device_Data(String device_ID_Ref){
        List<String> ret = new ArrayList<>();
        try {
            List<Sensor_DAO> sensor = new ArrayList<>();
            
            sensor = sensor_Pull_All_Sensors(device_ID_Ref);
            
            for(Sensor_DAO SD : sensor){
                ret.add( SD.toString() );
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public List<String> get_Sensor_Data_Within_Dates(int sensor_id, Date older, Date newer){
        List<String> ret = new ArrayList<>();
        try {
          
            ret = pull_data_within_dates(older.getTime(), newer.getTime(), sensor_id);

            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

}
