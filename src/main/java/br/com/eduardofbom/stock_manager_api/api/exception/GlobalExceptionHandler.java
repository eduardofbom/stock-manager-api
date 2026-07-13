package br.com.eduardofbom.stock_manager_api.api.exception;

import br.com.eduardofbom.stock_manager_api.domain.exception.EntidadeNaoEncontradaException;
import br.com.eduardofbom.stock_manager_api.domain.exception.RegraNegocioException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Status 404 (Not Found)
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroPadraoDTO> tratarEntidadeNaoEncontrada(
            EntidadeNaoEncontradaException exception,
            HttpServletRequest request
    ) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Status 400 (Bad Request)
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroPadraoDTO> tratarRegraNegocio(
            RegraNegocioException excecao,
            HttpServletRequest request
    ) {
        ErroPadraoDTO erro = new ErroPadraoDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                excecao.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
