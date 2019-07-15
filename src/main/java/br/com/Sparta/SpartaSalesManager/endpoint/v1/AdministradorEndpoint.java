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
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("findByCPF/{CPF}")
    @ApiOperation(value = "Pesquisar administrador pelo CPF", notes = "Exemplo de notas", response = Administrador.class)
    public ResponseEntity<?> getAdministradorByCPF (@PathVariable String CPF){
        return new ResponseEntity<>(dao.findByCpf(CPF), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Administrador administrador) {
        return new ResponseEntity<>(dao.save(administrador), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfAdministradorExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Administrador administrador) {
        verifyIfAdministradorExists(administrador.getId());
        dao.save(administrador);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfAdministradorExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourceNotFoundException("ApplicationUser not found for ID: " + id);
    }
}
