/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dist_sensorsystem_2018;

import static Data.Device_DTO.device_CreateDevice;
import static Data.Sensor_DTO.sensor_CreateSensor;
import static Data.Device_modi_DTO.device_Pull_all_device_ids;
import SensorSystemServer.SensorSystemImplements;
import static Data.Sensor_modi_DTO.sensor_Pull_Related_SensorIDs;
import static Data.Measurement_DTO.createMeasurement;
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
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here

        try {
            System.out.println("hellooooo1");
            Date dt = new Date();
            Timestamp dx = new Timestamp(dt.getTime());
            //createMeasurement(3, 323223232, dx);
            device_CreateDevice(2,"3","42", dx, dx);
            //sensor_CreateSensor("sensor1", 1, "DIGITAL", "36", dx, dx, "4");
            
/*            ArrayList<Integer> tmp = device_Pull_all_device_ids("42");
            for (Integer arg : tmp) {
                System.out.println(arg);
            }
            ArrayList<ArrayList<Integer>> xt = new ArrayList<>();
            for (Integer integer : tmp) {
                 xt.add((ArrayList<Integer>) sensor_Pull_Related_SensorIDs(integer)); 
                for (ArrayList<Integer> arrayList : xt) {
                    for (Integer integer1 : arrayList) {
                        System.out.println(integer1);
                    }
                }
            }
*/
            
        } catch (Exception e) {
            System.out.println("hejsa");
            e.printStackTrace();
        }
        System.out.println("Helllloo");
    }
}
