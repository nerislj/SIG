package br.gov.sc.sgi.domain;


import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class UsuarioStatus extends GenericDomain {
	
	@Column(length = 10, nullable = false, unique = true)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}