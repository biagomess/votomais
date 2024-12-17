package com.votomais.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    /**
     * Obtém uma conexão com o banco de dados.
     *
     * @return Objeto Connection para interação com o banco.
     * @throws ClassNotFoundException Se o driver do banco não for encontrado.
     * @throws SQLException Se ocorrer um erro de conexão.
     */
    public static Connection getConexao() throws ClassNotFoundException, SQLException {
        // Carrega o driver do banco de dados
        Class.forName("org.postgresql.Driver");

        // Carrega as propriedades do arquivo config.properties
        Properties properties = new Properties();
        try (InputStream input = Conexao.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Arquivo config.properties não encontrado no classpath!");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo config.properties", e);
        }

        // Obtém os valores do arquivo de propriedades
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        // Cria a conexão com o banco de dados
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        try {
            Connection connection = Conexao.getConexao();
            System.out.println("Conexão estabelecida com sucesso!");
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
        }
    }
}
