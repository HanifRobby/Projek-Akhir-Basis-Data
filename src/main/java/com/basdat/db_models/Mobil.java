package com.basdat.db_models;

public class Mobil {
    private String ID, nama, harga, merk, tahun, warna;

    public Mobil(String ID, String nama, String merk, String tahun, String warna, String harga) {
        this.ID = ID;
        this.nama = nama;
        this.harga = harga;
        this.merk = merk;
        this.tahun = tahun;
        this.warna = warna;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getNama() {
        return nama;
    }

    public String getHarga() {
        return harga;
    }

    public String getID() {
        return ID;
    }

    public String getMerk() {
        return merk;
    }

    public String getTahun() {
        return tahun;
    }

    public String getWarna() {
        return warna;
    }


}
