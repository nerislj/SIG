package br.gov.sc.sgi.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.gov.sc.sgi.domain.RecursoMulta;
import br.gov.sc.sgi.domain.RecursoMultaTramita;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class RecursoMultaTramitaDAO extends GenericDAO<RecursoMultaTramita> {
	

	public void recebeProcesso(List<RecursoMulta> listaRecursoSaida, Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			for (int posicao = 0; posicao < listaRecursoSaida.size(); posicao++) {
				
				RecursoMulta recursomulta = listaRecursoSaida.get(posicao);
				
				recursomulta.setStatus("Em Aberto");
				recursomulta.setUnidadeAtual(usuario.getUnidade());
				recursomulta.setSetorAtual(usuario.getSetor());
												
				RecursoMultaTramita tramitaSaida = new RecursoMultaTramita();
			
				//Ação 2 = Receber
				tramitaSaida.setAcao(2);
				tramitaSaida.setDataTramita(new Date());
				tramitaSaida.setOrigemUnidade(usuario.getUnidade());
				tramitaSaida.setOrigem(usuario.getSetor());
				tramitaSaida.setRecursomulta(recursomulta);
				tramitaSaida.setUsuario(usuario);
				tramitaSaida.setDestino(usuario.getUnidade().getUnidade().toString() + "/" + usuario.getSetor().getSetor());
				
				sessao.save(tramitaSaida);	
				
				sessao.merge(recursomulta);
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
	
	public void salvarInterno(List<RecursoMulta> listaRecursoSaida, Usuario usuario, Setor setor, Unidade unidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			for (int posicao = 0; posicao < listaRecursoSaida.size(); posicao++) {
				
				RecursoMulta recursomulta = listaRecursoSaida.get(posicao);
				
				recursomulta.setStatus("Pendente");
				recursomulta.setUnidadeAtual(unidade);
				recursomulta.setSetorAtual(setor);
												
				RecursoMultaTramita tramitaSaida = new RecursoMultaTramita();
			
				//Ação 1 = Enviar
				tramitaSaida.setAcao(1);
				tramitaSaida.setDataTramita(new Date());
				tramitaSaida.setOrigemUnidade(usuario.getUnidade());
				tramitaSaida.setOrigem(usuario.getSetor());
				tramitaSaida.setRecursomulta(recursomulta);
				tramitaSaida.setUsuario(usuario);
				tramitaSaida.setDestino(unidade.getUnidade().toString() + "/" + setor.getSetor().toString());
				
				sessao.save(tramitaSaida);	
				
				sessao.merge(recursomulta);
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

	public void salvarExterno(List<RecursoMulta> listaRecursoSaida, Usuario usuario, RecursoMultaTramita tramitaSaida2) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			for (int posicao = 0; posicao < listaRecursoSaida.size(); posicao++) {
				
				RecursoMulta recursomulta = listaRecursoSaida.get(posicao);
				
				recursomulta.setStatus("Encaminhado");
				
												
				RecursoMultaTramita tramitaSaida = new RecursoMultaTramita();
			
				//Ação 3 = Externo
				tramitaSaida.setAcao(3);
				tramitaSaida.setDataTramita(new Date());
				tramitaSaida.setOrigemUnidade(usuario.getUnidade());
				tramitaSaida.setOrigem(usuario.getSetor());
				tramitaSaida.setRecursomulta(recursomulta);
				tramitaSaida.setUsuario(usuario);
				tramitaSaida.setDestino(tramitaSaida2.getDestino());
                System.out.println(tramitaSaida2.getDestino());
				
				sessao.save(tramitaSaida);	
				
				sessao.merge(recursomulta);
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

	public void cancelaProcesso(RecursoMulta RecursoCancelado, Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
												
				RecursoMultaTramita tramitaSaida = new RecursoMultaTramita();
			
				//Ação 3 = 4 Cancelado
				tramitaSaida.setAcao(4);
				tramitaSaida.setDataTramita(new Date());
				tramitaSaida.setOrigemUnidade(usuario.getUnidade());
				tramitaSaida.setOrigem(usuario.getSetor());
				tramitaSaida.setRecursomulta(RecursoCancelado);
				tramitaSaida.setUsuario(usuario);
				tramitaSaida.setDestino(usuario.getUnidade().getUnidade().toString() + "/" + usuario.getSetor().getSetor().toString());
				
				sessao.save(tramitaSaida);	
				
				sessao.merge(RecursoCancelado);

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
