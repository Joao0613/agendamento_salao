package com.joao.agendamento.repository.memoria;

import com.joao.agendamento.model.Servico;
import com.joao.agendamento.repository.ServicoRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ServicoRepositoryMemoria implements ServicoRepository {

    private final Map<Integer, Servico> dados = new LinkedHashMap<>();

    @Override
    public Servico salvar(Servico servico) {
        dados.put(servico.getId(), servico);
        return servico;
    }

    @Override
    public Optional<Servico> buscarPorId(int id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<Servico> listarTodos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public void remover(int id) {
        dados.remove(id);
    }
}
