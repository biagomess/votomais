package com.votomais.model;

import java.util.List;

public class Vice {

    // Campos relacionados ao Vice
    private int idVice;                   // ID do Vice
    private String nomeVice;              // Nome do Vice
    private int idadeVice;                // Idade do Vice
    private String cargoPoliticoVice;     // Cargo político do Vice
    private String partidoVice;           // Partido político do Vice
    private String historicoVice;         // Histórico do Vice
    private String fotoVice;              // Foto do Vice
    private List<Avaliacao> avaliacoesVice; // Lista de avaliações do Vice

    // Novo campo para armazenar a média das avaliações
    private double mediaAvaliacoesVice;    // Média das avaliações
    private int nmrRegistroVice;
    
    // Construtor vazio
    public Vice() {
    }

    // Construtor completo
    public Vice(
            int idVice, String nomeVice, int idadeVice, String cargoPoliticoVice,
            String partidoVice, String historicoVice, String fotoVice, int idCandidato,
            List<Avaliacao> avaliacoesVice, int nmrRegistroVice
    ) {
        this.idVice = idVice;
        this.nomeVice = nomeVice;
        this.idadeVice = idadeVice;
        this.cargoPoliticoVice = cargoPoliticoVice;
        this.partidoVice = partidoVice;
        this.historicoVice = historicoVice;
        this.fotoVice = fotoVice;
        this.avaliacoesVice = avaliacoesVice;
        this.nmrRegistroVice = nmrRegistroVice;
    }

    // Getters e Setters para os campos do Vice
    public int getIdVice() {
        return idVice;
    }

    public void setIdVice(int idVice) {
        this.idVice = idVice;
    }

    public String getNomeVice() {
        return nomeVice;
    }

    public void setNomeVice(String nomeVice) {
        this.nomeVice = nomeVice;
    }

    public int getIdadeVice() {
        return idadeVice;
    }

    public void setIdadeVice(int idadeVice) {
        this.idadeVice = idadeVice;
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

    public String getFotoVice() {
        return fotoVice;
    }

    public void setFotoVice(String fotoVice) {
        this.fotoVice = fotoVice;
    }

    public List<Avaliacao> getAvaliacoesVice() {
        return avaliacoesVice;
    }

    public void setAvaliacoesVice(List<Avaliacao> avaliacoesVice) {
        this.avaliacoesVice = avaliacoesVice;
    }

    // Getter e Setter para a média das avaliações do Vice
    public double getMediaAvaliacoesVice() {
        return mediaAvaliacoesVice;
    }

    public void setMediaAvaliacoesVice(double mediaAvaliacoesVice) {
        this.mediaAvaliacoesVice = mediaAvaliacoesVice;
    }
    
    public int getNmrRegistroVice() {
        return nmrRegistroVice;
    }

    public void setNmrRegistroVice(int nmrRegistro) {
        this.nmrRegistroVice = nmrRegistro;
    } 

    // Método para calcular a média das avaliações do Vice
    public void calcularMediaAvaliacoesVice() {
        if (avaliacoesVice != null && !avaliacoesVice.isEmpty()) {
            double soma = 0;
            for (Avaliacao avaliacao : avaliacoesVice) {
                soma += avaliacao.getNota(); // Considerando que Avaliacao tem um método getNota()
            }
            this.mediaAvaliacoesVice = soma / avaliacoesVice.size();
        } else {
            this.mediaAvaliacoesVice = 0;
        }
    }

}