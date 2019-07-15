package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.error.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import br.com.Sparta.SpartaSalesManager.persistence.repository.ApplicationUserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/administrador/applicationUser")
public class ApplicationUserEndpoint {
    private final ApplicationUserRepository dao;

    @Autowired
    public ApplicationUserEndpoint(ApplicationUserRepository dao) {
        this.dao = dao;
    }

    @GetMapping
    @ApiOperation(value = "Listar todos os Usuários", notes = "Listar todos os usuários, sem exceções", response = ApplicationUser.class)
    public ResponseEntity<?> listAll() {
        return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "findByUsername/{name}")
    @ApiOperation(value = "Pesquisar Usuários pelo nome", notes = "Pesquisar usuários pelo nome indicado na URL", response = ApplicationUser.class)
    public ResponseEntity<?> findApplicationUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(dao.findByUsername(username), HttpStatus.OK);
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
        if (!dao.findById(id).isPresent())
            throw new ResourceNotFoundException("ApplicationUser not found for ID: " + id);
    }
}
