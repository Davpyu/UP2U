package com.surya.david.up2you;

public class news {
    public String judul, kota, author, isi;
    public int idC_news;

    public news() {
    }

    public news(String judul, String kota, String author, String isi, int idC_news) {
        this.judul = judul;
        this.kota = kota;
        this.author = author;
        this.isi = isi;
        this.idC_news = idC_news;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public int getIdC_news() {
        return idC_news;
    }

    public void setIdC_news(int idC_news) {
        this.idC_news = idC_news;
    }
}
