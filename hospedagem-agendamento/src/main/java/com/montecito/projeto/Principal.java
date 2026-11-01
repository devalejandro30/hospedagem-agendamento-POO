package com.montecito.projeto;

import com.montecito.projeto.model.*;
import com.montecito.projeto.service.*;
import corejava.Console;

import java.time.LocalDateTime;

public class Principal {
    public static void main(String[] args) {

        PrincipalCliente principalCliente = new PrincipalCliente();
        PrincipalAcomodacao principalAcomodacao = new PrincipalAcomodacao();
        PrincipalProduto principalProduto = new PrincipalProduto();
        PrincipalHospedagem principalHospedagem = new PrincipalHospedagem();

        popularDados();

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "1. Tratar Clientes");
            System.out.println("2. Tratar Acomodações");
            System.out.println("3. Tratar Produtos");
            System.out.println("4. Tratar Hospedagens");
            System.out.println("5. Sair");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 5:");

            switch (opcao) {
                case 1 -> principalCliente.principal();
                case 2 -> principalAcomodacao.principal();
                case 3 -> principalProduto.principal();
                case 4 -> principalHospedagem.principal();
                case 5 -> continua = false;
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }

    private static void popularDados() {
        ClienteService clienteService = new ClienteService();
        AcomodacaoService acomodacaoService = new AcomodacaoService();
        ProdutoService produtoService = new ProdutoService();
        HospedagemService hospedagemService = new HospedagemService();
        ConsumoService consumoService = new ConsumoService();

        Cliente ana = clienteService.cadastrar(new Cliente("Ana", "Rua A, 10"));
        Cliente bruno = clienteService.cadastrar(new Cliente("Bruno", "Rua B, 20"));


        Acomodacao q101 = acomodacaoService.cadastrar(new Acomodacao(101, 150.0));
        Acomodacao q102 = acomodacaoService.cadastrar(new Acomodacao(102, 200.0));
        acomodacaoService.cadastrar(new Acomodacao(103, 320.0));

        Produto agua = produtoService.cadastrar(new Produto(5.0, "Água"));
        Produto refrigerante = produtoService.cadastrar(new Produto(8.0, "Refrigerante"));
        produtoService.cadastrar(new Produto(25.0, "Café da manhã"));

        LocalDateTime agora = LocalDateTime.now();


        Hospedagem h1 = hospedagemService.cadastrar(
                new Hospedagem(ana, q101, agora.minusDays(1), agora.plusDays(2)));
        hospedagemService.fazerCheckIn(h1);


        consumoService.cadastrar(h1, new Consumo(1, agua));
        consumoService.cadastrar(h1, new Consumo(2, refrigerante));


        hospedagemService.cadastrar(
                new Hospedagem(bruno, q101, agora.plusDays(3), agora.plusDays(5)));

        hospedagemService.cadastrar(
                new Hospedagem(ana, q102, agora.plusDays(5), agora.plusDays(8)));
    }
}
