/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

import java.awt.List;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
        tampilDetail();
//        updatePembelianTerbanyak(); 
    }

    private void tampilDetail() {
        // Menentukan awal dan akhir bulan periode
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        String angka_bulan = dtf.format(now).toString().split("/")[1];
        String tahun = dtf.format(now).toString().split("/")[0];
        String param_tgl_awal = tahun.trim() + "-" + angka_bulan.trim() + "-1";
        String param_tgl_awal_dmyyyy = "1" + "/" + angka_bulan.trim() + "/" + tahun.trim();

        //Mengambil data akhir bulan 
        LocalDate convertedData = LocalDate.parse(param_tgl_awal_dmyyyy, DateTimeFormatter.ofPattern("d/M/yyyy"));
        convertedData = convertedData.withDayOfMonth(convertedData.getMonth().length(convertedData.isLeapYear()));
        String param_tgl_akhir = convertedData.toString();

        tampilPendapatanSebulan();
        tampilQtySebulan();
        tampilPalingBanyakDibeli();
    }

    private void tampilPendapatanSebulan() {
        // select data selama sebulan terakhir
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "select sum((barang.harga_jual * qty)) as nilai_kotor, sum((barang.harga_pokok * qty)) as nilai_bersih from transaksi, barang where transaksi.id_barang=barang.id_barang and DATEDIFF(CURRENT_TIMESTAMP, transaksi.tanggal_pembelian) BETWEEN 0 AND 30";

            rs = stmt.executeQuery(query);

            int kotor_sebulan = 0;
            int bersih_sebulan = 0;

            while (rs.next()) {
                kotor_sebulan = rs.getInt("nilai_kotor");
                bersih_sebulan = rs.getInt("nilai_bersih");
            }
            lbl_total_pendapatan.setText(setDotsCurrency(Double.parseDouble(String.valueOf(kotor_sebulan))));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void tampilQtySebulan() {
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "select count(qty) as n_qty from transaksi where DATEDIFF(CURRENT_TIMESTAMP, transaksi.tanggal_pembelian) BETWEEN 0 AND 30";

            rs = stmt.executeQuery(query);

            int n_qty = 0;

            while (rs.next()) {
                n_qty = rs.getInt("n_qty");
            }
            lbl_banyak_30_transaksi.setText(String.valueOf(n_qty));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tampilPalingBanyakDibeli() {
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "select sum(qty) as total_qty, transaksi.id_barang, nama_barang from transaksi, barang "
                    + "where transaksi.id_barang=barang.id_barang and DATEDIFF(CURRENT_TIMESTAMP, transaksi.tanggal_pembelian) "
                    + "BETWEEN 0 AND 30 GROUP BY id_barang Order By id_barang";

            rs = stmt.executeQuery(query);

            // Storage pencarian terbanyak
            Map<Integer, Map<String, String>> map_induk = new HashMap<>();

            // inisialisasi index
            int index = 0;
            int index_terbanyak = 0;

            //temporary data
            int temp_total = 0;
            while (rs.next()) {

                int total_qty = rs.getInt("total_qty");
                int id_barang = rs.getInt("id_barang");
                String nama_barang = rs.getString("nama_barang");

                Map<String, String> map_child = new HashMap<>();

                map_child.put("total_qty", String.valueOf(total_qty));
                map_child.put("id_barang", String.valueOf(id_barang));
                map_child.put("nama_barang", nama_barang);

                map_induk.put(index, map_child);

                if (total_qty > temp_total) {
                    temp_total = total_qty;
                    index_terbanyak = index;
                }
                index++;
            }

            System.out.println("index terbanyak : " + index_terbanyak);
            System.out.println("Dengan nama barang : " + map_induk.get(index_terbanyak).get("nama_barang"));

            // set label
            String temp_nama_barang = map_induk.get(index_terbanyak).get("nama_barang");
            String nama_barang = temp_nama_barang;
            
            //substring nama barang jika terlalu panjang
            if (temp_nama_barang.length() > 20) {
                nama_barang = temp_nama_barang.substring(0, 20);
            }
            
//            set label
            lbl_paling_banyak_dibeli.setText(nama_barang);
            lbl_n_banyak_dibeli.setText(map_induk.get(index_terbanyak).get("total_qty").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    + "AND (transaksi.id_transaksi='" + indexCari + "' or barang.nama_barang LIKE '%" + indexCari + "%' or jenis_barang.nama_jenis LIKE '%" + indexCari + "%' or admin.nama_admin LIKE '%" + indexCari + "%' or transaksi.tanggal_pembelian LIKE '%" + indexCari + "%') order by id_transaksi";

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
        nav_cashier = new javax.swing.JButton();
        nav_inventory = new javax.swing.JButton();
        nav_stat_selected = new javax.swing.JButton();
        nav_logout = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        tf_cari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_history = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_banyak_30_transaksi = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lbl_paling_banyak_dibeli = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        lbl_total_pendapatan = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_n_banyak_dibeli = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(70, 87, 117));

        nav_cashier.setBackground(new java.awt.Color(70, 87, 117));
        nav_cashier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/icon_cart_only.png"))); // NOI18N
        nav_cashier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nav_cashierActionPerformed(evt);
            }
        });

        nav_inventory.setBackground(new java.awt.Color(70, 87, 117));
        nav_inventory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/icon_box_only.png"))); // NOI18N
        nav_inventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nav_inventoryActionPerformed(evt);
            }
        });

        nav_stat_selected.setBackground(new java.awt.Color(13, 15, 21));
        nav_stat_selected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/icon_graph_only.png"))); // NOI18N
        nav_stat_selected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nav_stat_selectedActionPerformed(evt);
            }
        });

        nav_logout.setBackground(new java.awt.Color(70, 87, 117));
        nav_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/Icon_feather_power.png"))); // NOI18N
        nav_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nav_logoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nav_stat_selected, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nav_inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nav_cashier, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nav_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(nav_cashier, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(nav_inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(nav_stat_selected, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(500, 500, 500)
                .addComponent(nav_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(112, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(245, 249, 252));

        tf_cari.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        tf_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_cariActionPerformed(evt);
            }
        });
        tf_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_cariKeyPressed(evt);
            }
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
        tabel_history.setRowHeight(24);
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

        lbl_banyak_30_transaksi.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        lbl_banyak_30_transaksi.setForeground(new java.awt.Color(112, 112, 112));
        lbl_banyak_30_transaksi.setText("0");

        jLabel9.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(112, 112, 112));
        jLabel9.setText("Paling banyak dibeli/30 hari");

        lbl_paling_banyak_dibeli.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        lbl_paling_banyak_dibeli.setForeground(new java.awt.Color(112, 112, 112));
        lbl_paling_banyak_dibeli.setText("XXXXXXXXXX");

        jLabel20.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(112, 112, 112));
        jLabel20.setText(":");

        jLabel10.setFont(new java.awt.Font("Assistant", 0, 24)); // NOI18N
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
        lbl_total_pendapatan.setText("0000000");

        jLabel11.setFont(new java.awt.Font("Assistant", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(112, 112, 112));
        jLabel11.setText("(");

        lbl_n_banyak_dibeli.setFont(new java.awt.Font("Assistant", 0, 24)); // NOI18N
        lbl_n_banyak_dibeli.setForeground(new java.awt.Color(112, 112, 112));
        lbl_n_banyak_dibeli.setText("XXX");

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
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl_banyak_30_transaksi)
                                .addComponent(lbl_total_pendapatan))
                            .addGap(205, 205, 205)
                            .addComponent(jLabel9)
                            .addGap(22, 22, 22)
                            .addComponent(jLabel20)
                            .addGap(32, 32, 32)
                            .addComponent(lbl_paling_banyak_dibeli)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbl_n_banyak_dibeli)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel10)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1706, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel12)
                            .addComponent(jLabel9)
                            .addComponent(jLabel20)
                            .addComponent(lbl_paling_banyak_dibeli)
                            .addComponent(jLabel10)
                            .addComponent(lbl_n_banyak_dibeli)
                            .addComponent(jLabel11)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbl_banyak_30_transaksi)))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_cariActionPerformed

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

    private void nav_cashierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nav_cashierActionPerformed
        FrameCashier fc = new FrameCashier();
        fc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_nav_cashierActionPerformed

    private void nav_stat_selectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nav_stat_selectedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nav_stat_selectedActionPerformed

    private void nav_inventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nav_inventoryActionPerformed
        FrameInventory fi = new FrameInventory();
        fi.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_nav_inventoryActionPerformed

    private void nav_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nav_logoutActionPerformed

        if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin menutup aplikasi ini?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION && session.endSession()) {
            System.exit(0);
        }
    }//GEN-LAST:event_nav_logoutActionPerformed

    private void tf_cariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_cariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            
        }
    }//GEN-LAST:event_tf_cariKeyPressed

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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_banyak_30_transaksi;
    private javax.swing.JLabel lbl_n_banyak_dibeli;
    private javax.swing.JLabel lbl_paling_banyak_dibeli;
    private javax.swing.JLabel lbl_total_pendapatan;
    private javax.swing.JButton nav_cashier;
    private javax.swing.JButton nav_inventory;
    private javax.swing.JButton nav_logout;
    private javax.swing.JButton nav_stat_selected;
    private javax.swing.JTable tabel_history;
    private javax.swing.JTextField tf_cari;
    // End of variables declaration//GEN-END:variables
}
