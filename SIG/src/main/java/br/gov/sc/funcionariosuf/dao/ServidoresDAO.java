
package br.gov.sc.funcionariosuf.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.PartesProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.funcionariosuf.domain.CiretranCitran;
import br.gov.sc.funcionariosuf.domain.Servidores;
import br.gov.sc.funcionariosuf.domain.Terceirizados;
import br.gov.sc.funcionariosuf.domain.UnidadeCiretranCitran;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class ServidoresDAO extends GenericDAO<Servidores> {

	public static PessoaFisica carregarCpf(String cpf) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(PessoaFisica.class);
		SimpleExpression condicao1 = Restrictions.eq("cpf", cpf);
		consulta.add(Restrictions.or(new Criterion[] { condicao1 })).setMaxResults(1).uniqueResult();
		return consulta.uniqueResult().equals((Object) null) ? null
				: (PessoaFisica) consulta.setMaxResults(1).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Servidores> listarPorUnidade(Object processo, Setor setor) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Servidores.class);
			consulta.add(Restrictions.eq("ciretranCitran", processo));	
			consulta.add(Restrictions.eq("setor", setor));
			consulta.addOrder(Order.asc("codigo"));
			List<Servidores> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Servidores> listarPorUnidadeCodigo(CiretranCitran processo, Setor setor) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Servidores.class);
			consulta.add(Restrictions.eq("ciretranCitran", processo));	
			consulta.add(Restrictions.eq("setor", setor));	
			consulta.addOrder(Order.asc("codigo"));
			List<Servidores> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	

}