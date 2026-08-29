package com.joao.agendamento.service;

import com.joao.agendamento.model.Agendamento;
import com.joao.agendamento.model.Cliente;
import com.joao.agendamento.model.Profissional;
import com.joao.agendamento.model.Servico;
import com.joao.agendamento.model.StatusAgendamento;
import com.joao.agendamento.repository.AgendamentoRepository;
import com.joao.agendamento.service.desconto.PoliticaDesconto;
import com.joao.agendamento.service.exception.EntidadeNaoEncontradaException;
import com.joao.agendamento.service.exception.HorarioIndisponivelException;
import com.joao.agendamento.service.notificacao.Notificador;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Regras de negocio do agendamento: verificar disponibilidade de
 * horario, calcular o valor final (com desconto) e notificar o
 * cliente. Esta e a classe que concentrava, na versao inicial (ver
 * pacote "legado"), TODAS as responsabilidades do sistema (God Class).
 *
 * SRP: esta classe agora cuida apenas da regra de agendamento em si.
 * Persistencia foi extraida para AgendamentoRepository, calculo de
 * desconto para PoliticaDesconto (Strategy) e envio de aviso para
 * Notificador. Cadastro de cliente/profissional/servico foi extraido
 * para seus proprios services.
 *
 * DIP: todas as dependencias (repositorio, politica de desconto,
 * notificador) sao interfaces recebidas via construtor (injecao de
 * dependencia manual), nunca instanciadas diretamente com "new" dentro
 * dos metodos de regra de negocio.
 */
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final PoliticaDesconto politicaDesconto;
    private final Notificador notificador;
    private final AtomicInteger proximoId = new AtomicInteger(1);

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                               PoliticaDesconto politicaDesconto,
                               Notificador notificador) {
        this.agendamentoRepository = agendamentoRepository;
        this.politicaDesconto = politicaDesconto;
        this.notificador = notificador;
    }

    /**
     * Agenda um novo servico, validando conflito de horario para o
     * profissional escolhido.
     */
    public Agendamento agendar(Cliente cliente, Profissional profissional, Servico servico,
                                LocalDateTime dataHora) throws HorarioIndisponivelException {
        Agendamento novo = new Agendamento(proximoId.getAndIncrement(), cliente, profissional, servico, dataHora);

        for (Agendamento existente : agendamentoRepository.listarPorProfissional(profissional.getId())) {
            if (existente.getStatus() != StatusAgendamento.CANCELADO && existente.conflitaCom(novo)) {
                throw new HorarioIndisponivelException(
                        "Profissional " + profissional.getNome() + " ja possui agendamento nesse horario.");
            }
        }

        agendamentoRepository.salvar(novo);
        notificador.notificar(cliente.getEmail(),
                "Agendamento confirmado: " + servico.getNome() + " em " + dataHora + " com " + profissional.getNome());
        return novo;
    }

    /**
     * Calcula o valor final do agendamento aplicando a politica de
     * desconto configurada (Strategy).
     */
    public BigDecimal calcularValorFinal(Agendamento agendamento) {
        BigDecimal valorOriginal = agendamento.getServico().getPreco();
        BigDecimal desconto = politicaDesconto.calcularDesconto(agendamento.getCliente(), valorOriginal);
        return valorOriginal.subtract(desconto);
    }

    public void cancelar(int agendamentoId) throws EntidadeNaoEncontradaException {
        Agendamento agendamento = agendamentoRepository.buscarPorId(agendamentoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Agendamento nao encontrado: id=" + agendamentoId));
        agendamento.setStatus(StatusAgendamento.CANCELADO);
        notificador.notificar(agendamento.getCliente().getEmail(),
                "Seu agendamento de " + agendamento.getServico().getNome() + " foi cancelado.");
    }

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.listarTodos();
    }
}
