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

    @GetMapping
    @ApiOperation(value = "Listar todas as Saídas", notes = "Listar todas as Saídas, sem exceções", response = Saida.class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "Pequisar Saída pelo ID", notes = "Pesquisar Saída pelo ID indicado na URL", response = Saida.class)
    public ResponseEntity<?> getSaidaById(@PathVariable long id) {
        return new ResponseEntity<>(dao.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/serchByUsuarioLogado")
    @ApiOperation(value = "Pequisar Saídas pelo usuário logado", notes = "Pesquisar Saídas pelo usuário logado", response = Saida.class)
    public ResponseEntity<?> getSaidaByUser(Authentication authentication) {
        ApplicationUser applicationUser = ((ApplicationUser) authentication.getPrincipal());
        return new ResponseEntity<>(dao.findByApplicationUser(applicationUser), HttpStatus.OK);
    }

    @GetMapping(path = "/serchByUsuarioInformado")
    @ApiOperation(value = "Pequisar Saídas pelo usuário informado", notes = "Pesquisar Saídas pelo usuário informado", response = Saida.class)
    public ResponseEntity<?> getSaidaByInformeUser(ApplicationUser applicationUser) {
        return new ResponseEntity<>(dao.findByApplicationUser(applicationUser), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Salvar nova Saída", notes = "Salvar nova Saída enviado no Body", response = Saida.class)
    public ResponseEntity<?> save(@RequestBody Saida saida, Authentication authentication) {
        saida.setApplicationUser((ApplicationUser)authentication.getPrincipal());
        return new ResponseEntity<>(dao.save(saida), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Atualizar Saída", notes = "Atualizar Saída enviada no Body, desde que preenchido o ID para identificação", response = Saida.class)
    public ResponseEntity<?> update(@RequestBody Saida saida, Authentication authentication) {
        verifyIfSaidaExists(saida.getId());
        saida.setApplicationUser((ApplicationUser)authentication.getPrincipal());
        dao.save(saida);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletar Saída", notes = "Deletar Saída a partir do ID indicado na URL")
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
