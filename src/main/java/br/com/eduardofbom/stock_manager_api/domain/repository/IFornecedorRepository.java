package br.com.eduardofbom.stock_manager_api.domain.repository;

import br.com.eduardofbom.stock_manager_api.domain.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFornecedorRepository extends JpaRepository<Fornecedor, Long> {

    Optional<Fornecedor> findByDocumentoFiscal(String documentoFiscal);

}
