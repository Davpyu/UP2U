package com.surya.david.up2you;

import java.io.Serializable;
import java.util.List;

public class news{
    public String title, kota, author, isi, date, kategori, image;
    public int id_berita;

    public int getId_berita() {
        return id_berita;
    }

    public void setId_berita(int id_berita) {
        this.id_berita = id_berita;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public news() {

    }

    public news(String title, String kota, String author, String isi, String date, String kategori, String image, int id_berita) {
        this.title = title;
        this.kota = kota;
        this.author = author;
        this.isi = isi;
        this.date = date;
        this.kategori = kategori;
        this.image = image;
        this.id_berita = id_berita;
    }
}
