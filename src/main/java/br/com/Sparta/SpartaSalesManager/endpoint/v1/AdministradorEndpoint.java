package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.error.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.Administrador;
import br.com.Sparta.SpartaSalesManager.persistence.repository.AdministradorRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/administrador")
public class AdministradorEndpoint {

    private final AdministradorRepository dao;

    @Autowired
    public AdministradorEndpoint(AdministradorRepository administradorRepository) {
        this.dao = administradorRepository;
    }

    @GetMapping
    @ApiOperation(value = "Listar todos os Administradores", notes = "Lista todos os administradores, sem exceções", response = Administrador.class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("findByCPF/{CPF}")
    @ApiOperation(value = "Pesquisar administrador pelo CPF", notes = "Pesquisa Administrador específico digitando o CPF na URL", response = Administrador.class)
    public ResponseEntity<?> getAdministradorByCPF(@PathVariable String CPF) {
        return new ResponseEntity<>(dao.findByCpf(CPF), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Gravar novo Administrador", notes = "Gravar novo Administrador", response = Administrador.class)
    public ResponseEntity<?> save(@Valid @RequestBody Administrador administrador) {
        return new ResponseEntity<>(dao.save(administrador), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Atualizar Administrador", notes = "Atualizar Administrador enviado no Body, desde que preenchido o ID para identificação", response = Administrador.class)
    public ResponseEntity<?> update(@RequestBody Administrador administrador) {
        verifyIfAdministradorExists(administrador.getId());
        dao.save(administrador);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletar Administrador", notes = "Deletar Administrador a partir do ID indicado na URL")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfAdministradorExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfAdministradorExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourceNotFoundException("ApplicationUser not found for ID: " + id);
    }
}
