package com.basdat.db_models;

public class Pembeli {
    private int ID_Pengguna, ID_Pembeli;
    private String nama, NIK, jenisKelamin, jalan, kecamatan, kota;

    public Pembeli() {

    }

    public Pembeli(int ID_Pengguna, int ID_Pembeli, String nama, String NIK, String jenisKelamin, String jalan, String kecamatan, String kota) {
        this.ID_Pengguna = ID_Pengguna;
        this.ID_Pembeli = ID_Pembeli;
        this.nama = nama;
        this.NIK = NIK;
        this.jenisKelamin = jenisKelamin;
        this.jalan = jalan;
        this.kecamatan = kecamatan;
        this.kota = kota;
    }

    public int getID_Pengguna() {
        return ID_Pengguna;
    }

    public void setID_Pengguna(int ID_Pengguna) {
        this.ID_Pengguna = ID_Pengguna;
    }

    public int getID_Pembeli() {
        return ID_Pembeli;
    }

    public void setID_Pembeli(int ID_Pembeli) {
        this.ID_Pembeli = ID_Pembeli;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
}
