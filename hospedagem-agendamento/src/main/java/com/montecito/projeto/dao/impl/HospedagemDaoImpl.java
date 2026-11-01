package com.montecito.projeto.dao.impl;

import com.montecito.projeto.dao.HospedagemDao;
import com.montecito.projeto.model.Acomodacao;
import com.montecito.projeto.model.Cliente;
import com.montecito.projeto.model.Hospedagem;

import java.util.List;

public class HospedagemDaoImpl extends DaoGenericoImpl<Hospedagem> implements HospedagemDao {

    @Override
    public void listaHospedagensPorCliente(Cliente cliente){
        map.values().stream()
       .filter(h -> h.getCliente().equals(cliente))
       .forEach(System.out::println);
    }

    @Override
    public List<Hospedagem> recuperarPorCliente(Cliente cliente) {
        return map.values().stream()
                .filter(h -> h.getCliente().equals(cliente))
                .toList();
    }

    @Override
    public List<Hospedagem> recuperarPorAcomodacao(Acomodacao acomodacao) {
        return map.values().stream()
                .filter(h -> h.getAcomodacao().equals(acomodacao))
                .toList();
    }

}
