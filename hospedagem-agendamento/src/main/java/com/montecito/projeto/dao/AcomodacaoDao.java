package com.montecito.projeto.dao;


import com.montecito.projeto.model.Acomodacao;


public interface AcomodacaoDao extends DaoGenerico<Acomodacao> {
    Boolean existeNumeroQuarto(int num);
}
