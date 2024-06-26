package main.java.com.minhaescola;

public interface UsuarioAutenticavel {
    /**
     * Realiza o login do usuário.
     * @param usuario Nome de usuário
     * @param senha Senha do usuário
     * @return true se o login for bem-sucedido, false caso contrário
     */
    boolean login(String usuario, String senha);
}

