package br.com.eduardofbom.stock_manager_api.api.controller;

import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoRequestDTO;
import br.com.eduardofbom.stock_manager_api.api.dto.ProdutoResponseDTO;
import br.com.eduardofbom.stock_manager_api.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        List<ProdutoResponseDTO> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar (@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) {
        ProdutoResponseDTO produtoCriado = produtoService.criar(produtoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }
}
