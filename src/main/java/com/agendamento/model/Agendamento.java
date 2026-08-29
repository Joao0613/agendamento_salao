package com.joao.agendamento.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa o agendamento de um servico, para um cliente, com um
 * profissional, em uma data/hora especifica.
 *
 * SRP: a classe apenas guarda o estado do agendamento. As regras
 * de negocio (verificar conflito de horario, calcular desconto,
 * notificar) ficam na camada de servico (AgendamentoService),
 * nao aqui.
 */
public class Agendamento {

    private final int id;
    private final Cliente cliente;
    private final Profissional profissional;
    private final Servico servico;
    private final LocalDateTime dataHora;
    private StatusAgendamento status;

    public Agendamento(int id, Cliente cliente, Profissional profissional,
                        Servico servico, LocalDateTime dataHora) {
        this.id = id;
        this.cliente = cliente;
        this.profissional = profissional;
        this.servico = servico;
        this.dataHora = dataHora;
        this.status = StatusAgendamento.AGENDADO;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public Servico getServico() {
        return servico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    /**
     * Verifica se este agendamento conflita com outro, considerando o
     * mesmo profissional e sobreposicao do intervalo de tempo do servico.
     */
    public boolean conflitaCom(Agendamento outro) {
        if (!this.profissional.equals(outro.profissional)) {
            return false;
        }
        LocalDateTime inicioA = this.dataHora;
        LocalDateTime fimA = this.dataHora.plusMinutes(this.servico.getDuracaoMinutos());
        LocalDateTime inicioB = outro.dataHora;
        LocalDateTime fimB = outro.dataHora.plusMinutes(outro.servico.getDuracaoMinutos());
        return inicioA.isBefore(fimB) && inicioB.isBefore(fimA);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agendamento)) return false;
        Agendamento that = (Agendamento) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Agendamento{" + "id=" + id + ", cliente=" + cliente.getNome() +
                ", profissional=" + profissional.getNome() + ", servico=" + servico.getNome() +
                ", dataHora=" + dataHora + ", status=" + status + '}';
    }
}
