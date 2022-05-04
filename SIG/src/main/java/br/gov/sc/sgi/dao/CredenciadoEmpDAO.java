package br.gov.sc.sgi.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.CredenciadoEmpHist;
import br.gov.sc.sgi.domain.PessoaJuridica;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class CredenciadoEmpDAO extends GenericDAO<CredenciadoEmp> {

	public static CredenciadoEmp consultaporCnpj(PessoaJuridica empresa) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(CredenciadoEmp.class);

		consulta.add(Restrictions.eq("pessoaJuridica", empresa));

		if (consulta.uniqueResult().equals(null)) {
			return null;
		} else {
			return (CredenciadoEmp) consulta.uniqueResult();
		}
	}

	public void salvarCredenciado(CredenciadoEmp credenciado, PessoaJuridica pessoa, CredenciadoEmpHist credenciadoHist, Usuario usuarioLogado) {
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



}



