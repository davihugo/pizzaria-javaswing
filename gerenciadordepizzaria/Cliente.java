package com.example.gerenciadordepizzaria;

public class Cliente extends Pessoa {
    private String telefone;

    public Cliente(String nome, String cpf) {
        super(nome, cpf);
        this.telefone = telefone;
    }

    @Override
    public String getInformacao() {
        return null;
    }

    @Override
    public String getInformacoes() {
        return "Cliente: " + nome + " | CPF: " + cpf + " | Telefone: " + telefone;
    }

    public void setNome(String text) {
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}