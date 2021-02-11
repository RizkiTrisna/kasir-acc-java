/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rizki Trisna
 */
public class FrameStat extends javax.swing.JFrame {

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    private static DefaultTableModel tmodel;

    public FrameStat() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        initComponents();
        try {
            getKoneksi();
        } catch (SQLException ex) {
            Logger.getLogger(FrameInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        prepareTable();
        tampilTabelHistory();
//        updatePembelianTerbanyak(); 
        tampilQtySebulan();
        tampilJumlahSebulan();
    }
    
    private void tampilJumlahSebulan(){
        
    }
    
    private void tampilQtySebulan(){
    
    }
    private void updatePembelianTerbanyak() {
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nama_barang = rs.getString("nama_barang");
                String jenis_barang = rs.getString("jenis_barang");
                String tanggal_transaksi = rs.getString("tanggal_pembelian");
                String nama_admin = rs.getString("nama_admin");
                int id_transaksi = rs.getInt("id_transaksi");
                int qty = rs.getInt("qty");
                int harga_jual = rs.getInt("harga_jual");
                int subtotal = qty * harga_jual;

                Object[] o = new Object[7];
                o[0] = id_transaksi;
                o[1] = nama_barang;
                o[2] = jenis_barang;
                o[3] = qty;
                o[4] = subtotal;
                o[5] = tanggal_transaksi;
                o[6] = nama_admin;
                tmodel.addRow(o);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tampilTabelHistory() {
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select transaksi.id_transaksi as id_transaksi, barang.nama_barang as nama_barang, jenis_barang.nama_jenis as jenis_barang, qty, harga_jual, tanggal_pembelian, admin.nama_admin as nama_admin "
                    + "from barang, transaksi, admin, jenis_barang "
                    + "WHERE barang.id_barang=transaksi.id_barang and transaksi.id_admin=admin.id_admin and jenis_barang.id_jenis=barang.id_jenis_barang order by id_transaksi ";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nama_barang = rs.getString("nama_barang");
                String jenis_barang = rs.getString("jenis_barang");
                String tanggal_transaksi = rs.getString("tanggal_pembelian");
                String nama_admin = rs.getString("nama_admin");
                int id_transaksi = rs.getInt("id_transaksi");
                int qty = rs.getInt("qty");
                int harga_jual = rs.getInt("harga_jual");
                int subtotal = qty * harga_jual;

                Object[] o = new Object[7];
                o[0] = id_transaksi;
                o[1] = nama_barang;
                o[2] = jenis_barang;
                o[3] = qty;
                o[4] = subtotal;
                o[5] = tanggal_transaksi;
                o[6] = nama_admin;
                tmodel.addRow(o);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void tampilTabelHistory(String indexCari) {
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select transaksi.id_transaksi as id_transaksi, barang.nama_barang as nama_barang, jenis_barang.nama_jenis as jenis_barang, qty, harga_jual, tanggal_pembelian, admin.nama_admin as nama_admin "
                    + "from barang, transaksi, admin, jenis_barang "
                    + "WHERE (barang.id_barang=transaksi.id_barang and transaksi.id_admin=admin.id_admin and jenis_barang.id_jenis=barang.id_jenis_barang) "
                    + "AND (transaksi.id_transaksi='"+indexCari+"' or barang.nama_barang LIKE '%"+indexCari+"%' or jenis_barang.nama_jenis LIKE '%"+indexCari+"%' or admin.nama_admin LIKE '%"+indexCari+"%' or transaksi.tanggal_pembelian LIKE '%"+indexCari+"%') order by id_transaksi";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nama_barang = rs.getString("nama_barang");
                String jenis_barang = rs.getString("jenis_barang");
                String tanggal_transaksi = rs.getString("tanggal_pembelian");
                String nama_admin = rs.getString("nama_admin");
                int id_transaksi = rs.getInt("id_transaksi");
                int qty = rs.getInt("qty");
                int harga_jual = rs.getInt("harga_jual");
                int subtotal = qty * harga_jual;

                Object[] o = new Object[7];
                o[0] = id_transaksi;
                o[1] = nama_barang;
                o[2] = jenis_barang;
                o[3] = qty;
                o[4] = subtotal;
                o[5] = tanggal_transaksi;
                o[6] = nama_admin;
                tmodel.addRow(o);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareTable() {
        tmodel = new DefaultTableModel();
        tabel_history.setModel(tmodel);
        tmodel.addColumn("ID");
        tmodel.addColumn("Nama barang");
        tmodel.addColumn("Jenis barang");
        tmodel.addColumn("Qty");
        tmodel.addColumn("Subtotal");
        tmodel.addColumn("Tanggal Transaksi");
        tmodel.addColumn("Admin");
    }

    private void getKoneksi() throws SQLException {
        koneksi kon = new koneksi();
        conn = kon.getConnection();
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
        jPanel2 = new javax.swing.JPanel();
        tf_cari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_history = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_jumlah_transaksi = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_nama_dibeli = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_total_pendapatan = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_n_dibeli = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(70, 87, 117));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_cart.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_inventory.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_stat_selected.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/Icon_feather_power.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel4))
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(99, 99, 99))
        );

