package br.com.eduardofbom.stock_manager_api.domain.service;

import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoRequestDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoResponseDTO;
import br.com.eduardofbom.stock_manager_api.domain.model.Categoria;
import br.com.eduardofbom.stock_manager_api.domain.model.Produto;
import br.com.eduardofbom.stock_manager_api.domain.repository.ICategoriaRepository;
import br.com.eduardofbom.stock_manager_api.domain.repository.IProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final IProdutoRepository produtoRepository;
    private final ICategoriaRepository categoriaRepository;

    public ProdutoService(IProdutoRepository produtoRepository, ICategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.buscarTodosComCategoria()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
    }

    @Transactional
    public ProdutoResponseDTO criar(ProdutoRequestDTO produtoRequestDTO) {
        Categoria categoria = categoriaRepository.findById(produtoRequestDTO.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Não é possível cadastrar o produto. Categoria não encontrada com o ID: "
                        + produtoRequestDTO.categoriaId()
                ));

        Produto novoProduto = new Produto(
                produtoRequestDTO.nome(),
                categoria,
                produtoRequestDTO.unidMedida(),
                produtoRequestDTO.precoVenda()
        );
        novoProduto.setCodBarras(produtoRequestDTO.codBarras());
        novoProduto.setEstoqMin(produtoRequestDTO.estoqMin());

        Produto produtoSalvo = produtoRepository.save(novoProduto);

        return new ProdutoResponseDTO(produtoSalvo);
    }
}
