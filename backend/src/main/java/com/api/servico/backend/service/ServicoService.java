package com.api.servico.backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.servico.backend.entity.Servico;
import com.api.servico.backend.repository.ServicoRepository;
import com.api.servico.backend.service.exceptions.DatabaseException;
import com.api.servico.backend.service.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Serviço para operações relacionadas à entidade Servico.
 */
@Service
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    /**
     * Busca todos os serviços.
     *
     * @return Lista de todos os serviços.
     */
//    public List<Servico> buscarTodos(Pageable pageable) {
//        return servicoRepository.findAll();
//    }
    public Page<Servico> buscarTodos(Pageable pageable) {
        return servicoRepository.findAll(pageable);
    }

    /**
     * Busca um serviço pelo ID.
     *
     * @param id ID do serviço a ser buscado.
     * @return Serviço correspondente ao ID fornecido.
     * @throws ResourceNotFoundException Se o serviço não for encontrado.
     */
    public Servico buscarPorId(Long id) {
        Optional<Servico> obj = servicoRepository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * Busca serviços com pagamento pendente.
     *
     * @return Lista de serviços com pagamento pendente.
     */
    public List<Servico> buscarServicosPagamentoPendente() {
        return servicoRepository.buscarServicosPagamentoPendente();
    }

    /**
     * Busca serviços cancelados.
     *
     * @return Lista de serviços cancelados.
     */
    public List<Servico> buscarServicosCancelados() {
        return servicoRepository.buscarServicosCancelados();
    }

    /**
     * Busca serviços com pagamento pendente.
     *
     * @return Lista de serviços com pagamento pendente.
     */
    public List<Servico> buscarServicosRealizados() {
        return servicoRepository.buscarServicosRealizados();
    }

    /**
     * Insere um novo serviço.
     *
     * @param servico Serviço a ser inserido.
     * @return Serviço inserido.
     */
    public Servico inserir(Servico servico) {
        if ((servico.getValorPago() == null || servico.getValorPago() == 0) && servico.getDataPagamento() == null) {
            servico.setStatus("pendente");
        } else if(servico.getValorPago() > 0 &&  servico.getDataPagamento() == null){
        	servico.setDataPagamento(LocalDate.now());
            servico.setStatus("realizado");
        }
        return servicoRepository.save(servico);
    }

    /**
     * Altera um serviço existente.
     *
     * @param obj Serviço com as alterações a serem aplicadas.
     * @return Serviço alterado.
     */
    public Servico alterar(Servico obj) {
    	 Servico servicoExistente = servicoRepository.findById(obj.getId()).orElse(null);
    	 if (servicoExistente != null) {
	        if (obj.getValorPago() != null && (servicoExistente.getValorPago() == null || !obj.getValorPago().equals(servicoExistente.getValorPago()))) {
	            servicoExistente.setValorPago(obj.getValorPago());
	        }

	        // Verifica se o valor pago é maior que zero e se o status não é "realizado" antes de atualizar
	        if (servicoExistente.getValorPago() != null && servicoExistente.getValorPago() > 0 && !"realizado".equals(servicoExistente.getStatus()) || servicoExistente.getStatus() == null) {
	            obj.setStatus("realizado");
	            obj.setValorPago(servicoExistente.getValorPago());
	            obj.setDataPagamento(LocalDate.now());
	        }
	        
	        if(obj.getValorPago() == obj.getValorServico()) {
	        	obj.setDataPagamento(LocalDate.now());
	        	obj.setStatus("realizado");	        	
	        }
	        
	        // Atualiza o objeto existente no banco de dados
	        return servicoRepository.save(obj);  
    	 }
         return servicoRepository.save(obj);
    }
    

    /**
     * Cancela um serviço pelo ID.
     *
     * @param id ID do serviço a ser cancelado.
     */
    public void cancelarServico(Long id) {
    	Servico servico = servicoRepository.findById(id).get(); // Recupero os dados do banco
    	servico.setStatus("cancelado");							// Alteramos o Status
    	servicoRepository.save(servico);						// Atualizamos
    }
    
    /**
     * Cancela um serviço pelo ID.
     *
     * @param id ID do serviço a ser alterado para pendente.
     */
    public void reativarServico(Long id) {
    	Servico servico = servicoRepository.findById(id).get(); // Recupero os dados do banco
    	servico.setStatus("pendente");							// Alteramos o Status
    	servico.setDataPagamento(null);
    	servico.setValorPago(0.0);
    	servicoRepository.save(servico);						// Atualizamos
    }

    /**
     * Exclui um serviço pelo ID.
     *
     * @param id ID do serviço a ser excluído.
     * @throws ResourceNotFoundException Se o serviço não for encontrado.
     * @throws DatabaseException         Se ocorrer um erro de integridade no banco de dados.
     */
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
