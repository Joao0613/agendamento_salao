package com.joao.agendamento.service.exception;

/**
 * Lancada quando uma entidade (cliente, profissional, servico ou
 * agendamento) nao e encontrada pelo id informado.
 */
public class EntidadeNaoEncontradaException extends Exception {
    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
