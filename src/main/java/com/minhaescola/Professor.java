package main.java.com.minhaescola;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Professor implements UsuarioAutenticavel, OperacoesProfessor {
    private ConexaoBanco conexao;

    public Professor(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean login(String usuario, String senha) {
        try {
            String sql = "SELECT * FROM professores WHERE usuario = ? AND senha = ?";
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
    public void registrarNotas(String matriculaAluno, double nota) {
        try {
            String sql = "INSERT INTO notas (matricula_aluno, nota) VALUES (?, ?)";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, matriculaAluno);
            stmt.setDouble(2, nota);
            stmt.executeUpdate();
            System.out.println("Nota registrada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao registrar nota: " + e.getMessage());
        }
    }

    @Override
    public void alterarNota(String matriculaAluno, double novaNota) {
        try {
            String sql = "UPDATE notas SET nota = ? WHERE matricula_aluno = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setDouble(1, novaNota);
            stmt.setString(2, matriculaAluno);
            stmt.executeUpdate();
            System.out.println("Nota atualizada com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar nota: " + e.getMessage());
        }
    }

    @Override
    public void visualizarHorarioAulas() {
        // Lógica para visualizar horário de aulas
    }
}
