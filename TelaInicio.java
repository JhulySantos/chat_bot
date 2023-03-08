import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class TelaInicio extends JFrame implements ActionListener {

    private final JButton botaoLogin;
    private final JButton botaocadastro;
    private static UsuarioDAO acessoDAO;
    private static TelaLogin telaLogin;

    public static void inicilizaTelas() throws SQLException {
        telaLogin = new TelaLogin(acessoDAO);
    }
    public TelaInicio() {


        setTitle("Tela de inicio");

        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel(new GridLayout(2, 2));

        JLabel labelLogin = new JLabel("deseja fazer login ou cadastro");

        botaoLogin = new JButton("Login");
        botaoLogin.addActionListener(this);
        painel.add(botaoLogin);

        botaocadastro = new JButton("cadastro");
        botaocadastro.addActionListener(this);
        painel.add(botaocadastro);

        add(painel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoLogin) {
            //  String login = campoLogin.getText();
            // String senha = campoSenha.getText();

            // if (acessoDAO.conferirLogin(login, senha)) {

            dispose();
            try {
                Menu.menuLogin();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == botaocadastro){

            dispose();
            Menu.menuCadastro();
        }
    }
}
