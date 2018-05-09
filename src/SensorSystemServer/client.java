/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            display(server, token, owner);
            menu();
            System.out.println("Input:");
            int i = Integer.parseInt(scanner.nextLine());
            if(i == 0) break;
            options(server, token, owner, i, scanner);
        }
    }
    
    public static HashMap<Integer, int[]> get_ids(SensorSystemInterface server, 
                                                int id, 
                                                String owner)
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
    
    public static void display(SensorSystemInterface server, 
                                int token, 
                                String owner)
    {
  
        int[] device_IDs = server.get_Devices_ID(token, owner);
        if(device_IDs == null) return;
        System.out.println("Device - Sensors");
        for (int i = 0; i < device_IDs.length; i++) 
        {
            String pstm = device_IDs[i]+" : ";
            int sensor_ids[] = server.get_Sensors_ID(token, device_IDs[i]);
            if(sensor_ids != null)
            if(sensor_ids.length > 0 ){
                for (int j = 0; j < sensor_ids.length; j++) {
                    pstm = pstm + sensor_ids[j]+ " - ";
                }
            }
                
            System.out.println(pstm);
            
        }
    }
    
    public static void menu()
    {
        System.out.println("Choose Action \n"
                            +"0 \t exit \n"
                            +"1 \t Create Sensor \n"    
                            +"2 \t Create Device \n"    
                            +"3 \t Change Sensor \n"    
                            +"4 \t Change Device \n"
                            +"5 \t Delete Sensor \n"
                            +"6 \t Delete Device \n"
                            +"7 \t Sensor Info \n"
                            +"8 \t Device Info \n"
                            +"9 \t Print Sensor Data to file.\n" 
                            +"10 \t Print Sensor Data to file within dates. \n"
                              
        );
    }
    
    public static void options(SensorSystemInterface server, 
                                int token, 
                                String owner, 
                                int choice, 
                                Scanner scanner)
    {
        switch(choice){
            case 1:
            {
                System.out.println("Create Sensor");
                System.out.println("Input sensor name (type exit to cancel): ");
                String s = scanner.nextLine();
                if(s.equals("exit")) return;
                String name = s;
                System.out.println("Input device id (type exit to cancel): ");
                s = scanner.nextLine();
                if(s.equals("exit")) return;
                int device_id = Integer.parseInt(s);
                System.out.println("Input sensorType (ANALOG = 1 or DIGITAL = 0)(type exit to cancel): ");
                s = scanner.nextLine();
                if(s.equals("exit")) return;
                int type = Integer.parseInt(s);
                System.out.println("Input pin number (type exit to cancel): ");
                s = scanner.nextLine();
                if(s.equals("exit")) return;
                int pin = Integer.parseInt(s);
                String respons  = server.create_Sensor(token, name, device_id, type, pin);
                if( respons.isEmpty()) System.out.println("NULL was returned");
                else System.out.println(respons);
            }
                break;
            case 2:
            {   
                System.out.println("Create Device ");
                System.out.println("Input device name (type exit to cancel): ");
                String s = scanner.nextLine();
                if(s.equals("exit")) return;                
                String name = s;
                System.out.println("Input external device id (type exit to cancel): ");
                s = scanner.nextLine();
                if(s.equals("exit")) return;
                String device_id = s;
                String respons  = server.create_Device(token, 
                                                    name, 
                                                    Integer.parseInt(device_id), 
                                                    owner);
                if( respons.isEmpty()) System.out.println("NULL was returned");
                else System.out.println(respons);    
            }
                break;
            
            case 3:
            {
                System.out.println("Change Sensor ");
                System.out.println("Which sensor (type exit to cancel): ");
                String s = scanner.nextLine();
                if(s.equals("exit")) return;
                String sensor_id = s;
                System.out.println("Change what device sensor is connected to (type exit to cancel): ");
                s = scanner.nextLine();
                if(s.equals("exit")) return;
                String device_id = s;
                System.out.println("Change sensor type (type exit to cancel): ");
                s = scanner.nextLine();
                if(s.equals("exit")) return;
                String type = s;
                System.out.println("Change sensor pin (type exit to cancel): ");
                s = scanner.nextLine();
                if(s.equals("exit")) return;
                String pin = s;
                System.out.println("Change sensor name (type exit to cancel): ");
                s = scanner.nextLine();
                if(s.equals("exit")) return;
                String name = s;
                String respons  = server.set_Sensor_Info(token, 
                                                Integer.parseInt(sensor_id),
                                                Integer.parseInt(device_id), 
                                                Integer.parseInt(type), 
                                                Integer.parseInt(pin), name);
                if( respons.isEmpty()) System.out.println("NULL was returned");
                else System.out.println(respons);
            }
                break;
                
            case 4:
            {
                System.out.println("Change Device ");
                System.out.println("Which device (type exit to cancel): ");
                String device_id = scanner.nextLine();
                System.out.println("Change device name (type exit to cancel): ");
                String name = scanner.nextLine();
                System.out.println("Change device owner (type exit to cancel): ");
                String owner2 = scanner.nextLine();
                String respons  = server.set_Device_Info(token,  Integer.parseInt(device_id), owner2, name);
                if( respons.isEmpty()) System.out.println("NULL was returned");
                else System.out.println(respons);
            }
                break;
            case 5:
            {
                System.out.println("Delete Sensor. ");
                System.out.println("Choose sensor id (type exit to cancel):");
                String s = scanner.nextLine();
                if(s.equals("exit")) return;
                String respons = server.delete_Sensor(token, Integer.parseInt(s));
                if( respons.isEmpty()) System.out.println("NULL was returned");
                else System.out.println(respons);
            }
                break;
            
            case 6:
            {
                System.out.println("Delete Device. ");
                System.out.println("Choose device id (type exit to cancel):");
                
                String s = scanner.nextLine();
                if(s.equals("exit")) return;
                String respons = server.delete_Device(token, Integer.parseInt(s));
                if( respons.isEmpty()) System.out.println("NULL was returned");
                else System.out.println(respons);
            }
                break;
            
            case 7:
            {
                System.out.println("Show sensor info. ");
                System.out.println("Choose sensor id (type exit to cancel):");
                String s = scanner.nextLine();
                if(s.equals("exit")) return;
                print_sensor_info(server, token, owner, Integer.parseInt(s));
            }
                break;
            
            case 8:
            {
                System.out.println("Show device info. ");
                System.out.println("Choose device id (type exit to cancel): ");
                String s = scanner.nextLine();
                if(s.equals("exit")) return;
                print_device_info(server, token, owner, Integer.parseInt(s));
            }
                break;
            
            case 9:
            {
                System.out.println("Print Sensor Data to file. ");
                System.out.println("Choose sensor id (type exit to cancel): ");
                String s = scanner.nextLine();
                if(s.equals("exit")) return;
                System.out.println("Choose name(eg. test.txt): ");
                String name = scanner.nextLine();
                
                String[] info = server.get_Sensor_Info(token, Integer.parseInt(s));
                if(info.length == 0 ) break;
                String[] sd = server.get_Sensor_Data(token, Integer.parseInt(s));
                if(sd.length == 0) break;
                BufferedWriter writer = null;
                File outputFile = new File(name);
                System.out.println("File path: ");
                                
                try {
                    System.out.println(outputFile.getCanonicalPath());
                    writer = new BufferedWriter(new FileWriter(outputFile));
                    
                    for(String item : info)
                    {
                        writer.write(item+"\n");
                    }
                    writer.write("Data:"+"\n");
                    for (String item : sd) {
                        writer.write(item+"\n");
                    }
      
                } catch (IOException ex) {
                    Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
            case 10:
            {
                System.out.println("Print Sensor Data to file within dates. ");
                System.out.println("Choose sensor id (type exit to cancel): ");
                String s = scanner.nextLine();
                if(s.equals("exit")) return;
                String sensor_id = s;
                System.out.println("Choose name(eg. test.txt) (type exit to cancel): ");
                s = scanner.nextLine();
                if(s.equals("exit")) return;
                String name = s;
                System.out.println("Choose Old date: ");
                Date dateOld = chooseDate(scanner);
                Timestamp tpOld = new Timestamp(dateOld.getTime());
                System.out.println("Choose New date: ");
                Date dateNew = chooseDate(scanner);
                Timestamp tpNew = new Timestamp(dateNew.getTime()); 

                String[] info = server.get_Sensor_Info(token, Integer.parseInt(sensor_id));
                if(info.length == 0 ) break;
                String[] sd = server.get_Sensor_Data_Within_Dates(token, Integer.parseInt(sensor_id), dateOld, dateNew);
                if(sd.length == 0) break;
                BufferedWriter writer = null;
                File outputFile = new File(name);
                System.out.println("File path: ");
                                
                try {
                    System.out.println(outputFile.getCanonicalPath());
                    writer = new BufferedWriter(new FileWriter(outputFile));
                    writer.write("Time: "+dateOld.toString()+" : "+dateNew.toString());
                    for(String item : info)
                    {
                        writer.write(item);
                    }
                    for (String item : sd) {
                        writer.write(item);
                    }
      
                } catch (IOException ex) {
                    Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
            
        }
    }
    
    private static Date chooseDate(Scanner s)
    {
        System.out.println("Choose year(the year minus 1900): ");
        int year = Integer.parseInt(s.nextLine());
        System.out.println("Choose month(the month between 0-11): ");
        int month = Integer.parseInt(s.nextLine());
        System.out.println("Choose day(the day of the month between 1-31): ");
        int date = Integer.parseInt(s.nextLine());
        return new Date(year, month, date);
    }
    
    public static void print_sensor_info (SensorSystemInterface server, 
                                        int token, 
                                        String owner, 
                                        int sensor_id)
    {
        String[] info = server.get_Sensor_Info(token, sensor_id);
        if(info == null || info.length == 0) return;
        System.out.println("Sensor id: " + info[0]);
        System.out.println("Name: " + info[1]);
        System.out.println("Device id: " + info[2]);
        System.out.println("Type: "+ info[3]);
        System.out.println("Pin: "+ info[4]);
        System.out.println("Update time(minutes): "+ info[5]);
        System.out.println("Last Active Date: "+ info[6]);
        System.out.println("Created Date: "+ info[7]);
    }
    
    public static void print_device_info (SensorSystemInterface server, 
                                        int token, 
                                        String owner, 
                                        int device_id)
    {
        String[] info = server.get_Device_Info(token, device_id);
        System.out.println("Device id: " + info[0]);
        System.out.println("External id: " + info[1]);
        System.out.println("Name: " + info[2]);
        System.out.println("Owner: "+ info[3]);
        System.out.println("Last Active Date: "+ info[4]);
        System.out.println("Created Date: "+ info[5]);
    }
    
}