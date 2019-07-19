package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.exception.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.MovimentacaoSaida;
import br.com.Sparta.SpartaSalesManager.persistence.model.Saida;
import br.com.Sparta.SpartaSalesManager.persistence.repository.MovimentacaoSaidaRepository;
import br.com.Sparta.SpartaSalesManager.util.EndpointUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/vendedor/saida/movimentacaoSaida")
public class MovimentacaoSaidaEndpoint {
    private final MovimentacaoSaidaRepository dao;
    private final EndpointUtil<MovimentacaoSaida> endpointUtil;

    @Autowired
    public MovimentacaoSaidaEndpoint(MovimentacaoSaidaRepository movimentacaoSaidaRepository, EndpointUtil<MovimentacaoSaida> endpointUtil) {
        this.dao = movimentacaoSaidaRepository;
        this.endpointUtil = endpointUtil;
    }

    @GetMapping
    @ApiOperation(value = "Listar todas as Movimentações de Saída", notes = "Listar todas as Movimentações de Saída, sem exceções", response = MovimentacaoSaida.class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "Pesquisar Movimentação de Saída pelo ID", notes = "Pesquisar Movimentação de Saída pelo ID indicado na URL", response = MovimentacaoSaida.class)
    public ResponseEntity<?> getById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(dao.findById(id));
    }

    @GetMapping("findBySaida/{idSaida}")
    @ApiOperation(value = "Pesquisar Movimentações de Saída pela Saída", notes = "Pesquisar Movimentações de Saída pelo ID de Saída inficado na URL", response = MovimentacaoSaida.class)
    public ResponseEntity<?> getBySaida (@PathVariable Long idSaida) {
        Saida s = new Saida(idSaida);
        return endpointUtil.returnObjectOrNotFound(dao.findBySaida(s));
    }

    @PostMapping
    @ApiOperation(value = "Gravar nova Movimentação de Saída", notes = "Gravar nova Movimentação de Saída enviada no Body", response = MovimentacaoSaida.class)
    public ResponseEntity<?> save(@Valid @RequestBody MovimentacaoSaida movimentacaoSaida) {
        return new ResponseEntity<>(dao.save(movimentacaoSaida), HttpStatus.CREATED);
    }

    @PostMapping("saveAll")
    @ApiOperation(value = "Salvar novas Movimentações de Saída", notes = "Salvar novas Movimentações Saída enviadas no Body", response = MovimentacaoSaida.class)
    public ResponseEntity<?> saveAll(@RequestBody List<MovimentacaoSaida> movimentacaoEntradaList) {
        return new ResponseEntity<>(dao.saveAll(movimentacaoEntradaList), HttpStatus.CREATED);
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

    @DeleteMapping("deleteAll")
    @ApiOperation(value = "Deletar lista de Movimentações de Saáda", notes = "Deletar lista de Movimentações Saída enviadas no Body", response = MovimentacaoSaida.class)
    public ResponseEntity<?> deleteAll(@RequestBody List<MovimentacaoSaida> movimentacaosaidaList) {
        dao.deleteAll(movimentacaosaidaList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfMovimentacaoSaidaExists(Long id) {
        if (!dao.existsById(id))
            throw new ResourceNotFoundException("Movimentação de Saída não encontrada para o ID: " + id);
    }
}