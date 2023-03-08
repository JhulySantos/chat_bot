public class Pergunta {

    private int id;
    private String pergunta;
    private int idLinguagem;
    private int idResposta;

    public Pergunta(String pergunta, int idLinguagem, int idResposta) {
        this.id = id;
        this.pergunta = pergunta;
        this.idLinguagem = idLinguagem;
        this.idResposta = idResposta;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public int getIdLinguagem() {
        return idLinguagem;
    }

    public void setIdLinguagem(int idLinguagem) {
        this.idLinguagem = idLinguagem;
    }

    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    @Override
    public String toString() {
        String retorno = getPergunta();
        return retorno;
    }

}