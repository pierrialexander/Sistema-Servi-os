import '../App.css';
import React, { useEffect, useState } from 'react';
import axios from 'axios'

function Servico() {
    const baseURL = 'http://localhost:8080/api/servicos'

    const [servico, setServico] = useState({
        nomeCliente: '',
        dataInicio: '',
        dataTermino: '',
        descricaoServico: '',
        valorServico: '',
        valorPago: '',
        dataPagamento: ''
    })

    const [servicos, setServicos] = useState([]);
    const [atualizar, setAtualizar] = useState();

    const handleChange = (event) => {
        setServico({ ...servico, [event.target.name]: event.target.value })
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        if (servico.id) {
            console.log("Atualizando")
            axios.put(baseURL, servico)
                .then(result => {
                    setAtualizar(result)
                })
            setServico({
                nomeCliente: '',
                dataInicio: '',
                dataTermino: '',
                descricaoServico: '',
                valorServico: '',
                valorPago: '',
                dataPagamento: ''
            })
        } else {
            console.log("Inserindo")
            axios.post(baseURL, servico)
                .then(result => {
                    setAtualizar(result)
                })
            setServico({
                nomeCliente: '',
                dataInicio: '',
                dataTermino: '',
                descricaoServico: '',
                valorServico: '',
                valorPago: '',
                dataPagamento: ''
            })
        }
    }

    useEffect(() => {
        axios.get(baseURL) // ?size=5&page=2
            .then(result => {
                console.log(result.data.content);
                setServicos(result.data.content);
            })
            .catch(error => {
                console.error('Erro ao obter dados:', error);
            });
    }, [atualizar]);

    function formatarMoeda(valor) {
        return parseFloat(valor).toLocaleString('pt-BR', {
            style: 'currency',
            currency: 'BRL',
        });
    }

    function formatarData(data) {
        const dataUtc = new Date(data + 'T00:00:00Z'); // Adiciona o UTC para garantir consistência
        return dataUtc.toLocaleDateString('pt-BR', { timeZone: 'UTC' });
    }

    const handleAlterar = (srv) => {
        setServico(srv);
    };

    const handleCancelar = (srv) => {
        console.log(srv.id)
        axios.post(baseURL + "/cancelarservico/" + srv.id)
            .then(result => {
                setAtualizar(result)
            })
    };

    const handleExcluir = (srv) => {
        axios.delete(baseURL + "/" + srv.id)
            .then(result => {
                setAtualizar(result)
            })
    };

    const handleReativar = (srv) => {
        axios.post(baseURL + "/reativarservico/" + srv.id)
            .then(result => {
                setAtualizar(result)
            })
    };

    const limpar = () => {
        setServico({
            nomeCliente: '',
            dataInicio: '',
            dataTermino: '',
            descricaoServico: '',
            valorServico: '',
            valorPago: '',
            dataPagamento: ''
        })
    }

    return (
        <div className="container">
            <h1>Cadastro de Serviços</h1>
            <br />
            <form onSubmit={handleSubmit}>
                <div className='col-12'>

                    <div class="row mb-3">
                        <label htmlFor="nome" className='col-sm-2 col-form-label'>Nome do Cliente</label>
                        <div class="col-sm-10">
                            <input onChange={handleChange} value={servico.nomeCliente || ''} name='nomeCliente' type="text" className='form-control' id='nome' />
                        </div>
                    </div>


                   <div class="row mb-3">
                        <label htmlFor="datainicio" className='col-sm-2 col-form-label'>Data de Início</label>
                        <div class="col-sm-4">
                            <input onChange={handleChange} value={servico.dataInicio || ''} name='dataInicio' type="date" className='form-control' id='datainicio' />
                        </div>

                        <label htmlFor="datatermino" className='col-sm-1 col-form-label'>Término</label>
                        <div class="col-sm-5">
                            <input onChange={handleChange} value={servico.dataTermino || ''} name='dataTermino' type="date" className='form-control' id='datatermino' />
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label htmlFor="descricao" className='col-sm-2 col-form-label'>Descrição do Serviço</label>
                        <div class="col-sm-10">
                            <input onChange={handleChange} value={servico.descricaoServico || ''} name='descricaoServico' type="text" className='form-control' id='descricao' />
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label htmlFor="valorservico" className='col-sm-2 col-form-label'>Valor do Serviço</label>
                        <div class="col-sm-4">
                            <input onChange={handleChange} value={servico.valorServico || ''} name="valorServico" type="text" className='form-control' id='valorservico' />
                        </div>

                        <label htmlFor="valorpago" className='col-sm-1 col-form-label'>Vlr Pago</label>
                        <div class="col-sm-5">
                            <input onChange={handleChange} value={servico.valorPago || ''} name="valorPago" type="text" className='form-control' id='valorpago' />
                        </div>
                    </div>

                    <div class="row mb-3">
                        <label htmlFor="datapagamento" className='col-sm-2 col-form-label'>Data de Pagamento</label>
                        <div class="col-sm-10">
                            <input onChange={handleChange} value={servico.dataPagamento || ''} name='dataPagamento' type="date" className='form-control' id='datapagamento' />
                        </div>
                    </div>
                    <br />
                    <input type="submit" className='btn btn-primary' value="Gravar" />&nbsp;&nbsp;
                    <button className='btn btn-secondary'>Limpar</button>
                </div>
            </form>
            <hr />

            <table class="table table-dark">
                <thead>
                    <tr>
                        <th scope="col">Nome</th>
                        <th scope="col">Descrição</th>
                        <th scope="col">Valor</th>
                        <th scope="col">Data de Início</th>
                        <th scope="col">Status</th>
                        <th scope="col">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        servicos.map(srv => (
                            <tr key={srv.id}> {/* Adicione uma chave única para cada elemento */}
                                <td>{srv.nomeCliente}</td>
                                <td>{srv.descricaoServico}</td>
                                <td>{formatarMoeda(srv.valorServico)}</td>
                                <td>{formatarData(srv.dataInicio)}</td>
                                <td>{srv.status}</td>
                                <td>
                                    <button onClick={() => handleAlterar(srv)} className='btn tamanho-botao botao-alterar' disabled={srv.status === 'cancelado' || srv.status === 'realizado'}>Alterar</button>&nbsp;&nbsp;
                                    <button onClick={() => handleExcluir(srv)} className='btn tamanho-botao botao-excluir' disabled={srv.status === 'realizado'}>Excluir</button>&nbsp;&nbsp;
                                    <button onClick={() => handleCancelar(srv)} className='btn tamanho-botao botao-cancelar' disabled={srv.status === 'cancelado' || srv.status === 'realizado'}>Cancelar</button>&nbsp;&nbsp;
                                    <button onClick={() => handleReativar(srv)} className='btn tamanho-botao botao-reativar' disabled={srv.status === 'pendente'}>Reativar</button>&nbsp;&nbsp;
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </div>
    );
}

export default Servico;
