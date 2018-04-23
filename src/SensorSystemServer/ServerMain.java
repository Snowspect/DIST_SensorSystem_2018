/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import javax.xml.ws.Endpoint;

/**
 *
 * @author Bruger
 */
public class ServerMain {
    public static void main(String[] args){
        System.out.println("Starting server service..");
        
        SensorSystemImplements SSImpl = new SensorSystemImplements();
        String address = "http://[::]:9901/SensorSystemService";
        
        Endpoint.publish(address, SSImpl);
        System.out.println("Starting server service..");
    }
    
}
