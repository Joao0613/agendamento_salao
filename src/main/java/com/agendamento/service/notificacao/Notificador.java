package com.joao.agendamento.service.notificacao;

/**
 * Interface segregada (ISP - Interface Segregation Principle) apenas
 * com o metodo necessario para notificar. Na versao inicial existia
 * uma unica classe de servico grande demais, misturando persistencia,
 * notificacao e regras de agendamento (code smell: classe/interface
 * inchada). Aqui cada interface tem uma responsabilidade estreita e
 * coesa.
 */
public interface Notificador {
    void notificar(String destinatario, String mensagem);
}
