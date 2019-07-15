package br.com.Sparta.SpartaSalesManager.persistence.repository;

import br.com.Sparta.SpartaSalesManager.persistence.model.Administrador;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdministradorRepository extends PagingAndSortingRepository<Administrador, Long> {
    Administrador findByCpf(String cpf);
}
