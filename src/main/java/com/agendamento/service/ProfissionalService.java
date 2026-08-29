package com.joao.agendamento.service;

import com.joao.agendamento.model.Profissional;
import com.joao.agendamento.repository.ProfissionalRepository;
import com.joao.agendamento.service.exception.EntidadeNaoEncontradaException;

import java.util.List;

/**
 * SRP: regras de negocio ligadas a Profissional.
 * DIP: depende da abstracao ProfissionalRepository.
 */
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    public Profissional cadastrar(int id, String nome, String especialidade) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do profissional e obrigatorio.");
        }
        Profissional profissional = new Profissional(id, nome, especialidade);
        return profissionalRepository.salvar(profissional);
    }

    public Profissional buscarPorId(int id) throws EntidadeNaoEncontradaException {
        return profissionalRepository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Profissional nao encontrado: id=" + id));
    }

    public List<Profissional> listarTodos() {
        return profissionalRepository.listarTodos();
    }
}
