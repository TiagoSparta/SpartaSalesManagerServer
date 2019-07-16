package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.exception.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import br.com.Sparta.SpartaSalesManager.persistence.repository.ApplicationUserRepository;
import br.com.Sparta.SpartaSalesManager.util.EndpointUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/administrador/applicationUser")
public class ApplicationUserEndpoint {
    private final ApplicationUserRepository dao;
    private final EndpointUtil<ApplicationUser> endpointUtil;

    @Autowired
    public ApplicationUserEndpoint(ApplicationUserRepository dao, EndpointUtil<ApplicationUser> endpointUtil) {
        this.dao = dao;
        this.endpointUtil = endpointUtil;
    }

    @GetMapping
    @ApiOperation(value = "Listar todos os Usuários", notes = "Listar todos os usuários, sem exceções", response = ApplicationUser.class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "Pesquisar Usuário pelo ID", notes = "Pesquisar Usuário pelo ID indicado na URL", response = ApplicationUser.class)
    public ResponseEntity<?> getById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(dao.findById(id));
    }

    @GetMapping(path = "findByUsername/{name}")
    @ApiOperation(value = "Pesquisar Usuários pelo nome", notes = "Pesquisar usuários pelo nome indicado na URL", response = ApplicationUser.class)
    public ResponseEntity<?> getByApplicationUserByUsername(@PathVariable String username) {
        return endpointUtil.returnObjectOrNotFound(dao.findByUsername(username));
    }

    @PostMapping
    @ApiOperation(value = "Gravar novo Usuário", notes = "Gravar novo Usuário", response = ApplicationUser.class)
    public ResponseEntity<?> save(@Valid @RequestBody ApplicationUser applicationUser) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        return new ResponseEntity<>(dao.save(applicationUser), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Atualizar Usuário", notes = "Atualizar Usuário enviado no Body, desde que preenchido o ID para identificação", response = ApplicationUser.class)
    public ResponseEntity<?> update(@RequestBody ApplicationUser applicationUser) {
        verifyIfApplicationUserExists(applicationUser.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        dao.save(applicationUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletar Usuário", notes = "Deletar Usuário a partir do ID indicado na URL")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfApplicationUserExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfApplicationUserExists(Long id) {
        if (!dao.existsById(id))
            throw new ResourceNotFoundException("Usuário não encontrado para o ID: " + id);
    }
}
