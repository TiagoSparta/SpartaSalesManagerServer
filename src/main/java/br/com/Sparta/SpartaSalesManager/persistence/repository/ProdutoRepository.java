package br.com.Sparta.SpartaSalesManager.persistence.repository;

import br.com.Sparta.SpartaSalesManager.persistence.model.Produto;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long> {
    Optional<Produto> findByNome(String nome);
}
