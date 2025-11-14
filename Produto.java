/**
 * REFACTOR #6: Validações que falham explicitamente
 * 
 * PROBLEMA ORIGINAL: Setters ignoravam valores inválidos silenciosamente
 * SOLUÇÃO: Lançar IllegalArgumentException para valores inválidos
 * 
 * PRINCÍPIO: Fail Fast
 * - Melhor falhar imediatamente do que continuar com estado inválido
 * - Facilita debug (erro aparece onde realmente aconteceu)
 * - Garante que objetos sempre estão em estado válido
 */
public class Produto {
    private String nome;
    private int codigo;
    private double preco;

    public Produto(String nome, int codigo, double preco) {
        this.setNome(nome);
        this.setCodigo(codigo);
        this.setPreco(preco);
    }
    
    public String getNome() {
        return this.nome;
    }
    
    /**
     * Validação de nome não vazio
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
        this.nome = nome.trim();
    }
    
    public int getCodigo() {
        return this.codigo;
    }
    
    /**
     * REFACTOR: Validação explícita com exceção
     * ANTES: Se código < 0, simplesmente não setava (silencioso)
     * DEPOIS: Lança exceção informando o erro
     */
    public void setCodigo(int codigoParametro) {
        if (codigoParametro < 0) {
            throw new IllegalArgumentException("Código do produto não pode ser negativo: " + codigoParametro);
        }
        this.codigo = codigoParametro;
    }
    
    public double getPreco() {
        return this.preco;
    }
    
    /**
     * REFACTOR: Validação explícita com exceção
     * ANTES: Se preço < 0, simplesmente não setava (silencioso)
     * DEPOIS: Lança exceção informando o erro
     */
    public void setPreco(double precoParametro) {
        if (precoParametro < 0) {
            throw new IllegalArgumentException("Preço do produto não pode ser negativo: " + precoParametro);
        }
        this.preco = precoParametro;
    }
    
    @Override
    public String toString() {
        return String.format("Nome: %s%nCódigo: %d%nPreço: R$ %.2f", 
                           this.getNome(), 
                           this.getCodigo(), 
                           this.getPreco());
    }
}



