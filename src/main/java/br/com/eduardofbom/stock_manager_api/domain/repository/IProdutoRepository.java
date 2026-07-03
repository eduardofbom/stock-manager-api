package br.com.eduardofbom.stock_manager_api.domain.repository;

import br.com.eduardofbom.stock_manager_api.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProdutoRepository extends JpaRepository<Produto, Long> {
    @Query("SELECT p FROM Produto p JOIN FETCH p.categoria WHERE p.ativo = true")
    List<Produto> buscarTodosComCategoria();
}