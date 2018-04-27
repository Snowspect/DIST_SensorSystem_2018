/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dist_sensorsystem_2018;

import static Data.Device_DTO.device_CreateDevice;
import static Data.Sensor_DTO.sensor_CreateSensor;
import SensorSystemServer.SensorSystemImplements;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
            //device_CreateDevice(2,"3","42", dx, dx);
            //sensor_CreateSensor("sensor1", 3, "DIGITAL", "36", dx, dx, "4");
            SensorSystemImplements d = new SensorSystemImplements();
            ArrayList<ArrayList<Integer>> _ids = d.get_ids(42);
            if(_ids == null)
            {
                System.out.println("Her er ingen information");
            }
            else{
                System.out.println("");
                for (ArrayList<Integer> _id : _ids) {
                    for (Integer integer : _id) {
                        System.out.println(_id);
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("hejsa");
            e.printStackTrace();
        }
        System.out.println("Helllloo");
    }
}