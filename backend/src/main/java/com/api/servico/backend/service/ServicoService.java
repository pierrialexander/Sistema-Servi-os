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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Service
@JsonIgnoreProperties({"hibernateLazyInitializer"})
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
        return servicoRepository.save(servico);
    }

    public Servico alterar(Servico obj) {
    	 Servico servicoExistente = servicoRepository.findById(obj.getId()).orElse(null);
    	 if (servicoExistente != null) {
	        if (obj.getValorPago() != null && (servicoExistente.getValorPago() == null || !obj.getValorPago().equals(servicoExistente.getValorPago()))) {
	            servicoExistente.setValorPago(obj.getValorPago());
	        }

	        // Verifica se o valor pago é maior que zero e se o status não é "realizado" antes de atualizar
	        if (servicoExistente.getValorPago() != null && servicoExistente.getValorPago() > 0 && !"realizado".equals(servicoExistente.getStatus())) {
	            obj.setStatus("realizado");
	            obj.setValorPago(servicoExistente.getValorPago());
	        }
	        // Atualiza o objeto existente no banco de dados
	        return servicoRepository.save(obj);  
    	 }
         return servicoRepository.save(obj);
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
    
//    private void updateData(Servico entity, Servico obj) {
//        entity.setNomeCliente(obj.getNomeCliente());
//        entity.setDescricaoServico(obj.getDescricaoServico());
//        entity.setValorServico(obj.getValorServico());
//    }

}
