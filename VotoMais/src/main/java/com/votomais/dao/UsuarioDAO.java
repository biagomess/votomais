package com.votomais.dao;

import com.votomais.model.Conexao;
import com.votomais.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método para verificar se o e-mail já existe no banco de dados
    public boolean emailExistente(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email); // Define o e-mail como parâmetro da consulta
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se o e-mail já existe
            }
        }
        return false; // Retorna false se o e-mail não existe
    }

    // Método para cadastrar um novo usuário
    public void cadastrar(Usuario usuario) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha, nivel_acesso) VALUES (?, ?, ?, ?)";
        try (Connection connection = Conexao.getConexao(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Define os valores dos parâmetros da consulta
            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getEmail());
            preparedStatement.setString(3, usuario.getSenha()); // Armazena a senha em texto claro
            preparedStatement.setInt(4, Usuario.NivelAcesso.USUARIO.getNivel());
            preparedStatement.executeUpdate(); // Executa a inserção
        }
    }

    // Método para autenticar um usuário com e-mail e senha
    public Usuario autenticar(String email, String senha) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (Connection connection = Conexao.getConexao(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email); // Define o e-mail na consulta
            preparedStatement.setString(2, senha); // Comparação direta de senha (texto claro)
            ResultSet resultado = preparedStatement.executeQuery();

            if (resultado.next()) {
                // Cria o objeto Usuario caso a autenticação seja bem-sucedida
                Usuario usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setEmail(resultado.getString("email"));
                usuario.setSenha(null); // Não retorna a senha por segurança
                usuario.setNivelAcesso(Usuario.NivelAcesso.values()[resultado.getInt("nivel_acesso") - 1]); // Define o nível de acesso do usuário
                return usuario;
            }
        }
        return null; // Retorna null se a autenticação falhar
    }
}
