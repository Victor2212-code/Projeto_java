package main.java.com.minhaescola;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Secretario implements UsuarioAutenticavel, OperacoesSecretario {
    private ConexaoBanco conexao;

    public Secretario(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean login(String usuario, String senha) {
        try {
            String sql = "SELECT * FROM secretarios WHERE usuario = ? AND senha = ?";
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
    public void matricularAluno(String nome, String matricula, int ano) {
        try {
            String sql = "INSERT INTO alunos (nome, matricula, ano) VALUES (?, ?, ?)";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, matricula);
            stmt.setInt(3, ano);
            stmt.executeUpdate();
            System.out.println("Aluno matriculado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao matricular aluno: " + e.getMessage());
        }
    }

    @Override
    public void desmatricularAluno(String matricula) {
        try {
            String sql = "DELETE FROM alunos WHERE matricula = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, matricula);
            stmt.executeUpdate();
            System.out.println("Aluno desmatriculado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao desmatricular aluno: " + e.getMessage());
        }
    }

    @Override
    public void registrarMateria(String nome, String codigo) {
        try {
            String sql = "INSERT INTO materias (nome, codigo) VALUES (?, ?)";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, codigo);
            stmt.executeUpdate();
            System.out.println("Matéria registrada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao registrar matéria: " + e.getMessage());
        }
    }

    @Override
    public void removerMateria(String codigo) {
        try {
            String sql = "DELETE FROM materias WHERE codigo = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, codigo);
            stmt.executeUpdate();
            System.out.println("Matéria removida com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover matéria: " + e.getMessage());
        }
    }
}
