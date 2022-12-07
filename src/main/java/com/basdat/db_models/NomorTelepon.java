package com.basdat.db_models;

public class NomorTelepon {
    private String ID, Nama, NomorTelepon;

    public NomorTelepon() {

    }

    public NomorTelepon(String ID, String nama, String nomorTelepon) {
        this.ID = ID;
        Nama = nama;
        NomorTelepon = nomorTelepon;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getNomorTelepon() {
        return NomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        NomorTelepon = nomorTelepon;
    }
}
