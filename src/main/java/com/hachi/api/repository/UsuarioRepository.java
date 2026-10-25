package com.hachi.api.repository;

import com.hachi.api.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.email) = LOWER(:email)")
    Usuario buscarPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.idUsuario = :id")
    Usuario buscarPorId(@Param("id") Integer id);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE LOWER(u.email) = LOWER(:email)")
    long contarPorEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE LOWER(u.email) = LOWER(:email) AND u.idUsuario <> :id")
    long contarPorEmailDeOutroUsuario(@Param("email") String email, @Param("id") Integer id);
}