package com.example.gerenciadordepizzaria;

public class PizzaSimples implements Pizza {
    private String nome;
    private double preco;
    private String tamanho;

    public PizzaSimples(String nome, double preco, String tamanho) {
        this.nome = nome;
        this.preco = preco;
        this.tamanho = tamanho;
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
        return "Pizza Simples de " + nome + " (" + tamanho + ") - R$" + String.format("%.2f", preco);
    }
}