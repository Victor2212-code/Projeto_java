package my_company.projeto_java;

import com.mycompany.projeto_java.ConexaoBanco;
import com.mycompany.projeto_java.UsuarioAutenticavel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Professor implements UsuarioAutenticavel {
    private ConexaoBanco conexao;

    public Professor(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean login(String usuario, String senha) {
        try (Connection conn = conexao.getConexao();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "SELECT matricula FROM senhas WHERE usuario = ? AND senha = ?")) {
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return processLogin(resultSet.getString("matricula"));
            } else {
                System.out.println("Usuário ou senha incorretos.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar logar: " + e.getMessage());
        }
        return false;
    }

    private boolean processLogin(String matricula) throws SQLException {
        try (Connection conn = conexao.getConexao();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "SELECT matricula, nome FROM professor WHERE matricula = ?")) {
            preparedStatement.setString(1, matricula);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login bem-sucedido! Informações do(a) professor(a) - Nome: " + resultSet.getString("nome") + ", Matricula: " + matricula);
                performProfessorOperations(matricula);
                return true;
            }
        }
        return false;
    }

    private void performProfessorOperations(String matricula) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Qual operação deseja fazer [1] Visualizar Horário de Aulas, [2] Registrar Notas, [3] Alterar Notas:");
            int opcaoOperacao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            switch (opcaoOperacao) {
                case 1:
                    visualizarHorarioAulas(matricula);
                    break;
                case 2:
                    System.out.println("Registrar notas. Por favor, informe a matrícula do aluno, código da matéria e a nota:");
                    String matriculaAluno = scanner.nextLine();
                    String codigoMateria = scanner.nextLine();
                    double nota = scanner.nextDouble();
                    registrarNotas(matriculaAluno, codigoMateria, nota);
                    break;
                case 3:
                    System.out.println("Alterar nota. Por favor, informe a matrícula do aluno, código da matéria e a nova nota:");
                    String matriculaAlunoAlt = scanner.nextLine();
                    String codigoMateriaAlt = scanner.nextLine();
                    double novaNota = scanner.nextDouble();
                    alterarNota(matriculaAlunoAlt, codigoMateriaAlt, novaNota);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public void visualizarHorarioAulas(String matricula) {
        try (Connection conn = conexao.getConexao();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "SELECT horario FROM aulas WHERE professor_matricula = ?")) {
            preparedStatement.setString(1, matricula);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println("Horário: " + resultSet.getString("horario"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao visualizar horários: " + e.getMessage());
        }
    }

    public void registrarNotas(String matriculaAluno, String codigoMateria, double nota) {
        try (Connection conn = conexao.getConexao();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "INSERT INTO notas (aluno_matricula, materia_codigo, nota) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, matriculaAluno);
            preparedStatement.setString(2, codigoMateria);
            preparedStatement.setDouble(3, nota);
            preparedStatement.executeUpdate();
            System.out.println("Nota registrada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao registrar notas: " + e.getMessage());
        }
    }

    public void alterarNota(String matriculaAluno, String codigoMateria, double novaNota) {
        try (Connection conn = conexao.getConexao();
             PreparedStatement preparedStatement = conn.prepareStatement(
                     "UPDATE notas SET nota = ? WHERE aluno_matricula = ? AND materia_codigo = ?")) {
            preparedStatement.setDouble(1, novaNota);
            preparedStatement.setString(2, matriculaAluno);
            preparedStatement.setString(3, codigoMateria);
            preparedStatement.executeUpdate();
            System.out.println("Nota alterada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao alterar nota: " + e.getMessage());
        }
    }
}
