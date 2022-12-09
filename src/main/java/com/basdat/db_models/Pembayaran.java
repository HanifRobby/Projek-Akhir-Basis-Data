package com.basdat.db_models;
import java.sql.Date;

public class Pembayaran {
    private String ID_Pembeli, ID_Pembayaran;
    private Date tanggal_Pembayaran;

    public Pembayaran() {

    }

    public Pembayaran(String ID_Pembeli, String ID_Pembayaran, Date tanggal_Pembayaran) {
        this.ID_Pembeli = ID_Pembeli;
        this.ID_Pembayaran = ID_Pembayaran;
        this.tanggal_Pembayaran = tanggal_Pembayaran;
    }

    public String getID_Pembeli() {
        return ID_Pembeli;
    }

    public void setID_Pembeli(String ID_Pembeli) {
        this.ID_Pembeli = ID_Pembeli;
    }

    public void setID_Pembayaran(String ID_Pembayaran) {
        this.ID_Pembayaran = ID_Pembayaran;
    }

    public String getID_Pembayaran() {
        return ID_Pembayaran;
    }

    public void setTanggal_Pesanan(Date tanggal_Pembayaran) {
        this.tanggal_Pembayaran = tanggal_Pembayaran;
    }

    public Date getTanggal_Pembayaran() {
        return tanggal_Pembayaran;
    }
}
