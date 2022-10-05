/* Decompiler 9ms, total 419ms, lines 47 */
package br.gov.sc.contrato.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.contrato.domain.ContratoHistFuncionario;
import br.gov.sc.contrato.domain.ContratoTerceirizado;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.sgi.util.HibernateUtil;

public class ContratoHistFuncionarioDAO extends GenericDAO<ContratoHistFuncionario> {
	public List<ContratoHistFuncionario> listarPorEmpresa(ContratoTerceirizado contrato,
			FuncionarioTerceirizado funcioario, Date dateIni, Date dateFini) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		List var9;
		try {
			Criteria consulta = sessao.createCriteria(ContratoHistFuncionario.class);
			if (contrato != null && funcioario != null) {

				consulta.add(Restrictions.eq("contratoTerceirizado", contrato));
				consulta.add(Restrictions.eq("funcionarioTerceirizado", funcioario));
				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataCadastro", dateIni));
					} else {
						System.out.println(dateIni);
						System.out.println(dateFini);
						consulta.add(Restrictions.ge("dataCadastro", dateIni));
						consulta.add(Restrictions.le("dataCadastro", dateFini));
					}

				}
			}
			if (contrato == null && funcioario != null) {
				consulta.add(Restrictions.eq("funcionarioTerceirizado", funcioario));

				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataCadastro", dateIni));
					} else {
						System.out.println(dateIni);
						System.out.println(dateFini);
						consulta.add(Restrictions.ge("dataCadastro", dateIni));
						consulta.add(Restrictions.le("dataCadastro", dateFini));
					}

				}
			}
			if (contrato != null && funcioario == null) {
				consulta.add(Restrictions.eq("contratoTerceirizado", contrato));

				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataCadastro", dateIni));
					} else {
						System.out.println(dateIni);
						System.out.println(dateFini);
						consulta.add(Restrictions.ge("dataCadastro", dateIni));
						consulta.add(Restrictions.le("dataCadastro", dateFini));
					}

				}
			}

			List<ContratoHistFuncionario> resultado = consulta.list();
			var9 = resultado;
		} catch (RuntimeException var12) {
			throw var12;
		} finally {
			sessao.close();
		}

		return var9;
	}

	
}