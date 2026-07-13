package br.com.eduardofbom.stock_manager_api.api.dto;

import java.util.List;

public record FornecedorRequestDTO(
        String nome,
        String documentoFiscal,
        List<FornecedorContatoRequestDTO> contatos
) {
}
