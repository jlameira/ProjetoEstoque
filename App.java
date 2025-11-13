import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    //metodo principal
    public static void main(String[] args) {
        int tipoProduto = 0;
        int escolhaInterface = 0;
        boolean escolha = true;
        Estoque meuEstoque = new Estoque(10);
        Scanner entrada = new Scanner(System.in);
        while(escolha == true) {
            try {
                System.out.println("Sistema de Cadastro de Estoque \n [1]Adicionar Produto \n [2]Listar Produto \n [3]Buscar Produto Por Codigo\n [4]Atualizar Preço\n [5]Remover Produto\n [0]Sair");
                escolhaInterface = entrada.nextInt();
                entrada.nextLine();
                if (escolhaInterface == 1) {
                    System.out.println("O que gostaria de cadastrar \n [1]Produto Comum \n [2]Alimento \n [3]Eletronico \n [4]Roupa");
                    int tipoInt = entrada.nextInt();
                    entrada.nextLine();

                    TipoProduto tipoEscolhido;

                    if(tipoInt == 1) {
                        tipoEscolhido = TipoProduto.PRODUTO;
                    }
                    else if(tipoInt == 2) {
                        tipoEscolhido = TipoProduto.ALIMENTO;
                    }
                    else if(tipoInt == 3) {
                        tipoEscolhido= TipoProduto.ELETRONICO;
                    }
                    else if(tipoInt == 4) {
                        tipoEscolhido= TipoProduto.ROUPA;
                    }
                    else{
                        tipoEscolhido= TipoProduto.INVALIDO;
                    }
                    Produto p = ProdutoFactory.criarProduto(tipoEscolhido, entrada);
                    if (p != null) {
                        meuEstoque.adicionarProduto(p);
                        System.out.println("Produto adicionado com sucesso!");
                    }

                } else if (escolhaInterface == 2) {
                    meuEstoque.listarProdutos();
                } else if (escolhaInterface == 3) {
                    System.out.println("Digite o codigo do Produto que voce busca: ");
                    int codigoBuscar = entrada.nextInt();
                    entrada.nextLine();
                    //logica de busca
                    Produto codigoBuscado = meuEstoque.buscarProdutoPorCodigo(codigoBuscar);
                    if (codigoBuscado != null) {
                        System.out.println(codigoBuscado.toString() + "\n");

                    } else {
                        System.out.println("Produto com codigo " + codigoBuscar + " nao encontrado");
                    }
                } else if (escolhaInterface == 4) {
                    System.out.println("Digite o codigo do Produto que você deseja atualizar: ");
                    int codigoParaAtualizar = entrada.nextInt();
                    entrada.nextLine();
                    //logica de busca
                    Produto codigoBuscado = meuEstoque.buscarProdutoPorCodigo(codigoParaAtualizar);
                    if (codigoBuscado != null) {
                        System.out.println("---Produto Atual---");
                        System.out.println(codigoBuscado.toString() + "\n");
                        System.out.println("--------------------");
                        System.out.println("Informe o novo preço:");
                        double precoLido = entrada.nextDouble();
                        entrada.nextLine();
                        codigoBuscado.setPreco(precoLido);
                        System.out.println("---!!!Preço atualizado com sucesso!!!--- ");
                    } else {
                        System.out.println("Produto nao encontrado");
                    }

                } else if (escolhaInterface == 5) {
                    System.out.println("Digite o codigo do Produto que você deseja remover: ");
                    int codigoParaRemover = entrada.nextInt();
                    entrada.nextLine();
                    //logica de busca
                    boolean sucesso = meuEstoque.removerProduto(codigoParaRemover);
                    if (sucesso) {
                        System.out.println("Produto removido com sucesso");
                    } else {
                        System.out.println("Produto nao encontrado");
                    }

                } else if (escolhaInterface == 0) {
                    System.out.println("Saindo");
                    escolha = false;

                } else {
                    System.out.println("Escolha Nao encontrada");
                    escolhaInterface = 0;
                }
            }
            catch(InputMismatchException e) {
                System.out.println("Erro : Entrada inválida, você deve digitar um numero");
                entrada.nextLine();
            }
        }

    }
}