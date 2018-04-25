/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import static Data.Device_modi_DTO.*;
import UserInterface.*;
import static Data.Sensor_modi_DTO.*;

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
    /*
    * Change Sensor info, pull sensor info and the change what need and use this to send it back to database
    */
    
    public String set_Sensor_Info(int sensor_id, int device_id_ref, int type, int pin, String name)
    {
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
            status = status + "Sensor: " + tmp;
            
            //Change pin
            String pn = pin+"";
            tmp = sensor_Change_Pin(pn, sensor_id);
            status = status + "Pin: " + tmp;
            
            //Change name
            tmp = sensor_Change_Name(name);
            status = status + "Name: " + tmp; 
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
    public String set_Device_Info(int device_id, String owner, String name){
        String status = "Something went wrong";
        try {
            change_owner(owner);
            sensor_Change_Name(name);
        } catch (SQLException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SensorSystemImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Sensor get_Sensor_Info(int sensor_id){

    }
    public Device get_Device_Info(int device_id){

    }

    public void delete_Sensor(int sensor_id){

    }
    public void delete_Device(int device_id){

    }

    public ArrayList<Device> get_Devices(int owner){

    }
    public void delete_Devices(int owner){

    }

    public ArrayList<String> get_All_Sensor_Data(int sensor_id){

    }
    public ArrayList<String> get_All_Sensor_Data_Within_Dates(int sensor_id, Date older, Date newer){

    }

}
