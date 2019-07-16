package br.com.Sparta.SpartaSalesManager.persistence.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
public class Saida extends AbstractEntity {
    @ApiModelProperty(notes = "Usuário que realizou a operação")
    @ManyToOne(optional = false)
    private ApplicationUser applicationUser;
    @NotEmpty(message = "O tipo de operação deve ser declarado")
    private String tipo;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    public Saida() {
    }

    public Saida(Long id) {
        this.id = id;
    }

    public Saida(ApplicationUser applicationUser, @NotEmpty(message = "O tipo de operação deve ser declarado") String tipo, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        this.applicationUser = applicationUser;
        this.tipo = tipo;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
}
