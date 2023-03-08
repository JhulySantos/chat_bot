import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaResposta extends JFrame {

    private static final long serialVersionUID = 1L;
    private RespostaDAO respostaDAO;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoResposta;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoRemover;

    public TelaResposta(RespostaDAO respostaDAO) throws SQLException {
        this.respostaDAO = respostaDAO;
        inicializaComponentes();
        atualizarTabela();
    }

    private void inicializaComponentes() {
        // painel para a tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Resposta"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        painelTabela.add(scrollPane);

        // painel para o formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelDescricao = new JLabel("Resposta:");
        campoResposta = new JTextField();
        campoResposta.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoResposta.getPreferredSize().height));
        botaoAdicionar = new JButton("Adicionar");
        botaoEditar = new JButton("Editar");
        botaoRemover = new JButton("Remover");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
        painelFormulario.add(labelDescricao);
        painelFormulario.add(campoResposta);
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
                    adicionarResposta();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarResposta();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removerResposta();
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
                int id = Integer.parseInt((String) tabela.getValueAt(linhaSelecionada, 0));
                Resposta resposta = null;
                try {
                    resposta = respostaDAO.buscarPorId(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                campoResposta.setText(resposta.getRespostas());
            } else {
                botaoEditar.setEnabled(false);
                botaoRemover.setEnabled(false);
                campoResposta.setText("");
            }
        });

        // configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRUD de resposta");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<Resposta> respostas = respostaDAO.listarRespostas();
        for (Resposta resposta : respostas) {
            modeloTabela.addRow(new Object[]{resposta.getId(), resposta.getRespostas()});
        }
    }

    private void adicionarResposta() throws SQLException {
        String resposta = campoResposta.getText().trim();
        if (resposta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a resposta.");
            return;
        }
        Resposta resposta1 = new Resposta(resposta);
        respostaDAO.inserir(resposta1);
        atualizarTabela();
        campoResposta.setText("");
    }

    private void editarResposta() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = Integer.parseInt((String)tabela.getValueAt(linhaSelecionada, 0));
        String resposta = campoResposta.getText().trim();
        if (resposta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a resposta.");
            return;
        }
        Resposta resposta2 = respostaDAO.buscarPorId(id);
        resposta2.setRespostas(resposta);
        respostaDAO.atualizar(resposta2);
        atualizarTabela();
        campoResposta.setText("");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
    }

    private void removerResposta() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = Integer.parseInt((String) tabela.getValueAt(linhaSelecionada, 0));
        Resposta resposta = respostaDAO.buscarPorId(id);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a resposta " + resposta.getRespostas() + "?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            respostaDAO.deletar(id);
            atualizarTabela();
            campoResposta.setText("");
            botaoEditar.setEnabled(false);
            botaoRemover.setEnabled(false);
        }
    }
}
