package br.com.wt.agendadoador.controllers;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import br.com.wt.agendadoador.modelo.Agenda;
import br.com.wt.agendadoador.modelo.AgendaRepository;

@RestController
@RequestMapping(value = "agenda")
public class AgendaController {
	
	@Autowired
	private AgendaRepository agendaRepository;

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	public ResponseEntity<Void> add(@RequestBody Agenda agenda, UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		try {
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
		return new ResponseEntity<>(agendas, HttpStatus.FOUND);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Agenda> getAgenda(@PathVariable Long id) {
		Agenda agenda = agendaRepository.findOne(id);
		if (agenda == null) {
			return new ResponseEntity<>(agenda, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agenda, HttpStatus.FOUND);
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
		agendaBD.setDate(agenda.getDate());
	
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
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
