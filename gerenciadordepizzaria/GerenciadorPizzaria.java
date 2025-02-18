package com.example.gerenciadordepizzaria;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorPizzaria {
    private List<Cliente> clientes;
    private List<Pedido> pedidos;

    public GerenciadorPizzaria() {
        clientes = new ArrayList<>();
        pedidos = new ArrayList<>();
    }

    //  Cadastrar novos
    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    //  Buscar um cliente pelo CPF
    public Cliente buscarCliente(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        return null;
    }

    //  Buscar um pedido pelo ID
    public Pedido buscarPedido(int id) {
        for (Pedido p : pedidos) {
            if (p.getNumero() == id) {
                return p;
            }
        }
        return null;
    }

    //  Remover registros por CPF ou ID
    public boolean removerCliente(String cpf) {
        return clientes.removeIf(c -> c.getCpf().equals(cpf));
    }

    public boolean removerPedido(int id) {
        return pedidos.removeIf(p -> p.getNumero() == id);
    }

    //  Alterar status do pedido
    public boolean alterarStatusPedido(int id, String novoStatus) {
        Pedido pedido = buscarPedido(id);
        if (pedido != null) {
            pedido.alterarStatus(novoStatus);
            return true;
        }
        return false;
    }

    //  Definir
    public boolean definirPagamentoPedido(int id, String metodoPagamento) {
        Pedido pedido = buscarPedido(id);
        if (pedido != null) {
            pedido.definirPagamento(metodoPagamento);
            return true;
        }
        return false;
    }

    //  Listagem de registros
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            clientes.forEach(c -> System.out.println(c.getInformacao()));
        }
    }
    public void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
        } else {
            pedidos.forEach(p -> System.out.println(p.getDetalhes()));
        }
    }

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void alterarCliente(String cpf, String novoNome) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                c.setNome(novoNome);
                break;
            }
        }
    }

    public void excluirCliente(String cpf) {
        clientes.removeIf(c -> c.getCpf().equals(cpf));
    }

    public void fazerPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public String consultarPedido(int numeroPedido) {
        for (Pedido p : pedidos) {
            if (p.getNumero() == numeroPedido) {
                return p.getDetalhes();
            }
        }
        return "Pedido não encontrado";
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}