package com.joao.agendamento.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa um servico oferecido pelo salao (corte, manicure, coloracao...).
 * SRP: apenas dados da entidade.
 */
public class Servico {

    private final int id;
    private String nome;
    private int duracaoMinutos;
    private BigDecimal preco;

    public Servico(int id, String nome, int duracaoMinutos, BigDecimal preco) {
        this.id = id;
        this.nome = nome;
        this.duracaoMinutos = duracaoMinutos;
        this.preco = preco;
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

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Servico)) return false;
        Servico servico = (Servico) o;
        return id == servico.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Servico{" + "id=" + id + ", nome='" + nome + '\'' +
                ", duracaoMinutos=" + duracaoMinutos + ", preco=" + preco + '}';
    }
}
