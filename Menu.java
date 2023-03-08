import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Menu {
    private static UsuarioDAO usuarioDAO;
    private static TelaLogin telaLogin;
    private static TelaResposta telaResposta;
    private static RespostaDAO respostaDAO;
    private static TelaUsuario telaUsuario;
    private static LinguagemDAO linguagemDAO;
    private static TelaLinguagem telaLinguagem;
    private static PerguntaDAO perguntasDAO;
    private static TelaPergunta telaPergunta;

    public static void menuResposta(int resposta) {
        try (Connection conexao = BancoDeDados.conectar()) {
            RespostaDAO dao = new RespostaDAO(conexao);


            Resposta resposta1 = dao.buscarPorId(resposta);
            JOptionPane.showMessageDialog(null, resposta1.getRespostas());

            String sair = JOptionPane.showInputDialog(null, "Você deseja saber mais alguma coisa?(sim ou nao)");
            if (sair.equals("sim")) {
                menulingua();
            } else if (sair.equals("nao")){
                JOptionPane.showMessageDialog(null,"obrigado por usar o programa");
            }


        } catch (SQLException e) {


            System.out.println("Erro ao conectar com o banco de dados");
        }


    }

    public static void menuPergunta(int lingua) {
        try (Connection conexao = BancoDeDados.conectar()) {
            PerguntaDAO dao = new PerguntaDAO(conexao);
            String texto = " ";


            if (lingua == 1) {
                for (int i = 1; i < dao.listarPerguntas().size(); i++) {
                    ArrayList<Pergunta> linguagen2 = dao.listarPerguntaJava();

                    texto = texto + "\n" + (linguagen2.get(i - 1).getIdResposta()) + ": " + linguagen2.get(i - 1);


                    if (i == dao.listarPerguntaJava().size()) {
                        int resposta = Integer.parseInt(JOptionPane.showInputDialog(null, "Escolha a pergunta: \n" + texto));
                        menuResposta(resposta);
                    }
                }
            } else if (lingua == 2) {

                for (int i = 1; i < dao.listarPerguntas().size(); i++) {
                    ArrayList<Pergunta> linguagen2 = dao.listarPerguntaMysql();

                    texto = texto + "\n" + (linguagen2.get(i - 1).getIdResposta()) + ": " + linguagen2.get(i - 1);


                    if (i == dao.listarPerguntaMysql().size()) {
                        int resposta = Integer.parseInt(JOptionPane.showInputDialog(null, "Escolha a pergunta: " +
                                texto));
                        menuResposta(resposta);

                    }
                }
            }


        } catch (SQLException e) {


            System.out.println("Erro ao conectar com o banco de dados");
        }
    }

    public static void menulingua() {
        try (Connection conexao = BancoDeDados.conectar()) {

            int linguaescolha = Integer.parseInt(JOptionPane.showInputDialog(null, "Escolha 1 para java\nEscolha 2 para mySQL "));
            menuPergunta(linguaescolha);

        } catch (SQLException e) {


            System.out.println("Erro ao conectar com o banco de dados");
        }
    }

    public static void menuCadastro() {
        try (Connection conexao = BancoDeDados.conectar()) {
            UsuarioDAO dao = new UsuarioDAO(conexao);
            String nome = JOptionPane.showInputDialog(null, "Informe o nome:");
            String login = JOptionPane.showInputDialog(null, "Informe o login:");
            String senha = JOptionPane.showInputDialog(null, "Informe a senha:");

            Usuario usuario = new Usuario(nome, login, senha);
            dao.inserir(usuario);

            Main.inicilizaTelas();

        } catch (SQLException e) {


            System.out.println("Erro ao conectar com o banco de dados");
        }
    }

    public static void inicializaDAO() throws SQLException {
        //mudar o usuariodao para acessodao quando necessario e vice versa
        usuarioDAO = new UsuarioDAO(BancoDeDados.conectar());
        respostaDAO = new RespostaDAO(BancoDeDados.conectar());
        linguagemDAO = new LinguagemDAO(BancoDeDados.conectar());
        perguntasDAO = new PerguntaDAO(BancoDeDados.conectar());
    }

    public static void inicilizaTelaLogin() throws SQLException {
        telaLogin = new TelaLogin(usuarioDAO);
    }

    public static void menuLogin() throws SQLException {
        inicializaDAO();
        inicilizaTelaLogin();
    }
    public static void inicilizaTelaResposta() throws SQLException {
        telaResposta = new TelaResposta(respostaDAO);
    }
    public static void inicilizaTelaUsuario() throws SQLException {
        telaUsuario = new TelaUsuario(usuarioDAO);
    }
    public static void inicilizaTelaLinguagem() throws SQLException {
        telaLinguagem = new TelaLinguagem(linguagemDAO);
    }
    public static void inicilizaTelaPergunta() throws SQLException {
        telaPergunta = new TelaPergunta(perguntasDAO);
    }
    public static void menucruds() throws SQLException {
        inicializaDAO();
        int crudEscolha = Integer.parseInt(JOptionPane.showInputDialog(null, "Escolha 1 para programa\n" +
                "Escolha 2 para crud resposta\n" +
                "Escolha 3 para crud perguntas\n" +
                "Escolha 4 para crud linguagem\n" +
                "Escolha 5 para crud usuarios "));
        if (crudEscolha == 1){
            menulingua();
        } else  if (crudEscolha==2){
            inicilizaTelaResposta();
        } else if (crudEscolha==3){
            inicilizaTelaPergunta();
        } else if (crudEscolha==4){
            inicilizaTelaLinguagem();
        }else if (crudEscolha==5) {
            inicilizaTelaUsuario();
        }
    }
}
