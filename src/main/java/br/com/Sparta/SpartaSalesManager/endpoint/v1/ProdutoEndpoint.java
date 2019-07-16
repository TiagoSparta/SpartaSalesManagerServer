package br.com.Sparta.SpartaSalesManager.endpoint.v1;

import br.com.Sparta.SpartaSalesManager.exception.ResourceNotFoundException;
import br.com.Sparta.SpartaSalesManager.persistence.model.Produto;
import br.com.Sparta.SpartaSalesManager.persistence.repository.ProdutoRepository;
import br.com.Sparta.SpartaSalesManager.util.EndpointUtil;
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
    private final EndpointUtil<Produto> endpointUtil;

    @Autowired
    public ProdutoEndpoint(ProdutoRepository produtoRepository, EndpointUtil<Produto> endpointUtil) {
        this.dao = produtoRepository;
        this.endpointUtil = endpointUtil;
    }

    @GetMapping
    @ApiOperation(value = "Listar todos os Produtos", notes = "Listar todos os Produtos, sem exceções", response = Produto.class)
    public ResponseEntity<?> listAll(Pageable pageable) {
        return new ResponseEntity<>(dao.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "Pesquisar Produto pelo ID", notes = "Pesquisar Produto pelo ID indicado na URL", response = Produto.class)
    public ResponseEntity<?> getById(@PathVariable long id) {
        return endpointUtil.returnObjectOrNotFound(dao.findById(id));
    }

    @GetMapping("findByNome/{nome}")
    @ApiOperation(value = "Pesquisar Produto pelo nome", notes = "Pesquisar Produto pelo nome indicado na URL", response = Produto.class)
    public ResponseEntity<?> getProdutoByNome (@PathVariable String nome){
        return endpointUtil.returnObjectOrNotFound(dao.findByNome(nome));
    }

    @PostMapping
    @ApiOperation(value = "Salvar novo Produto", notes = "Salvar novo Produto enviado no Body", response = Produto.class)
    public ResponseEntity<?> save(@RequestBody Produto produto) {
        return new ResponseEntity<>(dao.save(produto), HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation(value = "Atualizar Produto", notes = "Atualizar Produto enviado no Body, desde que preenchido o ID para identificação", response = Produto.class)
    public ResponseEntity<?> update(@RequestBody Produto produto) {
        verifyIfProdutoExists(produto.getId());
        dao.save(produto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "Deletar Produto", notes = "Deletar Produto a partir do ID indicado na URL")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        verifyIfProdutoExists(id);
        dao.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void verifyIfProdutoExists(Long id) {
        if (!dao.existsById(id))
            throw new ResourceNotFoundException("Produto não encontrado para o ID: " + id);
    }
}
