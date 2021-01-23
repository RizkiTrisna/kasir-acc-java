/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class session {

    private static int id_admin;
    private static int id_session;
    private static String waktu_login;
    private static String waktu_logout;
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    private static void getKoneksi() {
        try {
            koneksi kon = new koneksi();
            conn = kon.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FrameInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the id_admin
     */
    public static int getId_admin() {
        return id_admin;
    }

    /**
     * @param aId_admin the id_admin to set
     */
    public static void setId_admin(int aId_admin) {
        id_admin = aId_admin;
    }

    /**
     * @return the id_session
     */
    public static int getId_session() {
        return id_session;
    }

    /**
     * @param aId_session the id_session to set
     */
    public static void setId_session(int aId_session) {
        id_session = aId_session;
    }

    /**
     * @return the waktu_login
     */
    public static String getWaktu_login() {
        return waktu_login;
    }

    /**
     * @param aWaktu_login the waktu_login to set
     */
    public static void setWaktu_login(String aWaktu_login) {
        waktu_login = aWaktu_login;
    }

    /**
     * @return the waktu_logout
     */
    public static String getWaktu_logout() {
        return waktu_logout;
    }

    /**
     * @param aWaktu_logout the waktu_logout to set
     */
    public static void setWaktu_logout(String aWaktu_logout) {
        waktu_logout = aWaktu_logout;
    }

    public session() {
        getSession();
    }

    public session(int id_session, int id_admin, String waktu_login, String waktu_logout) {
        setId_session(id_session);
        setId_admin(id_admin);
        setWaktu_login(waktu_login);
        setWaktu_logout(waktu_logout);
    }

    public static boolean setSession(int id_admin) {

        try {

            getKoneksi();

            if (countRowSession() > 100) {
                // hapus semua session tersimpan
                String query = "TRUNCATE TABLE session";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.execute();

                // tambahkan session baru
                query = "Insert into session(id_admin, status_login) values(" + id_admin + ", 1)";
                pst = conn.prepareStatement(query);
                pst.execute();
                return true;
            } else {
                String query = "Insert into session(id_admin, status_login) values(" + id_admin + ", 1)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.execute();
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to create session");
            e.printStackTrace();
        }
        return false;
    }

    public static int countRowSession() {
        int row = 0;
        try {
            getKoneksi();
            stmt = conn.createStatement();

            String query = "Select count(*) as banyak_session from session";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                row = rs.getInt("banyak_session");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    public void loadSession() {
        session obj = getSession();
        setId_session(obj.getId_session());
        setId_admin(obj.getId_admin());
        setWaktu_login(obj.getWaktu_login());
        setWaktu_logout(obj.getWaktu_logout());
    }

    public static session getSession() {
        int id_admin = 0;
        int id_session = 0;
        String waktu_login = "";
        String waktu_logout = "";
        try {
            getKoneksi();
            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select * from session order by id_session desc limit 1";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                id_admin = rs.getInt("id_admin");
                id_session = rs.getInt("id_session");
                waktu_login = rs.getString("waktu_login");
                waktu_logout = rs.getString("waktu_logout");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to getSession\n Error message: " + e);
            e.printStackTrace();
        }
        return new session(id_session, id_admin, waktu_login, waktu_logout);
    }

    public static boolean endSession() {
        try {
            getKoneksi();
            session obj = getSession();
            String query = "UPDATE session SET waktu_logout=current_timestamp(), status_login=0 where id_session=" + obj.getId_session();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to End Current Session\n Error Message: " + e);
            return false;
        }
    }

    public static boolean endSession(int id_session) {
        try {
            getKoneksi();
            session obj = getSession();
            String query = "UPDATE session SET waktu_logout=current_timestamp(), status_login=0 where id_session=" + id_session;
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to End Current Session\n Error Message: " + e);
            return false;
        }
    }
}
