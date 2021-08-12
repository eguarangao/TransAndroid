package com.example.transandroid;

public class Productos {
    private String info;
    private double valor;

    public Productos() {
    }

    public Productos(String info, double valor) {
        this.info = info;
        this.valor = valor;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
