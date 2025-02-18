package com.example.gerenciadordepizzaria;

public class MainApp {
    public static void main(String[] args) {
        GerenciadorPizzaria gerenciador = new GerenciadorPizzaria();

        Cliente cliente1 = new Cliente("João Silva", "123.456.789-00");
        Cliente cliente2 = new Cliente("Maria Santos", "987.654.321-00");
        
        gerenciador.adicionarCliente(cliente1);
        gerenciador.adicionarCliente(cliente2);

        Pizza pizzaSimples = new PizzaSimples("Mussarela", 32.90, "Média");
        Pizza pizzaEspecial = new PizzaEspecial("Portuguesa");

        Pedido pedido1 = new Pedido(1, cliente1, pizzaSimples);
        pedido1.definirPagamento("Cartão");
        
        Pedido pedido2 = new Pedido(2, cliente2, pizzaEspecial);
        pedido2.definirPagamento("Pix");

        // Adicionar pedidos
        gerenciador.adicionarPedido(pedido1);
        gerenciador.adicionarPedido(pedido2);

        System.out.println("=== Lista de Clientes ===");
        gerenciador.listarClientes();
        
        System.out.println("\n=== Lista de Pedidos ===");
        gerenciador.listarPedidos();

        System.out.println("\n=== Consulta de Pedido ===");
        System.out.println(gerenciador.consultarPedido(1));
    }
}
