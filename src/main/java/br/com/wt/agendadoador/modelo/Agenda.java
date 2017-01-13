package br.com.wt.agendadoador.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Agenda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "doador_id")
	private Doador doador;
	@Enumerated(EnumType.STRING)
	private StatusAgenda statusAgenda;
	@OneToOne(cascade ={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "laboratorio_id")
	private Laboratorio laboratorio;
	private String dataAgendamento;
	private String dataConclusao;
	private String numProtocolo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Doador getDoador() {
		return doador;
	}

	public void setDoador(Doador doador) {
		this.doador = doador;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}

	public String getDataAgendamento() {
		return dataAgendamento;
	}

	public void setDataAgendamento(String dataAgendamento) {
		this.dataAgendamento = dataAgendamento;
	}

	public StatusAgenda getStatusAgenda() {
		return statusAgenda;
	}
	
	public String getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(String dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public void setStatusAgenda(StatusAgenda statusAgenda) {
		this.statusAgenda = statusAgenda;
	}

	public String getNumProtocolo() {
		return numProtocolo;
	}

	public void setNumProtocolo(String numProtocolo) {
		this.numProtocolo = numProtocolo;
	}

	@Override
	public String toString() {
		return "Agenda [id=" + id + ", doador=" + doador + ", statusAgenda=" + statusAgenda + ", laboratorio="
				+ laboratorio + ", dataAgendamento=" + dataAgendamento + ", dataConclusao=" + dataConclusao
				+ ", numProtocolo=" + numProtocolo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataAgendamento == null) ? 0 : dataAgendamento.hashCode());
		result = prime * result + ((dataConclusao == null) ? 0 : dataConclusao.hashCode());
		result = prime * result + ((doador == null) ? 0 : doador.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((laboratorio == null) ? 0 : laboratorio.hashCode());
		result = prime * result + ((numProtocolo == null) ? 0 : numProtocolo.hashCode());
		result = prime * result + ((statusAgenda == null) ? 0 : statusAgenda.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Agenda other = (Agenda) obj;
		if (dataAgendamento == null) {
			if (other.dataAgendamento != null)
				return false;
		} else if (!dataAgendamento.equals(other.dataAgendamento))
			return false;
		if (dataConclusao == null) {
			if (other.dataConclusao != null)
				return false;
		} else if (!dataConclusao.equals(other.dataConclusao))
			return false;
		if (doador == null) {
			if (other.doador != null)
				return false;
		} else if (!doador.equals(other.doador))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (laboratorio == null) {
			if (other.laboratorio != null)
				return false;
		} else if (!laboratorio.equals(other.laboratorio))
			return false;
		if (numProtocolo == null) {
			if (other.numProtocolo != null)
				return false;
		} else if (!numProtocolo.equals(other.numProtocolo))
			return false;
		if (statusAgenda != other.statusAgenda)
			return false;
		return true;
	}

}
