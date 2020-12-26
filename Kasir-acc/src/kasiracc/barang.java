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
public class barang {
    private int id_barang = 0;
    private String nama_jenis_barang;
    private int id_jenis_barang;
    private String nama_barang;
    private int harga_jual;
    private int harga_pokok;
    private int stok;

    /**
     * @return the id_barang
     */
    public int getId_barang() {
        return id_barang;
    }

    /**
     * @param id_barang the id_barang to set
     */
    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    /**
     * @return the nama_jenis_barang
     */
    public String getNama_jenis_barang() {
        return nama_jenis_barang;
    }

    /**
     * @param nama_jenis_barang the nama_jenis_barang to set
     */
    public void setNama_jenis_barang(String nama_jenis_barang) {
        this.nama_jenis_barang = nama_jenis_barang;
    }

    /**
     * @return the id_jenis_barang
     */
    public int getId_jenis_barang() {
        return id_jenis_barang;
    }

    /**
     * @param id_jenis_barang the id_jenis_barang to set
     */
    public void setId_jenis_barang(int id_jenis_barang) {
        this.id_jenis_barang = id_jenis_barang;
    }

    /**
     * @return the nama_barang
     */
    public String getNama_barang() {
        return nama_barang;
    }

    /**
     * @param nama_barang the nama_barang to set
     */
    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    /**
     * @return the harga_jual
     */
    public int getHarga_jual() {
        return harga_jual;
    }

    /**
     * @param harga_jual the harga_jual to set
     */
    public void setHarga_jual(int harga_jual) {
        this.harga_jual = harga_jual;
    }

    /**
     * @return the harga_pokok
     */
    public int getHarga_pokok() {
        return harga_pokok;
    }

    /**
     * @param harga_pokok the harga_pokok to set
     */
    public void setHarga_pokok(int harga_pokok) {
        this.harga_pokok = harga_pokok;
    }

    /**
     * @return the stok
     */
    public int getStok() {
        return stok;
    }

    /**
     * @param stok the stok to set
     */
    public void setStok(int stok) {
        this.stok = stok;
    }
    
}
