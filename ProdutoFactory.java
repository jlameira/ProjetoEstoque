import java.util.Scanner;

/**
 * REFACTOR #9: Factory melhorado
 * 
 * MELHORIAS:
 * - Usa novos tipos (LocalDate, TamanhoRoupa)
 * - Tratamento de erros mais robusto
 * - Mensagens mais claras
 */
public class ProdutoFactory {
    // Factory Pattern: Centraliza a criação de produtos
    // Princípio: Single Responsibility Principle (SRP)
    
    public static Produto criarProduto(TipoProduto tipo, Scanner entrada) {
        try {
            // DRY (Don't Repeat Yourself): Dados comuns a todos os produtos
            System.out.println("Digite o nome do produto: ");
            String nomeLido = entrada.nextLine();
            
            System.out.println("Digite o Codigo do Produto: ");
            int codigoLido = entrada.nextInt();
            entrada.nextLine();
            
            System.out.println("Digite o preço do produto: ");
            double precoLido = entrada.nextDouble();
            entrada.nextLine();
            
            // Switch expression (Java 14+) ou tradicional
            switch (tipo) {
                case PRODUTO:
                    return new Produto(nomeLido, codigoLido, precoLido);
                    
                case ALIMENTO:
                    System.out.println("Digite a validade do Alimento (dd/MM/yyyy): ");
                    String dataDeValidadeLida = entrada.nextLine();
                    return new Alimento(nomeLido, codigoLido, precoLido, dataDeValidadeLida);
                    
                case ELETRONICO:
                    System.out.println("Digite a garantia do produto (em meses): ");
                    int mesesDeGarantiaLida = entrada.nextInt();
                    entrada.nextLine();
                    return new Eletronico(nomeLido, codigoLido, precoLido, mesesDeGarantiaLida);
                    
                case ROUPA:
                    System.out.println("Digite o tamanho do produto (PP, P, M, G, GG, XG): ");
                    String tamanhoLido = entrada.nextLine();
                    return new Roupa(nomeLido, codigoLido, precoLido, tamanhoLido);
                    
                default:
                    throw new IllegalArgumentException("Tipo do produto não encontrado: " + tipo);
            }
        } catch (IllegalArgumentException e) {
            // Propaga exceções de validação para quem chamou tratar
            System.out.println("Erro de validação: " + e.getMessage());
            return null;
        }
    }
}
