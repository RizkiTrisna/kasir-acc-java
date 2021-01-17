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
public class FrameCashier extends javax.swing.JFrame {

    /**
     * Creates new form FrameCashier
     */
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    private DefaultTableModel tmodel;

    public FrameCashier() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        initComponents();
        try {
            getKoneksi();
        } catch (SQLException ex) {
            Logger.getLogger(FrameInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        prepareTable();
        tampilTabelBarang();
    }

    private void getKoneksi() throws SQLException {
        koneksi kon = new koneksi();
        conn = kon.getConnection();
    }

    private Object[] cariBarang(int id) {
        try {
            Object[] o = new Object[6];

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select barang.id_barang as id_barang, barang.nama_barang as nama_barang, jenis_barang.nama_jenis as nama_jenis, harga_jual, harga_pokok, stok_barang from barang, jenis_barang WHERE barang.id_jenis_barang=jenis_barang.id_jenis AND barang.id_barang='" + id + "'";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                o[0] = rs.getInt("id_barang") + "";
                o[1] = rs.getString("nama_barang");
                o[2] = rs.getString("nama_jenis");
                o[3] = rs.getInt("harga_pokok");
                o[4] = rs.getInt("harga_jual");
                o[5] = rs.getInt("stok_barang");
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void prepareTable() {
        tmodel = new DefaultTableModel();
        tabel_barang.setModel(tmodel);
        tmodel.addColumn("ID");
        tmodel.addColumn("Nama barang");
        tmodel.addColumn("Jenis barang");
        tmodel.addColumn("Harga pokok");
        tmodel.addColumn("Harga jual");
        tmodel.addColumn("Sisa stok");
    }

    public void refreshTabel() {
        prepareTable();
        tampilTabelBarang();
    }

    private void tampilTabelBarang() {
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select barang.id_barang as id_barang, barang.nama_barang as nama_barang, jenis_barang.nama_jenis as nama_jenis, harga_jual, harga_pokok, stok_barang from barang, jenis_barang WHERE barang.id_jenis_barang=jenis_barang.id_jenis order by id_barang";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Object[] o = new Object[6];
                o[0] = rs.getInt("id_barang") + "";
                o[1] = rs.getString("nama_barang");
                o[2] = rs.getString("nama_jenis");
                o[3] = rs.getInt("harga_pokok");
                o[4] = rs.getInt("harga_jual");
                o[5] = rs.getInt("stok_barang");
                tmodel.addRow(o);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tampilTabelBarang(String indexCari) {
        try {
            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select barang.id_barang as id_barang, barang.nama_barang as nama_barang, jenis_barang.nama_jenis as nama_jenis, harga_jual, harga_pokok, stok_barang from barang, jenis_barang "
                    + "WHERE barang.id_jenis_barang=jenis_barang.id_jenis and (barang.id_barang='" + indexCari + "' or jenis_barang.nama_jenis like '%" + indexCari + "%' or barang.nama_barang like '%" + indexCari + "%' )";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Object[] o = new Object[6];
                o[0] = rs.getInt("id_barang") + "";
                o[1] = rs.getString("nama_barang");
                o[2] = rs.getString("nama_jenis");
                o[3] = rs.getInt("harga_pokok");
                o[4] = rs.getInt("harga_jual");
                o[5] = rs.getInt("stok_barang");
                tmodel.addRow(o);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        tf_cashier_cari = new javax.swing.JTextField();
        btn_cashier_batalBeli = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_barang = new javax.swing.JTable();
        btn_cashier_refresh = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_beli = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btn_cashier_proses = new javax.swing.JLabel();
        lbl_cashier_total = new javax.swing.JLabel();
        lbl_subtotal = new javax.swing.JLabel();
        lbl_cashier_kembalian = new javax.swing.JLabel();
        tf_cashier_dibayarkan = new javax.swing.JTextField();
        tf_cashier_diskon = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(70, 87, 117));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_cart_active.png"))); // NOI18N
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

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_stat.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

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
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(103, 103, 103))
        );

        jPanel2.setBackground(new java.awt.Color(245, 249, 252));

        tf_cashier_cari.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        tf_cashier_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_cashier_cariActionPerformed(evt);
            }
        });

        btn_cashier_batalBeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_batal_beli.png"))); // NOI18N

