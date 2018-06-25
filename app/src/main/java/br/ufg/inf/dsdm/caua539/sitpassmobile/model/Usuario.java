package br.ufg.inf.dsdm.caua539.sitpassmobile.model;

public class Usuario {

    private String nome;
    private String cpf;
    private int session;

    public String getNome() {
        return nome;
    }

    public int getSession() {
        return session;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
