package com.votomais.model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private NivelAcesso nivelAcesso;

    // Construtor padrão
    public Usuario() {
    }

    // Construtor
    public Usuario(int id, String nome, String email, String senha, NivelAcesso nivelAcesso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }

    public enum NivelAcesso {
        ADMIN(1),
        USUARIO(2);

        private final int nivel;

        NivelAcesso(int nivel) {
            this.nivel = nivel;
        }

        public int getNivel() {
            return nivel;
        }
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public NivelAcesso getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
