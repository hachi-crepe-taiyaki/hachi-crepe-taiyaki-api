package com.hachi.api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RespostaErroDTO", description = "Formato padronizado de resposta para erros da API.")
public class RespostaErroDTO {
    @Schema(description = "Mensagem explicando o motivo pelo qual a requisição não pôde ser processada.")
    private String mensagem;

    public RespostaErroDTO() {}
    public RespostaErroDTO(String mensagem) { this.mensagem = mensagem; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}