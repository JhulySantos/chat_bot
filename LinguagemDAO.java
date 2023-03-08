import java.sql.*;
import java.util.ArrayList;

public class LinguagemDAO {
    private static Connection conexao;

    public LinguagemDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public static Linguagem buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM linguagens WHERE id = ?";
        try (PreparedStatement retorno = conexao.prepareStatement(sql)) {
            retorno.setInt(1, id);

            ResultSet resultado = retorno.executeQuery();
            if (resultado.next()) {
                Linguagem linguagens = new Linguagem(resultado.getString("linguagem"));
                linguagens.setId(resultado.getInt("id"));
                return linguagens;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar a linguagem por ID: " + e.getMessage());
        }
        return null;
    }

    public static void deletar(int id) throws SQLException {
        String sql = "DELETE FROM linguagens WHERE id = ?";
        try (PreparedStatement resultado = conexao.prepareStatement(sql)) {
            resultado.setInt(1, id);

            resultado.executeUpdate();
            System.out.println("linguagem deletada com sucesso");

        } catch (SQLException e) {
            System.err.println("Erro ao buscar a linguagem por ID: " + e.getMessage());
        }

    }

    public void inserir(Linguagem linguagens) throws SQLException {
        String sql = "INSERT INTO linguagens (linguagem) VALUES (?)";

        try (PreparedStatement resposta = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            resposta.setString(1, linguagens.getLinguagem());

            resposta.executeUpdate();

            ResultSet resultado = resposta.getGeneratedKeys();
            if (resultado.next()) {
                int id = resultado.getInt(1);
                linguagens.setId(id);

                System.out.println("linguagem inserida com sucesso");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir a linguagem");
        }
    }

    public void atualizar(Linguagem linguagens) throws SQLException {
        String sql = "UPDATE linguagens SET linguagem = ? WHERE id = ?";

        try (PreparedStatement resposta = conexao.prepareStatement(sql)) {
            resposta.setString(1, linguagens.getLinguagem());
            resposta.setString(2, String.valueOf(linguagens.getId()));

            resposta.executeUpdate();

            System.out.println("linguagem Atualizada com sucesso!!!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar a linguagem");
        }

    }

    public static ArrayList<Linguagem> listarLinguagens() {
        ArrayList<Linguagem> lista = new ArrayList<Linguagem>();
        try {
            String query = "SELECT * FROM linguagens";
            Statement resposta = conexao.createStatement();
            ResultSet resultado = resposta.executeQuery(query);
            while (resultado.next()) {
                String descricao = resultado.getString("linguagem");
                Linguagem linguagen = new Linguagem( descricao);
                linguagen.setId(resultado.getInt("id"));
                lista.add(linguagen);
            }
            resultado.close();
            resposta.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta SQL.");
        }
        return lista;
    }
}
