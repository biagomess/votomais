package com.votomais.dao;

import com.votomais.model.Conexao;
import com.votomais.model.Avaliacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {

    // Método para adicionar uma avaliação
    public void cadastrar(Avaliacao avaliacao) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO avaliacao (candidato_id, usuario_id, nome_usuario, estrelas, comentario) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, avaliacao.getCandidatoId());
            stmt.setInt(2, avaliacao.getUsuarioId());

            // Buscar o nome do usuário
            String nomeSql = "SELECT nome FROM usuarios WHERE id = ?";
            try (PreparedStatement nomeStmt = connection.prepareStatement(nomeSql)) {
                nomeStmt.setInt(1, avaliacao.getUsuarioId());
                ResultSet rs = nomeStmt.executeQuery();
                stmt.setString(3, rs.next() ? rs.getString("nome") : null);
            }

            stmt.setInt(4, avaliacao.getEstrelas());
            stmt.setString(5, avaliacao.getComentario());
            stmt.executeUpdate();
        }
    }

    // Método para atualizar uma avaliação
    public void atualizar(Avaliacao avaliacao) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE avaliacao SET estrelas = ?, comentario = ? WHERE id = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, avaliacao.getEstrelas());
            stmt.setString(2, avaliacao.getComentario());
            stmt.setInt(3, avaliacao.getId());
            stmt.executeUpdate();
        }
    }

    // Método para deletar uma avaliação
    public void deletar(int id) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM avaliacao WHERE id = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Método para obter avaliações por candidato
    public List<Avaliacao> obterAvaliacoesPorCandidato(int candidatoId) throws ClassNotFoundException, SQLException {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT a.*, u.nome AS nome_usuario FROM avaliacao a JOIN usuarios u ON a.usuario_id = u.id WHERE a.candidato_id = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, candidatoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setCandidatoId(rs.getInt("candidato_id"));
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                avaliacao.setEstrelas(rs.getInt("estrelas"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacao.setNomeUsuario(rs.getString("nome_usuario")); // Preenche o nome do usuário
                avaliacoes.add(avaliacao);
            }
        }
        return avaliacoes;
    }

    // Método para obter todas as avaliações
    public List<Avaliacao> obterTodasAvaliacoes() throws ClassNotFoundException, SQLException {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT a.*, u.nome AS nome_usuario FROM avaliacao a JOIN usuarios u ON a.usuario_id = u.id";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setCandidatoId(rs.getInt("candidato_id"));
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                avaliacao.setEstrelas(rs.getInt("estrelas"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacao.setNomeUsuario(rs.getString("nome_usuario")); // Preenche o nome do usuário
                avaliacoes.add(avaliacao);
            }
        }
        return avaliacoes;
    }

    // Método para obter a média de avaliações de um candidato
    public double obterMediaAvaliacoesPorCandidato(int candidatoId) throws ClassNotFoundException, SQLException {
        String sql = "SELECT AVG(estrelas) AS media FROM avaliacao WHERE candidato_id = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, candidatoId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getDouble("media") : 0; // Retorna a média ou 0 se não houver avaliações
        }
    }
}
