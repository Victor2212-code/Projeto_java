package com.mycompany.projeto_java;

/**
 * Interface para definir operações comuns a todos os tipos de usuários dentro de um sistema.
 * Esta interface fornece o método para mostrar as operações disponíveis para um usuário,
 * facilitando a criação de uma experiência de usuário consistente e modular.
 */
public interface OperacoesUsuario {

    /**
     * Mostra as operações disponíveis para o usuário. Este método deve ser implementado
     * de forma a proporcionar uma lista ou descrição das ações que o usuário pode realizar,
     * dependendo do tipo de usuário e de suas permissões dentro do sistema.
     */
    void mostrarOperacoes();
}
