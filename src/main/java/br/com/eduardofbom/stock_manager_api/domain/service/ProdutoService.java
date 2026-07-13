package br.com.eduardofbom.stock_manager_api.domain.service;

import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoRequestDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoResponseDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoUpdateDTO;
import br.com.eduardofbom.stock_manager_api.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardofbom.stock_manager_api.domain.exception.RegraNegocioException;
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
                .orElseThrow(() -> new RegraNegocioException(
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

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoUpdateDTO produtoUpdateDTO) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado com o ID: " + id));

        Categoria categoriaExistente = categoriaRepository.findById(produtoUpdateDTO.categoriaId())
                .orElseThrow(() -> new RegraNegocioException("Categoria não encontrada com o ID: " + produtoUpdateDTO.categoriaId()));

        produtoExistente.setNome(produtoUpdateDTO.nome());
        produtoExistente.setEstoqMin(produtoUpdateDTO.estoqMin());
        produtoExistente.setPrecoVenda(produtoUpdateDTO.precoVenda());
        produtoExistente.setCategoria(categoriaExistente);

        produtoRepository.save(produtoExistente);

        return new ProdutoResponseDTO(produtoExistente);
    }

    @Transactional
    public void inativar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado com o ID: " + id));

        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

}
