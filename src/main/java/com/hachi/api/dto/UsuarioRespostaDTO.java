package com.hachi.api.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(name = "UsuarioRespostaDTO", description = "Dados públicos da conta retornados pela API. A senha e seu hash nunca são incluídos.")
public class UsuarioRespostaDTO {
    @Schema(description = "Identificador único da conta.")
    private Integer idUsuario;
    @Schema(description = "Nome do usuário.")
    private String nome;
    @Schema(description = "E-mail cadastrado.")
    private String email;
    @Schema(description = "Telefone cadastrado, quando informado.")
    private String telefone;
    @Schema(description = "Data e hora de criação da conta.")
    private LocalDateTime criadoEm;
    @Schema(description = "Data e hora da última atualização da conta.")
    private LocalDateTime atualizadoEm;
    @Schema(description = "Status da conta: 1 ativa, 0 inativa.")
    private Integer statusUsuario;
    @Schema(description = "Papel da conta: 0 cliente, 1 funcionário, 2 administrador.")
    private Integer funcao;
    @Schema(description = "Data de nascimento no formato yyyy-MM-dd, quando informada.")
    private LocalDate dtNasc;
    @Schema(description = "Cidade cadastrada.")
    private String cidade;
    @Schema(description = "Região cadastrada.")
    private String regiao;
    @Schema(description = "Quantidade atual de selos de fidelidade.")
    private Integer selos;

    public UsuarioRespostaDTO() {}
    public UsuarioRespostaDTO(Integer idUsuario, String nome, String email, String telefone,
            LocalDateTime criadoEm, LocalDateTime atualizadoEm, Integer statusUsuario, Integer funcao,
            LocalDate dtNasc, String cidade, String regiao, Integer selos) {
        this.idUsuario = idUsuario; this.nome = nome; this.email = email; this.telefone = telefone;
        this.criadoEm = criadoEm; this.atualizadoEm = atualizadoEm; this.statusUsuario = statusUsuario;
        this.funcao = funcao; this.dtNasc = dtNasc; this.cidade = cidade; this.regiao = regiao; this.selos = selos;
    }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
    public Integer getStatusUsuario() { return statusUsuario; }
    public void setStatusUsuario(Integer statusUsuario) { this.statusUsuario = statusUsuario; }
    public Integer getFuncao() { return funcao; }
    public void setFuncao(Integer funcao) { this.funcao = funcao; }
    public LocalDate getDtNasc() { return dtNasc; }
    public void setDtNasc(LocalDate dtNasc) { this.dtNasc = dtNasc; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getRegiao() { return regiao; }
    public void setRegiao(String regiao) { this.regiao = regiao; }
    public Integer getSelos() { return selos; }
    public void setSelos(Integer selos) { this.selos = selos; }
}