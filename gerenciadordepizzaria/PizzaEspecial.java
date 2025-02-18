package com.example.gerenciadordepizzaria;

public class PizzaEspecial implements Pizza {
    private String nome;
    private double preco;
    private String tamanho;

    public PizzaEspecial(String nome) {
        this.nome = nome;
        this.tamanho = "Média";
        // Preço base para pizzas especiais
        this.preco = 45.90;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getPreco() {
        return preco;
    }

    @Override
    public String getTamanho() {
        return tamanho;
    }

    @Override
    public String getDescricao() {
        return "Pizza Especial de " + nome + " (" + tamanho + ") - R$" + String.format("%.2f", preco);
    }
}