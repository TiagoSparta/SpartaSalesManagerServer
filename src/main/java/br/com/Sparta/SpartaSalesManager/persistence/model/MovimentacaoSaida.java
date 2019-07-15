package br.com.Sparta.SpartaSalesManager.persistence.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class MovimentacaoSaida extends AbstractEntity {
    @NotEmpty
    @ManyToOne
    private Saida saida;
    @ManyToOne
    private Produto produto;
    private float valorUnitarioAtual;
    private int quantidade;

    public MovimentacaoSaida() {
    }

    public MovimentacaoSaida(@NotEmpty Saida saida, Produto produto, float valorUnitarioAtual, int quantidade) {
        this.saida = saida;
        this.produto = produto;
        this.valorUnitarioAtual = valorUnitarioAtual;
        this.quantidade = quantidade;
    }

    public Saida getSaida() {
        return saida;
    }

    public void setSaida(Saida saida) {
        this.saida = saida;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public float getValorUnitarioAtual() {
        return valorUnitarioAtual;
    }

    public void setValorUnitarioAtual(float valorUnitarioAtual) {
        this.valorUnitarioAtual = valorUnitarioAtual;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
