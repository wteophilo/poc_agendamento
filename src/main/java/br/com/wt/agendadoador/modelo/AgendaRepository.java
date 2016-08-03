package br.com.wt.agendadoador.modelo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface AgendaRepository extends CrudRepository<Agenda, Long>{ 

}
