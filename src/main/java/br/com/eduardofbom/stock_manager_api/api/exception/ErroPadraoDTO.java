package br.com.eduardofbom.stock_manager_api.api.exception;

import java.time.LocalDateTime;

public record ErroPadraoDTO(
        LocalDateTime timestamp,
        Integer status,
        String erro,
        String caminho
) {
}
