package com.hachi.api.Repository;

import com.hachi.api.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    List<Feedback> findAllByOrderByDataHoraDesc();

    List<Feedback> findByUsuario_IdUsuarioOrderByDataHoraDesc(Integer idUsuario);

    List<Feedback> findByDataHoraGreaterThanEqualOrderByDataHoraDesc(LocalDateTime dataInicio);

    List<Feedback> findByDataHoraLessThanOrderByDataHoraDesc(LocalDateTime dataFimExclusivo);

    List<Feedback> findByDataHoraGreaterThanEqualAndDataHoraLessThanOrderByDataHoraDesc(
            LocalDateTime dataInicio, LocalDateTime dataFimExclusivo);

    List<Feedback> findByUsuario_IdUsuarioAndDataHoraGreaterThanEqualOrderByDataHoraDesc(
            Integer idUsuario, LocalDateTime dataInicio);

    List<Feedback> findByUsuario_IdUsuarioAndDataHoraLessThanOrderByDataHoraDesc(
            Integer idUsuario, LocalDateTime dataFimExclusivo);

    List<Feedback> findByUsuario_IdUsuarioAndDataHoraGreaterThanEqualAndDataHoraLessThanOrderByDataHoraDesc(
            Integer idUsuario, LocalDateTime dataInicio, LocalDateTime dataFimExclusivo);
}
