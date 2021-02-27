/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

import java.awt.event.KeyEvent;
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
public class FrameTableSearchCashierPopUp extends javax.swing.JFrame {

    /**
     * Creates new form FrameTableSearchCashierPopUp
     */
    private int qty_barang;
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public FrameTableSearchCashierPopUp() {
        setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        try {
            getKoneksi();
        } catch (SQLException ex) {
            Logger.getLogger(FrameTableSearchCashierPopUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDetail();

    }

    private void getKoneksi() throws SQLException {
        koneksi kon = new koneksi();
        conn = kon.getConnection();
    }

    private void setQty_barang(int qty) {
        this.qty_barang = qty;
    }

    private int getQty_barang() {
        return this.qty_barang;
    }

    private void setDetail() {
        lbl_id.setText(Barang.getId_barang() + "");
        lbl_nm_barang.setText(Barang.getNama_barang().substring(0, 14)+".." + "");
        lbl_harga.setText(Barang.getHarga_jual() + "");
        lbl_subtotal.setText(Barang.getHarga_jual() + "");
        lbl_id.setText(Barang.getId_barang() + "");
        tf_qty.setText("1");
    }

    private int countBarangDiTemp(int id_barang) {
        try {
            int count = 0;
            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select count(*) as n_barang from temp_transaksi where id_barang=" + id_barang;

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                count = rs.getInt("n_barang");
            }

            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private Object[] cariBarangDiTemp(int id) {
        try {
            Object[] o = new Object[6];

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select * from temp_transaksi where id_barang=" + id + "";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                o[0] = rs.getInt("id_temp");
                o[1] = rs.getInt("id_barang");
                o[2] = rs.getInt("id_admin");
                o[3] = rs.getInt("qty");
                o[4] = rs.getString("tanggal_pembelian");
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void tambahKeranjang(int id_barang, int id_admin, int qty_input) {
        try {

            //cari barang di database temp_transaksi
            if (countBarangDiTemp(id_barang) > 0) {
                // Update data yang sudah ada
                Object[] data_barang = cariBarangDiTemp(id_barang);
                int qty_lama = Integer.parseInt(data_barang[3].toString());
                int qty_baru = qty_lama + qty_input;
                String query = "Update temp_transaksi set qty=" + qty_baru + " Where id_barang=" + id_barang;
                PreparedStatement pst = conn.prepareStatement(query);
                pst.execute();
            } else {
                // Tambahkan data baru
                String query = "Insert into temp_transaksi(id_barang, id_admin, qty) values(" + id_barang + ", " + id_admin + ", " + qty_input + ")";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.execute();
            }
            FrameCashier.refreshTabelBarang();
            FrameCashier.refreshKeranjang();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal ditambahkan ke keranjang\n Error: " + e);
            e.printStackTrace();
        }
    }

    private Object[] cariDataBarang(int id_barang) {
        try {
            Object[] ob = new Object[5];

            stmt = conn.createStatement();

            String query = "Select * from barang where id_barang=" + id_barang;

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                ob[0] = rs.getInt("id_barang");
                ob[1] = rs.getInt("id_jenis_barang");
                ob[2] = rs.getString("nama_barang");
                ob[3] = rs.getInt("harga_jual");
                ob[4] = rs.getInt("stok_barang");
            }
            return ob;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbl_nm_barang = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_subtotal = new javax.swing.JLabel();
        lbl_id = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lbl_harga = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        tf_qty = new javax.swing.JTextField();
        btn_batal = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel2.setText("Nama Barang");

        jLabel3.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel3.setText("Banyaknya");

        jLabel4.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel4.setText("Harga");

        jLabel5.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel5.setText("Subtotal");

        jLabel6.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel6.setText(":");

        lbl_nm_barang.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        lbl_nm_barang.setText("XXXXXXXX");

        jLabel9.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel9.setText("Rp.");

        lbl_subtotal.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        lbl_subtotal.setText("0000000");

        lbl_id.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        lbl_id.setText("XXXXXXXX");

        jLabel12.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel12.setText(":");

        jLabel13.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel13.setText(":");

        jLabel14.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel14.setText(":");

        jLabel15.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel15.setText(":");

        lbl_harga.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        lbl_harga.setText("0000000");

        jLabel17.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel17.setText("Rp.");

        tf_qty.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        tf_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_qtyActionPerformed(evt);
            }
        });
        tf_qty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_qtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_qtyKeyTyped(evt);
            }
        });

