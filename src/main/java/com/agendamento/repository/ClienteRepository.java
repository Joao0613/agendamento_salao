package com.joao.agendamento.repository;

import com.joao.agendamento.model.Cliente;
import java.util.List;
import java.util.Optional;

/**
 * Abstracao de persistencia de Cliente.
 *
 * DIP (Dependency Inversion Principle): as camadas superiores
 * (service) dependem desta abstracao, e nao de uma implementacao
 * concreta (ex.: banco de dados ou memoria). Isso permite trocar a
 * forma de persistencia (memoria, arquivo, banco de dados) sem
 * alterar as regras de negocio.
 */
public interface ClienteRepository {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(int id);
    List<Cliente> listarTodos();
    void remover(int id);
}
