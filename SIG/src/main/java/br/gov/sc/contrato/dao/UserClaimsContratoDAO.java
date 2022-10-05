/* Decompiler 1ms, total 426ms, lines 7 */
package br.gov.sc.contrato.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.contrato.domain.UserClaimsContrato;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class UserClaimsContratoDAO extends GenericDAO<UserClaimsContrato> {
	
	 public List<UserClaimsContrato> listarPorUsuarioLogado(Usuario usuario) {
	      Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

	      List var6;
	      try {
	         Criteria consulta = sessao.createCriteria(UserClaimsContrato.class);
	         
	         
	        	 System.out.println(usuario + " usuario");
	      
	         consulta.add(Restrictions.eq("usuario", usuario));
	         consulta.addOrder(Order.asc("codigo"));
	         List<UserClaimsContrato> resultado = consulta.list();
	         var6 = resultado;
	         
	      
	      } catch (RuntimeException var9) {
	         throw var9;
	      } finally {
	         sessao.close();
	      }

	      return var6;
	   }
	 
}