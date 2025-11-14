import java.util.ArrayList;
import java.util.List;

/**
 * REFACTOR #1: Array → ArrayList
 * 
 * PROBLEMA ORIGINAL: Array fixo limitava o estoque e exigia código complexo para remoção
 * SOLUÇÃO: ArrayList cresce dinamicamente e tem métodos prontos (add, remove, etc)
 * 
 * VANTAGENS:
 * - Sem limite de capacidade
 * - Código mais simples e legível
 * - Menos propenso a bugs (IndexOutOfBounds, etc)
 * - Padrão da indústria para coleções dinâmicas
 */
public class Estoque {
    // List é a interface, ArrayList é a implementação
    // Boa prática: programar para interface, não para implementação
    private List<Produto> produtos;

    public Estoque() {
        // Não precisa mais de capacidade inicial
        this.produtos = new ArrayList<>();
    }
    
    /**
     * REFACTOR #2: Validação com exceção
     * 
     * PROBLEMA ORIGINAL: System.out.println dentro da classe de negócio
     * SOLUÇÃO: Lançar exceção e deixar quem chama decidir como tratar
     * 
     * PRINCÍPIO: Separação de responsabilidades (SRP)
     * - Estoque cuida da lógica de negócio
     * - App/UI cuida de mostrar mensagens ao usuário
     */
    public void adicionarProduto(Produto p) {
        if (p == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo");
        }
        
        // Validar código duplicado
        if (buscarProdutoPorCodigo(p.getCodigo()) != null) {
            throw new IllegalArgumentException("Já existe um produto com o código " + p.getCodigo());
        }
        
        produtos.add(p);
    }
    
    /**
     * REFACTOR #3: Retornar lista imutável
     * 
     * PROBLEMA: Se retornarmos a lista direta, alguém pode modificá-la externamente
     * SOLUÇÃO: Retornar cópia ou usar Collections.unmodifiableList()
     */
    public List<Produto> listarProdutos() {
        // Retorna uma nova lista para não expor a interna
        return new ArrayList<>(produtos);
    }
    
    /**
     * REFACTOR #4: Enhanced for loop
     * 
     * VANTAGEM: Mais legível, menos propenso a erros de índice
     */
    public Produto buscarProdutoPorCodigo(int codigoDoProduto) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigoDoProduto) {
                return produto;
            }
        }
        return null;
    }
    
    /**
     * REFACTOR #5: Remoção simplificada
     * 
     * ANTES: Loop manual para deslocar elementos
     * DEPOIS: ArrayList.remove() faz isso automaticamente
     */
    public boolean removerProduto(int codigoParaRemover) {
        Produto produto = buscarProdutoPorCodigo(codigoParaRemover);
        if (produto != null) {
            produtos.remove(produto);
            return true;
        }
        return false;
    }
    
    /**
     * Método auxiliar útil
     */
    public int quantidadeProdutos() {
        return produtos.size();
    }
    
    /**
     * Verifica se o estoque está vazio
     */
    public boolean estaVazio() {
        return produtos.isEmpty();
    }
}

