package com.montecito.projeto;

import com.montecito.projeto.exception.DadosInvalidosException;
import com.montecito.projeto.model.Produto;
import com.montecito.projeto.service.ProdutoService;
import corejava.Console;

public class PrincipalProduto {

    public static void principal() {

        ProdutoService produtoService = new ProdutoService();

        Produto umProduto;

        boolean continua = true;
        while (continua) {
            System.out.println('\n' + "========================================================");
            System.out.println('\n' + "O que você deseja fazer?");
            System.out.println('\n' + "1. Cadastrar um produto");
            System.out.println("2. Alterar um produto");
            System.out.println("3. Remover um produto");
            System.out.println("4. Listar relação de produtos");
            System.out.println("5. Voltar");

            int opcao = Console.readInt('\n' + "Digite um número entre 1 e 5:");

            switch (opcao) {
                case 1 -> {    // Cadastrar
                    String nome = Console.readLine('\n' + "Digite o nome do produto: ");
                    double preco = Console.readDouble("Digite o preço do produto: ");

                    try {
                        produtoService.cadastrar(new Produto(preco, nome));
                        System.out.println('\n' + "Produto cadastrado com sucesso!");
                    } catch (DadosInvalidosException e) {
                        System.out.println('\n' + e.getMessage());
                    }
                }
                case 2 -> {    // Alterar
                    int numero = Console.readInt('\n' +
                            "Digite o número do produto que você deseja alterar: ");

                    umProduto = produtoService.recuperar(numero);

                    if (umProduto != null) {
                        System.out.println('\n' + "O que você deseja alterar?");
                        System.out.println('\n' + "1. Nome");
                        System.out.println("2. Preço");

                        int opcaoAlteracao = Console.readInt('\n' + "Digite 1 ou 2:");

                        switch (opcaoAlteracao) {
                            case 1 -> {
                                String novoNome = Console.readLine("Digite o novo nome: ");

                                if (novoNome == null || novoNome.isBlank()) {
                                    System.out.println('\n' + "O nome não pode ser vazio!");
                                } else {
                                    umProduto.setNome(novoNome);
                                    System.out.println('\n' + "Alteração de nome efetuada com sucesso!");
                                }
                            }
                            case 2 -> {
                                double novoPreco = Console.readDouble("Digite o novo preço: ");

                                if (novoPreco <= 0) {
                                    System.out.println('\n' + "O preço precisa ser maior que zero!");
                                } else {
                                    umProduto.setPreco(novoPreco);
                                    System.out.println('\n' + "Alteração de preço efetuada com sucesso!");
                                }
                            }
                            default -> System.out.println('\n' + "Opção inválida!");
                        }
                    } else {
                        System.out.println('\n' + "Produto não encontrado!");
                    }
                }
                case 3 -> {    // Remover
                    int numero = Console.readInt('\n' +
                            "Digite o número do produto que você deseja remover: ");

                    if (produtoService.remover(numero) != null)
                        System.out.println('\n' + "Produto removido com sucesso!");
                    else
                        System.out.println('\n' + "Produto não encontrado!");
                }
                case 4 -> {    // Listar tudo
                    if (produtoService.vazio())
                        System.out.println('\n' + "Não há produtos cadastrados.");
                    else {
                        for (Produto produto : produtoService.recuperarTodos()) {
                            System.out.println(produto);
                        }
                    }
                }
                case 5 -> continua = false;
                default -> System.out.println('\n' + "Opção inválida!");
            }
        }
    }
}