package com.montecito.projeto;

import com.montecito.projeto.exception.AcomodacaoEmUsoException;
import com.montecito.projeto.exception.AcomodacaoJaExisteException;
import com.montecito.projeto.exception.DadosInvalidosException;
import com.montecito.projeto.model.Acomodacao;
import com.montecito.projeto.service.AcomodacaoService;
import com.montecito.projeto.util.Util;
import corejava.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class PrincipalAcomodacao {

    public static void principal() {

        AcomodacaoService acomodacaoService = new AcomodacaoService();

        Acomodacao umaAcomodacao;

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar uma acomodação");
            System.out.println("2. Alterar o preço de uma acomodação");
            System.out.println("3. Remover uma acomodação");
            System.out.println("4. Listar relação de acomodações");
            System.out.println("5. Listar acomodações disponíveis em um período");
            System.out.println("6. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 6:");

            switch (opcao) {
                case 1 -> {    // Cadastrar
                    int numDoQuarto = Console.readInt('\n' + "Digite o número do quarto: ");
                    double preco = Console.readDouble("Digite o preço da diária: ");

                    try {
                        acomodacaoService.cadastrar(new Acomodacao(numDoQuarto, preco));
                        System.out.println('\n' + "Acomodação cadastrada com sucesso!");
                    } catch (AcomodacaoJaExisteException | DadosInvalidosException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 2 -> {    // Alterar preço
                    int numero = Console.readInt('\n' +
                            "Digite o número da acomodação que você deseja alterar: ");

                    umaAcomodacao = acomodacaoService.recuperar(numero);

                    if (umaAcomodacao != null) {
                        double novoPreco = Console.readDouble("Digite o novo preço: ");

                        if (novoPreco <= 0) {
                            System.out.println('\n' + "O preço precisa ser maior que zero!");
                        } else {
                            umaAcomodacao.setPreco(novoPreco);
                            System.out.println('\n' + "Alteração de preço efetuada com sucesso!");
                        }
                    } else {
                        System.out.println('\n' + "Acomodação não encontrada!");
                    }
                }
                case 3 -> {    // Remover
                    int numero = Console.readInt('\n' +
                            "Digite o número da acomodação que você deseja remover: ");

                    try {
                        if (acomodacaoService.remover(numero) != null)
                            System.out.println('\n' + "Acomodação removida com sucesso!");
                        else
                            System.out.println('\n' + "Acomodação não encontrada!");
                    } catch (AcomodacaoEmUsoException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 4 -> {    // Listar tudo
                    if (acomodacaoService.vazio())
                        System.out.println('\n' + "Não há acomodações cadastradas.");
                    else {
                        for (Acomodacao acomodacao : acomodacaoService.recuperarTodos()) {
                            System.out.println(acomodacao);
                        }
                    }
                }
                case 5 -> {    // Disponíveis em um período
                    try {
                        LocalDateTime entrada = Util.lerDataHora(
                                Console.readLine('\n' + "Entrada (dd/MM/yyyy HH:mm): "));
                        LocalDateTime saida = Util.lerDataHora(
                                Console.readLine("Saída (dd/MM/yyyy HH:mm): "));

                        if (!saida.isAfter(entrada)) {
                            System.out.println('\n' + "A saída precisa ser depois da entrada!");
                        } else {
                            var disponiveis = acomodacaoService.listarAcomodacoesDisponiveis(entrada, saida);

                            if (disponiveis.isEmpty())
                                System.out.println('\n' + "Não há acomodações disponíveis nesse período.");
                            else {
                                for (Acomodacao acomodacao : disponiveis) {
                                    System.out.println(acomodacao);
                                }
                            }
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println('\n' + "Data/hora em formato inválido! Use dd/MM/yyyy HH:mm");
                    }
                }
                case 6 -> continua = false;
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}