package br.gov.sc.geapo.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.gov.sc.geapo.domain.Material;
import br.gov.sc.geapo.domain.MaterialEntrada;
import br.gov.sc.geapo.domain.MaterialSaida;
import br.gov.sc.geapo.domain.MaterialSaidaHist;
import br.gov.sc.geapo.domain.MaterialSaidaRelacao;
import br.gov.sc.geapo.domain.MaterialStatus;
import br.gov.sc.sgi.domain.Usuario;
import br.gov.sc.sgi.util.HibernateUtil;

public class MaterialSaidaDAO extends GenericDAO<MaterialSaida> {

	public MaterialSaida loadLast(Material material) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria criteria = sessao.createCriteria(MaterialSaida.class);
		criteria.addOrder(Order.desc("codigo"));
		criteria.add(Restrictions.eq("material", material));

		return (MaterialSaida) criteria.setMaxResults(1).uniqueResult();

	}

	@SuppressWarnings("unchecked")
	public List<MaterialSaida> listaPedidoMateriaisPorUsuario(Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(MaterialSaida.class);

			consulta.add(Restrictions.eq("usuarioCadastro", usuarioLogado));

			List<MaterialSaida> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<MaterialSaida> listarMaterialPendentes() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(MaterialSaida.class);

			MaterialStatusDAO materialStatusDAO = new MaterialStatusDAO();
			List<MaterialStatus> resultado2 = materialStatusDAO.listar();

			// APROVADO = 0
			// PENDENTE = 1
			// RESSALVA = 2

			MaterialStatus valor = resultado2.get(1);

			consulta.add(Restrictions.eq("materialStatus", valor));

			List<MaterialSaida> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<MaterialEntrada> ListaMaterialSaida(MaterialSaida materialSaida) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(MaterialEntrada.class);

			consulta.add(Restrictions.eq("material", materialSaida.getMaterial()));

			List<MaterialEntrada> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public MaterialSaida iniciarMaterialSaida(String material, String tipoMaterial) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria criteria = sessao.createCriteria(MaterialSaida.class);
		criteria.addOrder(Order.desc("codigo"));

		criteria.createAlias("materialTipo", "t");
		criteria.add(Restrictions.eq("t.tipomaterial", tipoMaterial));

		criteria.createAlias("material", "m");
		criteria.add(Restrictions.eq("m.material", material));

		return (MaterialSaida) criteria.setMaxResults(1).uniqueResult();

	}

	public MaterialEntrada iniciarMaterialEntrada(String material, String tipoMaterial) throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria criteria = sessao.createCriteria(MaterialEntrada.class);
		criteria.addOrder(Order.desc("codigo"));

		criteria.createAlias("materialTipo", "t");
		criteria.add(Restrictions.eq("t.tipomaterial", tipoMaterial));

		criteria.createAlias("material", "m");
		criteria.add(Restrictions.eq("m.material", material));

		return (MaterialEntrada) criteria.setMaxResults(1).uniqueResult();

	}

	public static MaterialSaida carregaMaterialSaida(Material material) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(MaterialSaida.class);
		consulta.addOrder(Order.desc("codigo"));
		consulta.add(Restrictions.eq("material", material));

		if (consulta.setMaxResults(1).uniqueResult().equals(null)) {
			return null;
		} else {
			return (MaterialSaida) consulta.setMaxResults(1).uniqueResult();
		}
	}

	public void salvarSaidaHist(MaterialSaida materialSaida, Material material, MaterialSaidaHist materialSaidaHist,
			Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			materialSaidaHist.setUsuarioCadastro(usuarioLogado);
			materialSaidaHist.setMaterial(material);
			materialSaidaHist.setMaterialSaida(materialSaida);
			materialSaidaHist.setQuantidadeHist(materialSaida.getQuantidade());
			materialSaidaHist.setDataAlteracao(new Date());

			sessao.save(materialSaidaHist);

			// sessao.update(materialEntrada);

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
	public List<MaterialSaida> listarPedidoMateriaisPorUsuario(Usuario usuarioLogado) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(MaterialSaida.class);

			consulta.add(Restrictions.eq("usuarioCadastro", usuarioLogado));
			consulta.add(Restrictions.eq("setorAbertura", usuarioLogado.getSetor()));
			consulta.addOrder(Order.desc("dataCadastro"));

			List<MaterialSaida> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void salvarSelecionados(List<MaterialSaidaRelacao> listaSaida, MaterialEntrada materialEntrada)
			throws Exception {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		MaterialStatusDAO materialStatusDAO = new MaterialStatusDAO();

		MaterialSaidaDAO materialSaidaDAO = new MaterialSaidaDAO();

		try {
			transacao = sessao.beginTransaction();

			for (int posicao = 0; posicao < listaSaida.size(); posicao++) {

				int aux00 = 0;
				

				boolean find = false;
				int count = 0;
				

				while (listaSaida.size() != 0) {
					
	

					System.out.println(listaSaida.get(posicao).getMaterialSaida().getMaterial().getMaterial());

					materialEntrada = materialSaidaDAO.iniciarMaterialEntrada(
							listaSaida.get(posicao).getMaterialSaida().getMaterial().getMaterial(),
							listaSaida.get(posicao).getMaterialSaida().getMaterialTipo().getTipomaterial());

					System.out.println(materialEntrada.getMaterial().getMaterial());

					for (int i = 0; i < listaSaida.size();) {

						MaterialSaidaRelacao nome = listaSaida.get(0);
						// MATERIAL SAIDA RELAÇÃO
						MaterialSaidaRelacao materialSaidaRelacao = listaSaida.get(posicao);
						MaterialSaida materialSaida = materialSaidaRelacao.getMaterialSaida();

						System.out.println("materialSaida " + materialSaida.getMaterial().getMaterial());

						// HISTÓRICO E SAIDA
						materialSaidaRelacao.setMaterialSaida(materialSaida);
						materialSaidaRelacao.setMaterialEntrada(materialEntrada);
						sessao.save(materialSaidaRelacao);
						List<MaterialStatus> resultado = materialStatusDAO.listar();
						// APROVADO = 0 / PENDENTE = 1 / RESSALVA = 2
						MaterialStatus valor2 = resultado.get(0);
						System.out.println("VALOR LISTA STATUS " + valor2.getMaterialStatus());
						materialSaida.setMaterialStatus(valor2);
						sessao.merge(materialSaida);

						System.out.println(listaSaida);

						System.out.println(nome.getMaterialSaida().getMaterial().getMaterial());
						System.out.println(materialSaida.getMaterial().getMaterial());
						
						
						if (nome.getMaterialSaida().getMaterial().getMaterial()
								.equals(materialSaida.getMaterial().getMaterial())) {

						
							aux00 = aux00 + listaSaida.get(i).getMaterialSaida().getQuantidade();
							System.out.println(aux00);
							
							try {
								for (int j = i + 1; j <= listaSaida.size(); j++) {
									
									System.out.println("AQUI "+ listaSaida.get(j));
									if(listaSaida.get(j).getMaterialSaida().getMaterial().getMaterial().isEmpty()){
										find = false;
										break;
									}
									if (listaSaida.get(i).getMaterialSaida().getMaterial().getMaterial()
											.equals(listaSaida.get(j).getMaterialSaida().getMaterial().getMaterial())) {
										find = true;
										count++;
										break;
									} else {
										find = false;
										count++;
										break;
									}
									

									
								}

							} catch (RuntimeException erro) {
								find = false;
								count++;
							}
							
							
							System.out.println("I " + listaSaida.get(i));
						

						
								listaSaida.remove(i);
								
							
							
							
								
							
							

						}

						if (count >= 2 && find==false) {

							System.out.println("materialEntrada " + materialEntrada.getMaterial().getMaterial());

							Integer nn = aux00;

							System.out.println("VALOR N listaSaida posicao 0 0 " + nn);

							nn = (materialSaidaDAO
									.iniciarMaterialEntrada(materialSaida.getMaterial().getMaterial(),
											materialSaida.getMaterial().getMaterialTipo().getTipomaterial())
									.getQuantidade() - nn);
							System.out.println("VALOR N listaSaida posicao 0 0 SUBTRAÇÃO " + nn);

							materialEntrada.setQuantidade(nn);
							sessao.merge(materialEntrada);

							nn = 0;

							
							aux00 = 0;
							find = false;
							count = 0;
							break;

						}

						if (count == 1 && find==false) {

							System.out.println("materialEntrada " + materialEntrada.getMaterial().getMaterial());

							Integer n = materialSaida.getQuantidade();

							System.out.println("VALOR N listaSaida " + n);

							System.out.println(materialSaidaDAO
									.iniciarMaterialEntrada(materialSaida.getMaterial().getMaterial(),
											materialSaida.getMaterial().getMaterialTipo().getTipomaterial())
									.getQuantidade());
							n = (materialSaidaDAO
									.iniciarMaterialEntrada(materialSaida.getMaterial().getMaterial(),
											materialSaida.getMaterial().getMaterialTipo().getTipomaterial())
									.getQuantidade() - n);

							System.out.println("VALOR N listaSaida SUBTRAÇÃO " + n);
							materialEntrada.setQuantidade(n);
							sessao.merge(materialEntrada);

							n = 0;
							aux00 = 0;
							
							
							break;
						}

					}

				}

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

}
