package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.error.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import br.com.Sparta.SpartaSalesManager.persistence.model.Saida;
import br.com.Sparta.SpartaSalesManager.persistence.repository.SaidaRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/vendedor/saida")
public class SaidaEndpoint {
    private final SaidaRepository dao;

    @Autowired
    public SaidaEndpoint(SaidaRepository entradaRepository) {
        this.dao = entradaRepository;
    }

    @ApiOperation(value = "Retorna todas as Entradas", response = Saida.class)
    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna Saída baseado no id informado", response = Saida.class, notes = "Usuário somente pode pesquisar Saidas próprias")
    @GetMapping(path = "{id}")
    public ResponseEntity<?> getSaidaById(@PathVariable long id) {
        return new ResponseEntity<>(dao.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna Saídas baseado no Usuário logado", response = Saida.class)
    @GetMapping(path = "/serchByUsuarioLogado")
    public ResponseEntity<?> getSaidaByUser(Authentication authentication) {
        ApplicationUser applicationUser = ((ApplicationUser) authentication.getPrincipal());
        return new ResponseEntity<>(dao.findByApplicationUser(applicationUser), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna Saídas baseado no Usuário informado", response = Saida.class)
    @GetMapping(path = "/serchByUsuarioInformado")
    public ResponseEntity<?> getSaidaByInformeUser(ApplicationUser applicationUser) {
        return new ResponseEntity<>(dao.findByApplicationUser(applicationUser), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Saida saida, Authentication authentication) {
        saida.setApplicationUser((ApplicationUser)authentication.getPrincipal());
        return new ResponseEntity<>(dao.save(saida), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Saida saida, Authentication authentication) {
        verifyIfSaidaExists(saida.getId());
        saida.setApplicationUser((ApplicationUser)authentication.getPrincipal());
        dao.save(saida);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfSaidaExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfSaidaExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourceNotFoundException("Saída nao encontrada para o ID: " + id);
    }
}
