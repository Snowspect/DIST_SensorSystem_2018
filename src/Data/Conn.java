/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author tooth
 */
public class Conn 
{
    static final String USER = "Dist"; //dan    
    static final String PASS = "1234"; //itElektronik2019!     
    static final String DRIVER = "org.mariadb.jdbc.Driver"; //com.mysql.jdbc.Driver //org.mariadb.jdbc.Driver
    static final String DATABASE = "jdbc:mariadb://159.89.134.40:22/sensorsystem"; //jdbc:mysql://128.76.255.24:25800/question    //jdbc:mysql://localhost:3306/question2´    
}