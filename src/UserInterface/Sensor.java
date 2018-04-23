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
public class Sensor {

    private String name = "undefined_Sensor";
    private int sensorId = 0;
    private int value;
    private ArrayList<Integer> data;

    public Sensor(String nameS, int id) {
        this.name = nameS;
        this.sensorId = id;
        this.value = 0;
        this.data = new ArrayList<>();
    }

    public void clearData() {
        this.data = new ArrayList<>();
    }
    
    /*
    *   SETS
    */
    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }
    
    public void setValue(int v) {
        this.value = v;
        data.add(value);
    }

    public void setId(int id) {
        this.sensorId = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /*
    *   GETS
    */
    public int getValue() {
        return value;
    }
    public ArrayList getData(){
        return data;
    }

    public int getId() {
        return sensorId;
    }

    public String getName() {
        return name;
    }
}
