package com.hachi.api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Schema(name = "UsuarioAtualizacaoDTO", description = "Campos opcionais que podem ser alterados na conta. Campos omitidos permanecem inalterados.")
public class UsuarioAtualizacaoDTO {
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Schema(description = "Novo nome opcional; máximo de 100 caracteres e, quando informado, não pode ser vazio.")
    private String nome;
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    @Schema(description = "Novo e-mail opcional; deve ser válido e não estar em uso por outra conta.")
    private String email;
    @Size(min = 8, max = 72, message = "A senha deve ter entre 8 e 72 caracteres.")
    @Schema(description = "Nova senha opcional; entre 8 e 72 caracteres. Será armazenada com BCrypt.")
    private String senha;
    @Size(max = 11, message = "O telefone deve ter no máximo 11 caracteres.")
    @Schema(description = "Novo telefone opcional; máximo de 11 caracteres.")
    private String telefone;
    @Past(message = "A data de nascimento deve estar no passado.")
    @Schema(description = "Nova data de nascimento opcional no formato yyyy-MM-dd, anterior à data atual.")
    private LocalDate dtNasc;
    @Size(max = 100, message = "A cidade deve ter no máximo 100 caracteres.")
    @Schema(description = "Nova cidade opcional; máximo de 100 caracteres.")
    private String cidade;
    @Size(max = 100, message = "A região deve ter no máximo 100 caracteres.")
    @Schema(description = "Nova região opcional; máximo de 100 caracteres.")
    private String regiao;

    public UsuarioAtualizacaoDTO() {}
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