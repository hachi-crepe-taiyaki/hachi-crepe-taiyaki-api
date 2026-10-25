package com.hachi.api.dto;

public class CodigoRespostaDTO {
    private Integer idCodigos;
    private String codigo;
    private Integer statusScan;

    public CodigoRespostaDTO() {}

    public CodigoRespostaDTO(Integer idCodigos, String codigo, Integer statusScan) {
        this.idCodigos = idCodigos;
        this.codigo = codigo;
        this.statusScan = statusScan;
    }

    public Integer getIdCodigos() {
        return idCodigos;
    }

    public void setIdCodigos(Integer idCodigos) {
        this.idCodigos = idCodigos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getStatusScan() {
        return statusScan;
    }

    public void setStatusScan(Integer statusScan) {
        this.statusScan = statusScan;
    }
}