        tabel_barang.setFont(new java.awt.Font("Assistant", 0, 20)); // NOI18N
        tabel_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        )

        {public boolean isCellEditable(int row, int column){return false;}}
    );
    jScrollPane1.setViewportView(tabel_barang);

    btn_cashier_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_refresh.png"))); // NOI18N
    btn_cashier_refresh.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            btn_cashier_refreshMouseClicked(evt);
        }
    });

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(50, 50, 50)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jScrollPane1)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(tf_cashier_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(btn_cashier_refresh)
                    .addGap(41, 41, 41)
                    .addComponent(btn_cashier_batalBeli)))
            .addContainerGap(50, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(100, 100, 100)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btn_cashier_batalBeli)
                .addComponent(tf_cashier_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_cashier_refresh))
            .addGap(55, 55, 55)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(88, Short.MAX_VALUE))
    );

    jPanel3.setBackground(new java.awt.Color(255, 255, 255));

    tabel_beli.setModel(new javax.swing.table.DefaultTableModel(
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
    jScrollPane2.setViewportView(tabel_beli);

    jLabel7.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel7.setForeground(new java.awt.Color(112, 112, 112));
    jLabel7.setText("Subtotal");

    jLabel8.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(112, 112, 112));
    jLabel8.setText("Diskon");

    jLabel9.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel9.setForeground(new java.awt.Color(112, 112, 112));
    jLabel9.setText("Total");

    jLabel10.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel10.setForeground(new java.awt.Color(112, 112, 112));
    jLabel10.setText("Dibayarkan");

    jLabel11.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel11.setForeground(new java.awt.Color(112, 112, 112));
    jLabel11.setText("Kembalian");

    jLabel12.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel12.setForeground(new java.awt.Color(112, 112, 112));
    jLabel12.setText(":");

    jLabel13.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel13.setForeground(new java.awt.Color(112, 112, 112));
    jLabel13.setText(":");

    jLabel14.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel14.setForeground(new java.awt.Color(112, 112, 112));
    jLabel14.setText(":");

    jLabel15.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel15.setForeground(new java.awt.Color(112, 112, 112));
    jLabel15.setText(":");

    jLabel16.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel16.setForeground(new java.awt.Color(112, 112, 112));
    jLabel16.setText(":");

    jLabel17.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel17.setForeground(new java.awt.Color(112, 112, 112));
    jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/garis_total.png"))); // NOI18N

    btn_cashier_proses.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    btn_cashier_proses.setForeground(new java.awt.Color(112, 112, 112));
    btn_cashier_proses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_proses_trans.png"))); // NOI18N
    btn_cashier_proses.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            btn_cashier_prosesMouseClicked(evt);
        }
    });

    lbl_cashier_total.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    lbl_cashier_total.setForeground(new java.awt.Color(112, 112, 112));
    lbl_cashier_total.setText("0");

    lbl_subtotal.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    lbl_subtotal.setForeground(new java.awt.Color(112, 112, 112));
    lbl_subtotal.setText("0");

    lbl_cashier_kembalian.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    lbl_cashier_kembalian.setForeground(new java.awt.Color(112, 112, 112));
    lbl_cashier_kembalian.setText("0");

    tf_cashier_dibayarkan.setFont(new java.awt.Font("Assistant SemiBold", 0, 32)); // NOI18N

    tf_cashier_diskon.setFont(new java.awt.Font("Assistant SemiBold", 0, 32)); // NOI18N

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(50, 50, 50)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(btn_cashier_proses))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel11)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_cashier_kembalian))
                        .addComponent(jLabel17)))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel13))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel12)))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel15)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbl_cashier_total)
                            .addComponent(tf_cashier_diskon)
                            .addComponent(tf_cashier_dibayarkan, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addComponent(lbl_subtotal, javax.swing.GroupLayout.Alignment.TRAILING))))
            .addContainerGap(67, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(100, 100, 100)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(53, 53, 53)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel12))
                    .addGap(20, 20, 20))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(lbl_subtotal)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(tf_cashier_diskon)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(20, 20, 20)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel9)
                .addComponent(jLabel14)
                .addComponent(lbl_cashier_total))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel15))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addComponent(tf_cashier_dibayarkan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(1, 1, 1)))
            .addGap(51, 51, 51)
            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel11)
                .addComponent(jLabel16)
                .addComponent(lbl_cashier_kembalian))
            .addGap(57, 57, 57)
            .addComponent(btn_cashier_proses)
            .addContainerGap(60, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_cashier_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_cashier_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_cashier_cariActionPerformed

    private void btn_cashier_prosesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cashier_prosesMouseClicked
        FrameCetakStrukPopUp fc = new FrameCetakStrukPopUp();
        fc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_cashier_prosesMouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin menutup aplikasi ini?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        new FrameStat().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        new FrameInventory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseClicked

    private void btn_cashier_refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cashier_refreshMouseClicked
        refreshTabel();
    }//GEN-LAST:event_btn_cashier_refreshMouseClicked

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
            java.util.logging.Logger.getLogger(FrameCashier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameCashier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameCashier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameCashier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameCashier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btn_cashier_batalBeli;
    private javax.swing.JLabel btn_cashier_proses;
    private javax.swing.JLabel btn_cashier_refresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_cashier_kembalian;
    private javax.swing.JLabel lbl_cashier_total;
    private javax.swing.JLabel lbl_subtotal;
    private javax.swing.JTable tabel_barang;
    private javax.swing.JTable tabel_beli;
    private javax.swing.JTextField tf_cashier_cari;
    private javax.swing.JTextField tf_cashier_dibayarkan;
    private javax.swing.JTextField tf_cashier_diskon;
    // End of variables declaration//GEN-END:variables
}
