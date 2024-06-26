package main.java.com.minhaescola;

public interface OperacoesAdministrador {
    /**
     * Adiciona um novo usuário ao sistema.
     * @param usuario Nome de usuário
     * @param senha Senha do usuário
     * @param tipo Tipo de usuário (administrador, professor, etc.)
     */
    void adicionarUsuario(String usuario, String senha, String tipo);

    /**
     * Remove um usuário do sistema baseado no seu nome de usuário.
     * @param usuario Nome de usuário a ser removido
     */
    void removerUsuario(String usuario);

    /**
     * Atualiza informações de um administrador.
     * @param usuario Nome de usuário atual do administrador
     * @param novoNome Novo nome de usuário para o administrador
     * @param novaSenha Nova senha para o administrador
     */
    void atualizarAdministrador(String usuario, String novoNome, String novaSenha);
}

