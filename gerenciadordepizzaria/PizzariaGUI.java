package com.example.gerenciadordepizzaria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
    private JTable clientesTable;
    private DefaultTableModel pedidosTableModel;
    private DefaultTableModel clientesTableModel;

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
        cadastroPanel.setPreferredSize(new Dimension(600, 300));

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

        // Tabela de clientes
        String[] colunasClientes = {"Nome", "CPF"};
        clientesTableModel = new DefaultTableModel(colunasClientes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        clientesTable = new JTable(clientesTableModel);
        JScrollPane clientesScrollPane = new JScrollPane(clientesTable);
        clientesScrollPane.setBounds(50, 150, 500, 120);
        cadastroPanel.add(clientesScrollPane);

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

        // Novos botões de alteração
        JButton alterarPizzaButton = new JButton("Alterar Pizza");
        alterarPizzaButton.setBounds(150, 160, 120, 30);
        alterarPizzaButton.setEnabled(false);
        pedidoPanel.add(alterarPizzaButton);

        JButton alterarPagamentoButton = new JButton("Alterar Pagamento");
        alterarPagamentoButton.setBounds(280, 160, 150, 30);
        alterarPagamentoButton.setEnabled(false);
        pedidoPanel.add(alterarPagamentoButton);

        statusLabel = new JLabel("Status: Aguardando cadastro...");
        statusLabel.setBounds(50, 200, 400, 25);
        pedidoPanel.add(statusLabel);

        // Painel de tabela de pedidos
        JPanel tabelaPanel = new JPanel();
        tabelaPanel.setLayout(new BorderLayout());
        tabelaPanel.setBorder(BorderFactory.createTitledBorder("Pedidos Realizados"));
        
        String[] colunas = {"Nº Pedido", "Cliente", "Pizza", "Pagamento", "Valor (R$)", "Ações"};
        pedidosTableModel = new DefaultTableModel(colunas, 0);
        pedidosTable = new JTable(pedidosTableModel);
        
        // Configurar botão de excluir na última coluna
        pedidosTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        pedidosTable.getColumnModel().getColumn(5).setCellEditor(
            new ButtonEditor(new JCheckBox(), "Excluir", (row) -> {
                int option = JOptionPane.showConfirmDialog(null, 
                    "Deseja realmente excluir este pedido?", 
                    "Confirmar exclusão", 
                    JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    int numeroPedido = (int) pedidosTableModel.getValueAt(row, 0);
                    pedidos.removeIf(p -> p.getNumero() == numeroPedido);
                    pedidosTableModel.removeRow(row);
                }
                return true;
            }));

        JScrollPane scrollPane = new JScrollPane(pedidosTable);
        tabelaPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(cadastroPanel, BorderLayout.NORTH);
        add(pedidoPanel, BorderLayout.CENTER);
        add(tabelaPanel, BorderLayout.SOUTH);

        // Listeners
        clientesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && clientesTable.getSelectedRow() != -1) {
                int row = clientesTable.getSelectedRow();
                nomeField.setText((String) clientesTable.getValueAt(row, 0));
                cpfField.setText((String) clientesTable.getValueAt(row, 1));
            }
        });

        pedidosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && pedidosTable.getSelectedRow() != -1) {
                alterarPizzaButton.setEnabled(true);
                alterarPagamentoButton.setEnabled(true);
            } else {
                alterarPizzaButton.setEnabled(false);
                alterarPagamentoButton.setEnabled(false);
            }
        });

        cadastrarButton.addActionListener(e -> {
            if (!nomeField.getText().isEmpty() && !cpfField.getText().isEmpty()) {
                Cliente cliente = new Cliente(nomeField.getText(), cpfField.getText());
                clientes.add(cliente);
                
                Object[] rowData = {cliente.getNome(), cliente.getCpf()};
                clientesTableModel.addRow(rowData);
                
                JOptionPane.showMessageDialog(null, "Cadastro realizado!");
                pizzaBox.setEnabled(true);
                pagamentoBox.setEnabled(true);
                pedidoButton.setEnabled(true);
                statusLabel.setText("Status: Escolha sua pizza e finalize o pedido.");
                
                nomeField.setText("");
                cpfField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
            }
        });

        alterarButton.addActionListener(e -> {
            int selectedRow = clientesTable.getSelectedRow();
            if (selectedRow != -1) {
                String cpfAntigo = (String) clientesTable.getValueAt(selectedRow, 1);
                for (Cliente cliente : clientes) {
                    if (cliente.getCpf().equals(cpfAntigo)) {
                        cliente.setNome(nomeField.getText());
                        cliente.setCpf(cpfField.getText());
                        
                        clientesTableModel.setValueAt(nomeField.getText(), selectedRow, 0);
                        clientesTableModel.setValueAt(cpfField.getText(), selectedRow, 1);
                        
                        // Atualizar a tabela de pedidos
                        for (int i = 0; i < pedidosTableModel.getRowCount(); i++) {
                            if (pedidosTableModel.getValueAt(i, 1).equals(cliente.getNome())) {
                                pedidosTableModel.setValueAt(nomeField.getText(), i, 1);
                            }
                        }
                        
                        JOptionPane.showMessageDialog(null, "Cadastro alterado com sucesso!");
                        
                        nomeField.setText("");
                        cpfField.setText("");
                        clientesTable.clearSelection();
                        return;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um cliente na tabela para alterar.");
            }
        });

        excluirButton.addActionListener(e -> {
            int selectedRow = clientesTable.getSelectedRow();
            if (selectedRow != -1) {
                String cpf = (String) clientesTable.getValueAt(selectedRow, 1);
                clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
                clientesTableModel.removeRow(selectedRow);
                
                JOptionPane.showMessageDialog(null, "Cadastro excluído!");
                nomeField.setText("");
                cpfField.setText("");
                pizzaBox.setEnabled(false);
                pagamentoBox.setEnabled(false);
                pedidoButton.setEnabled(false);
                clientesTable.clearSelection();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um cliente na tabela para excluir.");
            }
        });

        pedidoButton.addActionListener(e -> {
            int selectedRow = clientesTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Selecione um cliente na tabela para fazer o pedido.");
                return;
            }

            String pizzaSelecionada = (String) pizzaBox.getSelectedItem();
            String pagamentoSelecionado = (String) pagamentoBox.getSelectedItem();
            
            String cpfSelecionado = (String) clientesTable.getValueAt(selectedRow, 1);
            Cliente clienteSelecionado = null;
            for (Cliente cliente : clientes) {
                if (cliente.getCpf().equals(cpfSelecionado)) {
                    clienteSelecionado = cliente;
                    break;
                }
            }

            if (clienteSelecionado == null) {
                JOptionPane.showMessageDialog(null, "Erro ao encontrar cliente.");
                return;
            }

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
                String.format("%.2f", valor),
                "Excluir"
            };
            pedidosTableModel.addRow(row);

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

        alterarPizzaButton.addActionListener(e -> {
            int row = pedidosTable.getSelectedRow();
            if (row != -1) {
                int numeroPedido = (int) pedidosTableModel.getValueAt(row, 0);
                String novaPizza = (String) pizzaBox.getSelectedItem();
                double novoValor = extrairValorPizza(novaPizza);
                
                for (Pedido pedido : pedidos) {
                    if (pedido.getNumero() == numeroPedido) {
                        Pizza novaPizzaObj;
                        if (novaPizza.contains("Especial")) {
                            novaPizzaObj = new PizzaEspecial(novaPizza.split("\\(")[0].trim());
                        } else {
                            novaPizzaObj = new PizzaSimples(novaPizza.split("\\(")[0].trim(), novoValor, "Média");
                        }
                        pedido.setPizza(novaPizzaObj);
                        break;
                    }
                }
                
                pedidosTableModel.setValueAt(novaPizza.split("\\(")[0].trim(), row, 2);
                pedidosTableModel.setValueAt(String.format("%.2f", novoValor), row, 4);
                
                JOptionPane.showMessageDialog(null, "Pizza alterada com sucesso!");
                pedidosTable.clearSelection();
            }
        });

        alterarPagamentoButton.addActionListener(e -> {
            int row = pedidosTable.getSelectedRow();
            if (row != -1) {
                int numeroPedido = (int) pedidosTableModel.getValueAt(row, 0);
                String novoPagamento = (String) pagamentoBox.getSelectedItem();
                
                for (Pedido pedido : pedidos) {
                    if (pedido.getNumero() == numeroPedido) {
                        pedido.definirPagamento(novoPagamento);
                        break;
                    }
                }
                
                pedidosTableModel.setValueAt(novoPagamento, row, 3);
                
                JOptionPane.showMessageDialog(null, "Forma de pagamento alterada com sucesso!");
                pedidosTable.clearSelection();
            }
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

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Excluir");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private ButtonAction action;
        private int row;

        public ButtonEditor(JCheckBox checkBox, String label, ButtonAction action) {
            super(checkBox);
            this.label = label;
            this.action = action;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.row = row;
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                action.execute(row);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    interface ButtonAction {
        boolean execute(int row);
    }

    public static void main(String[] args) {
        new PizzariaGUI();
    }
}
