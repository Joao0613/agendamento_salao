package com.joao.agendamento.model;

import java.util.Objects;

/**
 * Representa um profissional do salao (cabeleireiro, manicure, etc).
 * SRP: apenas dados da entidade, sem regras de negocio.
 */
public class Profissional {

    private final int id;
    private String nome;
    private String especialidade;

    public Profissional(int id, String nome, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profissional)) return false;
        Profissional that = (Profissional) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Profissional{" + "id=" + id + ", nome='" + nome + '\'' +
                ", especialidade='" + especialidade + '\'' + '}';
    }
}
