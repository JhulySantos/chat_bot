public class Resposta {
    private String id;
    public String respostas;

    public Resposta( String respostas) {
        this.respostas = respostas;
    }
    public void setRespostas(String nova_resposta) {
        respostas = nova_resposta;
    }
    public String getRespostas(){
        return respostas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String retorno = getRespostas();
        return retorno;
    }

}