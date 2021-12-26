package com.example.reto8alrramirezso;

public class EmpresaModelo {
    private String nombre,urlweb,telefono,email,produServ,clasifica;
    private int imgBuiild;

    public EmpresaModelo() {
    }

    public EmpresaModelo(String nombre, String urlweb, String telefono, String email, String produServ, String clasifica){ //  ,int build) {
        this.nombre = nombre;
        this.urlweb = urlweb;
        this.telefono = telefono;
        this.email = email;
        this.produServ = produServ;
        this.clasifica = clasifica;
        // this.imgBuiild = build;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlweb() {
        return urlweb;
    }

    public void setUrlweb(String urlweb) {
        this.urlweb = urlweb;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProduServ() {
        return produServ;
    }

    public void setProduServ(String produServ) {
        this.produServ = produServ;
    }

    public String getClasifica() {
        return clasifica;
    }

    public void setClasifica(String clasifica) {
        this.clasifica = clasifica;
    }

    public int getImgBuiild() {
        return imgBuiild;
    }

    public void setImgBuiild(int imgBuiild) {
        this.imgBuiild = imgBuiild;
    }
}
