package br.com.eduardofbom.stock_manager_api.domain.repository;

import br.com.eduardofbom.stock_manager_api.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long> {

    boolean existsByNomeIgnoreCase(String nome);

}
