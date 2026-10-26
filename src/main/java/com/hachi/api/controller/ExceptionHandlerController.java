package com.hachi.api.controller;

import com.hachi.api.DTO.RespostaErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErroDTO> tratarErrosDeValidacao(MethodArgumentNotValidException excecao) {
        String mensagem = excecao.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining(" "));
        if (mensagem.isBlank()) mensagem = "Os dados enviados são inválidos.";
        return ResponseEntity.badRequest().body(new RespostaErroDTO(mensagem));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RespostaErroDTO> tratarJsonInvalido(HttpMessageNotReadableException excecao) {
        return ResponseEntity.badRequest().body(new RespostaErroDTO("O corpo da requisição contém um JSON inválido."));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<RespostaErroDTO> tratarErrosDaAplicacao(ResponseStatusException excecao) {
        HttpStatus status = HttpStatus.valueOf(excecao.getStatusCode().value());
        String mensagem = excecao.getReason();
        if (mensagem == null || mensagem.isBlank()) mensagem = "Não foi possível processar a solicitação.";
        return ResponseEntity.status(status).body(new RespostaErroDTO(mensagem));
    }
}