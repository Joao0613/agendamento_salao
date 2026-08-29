package com.joao.agendamento.service;

import com.joao.agendamento.model.Cliente;
import com.joao.agendamento.repository.ClienteRepository;
import com.joao.agendamento.service.exception.EntidadeNaoEncontradaException;

import java.util.List;

/**
 * SRP: responsavel apenas pelas regras de negocio ligadas a Cliente
 * (validacao de cadastro), delegando persistencia ao ClienteRepository.
 *
 * DIP: depende da abstracao ClienteRepository (interface), recebida
 * via construtor, e nao de uma implementacao concreta. Isso permite
 * testar esta classe com um repositorio falso (mock) e trocar a forma
 * de persistencia sem alterar este codigo.
 */
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrar(int id, String nome, String telefone, String email, boolean clienteFiel) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do cliente e obrigatorio.");
        }
        Cliente cliente = new Cliente(id, nome, telefone, email, clienteFiel);
        return clienteRepository.salvar(cliente);
    }

    public Cliente buscarPorId(int id) throws EntidadeNaoEncontradaException {
        return clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente nao encontrado: id=" + id));
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }

    public void remover(int id) {
        clienteRepository.remover(id);
    }
}
