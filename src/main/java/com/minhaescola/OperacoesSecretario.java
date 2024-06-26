package main.java.com.minhaescola;


public interface OperacoesSecretario {
    void matricularAluno(String nome, String matricula, int ano);
    void desmatricularAluno(String matricula);
    void registrarMateria(String nome, String codigo);
    void removerMateria(String codigo);
}

