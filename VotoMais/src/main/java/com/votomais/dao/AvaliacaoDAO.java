package com.votomais.dao;

import com.votomais.model.Avaliacao;
import com.votomais.model.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe AvaliacaoDAO - Gerencia operações no banco de dados relacionadas às
 * avaliações.
 */
public class AvaliacaoDAO {

    /**
     * Método para cadastrar uma nova avaliação.
     *
     * @param avaliacao Objeto Avaliacao contendo os dados a serem inseridos.
     * @throws ClassNotFoundException Se o driver do banco de dados não for
     * encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void cadastrar(Avaliacao avaliacao) throws ClassNotFoundException, SQLException {
        // Verifica se já existe uma avaliação do usuário para o candidato
        Avaliacao avaliacaoExistente = obterAvaliacaoPorUsuarioECandidato(avaliacao.getUsuarioId(), avaliacao.getCandidatoId());
        if (avaliacaoExistente != null) {
            throw new SQLException("O usuário já avaliou este candidato.");
        }

        String sql = "INSERT INTO avaliacao (candidato_id, usuario_id, nome_usuario, estrelas, comentario) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, avaliacao.getCandidatoId());
            stmt.setInt(2, avaliacao.getUsuarioId());
            stmt.setString(3, avaliacao.getNomeUsuario());
            stmt.setInt(4, avaliacao.getEstrelas());
            stmt.setString(5, avaliacao.getComentario());
            stmt.executeUpdate();
        }
    }

    /**
     * Método para atualizar uma avaliação existente.
     *
     * @param avaliacao Objeto Avaliacao contendo os dados atualizados.
     * @throws ClassNotFoundException Se o driver do banco de dados não for
     * encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void atualizar(Avaliacao avaliacao) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE avaliacao SET estrelas = ?, comentario = ? WHERE id = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, avaliacao.getEstrelas());
            stmt.setString(2, avaliacao.getComentario());
            stmt.setInt(3, avaliacao.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Método para deletar uma avaliação com base no ID.
     *
     * @param id ID da avaliação a ser removida.
     * @throws ClassNotFoundException Se o driver do banco de dados não for
     * encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public void deletar(int id) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM avaliacao WHERE id = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /**
     * Método para obter todas as avaliações de um candidato.
     *
     * @param candidatoId ID do candidato.
     * @return Lista de avaliações relacionadas ao candidato.
     * @throws ClassNotFoundException Se o driver do banco de dados não for
     * encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
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
                avaliacao.setNomeUsuario(rs.getString("nome_usuario"));
                avaliacao.setEstrelas(rs.getInt("estrelas"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacoes.add(avaliacao);
            }
        }
        return avaliacoes;
    }

    /**
     * Método para obter todas as avaliações.
     *
     * @return Lista contendo todas as avaliações do sistema.
     * @throws ClassNotFoundException Se o driver do banco de dados não for
     * encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public List<Avaliacao> obterTodasAvaliacoes() throws ClassNotFoundException, SQLException {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT a.*, u.nome AS nome_usuario FROM avaliacao a JOIN usuarios u ON a.usuario_id = u.id";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setCandidatoId(rs.getInt("candidato_id"));
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                avaliacao.setNomeUsuario(rs.getString("nome_usuario"));
                avaliacao.setEstrelas(rs.getInt("estrelas"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacoes.add(avaliacao);
            }
        }
        return avaliacoes;
    }

    /**
     * Método para calcular a média de estrelas de um candidato.
     *
     * @param candidatoId ID do candidato.
     * @return Média de estrelas das avaliações do candidato.
     * @throws ClassNotFoundException Se o driver do banco de dados não for
     * encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public double obterMediaAvaliacoesPorCandidato(int candidatoId) throws ClassNotFoundException, SQLException {
        String sql = "SELECT AVG(estrelas) AS media FROM avaliacao WHERE candidato_id = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, candidatoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("media");
            }
        }
        return 0;
    }

    /**
     * Método para verificar se já existe uma avaliação de um usuário para um candidato.
     *
     * @param usuarioId ID do usuário.
     * @param candidatoId ID do candidato.
     * @return A avaliação existente, ou null se não houver avaliação.
     * @throws ClassNotFoundException Se o driver do banco de dados não for encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public Avaliacao obterAvaliacaoPorUsuarioECandidato(int usuarioId, int candidatoId) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM avaliacao WHERE usuario_id = ? AND candidato_id = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, candidatoId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setCandidatoId(rs.getInt("candidato_id"));
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                avaliacao.setNomeUsuario(rs.getString("nome_usuario"));
                avaliacao.setEstrelas(rs.getInt("estrelas"));
                avaliacao.setComentario(rs.getString("comentario"));
                return avaliacao; // Retorna a avaliação se encontrada
            }
        }
        return null; // Retorna null se não houver avaliação
    }
    
    /**
     * Método para verificar se um usuário já fez um comentário sobre um candidato.
     *
     * @param usuarioId ID do usuário.
     * @param candidatoId ID do candidato.
     * @return boolean indicando se o usuário já fez um comentário.
     * @throws ClassNotFoundException Se o driver do banco de dados não for encontrado.
     * @throws SQLException Se ocorrer um erro de SQL.
     */
    public boolean verificarComentarioPorUsuarioECandidato(int usuarioId, int candidatoId) throws ClassNotFoundException, SQLException {
        String sql = "SELECT COUNT(*) FROM avaliacao WHERE usuario_id = ? AND candidato_id = ? AND comentario IS NOT NULL AND comentario != ''";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, candidatoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se o comentário existir
            }
        }
        return false; // Retorna false se não houver comentário
    }
}
