package com.joao.agendamento.service.desconto;

import com.joao.agendamento.model.Cliente;
import java.math.BigDecimal;

/**
 * Padrao de projeto: STRATEGY.
 *
 * Define uma familia de algoritmos de calculo de desconto,
 * intercambiaveis em tempo de execucao pelo AgendamentoService.
 *
 * Justificativa: na versao inicial, a regra de desconto (percentual
 * fixo para "cliente fiel") estava escrita com "if/else" direto dentro
 * do metodo que calculava o valor final do agendamento (code smell:
 * logica de negocio condicional misturada com outras responsabilidades,
 * dificultando a criacao de novas politicas de desconto sem alterar
 * codigo existente). Com Strategy, novas politicas (ex.: desconto de
 * aniversario, desconto por pacote de servicos) podem ser adicionadas
 * criando uma nova classe, sem modificar o AgendamentoService (OCP -
 * Open/Closed Principle: aberto para extensao, fechado para modificacao).
 */
public interface PoliticaDesconto {
    BigDecimal calcularDesconto(Cliente cliente, BigDecimal valorOriginal);
}
