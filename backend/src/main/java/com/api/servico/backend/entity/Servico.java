package com.api.servico.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "servico")
public class Servico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String nomeCliente;
    @Column(columnDefinition = "DATE")
    private LocalDateTime dataInicio = LocalDateTime.now();
    @Column(columnDefinition = "DATE")
    private LocalDateTime dataTermino = LocalDateTime.now();
    private String descricaoServico;
    private Double valorServico;
    private Double valorPago;
    @Column(columnDefinition = "DATE")
    private LocalDateTime dataPagamento;
    private String status; // "pendente", "realizado", "cancelado"
}
