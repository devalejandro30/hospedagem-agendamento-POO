package com.montecito.projeto.service;

import com.montecito.projeto.exception.HospedagemNaoAtivaException;
import com.montecito.projeto.exception.QuantidadeInvalidaException;
import com.montecito.projeto.model.Consumo;
import com.montecito.projeto.model.Hospedagem;

import java.util.List;

public class ConsumoService {

    public Consumo cadastrar(Hospedagem hospedagem, Consumo consumo) {
        if (consumo.getQtd() <= 0)
            throw new QuantidadeInvalidaException("A quantidade deve ser maior que zero.");

        boolean ativa = hospedagem.getDataHoraEntReal() != null
                && hospedagem.getDataHoraSaidaReal() == null;
        if (!ativa)
            throw new HospedagemNaoAtivaException(
                    "A hospedagem precisa estar com check-in e sem check-out.");

        hospedagem.adicionarConsumo(consumo);
        return consumo;
    }

    public List<Consumo> recuperarPorHospedagem(Hospedagem hospedagem) {
        return hospedagem.getConsumos();
    }

    public double totalPorHospedagem(Hospedagem hospedagem) {
        double total = 0;
        for (Consumo c : hospedagem.getConsumos())
            total += c.getQtd() * c.getProduto().getPreco();
        return total;
    }
}
