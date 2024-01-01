package com.api.servico.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.servico.backend.entity.Servico;

public interface ServicoRepository extends  JpaRepository<Servico, Long> {
    
    @Query(nativeQuery = true, value = """
            SELECT s 
              FROM Servico s 
             WHERE s.valorPago <> null 
               AND s.valorPago > 0
            """)
    List<Servico> buscarServicosPagamentoPendente();
}
