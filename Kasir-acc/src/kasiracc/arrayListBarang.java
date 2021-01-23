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
public class arrayListBarang {
    private static ArrayList<Barang> listBarang;

    public static void startArrayList() {
        listBarang = new ArrayList();
    }
    
    public static ArrayList<Barang> getArrayList() {
        return listBarang;
    }

    public static void emptyArrayList(){
        listBarang = null;
    }
    
    public static void addList(Barang list) {
        listBarang.add(list);
    }
}
