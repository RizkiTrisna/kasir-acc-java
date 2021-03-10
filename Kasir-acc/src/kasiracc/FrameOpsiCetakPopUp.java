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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Rizki Trisna
 */
public class FrameOpsiCetakPopUp extends javax.swing.JFrame {

    /**
     * Creates new form FrameCetakStrukPopUp
     */
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    private ArrayList dataCbb = new ArrayList();

    public FrameOpsiCetakPopUp() {

        setUndecorated(true);
        initComponents();
        setLocationRelativeTo(null);
        try {
            getKoneksi();
        } catch (SQLException ex) {
            Logger.getLogger(FrameInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCbbBulan();
        setCbbJenis();
    }

    private void setCbbBulan() {
        cb_bulan.removeAllItems();
        String[] bulan = {"1 - Januari", "2 - Februari", "3 - Maret", "4 - April", "5 - Mei", "6 - Juni", "7 - Juli", "8 - Agustus", "9 - September", "10 - Oktober",
            "11 - November", "12 - Desember"};

        for (int i = 0; i < bulan.length; i++) {
            cb_bulan.addItem(bulan[i]);
        }
    }

    private void setCbbJenis() {
        try {
            cb_jenis.removeAllItems();

            stmt = conn.createStatement();
            String query = "Select * from jenis_barang order by id_jenis";
            rs = stmt.executeQuery(query);
            cb_jenis.addItem("Semua");
            while (rs.next()) {
                cb_jenis.addItem(rs.getInt("id_jenis") + " - " + rs.getString("nama_jenis"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FrameOpsiCetakPopUp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void getKoneksi() throws SQLException {
        koneksi kon = new koneksi();
        conn = kon.getConnection();
    }

    public Object[] getJumlah(String param_tgl_awal, String param_tgl_akhir, String id_jenis) {
        Object[] o = new Object[3];
        try {
            if (id_jenis == null) {
                // Print semua
                int nilai_jual = 0;
                int nilai_pokok = 0;
                int laba = 0;
                // buat objek statement
                stmt = conn.createStatement();

                // buat query ke database
                String query = "select CAST(tanggal_pembelian AS DATE) as tanggal, id_transaksi, barang.nama_barang, qty, (barang.harga_jual * qty) as nilai_jual, (barang.harga_pokok * qty) as nilai_pokok, ((barang.harga_jual * qty) - (barang.harga_pokok * qty)) as laba "
                        + "from transaksi, barang "
                        + "where transaksi.id_barang=barang.id_barang and transaksi.tanggal_pembelian >= '" + param_tgl_awal + "' and transaksi.tanggal_pembelian <= '" + param_tgl_akhir + " 23:59:59.999'";
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    nilai_jual += rs.getInt("nilai_jual");
                    nilai_pokok += rs.getInt("nilai_pokok");
                    laba += rs.getInt("laba");
                }
                o[0] = nilai_jual;
                o[1] = nilai_pokok;
                o[2] = laba;

                return o;
            } else {
                //Print jenis barang tertentu
                int nilai_jual = 0;
                int nilai_pokok = 0;
                int laba = 0;
                // buat objek statement
                stmt = conn.createStatement();

                // buat query ke database
                String query = "select CAST(tanggal_pembelian AS DATE) as tanggal, id_transaksi, barang.nama_barang, qty, (barang.harga_jual * qty) as nilai_jual, (barang.harga_pokok * qty) as nilai_pokok, ((barang.harga_jual * qty) - (barang.harga_pokok * qty)) as laba "
                        + "from transaksi, barang "
                        + "where transaksi.id_barang=barang.id_barang and transaksi.tanggal_pembelian >= '" + param_tgl_awal + "' and transaksi.tanggal_pembelian <= '" + param_tgl_akhir + " 23:59:59.999' and barang.id_jenis_barang="+id_jenis;
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    nilai_jual += rs.getInt("nilai_jual");
                    nilai_pokok += rs.getInt("nilai_pokok");
                    laba += rs.getInt("laba");
                }
                o[0] = nilai_jual;
                o[1] = nilai_pokok;
                o[2] = laba;

                return o;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    private static String setDotsCurrency(double number) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol(" ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String result = kursIndonesia.format(number);

        return result;
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
        jLabel11 = new javax.swing.JLabel();
        lbl_batal = new javax.swing.JLabel();
        tf_tahun = new javax.swing.JTextField();
        btn_cetak = new javax.swing.JButton();
        cb_bulan = new javax.swing.JComboBox();
        cb_jenis = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel1.setText("Opsi Cetak Laporan Bulanan");

        jLabel2.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel2.setText("Bulan");

        jLabel11.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel11.setText("Tahun");

        lbl_batal.setFont(new java.awt.Font("Assistant", 0, 26)); // NOI18N
        lbl_batal.setForeground(new java.awt.Color(168, 168, 168));
        lbl_batal.setText("Batalkan");
        lbl_batal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_batalMouseClicked(evt);
            }
        });

        tf_tahun.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        tf_tahun.setText("2021");
        tf_tahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_tahunActionPerformed(evt);
            }
        });

        btn_cetak.setBackground(new java.awt.Color(26, 115, 232));
        btn_cetak.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        btn_cetak.setForeground(new java.awt.Color(255, 255, 255));
        btn_cetak.setText("Cetak Laporan");
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });

        cb_bulan.setFont(new java.awt.Font("Assistant", 1, 24)); // NOI18N
        cb_bulan.setForeground(new java.awt.Color(112, 112, 112));
        cb_bulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_bulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_bulanActionPerformed(evt);
            }
        });

        cb_jenis.setFont(new java.awt.Font("Assistant", 1, 24)); // NOI18N
        cb_jenis.setForeground(new java.awt.Color(112, 112, 112));
        cb_jenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_jenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_jenisActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
        jLabel3.setText("Jenis Barang");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(lbl_batal))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel11))
                                .addGap(124, 124, 124))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(52, 52, 52)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cb_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cb_bulan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(109, 109, 109)
                                    .addComponent(tf_tahun, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGap(82, 82, 82))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel1)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cb_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cb_bulan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tf_tahun, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_batal)
                .addContainerGap(72, Short.MAX_VALUE))
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

    private void tf_tahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_tahunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_tahunActionPerformed

    private void lbl_batalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_batalMouseClicked
        this.dispose();
    }//GEN-LAST:event_lbl_batalMouseClicked

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        try {
            //Mengambil id jenis barang
            String id_jenis_temp = cb_jenis.getSelectedItem().toString();

            if (id_jenis_temp.equalsIgnoreCase("Semua")) {
                // Print semua data di semua jenis

                // Menentukan awal dan akhir bulan periode
                String angka_bulan = cb_bulan.getSelectedItem().toString().split(" - ")[0];
                int int_bulan = Integer.parseInt(angka_bulan.trim());
                String nama_bulan = cb_bulan.getSelectedItem().toString().split(" - ")[1];
                String tahun = tf_tahun.getText();
                String param_tgl_awal = tahun.trim() + "-" + angka_bulan.trim() + "-1";
                String param_tgl_awal_dmyyyy = "1" + "/" + angka_bulan.trim() + "/" + tahun.trim();

                //Mengambil data akhir bulan 
                LocalDate convertedData = LocalDate.parse(param_tgl_awal_dmyyyy, DateTimeFormatter.ofPattern("d/M/yyyy"));
                convertedData = convertedData.withDayOfMonth(convertedData.getMonth().length(convertedData.isLeapYear()));
                String param_tgl_akhir = convertedData.toString();

                // Penentuan lokasi file jasper
                String file = "src/struk/report_bulanan.jrxml";

                // Passing variabel sbg parameter
                HashMap map = new HashMap();
                map.put("query", "select CAST(tanggal_pembelian AS DATE) as tanggal, id_transaksi, barang.nama_barang, qty, (barang.harga_jual * qty) as nilai_jual, (barang.harga_pokok * qty) as nilai_pokok, ((barang.harga_jual * qty) - (barang.harga_pokok * qty)) as laba "
                        + "from transaksi, barang "
                        + "where transaksi.id_barang=barang.id_barang and transaksi.tanggal_pembelian >= '" + param_tgl_awal + "' and transaksi.tanggal_pembelian <= '" + param_tgl_akhir + " 23:59:59.999'");
                map.put("bulan", nama_bulan);
                map.put("tahun", tahun);

                Object[] ob = getJumlah(param_tgl_awal, param_tgl_akhir, null);

                map.put("total_jual", setDotsCurrency(Double.parseDouble(ob[0].toString())));
                map.put("total_pokok", setDotsCurrency(Double.parseDouble(ob[1].toString())));
                map.put("total_laba", setDotsCurrency(Double.parseDouble(ob[2].toString())));

//            filling jasper report
                JasperReport jasperReport = JasperCompileManager.compileReport(file);
                JasperPrint print = JasperFillManager.fillReport(jasperReport, map, conn);
                JasperViewer.viewReport(print, false);

            } else {
                // Print jenis barang tertentu
                String id_jenis = id_jenis_temp.split(" - ")[0];
                System.out.println("Id jenis  : "+id_jenis);
                // Menentukan awal dan akhir bulan periode
                String angka_bulan = cb_bulan.getSelectedItem().toString().split(" - ")[0];
                int int_bulan = Integer.parseInt(angka_bulan.trim());
                String nama_bulan = cb_bulan.getSelectedItem().toString().split(" - ")[1];
                String tahun = tf_tahun.getText();
                String param_tgl_awal = tahun.trim() + "-" + angka_bulan.trim() + "-1";
                String param_tgl_awal_dmyyyy = "1" + "/" + angka_bulan.trim() + "/" + tahun.trim();

                //Mengambil data akhir bulan 
                LocalDate convertedData = LocalDate.parse(param_tgl_awal_dmyyyy, DateTimeFormatter.ofPattern("d/M/yyyy"));
                convertedData = convertedData.withDayOfMonth(convertedData.getMonth().length(convertedData.isLeapYear()));
                String param_tgl_akhir = convertedData.toString();

                // Penentuan lokasi file jasper
                String file = "src/struk/report_bulanan.jrxml";

                // Passing variabel sbg parameter
                HashMap map = new HashMap();
                map.put("query", "select CAST(tanggal_pembelian AS DATE) as tanggal, id_transaksi, barang.nama_barang, qty, (barang.harga_jual * qty) as nilai_jual, (barang.harga_pokok * qty) as nilai_pokok, ((barang.harga_jual * qty) - (barang.harga_pokok * qty)) as laba "
                        + "from transaksi, barang "
                        + "where transaksi.id_barang=barang.id_barang and transaksi.tanggal_pembelian >= '" + param_tgl_awal + "' and transaksi.tanggal_pembelian <= '" + param_tgl_akhir + " 23:59:59.999' and barang.id_jenis_barang=" + id_jenis);
                map.put("bulan", nama_bulan);
                map.put("tahun", tahun);

                Object[] ob = getJumlah(param_tgl_awal, param_tgl_akhir, id_jenis);

                map.put("total_jual", setDotsCurrency(Double.parseDouble(ob[0].toString())));
                map.put("total_pokok", setDotsCurrency(Double.parseDouble(ob[1].toString())));
                map.put("total_laba", setDotsCurrency(Double.parseDouble(ob[2].toString())));

//            filling jasper report
                JasperReport jasperReport = JasperCompileManager.compileReport(file);
                JasperPrint print = JasperFillManager.fillReport(jasperReport, map, conn);
                JasperViewer.viewReport(print, false);

            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e);
        }
        this.dispose();
    }//GEN-LAST:event_btn_cetakActionPerformed

    private void cb_bulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_bulanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_bulanActionPerformed

    private void cb_jenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_jenisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_jenisActionPerformed

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
            java.util.logging.Logger.getLogger(FrameOpsiCetakPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameOpsiCetakPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameOpsiCetakPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameOpsiCetakPopUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameOpsiCetakPopUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cetak;
    private javax.swing.JComboBox cb_bulan;
    private javax.swing.JComboBox cb_jenis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_batal;
    private javax.swing.JTextField tf_tahun;
    // End of variables declaration//GEN-END:variables
}
