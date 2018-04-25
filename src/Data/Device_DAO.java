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
public class Device_DAO {
    public int id_Device = 0;
    public String device_Name = "john doe";
    public String device_Owner = "john doe 2";
    public String last_Active_Date = "";
    public String created_Date = "";
    
    public String toString(){
        String ret = "ID:" + this.id_Device +
                    "_NAME:" + this.device_Name +
                    "_OWNER:" + this.device_Owner +
                    "_LAD:" + this.last_Active_Date +
                    "_CD:" + this.created_Date +
                    "!";
        return ret;
    }
}