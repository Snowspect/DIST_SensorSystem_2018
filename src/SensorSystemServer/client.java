/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import java.net.URL;
import java.util.ArrayList;
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

        Scanner reader = new Scanner(System.in);
        URL url = new URL("http://178.62.85.246:9901/SensorSystemService?wsdl");
        QName qname = new QName("http://SensorSystemServer/", "SensorSystemImplementsService");
        Service service = Service.create(url, qname);
        SensorSystemInterface s = service.getPort(SensorSystemInterface.class);


        int id = login(s, reader);
        
        System.out.print(id);
        
        ArrayList<ArrayList<Integer>> ids = s.get_ids(id);
        
        if(ids.isEmpty())
            return;
        System.out.print(ids);
    }
    
    public static int login(SensorSystemInterface server, Scanner s) {
        int id = 0;
        while(id == 0) {
            
            System.out.println("Input username:");
            String username = s.nextLine();
            System.out.println("Input password:");
            String password = s.nextLine();
            
            try {
                System.out.println("HEJ");
                id = server.login(username, password);
            } catch (Exception e) {
                System.out.println("Login failed..");
            }
        }
        return id;
    }    
        
    
}
