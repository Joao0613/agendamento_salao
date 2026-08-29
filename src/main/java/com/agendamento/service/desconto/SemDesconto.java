package com.joao.agendamento.service.desconto;

import com.joao.agendamento.model.Cliente;
import java.math.BigDecimal;

/**
 * Estrategia padrao: nenhum desconto aplicado.
 */
public class SemDesconto implements PoliticaDesconto {
    @Override
    public BigDecimal calcularDesconto(Cliente cliente, BigDecimal valorOriginal) {
        return BigDecimal.ZERO;
    }
}
