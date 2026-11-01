package com.montecito.projeto.service;

import com.montecito.projeto.dao.HospedagemDao;
import com.montecito.projeto.exception.*;
import com.montecito.projeto.model.Cliente;
import com.montecito.projeto.model.Hospedagem;
import com.montecito.projeto.util.FabricaDeDaos;

import java.time.LocalDateTime;
import java.util.List;

public class HospedagemService {

    HospedagemDao hospedagemDao =  FabricaDeDaos.getDao(HospedagemDao.class);
    ConsumoService consumoService = new ConsumoService();

    public Hospedagem cadastrar(Hospedagem hospedagem){
        var entradaPrevista = hospedagem.getDataHoraEntPrevista();
        var saidaPrevista = hospedagem.getDataHoraSaidaPrevista();

        if (saidaPrevista.isBefore(entradaPrevista)){
            throw new DatasInvalidasException("Data de saída precisa ser depois da  data de entrada");
        }

        for (Hospedagem existente : hospedagemDao.recuperarPorAcomodacao(hospedagem.getAcomodacao())) {
            boolean conflita = hospedagem.getDataHoraEntPrevista().isBefore(existente.getDataHoraSaidaPrevista())
                    && hospedagem.getDataHoraSaidaPrevista().isAfter(existente.getDataHoraEntPrevista());
            if (conflita)
                throw new AcomodacaoIndisponivelException("Acomodacao indisponivel");
        }
        return hospedagemDao.cadastrar(hospedagem);
    }

     public void fazerCheckIn(Hospedagem hospedagem){
        if (hospedagem.getDataHoraEntReal() != null){
            throw new CheckInInvalidoException("O check-in já foi realizado");
        }
        hospedagem.setDataHoraEntReal(LocalDateTime.now());
     }

    public void fazerCheckOut(Hospedagem hospedagem){
        if (hospedagem.getDataHoraEntReal() == null)
            throw new CheckOutInvalidoException("Não é possível fazer check-out sem check-in.");

        if (hospedagem.getDataHoraSaidaReal() != null){
            throw new CheckOutInvalidoException("O check-out já foi realizado");
        }
        hospedagem.setDataHoraSaidaReal(LocalDateTime.now());

    }

    public Hospedagem remover(Integer numero){
       var hospedagem = hospedagemDao.recuperar(numero);
       if (hospedagem != null && hospedagem.getDataHoraEntReal() != null){
           throw new CancelamentoInvalidoException("Essa hospedagem nao pode ser cancelada");
       }
       return hospedagemDao.remover(numero);
    }


    public double calcularValorTotal(Integer numero) {
        var hospedagem = hospedagemDao.recuperar(numero);
        if (hospedagem == null)
            return 0;

        return hospedagem.getAcomodacao().getPreco()
                + consumoService.totalPorHospedagem(hospedagem);
    }

    public List<Hospedagem> recuperarPorCliente(Cliente cliente){
        return hospedagemDao.recuperarPorCliente(cliente);
    }

    public List<Hospedagem> recuperarTodosAtivos() {
        return hospedagemDao.recuperarTodos().stream().
                filter(h -> h.getDataHoraEntReal() != null
                && h.getDataHoraSaidaReal() == null).toList();
    }

    public Hospedagem recuperar(Integer numero) {
        return hospedagemDao.recuperar(numero);
    }

    public List<Hospedagem> recuperarTodos() {
        return hospedagemDao.recuperarTodos();
    }
    public boolean vazio(){
        return hospedagemDao.vazio();
    }

}
