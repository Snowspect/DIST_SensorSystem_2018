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
    static final String CONNECTION_STRING = "jdbc:mariadb://178.62.85.246:3306/sensorsystem?user=Dist&password=*A4B6157319038724E3560894F7F932C8886EBFCF";
}