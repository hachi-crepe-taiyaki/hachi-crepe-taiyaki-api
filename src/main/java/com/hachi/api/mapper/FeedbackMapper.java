package com.hachi.api.mapper;

import com.hachi.api.Entity.Feedback;
import com.hachi.api.dto.FeedbackRespostaDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedbackMapper {

    public FeedbackRespostaDTO paraResposta(Feedback feedback) {
        return new FeedbackRespostaDTO(
                feedback.getIdFeedback(),
                feedback.getNota(),
                feedback.getComentario(),
                feedback.getDataHora(),
                feedback.getUsuario() == null ? null : feedback.getUsuario().getIdUsuario()
        );
    }

    public List<FeedbackRespostaDTO> paraRespostas(List<Feedback> feedbacks) {
        return feedbacks.stream().map(this::paraResposta).toList();
    }
}
