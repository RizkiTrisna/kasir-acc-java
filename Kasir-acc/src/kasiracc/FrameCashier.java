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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

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
    private static DefaultTableModel tmodelBarang;
    private static DefaultTableModel tmodelKeranjang;

    private static Transaksi temp_transaksi;

    private int kembalian;

    public FrameCashier() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        initComponents();
        try {
            getKoneksi();
        } catch (SQLException ex) {
            Logger.getLogger(FrameInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
        prepareTableBarang();
        prepareTableKeranjang();
        tampilTabelBarang();
        tampilKeranjang();

    }

    private void setKembalian(int kembalian) {
        this.kembalian = kembalian;
    }

    private int getKembalian() {
        int total = getSumSubtotalKeranjang();
        int diskon = 0;
        int dibayarkan = 0;
        int kembalian = 0;
        System.out.println("hasil kondisi : " + (tf_cashier_diskon.equals("")));
        if (!tf_cashier_diskon.getText().equals("")) {
            diskon = Integer.parseInt(tf_cashier_diskon.getText());
        }

        if (!tf_cashier_dibayarkan.getText().equals("")) {
            dibayarkan = Integer.parseInt(tf_cashier_dibayarkan.getText());
        }

        kembalian = dibayarkan - total + diskon;
        return kembalian;
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

    private static void prepareTableBarang() {
        tmodelBarang = new DefaultTableModel();
        tabel_barang.setModel(tmodelBarang);
        tmodelBarang.addColumn("ID");
        tmodelBarang.addColumn("Nama barang");
        tmodelBarang.addColumn("Jenis barang");
        tmodelBarang.addColumn("Harga jual");
        tmodelBarang.addColumn("Sisa stok");
    }

    public static void refreshTabelBarang() {
        prepareTableBarang();
        tampilTabelBarang();
    }

    private static void prepareTableKeranjang() {
        tmodelKeranjang = new DefaultTableModel();
        tabel_keranjang.setModel(tmodelKeranjang);
        tmodelKeranjang.addColumn("ID");
        tmodelKeranjang.addColumn("Nama barang");
        tmodelKeranjang.addColumn("Qty");
        tmodelKeranjang.addColumn("Harga");
        tmodelKeranjang.addColumn("Subtotal");
    }

    public static void refreshKeranjang() {
        prepareTableKeranjang();
        tampilKeranjang();
    }

    public static void refreshDetailPembayaran() {

        int diskon = 0;
        int dibayarkan = 0;
        int subtotal = getSumSubtotalKeranjang();

        if (!tf_cashier_diskon.getText().equals("")) {
            diskon = Integer.parseInt(tf_cashier_diskon.getText());
        }

        if (!tf_cashier_dibayarkan.getText().equals("")) {
            dibayarkan = Integer.parseInt(tf_cashier_dibayarkan.getText());
        }

        if (diskon <= subtotal) {
            int total = subtotal - diskon;
            lbl_cashier_total.setText(convertToCurrency(total));
            if (dibayarkan > total) {
                int kembalian = dibayarkan - total;
                lbl_cashier_kembalian.setText(convertToCurrency(kembalian));
            } else {
                lbl_cashier_kembalian.setText(convertToCurrency(0));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Diskon melebihi harga keseluruhan");
            lbl_cashier_total.setText(convertToCurrency(0));
        }

    }

    private static void tampilTabelBarang() {
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
                o[3] = rs.getInt("harga_jual");
                o[4] = rs.getInt("stok_barang");
                tmodelBarang.addRow(o);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void tampilKeranjang() {
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select barang.nama_barang as nama_barang, harga_jual, stok_barang, temp_transaksi.id_barang as id_barang, temp_transaksi.id_admin as id_admin, qty, tanggal_pembelian "
                    + "from barang, temp_transaksi, admin "
                    + "WHERE barang.id_barang=temp_transaksi.id_barang and temp_transaksi.id_admin=admin.id_admin order by id_barang";

            rs = stmt.executeQuery(query);
            int all_subtotal = 0;
            int all_total = 0;

            while (rs.next()) {
                Object[] o = new Object[6];
                int id_barang = rs.getInt("id_barang");
                String nama_barang = rs.getString("nama_barang");
                int qty = rs.getInt("qty");
                int harga_jual = rs.getInt("harga_jual");
                int subtotal = qty * harga_jual;
                o[0] = id_barang;
                o[1] = nama_barang;
                o[2] = qty;
                o[3] = harga_jual;
                o[4] = subtotal;
                tmodelKeranjang.addRow(o);
                all_subtotal += subtotal;
            }
            if (!tf_cashier_diskon.getText().equals("")) {
                all_total = all_subtotal - Integer.parseInt(tf_cashier_diskon.getText());

            } else {
                all_total = all_subtotal;
            }

            //Tampilkan ke label
            lbl_subtotal.setText(convertToCurrency((double) all_subtotal));
            lbl_cashier_total.setText(convertToCurrency((double) all_total));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getSumSubtotalKeranjang() {
        int all_subtotal = 0;
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select harga_jual, qty from barang, temp_transaksi where barang.id_barang=temp_transaksi.id_barang";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Object[] o = new Object[6];
                int qty = rs.getInt("qty");
                int harga_jual = rs.getInt("harga_jual");
                int subtotal = qty * harga_jual;
                all_subtotal += subtotal;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return all_subtotal;
    }

    private static String convertToCurrency(double number) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String result = kursIndonesia.format(number);

        return result;
    }

    private static String setDotsCurrency(double number) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String result = kursIndonesia.format(number);

        return result;
    }

    private Map<Integer, Map<String, String>> tampilTabelBarang(String indexCari) {
        try {

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select barang.id_barang as id_barang, barang.nama_barang as nama_barang, jenis_barang.nama_jenis as nama_jenis, harga_jual, harga_pokok, stok_barang from barang, jenis_barang "
                    + "WHERE barang.id_jenis_barang=jenis_barang.id_jenis and (barang.id_barang='" + indexCari + "' or jenis_barang.nama_jenis like '%" + indexCari + "%' or barang.nama_barang like '%" + indexCari + "%' )";

            rs = stmt.executeQuery(query);

            // Storage induk 
            Map<Integer, Map<String, String>> map_induk = new HashMap<>();
            int index = 0;
            while (rs.next()) {
                Object[] o = new Object[6];
                //Storage child
                Map<String, String> map_child = new HashMap<>();
                int id_barang = rs.getInt("id_barang");
                String nama_barang = rs.getString("nama_barang");
                String nama_jenis = rs.getString("nama_jenis");
                int harga_jual = rs.getInt("harga_jual");
                int harga_pokok = rs.getInt("harga_pokok");
                int stok_barang = rs.getInt("stok_barang");

                // inisialisasi object yang akan menjadi data isi tabel
                o[0] = id_barang;
                o[1] = nama_barang;
                o[2] = nama_jenis;
                o[3] = harga_jual;
                o[4] = stok_barang;
                tmodelBarang.addRow(o);

                // inisialisasi data di map induk
                map_child.put("id_barang", String.valueOf(id_barang));
                map_child.put("nama_barang", nama_barang);
                map_child.put("nama_jenis", nama_jenis);
                map_child.put("harga_jual", String.valueOf(harga_jual));
                map_child.put("harga_pokok", String.valueOf(harga_pokok));
                map_child.put("stok_barang", String.valueOf(stok_barang));
                map_induk.put(index, map_child);
                index++;
            }
            if (!map_induk.isEmpty()) {
                return map_induk;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean truncateTemp() {
        try {
            String query = "Truncate table temp_transaksi";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal ditambahkan ke keranjang\n Error: " + e);
            e.printStackTrace();
            return false;
        }
    }

    private void resetPembayaran() {

        tf_cashier_diskon.setText("");
        tf_cashier_dibayarkan.setText("");
        lbl_cashier_kembalian.setText(convertToCurrency(0));
    }

    private boolean tambahTransaksi(int id_barang, int id_admin, int qty) {

        try {
            String query = "Insert into transaksi(id_barang, id_admin, qty) VALUES(" + id_barang + ", " + id_admin + ", " + qty + ")";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean finalizeTransaksi() {
        try {
            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select barang.nama_barang as nama_barang, harga_jual, stok_barang, temp_transaksi.id_barang as id_barang, temp_transaksi.id_admin as id_admin, qty, tanggal_pembelian "
                    + "from barang, temp_transaksi, admin "
                    + "WHERE barang.id_barang=temp_transaksi.id_barang and temp_transaksi.id_admin=admin.id_admin order by id_barang";

            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id_barang = rs.getInt("id_barang");
                int id_admin = rs.getInt("id_admin");
                String nama_barang = rs.getString("nama_barang");
                int qty = rs.getInt("qty");
                int harga_jual = rs.getInt("harga_jual");
                if (tambahTransaksi(id_barang, id_admin, qty) == false) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mencatat transaksi dalam keranjang\nError:" + e);
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        nav_cashier_selected = new javax.swing.JButton();
        nav_inventory = new javax.swing.JButton();
        nav_stat = new javax.swing.JButton();
        nav_logout = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        tf_cashier_cari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_barang = new javax.swing.JTable();
        btn_batal_beli = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_keranjang = new javax.swing.JTable();
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
        lbl_cashier_total = new javax.swing.JLabel();
        lbl_subtotal = new javax.swing.JLabel();
        lbl_cashier_kembalian = new javax.swing.JLabel();
        tf_cashier_dibayarkan = new javax.swing.JTextField();
        tf_cashier_diskon = new javax.swing.JTextField();
        btn_proses_transaksi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(70, 87, 117));

        nav_cashier_selected.setBackground(new java.awt.Color(13, 15, 21));
        nav_cashier_selected.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/icon_cart_only.png"))); // NOI18N

        nav_inventory.setBackground(new java.awt.Color(70, 87, 117));
        nav_inventory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/icon_box_only.png"))); // NOI18N
        nav_inventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nav_inventoryActionPerformed(evt);
            }
        });

        nav_stat.setBackground(new java.awt.Color(70, 87, 117));
        nav_stat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/icon_graph_only.png"))); // NOI18N
        nav_stat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nav_statActionPerformed(evt);
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
                    .addComponent(nav_inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nav_stat, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nav_cashier_selected, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nav_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(nav_cashier_selected, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(nav_inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(nav_stat, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(500, 500, 500)
                .addComponent(nav_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(245, 249, 252));

        tf_cashier_cari.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        tf_cashier_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_cashier_cariActionPerformed(evt);
            }
        });
        tf_cashier_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tf_cashier_cariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_cashier_cariKeyReleased(evt);
            }
        });

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
    tabel_barang.setRowHeight(24);
    tabel_barang.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tabel_barangMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(tabel_barang);

    btn_batal_beli.setBackground(new java.awt.Color(251, 136, 134));
    btn_batal_beli.setFont(new java.awt.Font("Assistant SemiBold", 0, 26)); // NOI18N
    btn_batal_beli.setForeground(new java.awt.Color(255, 255, 255));
    btn_batal_beli.setText("Batalkan Pembelian");
    btn_batal_beli.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_batal_beliActionPerformed(evt);
        }
    });

    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/icon_refresh.png"))); // NOI18N
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
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
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(41, 41, 41)
                    .addComponent(btn_batal_beli, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(50, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(100, 100, 100)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tf_cashier_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_batal_beli, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(55, 55, 55)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(94, Short.MAX_VALUE))
    );

    jPanel3.setBackground(new java.awt.Color(255, 255, 255));

    tabel_keranjang.setFont(new java.awt.Font("Assistant", 0, 20)); // NOI18N
    tabel_keranjang.setModel(new javax.swing.table.DefaultTableModel(
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
    tabel_keranjang.setRowHeight(24);
    tabel_keranjang.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tabel_keranjangMouseClicked(evt);
        }
    });
    jScrollPane2.setViewportView(tabel_keranjang);

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
    tf_cashier_dibayarkan.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            tf_cashier_dibayarkanKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            tf_cashier_dibayarkanKeyTyped(evt);
        }
    });

    tf_cashier_diskon.setFont(new java.awt.Font("Assistant SemiBold", 0, 32)); // NOI18N
    tf_cashier_diskon.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            tf_cashier_diskonKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            tf_cashier_diskonKeyTyped(evt);
        }
    });

    btn_proses_transaksi.setBackground(new java.awt.Color(121, 232, 189));
    btn_proses_transaksi.setFont(new java.awt.Font("Assistant SemiBold", 0, 42)); // NOI18N
    btn_proses_transaksi.setForeground(new java.awt.Color(255, 255, 255));
    btn_proses_transaksi.setText("Proses Transaksi");
    btn_proses_transaksi.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btn_proses_transaksiActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(50, 50, 50)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 773, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addContainerGap(49, Short.MAX_VALUE))
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_proses_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(260, 260, 260))
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
            .addGap(26, 26, 26)
            .addComponent(btn_proses_transaksi)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_cashier_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_cashier_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_cashier_cariActionPerformed

    private void tf_cashier_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_cashier_cariKeyReleased
        String indexCari = tf_cashier_cari.getText();
        prepareTableBarang();
        Map<Integer, Map<String, String>> data_cari_barang = new HashMap<>();
        data_cari_barang = tampilTabelBarang(indexCari);

        if (evt.getKeyCode() == KeyEvent.VK_ENTER && data_cari_barang != null) {
            int index_selected = 0;
            Barang.setId_barang(Integer.parseInt(data_cari_barang.get(index_selected).get("id_barang")));
            Barang.setNama_barang(data_cari_barang.get(index_selected).get("nama_barang"));
            Barang.setHarga_jual(Integer.parseInt(data_cari_barang.get(index_selected).get("harga_jual")));
            Barang.setHarga_pokok(Integer.parseInt(data_cari_barang.get(index_selected).get("harga_pokok")));
            Barang.setStok(Integer.parseInt(data_cari_barang.get(index_selected).get("stok_barang")));

            new FrameTableSearchCashierPopUp().setVisible(true);
        }
    }//GEN-LAST:event_tf_cashier_cariKeyReleased

    private void tabel_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_barangMouseClicked
        // Mengambil data id barang di tabel
        int col = 0;
        int row = tabel_barang.getSelectedRow();
        int id_barang = Integer.parseInt(tabel_barang.getValueAt(row, col).toString());
        Object[] ob = cariBarang(id_barang);

        Barang.setId_barang(id_barang);
        Barang.setNama_barang(ob[1].toString());
        Barang.setHarga_jual(Integer.parseInt(ob[4].toString()));
        Barang.setHarga_pokok(Integer.parseInt(ob[3].toString()));
        Barang.setStok(Integer.parseInt(ob[5].toString()));

        new FrameTableSearchCashierPopUp().setVisible(true);
    }//GEN-LAST:event_tabel_barangMouseClicked

    private void tf_cashier_diskonKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_cashier_diskonKeyTyped
        filterAngka(evt);

    }//GEN-LAST:event_tf_cashier_diskonKeyTyped

    private void tf_cashier_dibayarkanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_cashier_dibayarkanKeyTyped
        filterAngka(evt);
    }//GEN-LAST:event_tf_cashier_dibayarkanKeyTyped

    private void tf_cashier_diskonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_cashier_diskonKeyReleased
        if (!tf_cashier_diskon.getText().equals("")) {
            int diskon = Integer.parseInt(tf_cashier_diskon.getText());
            int subtotal_keranjang = getSumSubtotalKeranjang();

            if (!tf_cashier_dibayarkan.getText().equals("")) {
                // Jika kolom pembayaran sudah diisi
                int total = subtotal_keranjang - diskon;
                int dibayarkan = Integer.parseInt(tf_cashier_dibayarkan.getText());
                if (dibayarkan < total) {
                    //tampilkan 0 di kembalian
                    lbl_cashier_kembalian.setText(convertToCurrency(0));

                } else {
                    int kembalian = total - dibayarkan;

                    if (diskon > subtotal_keranjang) {
                        JOptionPane.showMessageDialog(null, "Diskon melebihi harga keseluruhan");
                        lbl_cashier_kembalian.setText(convertToCurrency(kembalian));
                        lbl_cashier_total.setText(convertToCurrency(0));
                    } else {
                        lbl_cashier_kembalian.setText(convertToCurrency(kembalian));
                        lbl_cashier_total.setText(convertToCurrency(total));
                    }
                }

            } else {
                // Jika kolom pembayaran belum diisi  
                if (diskon > subtotal_keranjang) {
                    JOptionPane.showMessageDialog(null, "Diskon melebihi harga keseluruhan");
                    lbl_cashier_total.setText(convertToCurrency(0));
                } else {
                    int total = subtotal_keranjang - diskon;
                    lbl_cashier_total.setText(convertToCurrency(total));
                }
            }

        } else {
            lbl_cashier_total.setText(convertToCurrency(getSumSubtotalKeranjang()) + "");
        }

    }//GEN-LAST:event_tf_cashier_diskonKeyReleased

    private void tf_cashier_dibayarkanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_cashier_dibayarkanKeyReleased
        try {

            if (!tf_cashier_dibayarkan.getText().equals("")) {
                if (!tf_cashier_diskon.getText().equals("")) {
                    int dibayarkan = Integer.parseInt(tf_cashier_dibayarkan.getText());
                    int total = getSumSubtotalKeranjang() - Integer.parseInt(tf_cashier_diskon.getText());
                    if (!(dibayarkan <= total)) {
                        total -= Integer.parseInt(tf_cashier_dibayarkan.getText());
                        lbl_cashier_kembalian.setText(convertToCurrency(total));
                    } else {
                        lbl_cashier_kembalian.setText(convertToCurrency(0));
                    }
                } else {
                    int dibayarkan = Integer.parseInt(tf_cashier_dibayarkan.getText());
                    int total = getSumSubtotalKeranjang();
                    if (!(dibayarkan <= total)) {
                        total -= Integer.parseInt(tf_cashier_dibayarkan.getText());
                        lbl_cashier_kembalian.setText(convertToCurrency(total));
                    } else {
                        lbl_cashier_kembalian.setText(convertToCurrency(0));
                    }

                }
            } else {
                lbl_cashier_kembalian.setText(convertToCurrency(0));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat jumlah numerikal input di salah satu field melebihi standar.\n Error: " + e);
        }
    }//GEN-LAST:event_tf_cashier_dibayarkanKeyReleased

    private void tabel_keranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_keranjangMouseClicked
        // Mengambil data id barang di tabel
        int col = 0;
        int row = tabel_keranjang.getSelectedRow();

        System.out.println("col: " + col + "\t row: " + row);
        int id_barang = Integer.parseInt(tabel_keranjang.getValueAt(row, col).toString());
        System.out.println("id_barang : " + id_barang);
