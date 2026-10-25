package com.hachi.api.service;

import com.hachi.api.Entity.Feedback;
import com.hachi.api.Entity.Usuario;
import com.hachi.api.dto.FeedbackCadastroDTO;
import com.hachi.api.dto.FeedbackRespostaDTO;
import com.hachi.api.mapper.FeedbackMapper;
import com.hachi.api.repository.FeedbackRepository;
import com.hachi.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackService {

    private final UsuarioRepository usuarioRepositorio;
    private final FeedbackRepository feedbackRepositorio;
    private final FeedbackMapper feedbackMapeador;

    public FeedbackService(UsuarioRepository usuarioRepositorio,
                           FeedbackRepository feedbackRepositorio,
                           FeedbackMapper feedbackMapeador) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.feedbackRepositorio = feedbackRepositorio;
        this.feedbackMapeador = feedbackMapeador;
    }

    @Transactional
    public void cadastrarFeedback(Integer idUsuarioAutenticado, FeedbackCadastroDTO dto) {
        if (idUsuarioAutenticado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "É necessário estar autenticado para enviar um feedback.");
        }

        Usuario usuario = usuarioRepositorio.findByIdUsuario(idUsuarioAutenticado);
        if (usuario == null || !Integer.valueOf(1).equals(usuario.getStatusUsuario())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Usuário não encontrado ou inativo.");
        }

        Feedback feedback = new Feedback();
        feedback.setNota(dto.getNota());
        feedback.setComentario(dto.getComentario().trim());
        feedback.setDataHora(LocalDateTime.now());
        feedback.setUsuario(usuario);

        feedbackRepositorio.save(feedback);
    }

    @Transactional(readOnly = true)
    public List<FeedbackRespostaDTO> listarFeedbacks(Integer idUsuario, LocalDate dataInicio, LocalDate dataFim) {
        if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A data inicial não pode ser posterior à data final.");
        }

        if (idUsuario != null && usuarioRepositorio.findByIdUsuario(idUsuario) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }

        LocalDateTime inicio = dataInicio == null ? null : dataInicio.atStartOfDay();
        LocalDateTime fimExclusivo = dataFim == null ? null : dataFim.plusDays(1).atStartOfDay();

        List<Feedback> feedbacks;

        if (idUsuario != null && inicio != null && fimExclusivo != null) {
            feedbacks = feedbackRepositorio
                    .findByUsuario_IdUsuarioAndDataHoraGreaterThanEqualAndDataHoraLessThanOrderByDataHoraDesc(
                            idUsuario, inicio, fimExclusivo);
        } else if (idUsuario != null && inicio != null) {
            feedbacks = feedbackRepositorio
                    .findByUsuario_IdUsuarioAndDataHoraGreaterThanEqualOrderByDataHoraDesc(idUsuario, inicio);
        } else if (idUsuario != null && fimExclusivo != null) {
            feedbacks = feedbackRepositorio
                    .findByUsuario_IdUsuarioAndDataHoraLessThanOrderByDataHoraDesc(idUsuario, fimExclusivo);
        } else if (idUsuario != null) {
            feedbacks = feedbackRepositorio.findByUsuario_IdUsuarioOrderByDataHoraDesc(idUsuario);
        } else if (inicio != null && fimExclusivo != null) {
            feedbacks = feedbackRepositorio
                    .findByDataHoraGreaterThanEqualAndDataHoraLessThanOrderByDataHoraDesc(inicio, fimExclusivo);
        } else if (inicio != null) {
            feedbacks = feedbackRepositorio.findByDataHoraGreaterThanEqualOrderByDataHoraDesc(inicio);
        } else if (fimExclusivo != null) {
            feedbacks = feedbackRepositorio.findByDataHoraLessThanOrderByDataHoraDesc(fimExclusivo);
        } else {
            feedbacks = feedbackRepositorio.findAllByOrderByDataHoraDesc();
        }

        return feedbackMapeador.paraRespostas(feedbacks);
    }
}
