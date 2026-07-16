package br.com.eduardofbom.stock_manager_api.api.dto;

import br.com.eduardofbom.stock_manager_api.domain.model.TiposContato;

import java.util.List;

public record FornecedorResponseDTO(
        Long id,
        String nome,
        String documentoFiscal,
        boolean ativo,
        List<FornecedorContatoResponseDTO> contatos
) {
}
