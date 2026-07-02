package br.com.eduardofbom.stock_manager_api.api.dto;

import br.com.eduardofbom.stock_manager_api.domain.model.Produto;

import java.math.BigDecimal;

public record ProdutoResponseDTO (
        Long id,
        String nome,
        String codBarras,
        String unidMedida,
        BigDecimal quantAtual,
        BigDecimal precoVenda,
        Long categoriaId,
        String categoriaName
) {

    public ProdutoResponseDTO (Produto produto) {
        this (
                produto.getId(),
                produto.getNome(),
                produto.getCodBarras(),
                produto.getUnidMedida(),
                produto.getQuantAtual(),
                produto.getPrecoVenda(),
                produto.getCategoria().getId(),
                produto.getCategoria().getNome()
        );
    }
}
