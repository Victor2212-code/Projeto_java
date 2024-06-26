package com.mycompany.projeto_java;

import java.sql.*;
import java.util.Scanner;

public class Aluno implements UsuarioAutenticavel {
    private ConexaoBanco conexao;

    public Aluno() {
        this.conexao = new ConexaoBanco();
    }

    public Aluno(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean login(String usuario, String senha) {
        try (Connection connection = conexao.getConexao()) {
            String query = "SELECT * FROM senhas WHERE usuario = ? AND senha = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String matricula = resultSet.getString("matricula");

                System.out.println("Login bem-sucedido!");

                query = "SELECT nome, semestre FROM aluno WHERE matricula = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, matricula);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    String semestreAluno = resultSet.getString("semestre");

                    System.out.println("Informações do aluno - Nome: " + nome + ", Matricula: " + matricula + ", Semestre: " + semestreAluno);
                    System.out.println("--------------------------------------------------");

                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Qual operação deseja fazer [1] Ver Aulas, [2] Ver a lista de professores, [3] Ver lista de matérias, [4] Ver notas:");
                    String opcaoOperacao = scanner.nextLine();

                    switch (opcaoOperacao) {
                        case "1":
                            visualizarAulas(semestreAluno);
                            break;
                        case "2":
                            visualizarProfessores(semestreAluno);
                            break;
                        case "3":
                            visualizarMaterias(semestreAluno);
                            break;
                        case "4":
                            visualizarNotas(matricula);
                            break;
                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }
                    return true;
                } else {
                    System.out.println("Informações do aluno não encontradas.");
                }
            } else {
                System.out.println("Usuário ou senha incorretos.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao tentar logar: " + e.getMessage());
        }
        return false;
    }

    private void visualizarAulas(String semestreAluno) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual dia você deseja visualizar [1] Segunda-Feira, [2] Terça-Feira, [3] Quarta-Feira, [4] Quinta-Feira, [5] Sexta-Feira, [6] Sábado:");
        String opcao = scanner.nextLine();

        String diaSemana = "";
        switch (opcao) {
            case "1":
                diaSemana = "segunda_feira";
                break;
            case "2":
                diaSemana = "terca_feira";
                break;
            case "3":
                diaSemana = "quarta_feira";
                break;
            case "4":
                diaSemana = "quinta_feira";
                break;
            case "5":
                diaSemana = "sexta_feira";
                break;
            case "6":
                diaSemana = "sabado";
                break;
            default:
                System.out.println("Dia da semana inválido.");
                return;
        }

        imprimirTabela("escola." + semestreAluno + "_semestre_" + diaSemana);
    }

    private void visualizarProfessores(String semestreAluno) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual o dia que você deseja ver a lista de professores [1] Segunda-Feira, [2] Terça-Feira, [3] Quarta-Feira, [4] Quinta-Feira, [5] Sexta-Feira, [6] Sábado:");
        String opcao = scanner.nextLine();

        String diaSemana = "";
        switch (opcao) {
            case "1":
                diaSemana = "segunda_feira";
                break;
            case "2":
                diaSemana = "terca_feira";
                break;
            case "3":
                diaSemana = "quarta_feira";
                break;
            case "4":
                diaSemana = "quinta_feira";
                break;
            case "5":
                diaSemana = "sexta_feira";
                break;
            case "6":
                diaSemana = "sabado";
                break;
            default:
                System.out.println("Dia da semana inválido.");
                return;
        }

        imprimirColuna("escola." + semestreAluno + "_semestre_" + diaSemana, "professores");
    }

    private void visualizarMaterias(String semestreAluno) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual o dia que você deseja ver a lista de matérias [1] Segunda-Feira, [2] Terça-Feira, [3] Quarta-Feira, [4] Quinta-Feira, [5] Sexta-Feira, [6] Sábado:");
        String opcao = scanner.nextLine();

        String diaSemana = "";
        switch (opcao) {
            case "1":
                diaSemana = "segunda_feira";
                break;
            case "2":
                diaSemana = "terca_feira";
                break;
            case "3":
                diaSemana = "quarta_feira";
                break;
            case "4":
                diaSemana = "quinta_feira";
                break;
            case "5":
                diaSemana = "sexta_feira";
                break;
            case "6":
                diaSemana = "sabado";
                break;
            default:
                System.out.println("Dia da semana inválido.");
                return;
        }

        imprimirColuna("escola." + semestreAluno + "_semestre_" + diaSemana, "materias");
    }

    private void visualizarNotas(String matricula) {
        try (Connection connection = conexao.getConexao()) {
            String query = "SELECT * FROM notas_alunos WHERE matricula = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, matricula);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("--Essa é sua relação de notas---");
                do {
                    System.out.println("Disciplina: " + resultSet.getString("materia"));
                    System.out.println("Nota: " + resultSet.getString("notas"));
                    System.out.println("-----");
                } while (resultSet.next());
            } else {
                System.out.println("Nenhuma nota encontrada para a matrícula " + matricula);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao executar a consulta: " + e.getMessage());
        }
    }

    private void imprimirTabela(String nomeTabela) {
        try (Connection connection = conexao.getConexao()) {
            String consultaColunas = "SHOW COLUMNS FROM " + nomeTabela;
            Statement statement = connection.createStatement();
            ResultSet rsColunas = statement.executeQuery(consultaColunas);

            while (rsColunas.next()) {
                for (int i = 1; i <= rsColunas.getMetaData().getColumnCount(); i++) {
                    System.out.print(rsColunas.getString(i) + "\t");
                }
                System.out.println();
            }

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

    private void imprimirColuna(String nomeTabela, String nomeColuna) {
        try (Connection connection = conexao.getConexao()) {
            String query = "SELECT " + nomeColuna + " FROM " + nomeTabela;
            Statement statement = connection.createStatement();
            ResultSet rsColuna = statement.executeQuery(query);

            System.out.println("Lista de " + nomeColuna + ":");
            while (rsColuna.next()) {
                System.out.println(rsColuna.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao imprimir coluna: " + e.getMessage());
        }
    }
}
