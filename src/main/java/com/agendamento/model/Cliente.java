package com.joao.agendamento.model;

import java.util.Objects;

/**
 * Representa um cliente do salao.
 *
 * Aplicacao do SRP: esta classe tem a unica responsabilidade de
 * representar os dados de um cliente (entidade de dominio).
 * Nao contem regras de negocio, nem acesso a dados, nem logica de UI.
 */
public class Cliente {

    private final int id;
    private String nome;
    private String telefone;
    private String email;
    private boolean clienteFiel;

    public Cliente(int id, String nome, String telefone, String email, boolean clienteFiel) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.clienteFiel = clienteFiel;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isClienteFiel() {
        return clienteFiel;
    }

    public void setClienteFiel(boolean clienteFiel) {
        this.clienteFiel = clienteFiel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return id == cliente.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' + ", clienteFiel=" + clienteFiel + '}';
    }
}
