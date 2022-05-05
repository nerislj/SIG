package br.gov.sc.cetran.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.QueryHints;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.cetran.domain.ProcessoCetran;
import br.gov.sc.cetran.domain.Requerente;
import br.gov.sc.geapo.dao.GenericDAO;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class ProcessoCetranDAO extends GenericDAO<ProcessoCetran> {

	@SuppressWarnings("unchecked")
	public List<ProcessoCetran> listarTudo(Requerente requerente) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			
			
			
			Criteria consulta = sessao.createCriteria(ProcessoCetran.class);
			
			consulta.add(Restrictions.eq("requerente", requerente));
			
		

			List<ProcessoCetran> resultado = consulta.list();

			return resultado;
			/*
			 * List<ProcessoCetran> list = sessao
			 * .createQuery("from ProcessoCetran where requerente_codigo = '" +
			 * requerente.getCodigo() + "' order by dataCadastro desc")
			 * .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list(); return list;
			 */
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<ProcessoCetran> listaroOrdemCadastro() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			
			
			
			Criteria consulta = sessao.createCriteria(ProcessoCetran.class);
			
			
			consulta.addOrder(Order.desc("dataCadastro"));
		

			List<ProcessoCetran> resultado = consulta.list();

			return resultado;
			/*
			 * List<ProcessoCetran> list = sessao
			 * .createQuery("from ProcessoCetran where requerente_codigo = '" +
			 * requerente.getCodigo() + "' order by dataCadastro desc")
			 * .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list(); return list;
			 */
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ProcessoCetran> listarParaVincular(int anoHoje) throws ParseException {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(ProcessoCetran.class);

			String sDateS = anoHoje + "-01-01";
			Date sDateF = new SimpleDateFormat("yyyy-MM-dd").parse(sDateS);

			String eDateS = anoHoje + "-12-31";
			Date eDateF = new SimpleDateFormat("yyyy-MM-dd").parse(eDateS);

			consulta.add(Restrictions.ge("dataEntrada", sDateF));
			consulta.add(Restrictions.lt("dataEntrada", eDateF));
			consulta.addOrder(Order.desc("dataCadastro"));

			List<ProcessoCetran> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void salvarHistorico(HistoricoProcesso historicoProcessoCetran,
			List<HistoricoProcesso> listaHistoricoProcessos, Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			for (int posicao = 0; posicao < listaHistoricoProcessos.size(); posicao++) {
				HistoricoProcesso historicoSaida = listaHistoricoProcessos.get(posicao);

				historicoSaida.setConselheiro(historicoProcessoCetran.getConselheiro());
				historicoSaida.setSituacao(historicoProcessoCetran.getSituacao());
				historicoSaida.setUsuarioCadastro(usuarioLogado);
				historicoSaida.setDataDistribuicao(historicoProcessoCetran.getDataDistribuicao());
				historicoSaida.setDataJulgamento(historicoProcessoCetran.getDataJulgamento());
				historicoSaida.setDataCadastro(new Date());
				historicoSaida.setDataAtualizado(new Date());

				sessao.save(historicoSaida);

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

}
