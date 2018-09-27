package com.surya.david.up2you;

public class Thread {
    public String judul, isi, imageUrl,userId, tag, kategori, key;

    public Thread() {
    }

    public Thread(String judul, String isi, String imageUrl, String userId, String tag, String kategori, String key) {
        this.judul = judul;
        this.isi = isi;
        this.imageUrl = imageUrl;
        this.userId = userId;
        this.tag = tag;
        this.kategori = kategori;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
