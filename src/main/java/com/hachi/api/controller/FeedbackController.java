package com.hachi.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.hachi.api.DTO.FeedbackRespostaDTO;
import com.hachi.api.Service.FeedbackService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Feedbacks", description = "Consulta dos feedbacks enviados pelos usuários, com filtros opcionais.")
@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Operation(summary = "Listar feedbacks", description = "Retorna feedbacks ordenados do mais recente para o mais antigo. Os filtros idUsuario, dataInicio e dataFim são opcionais; as datas usam o formato ISO yyyy-MM-dd e os limites são inclusivos. Se o usuário informado não existir, retorna 404; se dataInicio for posterior a dataFim, retorna 400. Requer autenticação.")
    @SecurityRequirement(name = "cookieAuth")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Lista de feedbacks retornada; pode estar vazia."), @ApiResponse(responseCode = "400", description = "Intervalo de datas inválido."), @ApiResponse(responseCode = "401", description = "Usuário não autenticado."), @ApiResponse(responseCode = "404", description = "Usuário informado não encontrado.")})
    @GetMapping
    public ResponseEntity<List<FeedbackRespostaDTO>> listarFeedbacks(
            @Parameter(description = "Filtra os feedbacks pelo ID do usuário que os enviou.", example = "1") @RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false)
            @Parameter(description = "Data inicial inclusiva, no formato yyyy-MM-dd.", example = "2026-01-01") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false)
            @Parameter(description = "Data final inclusiva, no formato yyyy-MM-dd.", example = "2026-12-31") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(feedbackService.listarFeedbacks(idUsuario, dataInicio, dataFim));
    }
}
