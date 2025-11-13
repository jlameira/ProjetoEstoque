public class Roupa extends Produto {
    private int tamanho;
    public Roupa(String nome, int codigo, double preco, int tamanho){
        super(nome,codigo,preco);
        this.setTamanho(tamanho);
    }
    public int getTamanho(){
        return tamanho;
    }
    public void setTamanho(int tamanho){
        this.tamanho = tamanho;
    }
    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Tamanho: " + this.getTamanho();
    }
}
