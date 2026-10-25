package com.hachi.api.dto;

public class RespostaErroDTO {
    private String mensagem;

    public RespostaErroDTO() {}
    public RespostaErroDTO(String mensagem) { this.mensagem = mensagem; }
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}