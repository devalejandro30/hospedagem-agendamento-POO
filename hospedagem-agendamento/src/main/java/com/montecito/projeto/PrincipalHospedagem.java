package com.montecito.projeto;

import com.montecito.projeto.exception.*;
import com.montecito.projeto.model.Acomodacao;
import com.montecito.projeto.model.Cliente;
import com.montecito.projeto.model.Consumo;
import com.montecito.projeto.model.Hospedagem;
import com.montecito.projeto.model.Produto;
import com.montecito.projeto.service.*;
import com.montecito.projeto.util.Util;
import corejava.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class PrincipalHospedagem {

    private static final HospedagemService hospedagemService = new HospedagemService();
    private static final ClienteService clienteService = new ClienteService();
    private static final AcomodacaoService acomodacaoService = new AcomodacaoService();
    private static final ProdutoService produtoService = new ProdutoService();
    private static final ConsumoService consumoService = new ConsumoService();

    public static void principal() {

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Reservar uma hospedagem");
            System.out.println("2. Fazer check-in");
            System.out.println("3. Fazer check-out");
            System.out.println("4. Cancelar uma hospedagem");
            System.out.println("5. Registrar um consumo");
            System.out.println("6. Listar consumos de uma hospedagem");
            System.out.println("7. Calcular valor total de uma hospedagem");
            System.out.println("8. Listar todas as hospedagens");
            System.out.println("9. Listar hospedagens ativas");
            System.out.println("10. Listar hospedagens de um cliente");
            System.out.println("11. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 11:");

            switch (opcao) {
                case 1 -> reservar();
                case 2 -> fazerCheckIn();
                case 3 -> fazerCheckOut();
                case 4 -> cancelar();
                case 5 -> registrarConsumo();
                case 6 -> listarConsumos();
                case 7 -> calcularTotal();
                case 8 -> listarTodas();
                case 9 -> listarAtivas();
                case 10 -> listarPorCliente();
                case 11 -> continua = false;
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }

    // ---------- operações ----------

    private static void reservar() {
        Cliente cliente = clienteService.recuperar(
                Console.readInt('\n' + "Digite o número do cliente: "));
        if (cliente == null) {
            System.out.println('\n' + "Cliente não encontrado!");
            return;
        }

        Acomodacao acomodacao = acomodacaoService.recuperar(
                Console.readInt("Digite o número da acomodação: "));
        if (acomodacao == null) {
            System.out.println('\n' + "Acomodação não encontrada!");
            return;
        }

        try {
            LocalDateTime entrada = Util.lerDataHora(
                    Console.readLine("Entrada prevista (dd/MM/yyyy HH:mm): "));
            LocalDateTime saida = Util.lerDataHora(
                    Console.readLine("Saída prevista (dd/MM/yyyy HH:mm): "));

            Hospedagem hospedagem = hospedagemService.cadastrar(
                    new Hospedagem(cliente, acomodacao, entrada, saida));

            System.out.println('\n' + "Hospedagem reservada com sucesso! Número = " + hospedagem.getId());
        } catch (DateTimeParseException e) {
            System.out.println('\n' + "Data/hora em formato inválido! Use dd/MM/yyyy HH:mm");
        } catch (DatasInvalidasException | AcomodacaoIndisponivelException e) {
            System.out.println('\n' + e.getMessage());
        }
    }

    private static void fazerCheckIn() {
        Hospedagem hospedagem = buscarHospedagem("Digite o número da hospedagem: ");
        if (hospedagem == null) return;

        try {
            hospedagemService.fazerCheckIn(hospedagem);
            System.out.println('\n' + "Check-in realizado com sucesso!");
        } catch (CheckInInvalidoException e) {
            System.out.println('\n' + e.getMessage());
        }
    }

    private static void fazerCheckOut() {
        Hospedagem hospedagem = buscarHospedagem("Digite o número da hospedagem: ");
        if (hospedagem == null) return;

        try {
            hospedagemService.fazerCheckOut(hospedagem);
            System.out.println('\n' + "Check-out realizado com sucesso!");
            System.out.println("Valor total = " +
                    Util.formatarMoeda(hospedagemService.calcularValorTotal(hospedagem.getId())));
        } catch (CheckOutInvalidoException e) {
            System.out.println('\n' + e.getMessage());
        }
    }

    private static void cancelar() {
        int numero = Console.readInt('\n' + "Digite o número da hospedagem que você deseja cancelar: ");

        try {
            if (hospedagemService.remover(numero) != null)
                System.out.println('\n' + "Hospedagem cancelada com sucesso!");
            else
                System.out.println('\n' + "Hospedagem não encontrada!");
        } catch (CancelamentoInvalidoException e) {
            System.out.println('\n' + e.getMessage());
        }
    }

    private static void registrarConsumo() {
        Hospedagem hospedagem = buscarHospedagem("Digite o número da hospedagem: ");
        if (hospedagem == null) return;

        Produto produto = produtoService.recuperar(
                Console.readInt("Digite o número do produto: "));
        if (produto == null) {
            System.out.println('\n' + "Produto não encontrado!");
            return;
        }

        int qtd = Console.readInt("Digite a quantidade: ");

        try {
            consumoService.cadastrar(hospedagem, new Consumo(qtd, produto));
            System.out.println('\n' + "Consumo registrado com sucesso!");
        } catch (QuantidadeInvalidaException | HospedagemNaoAtivaException e) {
            System.out.println('\n' + e.getMessage());
        }
    }

    private static void listarConsumos() {
        Hospedagem hospedagem = buscarHospedagem("Digite o número da hospedagem: ");
        if (hospedagem == null) return;

        List<Consumo> consumos = consumoService.recuperarPorHospedagem(hospedagem);

        if (consumos.isEmpty())
            System.out.println('\n' + "Não há consumos nessa hospedagem.");
        else {
            for (Consumo consumo : consumos) {
                System.out.println(consumo);
            }
            System.out.println("Total consumido = " +
                    Util.formatarMoeda(consumoService.totalPorHospedagem(hospedagem)));
        }
    }

    private static void calcularTotal() {
        Hospedagem hospedagem = buscarHospedagem("Digite o número da hospedagem: ");
        if (hospedagem == null) return;

        double acomodacao = hospedagem.getAcomodacao().getPreco();
        double consumos = consumoService.totalPorHospedagem(hospedagem);

        System.out.println('\n' + "Acomodação = " + Util.formatarMoeda(acomodacao));
        System.out.println("Consumos   = " + Util.formatarMoeda(consumos));
        System.out.println("Total      = " + Util.formatarMoeda(hospedagemService.calcularValorTotal(hospedagem.getId())));
    }

    private static void listarTodas() {
        if (hospedagemService.vazio())
            System.out.println('\n' + "Não há hospedagens cadastradas.");
        else {
            for (Hospedagem hospedagem : hospedagemService.recuperarTodos()) {
                System.out.println(hospedagem);
            }
        }
    }

    private static void listarAtivas() {
        List<Hospedagem> ativas = hospedagemService.recuperarTodosAtivos();

        if (ativas.isEmpty())
            System.out.println('\n' + "Não há hospedagens ativas.");
        else {
            for (Hospedagem hospedagem : ativas) {
                System.out.println(hospedagem);
            }
        }
    }

    private static void listarPorCliente() {
        Cliente cliente = clienteService.recuperar(
                Console.readInt('\n' + "Digite o número do cliente: "));
        if (cliente == null) {
            System.out.println('\n' + "Cliente não encontrado!");
            return;
        }

        List<Hospedagem> hospedagens = hospedagemService.recuperarPorCliente(cliente);

        if (hospedagens.isEmpty())
            System.out.println('\n' + "Esse cliente não possui hospedagens.");
        else {
            for (Hospedagem hospedagem : hospedagens) {
                System.out.println(hospedagem);
            }
        }
    }


    // Lê o número e devolve a hospedagem, ou null (já avisando o usuário) se não existir
    private static Hospedagem buscarHospedagem(String mensagem) {
        Hospedagem hospedagem = hospedagemService.recuperar(Console.readInt('\n' + mensagem));

        if (hospedagem == null)
            System.out.println('\n' + "Hospedagem não encontrada!");

        return hospedagem;
    }
}