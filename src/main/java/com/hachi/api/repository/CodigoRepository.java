package com.hachi.api.Repository;

import com.hachi.api.Entity.Codigo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodigoRepository extends JpaRepository<Codigo, Integer> {
    boolean existsByCodigoIgnoreCase(String codigo);

    Codigo findByCodigoIgnoreCase(String codigo);
}
