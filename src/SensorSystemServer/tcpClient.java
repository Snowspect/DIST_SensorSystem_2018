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
public class tcpClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] arg) throws Exception
    {

        //URL url = new URL("http://localhost:9902/SensorTcpService?wsdl");

        URL url = new URL("http://165.227.232.158:9902/SensorTcpService?wsdl");
        QName qname = new QName("http://SensorSystemServer/", "TcpImplementsService");
        Service service = Service.create(url, qname);
        TcpInterface s = service.getPort(TcpInterface.class);

        s.oploadData(2, 3044);
    }
}