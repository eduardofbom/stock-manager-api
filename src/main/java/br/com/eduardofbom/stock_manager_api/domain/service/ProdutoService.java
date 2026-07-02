package br.com.eduardofbom.stock_manager_api.domain.service;

import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoResponseDTO;
import br.com.eduardofbom.stock_manager_api.domain.repository.IProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final IProdutoRepository produtoRepository;

    public ProdutoService(IProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.buscarTodosComCategoria()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
    }
}
