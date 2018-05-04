/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import static Data.Device_DTO.*;
import static Data.Device_modi_DTO.*;

import static Data.Measure_modi_DTO.*;
import static Data.Measurement_DTO.*;

import static Data.Sensor_DTO.*;
import static Data.Sensor_modi_DTO.*;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;

import java.net.URL;
import java.security.SecureRandom;
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
import java.util.Random;

/**
 *
 * @author Bruger
 */
@WebService(endpointInterface = "SensorSystemServer.SensorSystemInterface")
public class SensorSystemImplements implements SensorSystemInterface {

    private HashMap<Integer, Bruger> activeUsers;
    private HashMap<Bruger, Date> lastActive;

    public SensorSystemImplements() {
        activeUsers = new HashMap<>();
        lastActive = new HashMap<>();
    }
    
    private void opdateUser(int id)
    {
        
        Bruger b = activeUsers.get(id);
        Date currentTime = new Date();
        Date lastRequestTime = lastActive.get(b);
        
        if (lastRequestTime.getTime() + 300000 < currentTime.getTime())
        {
            lastActive.replace(b, lastRequestTime, currentTime);
        }
        else
        {
            lastActive.remove(b);
            activeUsers.remove(id);            
        }
    }
    

    public int login(String user, String password)
    {
        try {
            System.out.println("Login: " + user);
            URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
            QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
            Service service = Service.create(url, qname);
            
            Brugeradmin ba = service.getPort(Brugeradmin.class);
            Bruger b = ba.hentBruger(user, password);

            int id_code = 0;
            
            do{
                Random sr = new Random(); 
                id_code = sr.nextInt();
                System.out.println(id_code);
            }
            while( activeUsers.containsValue(id_code) || id_code == 0 );
            
            activeUsers.put(id_code, b);
            lastActive.put(b, new Date());
            return id_code;

        } catch (Exception ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public String create_Sensor(int user, 
                                String name, 
                                int id_device, 
                                String sensorType, 
                                String pin) 
    {
        String ret = "";
        opdateUser(user);
        if (activeUsers.containsKey(user)) {
            try {
                Date dt = new Date();
                Timestamp dx = new Timestamp(dt.getTime());
                ret = sensor_CreateSensor(name, id_device, sensorType, pin, dx, dx, "4");
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
    
    public String create_Device(int user, String name) 
    {
        String ret = "";
        //opdateUser(user);
        if (activeUsers.containsKey(user) || user == 42) {
            try {
                Date dt = new Date();
                Timestamp dx = new Timestamp(dt.getTime());
                ret = device_CreateDevice(2, name, user + "", dx, dx);
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
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
        opdateUser(user);
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
        opdateUser(user);
        if (activeUsers.containsKey(user)) {
            try {
                String tmp = device_Change_Owner(owner,2);
                status = "Owner: " + tmp;
                tmp = device_Change_Name(name);
                status = status + " Name: " + tmp;
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return status;
    }

    public String[] get_Sensor_Info(int user, int sensor_id) 
    {
        String[] ret = new String[0];
        opdateUser(user);
        
        if (activeUsers.containsKey(user)) {
            try 
            {
                List<String> lt = sensor_Pull_Sensor(sensor_id);
                ret = new String[lt.size() ];
                for (int i = 0; i < lt.size(); i++) {
                    ret[i] = lt.get(i);
                }
                
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    public String [] get_Device_Info(int user, int device_id) 
    {
        String[] ret = new String[0];

        opdateUser(user);
        if(activeUsers.containsKey(user)) {
            try {
                List<String> lt = device_Pull_Device(device_id);
                ret = new String[lt.size()];
                
                for (int  i = 0;  i < lt.size();  i++) {
                    ret[i] = lt.get(i);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
        
    public String [] get_ids(int user)
    {
        String[] ret = new String[0];
        ArrayList<ArrayList<Integer>> related_sensor_id = new ArrayList<>();   
        opdateUser(user);
        if(activeUsers.containsKey(user)) {
            try {
                int[] devices_id = get_Devices_ID_p(user);
                for(Integer i : devices_id)  
                {   
                    //each device returns a list of integer id's. each list is added to a list of integer lists called related_sensor_id
                    related_sensor_id.add((ArrayList<Integer>) sensor_Pull_Related_SensorIDs(i));
                    //ret.put(i, s);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return  ret;
    }

    public int[] get_Devices_ID(int user)  
    {
        int[] ret = new int[0];
        opdateUser(user);
        if(activeUsers.containsKey(user)) {
            String userString = user + "";
            List<Integer> lt = device_Pull_all_device_ids(userString);
            
            ret = new int [lt.size()];
                
            for (int  i = 0;  i < lt.size();  i++) {
                ret[i] = lt.get(i);
            }
            
        }
        return ret;
    }
    
    public int [] get_Sensors_ID(int user, int device_id)  
    {
        int [] ret = new int[0];
        opdateUser(user);
        if(activeUsers.containsKey(user)) {
            try {
                List<Integer> lt = sensor_Pull_Related_SensorIDs(device_id);
                
                ret = new int [lt.size()];

                for (int  i = 0;  i < lt.size();  i++) {
                    ret[i] = lt.get(i);
                }
                
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    private int[] get_Devices_ID_p(int user) throws SQLException, ClassNotFoundException 
    {
        
        String userString = user + "";
        List<Integer> lt = device_Pull_all_device_ids(userString);
        int[] ret = new int [lt.size()];
        
        for (int  i = 0;  i < lt.size();  i++) {
            ret[i] = lt.get(i);
        }
        
        for (Integer integer : ret) {
            System.out.println("DeviceID 1:" + integer);
        }
        return ret;
    }

    public String[] get_Sensor_Data(int user, int sensor_id) 
    {
        String[] ret = new String [0];
        opdateUser(user);
        if(activeUsers.containsKey(user)) {
            try {
                List<String> lt = pull_all_data(sensor_id);
                ret = new String [lt.size()];
        
                for (int  i = 0;  i < lt.size();  i++) {
                    ret[i] = lt.get(i);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    public String[] get_Sensor_Data_Within_Dates(int user, 
                                                    int sensor_id, 
                                                    Date older, 
                                                    Date newer) 
    {
        String[] ret = new String [0];
        
        opdateUser(user);
        if(activeUsers.containsKey(user)) {
            try {

                List<String> lt = pull_data_within_dates(older.getTime(), newer.getTime(), sensor_id);
                ret = new String [lt.size()];
        
                for (int  i = 0;  i < lt.size();  i++) {
                    ret[i] = lt.get(i);
                }

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

}
