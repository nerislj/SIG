package br.gov.sc.codet.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import br.gov.sc.cetran.domain.Conselheiro;
import br.gov.sc.cetran.domain.HistoricoProcesso;
import br.gov.sc.codet.domain.FasesProcesso;
import br.gov.sc.codet.domain.NomenclaturaProcesso;
import br.gov.sc.codet.domain.PartesProcesso;
import br.gov.sc.codet.domain.Processo;
import br.gov.sc.codet.domain.SetorAtual;
import br.gov.sc.codet.domain.SituacaoProcesso;
import br.gov.sc.sgi.domain.Credenciado;
import br.gov.sc.sgi.domain.CredenciadoEmp;
import br.gov.sc.sgi.domain.CredenciadoHist;
import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.PessoaFisica;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;
import util.DateUtil;

public class ProcessoDAO extends GenericDAO<Processo> {
	
	@SuppressWarnings("unchecked")
	public List<Processo> listarCampoDigitadoNULL() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Processo.class);

			

				consulta.addOrder(Order.desc("codigo"));
				
			
			

			List<Processo> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}


	@SuppressWarnings("unchecked")
	public List<Processo> listarProcessos(String campoDigitado, Credenciado cpf, Credenciado numeroCredencial,
			CredenciadoEmp empresaCNPJ, Credenciado credenciado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Processo.class);

			if (cpf == null && numeroCredencial == null) {

				consulta.addOrder(Order.desc("codigo"));
				consulta.add(Restrictions.disjunction()
						
						.add(Restrictions.eq("credenciado", credenciado))
						.add(Restrictions.eq("credenciadoPJ", empresaCNPJ))
						.add(Restrictions.eq("numProcesso", campoDigitado))
						.add(Restrictions.like("numProcesso", campoDigitado))
						.add(Restrictions.eq("numSGPE", campoDigitado)));

			} else {
				consulta.addOrder(Order.desc("codigo"));
				consulta.createAlias("partesProcesso", "e");

				consulta.add(Restrictions.disjunction().add(Restrictions.eq("e.credenciado", cpf))
						.add(Restrictions.eq("e.credenciado", numeroCredencial))
						.add(Restrictions.eq("credenciado", credenciado))
						.add(Restrictions.eq("credenciadoPJ", empresaCNPJ))
						.add(Restrictions.eq("numProcesso", campoDigitado))
						.add(Restrictions.eq("numSGPE", campoDigitado)));

			}
			
			

			List<Processo> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Processo> listarProcessosConsulta(Credenciado nomeCompleto,CredenciadoEmp razao,CredenciadoEmp fantasia, String campoDigitado, Credenciado cpf, Credenciado numeroCredencial,
			CredenciadoEmp empresaCNPJ, Credenciado credenciado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Processo.class);

			if (cpf == null && numeroCredencial == null) {

				consulta.addOrder(Order.desc("codigo"));
				consulta.add(Restrictions.disjunction()
						
						.add(Restrictions.eq("credenciado", nomeCompleto))
						.add(Restrictions.eq("credenciado", credenciado))
						.add(Restrictions.eq("credenciadoPJ", empresaCNPJ))
						.add(Restrictions.eq("credenciadoPJ", razao))
						.add(Restrictions.eq("credenciadoPJ", fantasia))
						.add(Restrictions.eq("numProcesso", campoDigitado))
						.add(Restrictions.like("numProcesso", campoDigitado))
						.add(Restrictions.eq("numSGPE", campoDigitado)));

			} else {
				consulta.addOrder(Order.desc("codigo"));
				consulta.createAlias("partesProcesso", "e");

				consulta.add(Restrictions.disjunction()
						
						.add(Restrictions.eq("e.credenciado", nomeCompleto))
						.add(Restrictions.eq("e.credenciado", cpf))
						.add(Restrictions.eq("e.credenciado", numeroCredencial))
						.add(Restrictions.eq("credenciado", credenciado))
						
						.add(Restrictions.eq("e.credenciadoEmpresa", razao))
						.add(Restrictions.eq("e.credenciadoEmpresa", fantasia))
						.add(Restrictions.eq("credenciadoPJ", empresaCNPJ))
						.add(Restrictions.eq("credenciadoPJ", razao))
						.add(Restrictions.eq("credenciadoPJ", fantasia))
						.add(Restrictions.eq("numProcesso", campoDigitado))
						.add(Restrictions.eq("numSGPE", campoDigitado)));

			}
			
			

			List<Processo> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public void salvarFasesEPartesDoBanco(FasesProcesso fasesProcesso, Processo processo, Usuario usuarioLogado,
			PartesProcesso parteProcesso) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			System.out.println(parteProcesso);

			parteProcesso.setProcesso(processo);
			parteProcesso.setCredenciadoEmpresa(processo.getCredenciadoPJ());
			parteProcesso.setDataCadastro(new Date());
			parteProcesso.setUsuarioCadastro(usuarioLogado);

			sessao.merge(parteProcesso);

			fasesProcesso.setUsuarioCadastro(usuarioLogado);
			fasesProcesso.setDataCadastro(new Date());
			fasesProcesso.setProcesso(processo);

			fasesProcesso.setOcorrencia("Nomenclatura: " + processo.getNomenclatura().getDescricao() + ", Nº Processo: "
					+ processo.getNumProcesso() + ", Nº SGPE: " + processo.getNumSGPE());
			fasesProcesso.setAnotacao(processo.getSituacao().getDescricao());

			if (processo.getSituacao().getDescricao().equals("ARQUIVADO")) {
				fasesProcesso.setProvidencia("Setor Atual: ARQUIVO");
			} else {
				fasesProcesso.setProvidencia("Setor Atual: " + processo.getSetorAtual().getDescricao());
			}

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

	public void salvarFasesEPartes(FasesProcesso fasesProcesso, Processo processo, Usuario usuarioLogado,
			PartesProcesso parteProcesso) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			System.out.println(parteProcesso);

			if(processo.getCredenciado()!=null) {
				parteProcesso.setCredenciado(processo.getCredenciado());
			} else {
				parteProcesso.setCredenciadoEmpresa(processo.getCredenciadoPJ());
			}
			
			parteProcesso.setProcesso(processo);
			//parteProcesso.setCredenciadoEmpresa(processo.getCredenciadoPJ());
			parteProcesso.setDataCadastro(new Date());
			parteProcesso.setUsuarioCadastro(usuarioLogado);

			sessao.merge(parteProcesso);

			fasesProcesso.setUsuarioCadastro(usuarioLogado);
			fasesProcesso.setDataCadastro(new Date());
			fasesProcesso.setProcesso(processo);

			fasesProcesso.setOcorrencia("Nomenclatura: " + processo.getNomenclatura().getDescricao() + ", Nº Processo: "
					+ processo.getNumProcesso() + ", Nº SGPE: " + processo.getNumSGPE());
			fasesProcesso.setAnotacao(processo.getSituacao().getDescricao());

			if (processo.getSituacao().getDescricao().equals("ARQUIVADO")) {
				fasesProcesso.setProvidencia("Setor Atual: ARQUIVO");
			} else {
				fasesProcesso.setProvidencia("Setor Atual: " + processo.getSetorAtual().getDescricao());
			}

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
	
	public void salvarFases(FasesProcesso fasesProcesso, Processo processo, Usuario usuarioLogado
			) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			

			fasesProcesso.setUsuarioCadastro(usuarioLogado);
			fasesProcesso.setDataCadastro(new Date());
			fasesProcesso.setProcesso(processo);

			fasesProcesso.setOcorrencia("Nomenclatura: " + processo.getNomenclatura().getDescricao() + ", Nº Processo: "
					+ processo.getNumProcesso() + ", Nº SGPE: " + processo.getNumSGPE());
			fasesProcesso.setAnotacao(processo.getSituacao().getDescricao());

			if (processo.getSituacao().getDescricao().equals("ARQUIVADO")) {
				fasesProcesso.setProvidencia("Setor Atual: ARQUIVO");
			} else {
				fasesProcesso.setProvidencia("Setor Atual: " + processo.getSetorAtual().getDescricao());
			}

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

			System.out.println(processo.getNomenclatura().getDescricao() + " Nº Processo " + processo.getNumProcesso()
					+ " Nº SGPE " + processo.getNumSGPE() + "Código " + processo);
			fasesProcesso.setOcorrencia(processo.getNomenclatura().getDescricao() + " Nº Processo "
					+ processo.getNumProcesso() + " Nº SGPE " + processo.getNumSGPE());
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

	public static Processo carregaProcesso(Long id) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Processo.class);

			consulta.add(Restrictions.eq("codigo", id));
			
			

			return (Processo) consulta.setMaxResults(1).uniqueResult();

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public static Processo carregaProcessoUltimo(CredenciadoEmp credenciadoPJ, NomenclaturaProcesso nomenclatura,
			SituacaoProcesso situacao) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Processo.class);

			consulta.addOrder(Order.desc("codigo"));
			consulta.add(Restrictions.eq("credenciadoPJ", credenciadoPJ));
			consulta.add(Restrictions.eq("nomenclatura", nomenclatura));
			consulta.add(Restrictions.eq("situacao", situacao));

			return (Processo) consulta.setMaxResults(1).uniqueResult();

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public static Processo carregaProcessoUltimoCredenciado(Credenciado credenciado, NomenclaturaProcesso nomenclatura,
			SituacaoProcesso situacao) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Processo.class);

			consulta.addOrder(Order.desc("codigo"));
			consulta.add(Restrictions.eq("credenciado", credenciado));
			consulta.add(Restrictions.eq("nomenclatura", nomenclatura));
			consulta.add(Restrictions.eq("situacao", situacao));

			return (Processo) consulta.setMaxResults(1).uniqueResult();

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public static PartesProcesso carregaParteProcesso(Processo processo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(PartesProcesso.class);

			consulta.add(Restrictions.disjunction()

					.add(Restrictions.eq("credenciadoEmpresa", processo.getCredenciadoPJ()))
					.add(Restrictions.eq("processo", processo)));

			return (PartesProcesso) consulta.setMaxResults(1).uniqueResult();

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public List<Processo> listarTudo(SetorAtual setor, SituacaoProcesso situacao, Date dateIni, Date dateFini) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Processo.class);

			if (setor != null && situacao == null) {

				consulta.add(Restrictions.eq("setorAtual", setor));

				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataInstauracao", dateIni));
					} else {
						System.out.println(dateIni);
						System.out.println(dateFini);

						consulta.add(Restrictions.ge("dataInstauracao", dateIni));
						consulta.add(Restrictions.le("dataInstauracao", dateFini));
					}
				}
			}

			if (situacao != null && setor == null) {

				consulta.add(Restrictions.eq("situacao", situacao));
				
				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataInstauracao", dateIni));
					} else {
						System.out.println(dateIni);
						System.out.println(dateFini);

						consulta.add(Restrictions.ge("dataInstauracao", dateIni));
						consulta.add(Restrictions.le("dataInstauracao", dateFini));
					}
				}
			}

			if (situacao != null && setor != null) {

				consulta.add(Restrictions.eq("situacao", situacao));
				consulta.add(Restrictions.eq("setorAtual", setor));

				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataInstauracao", dateIni));
					} else {
						System.out.println(dateIni);
						System.out.println(dateFini);

						consulta.add(Restrictions.ge("dataInstauracao", dateIni));
						consulta.add(Restrictions.le("dataInstauracao", dateFini));
					}
				}
			}
			
			if (situacao == null && setor == null) {

				consulta.addOrder(Order.desc("codigo"));

				if (dateIni != null || dateFini != null) {
					if (dateIni.equals(dateFini)) {
						consulta.add(Restrictions.eq("dataInstauracao", dateIni));
					} else {
						System.out.println(dateIni);
						System.out.println(dateFini);

						consulta.add(Restrictions.ge("dataInstauracao", dateIni));
						consulta.add(Restrictions.le("dataInstauracao", dateFini));
					}
				}
			}

			/*
			 * if (setor != null && situacao == null) {
			 * 
			 * if (dateIni != null || dateFini != null) {
			 * 
			 * if (dateIni == dateFini) { consulta.add(Restrictions.eq("dataInstauracao",
			 * dateIni)); } else { System.out.println(dateIni);
			 * System.out.println(dateFini); consulta.add(Restrictions.ge("dataInstauracao",
			 * dateIni)); consulta.add(Restrictions.le("dataInstauracao", dateFini));
			 * consulta.add(Restrictions.eq("setorAtual", setor)); } } else {
			 * consulta.add(Restrictions.eq("setorAtual", setor)); }
			 * 
			 * }
			 */

			/*
			 * if (situacao != null && setor == null) {
			 * 
			 * if (dateIni != null || dateFini != null) { if (dateIni.equals(dateFini)) {
			 * consulta.add(Restrictions.eq("dataInstauracao", dateIni)); } else {
			 * System.out.println(dateIni); System.out.println(dateFini);
			 * consulta.add(Restrictions.eq("situacao", situacao));
			 * consulta.add(Restrictions.ge("dataInstauracao", dateIni));
			 * consulta.add(Restrictions.le("dataInstauracao", dateFini)); } } else {
			 * consulta.add(Restrictions.eq("situacao", situacao)); } }
			 * 
			 * if (situacao != null && setor != null) {
			 * 
			 * if (dateIni != null || dateFini != null) {
			 * 
			 * if (dateIni == dateFini) { consulta.add(Restrictions.eq("dataInstauracao",
			 * dateIni)); } else { System.out.println(dateIni);
			 * System.out.println(dateFini); consulta.add(Restrictions.eq("setorAtual",
			 * setor)); consulta.add(Restrictions.eq("situacao", situacao));
			 * consulta.add(Restrictions.ge("dataInstauracao", dateIni));
			 * consulta.add(Restrictions.le("dataInstauracao", dateFini)); } } else {
			 * 
			 * consulta.add(Restrictions.ge("dataInstauracao", dateIni));
			 * consulta.add(Restrictions.le("dataInstauracao", dateFini)); }
			 * 
			 * }
			 */

			List<Processo> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