        btn_batal.setFont(new java.awt.Font("Assistant", 0, 26)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(168, 168, 168));
        btn_batal.setText("Batalkan");
        btn_batal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_batalMouseClicked(evt);
            }
        });

        btn_tambah.setBackground(new java.awt.Color(26, 115, 232));
        btn_tambah.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setText("Tambahkan ke Keranjang");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(btn_batal)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel12))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13))
                                .addComponent(jLabel4)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(133, 133, 133)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_nm_barang)
                            .addComponent(lbl_id)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_harga))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_subtotal))
                            .addComponent(tf_qty))
                        .addGap(68, 68, 68))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_id)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_nm_barang)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tf_qty, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(lbl_harga))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_subtotal)
                                    .addComponent(jLabel17)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14)))
                        .addGap(2, 2, 2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13)))
                        .addGap(11, 11, 11)
                        .addComponent(jLabel4)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_batal)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_qtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_qtyActionPerformed

    private void btn_batalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_batalMouseClicked
        this.dispose();
    }//GEN-LAST:event_btn_batalMouseClicked

    private void tf_qtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_qtyKeyReleased
        if (tf_qty.getText().equals("") || tf_qty.getText().equals("0")) {
            lbl_subtotal.setText(String.valueOf(Barang.getHarga_jual()));
        } else {
            int harga = Barang.getHarga_jual();
            int qty = Integer.parseInt(tf_qty.getText().toString());
            int subtotal = harga * qty;
            lbl_subtotal.setText(subtotal + "");

        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER && !tf_qty.getText().equals("")) {
            tambahBarang();
        }
    }//GEN-LAST:event_tf_qtyKeyReleased

    private void tf_qtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_qtyKeyTyped
        // TODO add your handling code here: 
        filterAngka(evt);
    }//GEN-LAST:event_tf_qtyKeyTyped

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        tambahBarang();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void tambahBarang() {

        int id_admin = session.getSession().getId_admin();
        int qty = Integer.parseInt(tf_qty.getText());
        int stok_lama = Barang.getStok();
        int stok_baru = stok_lama - qty;

//        if (!tf_qty.equals("")) {
//            try {
//                // Update stok lama jadi stok baru dengan menambahkan qty yang dimasukkan ke temp_transaksi by id barang
//                String query = "update barang set stok_barang=" + stok_baru + " where id_barang=" + Barang.getId_barang();
//                PreparedStatement pst = conn.prepareStatement(query);
//                pst.execute();
//                tambahKeranjang(Barang.getId_barang(), id_admin, qty);
//
//                this.dispose();
//            } catch (Exception e) {
//                JOptionPane.showMessageDialog(null, "Gagal menambahkan barang ke keranjang\n Error: " + e);
//            }
//
//        } else {
//            JOptionPane.showMessageDialog(null, "Kolom jumlah barang (Qty) tidak boleh kosong");
//        }
        //cek apakah qty melebihi stok
        if (!tf_qty.equals("")) {
            if (qty <= stok_lama) {
                try {
                    // Update stok lama jadi stok baru dengan menambahkan qty yang dimasukkan ke temp_transaksi by id barang
                    String query = "update barang set stok_barang=" + stok_baru + " where id_barang=" + Barang.getId_barang();
                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.execute();
                    tambahKeranjang(Barang.getId_barang(), id_admin, qty);

                    this.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal menambahkan barang ke keranjang\n Error: " + e);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Jumlah stok barang tidak mencukupi\n Sisa stok barang sekarang: " + stok_lama);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kolom jumlah barang (Qty) tidak boleh kosong");
        }

    }

    private boolean filterAngka(KeyEvent evt) {
        if (Character.isAlphabetic(evt.getKeyChar())) {
            //JIKA KARAKTER YANG DIINPUTKAN ADALAH ALPHABET, MAKA TIDAK AKAN DIPROSES OLEH KEYLISTENER KARENA DICEGAH METHOD CONSUME
            //DAN AKAN MENAMPILKAN PESAN JOPTIONPANE "FIELD INI HANYA MENERIMA INPUT ANGKA
            evt.consume();
            JOptionPane.showMessageDialog(null, "Field ini hanya menerima input Angka");
            return false;
        } else {
            return true;
        }
    }

    private boolean filterHuruf(KeyEvent evt) {
        if (Character.isDigit(evt.getKeyChar())) {
            //JIKA KARAKTER YANG DIINPUTKAN ADALAH NUMERIKAL, MAKA TIDAK AKAN DIPROSES OLEH KEYLISTENER KARENA DICEGAH METHOD CONSUME
            //DAN AKAN MENAMPILKAN PESAN JOPTIONPANE "FIELD INI HANYA MENERIMA INPUT HURUF"
            evt.consume();
            JOptionPane.showMessageDialog(null, "Field ini hanya menerima input Huruf");
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameTableSearchCashierPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameTableSearchCashierPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameTableSearchCashierPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameTableSearchCashierPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameTableSearchCashierPopUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_batal;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_harga;
    private javax.swing.JLabel lbl_id;
    private javax.swing.JLabel lbl_nm_barang;
    private javax.swing.JLabel lbl_subtotal;
    private javax.swing.JTextField tf_qty;
    // End of variables declaration//GEN-END:variables
}
