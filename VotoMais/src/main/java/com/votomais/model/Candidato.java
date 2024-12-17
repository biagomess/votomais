package com.votomais.model;

import java.util.List;

public class Candidato {

    // Campos relacionados ao Candidato
    private int idCandidato;               // ID do Candidato
    private String nomeCandidato;          // Nome do Candidato
    private Integer idVice;                // ID do Vice (associado ao Candidato)
    private int idadeCandidato;            // Idade do Candidato
    private String cargoPoliticoCandidato; // Cargo político do Candidato
    private String partidoCandidato;       // Partido político do Candidato
    private String historicoCandidato;     // Histórico do Candidato
    private String fotoCandidato;          // Foto do Candidato
    private List<Avaliacao> avaliacoesCandidato; // Lista de avaliações do Candidato
    
    private String nomeVice;               // Nome do Vice
    private String cargoPoliticoVice;      // Cargo político do Vice
    private String partidoVice;            // Partido político do Vice
    private String historicoVice;          // Histórico do Vice
    private int idadeVice;                 // Idade do Vice
    private double mediaAvaliacoes;        // Média das avaliações
    private int nmrRegistro;               // Número de Registro do Candidato
    private int nmrRegistroVice;           // Número de Registro do Vice (adicionado)

    // Construtor vazio
    public Candidato() {
    }

    // Construtor completo com idVice, nomeVice, e nmrRegistroVice
    public Candidato(
            int idCandidato, String nomeCandidato, Integer idVice, int idadeCandidato,
            String cargoPoliticoCandidato, String partidoCandidato,
            String historicoCandidato, String fotoCandidato,
            List<Avaliacao> avaliacoesCandidato, String nomeVice, String cargoPoliticoVice,
            String partidoVice, String historicoVice, int idadeVice, int nmrRegistro, int nmrRegistroVice
    ) {
        this.idCandidato = idCandidato;
        this.nomeCandidato = nomeCandidato;
        this.idVice = idVice;
        this.idadeCandidato = idadeCandidato;
        this.cargoPoliticoCandidato = cargoPoliticoCandidato;
        this.partidoCandidato = partidoCandidato;
        this.historicoCandidato = historicoCandidato;
        this.fotoCandidato = fotoCandidato;
        this.avaliacoesCandidato = avaliacoesCandidato;
        this.nomeVice = nomeVice;
        this.cargoPoliticoVice = cargoPoliticoVice;
        this.partidoVice = partidoVice;
        this.historicoVice = historicoVice;
        this.idadeVice = idadeVice;
        this.nmrRegistro = nmrRegistro;
        this.nmrRegistroVice = nmrRegistroVice; // Inicialização do campo nmrRegistroVice
    }

    // Getters e Setters para os campos do Candidato
    public int getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(int idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getNomeCandidato() {
        return nomeCandidato;
    }

    public void setNomeCandidato(String nomeCandidato) {
        this.nomeCandidato = nomeCandidato;
    }

    public Integer getIdVice() {
        return idVice;
    }

    public void setIdVice(Integer idVice) {
        this.idVice = idVice;
    }

    public int getIdadeCandidato() {
        return idadeCandidato;
    }

    public void setIdadeCandidato(int idadeCandidato) {
        this.idadeCandidato = idadeCandidato;
    }

    public String getCargoPoliticoCandidato() {
        return cargoPoliticoCandidato;
    }

    public void setCargoPoliticoCandidato(String cargoPoliticoCandidato) {
        this.cargoPoliticoCandidato = cargoPoliticoCandidato;
    }

    public String getPartidoCandidato() {
        return partidoCandidato;
    }

    public void setPartidoCandidato(String partidoCandidato) {
        this.partidoCandidato = partidoCandidato;
    }

    public String getHistoricoCandidato() {
        return historicoCandidato;
    }

    public void setHistoricoCandidato(String historicoCandidato) {
        this.historicoCandidato = historicoCandidato;
    }

    public String getFotoCandidato() {
        return fotoCandidato;
    }

    public void setFotoCandidato(String fotoCandidato) {
        this.fotoCandidato = fotoCandidato;
    }

    public List<Avaliacao> getAvaliacoesCandidato() {
        return avaliacoesCandidato;
    }

    public void setAvaliacoesCandidato(List<Avaliacao> avaliacoesCandidato) {
        this.avaliacoesCandidato = avaliacoesCandidato;
    }

    public double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public String getNomeVice() {
        return nomeVice;
    }

    public void setNomeVice(String nomeVice) {
        this.nomeVice = nomeVice;
    }

    public String getCargoPoliticoVice() {
        return cargoPoliticoVice;
    }

    public void setCargoPoliticoVice(String cargoPoliticoVice) {
        this.cargoPoliticoVice = cargoPoliticoVice;
    }

    public String getPartidoVice() {
        return partidoVice;
    }

    public void setPartidoVice(String partidoVice) {
        this.partidoVice = partidoVice;
    }

    public String getHistoricoVice() {
        return historicoVice;
    }

    public void setHistoricoVice(String historicoVice) {
        this.historicoVice = historicoVice;
    }

    public int getIdadeVice() {
        return idadeVice;
    }

    public void setIdadeVice(int idadeVice) {
        this.idadeVice = idadeVice;
    }

    public int getNmrRegistro() {
        return nmrRegistro;
    }

    public void setNmrRegistro(int nmrRegistro) {
        this.nmrRegistro = nmrRegistro;
    }

    public int getNmrRegistroVice() {
        return nmrRegistroVice; // Getter para o campo nmrRegistroVice
    }

    public void setNmrRegistroVice(int nmrRegistroVice) {
        this.nmrRegistroVice = nmrRegistroVice; // Setter para o campo nmrRegistroVice
    }

    // Método para calcular a média das avaliações
    public void calcularMediaAvaliacoes() {
        if (avaliacoesCandidato != null && !avaliacoesCandidato.isEmpty()) {
            double soma = 0;
            for (Avaliacao avaliacao : avaliacoesCandidato) {
                soma += avaliacao.getNota(); // Considerando que Avaliacao tem um método getNota()
            }
            this.mediaAvaliacoes = soma / avaliacoesCandidato.size();
        } else {
            this.mediaAvaliacoes = 0;
        }
    }
}
