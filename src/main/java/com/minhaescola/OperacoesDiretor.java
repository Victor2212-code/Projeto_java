package main.java.com.minhaescola;

public interface OperacoesDiretor {
    void visualizarRelatorios();
    void adicionarProfessor(String nome, String matricula);
    void removerProfessor(String matricula);
    void adicionarSecretario(String nome, String matricula);
    void removerSecretario(String matricula);
}