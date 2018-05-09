/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import static Data.Device_DTO.*;
import static Data.Device_modi_DTO.*;

import static Data.Measure_modi_DTO.*;
import Data.Measurement_DAO;
import static Data.Measurement_DTO.*;

import static Data.Sensor_DTO.*;
import static Data.Sensor_modi_DTO.*;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;

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
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Bruger
 */
@WebService(endpointInterface = "SensorSystemServer.SensorSystemInterface")
public class SensorSystemImplements implements SensorSystemInterface {

    //private
    private HashMap<Integer, Bruger> activeUsers;
    private HashMap<Bruger, Date> lastActive;
    private static final Logger LOGGER = Logger.getLogger( ServerMain.class.getName() );
    
    public void opdateActiveUsers(){
        Set<Integer> si = activeUsers.keySet();
        if(activeUsers.isEmpty()) return;
        int [] id = new int[activeUsers.size()];
        for (int i = 0; i < activeUsers.size(); i++) {
            id[i] = opdateToken(i);
        }
        for (int i : id) {
            if(i != 0){
                Bruger b = activeUsers.get(i);
                lastActive.remove(b);
                activeUsers.remove(i);
                LOGGER.log(Level.INFO, "{0} was log out", b.campusnetId);
            }
        }
        LOGGER.log(Level.INFO, "opdate Active Users");
    }

    private int opdateToken(int token) {
        if (activeUsers.containsKey(token)) {
            Bruger b = activeUsers.get(token);
            Date currentTime = new Date();
            Date lastRequestTime = lastActive.get(b);

            if (lastRequestTime.getTime() + 300000 > currentTime.getTime()) {
                lastActive.replace(b, lastRequestTime, currentTime);
               return 0;
            } else {
                return token;
            }
        }
        return 0;
    }

    private String get_Sensor_Owner(int sensor_id) throws SQLException, ClassNotFoundException {
        List<String> lt = sensor_Pull_Sensor(sensor_id);
        return device_Pull_Owner(Integer.parseInt(lt.get(2)));
    }

    private int[] get_Devices_ID_p(String owner) throws SQLException, ClassNotFoundException {
        List<Integer> lt = device_Pull_all_device_ids(owner);
        int[] ret = new int[lt.size()];

        for (int i = 0; i < lt.size(); i++) {
            ret[i] = lt.get(i);
        }
        return ret;
    }

    //public
    public SensorSystemImplements() {
        activeUsers = new HashMap<>();
        lastActive = new HashMap<>();
    }

