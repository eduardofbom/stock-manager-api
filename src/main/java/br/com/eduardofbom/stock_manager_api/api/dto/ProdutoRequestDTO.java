package br.com.eduardofbom.stock_manager_api.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoRequestDTO (
        @NotBlank(message = "O nome do produto não pode estar em branco")
        @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres")
        String nome,

        @Size(max = 13, message = "O código de barras deve ter no máximo 13 caracteres")
        String codBarras,

        @NotBlank(message = "A unidade de medida é obrigatória")
        @Size(min = 2, max = 2, message = "A unidade de medida deve ter exatamente 2 caracteres (ex: UN, KG)")
        String unidMedida,

        @NotNull(message = "O estoque mínimo é obrigatório")
        @PositiveOrZero(message = "O estoque mínimo não pode ser negativo")
        BigDecimal estoqMin,

        @NotNull(message = "O preço de venda é obrigatório")
        @PositiveOrZero(message = "O preço de venda não pode ser negativo")
        BigDecimal precoVenda,

        @NotNull(message = "O ID da categoria é obrigatório")
        Long categoriaId
) {
}
