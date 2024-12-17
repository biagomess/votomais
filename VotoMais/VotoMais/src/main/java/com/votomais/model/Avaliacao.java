package com.votomais.model;

public class Avaliacao {

    private int id;
    private int candidatoId;
    private int usuarioId;
    private String nomeUsuario;
    private int estrelas;
    private String comentario;

    // Construtor
    public Avaliacao(int id, int candidatoId, int usuarioId, String nomeUsuario, int estrelas, String comentario) {
        this.id = id;
        this.candidatoId = candidatoId;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
        this.estrelas = estrelas;
        this.comentario = comentario;
    }

    public Avaliacao() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCandidatoId() {
        return candidatoId;
    }

    public void setCandidatoId(int candidatoId) {
        this.candidatoId = candidatoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public int getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(int estrelas) {
        this.estrelas = estrelas;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
