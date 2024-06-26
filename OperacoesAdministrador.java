package com.mycompany.projeto_java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OperacoesAdministrador {

    private ConexaoBanco conexao;

    public OperacoesAdministrador() {
        this.conexao = new ConexaoBanco();
    }

    public boolean login(String usuario, String senha) {
        try (Connection conn = conexao.getConexao()) {
            String query = "SELECT matricula FROM senhas WHERE usuario = ? AND senha = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
