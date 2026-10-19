package com.hachi.api.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "senha", nullable = false, length = 255)
    private String senha;

    @Column(name = "telefone", length = 11)
    private String telefone;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    @Column(name = "status_usuario")
    private Boolean statusUsuario;

    @Column(name = "funcao")
    private Byte funcao;

    @Column(name = "dt_nasc")
    private LocalDate dtNasc;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "regiao", length = 100)
    private String regiao;

    @Column(name = "selos", length = 100)
    private Integer selos;

    public Usuario(Integer idUsuario, String nome, String email, String senha, String telefone, LocalDateTime criadoEm, LocalDateTime atualizadoEm, Boolean statusUsuario, Byte funcao, LocalDate dtNasc, String cidade, String regiao, Integer selos) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

    public Usuario() {
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
