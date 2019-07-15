package br.com.Sparta.SpartaSalesManager.persistence.model;

import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
public class Administrador extends AbstractEntity {
    @NotEmpty(message = "O campo nome do administrador não pode estar vazio")
    @Column(unique = true)
    private String nome;

    @NotEmpty(message = "O campo cpf não pode estar vazio")
    @CPF(message = "CPF inválido")
    private String cpf;

    public Administrador() {
    }

    public Administrador(@NotEmpty(message = "O campo nome não pode estar vazio") String nome, @NotEmpty(message = "O campo cpf não pode estar vazio") @org.hibernate.validator.constraints.br.CPF(message = "cpf inválido") String CPF) {
        this.nome = nome;
        this.cpf = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
