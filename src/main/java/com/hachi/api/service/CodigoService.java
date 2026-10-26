package com.hachi.api.Service;

import com.hachi.api.Entity.Codigo;
import com.hachi.api.Entity.Usuario;
import com.hachi.api.DTO.CodigoRespostaDTO;
import java.math.BigDecimal;
import com.hachi.api.Mapper.CodigoMapper;
import com.hachi.api.Repository.CodigoRepository;
import com.hachi.api.Repository.UsuarioRepository;
import com.hachi.api.util.GeradorCodigo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CodigoService {
    private static final int MAX_TENTATIVAS_GERACAO = 100;

    private final CodigoRepository codigoRepositorio;
    private final UsuarioRepository usuarioRepositorio;
    private final GeradorCodigo geradorCodigo;
    private final CodigoMapper mapeador;

    public CodigoService(CodigoRepository codigoRepositorio,
                         UsuarioRepository usuarioRepositorio,
                         GeradorCodigo geradorCodigo,
                         CodigoMapper mapeador) {
        this.codigoRepositorio = codigoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.geradorCodigo = geradorCodigo;
        this.mapeador = mapeador;
    }

    @Transactional
    public CodigoRespostaDTO gerarCodigo(Integer idUsuarioAutenticado, BigDecimal valor) {
        if (idUsuarioAutenticado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "É necessário estar autenticado para gerar um código.");
        }

        Usuario usuario = usuarioRepositorio.findByIdUsuario(idUsuarioAutenticado);
        if (usuario == null || !Integer.valueOf(1).equals(usuario.getStatusUsuario())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "A conta não existe ou está inativa.");
        }

        if (!Integer.valueOf(1).equals(usuario.getFuncao())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Somente funcionários podem gerar códigos.");
        }

        for (int tentativa = 0; tentativa < MAX_TENTATIVAS_GERACAO; tentativa++) {
            String codigoGerado = geradorCodigo.gerarCodigo();

            if (!codigoRepositorio.existsByCodigoIgnoreCase(codigoGerado)) {
                Codigo novoCodigo = new Codigo();
                novoCodigo.setCodigo(codigoGerado);
                novoCodigo.setStatusScan(0);
                novoCodigo.setValor(valor);

                return mapeador.paraResposta(codigoRepositorio.save(novoCodigo));
            }
        }

        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "Não foi possível gerar um código único. Tente novamente.");
    }
}
