package com.hachi.api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(name = "UsuarioCadastroDTO", description = "Dados necessários para criar uma conta de cliente.")
public class UsuarioCadastroDTO {
    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Schema(description = "Nome do usuário. Obrigatório; máximo de 100 caracteres.")
    private String nome;
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    @Schema(description = "E-mail válido e obrigatório; máximo de 100 caracteres e único no sistema.")
    private String email;
    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres.")
    @Schema(description = "Senha de acesso. Obrigatória; entre 8 e 72 caracteres. Será armazenada com BCrypt.")
    private String senha;
    @Size(max = 11, message = "O telefone deve ter no máximo 11 caracteres.")
    @Schema(description = "Telefone opcional; máximo de 11 caracteres.")
    private String telefone;
    @Past(message = "A data de nascimento deve estar no passado.")
    @Schema(description = "Data de nascimento opcional no formato yyyy-MM-dd e anterior à data atual.")
    private LocalDate dtNasc;
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres.")
    @Schema(description = "Cidade opcional; máximo de 100 caracteres.")
    private String cidade;
    @Size(max = 100, message = "A região deve ter no máximo 100 caracteres.")
    @Schema(description = "Região opcional; máximo de 100 caracteres.")
    private String regiao;

    public UsuarioCadastroDTO() {}
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public LocalDate getDtNasc() { return dtNasc; }
    public void setDtNasc(LocalDate dtNasc) { this.dtNasc = dtNasc; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getRegiao() { return regiao; }
    public void setRegiao(String regiao) { this.regiao = regiao; }
}