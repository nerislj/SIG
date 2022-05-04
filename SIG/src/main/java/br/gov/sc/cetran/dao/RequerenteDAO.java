package br.gov.sc.cetran.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.cetran.domain.ProcessoCetran;
import br.gov.sc.cetran.domain.Requerente;
import br.gov.sc.geapo.dao.GenericDAO;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class RequerenteDAO extends GenericDAO<Requerente> {

	public void salvarProcesso(Requerente requerente, ProcessoCetran processoCetran, Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			if (processoCetran.getPlaca() == null || processoCetran.getRecorrido() == null
					|| processoCetran.getNumero() == null || processoCetran.getDataEntrada() == null) {

				if (requerente.getDataCadastro() != null || requerente.getDataCadastro() != null) {

					requerente.setNome(requerente.getNome().toUpperCase());
					requerente.setDataCadastro(new java.util.Date());
					requerente.setUsuarioCadastro(usuarioLogado);

					sessao.merge(requerente);

				} else {
					requerente.setNome(requerente.getNome().toUpperCase());
					requerente.setDataCadastro(new java.util.Date());
					requerente.setUsuarioCadastro(usuarioLogado);

					sessao.save(requerente);
				}

			} else if(processoCetran.getPlaca() != null || processoCetran.getRecorrido() != null
					|| processoCetran.getNumero() != null || processoCetran.getDataEntrada() != null) {
				
				if (requerente.getDataCadastro() != null || requerente.getDataCadastro() != null) {

					requerente.setNome(requerente.getNome().toUpperCase());
					requerente.setDataCadastro(new java.util.Date());
					requerente.setUsuarioCadastro(usuarioLogado);

					System.out.println(requerente);
					sessao.merge(requerente);
					
					processoCetran.setDataCadastro(new java.util.Date());
					processoCetran.setRequerente(requerente);
					processoCetran.setUsuarioCadastro(usuarioLogado);
					processoCetran.setRecorrido(processoCetran.getRecorrido().toUpperCase());
					processoCetran.setPlaca(processoCetran.getPlaca().toUpperCase());

					System.out.println(processoCetran);
					sessao.merge(processoCetran);
					
				}
				else {
					
					requerente.setNome(requerente.getNome().toUpperCase());
					requerente.setDataCadastro(new java.util.Date());
					requerente.setUsuarioCadastro(usuarioLogado);

					System.out.println(requerente);
					sessao.save(requerente);

					processoCetran.setDataCadastro(new java.util.Date());
					processoCetran.setRequerente(requerente);
					processoCetran.setUsuarioCadastro(usuarioLogado);
					processoCetran.setRecorrido(processoCetran.getRecorrido().toUpperCase());
					processoCetran.setPlaca(processoCetran.getPlaca().toUpperCase());

					sessao.merge(processoCetran);
				}
				


				

			} 

			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public static Requerente carregarCpf(String cpf, String cnpj) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(Requerente.class);

		SimpleExpression condicao1 = Restrictions.eq("cpf", cpf);
		SimpleExpression condicao2 = Restrictions.eq("cnpj", cnpj);
		consulta.add(Restrictions.or(condicao1, condicao2)).setMaxResults(1).uniqueResult();

		if (consulta.uniqueResult().equals(null)) {
			return null;
		} else {
			return (Requerente) consulta.setMaxResults(1).uniqueResult();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Requerente> listarTudo() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Query query = sessao.createQuery("from Requerente"); // You will get Weayher object
			List<Requerente> list = query.list(); // You are accessing as list<WeatherModel>

			return list;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
