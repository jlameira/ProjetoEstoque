/**
 * REFACTOR #8: Enum para tamanhos de roupa
 * 
 * PROBLEMA ORIGINAL: Tamanho como int (não faz sentido)
 * SOLUÇÃO: Enum com valores válidos
 * 
 * VANTAGENS:
 * - Type safety: só aceita valores válidos
 * - Autocomplete na IDE
 * - Impossível ter tamanho inválido (ex: -5, 999)
 * - Pode adicionar métodos úteis
 */
public enum TamanhoRoupa {
    PP("Extra Pequeno"),
    P("Pequeno"),
    M("Médio"),
    G("Grande"),
    GG("Extra Grande"),
    XG("Extra Extra Grande");
    
    private final String descricao;
    
    TamanhoRoupa(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    /**
     * Converte String para enum (útil para input do usuário)
     */
    public static TamanhoRoupa fromString(String texto) {
        for (TamanhoRoupa tamanho : TamanhoRoupa.values()) {
            if (tamanho.name().equalsIgnoreCase(texto)) {
                return tamanho;
            }
        }
        throw new IllegalArgumentException("Tamanho inválido: " + texto + 
                                         ". Use: PP, P, M, G, GG ou XG");
    }
}
