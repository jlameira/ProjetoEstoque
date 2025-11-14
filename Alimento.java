import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * REFACTOR #7: Tipo correto para datas
 * 
 * PROBLEMA ORIGINAL: Data como String
 * - Impossível comparar datas
 * - Impossível validar se está vencido
 * - Aceita qualquer texto (ex: "banana")
 * 
 * SOLUÇÃO: LocalDate (Java 8+)
 * - Tipo específico para datas
 * - Métodos prontos: isBefore(), isAfter(), etc
 * - Validação automática de formato
 * - Padrão da indústria
 */
public class Alimento extends Produto {
    private LocalDate dataDeValidade;
    private static final DateTimeFormatter FORMATO_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor que aceita String e converte para LocalDate
     * Formato esperado: dd/MM/yyyy (ex: 25/12/2025)
     */
    public Alimento(String nome, int codigo, double preco, String dataDeValidade) {
        super(nome, codigo, preco);
        this.setDataDeValidade(dataDeValidade);
    }
    
    /**
     * Construtor alternativo que já recebe LocalDate
     */
    public Alimento(String nome, int codigo, double preco, LocalDate dataDeValidade) {
        super(nome, codigo, preco);
        this.dataDeValidade = dataDeValidade;
    }
    
    public LocalDate getDataDeValidade() {
        return this.dataDeValidade;
    }
    
    /**
     * Retorna data formatada para exibição
     */
    public String getDataDeValidadeFormatada() {
        return this.dataDeValidade.format(FORMATO_BR);
    }
    
    /**
     * REFACTOR: Validação e conversão de String para LocalDate
     */
    public void setDataDeValidade(String dataDeValidade) {
        try {
            this.dataDeValidade = LocalDate.parse(dataDeValidade, FORMATO_BR);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                "Data inválida. Use o formato dd/MM/yyyy (ex: 25/12/2025): " + dataDeValidade
            );
        }
    }
    
    public void setDataDeValidade(LocalDate dataDeValidade) {
        if (dataDeValidade == null) {
            throw new IllegalArgumentException("Data de validade não pode ser nula");
        }
        this.dataDeValidade = dataDeValidade;
    }
    
    /**
     * NOVO MÉTODO: Verifica se o alimento está vencido
     * Isso só é possível porque usamos LocalDate!
     */
    public boolean estaVencido() {
        return LocalDate.now().isAfter(this.dataDeValidade);
    }
    
    /**
     * NOVO MÉTODO: Dias até vencer (negativo se já venceu)
     */
    public long diasAteVencer() {
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), this.dataDeValidade);
    }
    
    @Override
    public String toString() {
        String status = estaVencido() ? " [VENCIDO]" : "";
        return super.toString() + "\n" +
               "Validade: " + getDataDeValidadeFormatada() + status;
    }
}
