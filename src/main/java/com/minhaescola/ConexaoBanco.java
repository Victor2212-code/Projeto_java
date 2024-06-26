package main.java.com.minhaescola;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private Connection conexao;

    public ConexaoBanco() {
        try {
            // Assegura que o driver JDBC do MySQL está carregado
            Class.forName("com.mysql.cj.jdbc.Driver"); // Assegura que o driver JDBC está disponível

            // Substitua pelos seus detalhes reais de conexão
            conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/escola?useTimezone=true&serverTimezone=UTC", "root", "Victorhug2212@");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: ", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado: ", e);
        }
    }

    public Connection getConexao() {
        return conexao;
    }

    public void encerrarConexao() {
        try {
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão com o banco de dados: " + e.getMessage());
        }
    }
}
