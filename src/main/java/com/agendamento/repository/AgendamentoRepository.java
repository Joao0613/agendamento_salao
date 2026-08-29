package com.joao.agendamento.repository;

import com.joao.agendamento.model.Agendamento;
import java.util.List;
import java.util.Optional;

public interface AgendamentoRepository {
    Agendamento salvar(Agendamento agendamento);
    Optional<Agendamento> buscarPorId(int id);
    List<Agendamento> listarTodos();
    List<Agendamento> listarPorProfissional(int profissionalId);
    void remover(int id);
}
