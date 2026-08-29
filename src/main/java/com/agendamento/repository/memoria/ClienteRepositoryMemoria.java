package com.joao.agendamento.repository.memoria;

import com.joao.agendamento.model.Cliente;
import com.joao.agendamento.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementacao de ClienteRepository usando uma estrutura em memoria
 * (simula uma fonte de dados). SRP: unica responsabilidade e
 * armazenar/recuperar objetos Cliente.
 */
public class ClienteRepositoryMemoria implements ClienteRepository {

    private final Map<Integer, Cliente> dados = new LinkedHashMap<>();

    @Override
    public Cliente salvar(Cliente cliente) {
        dados.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> buscarPorId(int id) {
        return Optional.ofNullable(dados.get(id));
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(dados.values());
    }

    @Override
    public void remover(int id) {
        dados.remove(id);
    }
}
