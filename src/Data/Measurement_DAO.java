/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author tooth
 */
public class Measurement_DAO {
    int id_measurement = 0;
    int sensor_id_ref = 0;
    String data = "";
    long data_created_date = 0;
    
    
    @Override
    public String toString(){
        return id_measurement + ":" + sensor_id_ref + ":" + data + ":" + data_created_date;
    }
}
