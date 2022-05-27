package br.gov.sc.sgi.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.CredenciadoHist;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class CredenciadoDAO extends GenericDAO<Credenciado> {

	public static Credenciado consultaporPessoa(PessoaFisica pessoa) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(Credenciado.class);

		consulta.add(Restrictions.eq("pessoa", pessoa));

		if (consulta.uniqueResult().equals(null)) {
			return null;
		} else {
			return (Credenciado) consulta.uniqueResult();
		}
	}


	public void salvarCredenciado(Credenciado credenciado, PessoaFisica pessoa, CredenciadoHist credenciadoHist, Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			credenciadoHist.setUsuarioCadastro(usuarioLogado);
			credenciadoHist.setPessoa(credenciado);
			credenciadoHist.setCredencialStatus(credenciado.getCredencialStatus());
			credenciadoHist.setCredencialTipo(credenciado.getCredencialTipo());
			credenciadoHist.setDataCadastro(new Date());
			credenciadoHist.setVencimentoCredencial(credenciado.getVencimentoCredencial());
						
			sessao.save(credenciadoHist);

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
	
	@SuppressWarnings("unchecked")
	public List<Credenciado> listarValidadeDesc() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Credenciado.class);
			consulta.addOrder(Order.asc("vencimentoCredencial"));
			List<Credenciado> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	
	public static Credenciado consultaporCpfString(String empresa) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
		Criteria consulta = sessao.createCriteria(Credenciado.class);
System.out.println(consulta);
		consulta.createAlias("pessoa", "p");
		consulta.add(Restrictions.eq("p.cpf", empresa));

		
		Credenciado resultado = (Credenciado) consulta.uniqueResult(); 
		
		return resultado;
		
		
	} catch (RuntimeException erro) {
		throw erro;
	} finally {
		sessao.close();
	}
	}


}



