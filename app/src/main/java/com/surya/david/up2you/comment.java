package com.surya.david.up2you;

public class comment {
    public String userId, keyThreads, isi;

    public comment() {
    }

    public comment(String userId, String keyThreads, String isi) {
        this.userId = userId;
        this.keyThreads = keyThreads;
        this.isi = isi;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeyThreads() {
        return keyThreads;
    }

    public void setKeyThreads(String keyThreads) {
        this.keyThreads = keyThreads;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
