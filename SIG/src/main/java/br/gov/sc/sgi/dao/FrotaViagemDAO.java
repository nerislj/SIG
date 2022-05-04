package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.sgi.domain.FrotaUnidade;
import br.gov.sc.sgi.domain.FrotaVeiculo;
import br.gov.sc.sgi.domain.FrotaViagem;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class FrotaViagemDAO extends GenericDAO<FrotaViagem> {

	@SuppressWarnings("unchecked")
	public List<FrotaViagem> listarViagem(FrotaUnidade unidade, FrotaVeiculo veiculo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FrotaViagem.class);

			System.out.println("Unidade " + unidade);

			consulta.add(Restrictions.eq("frotaUnidade", unidade));
			consulta.add(Restrictions.eq("frotaVeiculo", veiculo));

			List<FrotaViagem> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<FrotaViagem> listarCanceladas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FrotaViagem.class);
			
			
			consulta.add(Restrictions.eq("status", 2));
			consulta.addOrder(Order.desc("codigo"));

			List<FrotaViagem> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FrotaViagem> listarSolicitadas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FrotaViagem.class);
			
			
			consulta.add(Restrictions.eq("status", 0));
			consulta.addOrder(Order.desc("codigo"));

			List<FrotaViagem> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FrotaViagem> listarBaixadas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FrotaViagem.class);
			
			
			consulta.add(Restrictions.eq("status", 4));
			consulta.addOrder(Order.desc("codigo"));

			List<FrotaViagem> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FrotaViagem> listarAprovadas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FrotaViagem.class);
			
			
			consulta.add(Restrictions.eq("status", 1));
			consulta.addOrder(Order.desc("codigo"));

			List<FrotaViagem> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<FrotaViagem> listarReprovadas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FrotaViagem.class);
			
			
			consulta.add(Restrictions.eq("status", 3));
			consulta.addOrder(Order.desc("codigo"));

			List<FrotaViagem> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	

	public FrotaViagem carregaUltimaViagem(FrotaViagem viagem) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(FrotaViagem.class);
		consulta.addOrder(Order.desc("codigo"));

		if (consulta.setMaxResults(1).uniqueResult().equals(null)) {
			return null;
		} else {
			return (FrotaViagem) consulta.setMaxResults(1).uniqueResult();
		}
	}

	public void salvarVeiculo(FrotaVeiculo veiculo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			veiculo.setStatus("Solicitado");

			sessao.update(veiculo);

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
	
	public void salvarVeiculoExcluir(FrotaVeiculo veiculo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			veiculo.setStatus("Disponível");

			sessao.update(veiculo);

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

	public void excluirViagem(FrotaVeiculo veiculo, FrotaViagem viagem) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			viagem.setStatus(2);
			viagem.setObservacaoViagem(viagem.getObservacaoViagem());
			
			sessao.merge(viagem);

			veiculo.setStatus("Disponível");

			sessao.merge(veiculo);

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


	public void recusarViagem(FrotaVeiculo veiculo, FrotaViagem viagem) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			viagem.setStatus(3);
			viagem.setObservacaoViagem(viagem.getObservacaoViagem());
			
			sessao.merge(viagem);

			veiculo.setStatus("Disponível");

			sessao.merge(veiculo);

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

	
	public void viagemBaixa(FrotaVeiculo veiculo, FrotaViagem viagem) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			sessao.merge(viagem);

			FrotaVeiculo veiculo2 = viagem.getFrotaVeiculo();
			veiculo2.setKm(viagem.getFrotaVeiculo().getKm());
			veiculo2.setStatus("Disponível");

		
			

			sessao.merge(veiculo2);

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
	public List<FrotaViagem> listarOrder(Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FrotaViagem.class);
			consulta.add(Restrictions.eq("usuarioCadastro", usuario));
			consulta.addOrder(Order.desc("codigo"));
			List<FrotaViagem> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
