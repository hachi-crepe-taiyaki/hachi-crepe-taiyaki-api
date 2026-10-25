package com.hachi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ResultadoLoginDTO", description = "Objeto interno que reúne o token e os dados públicos do usuário autenticado.")
public class ResultadoLoginDTO {
    @Schema(description = "JWT emitido após autenticação. O endpoint de login entrega o token por cookie HttpOnly.")
    private String token;
    @Schema(description = "Dados públicos do usuário autenticado, sem incluir a senha.")
    private UsuarioRespostaDTO usuario;

    public ResultadoLoginDTO() {}
    public ResultadoLoginDTO(String token, UsuarioRespostaDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public UsuarioRespostaDTO getUsuario() { return usuario; }
    public void setUsuario(UsuarioRespostaDTO usuario) { this.usuario = usuario; }
}