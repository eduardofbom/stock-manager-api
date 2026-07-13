package br.com.eduardofbom.stock_manager_api.domain.exception;

public class RegraNegocioException extends RuntimeException{
    public RegraNegocioException(String message) {
        super(message);
    }
}
