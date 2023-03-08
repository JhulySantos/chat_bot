import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerguntaDAO {
    private Connection conexao;

    public PerguntaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public Pergunta buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM perguntas WHERE id = ?";
        try (PreparedStatement retorno = conexao.prepareStatement(sql)) {
            retorno.setInt(1, id);

            ResultSet resultado = retorno.executeQuery();
            if (resultado.next()) {
                Pergunta pergunta = new Pergunta(resultado.getString("pergunta"), resultado.getInt("id_linguagem"), resultado.getInt("id_resposta"));
                pergunta.setId(resultado.getInt("id"));
                return pergunta;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar a pergunta por ID: " + e.getMessage());
        }
        return null;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM perguntas WHERE id = ?";
        try (PreparedStatement resultado = conexao.prepareStatement(sql)) {
            resultado.setInt(1, id);

            resultado.executeUpdate();
            System.out.println("Pergunta deletada com sucesso");

        } catch (SQLException e) {
            System.err.println("erro no delete ao buscar a pergunta por ID: " + e.getMessage());
        }

    }

    public void inserir(Pergunta perguntas) throws SQLException {
        String sql = "INSERT INTO perguntas (pergunta, id_linguagem, id_resposta) VALUES (?, ?, ?)";

        try (PreparedStatement resposta = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            resposta.setString(1, perguntas.getPergunta());
            resposta.setInt(2, perguntas.getIdLinguagem());
            resposta.setInt(3, perguntas.getIdResposta());

            resposta.executeUpdate();

            ResultSet resultado = resposta.getGeneratedKeys();
            if (resultado.next()) {
                int id = resultado.getInt(1);
                perguntas.setId(Integer.parseInt(String.valueOf(id)));

                System.out.println("pergunta criada com sucesso");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir a pergunta");
        }
    }

    public void atualizar(Pergunta perguntas) throws SQLException {
        String sql = "UPDATE perguntas SET pergunta = ? WHERE id = ?";

        try (PreparedStatement resposta = conexao.prepareStatement(sql)) {
            resposta.setString(1, perguntas.getPergunta());
            resposta.setInt(2, perguntas.getId());

            resposta.executeUpdate();

            System.out.println("pergunta Atualizado com sucesso!!!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a pergunta");
        }

    }

    public Pergunta buscarPorlingua(int id) throws SQLException {
        String sql = "SELECT * FROM perguntas WHERE id_linguagem = ?";
        try (PreparedStatement retorno = conexao.prepareStatement(sql)) {
            retorno.setInt(1, id);

            ResultSet resultado = retorno.executeQuery();
            if (resultado.next()) {
                Pergunta pergunta = new Pergunta(resultado.getString("pergunta"), resultado.getInt("id_linguagem"), resultado.getInt("id_resposta"));
                pergunta.setId(resultado.getInt("id"));
                return pergunta;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar a pergunta por ID: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Pergunta> listarPerguntas() {
        ArrayList<Pergunta> lista = new ArrayList<Pergunta>();
        try {
            String query = "SELECT * FROM perguntas";
            Statement resposta = conexao.createStatement();
            ResultSet resultado = resposta.executeQuery(query);
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String pergunta = resultado.getString("pergunta");
                int id_linguagem = resultado.getInt("id_linguagem");
                int id_resposta = resultado.getInt("id_resposta");
                Pergunta perguntas = new Pergunta(pergunta,id_linguagem,id_resposta);
                perguntas.setId(resultado.getInt("id"));
                lista.add(perguntas);
            }
            resultado.close();
            resposta.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta SQL.");
        }
        return lista;
    }

    public ArrayList<Pergunta> listarPerguntaJava () {
        ArrayList<Pergunta> lista = new ArrayList<Pergunta>();
        try {
            String query = "SELECT * FROM perguntas where id_linguagem = 1";
            Statement resposta = conexao.createStatement();
            ResultSet resultado = resposta.executeQuery(query);
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String pergunta = resultado.getString("pergunta");
                int id_linguagem = resultado.getInt("id_linguagem");
                int id_resposta = resultado.getInt("id_resposta");
                Pergunta perguntas = new Pergunta(pergunta,id_linguagem,id_resposta);
                perguntas.setId(resultado.getInt("id"));
                lista.add(perguntas);
            }
            resultado.close();
            resposta.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta SQL.");
        }
        return lista;
    }

    public ArrayList<Pergunta> listarPerguntaMysql(){
        ArrayList<Pergunta> lista = new ArrayList<Pergunta>();
        try {
            String query = "SELECT * FROM perguntas where id_linguagem = 2";
            Statement resposta = conexao.createStatement();
            ResultSet resultado = resposta.executeQuery(query);
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String pergunta = resultado.getString("pergunta");
                int id_linguagem = resultado.getInt("id_linguagem");
                int id_resposta = resultado.getInt("id_resposta");
                Pergunta perguntas = new Pergunta(pergunta,id_linguagem,id_resposta);
                perguntas.setId(resultado.getInt("id"));
                lista.add(perguntas);
            }
            resultado.close();
            resposta.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta SQL.");
        }
        return lista;
    }

    public List<Pergunta> buscarTodos() {
        ArrayList<Pergunta> lista = new ArrayList<Pergunta>();
        try {
            String query = "SELECT * FROM perguntas";
            Statement resposta = conexao.createStatement();
            ResultSet resultado = resposta.executeQuery(query);
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String pergunta = resultado.getString("pergunta");
                int id_linguagem = resultado.getInt("id_linguagem");
                int id_resposta = resultado.getInt("id_resposta");
                Pergunta perguntas = new Pergunta(pergunta,id_linguagem,id_resposta);
                perguntas.setId(resultado.getInt("id"));
                lista.add(perguntas);
            }
            resultado.close();
            resposta.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta SQL.");
        }
        return lista;
    }
}