import java.sql.*;
import java.util.ArrayList;

public class RespostaDAO {
    private Connection conexao;

    public RespostaDAO(Connection conexao) {
        this.conexao = conexao;
    }
    public Resposta buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM respostas WHERE id = ?";

        try (PreparedStatement retorno = conexao.prepareStatement(sql)) {
            retorno.setInt(1, id);

            ResultSet resultado = retorno.executeQuery();

            if (resultado.next()) {
                Resposta resposta = new Resposta(resultado.getString("resposta"));
                resposta.setId(resultado.getString("id"));

                return resposta;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar o resposta por ID: " + e.getMessage());
        }
        return null;
    }
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM respostas WHERE id = ?";
        try (PreparedStatement resultado = conexao.prepareStatement(sql)) {
            resultado.setInt(1, id);

            resultado.executeUpdate();
            System.out.println("resposta deletado com sucesso");

        } catch (SQLException e) {
            System.err.println("erros delete ao buscar a resposta por ID: " + e.getMessage());
        }

    }

    public void inserir(Resposta respostas) throws SQLException {
        String sql = "INSERT INTO respostas (resposta) VALUES (?)";

        try (PreparedStatement resposta = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            resposta.setString(1, respostas.getRespostas());


            resposta.executeUpdate();

            ResultSet resultado = resposta.getGeneratedKeys();
            if (resultado.next()) {
                int id = resultado.getInt(1);
                respostas.setId(String.valueOf(id));

                System.out.println("resposta criada com sucesso");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao criar a resposta");
        }
    }

    public void atualizar(Resposta resposta) throws SQLException {
        String sql = "UPDATE respostas SET resposta = ? WHERE id = ?";

        try (PreparedStatement respostadao = conexao.prepareStatement(sql)) {
            respostadao.setString(1, resposta.getRespostas());
            respostadao.setString(2, resposta.getId());

            respostadao.executeUpdate();

            System.out.println("resposta Atualizado com sucesso!!!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a resposta");
        }

    }
    public ArrayList<Resposta> listarRespostas() {
        ArrayList<Resposta> lista = new ArrayList<Resposta>();
        try {
            String query = "SELECT * FROM respostas";
            Statement resposta = conexao.createStatement();
            ResultSet resultado = resposta.executeQuery(query);
            while (resultado.next()) {
                String id = resultado.getString("id");
                String descricao = resultado.getString("resposta");
                Resposta resposta1 = new Resposta(descricao);
                resposta1.setId(resultado.getString("id"));
                lista.add(resposta1);
            }
            resultado.close();
            resposta.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta SQL.");
        }
        return lista;
    }
}