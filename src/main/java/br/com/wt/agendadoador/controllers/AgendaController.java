package br.com.wt.agendadoador.controllers;

import java.sql.SQLException;
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

import br.com.wt.agendadoador.modelo.Agenda;
import br.com.wt.agendadoador.modelo.AgendaRepository;
import br.com.wt.agendadoador.modelo.Doador;
import br.com.wt.agendadoador.modelo.DoadorRepository;

@RestController
public class AgendaController {
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	@Autowired
	private DoadorRepository doadorRepository;
	
	@RequestMapping(value = "/agenda/lista", method = RequestMethod.GET,  produces = "application/json")
	public List<Agenda> lista() {
		List<Agenda> agenda = (List<Agenda>) agendaRepository.findAll();
		return agenda;
	}
	
	
	@RequestMapping(value = "/agenda/",method = RequestMethod.POST ,produces = "application/json")
	public ResponseEntity<Void> add(@RequestBody Agenda agenda ,UriComponentsBuilder ucBuilder){
		System.out.println("Criado um nova data ");
		HttpHeaders headers = new HttpHeaders();
		try{
			//adionadaDoador(agenda.getDoador());
			agendaRepository.save(agenda);
			headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(agenda.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}catch(RuntimeErrorException e){
			System.out.println(e.getMessage());
			return new ResponseEntity<Void>(headers, HttpStatus.PRECONDITION_FAILED);
		}
  
	}
	
	private void adionadaDoador(Doador doador) {
		doadorRepository.save(doador);
	}

}
