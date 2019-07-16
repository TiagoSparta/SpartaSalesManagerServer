package br.com.Sparta.SpartaSalesManager.persistence.repository;

import br.com.Sparta.SpartaSalesManager.persistence.model.Entrada;
import br.com.Sparta.SpartaSalesManager.persistence.model.MovimentacaoEntrada;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoEntradaRepository extends PagingAndSortingRepository<MovimentacaoEntrada, Long> {
    List<Optional<MovimentacaoEntrada>> findByEntrada(Entrada entrada);
}
