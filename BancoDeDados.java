import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDeDados {

    private static final String URL = "jdbc:mysql://localhost:3306/chat_boot";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    private static Connection conexao;

    public static Connection conectar() throws SQLException {
        conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        return conexao;
    }

    public static void desconectar() throws SQLException {
        if (conexao != null) {
            conexao.close();
            conexao = null;
        }
    }

    public static boolean testarConexao() {
        try {
            conectar();
            desconectar();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return false;
        }
    }
}