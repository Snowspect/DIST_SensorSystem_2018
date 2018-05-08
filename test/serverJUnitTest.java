/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import SensorSystemServer.SensorSystemInterface;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
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
            //url = new URL("http://165.227.232.158:9901/SensorSystemService?wsdl");
            url = new URL("http://localhost:9901/SensorSystemService?wsdl");
            qname = new QName("http://SensorSystemServer/", "SensorSystemImplementsService");
            service = Service.create(url, qname);
            server = service.getPort(SensorSystemInterface.class);
        } catch (MalformedURLException ex) {
            Logger.getLogger(serverJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        token = server.login("s164916", "FEDpik");
    }
    
    @After
    public void tearDown() {
        token = 0;
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
    public void test_Device()
    {
        String ret = server.create_Device(token, "test_device", 42, "s164916");
        assertTrue("Device failed to create "+ret, Objects.equals(ret, "device_created"));
        ret = server.create_Device(token+1, "test_device", 42, "s164916");
        assertTrue("wrong token failed "+ret, ret == null);
        ret = server.create_Device(token, "test_device", 42, "s364916");
        assertTrue("wrong user failed "+ret, ret == null);
        
    }
    
}
