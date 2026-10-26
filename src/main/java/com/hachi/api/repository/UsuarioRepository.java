package com.hachi.api.Repository;

import com.hachi.api.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmailIgnoreCase(String email);

    Usuario findByIdUsuario(Integer idUsuario);

    long countByEmailIgnoreCase(String email);

    long countByEmailIgnoreCaseAndIdUsuarioNot(String email, Integer idUsuario);

    @Query("""
            SELECT u FROM Usuario u
            WHERE (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%')))
              AND (:telefone IS NULL OR u.telefone LIKE CONCAT('%', :telefone, '%'))
              AND (:dtNasc IS NULL OR u.dtNasc = :dtNasc)
              AND (:cidade IS NULL OR LOWER(u.cidade) = LOWER(:cidade))
              AND (:regiao IS NULL OR LOWER(u.regiao) = LOWER(:regiao))
              AND (:nome IS NULL OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
            """)
    List<Usuario> buscarComFiltros(
            @Param("email") String email,
            @Param("telefone") String telefone,
            @Param("dtNasc") LocalDate dtNasc,
            @Param("cidade") String cidade,
            @Param("regiao") String regiao,
            @Param("nome") String nome
    );
}
