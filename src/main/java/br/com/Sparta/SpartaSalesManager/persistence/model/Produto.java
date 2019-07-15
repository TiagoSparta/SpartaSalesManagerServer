package br.com.Sparta.SpartaSalesManager.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
public class Produto extends AbstractEntity {
    @NotEmpty(message = "O campo Nome do produto não pode estar vazio")
    @Column(unique = true)
    private String nome;
    @NotEmpty(message = "O campo Preço de Venda não pode estar vazio")
    private Double precoVenda;
    private Double precoCustoMedio;
    private int estoqueAtual;
    @NotEmpty(message = "O campo Ativo não pode estar vazio")
    private boolean ativo;

    public Produto() {
    }

    public Produto(@NotEmpty(message = "O campo Nome do produto não pode estar vazio") String nome, @NotEmpty(message = "O campo Preço de Venda não pode estar vazio") Double precoVenda, Double precoCustoMedio, int estoqueAtual, @NotEmpty(message = "O campo Ativo não pode estar vazio") boolean ativo) {
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.precoCustoMedio = precoCustoMedio;
        this.estoqueAtual = estoqueAtual;
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Double getPrecoCustoMedio() {
        return precoCustoMedio;
    }

    public void setPrecoCustoMedio(Double precoCustoMedio) {
        this.precoCustoMedio = precoCustoMedio;
    }

    public int getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(int estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
