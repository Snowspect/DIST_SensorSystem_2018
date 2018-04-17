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
    static final String USER = "root"; //dan    
    static final String PASS = ""; //itElektronik2019!     
    static final String DRIVER = "com.mysql.jdbc.Driver"; //com.mysql.jdbc.Driver       
    static final String DATABASE = "jdbc:mysql://localhost:3306/question2"; //jdbc:mysql://128.76.255.24:25800/question    //jdbc:mysql://localhost:3306/question2´    
}