/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author Bruger
 */
public class client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] arg) throws Exception
    {
        Scanner scanner = new Scanner(System.in);
        URL url = new URL("http://165.227.232.158:9901/SensorSystemService?wsdl");
        //URL url = new URL("http://localhost:9901/SensorSystemService?wsdl");
        QName qname = new QName("http://SensorSystemServer/", "SensorSystemImplementsService");
        Service service = Service.create(url, qname);
        SensorSystemInterface server = service.getPort(SensorSystemInterface.class);
        
        String owner = "";
        int token = 0;
        while(token == 0){
            System.out.println("Input username:");
            owner = scanner.nextLine();
            System.out.println("Input password:");
            String password = scanner.nextLine();
            token = server.login(owner, password); //"FEDpik"
            
            
        }
        HashMap<Integer, int[]> ids = new HashMap<>();
        while(true) 
        {
        
        }
    }
    
    public static HashMap<Integer, int[]> get_ids(SensorSystemInterface server, int id, String owner)
    {   
        HashMap<Integer,int[]> ids = new HashMap<>();
        int[] sensor_IDS;
        int[] device_IDs = server.get_Devices_ID(id, owner);
        for (int i = 0; i < device_IDs.length; i++) {
            sensor_IDS = server.get_Sensors_ID(id, device_IDs[i]);
            ids.put(device_IDs[i], sensor_IDS);
        }
        return ids;
    }
    
    public static void display(SensorSystemInterface server, int token, String owner, HashMap<Integer, int[]> ids )
    {
        
        int[] device_IDs = server.get_Devices_ID(token, owner);
        for (int i = 0; i < device_IDs.length; i++) {
            if( !( ids.containsKey( device_IDs[i] ) ) )
            {
                ids = get_ids(server, token, owner);
                
            }
        }
        System.out.println("Device - Sensors");
        for (int i = 0; i < device_IDs.length; i++) 
        {
            if( ids.containsKey( device_IDs[i] ) )
            {
                String pstm = device_IDs[i]+" : ";
                int sensor_ids[] = ids.get(i);
                for (int j = 0; j < sensor_ids.length; j++) {
                    pstm = pstm + sensor_ids[j]+ " - ";
                }
            System.out.println(pstm);
            }
        }
    }
    
    public static void options(SensorSystemInterface server, int token, String owner, int choice, Scanner scanner)
    {
        switch(choice){
            case 0:
            {
                System.out.println("Create Sensor ");
                System.out.println("Input sensor name: ");
                String name = scanner.nextLine();
                System.out.println("Input device id: ");
                String device_id = scanner.nextLine();
                System.out.println("Input sensorType (ANALOG or DIGITAL): ");
                String type = scanner.nextLine();
                System.out.println("Input pin number: ");
                String pin = scanner.nextLine();
                String respons  = server.create_Sensor(token, name, Integer.parseInt(device_id), type, pin);
                System.out.println(respons);
            }
                break;
            case 1:
            {   
                System.out.println("Create Device ");
                System.out.println("Input device name: ");
                String name = scanner.nextLine();
                System.out.println("Input external device id: ");
                String device_id = scanner.nextLine();
                String respons  = server.create_Device(token, 
                                                    name, 
                                                    Integer.parseInt(device_id), 
                                                    owner);
                System.out.println(respons);    
            }
                break;
            
            case 2:
            {
                System.out.println("Change Sensor ");
                System.out.println("Which sensor: ");
                String sensor_id = scanner.nextLine();
                System.out.println("Change what device "
                        + "sensor is connected to: ");
                String device_id = scanner.nextLine();
                System.out.println("Change sensor type: ");
                String type = scanner.nextLine();
                System.out.println("Change sensor pin: ");
                String pin = scanner.nextLine();
                System.out.println("Change sensor name: ");
                String name = scanner.nextLine();
                String respons  = server.set_Sensor_Info(token, 
                                                Integer.parseInt(sensor_id),
                                                Integer.parseInt(device_id), 
                                                Integer.parseInt(type), 
                                                Integer.parseInt(pin), name);
                System.out.println(respons);
            }
                break;
                
            case 3:
            {
                System.out.println("Change Device ");
                System.out.println("Which device: ");
                String device_id = scanner.nextLine();
                System.out.println("Change device name: ");
                String name = scanner.nextLine();
                System.out.println("Change device owner: ");
                String owner2 = scanner.nextLine();
                String respons  = server.set_Device_Info(token,  Integer.parseInt(device_id), owner2, name);
                
            }
                break;
            
            case 4:
            {
                System.out.println("Change Device ");
                System.out.println("Which device: ");
                String device_id = scanner.nextLine();
                System.out.println("Change device name: ");
                String name = scanner.nextLine();
                System.out.println("Change device owner: ");
                String owner2 = scanner.nextLine();
                String respons  = server.set_Device_Info(token,  Integer.parseInt(device_id), owner2, name);
                
            }
                break;
        }
    }
    public static void get_sensor_info (SensorSystemInterface server, int token, String owner, int sensor_id)
    {
        String[] info = server.get_Sensor_Info(token, sensor_id);
        System.out.println("Sensor id: " + info[0]);
        System.out.println("Name: " + info[1]);
        System.out.println("Device id: " + info[2]);
        System.out.println("Type: "+ info[3]);
        System.out.println("Pin: "+ info[0]);
        System.out.println("Update time(minutes): "+ info[0]);
        System.out.println("Last Active Date: "+ info[0]);
        System.out.println("Created Date: "+ info[0]);
    }
    public static void get_device_info (SensorSystemInterface server, int token, String owner, int device_id){
        String[] info = server.get_Sensor_Info(token, device_id);       
    }
    
}