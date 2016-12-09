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

import br.com.wt.agendadoador.modelo.Doador;
import br.com.wt.agendadoador.repository.DoadorRepository;

@RestController
@RequestMapping(value = "doador")
public class DoadorController {

	@Autowired
	private DoadorRepository doadorRepository;

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> add(@RequestBody Doador doador, UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		try {
			if(naoExiste(doador.getRg())){
				doadorRepository.save(doador);
				headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(doador.getId()).toUri());
			}
			return new ResponseEntity<Void>(headers, HttpStatus.OK);
		} catch (RuntimeErrorException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<Void>(headers, HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@RequestMapping(value = "/lista", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Doador>> lista() {
		List<Doador> doadors = (List<Doador>) doadorRepository.findAll();
		if (doadors == null) {
			return new ResponseEntity<>(doadors, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(doadors, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/buscaPorRg/{rg}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Doador> buscaPorRg(@PathVariable String rg) {
		Doador doador = doadorRepository.findByrg(rg);
		if (doador == null) {
			return new ResponseEntity<>(doador, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(doador, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/buscaPorID/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Doador> getDoador(@PathVariable Long id) {
		Doador doador = doadorRepository.findOne(id);
		if (doador == null) {
			return new ResponseEntity<>(doador, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(doador, HttpStatus.OK);
	}

	@RequestMapping(value = "/atualizaPorID/{id}", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Void> update(@PathVariable long id, @RequestBody Doador doador,
			UriComponentsBuilder ucBuilder) {
		HttpHeaders headers = new HttpHeaders();
		Doador doadorBD = doadorRepository.findOne(id);

		if (doadorBD == null) {
			return new ResponseEntity<Void>(headers, HttpStatus.NOT_FOUND);
		}

		doadorBD =doador;

		doadorRepository.save(doadorBD);
		headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(doador.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);

	}

	@RequestMapping(value = "/deletaPorID/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		Doador doador = doadorRepository.findOne(id);
		if (doador == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		doadorRepository.delete(doador);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	private boolean naoExiste(String rg) {
		Doador doador = doadorRepository.findByrg(rg);
		if (doador == null){
			return true;
		}else{
			return false;
		}
	}

}
