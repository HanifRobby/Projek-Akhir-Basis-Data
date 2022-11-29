package com.basdat.db_models;

public class Mobil {
    private String ID, nama, merk, tahun, harga;

    public Mobil(String ID, String nama, String merk, String tahun, String harga) {
        this.ID = ID;
        this.nama = nama;
        this.merk = merk;
        this.tahun = tahun;
        this.harga = harga;
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

}
