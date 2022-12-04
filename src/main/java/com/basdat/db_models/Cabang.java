package com.basdat.db_models;

public class Cabang {
    private int noCabang, kapasitas;
    private String email, jalan, kecamatan, kota;

    public Cabang() {

    }

    public Cabang(int noCabang, String email, String jalan, String kecamatan, String kota, int kapasitas) {
        this.noCabang = noCabang;
        this.email = email;
        this.jalan = jalan;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.kapasitas = kapasitas;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public void setNoCabang(int noCabang) {
        this.noCabang = noCabang;
    }

    public String getKota() {
        return kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getJalan() {
        return jalan;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public int getNoCabang() {
        return noCabang;
    }

    public String getEmail() {
        return email;
    }


}
