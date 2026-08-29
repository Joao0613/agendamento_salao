package com.joao.agendamento.repository;

import com.joao.agendamento.model.Profissional;
import java.util.List;
import java.util.Optional;

public interface ProfissionalRepository {
    Profissional salvar(Profissional profissional);
    Optional<Profissional> buscarPorId(int id);
    List<Profissional> listarTodos();
    void remover(int id);
}
