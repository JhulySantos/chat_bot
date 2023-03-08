import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaPergunta extends JFrame {

    private static final long serialVersionUID = 1L;
    private PerguntaDAO perguntaDAO;
    private DefaultTableModel modeloTabela;
    private JTable tabela;
    private JTextField campoPergunta;
    private JTextField campoIdResposta;
    private JTextField campoIdLinguagem;
    private JButton botaoAdicionar;
    private JButton botaoEditar;
    private JButton botaoRemover;

    public TelaPergunta(PerguntaDAO perguntaDAO) throws SQLException {
        this.perguntaDAO = perguntaDAO;
        inicializaComponentes();
        atualizarTabela();
    }

    private void inicializaComponentes() {
        // painel para a tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Pergunta", "Id_Linguagem", "Id_Resposta"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        tabela.setFillsViewportHeight(true);
        painelTabela.add(scrollPane);

        // painel para o formulário
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new BoxLayout(painelFormulario, BoxLayout.Y_AXIS));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelPergunta = new JLabel("Pergunta:");
        JLabel labelIdLinguagem = new JLabel("Id_Linguagem:");
        JLabel labelIdResposta = new JLabel("Id_Resposta:");
        campoPergunta = new JTextField();
        campoPergunta.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoPergunta.getPreferredSize().height));
        campoIdResposta = new JTextField();
        campoIdResposta.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoIdResposta.getPreferredSize().height));
        campoIdLinguagem= new JTextField();
        campoIdLinguagem.setMaximumSize(new Dimension(Integer.MAX_VALUE, campoIdLinguagem.getPreferredSize().height));
        botaoAdicionar = new JButton("Adicionar");
        botaoEditar = new JButton("Editar");
        botaoRemover = new JButton("Remover");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
        painelFormulario.add(labelPergunta);
        painelFormulario.add(campoPergunta);
        painelFormulario.add(labelIdLinguagem);
        painelFormulario.add(campoIdResposta);
        painelFormulario.add(labelIdResposta);
        painelFormulario.add(campoIdLinguagem);
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
                    adicionarPergunta();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarPergunta();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    removerPergunta();
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
                Pergunta perguntas = null;
                try {
                    perguntas = perguntaDAO.buscarPorId(id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                campoPergunta.setText(perguntas.getPergunta());
                campoIdResposta.setText(String.valueOf(perguntas.getIdResposta()));
                campoIdLinguagem.setText(String.valueOf(perguntas.getIdLinguagem()));
            } else {
                botaoEditar.setEnabled(false);
                botaoRemover.setEnabled(false);
                campoPergunta.setText("");
                campoIdResposta.setText("");
                campoIdLinguagem.setText("");
            }
        });

        // configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CRUD de Categoria");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void atualizarTabela() throws SQLException {
        modeloTabela.setRowCount(0);
        List<Pergunta> perguntas = perguntaDAO.listarPerguntas();
        for (Pergunta pergunta : perguntas) {
            modeloTabela.addRow(new Object[]{pergunta.getId(), pergunta.getPergunta(), pergunta.getIdLinguagem(), pergunta.getIdResposta()});
        }
    }

    private void adicionarPergunta() throws SQLException {
        String pergunta = campoPergunta.getText().trim();
        String id_resposta = campoIdResposta.getText().trim();
        String id_linguagem = campoIdLinguagem.getText().trim();
        if (pergunta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição.");
            return;
        }

        if (id_resposta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição.");
            return;
        }

        if (id_linguagem.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição.");
            return;
        }
        Pergunta perguntas = new Pergunta(pergunta, Integer.parseInt(String.valueOf(id_linguagem)), Integer.parseInt(String.valueOf(id_resposta)));
        perguntaDAO.inserir(perguntas);
        atualizarTabela();
        campoPergunta.setText("");
        campoIdLinguagem.setText("");
        campoIdResposta.setText("");
    }

    private void editarPergunta() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        String descricao = campoPergunta.getText().trim();
        if (descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha a descrição.");
            return;
        }
        Pergunta perguntas = perguntaDAO.buscarPorId(id);
        perguntas.setPergunta(descricao);
        perguntaDAO.atualizar(perguntas);
        atualizarTabela();
        campoPergunta.setText("");
        botaoEditar.setEnabled(false);
        botaoRemover.setEnabled(false);
    }

    private void removerPergunta() throws SQLException {
        int linhaSelecionada = tabela.getSelectedRow();
        int id = (int) tabela.getValueAt(linhaSelecionada, 0);
        Pergunta pergunta = perguntaDAO.buscarPorId(id);
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja remover a pergunta " + pergunta.getPergunta() + "?");
        if (confirmacao == JOptionPane.YES_OPTION) {
            perguntaDAO.deletar(id);
            atualizarTabela();
            campoPergunta.setText("");
            campoIdLinguagem.setText("");
            campoIdResposta.setText("");
            botaoEditar.setEnabled(false);
            botaoRemover.setEnabled(false);
        }
    }
}
