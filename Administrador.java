package com.mycompany.projeto_java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Administrador implements UsuarioAutenticavel {

    private ConexaoBanco conexao;

    public Administrador(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean login(String usuario, String senha) {
        try (Connection connection = conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT matricula FROM senhas WHERE usuario = ? AND senha = ?")) {
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String matricula = resultSet.getString("matricula");
                return processLogin(matricula);
            } else {
                System.out.println("Usuário ou senha incorretos.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar logar: " + e.getMessage());
        }
        return false;
    }

    private boolean processLogin(String matricula) throws SQLException {
        try (Connection connection = conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT nome, codigo_autorizacao FROM administrador WHERE codigo_autorizacao = ?")) {
            preparedStatement.setString(1, matricula);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                handleUserLoginSuccess(resultSet);
                return true;
            }
        }
        return false;
    }

    private void handleUserLoginSuccess(ResultSet resultSet) throws SQLException {
        String codigoAutorizacaoAdmin = resultSet.getString("codigo_autorizacao");
        String nomeAdmin = resultSet.getString("nome");

        System.out.println("Login bem-sucedido! Informações do(a) administrador(a) - Nome: " + nomeAdmin + ", Código de Autorização: " + codigoAutorizacaoAdmin);
        performAdminOperations();
    }

    private void performAdminOperations() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Qual operação deseja fazer [1]Registrar novo administrador, [2] Registrar diretor, [3] registrar acesso de usuários, [4] Apagar acesso:");
            int opcaoOperacao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            switch (opcaoOperacao) {
                case 1:
                    System.out.println("Registrar novo administrador. Por favor, informe o nome e código de autorização:");
                    String nomeNovoAdmin = scanner.nextLine();
                    String codigoAutorizacaoNovoAdmin = scanner.nextLine();
                    registrarNovoAdministrador(nomeNovoAdmin, codigoAutorizacaoNovoAdmin);
                    break;
                case 2:
                    System.out.println("Registrar novo diretor. Por favor, informe a matrícula e o nome:");
                    String matriculaDiretor = scanner.nextLine();
                    String nomeDiretor = scanner.nextLine();
                    registrarDiretor(matriculaDiretor, nomeDiretor);
                    break;
                case 3:
                    System.out.println("Registrar novo acesso. Por favor, informe usuário, senha, matrícula e tipo de usuário:");
                    String usuarioFuncionario = scanner.nextLine();
                    String senhaFuncionario = scanner.nextLine();
                    String matriculaUsuario = scanner.nextLine();
                    int tipoUsuario = scanner.nextInt();
                    scanner.nextLine(); // Consome a nova linha
                    registrarUsuario(usuarioFuncionario, senhaFuncionario, matriculaUsuario, tipoUsuario);
                    break;
                case 4:
                    System.out.println("Apagar acesso. Por favor, informe o usuário e a matrícula:");
                    String usuarioApagar = scanner.nextLine();
                    String matriculaApagar = scanner.nextLine();
                    apagarAcesso(usuarioApagar, matriculaApagar);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public void registrarNovoAdministrador(String nome, String codigoAutorizacao) {
        try (Connection connection = conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO administrador (nome, codigo_autorizacao) VALUES (?, ?)")) {
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, codigoAutorizacao);
            preparedStatement.executeUpdate();
            System.out.println("Administrador registrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao registrar administrador: " + e.getMessage());
        }
    }

    public void registrarDiretor(String matricula, String nome) {
        try (Connection connection = conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO diretor (matricula, nome) VALUES (?, ?)")) {
            preparedStatement.setString(1, matricula);
            preparedStatement.setString(2, nome);
            preparedStatement.executeUpdate();
            System.out.println("Diretor registrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao registrar diretor: " + e.getMessage());
        }
    }

    public void registrarUsuario(String usuario, String senha, String matricula, int tipoUsuario) {
        try (Connection connection = conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO senhas (usuario, senha, matricula, tipo_usuario) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);
            preparedStatement.setString(3, matricula);
            preparedStatement.setInt(4, tipoUsuario);
            preparedStatement.executeUpdate();
            System.out.println("Usuário registrado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    public void apagarAcesso(String usuario, String matricula) {
        try (Connection connection = conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM senhas WHERE usuario = ? AND matricula = ?")) {
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, matricula);
            preparedStatement.executeUpdate();
            System.out.println("Usuário apagado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao apagar usuário: " + e.getMessage());
        }
    }
}
