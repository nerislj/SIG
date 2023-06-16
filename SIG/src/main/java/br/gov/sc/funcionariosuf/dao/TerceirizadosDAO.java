package br.gov.sc.funcionariosuf.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.dao.GenericDAO;
import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.PartesProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.funcionariosuf.domain.CiretranCitran;
import br.gov.sc.funcionariosuf.domain.Servidores;
import br.gov.sc.funcionariosuf.domain.Terceirizados;
import br.gov.sc.funcionariosuf.domain.UnidadeCiretranCitran;
import br.gov.sc.sgi.domain.Cidade;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class TerceirizadosDAO extends GenericDAO<Terceirizados>{
	
	public void salvarFuncionarioTerceirizado(Unidade unidade, FuncionarioTerceirizado funcionario, Terceirizados terceirizado, Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			if(funcionario==null) {
			funcionario = new FuncionarioTerceirizado();
			}
			
			funcionario.setDataCadastro(new Date());
			funcionario.setCargoTerceirizado(terceirizado.getCargoTerceirizado());
			funcionario.setPessoa(terceirizado.getPessoa());
			funcionario.setUnidade(unidade);
			funcionario.setUsuarioCadastro(usuarioLogado);
			funcionario.setSetor(terceirizado.getSetor());
			
			
			

			sessao.merge(funcionario);

	

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
	
	public static void excluirFuncionarioTerceirizadoById(Long codigoFuncionario) {
	    Session session2;
	    FuncionarioTerceirizado func;
	    session2 = HibernateUtil.getFabricaDeSessoes().openSession();
	    session2.beginTransaction();
	    func = (FuncionarioTerceirizado) session2.load(FuncionarioTerceirizado.class, codigoFuncionario);
	    
	    
	    session2.delete(func);
	    
	    session2.getTransaction().commit();
	}
	
	
	public Unidade loadUnidadePorNome(String nome) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(Unidade.class);
		criteria.add(Restrictions.eq("unidadeNome", nome));

		return (Unidade) criteria.setMaxResults(1).uniqueResult();
	}
	
	public FuncionarioTerceirizado loadFuncionario(String cpf) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(FuncionarioTerceirizado.class);
		criteria.createAlias("pessoa", "p");
		criteria.add(Restrictions.eq("p.cpf", cpf));	
		

		return (FuncionarioTerceirizado) criteria.setMaxResults(1).uniqueResult();
	}

	
	
	@SuppressWarnings("unchecked")
	public List<Terceirizados> listarPorUnidade(Object processo, Setor setor) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			System.out.println("DAO setor " + setor );
			Criteria consulta = sessao.createCriteria(Terceirizados.class);
			consulta.add(Restrictions.eq("ciretranCitran", processo));	
			consulta.add(Restrictions.eq("setor", setor));
			consulta.addOrder(Order.asc("codigo"));
			List<Terceirizados> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Terceirizados> listarPorUnidadeCodigo(CiretranCitran processo, Setor setor) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Terceirizados.class);
			consulta.add(Restrictions.eq("ciretranCitran", processo));	
			consulta.add(Restrictions.eq("setor", setor));
			consulta.addOrder(Order.asc("codigo"));
			List<Terceirizados> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

		
}
