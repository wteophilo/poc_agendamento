package br.com.wt.agendadoador.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	private static final Log LOG = LogFactory.getLog(AgendaController.class);
	

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> add(@RequestBody Agenda agenda, UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		try {
			agenda.setDoador(naoExiste(agenda.getDoador()));
			agenda.setLaboratorio(naoExisteLaboratorio(agenda.getLaboratorio()));
			agenda.setStatusAgenda(StatusAgenda.EMABERTO);
			agendaRepository.save(agenda);
			headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(agenda.getId()).toUri());
			LOG.trace("Salvando agenda...");
			return new ResponseEntity<Void>(headers, HttpStatus.OK);
		} catch (RuntimeErrorException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Void>(headers, HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@RequestMapping(value = "/lista", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Agenda>> lista() {
		List<Agenda> agendas = (List<Agenda>) agendaRepository.findAll();
		LOG.trace("listando agenda...");
		if (agendas == null) {
			return new ResponseEntity<>(agendas, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agendas, HttpStatus.OK);
	}

	@RequestMapping(value = "/buscaPorId/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Agenda> getAgenda(@PathVariable Long id) {
		LOG.trace("buscando agenda por id...");
		Agenda agenda = agendaRepository.findOne(id);
		if (agenda == null) {
			return new ResponseEntity<>(agenda, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agenda, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listaPorLaboratorio/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Agenda>> getAgendasPorLaboratorio(@PathVariable Long id) {
		LOG.trace("buscando agenda por laboratorio...");
		Laboratorio lab = laboratorioRepository.findOne(id);
		List<Agenda> agendas = agendaRepository.findBylaboratorio(lab);
		if (agendas == null) {
			return new ResponseEntity<>(agendas, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agendas, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/buscaPorData/{data}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Agenda> buscaPorData(@PathVariable String data) {
		LOG.trace("buscando agenda por data...");
		Agenda agenda = agendaRepository.findBydataAgendamento(data);
		if (agenda == null) {
			return new ResponseEntity<>(agenda, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agenda, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscaPorProtocolo/{protocolo}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Agenda> buscaPorProtocolo(@PathVariable String protocolo) {
		LOG.trace("buscando agenda por protocolo...");
		Agenda agenda = agendaRepository.findBynumProtocolo(protocolo);
		if (agenda == null) {
			return new ResponseEntity<Agenda>(agenda, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Agenda>(agenda, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/atualizaPorId/{id}", method = RequestMethod.PUT, produces = "application/json")
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
	
	@RequestMapping(value = "/alteraStatusParaConcluido/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Void> updateByStatus(@PathVariable long id,UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		Agenda  agenda = agendaRepository.findOne(id);
		agenda.setStatusAgenda(StatusAgenda.CONCLUIDO);
		agenda.setDataConclusao(geraData());
		agendaRepository.save(agenda);
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deletaPorId/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		LOG.trace("deleta agenda por id...");
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
	
	private String geraData() {
		LocalDateTime time =  LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
		return time.format(formatador).toString();
	}
}
