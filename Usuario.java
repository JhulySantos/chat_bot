public class Usuario {
    private int id;
    private String nome;
    private String login;
    private String senha;

    public Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }
    public void setId(int novo_id){
        id = novo_id;
    }
    public int getId(){
        return id;
    }
    public void setNome(String novo_nome){
        nome = novo_nome;
    }
    public String getNome(){
        return nome;
    }
    public void setLogin(String novo_login){
        login = novo_login;
    }
    public String getLogin(){
        return login;
    }
    public void setSenha(String nova_senha){
        senha = nova_senha;
    }
    public String getSenha(){
        return senha;
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

}


