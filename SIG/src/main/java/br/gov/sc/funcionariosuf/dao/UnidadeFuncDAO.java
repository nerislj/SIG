
package br.gov.sc.funcionariosuf.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.cetran.domain.Conselheiro;
import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.contrato.dao.FuncionarioTerceirizadoDAO;
import br.gov.sc.contrato.domain.ContratoRelacao;
import br.gov.sc.contrato.domain.ContratoTerceirizado;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.funcionariosuf.domain.Servidores;
import br.gov.sc.funcionariosuf.domain.UnidadeFunc;
import br.gov.sc.pesquisa.domain.Pesquisa;
import br.gov.sc.sgi.dao.CredenciadoDAO;
import br.gov.sc.sgi.dao.CredenciadoEmpDAO;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.util.HibernateUtil;

public class UnidadeFuncDAO extends GenericDAO<UnidadeFunc> {

	@SuppressWarnings("unchecked")
	public List<UnidadeFunc> buscarPorCiretran(Long estadoCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(UnidadeFunc.class);
			consulta.add(Restrictions.eq("unidade.codigo", estadoCodigo));
			consulta.addOrder(Order.asc("unidadeNome"));
			List<UnidadeFunc> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<UnidadeFunc> buscarPorEstadoNome(String nome) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(UnidadeFunc.class);

			consulta.createAlias("unidade", "e");
			consulta.add(Restrictions.eq("e.unidadeNome", nome));
			consulta.addOrder(Order.asc("unidadeNome"));
			List<UnidadeFunc> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}


	public UnidadeFunc loadNomeString(String nome) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(UnidadeFunc.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("unidadeNome", nome));

		return (UnidadeFunc) criteria.setMaxResults(1).uniqueResult();
	}

	private List<FuncionarioTerceirizado> listaTerceirizados;
	private List<Servidores> listaServidores;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UnidadeFunc> listaTodosFuncionarios() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			List lista = new ArrayList();

			FuncionarioTerceirizadoDAO terceirizadosDAO = new FuncionarioTerceirizadoDAO();
			listaTerceirizados = terceirizadosDAO.listar();

			ServidoresDAO servDAO = new ServidoresDAO();
			listaServidores = servDAO.listar();

			for (int i = 0; i < listaTerceirizados.size(); i++) {
				if (listaServidores.size() > i) {
					String nome1 = (String) "Nome Completo: " + listaTerceirizados.get(i).getPessoa().getNomeCompleto()
							+ "- " + "Unidade " + listaTerceirizados.get(i).getUnidade().getUnidadeNome();
					String nome2 = (String) "Nome Completo: " + listaServidores.get(i).getPessoa().getNomeCompleto() + "- " + "Unidade "
							+ listaTerceirizados.get(i).getUnidade().getUnidadeNome();
					lista.add(nome1);
					lista.add(nome2);

				} else {
					String nome1 = (String) listaTerceirizados.get(i).getPessoa().getNomeCompleto();
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

	public List<FuncionarioTerceirizado> getListaTerceirizados() {
		return listaTerceirizados;
	}

	public void setListaTerceirizados(List<FuncionarioTerceirizado> listaTerceirizados) {
		this.listaTerceirizados = listaTerceirizados;
	}

	public List<Servidores> getListaServidores() {
		return listaServidores;
	}

	public void setListaServidores(List<Servidores> listaServidores) {
		this.listaServidores = listaServidores;
	}
	
	 @SuppressWarnings("unchecked")
		public List<UnidadeFunc> listarPorClaims(List<String> unidades) {

			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
			try {

				Criteria consulta = sessao.createCriteria(UnidadeFunc.class);
				consulta.createAlias("unidade", "u");
				consulta.add(Restrictions.in("u.unidadeNome", unidades));
				
				

				List<UnidadeFunc> resultado = consulta.list();

				return resultado;

			} catch (RuntimeException erro) {
				throw erro;
			} finally {
				sessao.close();
			}
		}
	
	

}