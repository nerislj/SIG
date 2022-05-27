package br.gov.sc.codet.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.CredenciadoHist;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class ProcessoDAO extends GenericDAO<Processo> {

	@SuppressWarnings("unchecked")
	public List<Processo> listarProcessos(String campoDigitado, Credenciado cpf, CredenciadoEmp empresaCNPJ) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Processo.class);

			if (cpf == null) {

				consulta.add(Restrictions.disjunction()

						.add(Restrictions.eq("credenciadoPJ", empresaCNPJ))
						.add(Restrictions.eq("numProcesso", campoDigitado))
						.add(Restrictions.eq("numSGPE", campoDigitado)));

				List<Processo> resultado = consulta.list();
				return resultado;
			} else {
				consulta.createAlias("partesProcesso", "e");

				consulta.add(Restrictions.disjunction().add(Restrictions.eq("e.credenciado", cpf))
						.add(Restrictions.eq("credenciadoPJ", empresaCNPJ))
						.add(Restrictions.eq("numProcesso", campoDigitado))
						.add(Restrictions.eq("numSGPE", campoDigitado)));

				List<Processo> resultado = consulta.list();
				return resultado;
			}

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public void salvarFases(FasesProcesso fasesProcesso, Processo processo, Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			fasesProcesso.setUsuarioCadastro(usuarioLogado);
			fasesProcesso.setDataCadastro(new Date());
			fasesProcesso.setProcesso(processo);
			fasesProcesso.setOcorrencia(processo.getNomenclatura().getDescricao() + " Nº Processo " + processo.getNumProcesso() + " Nº SGPE " + processo.getNumSGPE());
			fasesProcesso.setAnotacao(processo.getSituacao().getDescricao());
			fasesProcesso.setProvidencia("Setor Atual: " + processo.getSetorAtual().getDescricao());
			
						
			sessao.save(fasesProcesso);

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
	
	public void salvarAdicionarNovaFase(FasesProcesso fasesProcesso, Processo processo, Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			fasesProcesso.setUsuarioCadastro(usuarioLogado);
			fasesProcesso.setDataCadastro(new Date());
			fasesProcesso.setProcesso(processo);
			fasesProcesso.setOcorrencia(fasesProcesso.getOcorrencia());
			fasesProcesso.setAnotacao(fasesProcesso.getAnotacao());
			fasesProcesso.setProvidencia(fasesProcesso.getProvidencia());
			
						
			sessao.save(fasesProcesso);

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
