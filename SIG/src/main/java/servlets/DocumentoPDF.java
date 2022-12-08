package servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.encoders.ImageEncoderFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.lowagie.text.HeaderFooter;

import br.gov.sc.contrato.bean.ContratoTerceirizadoBean;
import br.gov.sc.contrato.dao.EmpresaTerceirizadaDAO;
import br.gov.sc.contrato.dao.EventoTerceirizadoDAO;
import br.gov.sc.contrato.dao.UserClaimsContratoDAO;
import br.gov.sc.contrato.domain.EmpresaTerceirizada;
import br.gov.sc.contrato.domain.EventoTerceirizado;
import br.gov.sc.contrato.domain.UserClaimsContrato;
import br.gov.sc.sgi.dao.UnidadeDAO;
import br.gov.sc.sgi.dao.UsuarioDAO;
import br.gov.sc.sgi.domain.Unidade;
import br.gov.sc.sgi.domain.Usuario;

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
	private EmpresaTerceirizada empresaNome;
	private List<UserClaimsContrato> listaUserClaims;
	private String usuarioLogado;
	private Usuario userSessao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String report = request.getParameter("eventoinconsistencia");
		if (report != null) {
			gerarRelatorioEventoInconsistencias(request, response);
		}

		String report2 = request.getParameter("eventoafastamento");
		if (report2 != null) {
			gerarRelatorioEventoAfastamento(request, response);
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public void gerarRelatorioEventoInconsistencias(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Document documento = new Document(PageSize.A4.rotate());

		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

		try {

			System.out.println(request.getParameter("unidade") + " OQ ESTA VINDO?");

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

			if (request.getParameter("usuarioLogado") != null) {

				usuarioLogado = request.getParameter("usuarioLogado");

				System.out.println(usuarioLogado + "NOVO usuarioLogado");

			}

			Image img = Image.getInstance("C://Users/detran-logo.png");
			img.setAlignment(Element.ALIGN_CENTER);
			img.setWidthPercentage(50);

			Paragraph logo = new Paragraph();

			logo.add(img);

			// tipo conteudo
			response.setContentType("apllication/pdf");
			// nome documento
			response.addHeader("Content-Disposition", "inline; filename=" + "evento-inconsistencia.pdf");

			// tabela
			PdfPTable tabela = new PdfPTable(5);

			float[] widths1 = new float[] { 25f, 18f, 7f, 5f, 15f };
			tabela.setWidths(widths1);

			// cabeçalho

			Paragraph p1 = new Paragraph("ESTADO DE SANTA CATARINA");
			Paragraph p2 = new Paragraph("DEPARTAMENTO ESTADUAL DE TRÂNSITO DE SANTA CATARINA");

			Paragraph p3 = new Paragraph("GERÊNCIA DE APOIO OPERACIONAL");

			p1.setAlignment(Element.ALIGN_CENTER);
			p2.setAlignment(Element.ALIGN_CENTER);
			p3.setAlignment(Element.ALIGN_CENTER);
			p3.setSpacingAfter(10);

			EmpresaTerceirizadaDAO empresaDAO = new EmpresaTerceirizadaDAO();

			empresaNome = empresaDAO.carregaEmpresaPorCOD(empresaTerceiriza);

			PdfPCell header2 = new PdfPCell(new Paragraph("PLANILHA INCONSISTÊNCIA - " + fmt.format(dataInicial) + " à "
					+ fmt.format(dataFinal) + " - EMPRESA: " + empresaNome.getPessoaJuridica().getNomeFantasia()));

			header2.setExtraParagraphSpace(3);

			header2.setBackgroundColor(new BaseColor(120, 120, 120));

			header2.setColspan(5);

			tabela.addCell(header2);

			Font yourCustomFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

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

			System.out.println(usuarioLogado + "usuarioLogadousuarioLogadousuarioLogadousuarioLogadousuarioLogado");
			EventoTerceirizadoDAO eventoDAO = new EventoTerceirizadoDAO();
			UserClaimsContratoDAO userClaimDAO = new UserClaimsContratoDAO();
			UnidadeDAO unidadeDAO = new UnidadeDAO();

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			userSessao = usuarioDAO.autenticarPorCpf(usuarioLogado);

			System.out.println(userSessao + "userSessaouserSessao");

			listaUserClaims = userClaimDAO.listarPorUsuarioLogado(userSessao);

			ArrayList<Unidade> codigosUnidades = new ArrayList<Unidade>();
			for (int posicao = 0; posicao < listaUserClaims.size(); posicao++) {

				codigosUnidades.add(listaUserClaims.get(posicao).getUnidade());

			}

			if (userSessao.getNivelAcesso().getCodigo() == 1) {

				codigosUnidades = (ArrayList<Unidade>) unidadeDAO.listar();

			}

			if (userSessao.getSetor().getSetorNome().contains("Gerência de Apoio Operacional")
					&& userSessao.getNivelAcesso().getNivel().contains("CONTRATO")) {

				codigosUnidades = (ArrayList<Unidade>) unidadeDAO.listar();
			}

			System.out.println(nContrato + empresaTerceiriza + setorRelatorio + tipoEvento + dataInicial + dataFinal);
			listaEventosTerceirizados = eventoDAO.listarPorEmpresaPDF(nContrato, empresaTerceiriza, setorRelatorio,
					tipoEvento, dataInicial, dataFinal, codigosUnidades);
			System.out.println(listaEventosTerceirizados + "listaEventosTerceirizados DOCUMENTOPDF");
			Phrase ph;

			int total = 0;

			while (listaEventosTerceirizados.size() != 0) {
				int count = 0;
				int j = 0;

				String nome = listaEventosTerceirizados.get(j).getContratoTerceirizado().getUnidade().getUnidadeNome();

				for (int i = 0; i < listaEventosTerceirizados.size();) {

					if (listaEventosTerceirizados.get(i).getContratoTerceirizado().getUnidade().getUnidadeNome()
							.equals(nome)) {
						count++;

						String[] partes = listaEventosTerceirizados.get(i).getHoras().split(":");

						int horas = Integer.parseInt(partes[0]) * 60 + Integer.parseInt(partes[1]);

						System.out.println(horas + " horas");

						total += horas;

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getColaborador().getPessoa().getNomeCompleto(),
								yourCustomFont);
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getContratoTerceirizado().getUnidade()
								.getUnidadeNome(), yourCustomFont);
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(
								fmt.format(listaEventosTerceirizados.get(i).getDataEventoInicial()) + " - "
										+ fmt.format(listaEventosTerceirizados.get(i).getDataEventoFinal()),
								yourCustomFont);
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getHoras(), yourCustomFont);
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getMotivo(), yourCustomFont);
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

			
				
				PdfPCell totalAmtCell = new PdfPCell(new Phrase("Total Horas: " + totalRelatorio, yourCustomFont));
				totalAmtCell.setBackgroundColor(new BaseColor(152, 251, 152));
				totalAmtCell.setColspan(5);
				totalAmtCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				tabela.addCell(totalAmtCell);

			}

			// criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			// abrir documento -> conteudo
			documento.open();

			documento.add(logo);
			documento.add(p1);
			documento.add(p2);
			documento.add(p3);

			documento.add(tabela);
			documento.close();
			System.out.println("Successfull.");

		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	public void gerarRelatorioEventoAfastamento(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Document documento = new Document(PageSize.A4.rotate());

		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

		try {

			System.out.println(request.getParameter("unidade") + " OQ ESTA VINDO?");

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
				tipoEvento = new String("Afastamento");
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

			if (request.getParameter("usuarioLogado") != null) {

				usuarioLogado = request.getParameter("usuarioLogado");

				System.out.println(usuarioLogado + "NOVO usuarioLogado");

			}

			Image img = Image.getInstance("C://Users/detran-logo.png");
			
			img.setWidthPercentage(50);
			img.setAlignment(Element.ALIGN_CENTER);
			
			
		

	
			

			Paragraph logo = new Paragraph();
			
			

			logo.add(img);
			

			// tipo conteudo
			response.setContentType("apllication/pdf");
			// nome documento
			response.addHeader("Content-Disposition", "inline; filename=" + "evento-afastamento.pdf");

			// tabela
			PdfPTable tabela = new PdfPTable(7);

			float[] widths1 = new float[] { 18f, 18f, 8f, 18f, 8f, 4f, 5f };
			tabela.setWidths(widths1);

			// cabeçalho

			Paragraph p1 = new Paragraph("ESTADO DE SANTA CATARINA");
			Paragraph p2 = new Paragraph("DEPARTAMENTO ESTADUAL DE TRÂNSITO DE SANTA CATARINA");

			Paragraph p3 = new Paragraph("GERÊNCIA DE APOIO OPERACIONAL");

			p1.setAlignment(Element.ALIGN_CENTER);
			p2.setAlignment(Element.ALIGN_CENTER);
			p3.setAlignment(Element.ALIGN_CENTER);
			p3.setSpacingAfter(10);

			EmpresaTerceirizadaDAO empresaDAO = new EmpresaTerceirizadaDAO();

			empresaNome = empresaDAO.carregaEmpresaPorCOD(empresaTerceiriza);

			PdfPCell header2 = new PdfPCell(new Paragraph("PLANILHA AFASTAMENTO - " + fmt.format(dataInicial) + " à "
					+ fmt.format(dataFinal) + " - EMPRESA: " + empresaNome.getPessoaJuridica().getNomeFantasia()));

			header2.setExtraParagraphSpace(3);

			header2.setBackgroundColor(new BaseColor(120, 120, 120));

			header2.setColspan(7);

			tabela.addCell(header2);

			Font yourCustomFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

			PdfPCell cell = new PdfPCell(new Phrase("Colaborador"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Setor"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Data"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Substituto"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Data"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Dias"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			cell = new PdfPCell(new Phrase("Horas"));
			cell.setBackgroundColor(new BaseColor(0, 173, 239));
			tabela.addCell(cell);

			// popular

			System.out.println(usuarioLogado + "usuarioLogadousuarioLogadousuarioLogadousuarioLogadousuarioLogado");
			EventoTerceirizadoDAO eventoDAO = new EventoTerceirizadoDAO();
			UserClaimsContratoDAO userClaimDAO = new UserClaimsContratoDAO();
			UnidadeDAO unidadeDAO = new UnidadeDAO();

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			userSessao = usuarioDAO.autenticarPorCpf(usuarioLogado);

			System.out.println(userSessao + "userSessaouserSessao");

			listaUserClaims = userClaimDAO.listarPorUsuarioLogado(userSessao);

			ArrayList<Unidade> codigosUnidades = new ArrayList<Unidade>();
			for (int posicao = 0; posicao < listaUserClaims.size(); posicao++) {

				codigosUnidades.add(listaUserClaims.get(posicao).getUnidade());

			}

			if (userSessao.getNivelAcesso().getCodigo() == 1) {

				codigosUnidades = (ArrayList<Unidade>) unidadeDAO.listar();

			}

			if (userSessao.getSetor().getSetorNome().contains("Gerência de Apoio Operacional")
					&& userSessao.getNivelAcesso().getNivel().contains("CONTRATO")) {

				codigosUnidades = (ArrayList<Unidade>) unidadeDAO.listar();
			}

			System.out.println(nContrato + empresaTerceiriza + setorRelatorio + tipoEvento + dataInicial + dataFinal);
			listaEventosTerceirizados = eventoDAO.listarPorEmpresaPDF(nContrato, empresaTerceiriza, setorRelatorio,
					tipoEvento, dataInicial, dataFinal, codigosUnidades);
			System.out.println(listaEventosTerceirizados + "listaEventosTerceirizados DOCUMENTOPDF");
			Phrase ph;

			int totalDias = 0;
			int totalMinutos = 0;
			int total = 0;

			int totalDiasFinal = 0;

			while (listaEventosTerceirizados.size() != 0) {
				//int count = 0;
				int j = 0;

				String nome = listaEventosTerceirizados.get(j).getContratoTerceirizado().getUnidade().getUnidadeNome();

				for (int i = 0; i < listaEventosTerceirizados.size();) {

					if (listaEventosTerceirizados.get(i).getContratoTerceirizado().getUnidade().getUnidadeNome()
							.equals(nome)) {
						//count++;

						if (listaEventosTerceirizados.get(i).getHoras() == null) {

							String partesDias = listaEventosTerceirizados.get(i).getDias();

							/// System.out.println(partes + "partespartespartespartes");
							// int dias += Integer.parseInt(partes);

							// System.out.println(dias + "diasdiasdiasdiasdiasdiasdias");

							System.out.println("TOTAL DIAS" + partesDias);

							totalDias += Integer.parseInt(partesDias);

						} else {

							String[] partesHoras = listaEventosTerceirizados.get(i).getHoras().split(":");

							int horas = Integer.parseInt(partesHoras[0]) * 60 + Integer.parseInt(partesHoras[1]);

							/// System.out.println(partes + "partespartespartespartes");
							// int dias += Integer.parseInt(partes);

							// System.out.println(dias + "diasdiasdiasdiasdiasdiasdias");

							totalMinutos += horas;

							System.out.println(totalMinutos + " totalMinutos");

						}

						total = totalDias;

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getColaborador().getPessoa().getNomeCompleto(),
								yourCustomFont);
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getContratoTerceirizado().getUnidade()
								.getUnidadeNome(), yourCustomFont);
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(
								fmt.format(listaEventosTerceirizados.get(i).getDataEventoInicial()) + " - "
										+ fmt.format(listaEventosTerceirizados.get(i).getDataEventoFinal()),
								yourCustomFont);
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						if (listaEventosTerceirizados.get(i).getSubstituto() != null) {
							ph = new Phrase(
									listaEventosTerceirizados.get(i).getSubstituto().getPessoa().getNomeCompleto(),
									yourCustomFont);
						} else {
							ph = new Phrase("SEM COBERTURA", yourCustomFont);
						}
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						if (listaEventosTerceirizados.get(i).getDataSubstitutoInicial() != null) {
							ph = new Phrase(
									fmt.format(listaEventosTerceirizados.get(i).getDataSubstitutoInicial()) + " - "
											+ fmt.format(listaEventosTerceirizados.get(i).getDataSubstitutoFinal()),
									yourCustomFont);
						} else {
							ph = new Phrase(
									fmt.format(listaEventosTerceirizados.get(i).getDataEventoInicial()) + " - "
											+ fmt.format(listaEventosTerceirizados.get(i).getDataEventoFinal()),
									yourCustomFont);
						}
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getDias(), yourCustomFont);
						cell.addElement(ph);
						tabela.addCell(cell);

						cell = new PdfPCell();
						ph = new Phrase(listaEventosTerceirizados.get(i).getHoras(), yourCustomFont);
						cell.addElement(ph);
						tabela.addCell(cell);

						listaEventosTerceirizados.remove(i);

					} else {
						i++;
						total = 0;
						break;

					}

					int days = (int) TimeUnit.MINUTES.toDays(totalMinutos);
					long hours = TimeUnit.MINUTES.toHours(totalMinutos) - (days * 24);
					long minute = TimeUnit.MINUTES.toMinutes(totalMinutos)
							- (TimeUnit.MINUTES.toHours(totalMinutos) * 60);
					long second = TimeUnit.MINUTES.toSeconds(totalMinutos)
							- (TimeUnit.MINUTES.toMinutes(totalMinutos) * 60);

					System.out.println(days + " days");
					System.out.println(hours + " hours");
					System.out.println(minute + " minute");
					System.out.println(second + " second");

					totalDiasFinal = total + days;

					totalRelatorio = totalDiasFinal + " dia(s) " + hours + " hora(s) " + minute + " minuto(s)";

				}
				// System.out.println("Nome: " + nome + " Frequência: " + count);

				

				PdfPCell totalAmtCell = new PdfPCell(new Phrase("Total: " + totalRelatorio, yourCustomFont));
				totalAmtCell.setBackgroundColor(new BaseColor(152, 251, 152));
				totalAmtCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				totalAmtCell.setColspan(7);
				tabela.addCell(totalAmtCell);

			}

			// criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			// abrir documento -> conteudo
			documento.open();

			documento.add(logo);
			documento.add(p1);
			documento.add(p2);
			documento.add(p3);

			documento.add(tabela);
			documento.close();
			System.out.println("Successfull.");

		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}

	}

	public EmpresaTerceirizada getEmpresaNome() {
		return empresaNome;
	}

	public void setEmpresaNome(EmpresaTerceirizada empresaNome) {
		this.empresaNome = empresaNome;
	}

	public Usuario getUserSessao() {
		return userSessao;
	}

	public void setUserSessao(Usuario userSessao) {
		this.userSessao = userSessao;
	}

}
