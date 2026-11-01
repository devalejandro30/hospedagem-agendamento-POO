package com.montecito.projeto.service;

import com.montecito.projeto.dao.AcomodacaoDao;

import com.montecito.projeto.dao.HospedagemDao;
import com.montecito.projeto.exception.AcomodacaoEmUsoException;
import com.montecito.projeto.exception.AcomodacaoJaExisteException;
import com.montecito.projeto.exception.DadosInvalidosException;
import com.montecito.projeto.model.Acomodacao;
import com.montecito.projeto.model.Hospedagem;
import com.montecito.projeto.util.FabricaDeDaos;

import java.time.LocalDateTime;
import java.util.List;

public class AcomodacaoService {
    AcomodacaoDao acomodacaoDao = FabricaDeDaos.getDao(AcomodacaoDao.class);
    HospedagemDao hospedagemDao = FabricaDeDaos.getDao(HospedagemDao.class);

    public Acomodacao cadastrar (Acomodacao acomodacao){
        var num = acomodacao.getNumDoQuarto();
        if (acomodacaoDao.existeNumeroQuarto(num)) {
            throw new AcomodacaoJaExisteException("Essa acomodacao já existe");
        }
        if (acomodacao.getPreco() <= 0) {
            throw new DadosInvalidosException("Preço da acomodacao precisa ser maior que zero");
        }

        return acomodacaoDao.cadastrar(acomodacao);
    }

    public Acomodacao remover(Integer numero) throws AcomodacaoEmUsoException {
        Acomodacao acomodacao = acomodacaoDao.recuperar(numero);

        if (acomodacao != null) {
            for (Hospedagem h : hospedagemDao.recuperarPorAcomodacao(acomodacao)) {
                if (h.getDataHoraSaidaReal() == null)
                    throw new AcomodacaoEmUsoException("Acomodacao ja esta em uso ou tem reserva futura");
            }
        }

        return acomodacaoDao.remover(numero);
    }

    public List<Acomodacao> listarAcomodacoesDisponiveis(LocalDateTime entrada, LocalDateTime saida) {
        return acomodacaoDao.recuperarTodos().stream()
                .filter(a -> hospedagemDao.recuperarPorAcomodacao(a).stream()
                        .noneMatch(h -> entrada.isBefore(h.getDataHoraSaidaPrevista())
                                && saida.isAfter(h.getDataHoraEntPrevista())))
                .toList();
        }

    public Acomodacao recuperar(Integer numero) {
        return acomodacaoDao.recuperar(numero);
    }

    public List<Acomodacao> recuperarTodos() {
        return acomodacaoDao.recuperarTodos();
    }

    public boolean vazio() {
        return acomodacaoDao.vazio();
    }

}
