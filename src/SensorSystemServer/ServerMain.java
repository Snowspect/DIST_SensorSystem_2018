/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.xml.ws.Endpoint;

/**
 *
 * @author Bruger
 */
public class ServerMain {
    private static final Logger LOGGER = Logger.getLogger( ServerMain.class.getName() );
    public static void main(String[] args){        
        SensorSystemInterface SSImpl = new SensorSystemImplements();
        TcpInterface tcpImpl = new TcpImplements();
        String address1 = "http://[::]:9901/SensorSystemService";
        String address2 = "http://[::]:9902/SensorTcpService";
        FileHandler fh;
        
        try {
            fh = new FileHandler("/root/log/Serverlog.log");
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException | SecurityException ex) {
            LOGGER.log( Level.SEVERE, ex.toString(), ex );
        }
        
        Endpoint.publish(address1, SSImpl);
        Endpoint.publish(address2, tcpImpl);
        System.out.println("Starting server service..");
        while(true){
        SSImpl.opdateActiveUsers();
            try {
                Thread.sleep(300000);
            } catch (InterruptedException ex) {
                LOGGER.log( Level.SEVERE, ex.toString(), ex );
            }
        }
        
    }
    
}
