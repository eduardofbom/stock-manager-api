package br.com.eduardofbom.stock_manager_api.domain.repository;

import br.com.eduardofbom.stock_manager_api.domain.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoteRepository extends JpaRepository<Lote, Long> {
}
