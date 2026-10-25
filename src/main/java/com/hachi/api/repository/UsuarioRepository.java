package com.hachi.api.repository;

import com.hachi.api.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmailIgnoreCase(String email);

    Usuario findByIdUsuario(Integer idUsuario);

    long countByEmailIgnoreCase(String email);

    long countByEmailIgnoreCaseAndIdUsuarioNot(String email, Integer idUsuario);
}