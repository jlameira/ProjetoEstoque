import java.util.Scanner;

public class ProdutoFactory {
    //Constroi meu produto, tirando a responsabilidade do App -> "Single Responsibility Principle (SRP)"
    public static Produto criarProduto(TipoProduto tipo, Scanner entrada) {
        //DRY (Don't Repeat Yourself - Não se Repita)
        System.out.println("Digite o nome do produto: ");
        String nomeLido = entrada.nextLine();
        System.out.println("Digite o Codigo do Produto: ");
        int codigoLido = entrada.nextInt();
        entrada.nextLine();
        System.out.println("Digite o preço do produto: ");
        double precoLido = entrada.nextDouble();
        entrada.nextLine();
        switch (tipo) {
            case PRODUTO:
                Produto produtoConstruido = new Produto(nomeLido, codigoLido, precoLido);
                return produtoConstruido;
            case ALIMENTO:
                System.out.println("Digite a validade do Alimento: ");
                String dataDeValidadeLida = entrada.nextLine();
                Produto alimentoConstruido = new Alimento(nomeLido, codigoLido, precoLido, dataDeValidadeLida);
                return alimentoConstruido;
            case ELETRONICO:
                System.out.println("Digite a garantia do produto: ");
                int mesesDeGarantiaLida = entrada.nextInt();
                entrada.nextLine();
                Produto eletronicoConstruido = new Eletronico(nomeLido, codigoLido, precoLido, mesesDeGarantiaLida);
                return eletronicoConstruido;
            case ROUPA:
                System.out.println("Digite o tamanho do produto: ");
                int tamanhoLido = entrada.nextInt();
                entrada.nextLine();
                Produto roupaConstruida = new Roupa(nomeLido, codigoLido, precoLido, tamanhoLido);
                return roupaConstruida;
            default:
                System.out.println("Tipo do produto nao encontrado ");
                return null;
        }

    }
}
