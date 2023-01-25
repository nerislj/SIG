package br.gov.sc.ecvitl.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.codet.domain.Processo;
import br.gov.sc.ecvitl.domain.EcvItl;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.util.HibernateUtil;


public class EcvItlDAO extends GenericDAO<EcvItl>{


	@SuppressWarnings("unchecked")
	public List<EcvItl> listarEmpresasConsulta(String campoDigitado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(EcvItl.class);

			
				
			
				consulta.add(Restrictions.disjunction()
						
						.add(Restrictions.like("razaoSocial", campoDigitado, MatchMode.ANYWHERE))
						.add(Restrictions.eq("credencial", campoDigitado))
						.add(Restrictions.eq("tipoCadastro", campoDigitado))
						.add(Restrictions.eq("cnpj", campoDigitado)));

			
			
			

			List<EcvItl> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
}
