package br.gov.sc.sgi.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.cetran.domain.Conselheiro;
import br.gov.sc.codet.domain.NomenclaturaProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.CredenciadoEmpHist;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class CredenciadoEmpDAO extends GenericDAO<CredenciadoEmp> {
	
	public static CredenciadoEmp loadCredenciado(PessoaJuridica empresa) {
		
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		
		
		Criteria criteria = sessao.createCriteria(CredenciadoEmp.class);
	
		criteria.add(Restrictions.eq("pessoaJuridica", empresa));
		

		return (CredenciadoEmp) criteria.setMaxResults(1).uniqueResult();
		
		
	
	}

	public static CredenciadoEmp consultaporCnpj(PessoaJuridica empresa) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(CredenciadoEmp.class);

		consulta.add(Restrictions.eq("pessoaJuridica", empresa));

		
			return (CredenciadoEmp) consulta.setMaxResults(1).uniqueResult();
		
	}
	
	public static CredenciadoEmp consultaporCnpjString(String empresa) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
		Criteria consulta = sessao.createCriteria(CredenciadoEmp.class);
System.out.println(consulta);
		consulta.createAlias("pessoaJuridica", "p");
		consulta.add(Restrictions.eq("p.cnpj", empresa));

		
		CredenciadoEmp resultado = (CredenciadoEmp) consulta.uniqueResult(); 
		
		return resultado;
		
		
	} catch (RuntimeException erro) {
		throw erro;
	} finally {
		sessao.close();
	}
	}

	
	public void salvarEmpresaNaoVirtual(PessoaJuridica empresa, CredenciadoEmp credenciadoEmp) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			credenciadoEmp.setDataCadastro(new Date());
			credenciadoEmp.setVencimentoCredencial(credenciadoEmp.getVencimentoCredencial());
			credenciadoEmp.setCredencialStatus(credenciadoEmp.getCredencialStatus());
			credenciadoEmp.setCredencialTipo(credenciadoEmp.getCredencialTipo());
			credenciadoEmp.setPessoaJuridica(empresa);
			
						
			sessao.merge(credenciadoEmp);
			
			empresa.setCredenciadoEmpVirtual("Não");
			
			sessao.merge(empresa);

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
	
	

	public void salvarCredenciadoPrimeiroCadastro(CredenciadoEmp credenciado, PessoaJuridica empresa, CredenciadoEmpHist credenciadoHist, Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
	

			credenciado.setDataCadastro(new Date());
			
			credenciado.setPessoaJuridica(empresa);
			
			sessao.save(credenciado);
			
			System.out.println(credenciado);
			credenciadoHist.setUsuarioCadastro(usuarioLogado);
			credenciadoHist.setEmpresa(credenciado);
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
	
	public static PessoaJuridica carregaEmpresa(String cnpj) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
		Criteria consulta = sessao.createCriteria(PessoaJuridica.class);

		
		consulta.add(Restrictions.eq("cnpj", cnpj));
		
		
		return (PessoaJuridica) consulta.setMaxResults(1).uniqueResult();
		
		
		
		
	} catch (RuntimeException erro) {
		throw erro;
	} finally {
		sessao.close();
	}
	}
	
	public void salvarCredenciado(CredenciadoEmp credenciado, PessoaJuridica empresa, CredenciadoEmpHist credenciadoHist, Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			
	
			credenciadoHist.setUsuarioCadastro(usuarioLogado);
			credenciadoHist.setEmpresa(credenciado);
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
	public List<CredenciadoEmp> listarValidadeDesc() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(CredenciadoEmp.class);
			consulta.addOrder(Order.asc("vencimentoCredencial"));
			List<CredenciadoEmp> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}


	
	public static PessoaJuridica carregarCredenciadoEmp(String empresa) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		
		Criteria criteria = sessao.createCriteria(PessoaJuridica.class);
		
		criteria.add(Restrictions.eq("cnpj", empresa));
		

		return (PessoaJuridica) criteria.setMaxResults(1).uniqueResult();
	}
	
	

}



