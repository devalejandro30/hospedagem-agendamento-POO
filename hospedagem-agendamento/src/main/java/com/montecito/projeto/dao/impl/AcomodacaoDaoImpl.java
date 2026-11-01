package com.montecito.projeto.dao.impl;

import com.montecito.projeto.dao.AcomodacaoDao;
import com.montecito.projeto.model.Acomodacao;


public class AcomodacaoDaoImpl extends DaoGenericoImpl<Acomodacao> implements AcomodacaoDao {


    @Override
    public Boolean existeNumeroQuarto(int numero) {
        return map.values().stream()
                .anyMatch(a -> a.getNumDoQuarto() == numero);
    }

}