    public int validatToken(int token) {
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            return token;
        } else {
            return 0;
        }
    }

    public int login(
            String username, 
            String password) {
        try {
            LOGGER.log(Level.INFO, "Login: {0}", username);
            URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
            QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
            Service service = Service.create(url, qname);

            Brugeradmin ba = service.getPort(Brugeradmin.class);
            Bruger b = ba.hentBruger(username, password);

            int id_code = 0;

            do {
                Random sr = new Random();
                id_code = sr.nextInt();
                System.out.println(id_code);
            } while (activeUsers.containsValue(id_code) || id_code == 0);

            b.campusnetId = username;
            activeUsers.put(id_code, b);
            lastActive.put(b, new Date());
            LOGGER.log( Level.INFO, "user {0} login success",b.campusnetId);
            return id_code;

        } catch (Exception ex) {
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
            return 0;
        }
    }

    
    public String create_Sensor(
            int token,
            String name,
            int id_device,
            int sensorType,
            int pin) {
        String ret;
        try {
            opdateToken(token);
            if (activeUsers.containsKey(token)) {
                String s = device_Pull_Owner(id_device);
                String c = activeUsers.get(token).campusnetId;
                if (c.equals(s)) {
                    Date dt = new Date();
                    Timestamp dx = new Timestamp(dt.getTime());
                    String st; 
                    switch (sensorType) {
                        case 0:
                            st = "DIGITAL";
                            break;
                        case 1:
                            st = "ANALOG";
                            break;
                        default:
                            return null;
                    }
                    
                    ret = sensor_CreateSensor(name, id_device, st, pin+"", dx, dx, "4");
                } else {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{c, s});
                    return null;
                }
            } else {
                LOGGER.log(Level.INFO, "Token: {0} not in use", token);
                return null;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
            return null;
        }
        //LOGGER.log(Level.INFO, "token: {0} made a sensor", token);
        return ret;
    }

    public String create_Device(
            int token,
            String name,
            int external_id,
            String owner) {
        String ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            String id = activeUsers.get(token).campusnetId;
            if (id.equals(owner)) {
                try {
                    Date dt = new Date();
                    Timestamp dx = new Timestamp(dt.getTime());
                    ret = device_CreateDevice(external_id, name, owner, dx, dx);
                } catch (SQLException | ClassNotFoundException ex) {
                    LOGGER.log( Level.SEVERE, ex.toString(), ex );
                    return null;
                }
            } else {
                LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{id, owner});
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "token: {0} made a Device", token);
        return ret;
    }
    
    public String delete_Device(
            int token,
            int device_id) {
        String ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            String id = activeUsers.get(token).campusnetId;
            
                try {
                    String o = device_Pull_Owner(device_id);
                    if (id.equals( o )) {
                    ret = device_Delete_Device(device_id);
                    } else {
                        LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{id, o});
                        return null;
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    LOGGER.log( Level.SEVERE, ex.toString(), ex );
                    return null;
                }
            
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "token: {0} delete a device", token);
        return ret;
    }

    public String delete_Sensor(
            int token,
            int sensor_id)
    {
        String ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            String id = activeUsers.get(token).campusnetId;
                try {
                    String o = get_Sensor_Owner(sensor_id);
                    if (id.equals( o )) {
                    ret = sensor_Delete_Sensor(sensor_id);
                    } else {
                        LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{id, o});
                        return null;
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    LOGGER.log( Level.SEVERE, ex.toString(), ex );
                    return null;
                }
            
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "token: {0} delete a device", token);
        return ret;
    }
    
    public String set_Sensor_Info(
            int token,
            int sensor_id,
            int device_id_ref,
            int type,
            int pin,
            String name) {
        String status ;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
           try {
                String s = device_Pull_Owner(device_id_ref);
                String c = activeUsers.get(token).campusnetId;
                if (c.equals(s))  {
                    //Change device its connected to
                    String dir = device_id_ref + "";
                    sensor_Change_Device_Ref(dir, sensor_id);
                    
                    status = "Device: " + dir;

                    //Change sensor type
                    
                    String tp;
                    switch (type) {
                        case 0:
                            tp = "DIGITAL";
                            break;
                        case 1:
                            tp = "ANALOG";
                            break;
                        default:
                            return "False type";
                    }
                    sensor_Change_Sensortype(tp, sensor_id);
                    status = status + " Sensor: " + tp;

                    //Change pin
                    String pn = pin + "";
                    sensor_Change_Pin(pn, sensor_id);
                    status = status + " Pin: " + pn;

                    //Change name
                    sensor_Change_Name(name, sensor_id);
                    status = status + " Name: " + name;
                    LOGGER.log(Level.INFO, "Token: {0} - {1}", new Object[]{token, status});
                } else {
                LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{c, s});
                return null;
                }   
            } catch (SQLException | ClassNotFoundException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
                return "SQL failed";
            }   
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} changed a sensor", token);
        return status;
    }

    public String set_Device_Info(
            int token,
            int device_id,
            String owner,
            String name) {
        String status;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            try {
                String s = device_Pull_Owner(device_id);
                String c = activeUsers.get(token).campusnetId;
                if (c.equals(s)) {
                    String tmp = device_Change_Owner(owner, device_id);
                    status = "Owner: " + owner;
                    tmp = device_Change_Name(name, device_id);
                    status = status + " Name: " + name;
                    
                    LOGGER.log(Level.INFO, "Token: {0} - {1}", new Object[]{token, status});
                } else {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{c, owner});
                    return null;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                    LOGGER.log( Level.SEVERE, ex.toString(), ex );
                    return null;
                }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} changed a device", token);
        return status;
    }

    public String[] get_Sensor_Info(
            int token, 
            int sensor_id) {
        String[] ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            try {
                List<String> lt = sensor_Pull_Sensor(sensor_id);
                String s = device_Pull_Owner(Integer.parseInt(lt.get(2)));
                String c = activeUsers.get(token).campusnetId;
                if (!(s.equals(c))) {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{c, s});
                    return null;
                }
                ret = new String[lt.size()];
                for (int i = 0; i < lt.size(); i++) {
                    ret[i] = lt.get(i);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} got sensor info", token);
        return ret;
    }

    public String[] get_Device_Info(
            int token, 
            int device_id) {
        String[] ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            try {
                List<String> lt = device_Pull_Device(device_id);
                if(lt.isEmpty() || lt == null) return null;
                String c = activeUsers.get(token).campusnetId;
                if (!(lt.get(3).equals( c ))) {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{c, lt.get(3)});
                    return null;
                }
                ret = new String[lt.size()];
                for (int i = 0; i < lt.size(); i++) {
                    ret[i] = lt.get(i);
                }
            } catch (SQLException | ClassNotFoundException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} got device info", token);
        return ret;
    }
    
    public int[] get_Devices_ID(
            int token, 
            String owner) {
    int[] ret;
    opdateToken(token);
    if (activeUsers.containsKey(token)) {
        String c = activeUsers.get(token).campusnetId;
        if ( c.equals(owner)) {
            List<Integer> lt = new ArrayList<>();
            try {
                lt = device_Pull_all_device_ids(owner);
            } catch (ClassNotFoundException | SQLException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
            }

            ret = new int[lt.size()];

            for (int i = 0; i < lt.size(); i++) {
                ret[i] = lt.get(i);
            }
        } else {
            LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{c, owner});
            return null;
        }
    } else {
        LOGGER.log(Level.INFO, "Token: {0} not in use", token);
        return null;
    }
    //LOGGER.log(Level.INFO, "Token: {0} got device ids", token);
    return ret;
}

    public int[] get_Sensors_ID(
            int token, 
            int device_id) {
        
        int[] ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            try {
                String s = device_Pull_Owner(device_id);
                String c = activeUsers.get(token).campusnetId;
                if ( c.equals(s)) {
                    List<Integer> lt = sensor_Pull_Related_SensorIDs(device_id);
                    if(lt.isEmpty()) return null;
                    ret = new int[lt.size()];

                    for (int i = 0; i < lt.size(); i++) {
                        ret[i] = lt.get(i);
                    }
                } else {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{c, s});
                    return null;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} got sensor ids", token);
        return ret;
    }
    
    public String[] get_All_Sensor_Info(
            int token, 
            int device_id) {
        String[] ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            try {
                String s = device_Pull_Owner(device_id);
                String c = activeUsers.get(token).campusnetId;
                if ( !( s.equals(c) ) ) {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{c, s});
                    return null;
                }
                ret = sensor_Pull_All_Sensors(device_id);
            } catch (SQLException | ClassNotFoundException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} got sensor all info", token);
        return ret;
    }

    public String[] get_ids(
            int token, 
            String owner) {
        String[] ret = new String[0];
        ArrayList<ArrayList<Integer>> related_sensor_id = new ArrayList<>();
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            String c = activeUsers.get(token).campusnetId;
            if ( c.equals(owner) ) {
                try {
                    int[] devices_id = get_Devices_ID_p(owner);
                    for (Integer i : devices_id) {
                        //each device returns a list of integer id's. each list is added to a list of integer lists called related_sensor_id
                        related_sensor_id.add((ArrayList<Integer>) sensor_Pull_Related_SensorIDs(i));
                        //ret.put(i, s);
                    }
                    for (int i = 0; i < devices_id.length; i++) {
                        List<Integer> lt = sensor_Pull_Related_SensorIDs(devices_id[i]);
                        ret[i] = devices_id[i] + " : ";
                        for (int j = 0; j < lt.size(); j++) {
                            ret[i] = ret[i] + "-" + lt.get(j);
                        }
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    LOGGER.log( Level.SEVERE, ex.toString(), ex );
                    return null;
                }
            } else {
                LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{c, owner});
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} got all ids", token);
        return ret;
    }

    public String[] get_Sensor_Data(
            int token, 
            int sensor_id) {
        String[] ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            try {
                String b = activeUsers.get(token).campusnetId;
                String o = get_Sensor_Owner(sensor_id);
                if (b.equals(o)) {
                    List<String> lt = pull_all_data(sensor_id);
                    ret = new String[lt.size()];

                    for (int i = 0; i < lt.size(); i++) {
                        ret[i] = lt.get(i);
                    }
                } else {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{b, o});
                    return null;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} got sensor data", token);
        return ret;
    }

    public String[] get_Sensor_Data_Within_Dates(
            int token,
            int sensor_id,
            Date older,
            Date newer) {
        String[] ret;

        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            try {
                String b = activeUsers.get(token).campusnetId;
                String o = get_Sensor_Owner(sensor_id);
                if (b.equals(o)) {
                    List<String> lt = pull_data_within_dates(older.getTime(), newer.getTime(), sensor_id);
                    ret = new String[lt.size()];

                    for (int i = 0; i < lt.size(); i++) {
                        ret[i] = lt.get(i);
                    }
                } else {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{b, o});
                    return null;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} got sensor data within dates", token);
        return ret;
    }

    public String[] get_Measurement_Data(
            int token, 
            int sensor_id) {
        String[] ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            try {
                String b = activeUsers.get(token).campusnetId;
                String o = get_Sensor_Owner(sensor_id);
                if ( b.equals( o ) ) {
                    List<Measurement_DAO> lt = pull_all_measurements(sensor_id);
                    ret = new String[lt.size()];

                    for (int i = 0; i < lt.size(); i++) {
                        Measurement_DAO dao = lt.get(i);
                        ret[i] = dao.toString();
                    }
                } else {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{b, o});
                    return null;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} got measurement data", token);
        return ret;
    }

    public String[] get_Measurement_Data_Within_Dates(
            int token,
            int sensor_id,
            Date older,
            Date newer) {
        String[] ret;
        opdateToken(token);
        if (activeUsers.containsKey(token)) {
            try {
                String b = activeUsers.get(token).campusnetId;
                String o = get_Sensor_Owner(sensor_id);
                if ( b.equals( o ) ) {
                    List<Measurement_DAO> lt = pull_all_measurements_within_dates(older.getTime(), newer.getTime(), sensor_id);
                    ret = new String[lt.size()];

                    for (int i = 0; i < lt.size(); i++) {
                        Measurement_DAO dao = lt.get(i);
                        ret[i] = dao.toString();
                    }
                } else {
                    LOGGER.log(Level.INFO, "{0} != {1}", new Object[]{b, o});
                    return null;
                }
            } catch (SQLException | ClassNotFoundException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
                return null;
            }
        } else {
            LOGGER.log(Level.INFO, "Token: {0} not in use", token);
            return null;
        }
        //LOGGER.log(Level.INFO, "Token: {0} got measurement data within dates", token);
        return ret;
    }
}
