package com.votomais.dao;

import com.votomais.model.Conexao;
import com.votomais.model.Usuario;
import com.votomais.model.Usuario.NivelAcesso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método para cadastrar um novo usuário
    public void cadastrar(Usuario usuario) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha, nivel_acesso) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {

            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenha());
            comando.setInt(4, NivelAcesso.USUARIO.getNivel());
            comando.executeUpdate();
        }
    }

    // Método para autenticar um usuário
    public Usuario autenticar(String email, String senha) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (Connection con = Conexao.getConexao(); PreparedStatement comando = con.prepareStatement(sql)) {

            comando.setString(1, email);
            comando.setString(2, senha);
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setEmail(resultado.getString("email"));
                usuario.setSenha(resultado.getString("senha")); // Considere não retornar a senha
                usuario.setNivelAcesso(NivelAcesso.values()[resultado.getInt("nivel_acesso") - 1]);
                return usuario;
            }
        }
        return null;
    }
}
