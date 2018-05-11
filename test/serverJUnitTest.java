/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import SensorSystemServer.SensorSystemInterface;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bruger
 */
public class serverJUnitTest {
    static Service service;
    static URL url;
    static QName qname;
    static int token;
    static SensorSystemInterface server;
    
    public serverJUnitTest() {
        setUpClass();
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        try {
            url = new URL("http://165.227.232.158:9901/SensorSystemService?wsdl");
            //url = new URL("http://localhost:9901/SensorSystemService?wsdl");
            qname = new QName("http://SensorSystemServer/", "SensorSystemImplementsService");
            service = Service.create(url, qname);
            server = service.getPort(SensorSystemInterface.class);
            token = server.login("s164916", "FEDpik");
        } catch (MalformedURLException ex) {
            Logger.getLogger(serverJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        int []d_id = server.get_Devices_ID(token, "s164916");
        for (int j = 0; j < d_id.length; j++) {
            int []s_id = server.get_Sensors_ID(token, d_id[j]);
            
            String s = server.delete_Device(token, d_id[j]);
        }
        String name = "test_device";
        String ret = server.create_Device(token, name, 42, "s164916");
        assertTrue("Device failed to create "+ret, Objects.equals(ret, "device_created"));
        d_id = server.get_Devices_ID(token, "s164916");
        name = "john";
        int type = 1;
        int pin = 3;
        ret = server.create_Sensor(token, name, d_id[0], type, pin);
        assertTrue("Sensor failed to create "+ret, Objects.equals(ret, "sensor_created"));
        
    }
    
    @After
    public void tearDown() {
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void validatToken() 
    {
        assertTrue(server.validatToken(token) == token);
          
    }
    
    @Test
    public void device_create_test()
    {
        String name = "test_device";
        
//        String ret = server.create_Device(token, name, 42, "s164916");
//        assertTrue("Wrong ID "+ret, ret == null);
        
        String ret = server.create_Device(token+1, name, 32, "s164916");
        assertTrue("Wrong token failed "+ret, ret == null);
        
        ret = server.create_Device(token, name, 22, "s364916");
        assertTrue("Wrong user failed "+ret, ret == null); 
        
        
    }
    @Test 
    public void sensor_create_test()
    {
        String name = "john";
        int type = 1;
        int pin = 3;
        int[] d_id = server.get_Devices_ID(token, "s164916");
        assertTrue(d_id.length > 0);
        
        String ret = server.create_Sensor(token+1, name, d_id[0], type, pin);
        assertTrue("wrong token failed "+ret, ret == null);
        
        ret = server.create_Sensor(token, name, 1, type, pin);
        assertTrue("wrong device failed "+ret, ret == null);
        
        ret = server.create_Sensor(token, name, d_id[0], 2, pin);
        assertTrue("wrong pin failed "+ret, ret == null);
    }
    @Test 
    public void sensor_edit_test()
    {
        String name = "john";
        int type = 1;
        int pin = 3;
        int[] d_id = server.get_Devices_ID(token, "s164916");
        assertTrue(d_id.length > 0);
        String ret = server.create_Sensor(token, name, d_id[0], type, pin);
        assertTrue("Sensor failed to create "+ret, Objects.equals(ret, "sensor_created"));
        
        int[] s_id = server.get_Sensors_ID(token, d_id[0]);
        
        for (int i = 0; i < s_id.length; i++) {
            String[] si = server.get_Sensor_Info(token, s_id[i]);
            for (String string : si) {
                System.out.println(string);
            }
            assertTrue( si.length > 0);
            assertTrue(Integer.parseInt(si[0]) == s_id[i]);
            assertTrue(si[1].equals(name));
            assertTrue(Integer.parseInt(si[2]) == d_id[0]);
            assertTrue(si[3].equals("ANALOG"));
            assertTrue(Integer.parseInt(si[4]) == pin);
            
            String s = server.set_Sensor_Info(token, s_id[i], d_id[0], type-1, pin-1, "paul");
            
            si = server.get_Sensor_Info(token, s_id[i]);
            for (String string : si) {
                System.out.println(string);
            }
            assertTrue( si.length > 0);
            assertTrue(Integer.parseInt(si[0]) == s_id[i]);
            assertTrue(si[1].equals("paul"));
            assertTrue(Integer.parseInt(si[2]) == d_id[0]);
            assertTrue( si[3].equals("DIGITAL"));
            assertTrue(Integer.parseInt(si[4]) == pin-1);
        }
    }
    
    @Test
    public void device_edit_test()
    {
        String name = "test_device";
        String ret = server.create_Device(token, name, 42, "s164916");
        assertTrue("Device failed to create "+ret, Objects.equals(ret, "device_created"));
        
        int[] d_id = server.get_Devices_ID(token, "s164916");
        assertTrue(d_id.length > 0);
        
        System.out.print("ids: ");
        for (int i = 0; i < d_id.length; i++) {
            System.out.print(d_id[i]+" ");
        }
        System.out.print("\n");
        
        for (int i = 0; i < d_id.length; i++) {
            String[] di = server.get_Device_Info(token, d_id[i]);
            assertTrue(di.length > 0);
            assertTrue(Integer.parseInt(di[0]) == d_id[i]);
            assertTrue(di[2].equals(name));
            assertTrue(di[3].equals("s164916"));
            
            for (String string : di) {
                System.out.println(string);
            }
            String s = server.set_Device_Info(token, Integer.parseInt(di[0]), "s164916", "test");
            assertFalse(s.isEmpty());
            System.out.println(s);
            di = server.get_Device_Info(token, d_id[i]);
            assertTrue(di[2].equals("test"));
        }
    }
    
    
    public void test_delete()
    {
        String name = "test_device";
        String ret = server.create_Device(token, name, 42, "s164916");
        assertTrue("Device failed to create "+ret, Objects.equals(ret, "device_created"));
        int[] d_id = server.get_Devices_ID(token, "s164916");
        int[] s_id;
        d_id = server.get_Devices_ID(token, "s164916");
        assertTrue(d_id.length != 0);
        for (int j = 0; j < d_id.length; j++) {
            s_id = server.get_Sensors_ID(token, d_id[j]);
            
            String s;
            if(s_id.length > 0)
            {
                for (int i = 0; i < s_id.length; i++) {
                    s = server.delete_Sensor(token, s_id[j]);
                    assertTrue(s.equals("Sensor deleted"));
                }
            }
            s = server.delete_Device(token, d_id[j]);
            assertTrue(s.equals("Device deleted"));
        }
    }
    
    @Test
    public void test_System()
    {
        String name = "test_device";
        String ret = server.create_Device(token, name, 42, "s164916");
        assertTrue("Device failed to create "+ret, Objects.equals(ret, "device_created"));
        
        int[] d_id = server.get_Devices_ID(token, "s164916");
        assertTrue(d_id.length > 0);
        
        System.out.print("ids: ");
        for (int i = 0; i < d_id.length; i++) {
            System.out.print(d_id[i]+" ");
        }
        System.out.print("\n");
        
        for (int i = 0; i < d_id.length; i++) {
            String[] di = server.get_Device_Info(token, d_id[i]);
            assertTrue(di.length > 0);
            assertTrue(Integer.parseInt(di[0]) == d_id[i]);
            assertTrue(di[2].equals(name));
            assertTrue(di[3].equals("s164916"));
            
            for (String string : di) {
                System.out.println(string);
            }
            String s = server.set_Device_Info(token, Integer.parseInt(di[0]), "s164916", "test");
            assertFalse(s.isEmpty());
            System.out.println(s);
            di = server.get_Device_Info(token, d_id[i]);
            assertTrue(di[2].equals("test"));
            
        }  
    
        name = "john";
        int type = 1;
        int pin = 3;
        d_id = server.get_Devices_ID(token, "s164916");
        assertTrue(d_id.length > 0);
        ret = server.create_Sensor(token, name, d_id[0], type, pin);
        assertTrue("Sensor failed to create "+ret, Objects.equals(ret, "sensor_created"));
        
        int[] s_id = server.get_Sensors_ID(token, d_id[0]);
        
        for (int i = 0; i < s_id.length; i++) {
            String[] si = server.get_Sensor_Info(token, s_id[i]);
            for (String string : si) {
                System.out.println(string);
            }
            assertTrue( si.length > 0);
            assertTrue(Integer.parseInt(si[0]) == s_id[i]);
            assertTrue(si[1].equals(name));
            assertTrue(Integer.parseInt(si[2]) == d_id[0]);
            assertTrue(si[3].equals("ANALOG"));
            assertTrue(Integer.parseInt(si[4]) == pin);
            
            String s = server.set_Sensor_Info(token, s_id[i], d_id[0], type-1, pin-1, "paul");
            
            si = server.get_Sensor_Info(token, s_id[i]);
            for (String string : si) {
                System.out.println(string);
            }
            assertTrue( si.length > 0);
            assertTrue(Integer.parseInt(si[0]) == s_id[i]);
            assertTrue(si[1].equals("paul"));
            assertTrue(Integer.parseInt(si[2]) == d_id[0]);
            assertTrue( si[3].equals("DIGITAL"));
            assertTrue(Integer.parseInt(si[4]) == pin-1);
        }
        
    
        d_id = server.get_Devices_ID(token, "s164916");
        assertTrue(d_id.length != 0);
        for (int j = 0; j < d_id.length; j++) {
            s_id = server.get_Sensors_ID(token, d_id[j]);
          
            String s = server.delete_Device(token, d_id[j]);
            assertTrue(s.equals("Device deleted"));
        }
    }
    
}
