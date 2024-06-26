package com.mycompany.projeto_java;

import java.sql.*;
import java.util.Scanner;

public class Secretaria implements UsuarioAutenticavel {
    private ConexaoBanco conexao;

    public Secretaria() {
        this.conexao = new ConexaoBanco();
    }

    public Secretaria(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean login(String usuario, String senha) {
        try (Connection connection = conexao.getConexao()) {
            String query = "SELECT matricula FROM senhas WHERE usuario = ? AND senha = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String matricula = resultSet.getString("matricula");

                query = "SELECT matricula, nome FROM secretaria WHERE matricula = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, matricula);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String matriculaSecretaria = resultSet.getString("matricula");
                    String nomeSecretaria = resultSet.getString("nome");

                    System.out.println("Login bem-sucedido!");
                    System.out.println("Informações do(a) secretário(a) - Nome: " + nomeSecretaria + ", Matricula: " + matriculaSecretaria);
                    System.out.println("--------Bem-vindo Secretário(a)------------");

                    Scanner scanner = new Scanner(System.in);
                    System.out.println("[1] Registrar Aluno [2] Ver Lista de Professores [3] Ver Lista de Alunos:");
                    int opcaoOperacao = scanner.nextInt();
                    scanner.nextLine(); // Consome a nova linha

                    switch (opcaoOperacao) {
                        case 1:
                            System.out.print("Digite o nome: ");
                            String nome = scanner.nextLine();
                            System.out.print("Digite a Matrícula: ");
                            String matriculaAluno = scanner.nextLine();
                            registrarAluno(nome, matriculaAluno);
                            break;
                        case 2:
                            imprimirTabela("escola.professor");
                            break;
                        case 3:
                            imprimirTabela("escola.aluno");
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }
                    return true;
                } else {
                    System.out.println("Usuário ou senha incorretos.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao tentar logar: " + e.getMessage());
        }
        return false;
    }

    public void registrarAluno(String nome, String matricula) {
        try (Connection connection = conexao.getConexao()) {
            String query = "INSERT INTO aluno (matricula, nome) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, matricula);
            preparedStatement.setString(2, nome);
            preparedStatement.executeUpdate();
            System.out.println("Aluno registrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao registrar aluno: " + e.getMessage());
        }
    }

    public void imprimirTabela(String nomeTabela) {
        try (Connection connection = conexao.getConexao()) {
            String consultaColunas = "SHOW COLUMNS FROM " + nomeTabela;
            Statement statement = connection.createStatement();
            ResultSet rsColunas = statement.executeQuery(consultaColunas);

            StringBuilder colunas = new StringBuilder();
            while (rsColunas.next()) {
                colunas.append(rsColunas.getString(1)).append("\t");
            }
            System.out.println(colunas.toString());

            String consulta = "SELECT * FROM " + nomeTabela;
            ResultSet rsDados = statement.executeQuery(consulta);

            while (rsDados.next()) {
                for (int i = 1; i <= rsDados.getMetaData().getColumnCount(); i++) {
                    System.out.print(rsDados.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao imprimir tabela: " + e.getMessage());
        }
    }
}
