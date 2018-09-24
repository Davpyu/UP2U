package com.surya.david.up2you;

public class Thread {
    public String judul, isi, imageUrl,userId;

    public Thread() {
    }

    public Thread(String judul, String isi, String imageUrl, String userId) {
        this.judul = judul;
        this.isi = isi;
        this.imageUrl = imageUrl;
        this.userId = userId;
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
