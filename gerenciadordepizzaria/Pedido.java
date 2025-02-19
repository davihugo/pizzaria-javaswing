package com.example.gerenciadordepizzaria;

public class Pedido {
    private int numero;
    private Cliente cliente;
    private Pizza pizza;
    private String status;
    private String pagamento;

    public Pedido(int numero, Cliente cliente, Pizza pizza) {
        this.numero = numero;
        this.cliente = cliente;
        this.pizza = pizza;
        this.status = "Em preparo";
        this.pagamento = "Não definido";
    }

    public Pedido(String pizzaSelecionada, String pagamentoSelecionado) {
    }

    public Pedido(Pizza pizza, String pagamentoSelecionado) {
    }

    public int getNumero() {
        return numero;
    }

    public String getDetalhes() {
        return "Pedido #" + numero + 
               "\nCliente: " + cliente.getNome() +
               "\nPizza: " + pizza.getDescricao() +
               "\nPagamento: " + pagamento;
    }

    public void alterarStatus(String novoStatus) {
        this.status = novoStatus;
    }

    public void definirPagamento(String metodoPagamento) {
        this.pagamento = metodoPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public String getFormaPagamento() {
        return pagamento;
    }

    public void setPizza(Pizza novaPizzaObj) {
    }
}