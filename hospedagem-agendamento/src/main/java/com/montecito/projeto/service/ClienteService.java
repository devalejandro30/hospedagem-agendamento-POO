package com.montecito.projeto.service;

import com.montecito.projeto.dao.ClienteDao;
import com.montecito.projeto.dao.HospedagemDao;
import com.montecito.projeto.exception.ClienteComHospedagemException;
import com.montecito.projeto.exception.DadosInvalidosException;
import com.montecito.projeto.model.Cliente;
import com.montecito.projeto.util.FabricaDeDaos;

import java.util.List;

public class ClienteService {
    // injeção de dependência
    ClienteDao clienteDao = FabricaDeDaos.getDao(ClienteDao.class);
    HospedagemDao hospedagemDao = FabricaDeDaos.getDao(HospedagemDao.class);

    public Cliente cadastrar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank())
            throw new DadosInvalidosException("O nome não pode ser vazio.");
        if (cliente.getEndereco() == null || cliente.getEndereco().isBlank())
            throw new DadosInvalidosException("O endereço não pode ser vazio.");

        return clienteDao.cadastrar(cliente);
    }

    public Cliente remover(Integer numero) throws ClienteComHospedagemException {
        Cliente cliente = clienteDao.recuperar(numero);

        if (cliente != null && !hospedagemDao.recuperarPorCliente(cliente).isEmpty())
            throw new ClienteComHospedagemException("Clientes com hospedagens não podem ser removidos");

        return clienteDao.remover(numero);
    }

    public Cliente recuperar(Integer numero) {
        return clienteDao.recuperar(numero);
    }

    public List<Cliente> recuperarTodos() {
        return clienteDao.recuperarTodos();
    }

    public boolean vazio() {
        return clienteDao.vazio();
    }
}