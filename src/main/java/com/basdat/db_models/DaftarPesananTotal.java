package com.basdat.db_models;

public class DaftarPesananTotal {
    private String ID_Pesanan, ID_Produk;
    private int kuantitas;
    private long SubTotal;

    public DaftarPesananTotal() {

    }

    public DaftarPesananTotal(String ID_Pesanan, String ID_Produk, int kuantitas, long SubTotal) {
        this.ID_Pesanan = ID_Pesanan;
        this.ID_Produk = ID_Produk;
        this.kuantitas = kuantitas;
        this.SubTotal = SubTotal;
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

    public long getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(int subTotal) {
        SubTotal = subTotal;
    }
}
