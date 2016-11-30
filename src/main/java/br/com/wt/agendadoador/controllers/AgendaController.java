package br.com.wt.agendadoador.controllers;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.wt.agendadoador.modelo.Agenda;
import br.com.wt.agendadoador.modelo.Doador;
import br.com.wt.agendadoador.modelo.Laboratorio;
import br.com.wt.agendadoador.modelo.StatusAgenda;
import br.com.wt.agendadoador.repository.AgendaRepository;
import br.com.wt.agendadoador.repository.DoadorRepository;
import br.com.wt.agendadoador.repository.LaboratorioRepository;

@RestController
@RequestMapping(value = "agenda")
public class AgendaController {

	@Autowired
	private AgendaRepository agendaRepository;
	@Autowired
	private DoadorRepository doadorRepository;
	@Autowired
	private LaboratorioRepository laboratorioRepository;
	

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> add(@RequestBody Agenda agenda, UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		try {
			agenda.setDoador(naoExiste(agenda.getDoador()));
			agenda.setLaboratorio(naoExisteLaboratorio(agenda.getLaboratorio()));
			agenda.setStatusAgenda(StatusAgenda.EMABERTO);
			agendaRepository.save(agenda);
			headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(agenda.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.OK);
		} catch (RuntimeErrorException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Void>(headers, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@RequestMapping(value = "/lista", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Agenda>> lista() {
		List<Agenda> agendas = (List<Agenda>) agendaRepository.findAll();
		if (agendas == null) {
			return new ResponseEntity<>(agendas, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agendas, HttpStatus.OK);
	}

	@RequestMapping(value = "/buscaPorId/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Agenda> getAgenda(@PathVariable Long id) {
		Agenda agenda = agendaRepository.findOne(id);
		if (agenda == null) {
			return new ResponseEntity<>(agenda, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agenda, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/buscaPorData/{data}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Agenda> buscaPorData(@PathVariable String data) {
		Agenda agenda = agendaRepository.findBydataAgendamento(data);
		if (agenda == null) {
			return new ResponseEntity<>(agenda, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agenda, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscaPorProtocolo/{protocolo}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Agenda> buscaPorProtocolo(@PathVariable String protocolo) {
		Agenda agenda = agendaRepository.findBynumProtocolo(protocolo);
		if (agenda == null) {
			return new ResponseEntity<>(agenda, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agenda, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Agenda agenda,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		Agenda agendaBD = agendaRepository.findOne(id);

		if (agendaBD == null) {
			return new ResponseEntity<Void>(headers, HttpStatus.NOT_FOUND);
		}

		agendaBD.setLaboratorio(agenda.getLaboratorio());
		agendaBD.setDoador(agenda.getDoador());
		agendaBD.setDataAgendamento(agenda.getDataAgendamento());

		agendaRepository.save(agendaBD);
		headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(agenda.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		Agenda agenda = agendaRepository.findOne(id);
		if (agenda == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		agendaRepository.delete(agenda);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private Doador naoExiste(Doador doador) {
		Doador dbDoador = doadorRepository.findByrg(doador.getRg());
		if (dbDoador == null){
			return doador;
		}else{
			return dbDoador;
		}
	}
	
	private Laboratorio naoExisteLaboratorio(Laboratorio lab){
		Laboratorio dbLaboratorio = laboratorioRepository.findBycnpj(lab.getCnpj());
		if (dbLaboratorio == null){
			return lab;
		}else{
			return dbLaboratorio;
		}	
	}
}
