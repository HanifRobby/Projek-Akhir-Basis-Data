package com.basdat.db_models;

import java.sql.Date;

public class Pesanan {
    private Date tanggal_Pesanan;
    private String ID_Pesanan, ID_Pembeli, ID_Pegawai, ID_Pembayaran, status;

    public Pesanan() {

    }

    public Pesanan(String ID_Pesanan, String ID_Pembeli, Date tanggal_Pesanan, String status, String ID_Pegawai, String ID_Pembayaran) {
        this.ID_Pesanan = ID_Pesanan;
        this.ID_Pembeli = ID_Pembeli;
        this.ID_Pegawai = ID_Pegawai;
        this.ID_Pembayaran = ID_Pembayaran;
        this.tanggal_Pesanan = tanggal_Pesanan;
        this.status = status;
    }

    public String getID_Pesanan() {
        return ID_Pesanan;
    }

    public void setID_Pesanan(String ID_Pesanan) {
        this.ID_Pesanan = ID_Pesanan;
    }

    public String getID_Pembeli() {
        return ID_Pembeli;
    }

    public void setID_Pembeli(String ID_Pembeli) {
        this.ID_Pembeli = ID_Pembeli;
    }

    public String getID_Pegawai() {
        return ID_Pegawai;
    }

    public void setID_Pegawai(String ID_Pegawai) {
        this.ID_Pegawai = ID_Pegawai;
    }

    public String getID_Pembayaran() {
        return ID_Pembayaran;
    }

    public void setID_Pembayaran(String ID_Pembayaran) {
        this.ID_Pembayaran = ID_Pembayaran;
    }

    public Date getTanggal_Pesanan() {
        return tanggal_Pesanan;
    }

    public void setTanggal_Pesanan(Date tanggal_Pesanan) {
        this.tanggal_Pesanan = tanggal_Pesanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
