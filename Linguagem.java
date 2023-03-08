public class Linguagem {
    private int id;
    private String linguagem;

    public Linguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public void setDescricao(String linguagem) {
        this.linguagem = linguagem;
    }

    @Override
    public String toString() {
        String retorno = getLinguagem();
        return retorno;
    }

}