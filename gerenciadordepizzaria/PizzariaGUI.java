package com.example.gerenciadordepizzaria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PizzariaGUI extends JFrame {
    private JTextField nomeField;
    private JTextField cpfField;
    private JComboBox<String> pizzaBox;
    private JComboBox<String> pagamentoBox;
    private JLabel statusLabel;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;
    private JTable pedidosTable;
    private DefaultTableModel tableModel;

    public PizzariaGUI() {
        clientes = new ArrayList<>();
        pedidos = new ArrayList<>();

        setTitle("Gerenciador de Pizzaria");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de Cadastro
        JPanel cadastroPanel = new JPanel();
        cadastroPanel.setLayout(null);
        cadastroPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Cliente"));
        cadastroPanel.setPreferredSize(new Dimension(600, 200));

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(50, 30, 100, 25);
        cadastroPanel.add(nomeLabel);

        nomeField = new JTextField();
        nomeField.setBounds(150, 30, 200, 25);
        cadastroPanel.add(nomeField);

        JLabel cpfLabel = new JLabel("CPF:");
        cpfLabel.setBounds(50, 70, 100, 25);
        cadastroPanel.add(cpfLabel);

        cpfField = new JTextField();
        cpfField.setBounds(150, 70, 200, 25);
        cadastroPanel.add(cpfField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(150, 110, 100, 30);
        cadastroPanel.add(cadastrarButton);

        JButton alterarButton = new JButton("Alterar");
        alterarButton.setBounds(260, 110, 100, 30);
        cadastroPanel.add(alterarButton);

        JButton excluirButton = new JButton("Excluir");
        excluirButton.setBounds(370, 110, 100, 30);
        cadastroPanel.add(excluirButton);

        // Painel de Pedidos
        JPanel pedidoPanel = new JPanel();
        pedidoPanel.setLayout(null);
        pedidoPanel.setBorder(BorderFactory.createTitledBorder("Realizar Pedido"));
        pedidoPanel.setPreferredSize(new Dimension(600, 300));

        JLabel pizzaLabel = new JLabel("Escolha sua pizza:");
        pizzaLabel.setBounds(50, 30, 200, 25);
        pedidoPanel.add(pizzaLabel);

        String[] pizzas = {
            "Simples - Calabresa (R$ 35,90)", 
            "Simples - Mussarela (R$ 32,90)", 
            "Especial - Portuguesa (R$ 45,90)", 
            "Especial - Frango com Catupiry (R$ 47,90)"
        };
        pizzaBox = new JComboBox<>(pizzas);
        pizzaBox.setBounds(200, 30, 200, 25);
        pizzaBox.setEnabled(false);
        pedidoPanel.add(pizzaBox);

        JLabel pagamentoLabel = new JLabel("Forma de pagamento:");
        pagamentoLabel.setBounds(50, 70, 200, 25);
        pedidoPanel.add(pagamentoLabel);

        String[] pagamentos = {"Pix", "Cartão", "Dinheiro"};
        pagamentoBox = new JComboBox<>(pagamentos);
        pagamentoBox.setBounds(200, 70, 200, 25);
        pagamentoBox.setEnabled(false);
        pedidoPanel.add(pagamentoBox);

        JButton pedidoButton = new JButton("Fazer Pedido");
        pedidoButton.setBounds(150, 120, 200, 30);
        pedidoButton.setEnabled(false);
        pedidoPanel.add(pedidoButton);

        statusLabel = new JLabel("Status: Aguardando cadastro...");
        statusLabel.setBounds(50, 170, 400, 25);
        pedidoPanel.add(statusLabel);

        JPanel tabelaPanel = new JPanel();
        tabelaPanel.setLayout(new BorderLayout());
        tabelaPanel.setBorder(BorderFactory.createTitledBorder("Pedidos Realizados"));
        
        String[] colunas = {"Nº Pedido", "Cliente", "Pizza", "Pagamento", "Valor (R$)"};
        tableModel = new DefaultTableModel(colunas, 0);
        pedidosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(pedidosTable);
        tabelaPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(cadastroPanel, BorderLayout.NORTH);
        add(pedidoPanel, BorderLayout.CENTER);
        add(tabelaPanel, BorderLayout.SOUTH);

        cadastrarButton.addActionListener(e -> {
            if (!nomeField.getText().isEmpty() && !cpfField.getText().isEmpty()) {
                Cliente cliente = new Cliente(nomeField.getText(), cpfField.getText());
                clientes.add(cliente);
                JOptionPane.showMessageDialog(null, "Cadastro realizado!");
                pizzaBox.setEnabled(true);
                pagamentoBox.setEnabled(true);
                pedidoButton.setEnabled(true);
                statusLabel.setText("Status: Escolha sua pizza e finalize o pedido.");
            } else {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
            }
        });

        alterarButton.addActionListener(e -> {
            for (Cliente cliente : clientes) {
                if (cliente.getCpf().equals(cpfField.getText())) {
                    cliente.setNome(nomeField.getText());
                    JOptionPane.showMessageDialog(null, "Cadastro alterado com sucesso!");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "CPF não encontrado!");
        });

        excluirButton.addActionListener(e -> {
            clientes.removeIf(cliente -> cliente.getCpf().equals(cpfField.getText()));
            JOptionPane.showMessageDialog(null, "Cadastro excluído!");
            nomeField.setText("");
            cpfField.setText("");
            pizzaBox.setEnabled(false);
            pagamentoBox.setEnabled(false);
            pedidoButton.setEnabled(false);
        });

        pedidoButton.addActionListener(e -> {
            String pizzaSelecionada = (String) pizzaBox.getSelectedItem();
            String pagamentoSelecionado = (String) pagamentoBox.getSelectedItem();
            Cliente clienteSelecionado = clientes.get(clientes.size() - 1);

            double valor = extrairValorPizza(pizzaSelecionada);
            
            Pizza pizza;
            if (pizzaSelecionada.contains("Especial")) {
                pizza = new PizzaEspecial(pizzaSelecionada.split("\\(")[0].trim());
            } else {
                pizza = new PizzaSimples(pizzaSelecionada.split("\\(")[0].trim(), valor, "Média");
            }

            Pedido pedido = new Pedido(pedidos.size() + 1, clienteSelecionado, pizza);
            pedido.definirPagamento(pagamentoSelecionado);
            pedidos.add(pedido);

            Object[] row = {
                pedido.getNumero(),
                clienteSelecionado.getNome(),
                pizzaSelecionada.split("\\(")[0].trim(),
                pagamentoSelecionado,
                String.format("%.2f", valor)
            };
            tableModel.addRow(row);

            new Thread(() -> {
                try {
                    statusLabel.setText("Status: Preparando...");
                    Thread.sleep(2000);
                    statusLabel.setText("Status: Pronto!");
                    Thread.sleep(2000);
                    statusLabel.setText("Status: Entregue!");
                    JOptionPane.showMessageDialog(null, "Pedido concluído!\n" + pedido.getDetalhes());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        });

        setVisible(true);
    }

    private double extrairValorPizza(String pizzaTexto) {
        try {
            String valorStr = pizzaTexto.split("R\\$ ")[1].replace(")", "").replace(",", ".");
            return Double.parseDouble(valorStr);
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static void main(String[] args) {
        new PizzariaGUI();
    }
}
