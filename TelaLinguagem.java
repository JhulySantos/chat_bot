import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaLinguagem extends JFrame {

    private static final long serialVersionUID = 1L;
    private LinguagemDAO linguagemDAO;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoLinguagem;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoRemover;

    public TelaLinguagem( LinguagemDAO linguagemDAO) throws SQLException {
        this.linguagemDAO = linguagemDAO;
        inicializaComponentes();
        atualizarTabela();
    }

    private void inicializaComponentes() {
        // painel para a tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Linguagem"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        painelTabela.add(scrollPane);

        // painel para o formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelLinguagem = new JLabel("Linguagem:");
        campoLinguagem = new JTextField();
        campoLinguagem.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoLinguagem.getPreferredSize().height));
        botaoAdicionar = new JButton("Adicionar");
        botaoEditar = new JButton("Editar");
        botaoRemover = new JButton("Remover");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
        painelFormulario.add(labelLinguagem);
        painelFormulario.add(campoLinguagem);
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
                    adicionarLinguagem();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarLinguagem();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removerLinguagem();
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
                int id = Integer.parseInt(String.valueOf(tabela.getValueAt(linhaSelecionada, 0)));
                Linguagem linguagem = null;
                try {
                    linguagem = LinguagemDAO.buscarPorId(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                campoLinguagem.setText(linguagem.getLinguagem());
            } else {
                botaoEditar.setEnabled(false);
                botaoRemover.setEnabled(false);
                campoLinguagem.setText("");
            }
        });

        // configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRUD de Linguagem");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<Linguagem> linguagem = LinguagemDAO.listarLinguagens();
        for (Linguagem linguagem1 : linguagem) {
            modeloTabela.addRow(new Object[]{linguagem1.getId(), linguagem1.getLinguagem()});
        }
    }
    private void adicionarLinguagem() throws SQLException {
        String descricao = campoLinguagem.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a linguagem.");
            return;
        }
        Linguagem linguagem = new Linguagem(descricao);
        linguagemDAO.inserir(linguagem);
        atualizarTabela();
        campoLinguagem.setText("");
    }

    private void editarLinguagem() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = Integer.parseInt(String.valueOf(tabela.getValueAt(linhaSelecionada, 0)));
        String descricao = campoLinguagem.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a linguagem.");
            return;
        }
        Linguagem linguagem = linguagemDAO.buscarPorId(id);
        linguagem.setLinguagem(descricao);
        linguagemDAO.atualizar(linguagem);
        atualizarTabela();
        campoLinguagem.setText("");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
    }

    private void removerLinguagem() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = Integer.parseInt(String.valueOf(tabela.getValueAt(linhaSelecionada, 0)));
        Linguagem linguagem = LinguagemDAO.buscarPorId(id);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a linguagem " + linguagem.getLinguagem() + "?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            LinguagemDAO.deletar(id);
            atualizarTabela();
            campoLinguagem.setText("");
            botaoEditar.setEnabled(false);
            botaoRemover.setEnabled(false);
        }
    }
}