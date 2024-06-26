package main.java.com.minhaescola;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrador implements UsuarioAutenticavel, OperacoesAdministrador {
    private ConexaoBanco conexao;

    public Administrador(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean login(String usuario, String senha) {
        try {
            String sql = "SELECT * FROM administradores WHERE usuario = ? AND senha = ?";
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
    public void adicionarUsuario(String usuario, String senha, String tipo) {
        try {
            String sql = "INSERT INTO usuarios (usuario, senha, tipo) VALUES (?, ?, ?)";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            stmt.setString(3, tipo);
            stmt.executeUpdate();
            System.out.println("Usuário adicionado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
        }
    }

    @Override
    public void removerUsuario(String usuario) {
        try {
            String sql = "DELETE FROM usuarios WHERE usuario = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.executeUpdate();
            System.out.println("Usuário removido com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao remover usuário: " + e.getMessage());
        }
    }

    @Override
    public void atualizarAdministrador(String usuario, String novoNome, String novaSenha) {
        try {
            String sql = "UPDATE administradores SET nome = ?, senha = ? WHERE usuario = ?";
            PreparedStatement stmt = conexao.getConexao().prepareStatement(sql);
            stmt.setString(1, novoNome);
            stmt.setString(2, novaSenha);
            stmt.setString(3, usuario);
            stmt.executeUpdate();
            System.out.println("Dados do administrador atualizados com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dados do administrador: " + e.getMessage());
        }
    }
}

