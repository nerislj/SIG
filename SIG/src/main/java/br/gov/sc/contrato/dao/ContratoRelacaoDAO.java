/* Decompiler 34ms, total 184ms, lines 78 */
package br.gov.sc.contrato.dao;

import br.gov.sc.contrato.domain.ContratoHistFuncionario;
import br.gov.sc.contrato.domain.ContratoRelacao;
import br.gov.sc.contrato.domain.ContratoTerceirizado;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.sgi.util.HibernateUtil;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ContratoRelacaoDAO extends GenericDAO<ContratoRelacao> {
	public List<ContratoRelacao> listarPorContratoTerceirizadoObject(Object contratoTerceirizado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		List var6;
		try {
			Criteria consulta = sessao.createCriteria(ContratoRelacao.class);
			consulta.add(Restrictions.eq("contratoTerceirizado", contratoTerceirizado));
			consulta.addOrder(Order.asc("codigo"));
			List<ContratoRelacao> resultado = consulta.list();
			var6 = resultado;
		} catch (RuntimeException var9) {
			throw var9;
		} finally {
			sessao.close();
		}

		return var6;
	}

	public static ContratoRelacao carregaFuncionario(FuncionarioTerceirizado id) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		ContratoRelacao var4;
		try {
			Criteria consulta = sessao.createCriteria(ContratoRelacao.class);
			consulta.add(Restrictions.eq("funcionarioTerceirizado", id));
			var4 = (ContratoRelacao) consulta.setMaxResults(1).uniqueResult();
		} catch (RuntimeException var7) {
			throw var7;
		} finally {
			sessao.close();
		}

		return var4;
	}
	

	public void salvarFuncionarioHist(ContratoRelacao contratoRelacao, ContratoHistFuncionario funcionarioHist) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			System.out.println("funcionarioHist " + funcionarioHist);
			System.out.println("contratoRelacao " + contratoRelacao);
			funcionarioHist.setContratoTerceirizado(contratoRelacao.getContratoTerceirizado());
			funcionarioHist.setFuncionarioTerceirizado(contratoRelacao.getFuncionarioTerceirizado());
			funcionarioHist.setDataCadastro(new Date());
			funcionarioHist.setUsuarioCadastro(contratoRelacao.getUsuarioCadastro());
			sessao.save(funcionarioHist);
			transacao.commit();
		} catch (RuntimeException var9) {
			if (transacao != null) {
				transacao.rollback();
			}

			throw var9;
		} finally {
			sessao.close();
		}

	}

	public List<ContratoRelacao> listarPorFuncionarioRelacao(FuncionarioTerceirizado funcionario,
			ContratoRelacao contratoRelacao, Date dateIni, Date dateFini) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		List var9;
		try {
			
			
			System.out.println(funcionario + " funcionario");
			System.out.println(contratoRelacao.getStatus() + " contratoRelacao.getStatus()");
			
			if (contratoRelacao.getStatus().equals("")) {
				contratoRelacao.setStatus(null);
				System.out.println(contratoRelacao.getStatus() + " contratoRelacao.getStatus() DENTRO IF");
			}
			Criteria consulta = sessao.createCriteria(ContratoRelacao.class);
			if (funcionario != null && contratoRelacao.getStatus() != null) {

				consulta.add(Restrictions.eq("status", contratoRelacao.getStatus()));
				consulta.add(Restrictions.eq("funcionarioTerceirizado", funcionario));
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
			if (funcionario == null && contratoRelacao.getStatus() == null) {
				consulta.addOrder(Order.asc("codigo"));
				
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
			if (contratoRelacao.getStatus() == null && funcionario != null) {
				consulta.add(Restrictions.eq("funcionarioTerceirizado", funcionario));

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
			if (contratoRelacao.getStatus() != null && funcionario == null) {
				consulta.add(Restrictions.eq("status", contratoRelacao.getStatus()));

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

			List<ContratoRelacao> resultado = consulta.list();
			var9 = resultado;
		} catch (RuntimeException var12) {
			throw var12;
		} finally {
			sessao.close();
		}

		return var9;
	}
}