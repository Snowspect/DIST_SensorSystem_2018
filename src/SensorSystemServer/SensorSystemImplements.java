/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import Data.Device_DAO;
import static Data.Device_DTO.*;
import static Data.Device_modi_DTO.*;

import static Data.Measure_modi_DTO.*;
import static Data.Measurement_DTO.*;

import static Data.Sensor_DTO.*;
import static Data.Sensor_modi_DTO.*;

import UserInterface.*;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class SensorSystemImplements implements SensorSystemInterface {

    private HashMap<Integer, User> activeUsers;

    public SensorSystemImplements() {
        activeUsers = new HashMap<>();
    }

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
    
    public String create_Sensor(int user, 
                                String name, 
                                String id_device, 
                                String sensorType, 
                                String pin) 
    {
        String ret = "";
        if (activeUsers.containsKey(user)) {
            try {
                Date dt = new Date();
                ret = sensor_CreateSensor(name, id_device, sensorType, pin, dt.toString(), dt.toString());
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
    
    public String create_Device(int user, String name) 
    {
        String ret = "";
        if (activeUsers.containsKey(user)) {
            try {
                Date dt = new Date();
                Timestamp dx = new Timestamp(dt.getTime());
                ret = device_CreateDevice(2,name, user + "", dx, dx);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    public String set_Sensor_Info(int user, 
                                int sensor_id, 
                                int device_id_ref, 
                                int type, 
                                int pin, 
                                String name) 
    {
        String status = "Something went wrong";
        if (activeUsers.containsKey(user)) {
            try {
                //Change device its connected to
                String dir = device_id_ref + "";
                String tmp = sensor_Change_Device_Ref(dir);
                status = "Owner: " + tmp;

                //Change sensor type
                String tp = "";
                if (type == 0) {
                    tp = "ANALOG";
                } else if (type == 1) {
                    tp = "DIGITAL";
                }
                tmp = sensor_Change_Sensortype(tp, sensor_id);
                status = status + " Sensor: " + tmp;

                //Change pin
                String pn = pin + "";
                tmp = sensor_Change_Pin(pn, sensor_id);
                status = status + " Pin: " + tmp;

                //Change name
                tmp = sensor_Change_Name(name);
                status = status + " Name: " + tmp;
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public String set_Device_Info(int user, 
                                int device_id, 
                                String owner,    
                                String name) 
    {
        String status = "Something went wrong";
        if (activeUsers.containsKey(user)) {
            try {
                String tmp = device_Change_Owner(owner);
                status = "Owner: " + tmp;
                tmp = device_Change_Name(name);
                status = status + " Name: " + tmp;
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }


    public List<String> get_Sensor_Info(int user, int sensor_id) 
    {
        List<String> lt = new ArrayList<>();
        if (activeUsers.containsKey(user)) {
            try {
                lt = sensor_Pull_Sensor(sensor_id);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lt;
    }

    public List<String> get_Device_Info(int user, int device_id) 
    {
        List<String> lt = new ArrayList<>();
        if(activeUsers.containsKey(user)) {
            try {
                lt = device_Pull_Device(device_id);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lt;
    }
    
    public List<String> get_ids(int user)
    {
        List<String> ret = new ArrayList<>();
        if(activeUsers.containsKey(user)) {
            try {
                List<Integer> devices_id = get_Devices_ID(user);
                
                ArrayList<Integer> related_sensor_id = new ArrayList<>();
                for(Integer i : devices_id)  
                {
<<<<<<< HEAD
                    String s = i+"-"+sensor_Pull_Related_SensorIDs(i);
                    ret.add(s);
||||||| merged common ancestors
                    String s = sensor_Pull_Related_SensorIDs(i);
                    ret.put(i, s);
=======
                    
                    related_sensor_id = (ArrayList<Integer>) sensor_Pull_Related_SensorIDs(i);
                    //ret.put(i, s);
>>>>>>> 92223886bb443b4153feff55958bec66b0f0834f
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return ret;
    }


    /*
    * Get List of IDs of devices connected to user
    */
    private List<Integer> get_Devices_ID(int user) throws SQLException, ClassNotFoundException 
    {
        List<Integer> ret = new ArrayList<>();
        List<Device_DAO> devices = device_Pull_All_Devices(user+"");
        devices.stream().map((dd) -> dd.id_Device ).forEachOrdered((dvc) -> {
            ret.add(dvc);
        });
        return ret;
    }

    
    /*
    *   Get all data from a sensor
    */
    public List<String> get_Sensor_Data(int user, int sensor_id) 
    {
        List<String> ret = new ArrayList<>();
        if(activeUsers.containsKey(user)) {
            try {
                ret = pull_all_data(sensor_id);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    /*
    *   Get all data from sensor within Dates
    */
    public List<String> get_Sensor_Data_Within_Dates(int user, 
                                                    int sensor_id, 
                                                    Date older, 
                                                    Date newer) 
    {
        List<String> ret = new ArrayList<>();
        if(activeUsers.containsKey(user)) {
            try {

                ret = pull_data_within_dates(older.getTime(), newer.getTime(), sensor_id);

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

}
