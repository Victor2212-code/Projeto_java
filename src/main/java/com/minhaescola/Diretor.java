package main.java.com.minhaescola;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Diretor implements UsuarioAutenticavel, OperacoesDiretor {
    private ConexaoBanco conexao;

    public Diretor(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean login(String usuario, String senha) {
        try {
            String sql = "SELECT * FROM diretores WHERE usuario = ? AND senha = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet resultSet = stmt.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Erro ao tentar logar: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void visualizarRelatorios() {
        // Lógica para gerar e visualizar relatórios
    }

    @Override
    public void adicionarProfessor(String nome, String matricula) {
        try {
            String sql = "INSERT INTO professores (nome, matricula) VALUES (?, ?)";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, matricula);
            stmt.executeUpdate();
            System.out.println("Professor adicionado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar professor: " + e.getMessage());
        }
    }

    @Override
    public void removerProfessor(String matricula) {
        try {
            String sql = "DELETE FROM professores WHERE matricula = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, matricula);
            stmt.executeUpdate();
            System.out.println("Professor removido com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover professor: " + e.getMessage());
        }
    }

    @Override
    public void adicionarSecretario(String nome, String matricula) {
        try {
            String sql = "INSERT INTO secretarios (nome, matricula) VALUES (?, ?)";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, matricula);
            stmt.executeUpdate();
            System.out.println("Secretário adicionado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar secretário: " + e.getMessage());
        }
    }

    @Override
    public void removerSecretario(String matricula) {
        try {
            String sql = "DELETE FROM secretarios WHERE matricula = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, matricula);
            stmt.executeUpdate();
            System.out.println("Secretário removido com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover secretário: " + e.getMessage());
        }
    }
}
