package com.joao.agendamento.repository.memoria;

import com.joao.agendamento.model.Profissional;
import com.joao.agendamento.repository.ProfissionalRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProfissionalRepositoryMemoria implements ProfissionalRepository {

    private final Map<Integer, Profissional> dados = new LinkedHashMap<>();

    @Override
    public Profissional salvar(Profissional profissional) {
        dados.put(profissional.getId(), profissional);
        return profissional;
    }

    @Override
    public Optional<Profissional> buscarPorId(int id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<Profissional> listarTodos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public void remover(int id) {
        dados.remove(id);
    }
}
