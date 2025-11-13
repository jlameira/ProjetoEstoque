public class Produto {
    private String nome = "";
    private int codigo;
    private double preco;

    public Produto(String nome, int codigo, double preco) {
        this.setNome(nome);
        this.setCodigo(codigo); // Chama o seu método 'setCodigo'
        this.setPreco(preco);   // Chama o seu método 'setPreco'
    }
    public String getNome() {
        return this.nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getCodigo() {
        return this.codigo;
    }
    public void setCodigo(int codigoParametro) {
        if(codigoParametro >= 0) {
            codigo = codigoParametro;
        }
    }
    public double getPreco() {
        return this.preco;
    }
    public void setPreco(double precoParametro) {
        if(precoParametro >= 0) {
            preco = precoParametro;
        }
    }
    @Override
    public String toString() {
        return "Nome: " + this.getNome() + "\n" +
                "Código: " + this.getCodigo() + "\n" +
                "Preço: " + this.getPreco();
    }
}



