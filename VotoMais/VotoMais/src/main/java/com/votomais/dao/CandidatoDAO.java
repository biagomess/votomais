package com.votomais.dao;

import com.votomais.model.Conexao;
import com.votomais.model.Avaliacao;
import com.votomais.model.Candidato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CandidatoDAO {

    // Método para cadastrar um candidato
    public void cadastrar(Candidato c) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO candidatos(nome, idade, cargo_politico, partido, historico, foto) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {
            comando.setString(1, c.getNome());
            comando.setInt(2, c.getIdade());
            comando.setString(3, c.getCargoPolitico());
            comando.setString(4, c.getPartido());
            comando.setString(5, c.getHistorico());
            comando.setString(6, c.getFoto());
            comando.execute();
        }
    }

    // Método para deletar um candidato
    public void deletar(int id) throws ClassNotFoundException, SQLException {
        String sql = "DELETE FROM candidatos WHERE id=?";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {
            comando.setInt(1, id);
            comando.execute();
        }
    }

    // Método para atualizar um candidato
    public void atualizar(Candidato c) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE candidatos SET nome=?, idade=?, cargo_politico=?, partido=?, historico=?, foto=?, vice_id=? WHERE id=?";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {
            comando.setString(1, c.getNome());
            comando.setInt(2, c.getIdade());
            comando.setString(3, c.getCargoPolitico());
            comando.setString(4, c.getPartido());
            comando.setString(5, c.getHistorico());
            comando.setString(6, c.getFoto());
            comando.setInt(7, c.getVice_id());
            comando.setInt(8, c.getId());
            comando.execute();
        }
    }

    // Método para consultar candidato por ID
    public Candidato consultarPorId(int id) throws ClassNotFoundException, SQLException {
        Candidato candidato = null;
        String sql = "SELECT * FROM candidatos WHERE id=?";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {
            comando.setInt(1, id);
            ResultSet rs = comando.executeQuery();
            if (rs.next()) {
                candidato = mapCandidato(rs);
                candidato.setAvaliacoes(obterAvaliacoesPorCandidato(candidato.getId()));
            }
        }
        return candidato;
    }

    // Método para consultar todos os candidatos
    public List<Candidato> consultarTodos() throws ClassNotFoundException, SQLException {
        List<Candidato> candidatos = new ArrayList<>();
        String sql = "SELECT * FROM candidatos";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql); ResultSet rs = comando.executeQuery()) {
            while (rs.next()) {
                Candidato candidato = mapCandidato(rs);
                candidatos.add(candidato);
            }
        }
        return candidatos;
    }

    // Método para filtrar candidatos por partido
    public List<Candidato> filtrarPorPartido(String partido) throws ClassNotFoundException, SQLException {
        List<Candidato> candidatos = new ArrayList<>();
        String sql = "SELECT * FROM candidatos WHERE partido=?";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {
            comando.setString(1, partido);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                Candidato candidato = mapCandidato(rs);
                candidatos.add(candidato);
            }
        }
        return candidatos;
    }

    // Método para filtrar candidatos pelos melhores avaliados
    public List<Candidato> filtrarMelhoresAvaliados() throws ClassNotFoundException, SQLException {
        List<Candidato> candidatos = new ArrayList<>();
        String sql = "SELECT c.*, AVG(a.estrelas) AS media_estrelas FROM candidatos c "
                + "JOIN avaliacao a ON c.id = a.candidato_id "
                + "GROUP BY c.id ORDER BY media_estrelas DESC";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql); ResultSet rs = comando.executeQuery()) {
            while (rs.next()) {
                Candidato candidato = mapCandidato(rs);
                candidatos.add(candidato);
            }
        }
        return candidatos;
    }

    // Método para filtrar candidatos pelos piores avaliados
    public List<Candidato> filtrarPioresAvaliados() throws ClassNotFoundException, SQLException {
        List<Candidato> candidatos = new ArrayList<>();
        String sql = "SELECT c.*, AVG(a.estrelas) AS media_estrelas FROM candidatos c "
                + "JOIN avaliacao a ON c.id = a.candidato_id "
                + "GROUP BY c.id ORDER BY media_estrelas ASC";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql); ResultSet rs = comando.executeQuery()) {
            while (rs.next()) {
                Candidato candidato = mapCandidato(rs);
                candidatos.add(candidato);
            }
        }
        return candidatos;
    }

    // Método para agregar um vice a um candidato
    public void agregarVice(int candidatoId, int viceId) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE candidatos SET vice_id=? WHERE id=?";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {
            comando.setInt(1, viceId);
            comando.setInt(2, candidatoId);
            comando.executeUpdate();
        }
    }

    // Método para obter avaliações de um candidato
    public List<Avaliacao> obterAvaliacoesPorCandidato(int candidatoId) throws ClassNotFoundException, SQLException {
        List<Avaliacao> avaliacoes = new ArrayList<>();
        String sql = "SELECT * FROM avaliacao WHERE candidato_id=?";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {
            comando.setInt(1, candidatoId);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setId(rs.getInt("id"));
                avaliacao.setCandidatoId(rs.getInt("candidato_id"));
                avaliacao.setUsuarioId(rs.getInt("usuario_id"));
                avaliacao.setEstrelas(rs.getInt("estrelas"));
                avaliacao.setComentario(rs.getString("comentario"));
                avaliacoes.add(avaliacao);
            }
        }
        return avaliacoes;
    }

    // Método para verificar se um candidato existe
    public boolean existeCandidato(int id) throws ClassNotFoundException, SQLException {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM candidatos WHERE id=?";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {
            comando.setInt(1, id);
            ResultSet rs = comando.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        }
        return existe;
    }

    // Método privado para mapear um ResultSet para um objeto Candidato
    private Candidato mapCandidato(ResultSet rs) throws SQLException {
        Candidato candidato = new Candidato();
        candidato.setId(rs.getInt("id"));
        candidato.setNome(rs.getString("nome"));
        candidato.setIdade(rs.getInt("idade"));
        candidato.setCargoPolitico(rs.getString("cargo_politico"));
        candidato.setPartido(rs.getString("partido"));
        candidato.setHistorico(rs.getString("historico"));
        candidato.setFoto(rs.getString("foto"));
        candidato.setVice_id(rs.getInt("vice_id"));
        return candidato;
    }

    public Candidato consultarById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void update(Candidato c) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
