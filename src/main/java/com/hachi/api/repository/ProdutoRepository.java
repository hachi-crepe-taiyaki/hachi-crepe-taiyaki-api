package com.hachi.api.Repository;

import com.hachi.api.Entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    boolean existsByCategoria_IdCategoria(Integer idCategoria);

    List<Produto> findAllByOrderByNomeAsc();
}
