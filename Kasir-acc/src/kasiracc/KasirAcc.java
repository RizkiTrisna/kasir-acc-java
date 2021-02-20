/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Rizki Trisna
 */
public class KasirAcc {

    /**
     * @param args the command line arguments
     */
    // TODO code application logic here
    public static void main(String[] args) throws SQLException {
        koneksi kn = new koneksi();
        Connection con = kn.getConnection();
        if (con == null) {
            JOptionPane.showMessageDialog(null, "Terjadi kegagalan saat mengambil data MYSQL");
        } else {
            FrameLogin.main(args);            
        }
    }

}
