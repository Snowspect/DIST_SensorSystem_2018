/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import java.net.URL;
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
        URL url = new URL("http://165.227.232.158:9901/SensorSystemService?wsdl");
        //URL url = new URL("http://localhost:9901/SensorSystemService?wsdl");
        QName qname = new QName("http://SensorSystemServer/", "SensorSystemImplementsService");
        Service service = Service.create(url, qname);
        SensorSystemInterface s = service.getPort(SensorSystemInterface.class);
        String owner = "s164916";      
        int id = s.login(owner, "FEDpik");
        
        s.create_Device(id, "analprop", id, owner);
        System.out.println("id: " + id);
    }   
}