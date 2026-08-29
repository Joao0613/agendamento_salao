package com.joao.agendamento.repository;

import com.joao.agendamento.model.Servico;
import java.util.List;
import java.util.Optional;

public interface ServicoRepository {
    Servico salvar(Servico servico);
    Optional<Servico> buscarPorId(int id);
    List<Servico> listarTodos();
    void remover(int id);
}
