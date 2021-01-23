/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

/**
 *
 * @author Rizki Trisna
 */
public class Transaksi {

    private static int id_transaksi;
    private static int id_barang;
    private static int qty;
    private static String tanggal_pembelian;
    private static int subtotal;
    private static int total;
    private static int id_admin;

    /**
     * @return the subtotal
     */
    public static int getSubtotal() {
        return subtotal;
    }

    /**
     * @param aSubtotal the subtotal to set
     */
    public static void setSubtotal(int aSubtotal) {
        subtotal = aSubtotal;
    }

    /**
     * @return the total
     */
    public static int getTotal() {
        return total;
    }

    /**
     * @param aTotal the total to set
     */
    public static void setTotal(int aTotal) {
        total = aTotal;
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

    private Transaksi(int id_transaksi, int id_barang, int id_admin, int qty, String tanggal_pembelian) {
        setId_transaksi(id_transaksi);
        setId_barang(id_barang);
        setId_admin(id_admin);
        setQty(qty);
        setTanggal_pembelian(tanggal_pembelian);
    }

    public static Transaksi getTransaksi() {
        return new Transaksi(getId_transaksi(), getId_barang(), getId_admin(), getQty(), getTanggal_pembelian());
    }

    /**
     * @return the id_transaksi
     */
    public static int getId_transaksi() {
        return id_transaksi;
    }

    /**
     * @param aId_transaksi the id_transaksi to set
     */
    public static void setId_transaksi(int aId_transaksi) {
        id_transaksi = aId_transaksi;
    }

    /**
     * @return the id_barang
     */
    public static int getId_barang() {
        return id_barang;
    }

    /**
     * @param aId_barang the id_barang to set
     */
    public static void setId_barang(int aId_barang) {
        id_barang = aId_barang;
    }

    /**
     * @return the qty
     */
    public static int getQty() {
        return qty;
    }

    /**
     * @param aQty the qty to set
     */
    public static void setQty(int aQty) {
        qty = aQty;
    }

    /**
     * @return the tanggal_pembelian
     */
    public static String getTanggal_pembelian() {
        return tanggal_pembelian;
    }

    /**
     * @param aTanggal_pembelian the tanggal_pembelian to set
     */
    public static void setTanggal_pembelian(String aTanggal_pembelian) {
        tanggal_pembelian = aTanggal_pembelian;
    }

}
