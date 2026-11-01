package com.montecito.projeto.dao;

import com.montecito.projeto.model.Acomodacao;
import com.montecito.projeto.model.Cliente;
import com.montecito.projeto.model.Hospedagem;

import java.util.List;


public interface HospedagemDao extends DaoGenerico<Hospedagem> {

    void listaHospedagensPorCliente(Cliente cliente);

    List<Hospedagem> recuperarPorCliente(Cliente cliente);

    List<Hospedagem> recuperarPorAcomodacao(Acomodacao acomodacao);
}
