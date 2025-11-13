public class Estoque {
    private Produto[] produto;
    private int proximaPosLivre = 0;

    public Estoque(int capacidade) {
        this.produto = new Produto[capacidade];
    }
    public void adicionarProduto(Produto p){
        if(this.proximaPosLivre < this.produto.length){
            this.produto[proximaPosLivre] = p;
            proximaPosLivre++;
        }
        else{
            System.out.println("Erro, Estoque Cheio");
        }

    }
    public void listarProdutos(){
        System.out.println("---Listando Produtos---");
        for(int j = 0; j < this.proximaPosLivre; j++){
            System.out.println(produto[j].toString() + "\n");
        }
        System.out.println("----------------------------");
    }
    public Produto buscarProdutoPorCodigo( int codigoDoProduto){
        for(int k = 0; k < this.proximaPosLivre; k++){
            if(this.produto[k].getCodigo() == codigoDoProduto){
                return this.produto[k];
            }
        }
        return null;
    }
    public boolean removerProduto(int codigoParaRemover){
        for(int k = 0; k < this.proximaPosLivre; k++){
            if(this.produto[k].getCodigo() == codigoParaRemover){
                for(int j = k; j < this.proximaPosLivre - 1; j++){
                    this.produto[j] = this.produto[j+1];
                }
                this.proximaPosLivre--;
                this.produto[this.proximaPosLivre] = null;
                return true;
            }
        }
        return false;
    }
}

