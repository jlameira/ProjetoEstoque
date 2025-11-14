import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * REFACTOR #10: App.java refatorado
 * 
 * MELHORIAS:
 * - Métodos extraídos (cada um faz uma coisa)
 * - Constantes para magic numbers
 * - Código mais legível e manutenível
 * - Scanner fechado corretamente
 * - Tratamento de exceções melhorado
 */
public class App {
    // Constantes para opções do menu (evita magic numbers)
    private static final int OPCAO_ADICIONAR = 1;
    private static final int OPCAO_LISTAR = 2;
    private static final int OPCAO_BUSCAR = 3;
    private static final int OPCAO_ATUALIZAR = 4;
    private static final int OPCAO_REMOVER = 5;
    private static final int OPCAO_SAIR = 0;
    
    private static Estoque estoque;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        estoque = new Estoque();
        scanner = new Scanner(System.in);
        
        try {
            executarSistema();
        } finally {
            // REFACTOR: Sempre fechar recursos (evita resource leak)
            scanner.close();
        }
    }
    
    /**
     * Loop principal do sistema
     */
    private static void executarSistema() {
        boolean continuar = true;
        
        while (continuar) {
            try {
                exibirMenu();
                int opcao = scanner.nextInt();
                scanner.nextLine();
                
                if (opcao == OPCAO_SAIR) {
                    System.out.println("Saindo do sistema...");
                    continuar = false;
                } else {
                    processarOpcao(opcao);
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Digite um número.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }
    
    /**
     * Exibe o menu principal
     */
    private static void exibirMenu() {
        System.out.println("\n=== Sistema de Cadastro de Estoque ===");
        System.out.println("[1] Adicionar Produto");
        System.out.println("[2] Listar Produtos");
        System.out.println("[3] Buscar Produto por Código");
        System.out.println("[4] Atualizar Preço");
        System.out.println("[5] Remover Produto");
        System.out.println("[0] Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    /**
     * Processa a opção escolhida pelo usuário
     */
    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case OPCAO_ADICIONAR:
                adicionarProduto();
                break;
            case OPCAO_LISTAR:
                listarProdutos();
                break;
            case OPCAO_BUSCAR:
                buscarProduto();
                break;
            case OPCAO_ATUALIZAR:
                atualizarPreco();
                break;
            case OPCAO_REMOVER:
                removerProduto();
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
    
    /**
     * REFACTOR: Método extraído para adicionar produto
     */
    private static void adicionarProduto() {
        try {
            System.out.println("\n=== Tipo de Produto ===");
            System.out.println("[1] Produto Comum");
            System.out.println("[2] Alimento");
            System.out.println("[3] Eletrônico");
            System.out.println("[4] Roupa");
            System.out.print("Escolha o tipo: ");
            
            int tipoInt = scanner.nextInt();
            scanner.nextLine();
            
            TipoProduto tipo = obterTipoProduto(tipoInt);
            
            if (tipo == TipoProduto.INVALIDO) {
                System.out.println("Tipo de produto inválido!");
                return;
            }
            
            Produto produto = ProdutoFactory.criarProduto(tipo, scanner);
            
            if (produto != null) {
                estoque.adicionarProduto(produto);
                System.out.println("✓ Produto adicionado com sucesso!");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    /**
     * REFACTOR: Método extraído para converter int em TipoProduto
     * Elimina cadeia de if-else
     */
    private static TipoProduto obterTipoProduto(int opcao) {
        switch (opcao) {
            case 1: return TipoProduto.PRODUTO;
            case 2: return TipoProduto.ALIMENTO;
            case 3: return TipoProduto.ELETRONICO;
            case 4: return TipoProduto.ROUPA;
            default: return TipoProduto.INVALIDO;
        }
    }
    
    /**
     * REFACTOR: Método extraído para listar produtos
     */
    private static void listarProdutos() {
        if (estoque.estaVazio()) {
            System.out.println("Estoque vazio!");
            return;
        }
        
        System.out.println("\n=== Produtos no Estoque ===");
        for (Produto produto : estoque.listarProdutos()) {
            System.out.println(produto);
            System.out.println("---");
        }
        System.out.println("Total: " + estoque.quantidadeProdutos() + " produto(s)");
    }
    
    /**
     * REFACTOR: Método extraído para buscar produto
     */
    private static void buscarProduto() {
        System.out.print("Digite o código do produto: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        
        Produto produto = estoque.buscarProdutoPorCodigo(codigo);
        
        if (produto != null) {
            System.out.println("\n=== Produto Encontrado ===");
            System.out.println(produto);
        } else {
            System.out.println("Produto com código " + codigo + " não encontrado.");
        }
    }
    
    /**
     * REFACTOR: Método extraído para atualizar preço
     */
    private static void atualizarPreco() {
        try {
            System.out.print("Digite o código do produto: ");
            int codigo = scanner.nextInt();
            scanner.nextLine();
            
            Produto produto = estoque.buscarProdutoPorCodigo(codigo);
            
            if (produto == null) {
                System.out.println("Produto não encontrado.");
                return;
            }
            
            System.out.println("\n=== Produto Atual ===");
            System.out.println(produto);
            System.out.println();
            
            System.out.print("Digite o novo preço: R$ ");
            double novoPreco = scanner.nextDouble();
            scanner.nextLine();
            
            produto.setPreco(novoPreco);
            System.out.println("✓ Preço atualizado com sucesso!");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    /**
     * REFACTOR: Método extraído para remover produto
     */
    private static void removerProduto() {
        System.out.print("Digite o código do produto a remover: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        
        boolean removido = estoque.removerProduto(codigo);
        
        if (removido) {
            System.out.println("✓ Produto removido com sucesso!");
        } else {
            System.out.println("Produto com código " + codigo + " não encontrado.");
        }
    }
}