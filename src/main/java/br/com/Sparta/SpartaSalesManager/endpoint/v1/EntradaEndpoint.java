package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.exception.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import br.com.Sparta.SpartaSalesManager.persistence.model.Entrada;
import br.com.Sparta.SpartaSalesManager.persistence.repository.EntradaRepository;
import br.com.Sparta.SpartaSalesManager.util.EndpointUtil;
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
    private final EndpointUtil<Entrada> endpointUtil;

    @Autowired
    public EntradaEndpoint(EntradaRepository entradaRepository, EndpointUtil endpointUtil, EndpointUtil<Entrada> endpointUtil1) {
        this.dao = entradaRepository;
        this.endpointUtil = endpointUtil1;
    }

    @GetMapping
    @ApiOperation(value = "Listar todas as Entradas", notes = "Listar todas as Entradas, sem exceções", response = Entrada.class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "Pesquisar Entrada pelo ID", notes = "Pesquisar Entrada pelo ID indicado na URL", response = Entrada.class)
    public ResponseEntity<?> getById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(dao.findById(id));
    }

    @GetMapping(path = "serchByUsuarioLogado")
    @ApiOperation(value = "Pesquisar Entradas pelo usuário logado", notes = "Pesquisar todas as Entradas a partir do Usuário logado", response = Entrada.class)
    public ResponseEntity<?> getEntradaByUsuarioLogado(Authentication authentication) {
        ApplicationUser applicationUser = ((ApplicationUser) authentication.getPrincipal());
        return endpointUtil.returnObjectOrNotFound(dao.findByApplicationUser(applicationUser));
    }

    @GetMapping(path = "serchByUsuarioInformado")
    @ApiOperation(value = "Pesquisar Entradas pelo usuário informado", notes = "Pesquisar todas as Entradas a partido do usuário informado no Body", response = Entrada.class)
    public ResponseEntity<?> getEntradaByInformedUser(@RequestBody ApplicationUser applicationUser) {
        return endpointUtil.returnObjectOrNotFound(dao.findByApplicationUser(applicationUser));
    }

    @PostMapping
    @ApiOperation(value = "Salvar nova Entrada", notes = "Salvar nova Entrada", response = Entrada.class)
    public ResponseEntity<?> save(@RequestBody Entrada entrada, Authentication authentication) {
        entrada.setApplicationUser((ApplicationUser) authentication.getPrincipal());
        return new ResponseEntity<>(dao.save(entrada), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Atualizar Entrada", notes = "Atualizar Entrada enviado no Body, desde que preenchido o ID para identificação", response = Entrada.class)
    public ResponseEntity<?> update(@RequestBody Entrada entrada, Authentication authentication) {
        verifyIfEntradaExists(entrada.getId());
        entrada.setApplicationUser((ApplicationUser) authentication.getPrincipal());
        dao.save(entrada);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletar Entrada", notes = "Deletar Entrada a partir do ID indicado na URL")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfEntradaExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfEntradaExists(Long id) {
        if (!dao.existsById(id))
            throw new ResourceNotFoundException("Entrada não encontrada para o ID: " + id);
    }
}
