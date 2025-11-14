/**
 * REFACTOR #8: Enum para tamanho
 * 
 * PROBLEMA ORIGINAL: Tamanho como int não faz sentido semântico
 * SOLUÇÃO: Usar enum TamanhoRoupa
 * 
 * BENEFÍCIO: Type safety - impossível ter tamanho inválido
 */
public class Roupa extends Produto {
    private TamanhoRoupa tamanho;
    
    /**
     * Construtor que aceita enum diretamente
     */
    public Roupa(String nome, int codigo, double preco, TamanhoRoupa tamanho) {
        super(nome, codigo, preco);
        this.setTamanho(tamanho);
    }
    
    /**
     * Construtor alternativo que aceita String e converte
     * Útil para manter compatibilidade com input do usuário
     */
    public Roupa(String nome, int codigo, double preco, String tamanho) {
        super(nome, codigo, preco);
        this.setTamanho(TamanhoRoupa.fromString(tamanho));
    }
    
    public TamanhoRoupa getTamanho() {
        return tamanho;
    }
    
    public void setTamanho(TamanhoRoupa tamanho) {
        if (tamanho == null) {
            throw new IllegalArgumentException("Tamanho não pode ser nulo");
        }
        this.tamanho = tamanho;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Tamanho: " + this.tamanho.name() + " (" + this.tamanho.getDescricao() + ")";
    }
}
