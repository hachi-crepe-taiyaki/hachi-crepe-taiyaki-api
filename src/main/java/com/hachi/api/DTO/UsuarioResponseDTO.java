package com.hachi.api.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioResponseDTO {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String telefone;
    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;
    private Boolean statusUsuario;
    private Byte funcao;
    private LocalDate dtNasc;
    private String cidade;
    private String regiao;
    private Integer selos;

    public UsuarioResponseDTO(Integer idUsuario, String nome, String email, String telefone, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Boolean statusUsuario, Byte funcao, LocalDate dtNasc, String cidade, String regiao, Integer selos) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.criadoEm = criadoEm;
        this.atualizadoEm = atualizadoEm;
        this.statusUsuario = statusUsuario;
        this.funcao = funcao;
        this.dtNasc = dtNasc;
        this.cidade = cidade;
        this.regiao = regiao;
        this.selos = selos;
    }

    public UsuarioResponseDTO() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public LocalDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public void setAtualizadoEm(LocalDateTime atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }

    public Boolean getStatusUsuario() {
        return statusUsuario;
    }

    public void setStatusUsuario(Boolean statusUsuario) {
        this.statusUsuario = statusUsuario;
    }

    public Byte getFuncao() {
        return funcao;
    }

    public void setFuncao(Byte funcao) {
        this.funcao = funcao;
    }

    public LocalDate getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(LocalDate dtNasc) {
        this.dtNasc = dtNasc;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public Integer getSelos() {
        return selos;
    }

    public void setSelos(Integer selos) {
        this.selos = selos;
    }
}
