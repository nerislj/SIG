
package br.gov.sc.visita.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.gov.sc.sgi.dao.CidadeDAO;
import br.gov.sc.sgi.dao.EstadoDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Estado;
import br.gov.sc.visita.dao.CategoriaVisitaDAO;
import br.gov.sc.visita.dao.DestinoVisitaDAO;
import br.gov.sc.visita.dao.VisitanteDAO;
import br.gov.sc.visita.domain.CategoriaVisita;
import br.gov.sc.visita.domain.DestinoVisita;
import br.gov.sc.visita.domain.Visitante;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DestinoBean implements Serializable {
	
	private DestinoVisita destinoVisita;
	private List<DestinoVisita> listaDestinoVisitas;
	
	

	@PostConstruct
	public void listar() {
		try {

			DestinoVisitaDAO destinoDAO = new DestinoVisitaDAO();
			listaDestinoVisitas = destinoDAO.listar();
			
		
			

			destinoVisita = new DestinoVisita();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar o Destino.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			DestinoVisitaDAO destinoDAO = new DestinoVisitaDAO();

			destinoVisita.setDestino(destinoVisita.getDestino().toUpperCase());
			
			destinoDAO.merge(destinoVisita);

			destinoVisita = new DestinoVisita();

			listaDestinoVisitas = destinoDAO.listar();

			Messages.addGlobalInfo("Destino cadastrado com Sucesso!");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Destino.");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			destinoVisita = (DestinoVisita) evento.getComponent().getAttributes().get("destinoVisitaSelecionado");

			DestinoVisitaDAO destinoDAO = new DestinoVisitaDAO();
			destinoDAO.excluir(destinoVisita);

			listaDestinoVisitas = destinoDAO.listar();

			Messages.addGlobalInfo("Destino removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Categoria.");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			destinoVisita = (DestinoVisita) evento.getComponent().getAttributes().get("destinoVisitaSelecionado");

			DestinoVisitaDAO destinoDAO = new DestinoVisitaDAO();
			listaDestinoVisitas = destinoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o Destino.");
			erro.printStackTrace();
		}
	}

	public DestinoVisita getDestinoVisita() {
		return destinoVisita;
	}

	public void setDestinoVisita(DestinoVisita destinoVisita) {
		this.destinoVisita = destinoVisita;
	}

	public List<DestinoVisita> getListaDestinoVisitas() {
		return listaDestinoVisitas;
	}

	public void setListaDestinoVisitas(List<DestinoVisita> listaDestinoVisitas) {
		this.listaDestinoVisitas = listaDestinoVisitas;
	}

	

	
}