//        Object[] ob = cariBarang(id_barang);
//
//        Barang.setId_barang(id_barang);
//        Barang.setNama_barang(ob[1].toString());
//        Barang.setHarga_jual(Integer.parseInt(ob[4].toString()));
//        Barang.setHarga_pokok(Integer.parseInt(ob[3].toString()));
//        Barang.setStok(Integer.parseInt(ob[5].toString()));
        Object[] ob = cariTempTransaksi(id_barang);
        Transaksi.setId_transaksi(Integer.parseInt(ob[0].toString()));
        Transaksi.setId_barang(Integer.parseInt(ob[1].toString()));
        Transaksi.setId_admin(Integer.parseInt(ob[2].toString()));
        Transaksi.setQty(Integer.parseInt(ob[3].toString()));
        Transaksi.setTanggal_pembelian(ob[4].toString());
        new FrameTableEnteredCashierPopUp().setVisible(true);
    }//GEN-LAST:event_tabel_keranjangMouseClicked

    private void btn_proses_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_proses_transaksiActionPerformed
        //        new FrameCetakStrukPopUp().setVisible(true);

        try {
            String file = "src/struk/strukbelanja.jrxml";
            HashMap parameter = new HashMap();
            int diskon = 0;
            int total = getSumSubtotalKeranjang();
            int dibayarkan = 0;
            int kembalian = getKembalian();

            if (!tf_cashier_diskon.getText().equals("")) {
                System.out.println("diskon ada");
                diskon = Integer.parseInt(tf_cashier_diskon.getText());
            }

            if (!tf_cashier_dibayarkan.getText().equals("")) {
                System.out.println("dibayarkan masuk");
                dibayarkan = Integer.parseInt(tf_cashier_dibayarkan.getText());

                if ((dibayarkan - total + diskon) < 0) {
                    // Uang tidak cukup
                    JOptionPane.showMessageDialog(null, "Jumlah tunai kurang dari total keseluruhan belanja.");
                } else {
                    // Uang cukup
                    if (JOptionPane.showConfirmDialog(null, "Proses Transaksi?\nTransaksi akan tercatat ketika anda memilih \"Ya\"", null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

                        parameter.put("total", total + "");
                        parameter.put("diskon", diskon + "");
                        parameter.put("dibayarkan", dibayarkan + "");
                        parameter.put("kembalian", kembalian + "");

                        JasperReport jasperReport = JasperCompileManager.compileReport(file);
                        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameter, conn);
                        JasperViewer.viewReport(print, false);

                        //Pindah dari tabel temp ke tabel transaksi
                        if (finalizeTransaksi() == true) {
                            truncateTemp();
                            refreshKeranjang();
                            resetPembayaran();
                        } else {
                            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat memproses pembelian.");
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nominal pembayaran belum diisi");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat memproses pembelian.\n Error: " + e);
            e.printStackTrace();
        }

//            prosesTransaksi();

    }//GEN-LAST:event_btn_proses_transaksiActionPerformed

    private void btn_batal_beliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batal_beliActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin membatalkan transaksi ini?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            // Ambil semua data dari tabel temp_transaksi
            try {

                // buat objek statement
                Statement stmt = conn.createStatement();

                // buat query ke database
                String query = "Select * from temp_transaksi";

                ResultSet rs = stmt.executeQuery(query);

                // Iterasi seluruh isi tabel temp_transaksi
                while (rs.next()) {

                    // Cari barang by id barang di tabel barang
                    int id_barang = rs.getInt("id_barang");
                    Object[] obBarang = cariBarang(id_barang);

                    // Ambil stok lama
                    int stok_lama = Integer.parseInt(obBarang[5].toString());

                    // Tambahkan stok lama dengan qty dari tabel temp_transaksi by id_barang
                    int qty_lama = rs.getInt("qty");
                    int stok_baru = stok_lama + qty_lama;

                    // Update tabel stok lama di tabel barang dengan set stok lama di set stok baru 
                    String query_update = "update barang set stok_barang=" + stok_baru + " where id_barang=" + id_barang;
                    PreparedStatement pst = conn.prepareStatement(query_update);
                    pst.execute();

                }

                // Truncate tabel temp_transaksi
                truncateTemp();

                // Reset pembayaran
                resetPembayaran();

                // Refresh tabel keranjang dan tabel barang
                refreshTabelBarang();
                refreshKeranjang();
                refreshDetailPembayaran();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat membatalkan pembelian\n Error: " + e);
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_btn_batal_beliActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        refreshTabelBarang();
        tf_cashier_cari.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void nav_statActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nav_statActionPerformed
        // TODO add your handling code here:
        new FrameStat().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_nav_statActionPerformed

    private void nav_inventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nav_inventoryActionPerformed
        new FrameInventory().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_nav_inventoryActionPerformed

    private void nav_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nav_logoutActionPerformed

        if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin menutup aplikasi ini?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION && session.endSession()) {
            System.exit(0);
        }
    }//GEN-LAST:event_nav_logoutActionPerformed

    private void tf_cashier_cariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_cashier_cariKeyPressed

    }//GEN-LAST:event_tf_cashier_cariKeyPressed
    private Object[] cariTempTransaksi(int id_barang) {
        try {
            Object[] o = new Object[5];

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select * from temp_transaksi where id_barang=" + id_barang;

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                o[0] = rs.getInt("id_temp") + "";
                o[1] = rs.getInt("id_barang");
                o[2] = rs.getInt("id_admin");
                o[3] = rs.getInt("qty");
                o[4] = rs.getInt("tanggal_pembelian");
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    private javax.swing.JButton btn_batal_beli;
    private javax.swing.JButton btn_proses_transaksi;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JLabel lbl_cashier_kembalian;
    private static javax.swing.JLabel lbl_cashier_total;
    private static javax.swing.JLabel lbl_subtotal;
    private javax.swing.JButton nav_cashier_selected;
    private javax.swing.JButton nav_inventory;
    private javax.swing.JButton nav_logout;
    private javax.swing.JButton nav_stat;
    private static javax.swing.JTable tabel_barang;
    private static javax.swing.JTable tabel_keranjang;
    private javax.swing.JTextField tf_cashier_cari;
    private static javax.swing.JTextField tf_cashier_dibayarkan;
    private static javax.swing.JTextField tf_cashier_diskon;
    // End of variables declaration//GEN-END:variables
}
