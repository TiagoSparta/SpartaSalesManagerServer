package br.com.Sparta.SpartaSalesManager.persistence.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class MovimentacaoEntrada extends AbstractEntity {
    @NotNull(message = "O campo Entrada não pode estar vazio")
    @ManyToOne
    private Entrada entrada;
    @ManyToOne
    private Produto produto;
    private float valorUnitarioAtual;
    private int quantidade;

    public MovimentacaoEntrada() {
    }

    public MovimentacaoEntrada(@NotNull(message = "O campo Entrada não pode estar vazio") Entrada entrada, Produto produto, float valorUnitarioAtual, int quantidade) {
        this.entrada = entrada;
        this.produto = produto;
        this.valorUnitarioAtual = valorUnitarioAtual;
        this.quantidade = quantidade;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
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
