package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.error.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.MovimentacaoSaida;
import br.com.Sparta.SpartaSalesManager.persistence.repository.MovimentacaoSaidaRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/vendedor/saida/movimentacaoSaida")
public class MovimentacaoSaidaEndpoint {
    private final MovimentacaoSaidaRepository dao;

    @Autowired
    public MovimentacaoSaidaEndpoint(MovimentacaoSaidaRepository movimentacaoSaidaRepository) {
        this.dao = movimentacaoSaidaRepository;
    }

    @GetMapping
    @ApiOperation(value = "Listar todas as Movimentações de Saída", notes = "Listar todas as Movimentações de Saída, sem exceções", response = MovimentacaoSaida.class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Gravar nova Movimentação de Saída", notes = "Gravar nova Movimentação de Saída enviada no Body", response = MovimentacaoSaida.class)
    public ResponseEntity<?> save(@Valid @RequestBody MovimentacaoSaida movimentacaoSaida) {
        return new ResponseEntity<>(dao.save(movimentacaoSaida), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Atualizar Movimentação de Saída", notes = "Atualizar Movimentação Saída enviada no Body, desde que preenchido o ID para identificação", response = MovimentacaoSaida.class)
    public ResponseEntity<?> update(@RequestBody MovimentacaoSaida movimentacaoSaida) {
        verifyIfMovimentacaoSaidaExists(movimentacaoSaida.getId());
        dao.save(movimentacaoSaida);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletar Movimentação de Saída", notes = "Deletar Movimentação Saída a partir do ID indicado na URL")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfMovimentacaoSaidaExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfMovimentacaoSaidaExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourceNotFoundException("Movimentação de saida não encontrada para o ID: " + id);
    }
}
