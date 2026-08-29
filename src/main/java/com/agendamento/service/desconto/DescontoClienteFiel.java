package com.joao.agendamento.service.desconto;

import com.joao.agendamento.model.Cliente;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Estrategia de desconto para clientes fieis: aplica 10% de desconto
 * sobre o valor original do servico.
 */
public class DescontoClienteFiel implements PoliticaDesconto {

    private static final BigDecimal PERCENTUAL = new BigDecimal("0.10");

    @Override
    public BigDecimal calcularDesconto(Cliente cliente, BigDecimal valorOriginal) {
        if (cliente.isClienteFiel()) {
            return valorOriginal.multiply(PERCENTUAL).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
