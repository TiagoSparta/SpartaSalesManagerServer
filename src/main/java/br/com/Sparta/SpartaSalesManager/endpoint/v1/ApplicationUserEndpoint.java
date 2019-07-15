package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.error.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import br.com.Sparta.SpartaSalesManager.persistence.repository.ApplicationUserRepository;
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

    @Autowired
    public ApplicationUserEndpoint(ApplicationUserRepository dao) {
        this.dao = dao;
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "findByUsername/{name}")
    public ResponseEntity<?> findApplicationUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(dao.findByUsername(username), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ApplicationUser applicationUser) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        return new ResponseEntity<>(dao.save(applicationUser), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfApplicationUserExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ApplicationUser applicationUser) {
        verifyIfApplicationUserExists(applicationUser.getId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        dao.save(applicationUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfApplicationUserExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourceNotFoundException("ApplicationUser not found for ID: " + id);
    }
}
