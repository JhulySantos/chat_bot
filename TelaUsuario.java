import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaUsuario extends JFrame {

    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoNome;
    private JTextField campoLogin;
    private JTextField campoSenha;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoRemover;

    public TelaUsuario(UsuarioDAO usuarioDAO) throws SQLException {
        this.usuarioDAO = usuarioDAO;
        inicializaComponentes();
        atualizarTabela();
    }

    private void inicializaComponentes() {
        // painel para a tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome","Login","Senha"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        painelTabela.add(scrollPane);

        // painel para o formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelNome = new JLabel("Nome:");
        JLabel labelLogin = new JLabel("Login:");
        JLabel labelSenha = new JLabel("Senha:");
        campoNome = new JTextField();
        campoNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoNome.getPreferredSize().height));
        campoLogin = new JTextField();
        campoLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoLogin.getPreferredSize().height));
        campoSenha = new JTextField();
        campoSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoSenha.getPreferredSize().height));
        botaoAdicionar = new JButton("Adicionar");
        botaoEditar = new JButton("Editar");
        botaoRemover = new JButton("Remover");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
        painelFormulario.add(labelNome);
        painelFormulario.add(campoNome);
        painelFormulario.add(labelLogin);
        painelFormulario.add(campoLogin);
        painelFormulario.add(labelSenha);
        painelFormulario.add(campoSenha);
        painelFormulario.add(botaoAdicionar);
        painelFormulario.add(botaoEditar);
        painelFormulario.add(botaoRemover);

        // painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelFormulario, BorderLayout.EAST);
        getContentPane().add(painelPrincipal);

        // ações dos botões
        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adicionarUsuario();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarUsuario();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removerUsuario();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        tabela.getSelectionModel().addListSelectionListener(event -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada != -1) {
                botaoEditar.setEnabled(true);
                botaoRemover.setEnabled(true);
                int id = (int) tabela.getValueAt(linhaSelecionada, 0);
                Usuario usuario = null;
                try {
                    usuario = usuarioDAO.buscarPorId(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                campoNome.setText(usuario.getNome());
                campoLogin.setText(usuario.getLogin());
                campoSenha.setText(usuario.getSenha());
            } else {
                botaoEditar.setEnabled(false);
                botaoRemover.setEnabled(false);
                campoNome.setText("");
            }
        });

        // configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRUD de Login");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        for (Usuario usuario : usuarios) {
            modeloTabela.addRow(new Object[]{usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getSenha()});
        }
    }

    private void adicionarUsuario() throws SQLException {
        String nome = campoNome.getText().trim();
        String login = campoLogin.getText().trim();
        String senha = campoSenha.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome.");
            return;
        }
        if (login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o login.");
            return;
        }
        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o senha.");
            return;
        }
        Usuario usuario = new Usuario(nome,login,senha);
        usuarioDAO.inserir(usuario);
        atualizarTabela();
        campoNome.setText("");
        campoLogin.setText("");
        campoSenha.setText("");
    }

    private void editarUsuario() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        String nome = campoNome.getText().trim();
        String login = campoLogin.getText().trim();
        String senha = campoSenha.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome.");
            return;
        }
        if (login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o login.");
            return;
        }
        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a senha.");
            return;
        }
        Usuario usuario = usuarioDAO.buscarPorId(id);
        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setSenha(senha);
        usuarioDAO.atualizar(usuario);

        atualizarTabela();
        campoNome.setText("");
        campoLogin.setText("");
        campoSenha.setText("");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
    }

    private void removerUsuario() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        Usuario usuario = usuarioDAO.buscarPorId(id);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover o usuario " + usuario.getNome() + "?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            usuarioDAO.excluirPorId(id);
            atualizarTabela();
            campoNome.setText("");
            campoLogin.setText("");
            campoSenha.setText("");
            botaoEditar.setEnabled(false);
            botaoRemover.setEnabled(false);
        }
    }
}


