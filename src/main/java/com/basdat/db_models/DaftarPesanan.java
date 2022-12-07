package com.basdat.db_models;

public class DaftarPesanan {
    private String ID_Pesanan, ID_Produk;
    private int kuantitas;

    public DaftarPesanan() {

    }

    public DaftarPesanan(String ID_Pesanan, String ID_Produk, int kuantitas) {
        this.ID_Pesanan = ID_Pesanan;
        this.ID_Produk = ID_Produk;
        this.kuantitas = kuantitas;
    }

    public String getID_Pesanan() {
        return ID_Pesanan;
    }

    public void setID_Pesanan(String ID_Pesanan) {
        this.ID_Pesanan = ID_Pesanan;
    }

    public String getID_Produk() {
        return ID_Produk;
    }

    public void setID_Produk(String ID_Produk) {
        this.ID_Produk = ID_Produk;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }
}
