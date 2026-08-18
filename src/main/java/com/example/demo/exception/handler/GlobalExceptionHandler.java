package com.example.demo.exception.handler;

import com.example.demo.exception.ConflitoException;
import com.example.demo.exception.RecursoNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import com.example.demo.dto.exception.ErrorResponseDTO;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Comparator;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflitoException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflitoException ex){
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(), 409);
        return ResponseEntity.status(409).body(errorResponseDTO);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(RecursoNaoEncontradoException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(ex.getMessage(), 404);
        return ResponseEntity.status(404).body(errorResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .sorted(Comparator.comparing(FieldError::getField))
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(mensagem, 400);
        return ResponseEntity.status(400).body(errorResponseDTO);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Corpo da requisição inválido ou mal formatado", 400);
        return ResponseEntity.status(400).body(errorResponseDTO);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Content-Type não suportado, use application/json", 415);
        return ResponseEntity.status(415).body(errorResponseDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Parâmetro '" + ex.getName() + "' inválido", 400);
        return ResponseEntity.status(400).body(errorResponseDTO);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoResourceFoundException(NoResourceFoundException ex) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Rota não encontrada", 404);
        return ResponseEntity.status(404).body(errorResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception ex) {
        log.error("Erro não tratado", ex);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Erro interno no servidor", 500);
        return ResponseEntity.status(500).body(errorResponseDTO);
    }
}
