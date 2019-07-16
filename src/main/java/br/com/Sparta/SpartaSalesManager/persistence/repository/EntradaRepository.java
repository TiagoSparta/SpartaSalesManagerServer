package br.com.Sparta.SpartaSalesManager.persistence.repository;

import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import br.com.Sparta.SpartaSalesManager.persistence.model.Entrada;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface EntradaRepository extends PagingAndSortingRepository<Entrada, Long> {
    List<Optional<Entrada>> findByApplicationUser(ApplicationUser applicationUser);
}
