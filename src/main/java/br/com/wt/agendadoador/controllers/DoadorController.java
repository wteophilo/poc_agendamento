package br.com.wt.agendadoador.controllers;

import java.util.List;

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

import br.com.wt.agendadoador.modelo.Doador;
import br.com.wt.agendadoador.modelo.DoadorRepository;

@RestController
public class DoadorController {

	@Autowired
	private DoadorRepository doadorRepository;
	
	@RequestMapping(value = "/doador/lista", method = RequestMethod.GET,  produces = "application/json")
	public List<Doador> lista() {
		List<Doador> doadores = (List<Doador>) doadorRepository.findAll();
		return doadores;
	}
	
	@RequestMapping(value = "/doador/{id}", method = RequestMethod.GET, produces = "application/json")
	public Doador getDoador(@PathVariable Long id) {
		Doador doador = doadorRepository.findOne(id);
		return doador;
	}
	
	@RequestMapping(value = "/doador/",method = RequestMethod.POST,produces = "application/json" )
	public ResponseEntity<Doador> add(@RequestBody Doador doador,UriComponentsBuilder ucBuilder){
		System.out.println("Criado um novo usuario: " + doador.getNome());
		doadorRepository.save(doador);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(doador.getId()).toUri());
        return new ResponseEntity<Doador>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/doador/update",method = RequestMethod.PUT ,produces = "application/json")
	public ResponseEntity<Void> update(@PathVariable long id,@RequestBody Doador doador,UriComponentsBuilder ucBuilder){
		
		Doador doadorBD = doadorRepository.findOne(id);
		doadorBD.setCep(doador.getCep());
		doadorBD.setCpf(doador.getCpf());
		doadorBD.setEmail(doador.getEmail());
		doadorBD.setEndereco(doador.getEndereco());
		doadorBD.setNome(doador.getNome());
		
		doadorRepository.save(doadorBD);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(doador.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
		
}
