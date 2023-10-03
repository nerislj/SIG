
package br.gov.sc.pesquisa.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.pesquisa.domain.Pesquisa;
import br.gov.sc.sgi.dao.CredenciadoDAO;
import br.gov.sc.sgi.dao.CredenciadoEmpDAO;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.util.HibernateUtil;

public class PesquisaDAO extends GenericDAO<Pesquisa> {

	private List<CredenciadoEmp> listaCredenciadoEmp;
	private List<Credenciado> listaCredenciado;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Pesquisa> listaCredenciados() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			List lista = new ArrayList();

			

			CredenciadoEmpDAO credenciadoEmpDAO = new CredenciadoEmpDAO();
			listaCredenciadoEmp = credenciadoEmpDAO.listar();

			CredenciadoDAO credenciadoDAO = new CredenciadoDAO();
			listaCredenciado = credenciadoDAO.listar();

			for (int i = 0; i < listaCredenciadoEmp.size(); i++) {
				if (listaCredenciado.size() > i) {
					String nome1 = (String) listaCredenciadoEmp.get(i).getPessoaJuridica().getNomeFantasia() + " - CNPJ " + listaCredenciadoEmp.get(i).getPessoaJuridica().getCnpj();
					String nome2 = (String) listaCredenciado.get(i).getPessoa().getNomeCompleto() + " - CPF " + listaCredenciado.get(i).getPessoa().getCpf();
					lista.add(nome1);
					lista.add(nome2);
				} else {
					String nome1 = (String) listaCredenciadoEmp.get(i).getPessoaJuridica().getNomeFantasia() + " - CNPJ " + listaCredenciadoEmp.get(i).getPessoaJuridica().getCnpj();
					lista.add(nome1);
				}
			}

			return lista;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public List<CredenciadoEmp> getListaCredenciadoEmp() {
		return listaCredenciadoEmp;
	}

	public void setListaCredenciadoEmp(List<CredenciadoEmp> listaCredenciadoEmp) {
		this.listaCredenciadoEmp = listaCredenciadoEmp;
	}

	public List<Credenciado> getListaCredenciado() {
		return listaCredenciado;
	}

	public void setListaCredenciado(List<Credenciado> listaCredenciado) {
		this.listaCredenciado = listaCredenciado;
	}

}