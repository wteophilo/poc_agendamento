package br.com.wt.agendadoador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.wt.agendadoador.modelo.Laboratorio;

@RepositoryRestResource
public interface LaboratorioRepository extends JpaRepository<Laboratorio,Long>{
	Laboratorio findBycnpj(String cnpj);
}
