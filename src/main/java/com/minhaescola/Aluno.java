package main.java.com.minhaescola;  // Corrigido o nome do pacote

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Aluno implements UsuarioAutenticavel, OperacoesAluno {
    private ConexaoBanco conexao;

    public Aluno(ConexaoBanco conexao) {
        this.conexao = conexao;
    }

    @Override
    public boolean login(String usuario, String senha) {
        try {
            // Verifique se o nome da tabela e colunas estão corretos
            String sql = "SELECT * FROM alunos WHERE usuario = ? AND senha = ?";
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

    // Implementações de OperacoesAluno devem seguir aqui...
}
