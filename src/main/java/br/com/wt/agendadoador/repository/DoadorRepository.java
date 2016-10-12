package br.com.wt.agendadoador.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.wt.agendadoador.modelo.Doador;

@RepositoryRestResource
public interface DoadorRepository  extends JpaRepository<Doador, Long>{
}
