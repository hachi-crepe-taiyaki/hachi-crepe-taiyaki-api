package com.hachi.api.repository;

import com.hachi.api.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, JpaSpecificationExecutor<Usuario> {
    Usuario findByEmailIgnoreCase(String email);

    Usuario findByIdUsuario(Integer idUsuario);

    long countByEmailIgnoreCase(String email);

    long countByEmailIgnoreCaseAndIdUsuarioNot(String email, Integer idUsuario);
}
