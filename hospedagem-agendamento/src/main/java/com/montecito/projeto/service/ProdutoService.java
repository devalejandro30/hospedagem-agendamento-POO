package com.montecito.projeto.service;

import com.montecito.projeto.dao.ProdutoDao;
import com.montecito.projeto.exception.DadosInvalidosException;
import com.montecito.projeto.model.Produto;
import com.montecito.projeto.util.FabricaDeDaos;

import java.util.List;

public class ProdutoService {
    ProdutoDao produtoDao = FabricaDeDaos.getDao(ProdutoDao.class);

    public Produto cadastrar(Produto produto){
        if ((produto.getNome() == null || produto.getNome().isBlank())){
            throw new DadosInvalidosException("Produto precisa ter algum nome");
        }
        if (produto.getPreco() <= 0){
            throw new DadosInvalidosException("Produto precisa ter valor maior que zero");
        }
        return produtoDao.cadastrar(produto);
    }

    public Produto remover(Integer numero) {
        return produtoDao.remover(numero);
    }

    public Produto recuperar(Integer numero) {
        return produtoDao.recuperar(numero);
    }

    public List<Produto> recuperarTodos() {
        return produtoDao.recuperarTodos();
    }

    public boolean vazio() {
        return produtoDao.vazio();
    }
}
