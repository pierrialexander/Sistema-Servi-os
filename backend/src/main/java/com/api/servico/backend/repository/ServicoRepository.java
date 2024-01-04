package com.api.servico.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.servico.backend.entity.Servico;

public interface ServicoRepository extends  JpaRepository<Servico, Long> {
    
    @Query(value = """
            SELECT s 
              FROM Servico s 
             WHERE s.valorPago IS NULL
             	OR s.valorPago = 0
               AND s.status <> 'cancelado'
            """)
    List<Servico> buscarServicosPagamentoPendente();


    @Query(value = """
            SELECT s 
              FROM Servico s 
             WHERE s.status = 'cancelado'
            """)
    List<Servico> buscarServicosCancelados();

    @Query(value = """
            SELECT s 
              FROM Servico s 
             WHERE s.status = 'realizado'
            """)
    List<Servico> buscarServicosRealizados();
}
