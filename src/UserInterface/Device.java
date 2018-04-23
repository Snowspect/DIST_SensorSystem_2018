/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import java.util.ArrayList;

/**
 *
 * @author Bruger
 */
public class Device 
{
    private int id;
    private int numOfSensors;
    private String name = "undefined_device";
    private ArrayList<Sensor> sensors;

    public Device(int id, String name) {
        this.name = name;
        this.id = id;
        this.sensors = new ArrayList<>();
    }
    
    public void setDeviceName(String name){
        this.name = name;
    }
    
    public void setSensorName(int id, String name){
        sensors.get(id).setName(name);
        
    }

    public int getId(){
        return this.id;
    }
    
    
    public String getName() {
        return name;
    }

    public Sensor getSensor(int id) {
        return sensors.get(id);
    }

    public void addSensor(String name) {
        Sensor e = new Sensor(name, sensors.size());
        sensors.add(e);
    }

    public void removeSensor(int id) {
        sensors.remove(id);
    }

    public void clearSensors(int id) {
        this.sensors = new ArrayList<>();
    }
}
