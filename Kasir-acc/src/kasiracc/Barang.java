/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

import java.util.ArrayList;

/**
 *
 * @author Rizki Trisna
 */
public class Barang {

    private static int id_barang = 0;
    private static String nama_jenis_barang;
    private static int id_jenis_barang;
    private static String nama_barang;
    private static int harga_jual;
    private static int harga_pokok;
    private static int stok;

    public Barang() {

    }

    public Barang(int id_barang, String nama_jenis_barang, int id_jenis_barang, String nama_barang, int harga_jual, int harga_pokok, int stok) {
        setId_barang(id_barang);
        setNama_jenis_barang(nama_jenis_barang);
        setId_jenis_barang(id_jenis_barang);
        setNama_barang(nama_barang);
        setHarga_jual(harga_jual);
        setHarga_pokok(harga_pokok);
        setStok(stok);
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
     * @return the nama_jenis_barang
     */
    public static String getNama_jenis_barang() {
        return nama_jenis_barang;
    }

    /**
     * @param aNama_jenis_barang the nama_jenis_barang to set
     */
    public static void setNama_jenis_barang(String aNama_jenis_barang) {
        nama_jenis_barang = aNama_jenis_barang;
    }

    /**
     * @return the id_jenis_barang
     */
    public static int getId_jenis_barang() {
        return id_jenis_barang;
    }

    /**
     * @param aId_jenis_barang the id_jenis_barang to set
     */
    public static void setId_jenis_barang(int aId_jenis_barang) {
        id_jenis_barang = aId_jenis_barang;
    }

    /**
     * @return the nama_barang
     */
    public static String getNama_barang() {
        return nama_barang;
    }

    /**
     * @param aNama_barang the nama_barang to set
     */
    public static void setNama_barang(String aNama_barang) {
        nama_barang = aNama_barang;
    }

    /**
     * @return the harga_jual
     */
    public static int getHarga_jual() {
        return harga_jual;
    }

    /**
     * @param aHarga_jual the harga_jual to set
     */
    public static void setHarga_jual(int aHarga_jual) {
        harga_jual = aHarga_jual;
    }

    /**
     * @return the harga_pokok
     */
    public static int getHarga_pokok() {
        return harga_pokok;
    }

    /**
     * @param aHarga_pokok the harga_pokok to set
     */
    public static void setHarga_pokok(int aHarga_pokok) {
        harga_pokok = aHarga_pokok;
    }

    /**
     * @return the stok
     */
    public static int getStok() {
        return stok;
    }

    /**
     * @param aStok the stok to set
     */
    public static void setStok(int aStok) {
        stok = aStok;
    }

}
