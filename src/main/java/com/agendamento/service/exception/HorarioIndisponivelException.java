package com.joao.agendamento.service.exception;

/**
 * Lancada quando um agendamento e solicitado em um horario que
 * conflita com outro agendamento ja existente do mesmo profissional.
 *
 * Substitui o uso de RuntimeException generica (code smell encontrado
 * na versao inicial), tornando o tratamento de erros explicito e
 * especifico do dominio.
 */
public class HorarioIndisponivelException extends Exception {
    public HorarioIndisponivelException(String mensagem) {
        super(mensagem);
    }
}
