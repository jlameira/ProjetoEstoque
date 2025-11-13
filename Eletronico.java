public class Eletronico extends Produto {
    private int mesesDeGarantia;

    public Eletronico(String nome, int codigo, double preco, int mesesDeGarantia) {
        super(nome,codigo,preco);
        this.setMesesDeGarantia(mesesDeGarantia);
    }
    public int getMesesDeGarantia() {
        return mesesDeGarantia;
    }
    public void setMesesDeGarantia(int mesesDeGarantia) {
        this.mesesDeGarantia = mesesDeGarantia;
    }
    @Override
    public String toString() {
        return super.toString() + "\n" +
                "Meses de Garantia: " + this.getMesesDeGarantia();
    }
}

