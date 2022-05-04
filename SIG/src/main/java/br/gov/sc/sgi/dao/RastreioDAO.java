package br.gov.sc.sgi.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.gov.sc.sgi.domain.Oficio;
import br.gov.sc.sgi.domain.Rastreio;
import br.gov.sc.sgi.domain.RastreioRelacao;
import br.gov.sc.sgi.util.HibernateUtil;

public class RastreioDAO extends GenericDAO<Rastreio> {

	public void salvarRastreio(Rastreio recursomultarastreio, List<RastreioRelacao> listaSaida) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();

			recursomultarastreio.setRastreio(recursomultarastreio.getRastreio());
			recursomultarastreio.setDestino(recursomultarastreio.getDestino());

			sessao.save(recursomultarastreio);

			for (int posicao = 0; posicao < listaSaida.size(); posicao++) {
				RastreioRelacao oficioSaida = listaSaida.get(posicao);

				oficioSaida.setRastreio(recursomultarastreio);

				sessao.save(oficioSaida);

				Oficio oficio = oficioSaida.getOficio();
				oficio.setStatus("Encaminhado");

				sessao.update(oficio);
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
