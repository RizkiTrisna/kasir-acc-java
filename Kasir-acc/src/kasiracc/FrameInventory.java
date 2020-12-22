/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasiracc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import kasiracc.koneksi;

/**
 *
 * @author Rizki Trisna
 */
public class FrameInventory extends javax.swing.JFrame {

    /**
     * Creates new form FrameInventory
     */
    static final String jdbc_driver = "com.mysql.jdbc.Driver";
    static final String db_url = "jdbc:mysql://localhost/acc_kasir";
    static final String user = "root";
    static final String pass = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    private DefaultTableModel tmodel;

    private ArrayList dataCbb = new ArrayList();

    public FrameInventory() {
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
            // register driver
            Class.forName(jdbc_driver);

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

    private void tampilTabelBarang() {
        try {

            // register driver
            Class.forName(jdbc_driver);

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select barang.id_barang as id_barang, barang.nama_barang as nama_barang, jenis_barang.nama_jenis as nama_jenis, harga_jual, harga_pokok, stok_barang from barang, jenis_barang WHERE barang.id_jenis_barang=jenis_barang.id_jenis";

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

            tampilCombobox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void tampilTabelBarang(String indexCari) {
        try {
            // register driver
            Class.forName(jdbc_driver);

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select barang.id_barang as id_barang, barang.nama_barang as nama_barang, jenis_barang.nama_jenis as nama_jenis, harga_jual, harga_pokok, stok_barang from barang, jenis_barang WHERE barang.id_jenis_barang=jenis_barang.id_jenis and (barang.id_barang='"+indexCari+"' or jenis_barang.nama_jenis like '%"+indexCari+"%' or barang.nama_barang like '$"+indexCari+"$' )";

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

            tampilCombobox();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tampilCombobox() {
        cb_inventory_jenis.removeAllItems();
        try {

            // register driver
            Class.forName(jdbc_driver);

            // buat koneksi ke database
            conn = DriverManager.getConnection(db_url, user, pass);

            // buat objek statement
            stmt = conn.createStatement();

            // buat query ke database
            String query = "Select * from jenis_barang";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id_jenis = rs.getInt("id_jenis");
                String nama_jenis = rs.getString("nama_jenis");
                String inputCbb = id_jenis + " - " + nama_jenis;

                dataCbb.add(nama_jenis);
                cb_inventory_jenis.addItem(inputCbb);

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
            prepareTable();
            tampilTabelBarang();

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
        jPanel2 = new javax.swing.JPanel();
        tf_inventory_cari = new javax.swing.JTextField();
        lbl_inventory_cari = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_barang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        tf_inventory_hrgPokok = new javax.swing.JTextField();
        tf_inventory_nmBarang = new javax.swing.JTextField();
        tf_inventory_hrgJual = new javax.swing.JTextField();
        tf_inventory_stok = new javax.swing.JTextField();
        lbl_inventory_cancel = new javax.swing.JLabel();
        lbl_inventory_simpan = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cb_inventory_jenis = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(70, 87, 117));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/Icon_awesome_shopping_cart.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/Icon_awesome_box_open.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/Icon_open_graph.png"))); // NOI18N
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
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addComponent(jLabel2)
                .addGap(49, 49, 49)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(82, 82, 82))
        );

        jPanel2.setBackground(new java.awt.Color(245, 249, 252));

        tf_inventory_cari.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
        tf_inventory_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_inventory_cariActionPerformed(evt);
            }
        });

        lbl_inventory_cari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_cari.png"))); // NOI18N
        lbl_inventory_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_inventory_cariMouseClicked(evt);
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
    tabel_barang.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            tabel_barangMouseClicked(evt);
        }
    });
    jScrollPane1.setViewportView(tabel_barang);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(50, 50, 50)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 842, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(tf_inventory_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addComponent(lbl_inventory_cari)))
            .addContainerGap(91, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(100, 100, 100)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lbl_inventory_cari)
                .addComponent(tf_inventory_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(55, 55, 55)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jPanel3.setBackground(new java.awt.Color(255, 255, 255));

    jLabel7.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel7.setForeground(new java.awt.Color(112, 112, 112));
    jLabel7.setText("Nama barang");

    jLabel8.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel8.setForeground(new java.awt.Color(112, 112, 112));
    jLabel8.setText("Jenis barang");

    jLabel9.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel9.setForeground(new java.awt.Color(112, 112, 112));
    jLabel9.setText("Harga pokok");

    jLabel10.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel10.setForeground(new java.awt.Color(112, 112, 112));
    jLabel10.setText("Harga jual");

    jLabel11.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel11.setForeground(new java.awt.Color(112, 112, 112));
    jLabel11.setText("Detail barang");

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

    jLabel17.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel17.setForeground(new java.awt.Color(112, 112, 112));
    jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/garis_total.png"))); // NOI18N

    jLabel16.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel16.setForeground(new java.awt.Color(112, 112, 112));
    jLabel16.setText("Stok");

    jLabel20.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel20.setForeground(new java.awt.Color(112, 112, 112));
    jLabel20.setText(":");

    tf_inventory_hrgPokok.setFont(new java.awt.Font("Assistant SemiBold", 0, 32)); // NOI18N

    tf_inventory_nmBarang.setFont(new java.awt.Font("Assistant SemiBold", 0, 32)); // NOI18N

    tf_inventory_hrgJual.setFont(new java.awt.Font("Assistant SemiBold", 0, 32)); // NOI18N

    tf_inventory_stok.setFont(new java.awt.Font("Assistant SemiBold", 0, 32)); // NOI18N

    lbl_inventory_cancel.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    lbl_inventory_cancel.setForeground(new java.awt.Color(112, 112, 112));
    lbl_inventory_cancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_cancel.png"))); // NOI18N
    lbl_inventory_cancel.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            lbl_inventory_cancelMouseClicked(evt);
        }
    });

    lbl_inventory_simpan.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    lbl_inventory_simpan.setForeground(new java.awt.Color(112, 112, 112));
    lbl_inventory_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/btn_simpan.png"))); // NOI18N
    lbl_inventory_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            lbl_inventory_simpanMouseClicked(evt);
        }
    });

    jLabel18.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel18.setForeground(new java.awt.Color(112, 112, 112));
    jLabel18.setText("Rp.");

    jLabel19.setFont(new java.awt.Font("Assistant", 1, 32)); // NOI18N
    jLabel19.setForeground(new java.awt.Color(112, 112, 112));
    jLabel19.setText("Rp.");

    cb_inventory_jenis.setFont(new java.awt.Font("Assistant", 1, 24)); // NOI18N
    cb_inventory_jenis.setForeground(new java.awt.Color(112, 112, 112));
    cb_inventory_jenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addGap(50, 50, 50)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 777, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel16)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                            .addComponent(jLabel14))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addGap(18, 18, 18)
                            .addComponent(tf_inventory_hrgJual, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(tf_inventory_stok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tf_inventory_hrgPokok)
                                .addComponent(cb_inventory_jenis, 0, 391, Short.MAX_VALUE)))))
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(44, 44, 44)
                            .addComponent(jLabel12))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(289, 289, 289)
                            .addComponent(jLabel11))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(363, 363, 363)
                            .addComponent(lbl_inventory_cancel)
                            .addGap(18, 18, 18)
                            .addComponent(lbl_inventory_simpan)))
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGap(41, 41, 41))
        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(437, Short.MAX_VALUE)
                .addComponent(tf_inventory_nmBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addGap(251, 251, 251)
            .addComponent(jLabel11)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel12))
                    .addGap(24, 24, 24)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(cb_inventory_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel14))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tf_inventory_hrgPokok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18))))
                        .addComponent(jLabel13)
                        .addComponent(jLabel8))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel15))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tf_inventory_hrgJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)))
                    .addGap(23, 23, 23)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(jLabel20)
                        .addComponent(tf_inventory_stok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(168, 168, 168)
                    .addComponent(lbl_inventory_cancel))
                .addComponent(lbl_inventory_simpan))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(388, 388, 388)
                .addComponent(tf_inventory_nmBarang)
                .addGap(1137, 1137, 1137)))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(0, 0, 0)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 0, Short.MAX_VALUE))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_inventory_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_inventory_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_inventory_cariActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        FrameCashier fc = new FrameCashier();
        fc.show();
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        FrameStat fs = new FrameStat();
        fs.show();
        this.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin menutup aplikasi ini?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void tabel_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_barangMouseClicked

        // Mengambil data id barang di tabel
        int col = 0;
        int row = tabel_barang.getSelectedRow();
        int id_barang = Integer.parseInt(tabel_barang.getValueAt(row, col).toString());
        Object[] ob = cariBarang(id_barang);

        tf_inventory_nmBarang.setText(ob[1].toString());
        tf_inventory_hrgJual.setText(ob[4].toString());
        tf_inventory_hrgPokok.setText(ob[3].toString());
        tf_inventory_stok.setText(ob[5].toString());

        int indexSelectedCbb = dataCbb.indexOf(ob[2]);
        System.out.println("index: " + indexSelectedCbb);
        cb_inventory_jenis.setSelectedIndex(indexSelectedCbb);
    }//GEN-LAST:event_tabel_barangMouseClicked

    private void lbl_inventory_simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_inventory_simpanMouseClicked

        int hasil = JOptionPane.showConfirmDialog(null, "Simpan perubahan barang ini?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (hasil == JOptionPane.YES_OPTION) {
            String nama_barang = tf_inventory_nmBarang.getText();
            int harga_pokok = Integer.parseInt(tf_inventory_hrgPokok.getText().toString());
            int harga_jual = Integer.parseInt(tf_inventory_hrgJual.getText().toString());
            int stok = Integer.parseInt(tf_inventory_stok.getText().toString());
            int id_jenis = Integer.parseInt(cb_inventory_jenis.getSelectedItem().toString().split(" - ")[0]);

            Object[] ob = new Object[5];
            ob[0] = id_jenis;
            ob[1] = nama_barang;
            ob[2] = harga_jual;
            ob[3] = harga_pokok;
            ob[4] = stok;
            insertData(ob);
        }
    }//GEN-LAST:event_lbl_inventory_simpanMouseClicked

    private void lbl_inventory_cancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_inventory_cancelMouseClicked
        tf_inventory_nmBarang.setText("");
        tf_inventory_hrgJual.setText("");
        tf_inventory_hrgPokok.setText("");
        tf_inventory_stok.setText("");
    }//GEN-LAST:event_lbl_inventory_cancelMouseClicked

    private void lbl_inventory_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_inventory_cariMouseClicked
        String indexCari = tf_inventory_cari.getText();
        prepareTable();
        tampilTabelBarang(indexCari);
    }//GEN-LAST:event_lbl_inventory_cariMouseClicked

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
            java.util.logging.Logger.getLogger(FrameInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameInventory.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameInventory().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cb_inventory_jenis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_inventory_cancel;
    private javax.swing.JLabel lbl_inventory_cari;
    private javax.swing.JLabel lbl_inventory_simpan;
    private javax.swing.JTable tabel_barang;
    private javax.swing.JTextField tf_inventory_cari;
    private javax.swing.JTextField tf_inventory_hrgJual;
    private javax.swing.JTextField tf_inventory_hrgPokok;
    private javax.swing.JTextField tf_inventory_nmBarang;
    private javax.swing.JTextField tf_inventory_stok;
    // End of variables declaration//GEN-END:variables
}
