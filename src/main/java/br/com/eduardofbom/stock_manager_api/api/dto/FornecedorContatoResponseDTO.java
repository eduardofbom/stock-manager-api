package br.com.eduardofbom.stock_manager_api.api.dto;

import br.com.eduardofbom.stock_manager_api.domain.model.TiposContato;

public record FornecedorContatoResponseDTO(
        Long id,
        TiposContato tipo,
        String valor,
        String nomeContato
) {
}
