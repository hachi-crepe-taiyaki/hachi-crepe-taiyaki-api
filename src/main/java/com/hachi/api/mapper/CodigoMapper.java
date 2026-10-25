package com.hachi.api.mapper;

import com.hachi.api.Entity.Codigo;
import com.hachi.api.dto.CodigoRespostaDTO;
import org.springframework.stereotype.Component;

@Component
public class CodigoMapper {
    public CodigoRespostaDTO paraResposta(Codigo codigo) {
        return new CodigoRespostaDTO(
                codigo.getIdCodigos(),
                codigo.getCodigo(),
                codigo.getStatusScan(),
                codigo.getValor()
        );
    }
}
