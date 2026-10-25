package com.hachi.api.specification;

import com.hachi.api.Entity.Usuario;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Locale;

public final class UsuarioSpecification {

    private UsuarioSpecification() {
    }

    public static Specification<Usuario> comFiltros(
            String email, String telefone, LocalDate dtNasc,
            String cidade, String regiao, String nome) {

        Specification<Usuario> especificacao = (root, query, criterios) -> criterios.conjunction();

        if (email != null && !email.isBlank()) {
            String valor = "%" + email.trim().toLowerCase(Locale.ROOT) + "%";
            especificacao = especificacao.and((root, query, criterios) ->
                    criterios.like(criterios.lower(root.get("email")), valor));
        }

        if (telefone != null && !telefone.isBlank()) {
            String valor = "%" + telefone.trim().toLowerCase(Locale.ROOT) + "%";
            especificacao = especificacao.and((root, query, criterios) ->
                    criterios.like(criterios.lower(root.get("telefone")), valor));
        }

        if (dtNasc != null) {
            especificacao = especificacao.and((root, query, criterios) ->
                    criterios.equal(root.get("dtNasc"), dtNasc));
        }

        if (cidade != null && !cidade.isBlank()) {
            String valor = cidade.trim().toLowerCase(Locale.ROOT);
            especificacao = especificacao.and((root, query, criterios) ->
                    criterios.equal(criterios.lower(root.get("cidade")), valor));
        }

        if (regiao != null && !regiao.isBlank()) {
            String valor = regiao.trim().toLowerCase(Locale.ROOT);
            especificacao = especificacao.and((root, query, criterios) ->
                    criterios.equal(criterios.lower(root.get("regiao")), valor));
        }

        if (nome != null && !nome.isBlank()) {
            String valor = "%" + nome.trim().toLowerCase(Locale.ROOT) + "%";
            especificacao = especificacao.and((root, query, criterios) ->
                    criterios.like(criterios.lower(root.get("nome")), valor));
        }

        return especificacao;
    }
}
