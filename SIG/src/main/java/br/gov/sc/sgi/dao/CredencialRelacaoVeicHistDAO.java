package br.gov.sc.sgi.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.gov.sc.sgi.domain.CredencialRelacaoVeicHist;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class CredencialRelacaoVeicHistDAO extends GenericDAO<CredencialRelacaoVeicHist>{

	public void atualizaHistorico(Usuario usuarioLogado, CredencialRelacaoVeicHist relacaoVeic) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			relacaoVeic.setUsuarioAlteracao(usuarioLogado);
			relacaoVeic.setDataInclusao(new Date());

			sessao.save(relacaoVeic);

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
}







