package br.com.wt.agendadoador.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.wt.agendadoador.modelo.Agenda;
import br.com.wt.agendadoador.modelo.Laboratorio;


@RepositoryRestResource
public interface AgendaRepository extends JpaRepository<Agenda, Long>{ 
	Agenda findBydataAgendamento(String data);
	Agenda findBynumProtocolo(String protocolo);
	List<Agenda> findBylaboratorio(Laboratorio laboratorio);
}
