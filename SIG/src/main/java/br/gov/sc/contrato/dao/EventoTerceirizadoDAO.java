/* Decompiler 4ms, total 154ms, lines 45 */
package br.gov.sc.contrato.dao;

import br.gov.sc.contrato.domain.ContratoTerceirizado;
import br.gov.sc.contrato.domain.EmpresaTerceirizada;
import br.gov.sc.contrato.domain.EventoTerceirizado;
import br.gov.sc.contrato.domain.FuncionarioTerceirizado;
import br.gov.sc.geapo.domain.MaterialEntrada;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.Setor;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.util.HibernateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class EventoTerceirizadoDAO extends GenericDAO<EventoTerceirizado> {
	public List<EventoTerceirizado> listarFuncionarioSelecionado(FuncionarioTerceirizado colaborador, String tipoEvento,
			Date dateIni, Date dateFini) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		List var9;

		try {
			Criteria consulta = sessao.createCriteria(EventoTerceirizado.class);
			if (colaborador != null) {
				consulta.add(Restrictions.eq("colaborador", colaborador));
				consulta.add(Restrictions.eq("tipoEvento", tipoEvento));
				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataEventoInicial", dateIni));

					} else {

						consulta.add(Restrictions.ge("dataEventoInicial", dateIni));
						consulta.add(Restrictions.le("dataEventoFinal", dateFini));
					}
				}
			}

			List<EventoTerceirizado> resultado = consulta.list();
			var9 = resultado;
		} catch (RuntimeException var12) {
			throw var12;
		} finally {
			sessao.close();
		}

		return var9;
	}

	public List<EventoTerceirizado> listarPorEmpresa(String nContrato, String empresaTerceiriza, String setorRelatorio,
			String tipoEvento, Date dateIni, Date dateFini, List<Unidade> unidades) throws ParseException {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		List var9;
		try {
			Criteria consulta = sessao.createCriteria(EventoTerceirizado.class);

		
			if (nContrato != null) {
				consulta.createCriteria("contratoTerceirizado", "c");

				// consulta.add(Restrictions.in("c.codigo", contratos));

				// System.out.println(contratos + "contratoscontratoscontratoscontratos");


				consulta.add(Restrictions.eq("c.nContrato", nContrato));
				consulta.createAlias("empresaTerceirizada", "e");
				consulta.createAlias("e.pessoaJuridica", "pJ");
				consulta.add(Restrictions.eq("pJ.nomeFantasia", empresaTerceiriza));
				consulta.add(Restrictions.eq("tipoEvento", tipoEvento));

				// consulta.createAlias("c.unidade", "un");

				if (setorRelatorio.isEmpty()) {

					consulta.add(Restrictions.in("c.unidade", unidades));

					consulta.createAlias("c.unidade", "un");
					consulta.addOrder(Order.asc("un.unidadeNome"));

				} else {
				

					consulta.createAlias("c.unidade", "un");
					consulta.add(Restrictions.eq("un.unidadeNome", setorRelatorio));
					consulta.addOrder(Order.asc("un.unidadeNome"));
				}
				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataEventoInicial", dateIni));
					} else {

						consulta.add(Restrictions.ge("dataEventoInicial", dateIni));
						consulta.add(Restrictions.le("dataEventoFinal", dateFini));
					}
				}
			}

			List<EventoTerceirizado> resultado = consulta.list();
			var9 = resultado;
		} catch (RuntimeException var12) {
			throw var12;
		} finally {
			sessao.close();
		}

		return var9;
	}

	public List<EventoTerceirizado> listarPorEmpresaPDF(String nContrato, Long empresaTerceiriza, Long setorRelatorio,
			String tipoEvento, Date dateIni, Date dateFini, List<Unidade> unidades) throws ParseException {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		List var9;
		try {
			Criteria consulta = sessao.createCriteria(EventoTerceirizado.class);

			if (nContrato != null) {

				System.out.println(dateIni + " date1");
				System.out.println(dateFini + " date2");
				
				

				consulta.createCriteria("contratoTerceirizado", "c");
				consulta.add(Restrictions.eq("c.nContrato", nContrato));

				System.out.println("ENTROU DAO " + empresaTerceiriza);
				consulta.add(Restrictions.eq("empresaTerceirizada.codigo", empresaTerceiriza));
				consulta.add(Restrictions.eq("tipoEvento", tipoEvento));

				if (setorRelatorio == null) {

					consulta.add(Restrictions.in("c.unidade", unidades));
					
					consulta.createAlias("c.unidade", "un");
					consulta.addOrder(Order.asc("un.unidadeNome"));

					System.out.println("ENTROU isEmpty");

				} else {
					System.out.println("ENTROU" + setorRelatorio);

					consulta.createAlias("c.unidade", "un");
					consulta.add(Restrictions.eq("un.codigo", setorRelatorio));
					consulta.addOrder(Order.asc("un.unidadeNome"));
					
					System.out.println("PASSOU ORDERDESC");
				}
				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataEventoInicial", dateIni));
					} else {

						consulta.add(Restrictions.ge("dataEventoInicial", dateIni));
						consulta.add(Restrictions.le("dataEventoFinal", dateFini));
					}
				}
			}

			List<EventoTerceirizado> resultado = consulta.list();
			var9 = resultado;
		} catch (RuntimeException var12) {
			throw var12;
		} finally {
			sessao.close();
		}

		return var9;
	}

	/* NÃO EDITAR O LOAD LAST!!!!!!!!!!!!!!! */

	public EventoTerceirizado loadLast(FuncionarioTerceirizado funcSelecionado) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Criteria criteria = sessao.createCriteria(EventoTerceirizado.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("colaborador", funcSelecionado));

		return (EventoTerceirizado) criteria.setMaxResults(1).uniqueResult();
	}

}