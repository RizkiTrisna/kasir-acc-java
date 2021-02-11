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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Rizki Trisna
 */
public class FramePopUpTambahBarang extends javax.swing.JFrame {

    /**
     * Creates new form FramePopUpTambahBarang
     */
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public FramePopUpTambahBarang() {
        setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        try {
            getKoneksi();
        } catch (SQLException ex) {
            Logger.getLogger(FramePopUpTambahBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
        tampilCombobox();
    }

    private void getKoneksi() throws SQLException {
        koneksi kon = new koneksi();
        conn = kon.getConnection();
    }

    private void tampilCombobox() {
        cb_popupTambah_jenis_barang.removeAllItems();
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select * from jenis_barang";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id_jenis = rs.getInt("id_jenis");
                String nama_jenis = rs.getString("nama_jenis");
                String inputCbb = id_jenis + " - " + nama_jenis;
                cb_popupTambah_jenis_barang.addItem(inputCbb);
            }

//            System.out.println("Data di dalam combobox: ");
//            Iterator iter = dataCbb.iterator();
//            while (iter.hasNext()){
//                System.out.println(iter.next());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertData(Object[] ob) {
        int id_jenis_barang = Integer.parseInt(ob[0].toString());
        String nama_barang = ob[1].toString();
        int harga_jual = Integer.parseInt(ob[2].toString());
        int harga_pokok = Integer.parseInt(ob[3].toString());
        int stok = Integer.parseInt(ob[4].toString());

        try {
            String query = "Insert into barang(id_jenis_barang, nama_barang, harga_jual, harga_pokok, stok_barang) values(" + id_jenis_barang + ", '" + nama_barang + "', " + harga_jual + ", " + harga_pokok + ", " + stok + " )";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan");
            e.printStackTrace();
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
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btn_popupTambah_batal = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tf_popupTambah_namaBarang = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        tf_popupTambah_hargaJual = new javax.swing.JTextField();
        tf_popupTambah_hargaPokok = new javax.swing.JTextField();
        tf_popupTambah_stok = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        cb_popupTambah_jenis_barang = new javax.swing.JComboBox();
        btn_tambah_barang = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel1.setText("Nama barang");

        jLabel2.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel2.setText("Jenis barang");

        jLabel3.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel3.setText("Harga pokok");

        jLabel4.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel4.setText("Harga jual");

        jLabel5.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel5.setText("Stok");

        jLabel6.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel6.setText(":");

        jLabel12.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel12.setText(":");

        jLabel13.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel13.setText(":");

        jLabel14.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel14.setText(":");

        jLabel15.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel15.setText(":");

        jLabel17.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel17.setText("Rp.");

        btn_popupTambah_batal.setFont(new java.awt.Font("Assistant", 0, 26)); // NOI18N
        btn_popupTambah_batal.setForeground(new java.awt.Color(168, 168, 168));
        btn_popupTambah_batal.setText("Batalkan");
        btn_popupTambah_batal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_popupTambah_batalMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel8.setText("Tambah barang");

        tf_popupTambah_namaBarang.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        tf_popupTambah_namaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_popupTambah_namaBarangActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel21.setText("Rp.");

        tf_popupTambah_hargaJual.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        tf_popupTambah_hargaJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_popupTambah_hargaJualActionPerformed(evt);
            }
        });

