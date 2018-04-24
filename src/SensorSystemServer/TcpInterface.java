/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SensorSystemServer;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Bruger
 */
@WebService 
public interface TcpInterface {
    
    @WebMethod public void oploadData();
    @WebMethod public void opdateDevice();
    
}
