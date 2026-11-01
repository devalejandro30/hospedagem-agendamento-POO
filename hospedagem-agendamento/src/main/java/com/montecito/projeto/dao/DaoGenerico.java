package com.montecito.projeto.dao;

import java.util.List;

public interface DaoGenerico <V>{
    V cadastrar(V obj);
    V remover(Integer numero);
    V recuperar(Integer numero);
    List<V> recuperarTodos();
    boolean vazio();
}