        tf_popupTambah_hargaPokok.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        tf_popupTambah_hargaPokok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_popupTambah_hargaPokokActionPerformed(evt);
            }
        });

        tf_popupTambah_stok.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        tf_popupTambah_stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_popupTambah_stokActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel16.setText(":");

        cb_popupTambah_jenis_barang.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        cb_popupTambah_jenis_barang.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_popupTambah_jenis_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_popupTambah_jenis_barangActionPerformed(evt);
            }
        });

        btn_tambah_barang.setBackground(new java.awt.Color(26, 115, 232));
        btn_tambah_barang.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        btn_tambah_barang.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah_barang.setText("Tambah Barang");
        btn_tambah_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambah_barangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel1)
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel15))
                        .addGap(63, 63, 63)
                        .addComponent(tf_popupTambah_namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel2)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel12)
                        .addGap(63, 63, 63)
                        .addComponent(cb_popupTambah_jenis_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel3)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel13)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel21)
                        .addGap(3, 3, 3)
                        .addComponent(tf_popupTambah_hargaPokok, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel4)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel16)
                        .addGap(23, 23, 23)
                        .addComponent(jLabel17)
                        .addGap(3, 3, 3)
                        .addComponent(tf_popupTambah_hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel5)
                        .addGap(110, 110, 110)
                        .addComponent(jLabel14)
                        .addGap(63, 63, 63)
                        .addComponent(tf_popupTambah_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_tambah_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_popupTambah_batal)
                        .addGap(207, 207, 207))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel8)
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel15)
                    .addComponent(tf_popupTambah_namaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel12)
                    .addComponent(cb_popupTambah_jenis_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel13)
                    .addComponent(jLabel21)
                    .addComponent(tf_popupTambah_hargaPokok, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(tf_popupTambah_hargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5))
                    .addComponent(jLabel14)
                    .addComponent(tf_popupTambah_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(btn_tambah_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_popupTambah_batal)
                .addContainerGap(23, Short.MAX_VALUE))
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

    private void btn_popupTambah_batalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_popupTambah_batalMouseClicked
        this.dispose();
    }//GEN-LAST:event_btn_popupTambah_batalMouseClicked

    private void tf_popupTambah_namaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_popupTambah_namaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_popupTambah_namaBarangActionPerformed

    private void tf_popupTambah_hargaJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_popupTambah_hargaJualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_popupTambah_hargaJualActionPerformed

    private void tf_popupTambah_hargaPokokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_popupTambah_hargaPokokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_popupTambah_hargaPokokActionPerformed

    private void tf_popupTambah_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_popupTambah_stokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_popupTambah_stokActionPerformed

    private void cb_popupTambah_jenis_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_popupTambah_jenis_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_popupTambah_jenis_barangActionPerformed

    private void btn_tambah_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah_barangActionPerformed
        // TODO add your handling code here:
        if ((!tf_popupTambah_namaBarang.getText().equals("") && !tf_popupTambah_hargaJual.getText().equals("") && !tf_popupTambah_hargaPokok.getText().equals("") && !tf_popupTambah_stok.getText().equals(""))) {

            int hasil = JOptionPane.showConfirmDialog(null, "Tambahkan barang?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (hasil == JOptionPane.YES_OPTION) {
                String nama_barang = tf_popupTambah_namaBarang.getText();
                int harga_pokok = Integer.parseInt(tf_popupTambah_hargaPokok.getText().toString());
                int harga_jual = Integer.parseInt(tf_popupTambah_hargaJual.getText().toString());
                int stok = Integer.parseInt(tf_popupTambah_stok.getText().toString());
                int id_jenis = Integer.parseInt(cb_popupTambah_jenis_barang.getSelectedItem().toString().split(" - ")[0]);
                Object[] ob = new Object[5];
                ob[0] = id_jenis;
                ob[1] = nama_barang;
                ob[2] = harga_jual;
                ob[3] = harga_pokok;
                ob[4] = stok;
                insertData(ob);
                FrameInventory.refreshTableRemote();
                tf_popupTambah_namaBarang.setText("");
                tf_popupTambah_hargaJual.setText("");
                tf_popupTambah_hargaPokok.setText("");
                tf_popupTambah_stok.setText("");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Terdapat field yang masih kosong");
        }
    }//GEN-LAST:event_btn_tambah_barangActionPerformed

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
            java.util.logging.Logger.getLogger(FramePopUpTambahBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FramePopUpTambahBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FramePopUpTambahBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FramePopUpTambahBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FramePopUpTambahBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_popupTambah_batal;
    private javax.swing.JButton btn_tambah_barang;
    private javax.swing.JComboBox cb_popupTambah_jenis_barang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField tf_popupTambah_hargaJual;
    private javax.swing.JTextField tf_popupTambah_hargaPokok;
    private javax.swing.JTextField tf_popupTambah_namaBarang;
    private javax.swing.JTextField tf_popupTambah_stok;
    // End of variables declaration//GEN-END:variables
}
