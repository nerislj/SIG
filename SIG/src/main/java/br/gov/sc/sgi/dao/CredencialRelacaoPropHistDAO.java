package br.gov.sc.sgi.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.gov.sc.sgi.domain.CredencialRelacaoCredHist;
import br.gov.sc.sgi.domain.CredencialRelacaoPropHist;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class CredencialRelacaoPropHistDAO extends GenericDAO<CredencialRelacaoCredHist>{

	public void atualizaHistorico(Usuario usuarioLogado, CredencialRelacaoPropHist relacaoProprietarioHist) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			relacaoProprietarioHist.setUsuarioAlteracao(usuarioLogado);
			relacaoProprietarioHist.setDataInclusao(new Date());

			sessao.save(relacaoProprietarioHist);

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





