package com.joao.agendamento.repository.memoria;

import com.joao.agendamento.model.Agendamento;
import com.joao.agendamento.repository.AgendamentoRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AgendamentoRepositoryMemoria implements AgendamentoRepository {

    private final Map<Integer, Agendamento> dados = new LinkedHashMap<>();

    @Override
    public Agendamento salvar(Agendamento agendamento) {
        dados.put(agendamento.getId(), agendamento);
        return agendamento;
    }

    @Override
    public Optional<Agendamento> buscarPorId(int id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<Agendamento> listarTodos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public List<Agendamento> listarPorProfissional(int profissionalId) {
        return dados.values().stream()
                .filter(a -> a.getProfissional().getId() == profissionalId)
                .collect(Collectors.toList());
    }

    @Override
    public void remover(int id) {
        dados.remove(id);
    }
}
