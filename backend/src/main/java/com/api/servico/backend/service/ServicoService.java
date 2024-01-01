package com.api.servico.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.servico.backend.entity.Servico;
import com.api.servico.backend.repository.ServicoRepository;
import com.api.servico.backend.service.exceptions.DatabaseException;
import com.api.servico.backend.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> buscarTodos() {
        return servicoRepository.findAll();
    }

    public Servico buscarPorId(Long id) {
        Optional<Servico> obj = servicoRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Servico> buscarServicosPagamentoPendente() {
        return servicoRepository.buscarServicosPagamentoPendente();
    }

    public List<Servico> buscarServicosCancelados() {
        return servicoRepository.buscarServicosCancelados();
    }

    public Servico inserir(Servico servico) {
        if (servico.getValorPago() == null || servico.getValorPago() == 0 || servico.getDataPagamento() == null) {
            servico.setStatus("pendente");
        } else {
            servico.setStatus("realizado");
        }
        return servicoRepository.saveAndFlush(servico);
    }

    public Servico alterar(Long id, Servico obj) {
        try {
            if (obj.getValorPago() != null || obj.getValorPago() > 0 || obj.getDataPagamento() != null) {
                obj.setStatus("realizado");
            }
            return servicoRepository.save(servicoRepository.getReferenceById(id));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    public void excluir(Long id) {
        try {
            servicoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
