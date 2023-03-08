import java.sql.SQLException;

public class Main {

    // Telas
    private static TelaInicio telaInicio;

    public static void inicilizaTelas() throws SQLException {
        telaInicio = new TelaInicio();
    }

    public static void main(String[] args) throws SQLException {
        inicilizaTelas();
        //BancoDeDados.desconectar();
    }

}
