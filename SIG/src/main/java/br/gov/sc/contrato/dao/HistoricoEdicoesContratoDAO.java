package br.gov.sc.contrato.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.HistoricoProcessoCODET;
import br.gov.sc.codet.domain.PartesProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.contrato.domain.ContratoTerceirizado;
import br.gov.sc.contrato.domain.HistoricoEdicoesContrato;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class HistoricoEdicoesContratoDAO extends GenericDAO<HistoricoEdicoesContrato>{
	
	
	@SuppressWarnings("unchecked")
	public List<HistoricoEdicoesContrato> listarEdicoesPorContrato(ContratoTerceirizado contrato) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(HistoricoEdicoesContrato.class);
			consulta.add(Restrictions.eq("contrato", contrato));	
			consulta.addOrder(Order.desc("dataCadastro"));
			List<HistoricoEdicoesContrato> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public void salvarHistEdicoes(HistoricoEdicoesContrato hist, ContratoTerceirizado contrato, Usuario usuarioLogado
			) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			
			System.out.println(contrato + "contrato histDAO");
			
			hist.setContrato(contrato);
			hist.setHrContrato(contrato.getHrContrato());
			hist.setnSGPE(contrato.getnSGPE());
			hist.setQtdFuncionarios(contrato.getQtdFuncionarios());
			hist.setValorNF(contrato.getValorNF());
			hist.setDataCadastro(new Date());
			hist.setUsuarioCadastro(usuarioLogado);

			sessao.merge(hist);

			

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
