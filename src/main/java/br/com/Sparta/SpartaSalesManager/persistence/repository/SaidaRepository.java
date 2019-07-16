package br.com.Sparta.SpartaSalesManager.persistence.repository;

import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import br.com.Sparta.SpartaSalesManager.persistence.model.Saida;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface SaidaRepository extends PagingAndSortingRepository<Saida, Long> {
    List<Optional<Saida>> findByApplicationUser(ApplicationUser applicationUser);
}
