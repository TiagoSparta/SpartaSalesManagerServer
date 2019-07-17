package br.com.Sparta.SpartaSalesManager.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Entity
public class ApplicationUser extends AbstractEntity {
    @NotEmpty(message = "O campo username não pode ser vazio")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "O campo password não pode ser vazio")
    private String password;

    @OneToOne
    private Administrador administrador;

    public ApplicationUser() {
    }

    public ApplicationUser(ApplicationUser applicationUser) {
        this.username = applicationUser.username;
        this.password = applicationUser.password;
        this.administrador = applicationUser.administrador;
    }

    public ApplicationUser(Optional<ApplicationUser> applicationUser) {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}
