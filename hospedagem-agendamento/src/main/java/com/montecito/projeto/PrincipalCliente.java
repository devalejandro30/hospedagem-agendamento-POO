package com.montecito.projeto;

import com.montecito.projeto.exception.ClienteComHospedagemException;
import com.montecito.projeto.exception.DadosInvalidosException;
import com.montecito.projeto.model.Cliente;
import com.montecito.projeto.service.ClienteService;
import corejava.Console;

public class PrincipalCliente {

    public static void principal() {

        ClienteService clienteService = new ClienteService();

        Cliente umCliente;

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um cliente");
            System.out.println("2. Alterar um cliente");
            System.out.println("3. Remover um cliente");
            System.out.println("4. Listar relação de clientes");
            System.out.println("5. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 6:");

            switch (opcao) {
                case 1 -> {    // Cadastrar
                    String nome = Console.readLine('\n' + "Digite o nome do cliente: ");
                    String endereco = Console.readLine("Digite o endereço do cliente: ");

                    try {
                        clienteService.cadastrar(new Cliente(nome, endereco));
                        System.out.println('\n' + "Cliente cadastrado com sucesso!");
                    } catch (DadosInvalidosException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 2 -> {    // Alterar
                    int numero = Console.readInt('\n' +
                            "Digite o número do cliente que você deseja alterar: ");

                    umCliente = clienteService.recuperar(numero);

                    if (umCliente != null) {
                        System.out.println('\n' + "O que você deseja alterar?");
                        System.out.println('\n' + "1. Nome");
                        System.out.println("2. Endereço");

                        int opcaoAlteracao = Console.readInt('\n' + "Digite 1 ou 2:");

                        switch (opcaoAlteracao) {
                            case 1 -> {
                                String novoNome = Console.readLine("Digite o novo nome: ");
                                umCliente.setNome(novoNome);
                                System.out.println('\n' + "Alteração de nome efetuada com sucesso!");
                            }
                            case 2 -> {
                                String novoEndereco = Console.readLine("Digite o novo endereço: ");
                                umCliente.setEndereco(novoEndereco);
                                System.out.println('\n' + "Alteração de endereço efetuada com sucesso!");
                            }
                            default -> System.out.println('\n' + "Opção inválida!");
                        }
                    } else {
                        System.out.println('\n' + "Cliente não encontrado!");
                    }
                }
                case 3 -> {    // Remover
                    int numero = Console.readInt('\n' +
                            "Digite o número do cliente que você deseja remover: ");

                    try {
                        if (clienteService.remover(numero) != null)
                            System.out.println('\n' + "Cliente removido com sucesso!");
                        else
                            System.out.println('\n' + "Cliente não encontrado!");
                    } catch (ClienteComHospedagemException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 4 -> {    // Listar tudo
                    if (clienteService.vazio())
                        System.out.println('\n' + "Não há clientes cadastrados.");
                    else {
                        for (Cliente cliente : clienteService.recuperarTodos()) {
                            System.out.println(cliente);
                        }
                    }
                }

                case 5 -> continua = false;
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}
