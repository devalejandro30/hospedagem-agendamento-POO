package com.montecito.projeto.model;

import com.montecito.projeto.annotation.Id;

public class Acomodacao {
    @Id
    private int id;
    private int numDoQuarto;
    private double preco;

    public Acomodacao(int numDoQuarto, double preco) {
        this.numDoQuarto = numDoQuarto;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumDoQuarto() {
        return numDoQuarto;
    }

    public void setNumDoQuarto(int numDoQuarto) {
        this.numDoQuarto = numDoQuarto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Acomodacao{" +
                "id=" + id +
                ", numDoQuarto=" + numDoQuarto +
                ", preco=" + preco +
                '}';
    }
}
