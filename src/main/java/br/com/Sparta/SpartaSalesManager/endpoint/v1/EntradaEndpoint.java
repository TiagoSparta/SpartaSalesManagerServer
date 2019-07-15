package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.error.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import br.com.Sparta.SpartaSalesManager.persistence.model.Entrada;
import br.com.Sparta.SpartaSalesManager.persistence.repository.EntradaRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/administrador/entrada")
public class EntradaEndpoint {
    private final EntradaRepository dao;

    @Autowired
    public EntradaEndpoint(EntradaRepository entradaRepository) {
        this.dao = entradaRepository;
    }

    @ApiOperation(value = "Retorna todas as Entradas", response = Entrada.class)
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna entrada baseado no id informado", response = Entrada.class)
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getEntradaById(@PathVariable long id) {
        return new ResponseEntity<>(dao.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna Entradas baseado no Usuário logado", response = Entrada.class)
    @GetMapping(path = "/serchByUsuarioLogado")
    public ResponseEntity<?> getEntradaByUsuarioLogado(Authentication authentication) {
        ApplicationUser applicationUser = ((ApplicationUser) authentication.getPrincipal());
        return new ResponseEntity<>(dao.findByApplicationUser(applicationUser), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna Entradas baseado no Usuário informado", response = Entrada.class)
    @GetMapping(path = "/serchByUsuarioInformado")
    public ResponseEntity<?> getEntradaByInformedUser(ApplicationUser applicationUser) {
        return new ResponseEntity<>(dao.findByApplicationUser(applicationUser), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Entrada entrada, Authentication authentication) {
        entrada.setApplicationUser((ApplicationUser) authentication.getPrincipal());
        return new ResponseEntity<>(dao.save(entrada), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfEntradaExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Entrada entrada, Authentication authentication) {
        verifyIfEntradaExists(entrada.getId());
        entrada.setApplicationUser((ApplicationUser) authentication.getPrincipal());
        dao.save(entrada);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfEntradaExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourceNotFoundException("Entrada nao encontrada para o ID: " + id);
    }
}
