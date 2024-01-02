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

    const handleChange = (event) => {
        setServico({...servico, [event.target.name]: event.target.value})
    }

    const handleSubmit = (event) => {
        event.preventDefault()
        axios.post(baseURL, servico)
            .then(result => {
                console.log(result)
            })
    }



  return (
    <div className="container">
        <h1>Cadastro de Serviços</h1>
        <br />
        <form onSubmit={handleSubmit}>
          <div className='col-6'>
            
            <div class="row mb-3">
                <label htmlFor="nome" className='col-sm-2 col-form-label'>Nome</label>
                <div class="col-sm-10">
                    <input onChange={handleChange} value={servico.nomeCliente} name='nomeCliente' type="text" className='form-control' id='nome'/>
                </div>
            </div>

            <div class="row mb-3">
                <label htmlFor="datainicio" className='col-sm-2 col-form-label'>Início</label>
                <div class="col-sm-10">
                    <input onChange={handleChange} value={servico.dataInicio} name='dataInicio' type="date" className='form-control' id='datainicio'/>
                </div>
            </div>
            
            <div class="row mb-3">
                <label htmlFor="datatermino" className='col-sm-2 col-form-label'>Término</label>
                <div class="col-sm-10">
                    <input onChange={handleChange} value={servico.dataTermino} name='dataTermino' type="date" className='form-control' id='datatermino'/>
                </div>
            </div>

            <div class="row mb-3">
                <label htmlFor="descricao" className='col-sm-2 col-form-label'>Descrição</label>
                <div class="col-sm-10">
                    <input onChange={handleChange} value={servico.descricaoServico} name='descricaoServico' type="text" className='form-control' id='descricao'/>
                </div>
            </div>

            <div class="row mb-3">
                <label htmlFor="valorservico" className='col-sm-2 col-form-label'>$ Serviço</label>
                <div class="col-sm-10">
                    <input onChange={handleChange} value={servico.valorServico} name="valorServico" type="text" className='form-control' id='valorservico'/>
                </div>
            </div>

            <div class="row mb-3">
                <label htmlFor="valorpago" className='col-sm-2 col-form-label'>$ Pago</label>
                <div class="col-sm-10">
                    <input onChange={handleChange} value={servico.valorPago} name="valorPago" type="text" className='form-control' id='valorpago'/>
                </div>
            </div>

            <div class="row mb-3">
                <label htmlFor="datapagamento" className='col-sm-2 col-form-label'>Data Pag.</label>
                <div class="col-sm-10">
                    <input onChange={handleChange} value={servico.dataPagamento} name='dataPagamento' type="date" className='form-control' id='datapagamento'/>
                </div>
            </div>
            <br />
            <input type="submit" className='btn btn-success' value="Cadastrar" />
          </div>
        </form>
    </div>
  );
}

export default Servico;
