package br.com.Sparta.SpartaSalesManager.persistence.repository;

import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import br.com.Sparta.SpartaSalesManager.persistence.model.Saida;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SaidaRepository extends PagingAndSortingRepository<Saida, Long> {
    List<Saida> findByApplicationUser(ApplicationUser applicationUser);
}
