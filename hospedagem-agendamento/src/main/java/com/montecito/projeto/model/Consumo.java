package com.montecito.projeto.model;

import java.time.LocalDateTime;

public class Consumo {
    private int qtd;
    private LocalDateTime horaConsumo;
    private Produto produto;


    public Consumo(int qtd,  Produto produto) {
        this.qtd = qtd;
        this.produto = produto;
        this.horaConsumo = LocalDateTime.now();
    }

    public LocalDateTime getHoraConsumo() {
        return horaConsumo;
    }

    public void setHoraConsumo(LocalDateTime horaConsumo) {
        this.horaConsumo = horaConsumo;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override
    public String toString() {
        return "Produto = " + produto.getNome() + "  Qtd = " + qtd +
                "  Hora = " + horaConsumo;
    }
}
