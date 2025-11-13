public class Alimento extends Produto {
    private String dataDeValidade;

    public Alimento(String nome, int codigo, double preco, String dataDeValidade){
        super(nome, codigo, preco);
        this.setDataDeValidade(dataDeValidade);
    }
    public String getDataDeValidade() {
        return this.dataDeValidade;
    }
    public void setDataDeValidade(String dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Validade: " + this.getDataDeValidade();
    }
}