        jPanel2.setBackground(new java.awt.Color(245, 249, 252));

        tf_cari.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        tf_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_cariActionPerformed(evt);
            }
        });
        tf_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_cariKeyReleased(evt);
            }
        });

        tabel_history.setFont(new java.awt.Font("Assistant", 0, 20)); // NOI18N
        tabel_history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabel_history);

        jLabel7.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(112, 112, 112));
        jLabel7.setText("Jumlah Transaksi/30 hari");

        jLabel8.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(112, 112, 112));
        jLabel8.setText("Total Pendapatan/30 hari");

        jLabel18.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(112, 112, 112));
        jLabel18.setText("Rp.");

        jLabel12.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(112, 112, 112));
        jLabel12.setText(":");

        jLabel13.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(112, 112, 112));
        jLabel13.setText(":");

        lbl_jumlah_transaksi.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        lbl_jumlah_transaksi.setForeground(new java.awt.Color(112, 112, 112));
        lbl_jumlah_transaksi.setText("0000000");

        jLabel9.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(112, 112, 112));
        jLabel9.setText("Paling banyak dibeli");

        lbl_nama_dibeli.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        lbl_nama_dibeli.setForeground(new java.awt.Color(112, 112, 112));
        lbl_nama_dibeli.setText("XXXXXXXXX");

        jLabel20.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(112, 112, 112));
        jLabel20.setText(":");

        jLabel10.setFont(new java.awt.Font("Assistant", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(112, 112, 112));
        jLabel10.setText("transaksi )");

        jButton4.setBackground(new java.awt.Color(121, 232, 189));
        jButton4.setFont(new java.awt.Font("Assistant SemiBold", 0, 42)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Cetak Laporan Bulanan");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/icon_refresh.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        lbl_total_pendapatan.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        lbl_total_pendapatan.setForeground(new java.awt.Color(112, 112, 112));
        lbl_total_pendapatan.setText("0.000.000");

        jLabel11.setFont(new java.awt.Font("Assistant", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(112, 112, 112));
        jLabel11.setText("(");

        lbl_n_dibeli.setFont(new java.awt.Font("Assistant", 1, 24)); // NOI18N
        lbl_n_dibeli.setForeground(new java.awt.Color(112, 112, 112));
        lbl_n_dibeli.setText("XXX");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(tf_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(34, 34, 34)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel12)
                                    .addGap(90, 90, 90))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel13)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel18)
                                    .addGap(28, 28, 28)))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbl_total_pendapatan)
                                    .addGap(124, 124, 124))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbl_jumlah_transaksi)
                                    .addGap(353, 353, 353)
                                    .addComponent(jLabel9)
                                    .addGap(83, 83, 83)
                                    .addComponent(jLabel20)
                                    .addGap(32, 32, 32)
                                    .addComponent(lbl_nama_dibeli)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(lbl_n_dibeli)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel10)))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1706, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9)
                    .addComponent(jLabel20)
                    .addComponent(lbl_nama_dibeli)
                    .addComponent(jLabel10)
                    .addComponent(lbl_jumlah_transaksi)
                    .addComponent(lbl_n_dibeli)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel13)
                            .addComponent(jLabel18)
                            .addComponent(lbl_total_pendapatan))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4)))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_cariActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        FrameCashier fc = new FrameCashier();
        fc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        FrameInventory fi = new FrameInventory();
        fi.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin menutup aplikasi ini?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION && session.endSession()) {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void tf_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_cariKeyReleased
        String indexCari = tf_cari.getText();
        prepareTable();
        tampilTabelHistory(indexCari);
    }//GEN-LAST:event_tf_cariKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        prepareTable();
        tampilTabelHistory();
        tf_cari.setText(null);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new FrameOpsiCetakPopUp().setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(FrameStat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameStat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameStat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameStat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameStat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_jumlah_transaksi;
    private javax.swing.JLabel lbl_n_dibeli;
    private javax.swing.JLabel lbl_nama_dibeli;
    private javax.swing.JLabel lbl_total_pendapatan;
    private javax.swing.JTable tabel_history;
    private javax.swing.JTextField tf_cari;
    // End of variables declaration//GEN-END:variables
}
