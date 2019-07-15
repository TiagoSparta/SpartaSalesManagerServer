package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.error.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.Entrada;
import br.com.Sparta.SpartaSalesManager.persistence.model.MovimentacaoEntrada;
import br.com.Sparta.SpartaSalesManager.persistence.repository.MovimentacaoEntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/administrador/entrada/movimentacaoEntrada")
public class MovimentacaoEntradaEndpoint {
    private final MovimentacaoEntradaRepository dao;
        
    @Autowired
    public MovimentacaoEntradaEndpoint(MovimentacaoEntradaRepository movimentacaoEntradaRepository) {
        this.dao = movimentacaoEntradaRepository;
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("findByEntrada/{idEntrada}")
    public ResponseEntity<?> findByEntrada (@PathVariable Long idEntrada) {
        Entrada e = new Entrada();
        e.setId(idEntrada);
        return new ResponseEntity<>(dao.findByEntrada(e), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MovimentacaoEntrada movimentacaoEntrada) {
        return new ResponseEntity<>(dao.save(movimentacaoEntrada), HttpStatus.CREATED);
    }

    @PostMapping("saveAll")
    public ResponseEntity<?> saveAll(@RequestBody List<MovimentacaoEntrada> movimentacaoEntradaList) {
        return new ResponseEntity<>(dao.saveAll(movimentacaoEntradaList), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfMovimentacaoEntradaExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody MovimentacaoEntrada movimentacaoEntrada) {
        verifyIfMovimentacaoEntradaExists(movimentacaoEntrada.getId());
        dao.save(movimentacaoEntrada);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfMovimentacaoEntradaExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourceNotFoundException("Movimentação de entrada não encontrada para o ID: " + id);
    }
}
