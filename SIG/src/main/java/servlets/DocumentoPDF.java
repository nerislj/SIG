package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.gov.sc.contrato.bean.ContratoTerceirizadoBean;
import br.gov.sc.contrato.dao.EventoTerceirizadoDAO;
import br.gov.sc.contrato.domain.EventoTerceirizado;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.domain.Unidade;

@SuppressWarnings("serial")
@WebServlet("/pages/DocumentoPDF")
public class DocumentoPDF extends HttpServlet {

	private List<EventoTerceirizado> listaEventosTerceirizados;
	String nContrato;
	Long empresaTerceiriza;
	Long setorRelatorio;
	String tipoEvento;
	Date dataInicial;
	Date dataFinal;
	
	String totalRelatorio;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String report = request.getParameter("eventoinconsistencia");
		if (report != null) {
			gerarRelatorioEventoInconsistencias(request, response);
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	protected void gerarRelatorioEventoInconsistencias(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Document documento = new Document();
		try {

			System.out.println(request.getParameter("unidade") + " OQ ESTA VINDO?");

			SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

			if (request.getParameter("nContrato") != null) {
				nContrato = request.getParameter("nContrato");
				System.out.println(nContrato + " PEGOU ncontrato");
			}
			if (request.getParameter("empresaNome") != null) {
				empresaTerceiriza = Long.parseLong(request.getParameter("empresaNome"));
				System.out.println(empresaTerceiriza + " PEGOU empresanome");
			}
			if (request.getParameter("unidade") == null) {
				setorRelatorio = null;
				System.out.println("É NULO DOCUMENTOPDF");
			} else {
				System.out.println("ENTROU request UnidadeS");
				setorRelatorio = Long.parseLong(request.getParameter("unidade"));
				System.out.println(setorRelatorio + " PEGOU setorrelatorio");
			}

			if (request.getParameter("tipoEvento") != null) {
				tipoEvento = new String("Inconsistência");
				System.out.println(tipoEvento + " PEGOU tipoevento");
			}
			if (request.getParameter("dataInicial") != null) {
				String parameter = request.getParameter("dataInicial");
				dataInicial = fmt.parse(parameter);

				System.out.println(dataInicial + " PEGOU datainicial");
			}
			if (request.getParameter("dataFinal") != null) {

				String parameter = request.getParameter("dataFinal");
				dataFinal = fmt.parse(parameter);

				System.out.println(dataFinal + " PEGOU datafinal");
			}

			// tipo conteudo
			response.setContentType("apllication/pdf");
			// nome documento
			response.addHeader("Content-Disposition", "inline; filename=" + "eventos.pdf");

			// tabela
			PdfPTable tabela = new PdfPTable(5);

			// cabeçalho

			PdfPCell cell = new PdfPCell(new Phrase("Colaborador"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Setor"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Data"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Horas"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Motivo"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			// popular
			EventoTerceirizadoDAO eventoDAO = new EventoTerceirizadoDAO();

			System.out.println(nContrato + empresaTerceiriza + setorRelatorio + tipoEvento + dataInicial + dataFinal);
			listaEventosTerceirizados = eventoDAO.listarPorEmpresaPDF(nContrato, empresaTerceiriza, setorRelatorio,
					tipoEvento, dataInicial, dataFinal);
			System.out.println(listaEventosTerceirizados + "listaEventosTerceirizados DOCUMENTOPDF");
			Phrase ph;

			int total = 0;

			while (listaEventosTerceirizados.size() != 0) {
				int count = 0;
				int j = 0;

				String nome = listaEventosTerceirizados.get(j).getColaborador().getUnidade().getUnidadeNome();

				for (int i = 0; i < listaEventosTerceirizados.size();) {

					if (listaEventosTerceirizados.get(i).getColaborador().getUnidade().getUnidadeNome().equals(nome)) {
						count++;

						String[] partes = listaEventosTerceirizados.get(i).getHoras().split(":");

						int horas = Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);

						System.out.println(horas + " horas");

						total += horas;

						cell = new PdfPCell();
						ph = new Phrase(
								listaEventosTerceirizados.get(i).getColaborador().getPessoa().getNomeCompleto());
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(
								listaEventosTerceirizados.get(i).getColaborador().getUnidade().getUnidadeNome());
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase("TEste");
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getHoras());
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getMotivo());
						cell.addElement(ph);
						tabela.addCell(cell);

						listaEventosTerceirizados.remove(i);

					} else {
						i++;
						total = 0;
						break;
						

					}

					
					long hours = TimeUnit.MINUTES.toHours(Long.valueOf(total));
					long remainMinutes = total - TimeUnit.HOURS.toMinutes(hours);

					System.out.println(String.format("%02d:%02d", hours, remainMinutes)
							+ " String.format(\"%02d:%02d\", hours, remainMinutes)");
					
				totalRelatorio = String.format("%02d:%02d", hours, remainMinutes);
					
				}
				System.out.println("Nome: " + nome + " Frequência: " + count);

				
				
				PdfPCell totalAmtCell1 = new PdfPCell(new Phrase(""));

				tabela.addCell(totalAmtCell1);
				PdfPCell totalAmtCell2 = new PdfPCell(new Phrase(""));

				tabela.addCell(totalAmtCell2);
				PdfPCell totalAmtCell3 = new PdfPCell(new Phrase(""));

				tabela.addCell(totalAmtCell3);
				PdfPCell totalAmtStrCell = new PdfPCell(new Phrase(""));

				tabela.addCell(totalAmtStrCell);
				PdfPCell totalAmtCell = new PdfPCell(new Phrase("Total Horas: " + totalRelatorio));

				tabela.addCell(totalAmtCell);

			}

			// criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			// abrir documento -> conteudo
			documento.open();
			documento.add(tabela);
			documento.close();
			System.out.println("Successfull.");

		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}

	}

}
