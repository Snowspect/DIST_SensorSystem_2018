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
        
        SensorSystemInterface SSImpl = new SensorSystemImplements();
        TcpInterface tcpImpl = new TcpImplements();
        String address1 = "http://[::]:9901/SensorSystemService";
        String address2 = "http://[::]:9902/SensorTcpService";
        
        Endpoint.publish(address1, SSImpl);
        Endpoint.publish(address2, tcpImpl);
        System.out.println("Starting server service..");
    }
    
}
