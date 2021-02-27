/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Rizki Trisna
 */
public class koneksi {

    private static Connection mysqlconfig;

    public static Connection getConnection() throws SQLException {
        try {
            String db_name = "acc_kasir";
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url, user, pass);

            //sandbox
            ResultSet res = null;
            res = mysqlconfig.getMetaData().getCatalogs();
            Statement stmt = null;
            boolean find = false;

            while (res.next()) {
                String catalog = res.getString(1);
                if (db_name.equals(catalog)) {
                    find = true;
                    break;
                }
            }

            if (find == false) {
                // jika database tidak bisa ditemukan, maka buat databse bseserta tabelnya
                //Create database acc_kasir
                System.out.println("Creating new database...");
                try {
                    stmt = mysqlconfig.createStatement();
                    String sql = "CREATE DATABASE " + db_name;
                    stmt.executeUpdate(sql);
                    System.out.println("New database created");
                } catch (SQLException e) {
                    System.out.println("Failed to create databse\n" + e.getMessage());
                }

                //Create table
                System.out.println("Creating new table...");
                try {
                    url = "jdbc:mysql://localhost:3306/" + db_name;
                    user = "root";
                    pass = "";
                    DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                    mysqlconfig = DriverManager.getConnection(url, user, pass);
                    String[] query_list = {
                        "CREATE TABLE `admin` ("
                        + "  `id_admin` int(11) NOT NULL,"
                        + "  `username` varchar(150) NOT NULL,"
                        + "  `password` varchar(80) NOT NULL,"
                        + "  `nama_admin` varchar(100) NOT NULL"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
                        "INSERT INTO `admin` (`id_admin`, `username`, `password`, `nama_admin`) VALUES"
                        + "(1, 'admin', 'admin', 'Admin');",
                        "CREATE TABLE `barang` ("
                        + "  `id_barang` int(11) NOT NULL,"
                        + "  `id_jenis_barang` int(11) NOT NULL,"
                        + "  `nama_barang` varchar(175) NOT NULL,"
                        + "  `harga_jual` int(11) NOT NULL,"
                        + "  `harga_pokok` int(11) NOT NULL,"
                        + "  `stok_barang` int(11) NOT NULL,"
                        + "  `input_date` timestamp NOT NULL DEFAULT current_timestamp(),"
                        + "  `update_date` timestamp NOT NULL DEFAULT current_timestamp()"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
                        "CREATE TABLE `checkout` ("
                        + "`id_temp` int(11)"
                        + ",`id_admin` int(11)"
                        + ",`nama_admin` varchar(100)"
                        + ",`id_barang` int(11)"
                        + ",`nama_barang` varchar(175)"
                        + ",`qty` int(11)"
                        + ",`harga_jual` int(11)"
                        + ",`subtotal` bigint(21)"
                        + ",`tanggal_pembelian` timestamp"
                        + ");",
                        "CREATE TABLE `jenis_barang` ("
                        + "  `id_jenis` int(11) NOT NULL,"
                        + "  `nama_jenis` varchar(150) NOT NULL"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
                        "CREATE TABLE `session` ("
                        + "  `id_session` int(11) NOT NULL,"
                        + "  `id_admin` int(11) NOT NULL,"
                        + "  `waktu_login` timestamp NOT NULL DEFAULT current_timestamp(),"
                        + "  `waktu_logout` timestamp NOT NULL DEFAULT current_timestamp(),"
                        + "  `status_login` int(11) NOT NULL"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
                        "CREATE TABLE `temp_transaksi` ("
                        + "  `id_temp` int(11) NOT NULL,"
                        + "  `id_barang` int(11) NOT NULL,"
                        + "  `id_admin` int(11) NOT NULL,"
                        + "  `qty` int(11) NOT NULL,"
                        + "  `tanggal_pembelian` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
                        "CREATE TABLE `transaksi` ("
                        + "  `id_transaksi` int(11) NOT NULL,"
                        + "  `id_barang` int(11) NOT NULL,"
                        + "  `id_admin` int(11) NOT NULL,"
                        + "  `qty` int(11) NOT NULL,"
                        + "  `tanggal_pembelian` timestamp NOT NULL DEFAULT current_timestamp()"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
                        "DROP TABLE IF EXISTS `checkout`;",
                        "CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `checkout`  AS  select `temp_transaksi`.`id_temp` AS `id_temp`,`temp_transaksi`.`id_admin` AS `id_admin`,`admin`.`nama_admin` AS `nama_admin`,`temp_transaksi`.`id_barang` AS `id_barang`,`barang`.`nama_barang` AS `nama_barang`,`temp_transaksi`.`qty` AS `qty`,`barang`.`harga_jual` AS `harga_jual`,`barang`.`harga_jual` * `temp_transaksi`.`qty` AS `subtotal`,`temp_transaksi`.`tanggal_pembelian` AS `tanggal_pembelian` from ((`temp_transaksi` join `barang`) join `admin`) where `barang`.`id_barang` = `temp_transaksi`.`id_barang` and `admin`.`id_admin` = `temp_transaksi`.`id_admin` ;",
                        "ALTER TABLE `admin`"
                        + "  ADD PRIMARY KEY (`id_admin`),"
                        + "  ADD UNIQUE KEY `username` (`username`);",
                        "ALTER TABLE `barang`"
                        + "  ADD PRIMARY KEY (`id_barang`),"
                        + "  ADD KEY `id_jenis_barang` (`id_jenis_barang`);",
                        "ALTER TABLE `jenis_barang`"
                        + "  ADD PRIMARY KEY (`id_jenis`);",
                        "ALTER TABLE `session`"
                        + "  ADD PRIMARY KEY (`id_session`),"
                        + "  ADD KEY `id_admin` (`id_admin`);",
                        "ALTER TABLE `temp_transaksi`"
                        + "  ADD PRIMARY KEY (`id_temp`),"
                        + "  ADD KEY `id_barang` (`id_barang`),"
                        + "  ADD KEY `id_admin` (`id_admin`);",
                        "ALTER TABLE `transaksi`"
                        + "  ADD PRIMARY KEY (`id_transaksi`),"
                        + "  ADD KEY `id_barang` (`id_barang`),"
                        + "  ADD KEY `id_admin` (`id_admin`);",
                        "ALTER TABLE `admin`"
                        + "  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;",
                        "ALTER TABLE `barang`"
                        + "  MODIFY `id_barang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;",
                        "ALTER TABLE `jenis_barang`"
                        + "  MODIFY `id_jenis` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;",
                        "ALTER TABLE `session`"
                        + "  MODIFY `id_session` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;",
                        "ALTER TABLE `temp_transaksi`"
                        + "  MODIFY `id_temp` int(11) NOT NULL AUTO_INCREMENT;",
                        "ALTER TABLE `transaksi`"
                        + "  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;",
                        "ALTER TABLE `barang`"
                        + "  ADD CONSTRAINT `barang_ibfk_1` FOREIGN KEY (`id_jenis_barang`) REFERENCES `jenis_barang` (`id_jenis`);",
                        "ALTER TABLE `session`"
                        + "  ADD CONSTRAINT `session_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`);",
                        "ALTER TABLE `temp_transaksi`"
                        + "  ADD CONSTRAINT `temp_transaksi_ibfk_1` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`),"
                        + "  ADD CONSTRAINT `temp_transaksi_ibfk_2` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`);",
                        "ALTER TABLE `transaksi`"
                        + "  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`id_barang`) REFERENCES `barang` (`id_barang`) ON UPDATE CASCADE,"
                        + "  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`);",};

                    for (int i = 0; i < query_list.length; i++) {
                        stmt = mysqlconfig.createStatement();
                        String sql = query_list[i];
                        stmt.executeUpdate(sql);
                    }
                    System.out.println("New table created");
                } catch (SQLException e) {
                    System.out.println("Failed to create databse" + e.getMessage());
                }
            } else if (find == true) {
                // ketika database ditemukan
                db_name = "acc_kasir";
                url = "jdbc:mysql://localhost:3306/" + db_name;
                user = "root";
                pass = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                mysqlconfig = DriverManager.getConnection(url, user, pass);
            }
            //end of sandbox
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kegagalan saat mengambil data MYSQL" + e.getMessage());
            System.out.println("Koneksi gagal: " + e.getMessage());
            mysqlconfig = null;
        }
        if (mysqlconfig == null) {
            JOptionPane.showMessageDialog(null, "Terjadi kegagalan saat mengambil data MYSQL\nMungkin database belum didaftarkan");
        }
        return mysqlconfig;
    }

//    public static void main(String[] args) {
//        try {
//            getConnection();
//        } catch (SQLException ex) {
//            Logger.getLogger(koneksi.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

}
