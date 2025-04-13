package com.barbosa.sistema_reembolso.Exception.handler;

import com.barbosa.sistema_reembolso.Exception.business.*;
import com.barbosa.sistema_reembolso.Exception.model.ApiError;
import com.barbosa.sistema_reembolso.Exception.model.CampoErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ApiError> handlerUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
        ApiError error = ApiError.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handlerGeneric(Exception ex){
        ApiError error = ApiError.builder()
                .mensagem("Erro interno no servidor")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();

       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handlerValidationsException(MethodArgumentNotValidException ex){

        List<CampoErroDTO> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new CampoErroDTO(error.getField(), error.getDefaultMessage()))
                .toList();

        ApiError erro = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .mensagem("Campos inválidos")
                .erros(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ApiError> handlerEmailJaCadastrado(EmailJaCadastradoException ex) {
        ApiError erro = ApiError.builder()
                .status(HttpStatus.CONFLICT.value())
                .mensagem(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(EmailNaoEncontradoException.class)
    public ResponseEntity<ApiError> handlerEmailNaoEncontrado(EmailNaoEncontradoException ex) {
        ApiError erro = ApiError.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ValorReembolsoInvalidoException.class)
    public ResponseEntity<ApiError> handlerValorReembolsoInvalidoException(ValorReembolsoInvalidoException ex) {
        ApiError erro = ApiError.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(DataDespesaInvalidaException.class)
    public ResponseEntity<ApiError> handlerDataDespesaInvalida(DataDespesaInvalidaException ex) {
        ApiError erro = ApiError.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ValorDespesaInvalidoException.class)
    public ResponseEntity<ApiError> handlerDataDespesaInvalida(ValorDespesaInvalidoException ex){
        ApiError erro = ApiError.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ReembolsoNaoEncontradoException.class)
    public ResponseEntity<ApiError> handlerReembolsoNaoEncontrado(ReembolsoNaoEncontradoException ex) {
        ApiError erro = ApiError.builder()
                .mensagem(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

}
