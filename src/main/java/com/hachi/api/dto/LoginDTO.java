package com.hachi.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "LoginDTO", description = "Credenciais utilizadas para autenticar um usuário.")
public class LoginDTO {
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Schema(description = "E-mail cadastrado da conta. Deve ser um endereço válido.")
    private String email;
    @NotBlank(message = "A senha é obrigatória.")
    @Schema(description = "Senha em texto puro enviada para autenticação via HTTPS; nunca é devolvida pela API.")
    private String senha;

    public LoginDTO() {}
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}