/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Rizki Trisna
 */
public class koneksi {

    private static Connection mysqlconfig;

    public static Connection getConnection() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3306/acc_kasir";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url, user, pass);
             
        } catch (Exception e) { 
            System.out.println("Koneksi gagal: " + e.getMessage());
        }

        return mysqlconfig;
    }

}
