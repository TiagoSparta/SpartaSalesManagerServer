package br.com.Sparta.SpartaSalesManager.persistence.repository;

import br.com.Sparta.SpartaSalesManager.persistence.model.ApplicationUser;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);
}
