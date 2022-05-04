package br.gov.sc.sgi.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.gov.sc.sgi.domain.CredencialEndAdicHist;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class CredencialEndAdicHistDAO extends GenericDAO<CredencialEndAdicHist> {

	public void atualizaHistorico(Usuario usuarioLogado, CredencialEndAdicHist endAdicHist) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			endAdicHist.setUsuarioInclusao(usuarioLogado);
			endAdicHist.setDataInclusao(new Date());

			sessao.save(endAdicHist);

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
