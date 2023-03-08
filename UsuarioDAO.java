import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO(Connection conexao) {
        this.conexao = conexao;
    }
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement retorno = conexao.prepareStatement(sql)) {
            retorno.setInt(1, id);

            ResultSet resultado = retorno.executeQuery();
            if (resultado.next()) {
                Usuario usuario = new Usuario(resultado.getString("nome"), resultado.getString("login"), resultado.getString("senha"));
                usuario.setId(resultado.getInt("id"));
                return usuario;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar a pergunta por ID: " + e.getMessage());
        }
        return null;
    }
    public Usuario excluirPorId(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (PreparedStatement retorno = conexao.prepareStatement(sql)) {
            retorno.setInt(1, id);

            retorno.executeUpdate();

            System.out.println("Usuario Deletado com sucesso!!!");
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar o Usuario");
        }
        return null;
    }
    public void inserir(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios ( nome, login, senha ) VALUES ( ?, ?, ? )";
        try (PreparedStatement resposta = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            resposta.setString(1, usuario.getNome());
            resposta.setString(2, usuario.getLogin());
            resposta.setString(3, usuario.getSenha());

            resposta.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao Inserir o Usuario : " + e.getMessage());
        }
    }
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?,senha = ? , login = ? WHERE id = ?";
        try (PreparedStatement retorno = conexao.prepareStatement(sql)) {
            retorno.setString(1, usuario.getNome());
            retorno.setString(2, usuario.getSenha());
            retorno.setString(3, usuario.getLogin());
            retorno.setString(4, String.valueOf(usuario.getId()));

            retorno.executeUpdate();

            System.out.println("Usuário Atualizado com sucesso!!!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o usuário");
        }
    }
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> listarUsuarios = new ArrayList<Usuario>();
        try {
            String query = "SELECT * FROM usuarios";
            Statement resposta= conexao.createStatement();
            ResultSet resultado = resposta.executeQuery(query);

            while (resultado.next()) {
                int id = resultado .getInt("id");
                String nome = resultado.getString("nome");
                String login = resultado.getString("login");
                String senha = resultado.getString("senha");
                Usuario usuarios = new Usuario(nome,login,senha);

                usuarios.setId(resultado.getInt("id"));
                listarUsuarios.add(usuarios);
            }
            resultado.close();
            resposta.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta SQL.");
        }
        return listarUsuarios;
    }
    public boolean conferirLogin(String login, String senha) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
