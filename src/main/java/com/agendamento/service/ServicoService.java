package com.joao.agendamento.service;

import com.joao.agendamento.model.Servico;
import com.joao.agendamento.repository.ServicoRepository;
import com.joao.agendamento.service.exception.EntidadeNaoEncontradaException;

import java.math.BigDecimal;
import java.util.List;

/**
 * SRP: regras de negocio ligadas a Servico (o servico oferecido pelo
 * salao, ex.: corte, manicure). Nao confundir com "camada de service"
 * do projeto.
 * DIP: depende da abstracao ServicoRepository.
 */
public class ServicoService {

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public Servico cadastrar(int id, String nome, int duracaoMinutos, BigDecimal preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do servico e obrigatorio.");
        }
        if (duracaoMinutos <= 0) {
            throw new IllegalArgumentException("Duracao do servico deve ser maior que zero.");
        }
        if (preco == null || preco.signum() < 0) {
            throw new IllegalArgumentException("Preco do servico invalido.");
        }
        Servico servico = new Servico(id, nome, duracaoMinutos, preco);
        return servicoRepository.salvar(servico);
    }

    public Servico buscarPorId(int id) throws EntidadeNaoEncontradaException {
        return servicoRepository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Servico nao encontrado: id=" + id));
    }

    public List<Servico> listarTodos() {
        return servicoRepository.listarTodos();
    }
}
