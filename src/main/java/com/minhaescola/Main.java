package main.java.com.minhaescola;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConexaoBanco conexao = new ConexaoBanco();
        UsuarioAutenticavel usuario = null;

        try {
            System.out.println("Bem-vindo ao Sistema Escolar!");
            System.out.println("Por favor, selecione seu tipo de usuário:");
            System.out.println("[1] Administrador");
            System.out.println("[2] Diretor");
            System.out.println("[3] Professor");
            System.out.println("[4] Secretário");
            System.out.println("[5] Aluno");
            System.out.print("Digite sua escolha: ");
            int escolha = Integer.parseInt(scanner.nextLine());

            switch (escolha) {
                case 1:
                    usuario = new Administrador(conexao);
                    break;
                case 2:
                    usuario = new Diretor(conexao);
                    break;
                case 3:
                    usuario = new Professor(conexao);
                    break;
                case 4:
                    usuario = new Secretario(conexao);
                    break;
                case 5:
                    usuario = new Aluno(conexao);
                    break;
                default:
                    System.out.println("Escolha inválida!");
                    return;
            }

            System.out.print("Usuário: ");
            String nomeUsuario = scanner.nextLine();
            System.out.print("Senha: ");
            String senhaUsuario = scanner.nextLine();

            boolean sucessoLogin = usuario.login(nomeUsuario, senhaUsuario);
            if (sucessoLogin) {
                System.out.println("Login bem-sucedido!");
                // Aqui você pode expandir as opções para cada tipo de usuário
            } else {
                System.out.println("Falha no login, verifique seu usuário e senha.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            scanner.close();
            conexao.encerrarConexao();
        }
    }
}
