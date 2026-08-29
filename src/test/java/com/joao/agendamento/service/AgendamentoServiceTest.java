package com.joao.agendamento.service;

import com.joao.agendamento.model.Agendamento;
import com.joao.agendamento.model.Cliente;
import com.joao.agendamento.model.Profissional;
import com.joao.agendamento.model.Servico;
import com.joao.agendamento.repository.AgendamentoRepository;
import com.joao.agendamento.repository.memoria.AgendamentoRepositoryMemoria;
import com.joao.agendamento.service.desconto.DescontoClienteFiel;
import com.joao.agendamento.service.desconto.PoliticaDesconto;
import com.joao.agendamento.service.exception.HorarioIndisponivelException;
import com.joao.agendamento.service.notificacao.Notificador;
import com.joao.agendamento.service.notificacao.NotificadorConsole;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AgendamentoServiceTest {

    private AgendamentoService agendamentoService;
    private Cliente clienteFiel;
    private Cliente clienteComum;
    private Profissional profissional;
    private Servico servico;

    @BeforeEach
    void setUp() {
        AgendamentoRepository repo = new AgendamentoRepositoryMemoria();
        PoliticaDesconto politicaDesconto = new DescontoClienteFiel();
        Notificador notificador = new NotificadorConsole();

        agendamentoService = new AgendamentoService(repo, politicaDesconto, notificador);

        clienteFiel = new Cliente(1, "Maria Silva", "48999999999", "maria@email.com", true);
        clienteComum = new Cliente(2, "João Souza", "48888888888", "joao@email.com", false);
        profissional = new Profissional(1, "Carlos Barbeiro", "Corte");
        servico = new Servico(1, "Corte Masculino", 30, new BigDecimal("50.00"));
    }

    @Test
    @DisplayName("Deve realizar agendamento com sucesso para cliente fiel")
    void deveAgendarParaClienteFiel() throws HorarioIndisponivelException {
        LocalDateTime dataHora = LocalDateTime.of(2026, 5, 10, 14, 0);

        Agendamento agendamento = agendamentoService.agendar(clienteFiel, profissional, servico, dataHora);

        assertNotNull(agendamento);
        assertEquals(clienteFiel, agendamento.getCliente());
        assertEquals(profissional, agendamento.getProfissional());
    }

    @Test
    @DisplayName("Deve lançar exceção ao agendar no mesmo horário para o mesmo profissional")
    void deveLancarExcecaoQuandoHorarioConflitar() throws HorarioIndisponivelException {
        LocalDateTime dataHora = LocalDateTime.of(2026, 5, 10, 16, 0);

        agendamentoService.agendar(clienteFiel, profissional, servico, dataHora);

        assertThrows(HorarioIndisponivelException.class, () -> {
            agendamentoService.agendar(clienteComum, profissional, servico, dataHora);
        });
    }
}