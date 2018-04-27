/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dist_sensorsystem_2018;

import static Data.Device_DTO.device_CreateDevice;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author tooth
 */
public class DIST_SensorSystem_2018 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
        try{
            System.out.println("hellooooo1");
                            Date dt = new Date();
                Timestamp dx = new Timestamp(dt.getTime());
            device_CreateDevice(2,"test1","Dan", dx, dx);
        }
        catch(Exception e)
        {
            System.out.println("hejsa");
            e.printStackTrace();
        }
        System.out.println("Helllloo");

    }    
}
