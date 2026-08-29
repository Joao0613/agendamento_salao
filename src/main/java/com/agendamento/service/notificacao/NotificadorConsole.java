package com.joao.agendamento.service.notificacao;

/**
 * Implementacao simples de Notificador que imprime a mensagem no console.
 * Em um cenario real, poderia ser substituida por uma implementacao de
 * e-mail ou SMS sem que o restante do sistema precise mudar
 * (DIP: AgendamentoService depende da interface Notificador, nao desta
 * classe concreta; OCP: nova forma de notificar = nova classe).
 */
public class NotificadorConsole implements Notificador {
    @Override
    public void notificar(String destinatario, String mensagem) {
        System.out.println("[NOTIFICACAO para " + destinatario + "]: " + mensagem);
    }
}
