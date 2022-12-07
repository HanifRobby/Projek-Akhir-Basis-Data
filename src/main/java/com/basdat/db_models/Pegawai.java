package com.basdat.db_models;

public class Pegawai {
    private int ID_Pengguna, ID_Pegawai, no_cabang;
    private String nama, jenisKelamin, jalan, kecamatan, kota;

    public Pegawai() {}

    public Pegawai(int pengguna, int pegawai, String nama, String jenisKelamin, String jalan, String kecamatan, String kota, int no_cabang) {
        this.ID_Pengguna = pengguna;
        this.ID_Pegawai = pegawai;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.jalan = jalan;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.no_cabang = no_cabang;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setID_Pegawai(int ID_Pegawai) {
        this.ID_Pegawai = ID_Pegawai;
    }

    public void setID_Pengguna(int ID_Pengguna) {
        this.ID_Pengguna = ID_Pengguna;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public void setNo_cabang(int no_cabang) {
        this.no_cabang = no_cabang;
    }

    public String getNama() {
        return nama;
    }

    public int getID_Pegawai() {
        return ID_Pegawai;
    }

    public int getID_Pengguna() {
        return ID_Pengguna;
    }

    public String getJalan() {
        return jalan;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public int getNo_cabang() {
        return no_cabang;
    }

}
