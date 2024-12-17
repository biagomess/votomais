package com.votomais.dao;

import com.votomais.model.Conexao;
import com.votomais.model.Candidato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe CandidatoDAO - Gerencia operações relacionadas a candidatos no banco
 * de dados.
 */
public class CandidatoDAO {

    private String nmrRegistro;

    /**
     * Salva os dados de um candidato no banco de dados.
     */
    public void salvarCandidato(Candidato candidato) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO candidatos (nome, idade, cargo_politico, partido, historico, foto, idVice, nmrRegistro) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidato.getNomeCandidato());
            ps.setInt(2, candidato.getIdadeCandidato());

            // Verifica se o cargo foi informado, caso contrário, define "Prefeito"
            String cargo = candidato.getCargoPoliticoCandidato();
            if (cargo == null || cargo.isEmpty()) {
                cargo = "Prefeito";  // Definindo valor padrão caso o cargo não seja informado
            }
            ps.setString(3, cargo);

            ps.setString(4, candidato.getPartidoCandidato());
            ps.setString(5, candidato.getHistoricoCandidato());
            ps.setString(6, candidato.getFotoCandidato());

            // Verifica se o idVice é null, se for, o valor nulo é passado para o banco
            if (candidato.getIdVice() != null) {
                ps.setInt(7, candidato.getIdVice());
            } else {
                ps.setNull(7, Types.INTEGER); // Tratando o campo idVice como nulo
            }

            // Atribuindo o valor do nmrRegistro informado pelo usuário
            int nmrRegistro = candidato.getNmrRegistro();
            ps.setInt(8, nmrRegistro);  // Passando o valor do nmrRegistro

            ps.executeUpdate();

            // Obtém o ID gerado automaticamente para o candidato
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                candidato.setIdCandidato(rs.getInt(1));
            }
        }
    }

    /**
     * Atualiza os dados de um candidato no banco de dados.
     */
    public int atualizarCandidato(Candidato candidato) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE candidatos SET nmrRegistro = ?, nome = ?, idade = ?, cargo_politico = ?, partido = ?, historico = ?, foto = ?, idVice = ?"
                + "WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, candidato.getNmrRegistro());
            ps.setString(2, candidato.getNomeCandidato());
            ps.setInt(3, candidato.getIdadeCandidato());
            ps.setString(4, candidato.getCargoPoliticoCandidato());
            ps.setString(5, candidato.getPartidoCandidato());
            ps.setString(6, candidato.getHistoricoCandidato());
            ps.setString(7, candidato.getFotoCandidato());

            // Verifica se o idVice é null
            if (candidato.getIdVice() != null) {
                ps.setInt(8, candidato.getIdVice());
            } else {
                ps.setNull(8, Types.INTEGER); // Tratando o campo idVice como nulo
            }

            ps.setInt(9, candidato.getIdCandidato());

            return ps.executeUpdate(); // Retorna o número de linhas afetadas
        }
    }

    /**
     * Deleta um candidato do banco de dados pelo ID.
     */
    public void deletarCandidato(int idCandidato) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM candidatos WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCandidato);
            ps.executeUpdate();
        }
    }

    /**
     * Consulta os dados de um candidato pelo ID.
     */
    public Candidato consultarCandidatoPorId(int idCandidato) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM candidatos WHERE id = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCandidato);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Candidato candidato = new Candidato();
                candidato.setIdCandidato(rs.getInt("id"));
                candidato.setNomeCandidato(rs.getString("nome"));
                candidato.setIdadeCandidato(rs.getInt("idade"));
                candidato.setCargoPoliticoCandidato(rs.getString("cargo_politico"));
                candidato.setPartidoCandidato(rs.getString("partido"));
                candidato.setHistoricoCandidato(rs.getString("historico"));
                candidato.setFotoCandidato(rs.getString("foto"));
                candidato.setNmrRegistro(rs.getInt("nmrRegistro"));  // Mapeando o campo nmrRegistro

                // Verifica se o idVice é null e trata corretamente
                int idVice = rs.getInt("idVice");
                if (rs.wasNull()) {
                    candidato.setIdVice(null); // Se o valor for nulo, definimos o idVice como null
                } else {
                    candidato.setIdVice(idVice); // Se o idVice for válido, definimos

                    // Agora, buscamos o nome do vice, caso o idVice não seja null
                    String sqlVice = "SELECT nome, nmrRegistroVice, idade, cargo_politico, partido, historico FROM vice WHERE idVice = ?";
                    try (PreparedStatement psVice = conn.prepareStatement(sqlVice)) {
                        psVice.setInt(1, idVice);
                        try (ResultSet rsVice = psVice.executeQuery()) {
                            if (rsVice.next()) {
                                candidato.setNomeVice(rsVice.getString("nome"));  // Atribui o nome do vice
                                candidato.setNmrRegistroVice(rsVice.getInt("nmrRegistroVice"));
                                candidato.setIdadeVice(rsVice.getInt("idade"));
                                candidato.setCargoPoliticoVice(rsVice.getString("cargo_politico"));
                                candidato.setPartidoVice(rsVice.getString("partido"));
                                candidato.setHistoricoVice(rsVice.getString("historico"));
                            }
                        }
                    }
                }

                return candidato;
            }
        }
        return null; // Retorna null se não encontrar o candidato
    }
    
    /**
     * Consulta todos os candidatos do banco de dados.
     */
    public List<Candidato> consultarTodosCandidatos() throws SQLException, ClassNotFoundException {
        List<Candidato> candidatos = new ArrayList<>();
        String sql = "SELECT c.id, c.nome, c.idade, c.cargo_politico, c.partido, c.historico, c.foto, c.nmrRegistro, v.nome AS nomeVice "
                + "FROM candidatos c LEFT JOIN vice v ON c.idVice = v.idVice";  // LEFT JOIN para pegar todos os candidatos, mesmo sem vice

        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Candidato candidato = new Candidato();
                candidato.setIdCandidato(rs.getInt("id"));
                candidato.setNomeCandidato(rs.getString("nome"));
                candidato.setIdadeCandidato(rs.getInt("idade"));
                candidato.setCargoPoliticoCandidato(rs.getString("cargo_politico"));
                candidato.setPartidoCandidato(rs.getString("partido"));
                candidato.setHistoricoCandidato(rs.getString("historico"));
                candidato.setFotoCandidato(rs.getString("foto"));
                candidato.setNmrRegistro(rs.getInt("nmrRegistro"));

                // Pega o nome do vice diretamente da consulta
                candidato.setNomeVice(rs.getString("nomeVice"));  // Pode ser null se não houver vice

                candidatos.add(candidato);
            }
        }
        return candidatos;
    }

    // Método para associar um vice ao candidato pelo ID do vice
    public void associarCandidatoVicePorID(int idCandidato, int idVice) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE candidatos SET idVice = ? WHERE id = ?";

        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVice);
            ps.setInt(2, idCandidato);

            ps.executeUpdate();
        }
    }

    public Candidato consultarCandidatoPorNmrRegistro(int nmrRegistro) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM candidatos WHERE nmrRegistro = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nmrRegistro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Candidato candidato = new Candidato();
                    candidato.setIdCandidato(rs.getInt("id"));
                    candidato.setNomeCandidato(rs.getString("nome"));
                    candidato.setIdadeCandidato(rs.getInt("idade"));
                    candidato.setCargoPoliticoCandidato(rs.getString("cargo_politico"));
                    candidato.setPartidoCandidato(rs.getString("partido"));
                    candidato.setHistoricoCandidato(rs.getString("historico"));
                    candidato.setFotoCandidato(rs.getString("foto"));
                    candidato.setNmrRegistro(rs.getInt("nmrRegistro"));  // Mapeando o campo nmrRegistro

                    return candidato;
                }
            }
        }
        return null; // Retorna null se o candidato não for encontrado
    }

    public void associarCandidatoVicePorNmrRegistro(int nmrRegistro, int nmrRegistroVice) throws SQLException, ClassNotFoundException {
        // Buscar os dados do vice
        String sqlVice = "SELECT idVice, nome FROM vice WHERE nmrRegistroVice = ?";
        int idVice;
        String nomeVice = "";

        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sqlVice)) {
            ps.setInt(1, nmrRegistroVice);  // nmrRegistroVice agora é um INT

            // Executando a consulta para pegar as informações do vice
            try (ResultSet rsVice = ps.executeQuery()) {
                if (rsVice.next()) {
                    // Se encontrar o vice, pegamos os dados
                    idVice = rsVice.getInt("idVice");
                    nomeVice = rsVice.getString("nome");
                } else {
                    // Caso não encontre, lançar uma exceção
                    throw new SQLException("Vice não encontrado para o número de registro: " + nmrRegistroVice);
                }
            }

            // 2. Agora, atualizar a tabela candidatos com os dados do vice
            String sqlCandidato = "UPDATE candidatos SET idVice = ?, nomeVice = ? WHERE nmrRegistro = ?";
            try (PreparedStatement psCandidato = conn.prepareStatement(sqlCandidato)) {
                psCandidato.setInt(1, idVice); // Atualizando com idVice
                psCandidato.setString(2, nomeVice); // Atualizando com nomeVice
                psCandidato.setInt(3, nmrRegistro); // Atualizando o candidato com o nmrRegistro
                int rowsUpdated = psCandidato.executeUpdate();

                if (rowsUpdated == 0) {
                    throw new SQLException("Nenhuma linha foi atualizada para o candidato com Nº de Registro: " + nmrRegistro);
                }
            } catch (SQLException e) {
                throw new SQLException("Erro ao associar candidato e vice: " + e.getMessage(), e);
            }

        }
    }
}
