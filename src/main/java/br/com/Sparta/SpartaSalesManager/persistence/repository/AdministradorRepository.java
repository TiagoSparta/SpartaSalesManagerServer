package br.com.Sparta.SpartaSalesManager.persistence.repository;

import br.com.Sparta.SpartaSalesManager.persistence.model.Administrador;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AdministradorRepository extends PagingAndSortingRepository<Administrador, Long> {
    Optional<Administrador> findByCpf(String cpf);
}
