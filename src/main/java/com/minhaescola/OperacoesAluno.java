package main.java.com.minhaescola;

public interface OperacoesAluno {
    /**
     * Visualiza as notas do aluno.
     */
    void visualizarNotas();

    /**
     * Visualiza o horário de aulas do aluno.
     */
    void visualizarHorarioAulas();

    /**
     * Consulta as matérias que o aluno está matriculado.
     */
    void consultarMaterias();

    /**
     * Consulta os professores das matérias em que o aluno está matriculado.
     */
    void consultarProfessores();
}
