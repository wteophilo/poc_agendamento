package br.com.wt.agendadoador.controllers;

import java.util.List;

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

@RestController
public class AgendaController {
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	@RequestMapping(value = "/agenda/lista", method = RequestMethod.GET,  produces = "application/json")
	public List<Agenda> lista() {
		List<Agenda> agenda = (List<Agenda>) agendaRepository.findAll();
		return agenda;
	}
	
	
	@RequestMapping(value = "/agenda/",method = RequestMethod.POST ,produces = "application/json")
	public ResponseEntity<Void> add(@RequestBody Agenda agenda ,UriComponentsBuilder ucBuilder){
		System.out.println("Criado um nova data ");
		agendaRepository.save(agenda);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(agenda.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

}
