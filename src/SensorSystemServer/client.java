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

        System.out.println("111");
        
        int id = s.login("s164916", "FEDpik");
        System.out.println("f you");
        String str = s.create_Device(42, "COOCK");
        System.out.println(str);
    }
    
}