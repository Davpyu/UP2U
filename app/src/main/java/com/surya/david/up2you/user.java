package com.surya.david.up2you;

public class user {
    public String name, email, jen_kel, tl, bio, foto;
    public Boolean status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJen_kel() {
        return jen_kel;
    }

    public void setJen_kel(String jen_kel) {
        this.jen_kel = jen_kel;
    }

    public String getTl() {
        return tl;
    }

    public void setTl(String tl) {
        this.tl = tl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public user() {
    }

    public user(String name, String email, String jen_kel, String tl, String bio, String foto, Boolean status) {
        this.name = name;
        this.email = email;
        this.jen_kel = jen_kel;
        this.tl = tl;
        this.bio = bio;
        this.foto = foto;
        this.status = status;
    }
}
