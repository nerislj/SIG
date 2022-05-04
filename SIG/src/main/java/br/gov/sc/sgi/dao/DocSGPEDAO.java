package br.gov.sc.sgi.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.DocSGPE;
import br.gov.sc.sgi.domain.DocSGPEHist;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class DocSGPEDAO extends GenericDAO<DocSGPE> {

	public static DocSGPE carregaSgpe(Usuario usuarioLogado, DocSGPE sgpe) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(DocSGPE.class);

		consulta.add(Restrictions.eq("setorAbertura", usuarioLogado.getSetor()));
		consulta.add(Restrictions.eq("unidadeAbertura", usuarioLogado.getUnidade()));
		consulta.add(Restrictions.eq("sgpe", sgpe.getSgpe()));
		consulta.add(Restrictions.eq("sgpeNumero", sgpe.getSgpeNumero()));
		consulta.add(Restrictions.eq("sgpeAno", sgpe.getSgpeAno()));

		if (consulta.uniqueResult().equals(null)) {
			return null;
		} else {
			return (DocSGPE) consulta.uniqueResult();
		}

	}

	@SuppressWarnings("unchecked")
	public List<DocSGPE> carregaSgpeLista(DocSGPE sgpe, Usuario usuarioLogado) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {

			Criteria consulta = sessao.createCriteria(DocSGPE.class);

			consulta.add(Restrictions.eq("setorAbertura", usuarioLogado.getSetor()));
			consulta.add(Restrictions.eq("unidadeAbertura", usuarioLogado.getUnidade()));
			consulta.add(Restrictions.like("descricao", sgpe.getDescricao(), MatchMode.ANYWHERE));
			consulta.add(Restrictions.like("palavrachave", sgpe.getPalavrachave(), MatchMode.ANYWHERE));

			List<DocSGPE> resultado = consulta.list();

			return resultado;
			
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void salvarSgpeHist(DocSGPE sgpe, DocSGPEHist sgpehist, Usuario usuarioLogado) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			sgpehist.setDocsgpe(sgpe);
			sgpehist.setDataAcao(new Date());
			sgpehist.setUsuario(usuarioLogado);
			sgpehist.setOrigemUnidade(usuarioLogado.getUnidade());
			sgpehist.setOrigemSetor(usuarioLogado.getSetor());

			sessao.save(sgpehist);

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
