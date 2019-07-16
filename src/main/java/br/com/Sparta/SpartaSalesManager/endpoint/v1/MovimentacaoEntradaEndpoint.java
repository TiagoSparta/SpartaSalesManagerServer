package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.exception.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.Entrada;
import br.com.Sparta.SpartaSalesManager.persistence.model.MovimentacaoEntrada;
import br.com.Sparta.SpartaSalesManager.persistence.repository.MovimentacaoEntradaRepository;
import br.com.Sparta.SpartaSalesManager.util.EndpointUtil;
import io.swagger.annotations.ApiOperation;
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
    private final EndpointUtil<MovimentacaoEntrada> endpointUtil;

    @Autowired
    public MovimentacaoEntradaEndpoint(MovimentacaoEntradaRepository movimentacaoEntradaRepository, EndpointUtil<MovimentacaoEntrada> endpointUtil) {
        this.dao = movimentacaoEntradaRepository;
        this.endpointUtil = endpointUtil;
    }

    @GetMapping
    @ApiOperation(value = "Listar todas as Movimentações de Entrada", notes = "Listar todas as Movimentações de Entrada, sem exceções", response = MovimentacaoEntrada.class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "Pesquisar Movimentação de Entrada pelo ID", notes = "Pesquisar Movimentação de Entrada pelo ID indicado na URL", response = MovimentacaoEntrada.class)
    public ResponseEntity<?> getById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(dao.findById(id));
    }

    @GetMapping("findByEntrada/{idEntrada}")
    @ApiOperation(value = "Pesquisar Movimentações de Entrada pela Entrada", notes = "Pesquisar Movimentações de Entrada pelo ID de Entrada inficado na URL", response = MovimentacaoEntrada.class)
    public ResponseEntity<?> findByEntrada(@PathVariable Long idEntrada) {
        Entrada e = new Entrada(idEntrada);
        return endpointUtil.returnObjectOrNotFound(dao.findByEntrada(e));
    }

    @PostMapping
    @ApiOperation(value = "Salvar nova Movimentação de Entrada", notes = "Salvar nova Movimentação Entrada enviada no Body", response = MovimentacaoEntrada.class)
    public ResponseEntity<?> save(@RequestBody MovimentacaoEntrada movimentacaoEntrada) {
        return new ResponseEntity<>(dao.save(movimentacaoEntrada), HttpStatus.CREATED);
    }

    @PostMapping("saveAll")
    @ApiOperation(value = "Salvar novas Movimentações de Entrada", notes = "Salvar novas Movimentações Entrada enviadas no Body", response = MovimentacaoEntrada.class)
    public ResponseEntity<?> saveAll(@RequestBody List<MovimentacaoEntrada> movimentacaoEntradaList) {
        return new ResponseEntity<>(dao.saveAll(movimentacaoEntradaList), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Atualizar Movimentação de Entrada", notes = "Atualizar Movimentação Entrada enviada no Body, desde que preenchido o ID para identificação", response = MovimentacaoEntrada.class)
    public ResponseEntity<?> update(@RequestBody MovimentacaoEntrada movimentacaoEntrada) {
        verifyIfMovimentacaoEntradaExists(movimentacaoEntrada.getId());
        dao.save(movimentacaoEntrada);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletar Movimentação de Entrada", notes = "Deletar Movimentação Entrada a partir do ID indicado na URL")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfMovimentacaoEntradaExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfMovimentacaoEntradaExists(Long id) {
        if (!dao.existsById(id))
            throw new ResourceNotFoundException("Movimentação de Entrada não encontrada para o ID: " + id);
    }
}