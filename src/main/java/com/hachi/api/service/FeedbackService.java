package com.hachi.api.service;

import com.hachi.api.Entity.Feedback;
import com.hachi.api.Entity.Usuario;
import com.hachi.api.dto.FeedbackCadastroDTO;
import com.hachi.api.repository.FeedbackRepository;
import com.hachi.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class FeedbackService {

    private final UsuarioRepository usuarioRepositorio;
    private final FeedbackRepository feedbackRepositorio;

    public FeedbackService(UsuarioRepository usuarioRepositorio,
                           FeedbackRepository feedbackRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.feedbackRepositorio = feedbackRepositorio;
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
}
