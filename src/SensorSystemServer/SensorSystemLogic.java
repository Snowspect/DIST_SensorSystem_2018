/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import UserInterface.Device;
import UserInterface.User;
import java.util.ArrayList;
import javax.jws.WebService;

/**
 *
 * @author Bruger
 */
@WebService(endpointInterface = "SensorSystemServer.SensorSystemInterface")
public class SensorSystemLogic {

    private User user;

    public SensorSystemLogic() {

    }
    
    public void getUserData(){
        
        // get data from mySQL
        
    }
    
    public void getUserData(){
        
        // get data from mySQL
        
    }
    
    public void logStatus(){
    System.out.println("---------- ");
    System.out.println("user: "+user.brugernavn);
    System.out.println("requested: ");
    System.out.println("---------- ");
    }


}
/*
    public void setDeviceSensorData(ArrayList<Integer> data, int deviceID, int sensorID) {
        user.devices.get(deviceID).getSensor(sensorID).setData(data);
    }

    public void setDeviceName(int deviceID, String name) {
        user.devices.get(deviceID).setDeviceName(name);
    }

    public void setSensorName(int deviceID, int sensorID, String name) {
        user.devices.get(deviceID).getSensor(sensorID).setName(name);
    }

    public void addDevice(String name) {
        user.devices.add(new Device(user.devices.size(), name));
    }
    
    public void deleteDevice(int deviceID) {
        user.devices.remove(deviceID);
    }

    public void addSensor(int deviceID, String name) {
        user.devices.get(deviceID).addSensor(name);
    }
    
    public void removeSensor(int deviceID, int sensorID) {
        user.devices.get(deviceID).removeSensor(sensorID);
    }
*/