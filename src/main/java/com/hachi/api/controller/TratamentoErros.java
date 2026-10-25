package com.hachi.api.controller;

import com.hachi.api.dto.RespostaErroDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class TratamentoErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespostaErroDTO> tratarErrosDeValidacao(MethodArgumentNotValidException excecao) {
        String mensagem = excecao.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining(" "));
        if (mensagem.isBlank()) mensagem = "Os dados enviados são inválidos.";
        return ResponseEntity.badRequest().body(new RespostaErroDTO(mensagem));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<RespostaErroDTO> tratarErrosDaAplicacao(ResponseStatusException excecao) {
        HttpStatus status = HttpStatus.valueOf(excecao.getStatusCode().value());
        String mensagem = excecao.getReason();
        if (mensagem == null || mensagem.isBlank()) mensagem = "Não foi possível processar a solicitação.";
        return ResponseEntity.status(status).body(new RespostaErroDTO(mensagem));
    }
}