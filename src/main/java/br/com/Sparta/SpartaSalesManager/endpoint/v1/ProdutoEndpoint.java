package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.error.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.Produto;
import br.com.Sparta.SpartaSalesManager.persistence.repository.ProdutoRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/administrador/produto")
public class ProdutoEndpoint {
    private final ProdutoRepository dao;

    @Autowired
    public ProdutoEndpoint(ProdutoRepository produtoRepository) {
        this.dao = produtoRepository;
    }

    @GetMapping
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("findByNome/{nome}")
    @ApiOperation(value = "Pesquisar produto pelo nome", notes = "Pesquisa produto pelo nome, campo que não pode ter valor repetido", response = Produto.class)
    public ResponseEntity<?> getProdutoByNome (@PathVariable String nome){
        return new ResponseEntity<>(dao.findByNome(nome), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Produto produto) {
        return new ResponseEntity<>(dao.save(produto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Produto produto) {
        verifyIfProdutoExists(produto.getId());
        dao.save(produto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfProdutoExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfProdutoExists(Long id) {
        if (!dao.findById(id).isPresent())
            throw new ResourceNotFoundException("Produto não encontrado para o ID: " + id);
    }
}
