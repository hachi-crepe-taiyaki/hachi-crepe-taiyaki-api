package com.hachi.api.DTO;

import java.time.LocalDate;

public class UsuarioRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private Boolean statusUsuario;
    private Byte funcao;
    private LocalDate dtNasc;
    private String cidade;
    private String regiao;
    private Integer selos;

    public UsuarioRequestDTO(String nome, String email, String senha, String telefone, Boolean statusUsuario, Byte funcao, LocalDate dtNasc, String cidade, String regiao, Integer selos) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.statusUsuario = statusUsuario;
        this.funcao = funcao;
        this.dtNasc = dtNasc;
        this.cidade = cidade;
        this.regiao = regiao;
        this.selos = selos;
    }

    public UsuarioRequestDTO() {
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
