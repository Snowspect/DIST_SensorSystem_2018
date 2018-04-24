/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import UserInterface.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author Bruger
 */
@WebService(endpointInterface = "SensorSystemServer.SensorSystemInterface")
public class SensorSystemImplements implements SensorSystemInterface {

//    private HashMap<Integer, User> activeUsers;
//
//    public SensorSystemImplements() {
//        activeUsers = new HashMap<>();
//        
//    }
//    public void putUserInLogic(int user){
//        if(activeUsers.containsKey(user)) {
//            activeEnvironment.put(user, new SensorSystemLogic());
//        }
//    }
//    
//    public void setDeviceSensorData(ArrayList<Integer> data, int user, int deviceID, int sensorID) {
//        if (activeUsers.containsKey(user)) {
//            activeUsers.get(user).devices.get(deviceID).getSensor(sensorID).setData(data);
//        }
//    }
//
//    public void setDeviceName(int user, int deviceID, String name) {
//        if (activeUsers.containsKey(user)) {
//            activeUsers.get(user).devices.get(deviceID).setDeviceName(name);
//        }
//    }
//
//    public void setSensorName(int user, int deviceID, int sensorID, String name) {
//        if (activeUsers.containsKey(user)) {
//            activeUsers.get(user).devices.get(deviceID).getSensor(sensorID).setName(name);
//        }
//    }
//
//    public void addDevice(int user, String name) {
//        if (activeUsers.containsKey(user)) {
//            User u = activeUsers.get(user);
//
//            u.devices.add(new Device(u.devices.size(), name));
//        }
//    }
//
//    public void addSensor(int user, int deviceID, String name) {
//        if (activeUsers.containsKey(user)) {
//            User u = activeUsers.get(user);
//            u.devices.get(deviceID).addSensor(name);
//
//        }
//    }
//
//    public void deleteDevice(int user, int deviceID) {
//        if (activeUsers.containsKey(user)) {
//            activeUsers.get(user).devices.remove(deviceID);
//        }
//    }
//
//    public void deleteSensor(int user, int deviceID, int sensorID) {
//        if (activeUsers.containsKey(user)) {
//            activeUsers.get(user).devices.get(deviceID).removeSensor(sensorID);
//        }
//    }
//    
//    public int login(String user, String password) {
//        try {
//            System.out.println("Login: " + user);
//            URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
//            QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
//            Service service = Service.create(url, qname);
//            Useradmin ba = service.getPort(Useradmin.class);
//
//            User b = ba.hentBruger(user, password);
//            String id_string = b.adgangskode + ":" + b.campusnetId + ":" + b.studeretning;
//            int id_code = id_string.hashCode();
//            activeUsers.put(id_code, b);
//            return id_code;
//
//        } catch (Exception ex) {
//            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
//            return 0;
//        }
//    }

}
