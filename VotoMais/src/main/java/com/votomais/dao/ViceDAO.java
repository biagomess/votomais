package com.votomais.dao;

import com.votomais.model.Conexao;
import com.votomais.model.Vice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe ViceDAO - Gerencia operações relacionadas ao vice no banco de dados.
 */
public class ViceDAO {

    /**
     * Salva os dados de um vice no banco de dados.
     */
    public void salvarVice(Vice vice) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO vice (nmrRegistroVice ,nome, idade, cargo_politico, partido, historico, foto) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, vice.getNmrRegistroVice());
            ps.setString(2, vice.getNomeVice());
            ps.setInt(3, vice.getIdadeVice());
            ps.setString(4, vice.getCargoPoliticoVice() != null && !vice.getCargoPoliticoVice().isEmpty() ? 
                        vice.getCargoPoliticoVice() : "Vice-Prefeito"); // Default
            ps.setString(5, vice.getPartidoVice());
            ps.setString(6, vice.getHistoricoVice());
            ps.setString(7, vice.getFotoVice());
            ps.executeUpdate();

            // Obtém o ID gerado automaticamente para o vice
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                vice.setIdVice(rs.getInt(1));  // Acessando o idVice gerado
            }
        }
    }

    /**
     * Atualiza os dados de um vice no banco de dados.
     */
    public int atualizarVice(Vice vice) throws SQLException, ClassNotFoundException {
    // A query agora inclui todos os campos necessários, incluindo o campo nmrRegistroVice
    String sql = "UPDATE vice SET nmrRegistroVice = ?, nome = ?, partido = ?, foto = ? WHERE idVice = ?";

    try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
        // Passando os parâmetros corretamente
        ps.setInt(1, vice.getNmrRegistroVice());
        ps.setString(2, vice.getNomeVice());
        ps.setString(3, vice.getPartidoVice());  // Partido do vice
        ps.setString(4, vice.getFotoVice());  // Foto do vice
        ps.setInt(5, vice.getIdVice());  // ID do vice que será atualizado

        return ps.executeUpdate();  // Retorna o número de linhas afetadas
    }
}

    /**
     * Deleta um vice do banco de dados pelo ID.
     */
    public void deletarVice(int idVice) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM vice WHERE idVice = ?"; 

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVice);
            ps.executeUpdate();
        }
    }

    /**
     * Consulta os dados de um vice pelo ID.
     */
    public Vice consultarVicePorId(int idVice) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM vice WHERE idVice = ?";
        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idVice);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapVice(rs); // Utilizando o método mapVice para simplificar o código
            }
        }
        return null; // Retorna null se não encontrar o vice
    }
    
    // Método para consultar vice por nome
    public Vice consultarVicePorNome(String nome) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM vice WHERE nome = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Vice vice = new Vice();
                vice.setIdVice(rs.getInt("idVice"));
                vice.setNmrRegistroVice(rs.getInt("nmrRegistroVice")); 
                vice.setNomeVice(rs.getString("nome"));
                vice.setIdadeVice(rs.getInt("idade"));
                vice.setCargoPoliticoVice(rs.getString("cargo_politico"));
                vice.setPartidoVice(rs.getString("partido"));
                vice.setFotoVice(rs.getString("foto"));
                vice.setHistoricoVice(rs.getString("historico"));
                return vice;
            }
        }
        return null; // Retorna null caso não encontre
    }
    
    /**
     * Consulta todos os vices do banco de dados.
     */
    public List<Vice> consultarTodosVices() throws SQLException, ClassNotFoundException {
        List<Vice> vice = new ArrayList<>();
        String sql = "SELECT * FROM vice";  // ALTERADO PARA 'idVice'

        try (Connection conn = Conexao.getConexao(); 
             PreparedStatement ps = conn.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                vice.add(mapVice(rs)); // Utilizando o método mapVice para simplificar o código
            }
        }
        return vice;
    }

    /**
     * Mapeia um ResultSet para um objeto Vice representando um vice.
     */
    private Vice mapVice(ResultSet rs) throws SQLException {
        Vice vice = new Vice();
        vice.setIdVice(rs.getInt("idVice")); 
        vice.setNmrRegistroVice(rs.getInt("nmrRegistroVice")); 
        vice.setNomeVice(rs.getString("nome"));
        vice.setIdadeVice(rs.getInt("idade"));
        vice.setCargoPoliticoVice(rs.getString("cargo_politico"));
        vice.setPartidoVice(rs.getString("partido"));
        vice.setHistoricoVice(rs.getString("historico"));
        vice.setFotoVice(rs.getString("foto"));
        return vice;
    }

    public Vice consultarVicePorNmrRegistro(int nmrRegistroVice) throws SQLException, ClassNotFoundException {
        // Modifique a consulta para buscar todas as colunas necessárias
        String sql = "SELECT * FROM vice WHERE nmrRegistroVice = ?";
        try (Connection conn = Conexao.getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nmrRegistroVice);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Criação do objeto Vice e mapeamento de todas as colunas
                    Vice vice = new Vice();
                    vice.setIdVice(rs.getInt("id")); // Atribui o ID
                    vice.setNomeVice(rs.getString("nome")); // Atribui o Nome
                    vice.setIdadeVice(rs.getInt("idade")); // Atribui a Idade
                    vice.setCargoPoliticoVice(rs.getString("cargo_politico")); // Atribui o Cargo Político
                    vice.setPartidoVice(rs.getString("partido")); // Atribui o Partido
                    vice.setHistoricoVice(rs.getString("historico")); // Atribui o Histórico
                    vice.setFotoVice(rs.getString("foto")); // Atribui a Foto
                    vice.setNmrRegistroVice(rs.getInt("nmrRegistroVice")); // Atribui o Número de Registro
                    return vice; // Retorna o objeto Vice completo
                }
            }
        }
           return null; // Retorna null se o vice não for encontrado
    }
}

 
