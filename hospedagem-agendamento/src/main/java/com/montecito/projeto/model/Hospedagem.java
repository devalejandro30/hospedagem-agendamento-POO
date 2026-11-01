package com.montecito.projeto.model;

import com.montecito.projeto.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Hospedagem {
    @Id
    private int id;
    private Cliente cliente;
    private Acomodacao acomodacao;
    private List<Consumo> consumos = new ArrayList<>();
    private LocalDateTime dataHoraEntPrevista;
    private LocalDateTime dataHoraEntReal;
    private LocalDateTime dataHoraSaidaPrevista;
    private LocalDateTime dataHoraSaidaReal;

    public Hospedagem(Cliente cliente, Acomodacao acomodacao, LocalDateTime dataHoraEntPrevista, LocalDateTime dataHoraSaidaPrevista) {
        this.cliente = cliente;
        this.acomodacao = acomodacao;
        this.dataHoraEntPrevista = dataHoraEntPrevista;
        this.dataHoraSaidaPrevista = dataHoraSaidaPrevista;
    }

    public LocalDateTime getDataHoraEntPrevista() {
        return dataHoraEntPrevista;
    }

    public void setDataHoraEntPrevista(LocalDateTime dataHoraEntPrevista) {
        this.dataHoraEntPrevista = dataHoraEntPrevista;
    }

    public LocalDateTime getDataHoraEntReal() {
        return dataHoraEntReal;
    }

    public void setDataHoraEntReal(LocalDateTime dataHoraEntReal) {
        this.dataHoraEntReal = dataHoraEntReal;
    }

    public LocalDateTime getDataHoraSaidaPrevista() {
        return dataHoraSaidaPrevista;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Acomodacao getAcomodacao() {
        return acomodacao;
    }

    public void setDataHoraSaidaPrevista(LocalDateTime dataHoraSaidaPrevista) {
        this.dataHoraSaidaPrevista = dataHoraSaidaPrevista;
    }

    public LocalDateTime getDataHoraSaidaReal() {
        return dataHoraSaidaReal;
    }

    public void setDataHoraSaidaReal(LocalDateTime dataHoraSaidaReal) {
        this.dataHoraSaidaReal = dataHoraSaidaReal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void adicionarConsumo(Consumo consumo) {
        consumos.add(consumo);
    }

    public List<Consumo> getConsumos() {
        return consumos;
    }

    @Override
    public String toString() {
        return "Número = " + id +
                "  Cliente = " + cliente.getNome() +
                "  Quarto = " + acomodacao.getNumDoQuarto() +
                "  Entrada prevista = " + dataHoraEntPrevista +
                "  Saída prevista = " + dataHoraSaidaPrevista +
                "  Check-in = " + (dataHoraEntReal == null ? "pendente" : dataHoraEntReal) +
                "  Check-out = " + (dataHoraSaidaReal == null ? "pendente" : dataHoraSaidaReal);
    }

}
