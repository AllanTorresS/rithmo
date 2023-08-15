package br.com.rhitmo.hotel.exceptionhandler;

import br.com.rhitmo.hotel.model.exceptions.ApiCustomError;
import br.com.rhitmo.hotel.model.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String ERROR_GENERIC_MESSAGE = "Ocorreu um erro inesperado no sistema.";


    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return createResponseError(status, ERROR_GENERIC_MESSAGE);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                    ((MethodArgumentTypeMismatchException) ex).getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

            log.error(detail, ex);
            return createResponseError(HttpStatus.BAD_REQUEST, detail);
        }
        log.error(ex.getMessage(), ex);
        return createResponseError(HttpStatus.BAD_REQUEST, ex.getMessage());

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        log.error(errors.toString(), ex);

        var apiCustomError = new ApiCustomError(status, errors);

        return new ResponseEntity<>(apiCustomError, apiCustomError.getStatus());

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var mostSpecificCause = ex.getMostSpecificCause();
        var message = "";

        if (mostSpecificCause != null) {
            String exceptionName = mostSpecificCause.getClass().getName();
            message = mostSpecificCause.getMessage();

        } else {
            message = ex.getMessage();
        }

        log.error(message, ex);

        return createResponseError(status, ex.getMessage());
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return createResponseError(HttpStatus.BAD_REQUEST, "A rota: " + ex.getRequestURL() + " não existe.");
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error(ex.getMessage(), ex);

        return createResponseError(status, "Esse metodo HTPP não é suportado");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(BusinessException ex,
                                                         WebRequest request) {

        log.error(ex.getMessage(), ex);
        return createResponseError(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private static ResponseEntity<Object> createResponseError(HttpStatusCode status, String message) {
        var apiCustomError = new ApiCustomError(status, message);
        return new ResponseEntity<>(apiCustomError, apiCustomError.getStatus());
    }
}
