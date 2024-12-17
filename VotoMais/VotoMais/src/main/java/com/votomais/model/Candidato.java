package com.votomais.model;

import java.util.List;

public class Candidato {

    private int id;
    private String nome;
    private int idade;
    private String cargoPolitico;
    private String partido;
    private String historico;
    private String foto;
    private List<Avaliacao> avaliacoes;
    private Integer vice_id;

    // Construtor
    public Candidato(int id, String nome, int idade, String cargoPolitico, String partido, String historico, String foto, int vice_id) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.cargoPolitico = cargoPolitico;
        this.partido = partido;
        this.historico = historico;
        this.foto = foto;
        this.vice_id = vice_id;
    }

    public Candidato() {
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getCargoPolitico() {
        return cargoPolitico;
    }

    public void setCargoPolitico(String cargoPolitico) {
        this.cargoPolitico = cargoPolitico;
    }

    public String getPartido() {
        return partido;
    }

    public void setPartido(String partido) {
        this.partido = partido;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Integer getVice_id() {
        return vice_id;
    }

    public void setVice_id(Integer vice_id) {
        this.vice_id = vice_id;
    }
}
