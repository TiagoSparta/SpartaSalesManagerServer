package br.com.Sparta.SpartaSalesManager.persistence.repository;

import br.com.Sparta.SpartaSalesManager.persistence.model.MovimentacaoSaida;
import br.com.Sparta.SpartaSalesManager.persistence.model.Saida;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoSaidaRepository extends PagingAndSortingRepository<MovimentacaoSaida, Long> {
    List<Optional<MovimentacaoSaida>> findBySaida(Saida saida);
}
