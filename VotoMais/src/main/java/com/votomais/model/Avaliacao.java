package com.votomais.model;

// Classe Avaliacao
public class Avaliacao {

    // Campos relacionados à Avaliação
    private int id;                  // ID da avaliação
    private int candidatoId;         // ID do candidato avaliado
    private int usuarioId;           // ID do usuário que realizou a avaliação
    private String nomeUsuario;      // Nome do usuário que realizou a avaliação
    private int estrelas;            // Quantidade de estrelas atribuídas (1 a 5)
    private String comentario;       // Comentário sobre o candidato

    // Construtor vazio
    public Avaliacao() {
    }

    // Construtor completo
    public Avaliacao(int id, int candidatoId, int usuarioId, String nomeUsuario, int estrelas, String comentario) {
        this.id = id;
        this.candidatoId = candidatoId;
        this.usuarioId = usuarioId;
        this.nomeUsuario = nomeUsuario;
        this.estrelas = estrelas;
        this.comentario = comentario;
    }

    // Getters e Setters para os campos da Avaliação
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

    // Método getNota() que retorna a nota (estrela) da avaliação
    public double getNota() {
        return estrelas;  // Retorna as estrelas como a nota
    }

    // Sobrescrita do método equals para comparação de avaliações
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Se os objetos são iguais, retorna true
        if (o == null || getClass() != o.getClass()) return false; // Verifica se o objeto é do tipo correto
        Avaliacao avaliacao = (Avaliacao) o;
        return candidatoId == avaliacao.candidatoId && usuarioId == avaliacao.usuarioId; // Compara pelo candidatoId e usuarioId
    }

    // Sobrescrita do método hashCode
    @Override
    public int hashCode() {
        return 31 * candidatoId + usuarioId; // Gera hashCode com base em candidatoId e usuarioId
    }
}
