package br.com.wt.agendadoador.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.wt.agendadoador.modelo.Agenda;


@RepositoryRestResource
public interface AgendaRepository extends CrudRepository<Agenda, Long>{ 

}
