package servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import bd.Conexao;
//import resource.ResourceReader;

@WebServlet("/pages/ImprimirRelatorioExcel")
public class ImprimirRelatorioIreportExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private boolean relatorioGerado;

	public ImprimirRelatorioIreportExcel() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletOutputStream saida = null;

		Conexao con = new Conexao();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("REPORT_CONNECTION", con);
		parameters.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
		String report = request.getParameter("rlt_nome");

		try {

			// CRED
			if (request.getParameter("credenciadoId") != null && !request.getParameter("credenciadoId").equals("0")) {
				parameters.put("CREDENCIADO", Integer.parseInt(request.getParameter("credenciadoId")));
			}

			// CODET
			if (request.getParameter("processoId") != null && !request.getParameter("processoId").equals("0")) {
				parameters.put("PROCESSOID", request.getParameter("processoId"));
			}
			if (request.getParameter("setorId") != null && !request.getParameter("setorId").equals("0")) {
				parameters.put("SETORATUALID", request.getParameter("setorId"));
				System.out.println("request.getParameter(\"setorId\")" + request.getParameter("setorId"));
			}
			if (request.getParameter("situacaoId") != null && !request.getParameter("situacaoId").equals("0")) {
				parameters.put("SITUACAOID", request.getParameter("situacaoId"));
				System.out.println("request.getParameter(\"situacaoId\")" + request.getParameter("situacaoId"));
			}

			// CETRAN
			if (request.getParameter("data") != null && !request.getParameter("data").equals("0")) {
				parameters.put("DATA", (request.getParameter("data")));
			}
			if (request.getParameter("id") != null && !request.getParameter("id").equals("0")) {
				parameters.put("ID", request.getParameter("id"));
			}

			// CONTRATO TERCEIRIZADO
			if (request.getParameter("dataInicial") != null && !request.getParameter("dataInicial").equals("0")) {
				parameters.put("DATAINICIAL", request.getParameter("dataInicial"));
			}
			if (request.getParameter("dataFinal") != null && !request.getParameter("dataFinal").equals("0")) {
				parameters.put("DATAFINAL", request.getParameter("dataFinal"));
			}
			if (request.getParameter("empresaNome") != null && !request.getParameter("empresaNome").equals("0")) {
				parameters.put("EMPRESA", String.format(request.getParameter("empresaNome")));
			}
			if (request.getParameter("nContrato") != null && !request.getParameter("nContrato").equals("0")) {
				parameters.put("NCONTRATO", request.getParameter("nContrato"));
			}
			if (request.getParameter("tipoEvento") != null && !request.getParameter("tipoEvento").equals("0")) {
				parameters.put("TIPOEVENTO", request.getParameter("tipoEvento"));
			}
			if (request.getParameter("unidade") != null && !request.getParameter("unidade").equals("0")) {
				parameters.put("UNIDADE", request.getParameter("unidade"));
			}

			// VISITA
			if (request.getParameter("visitaId") != null && !request.getParameter("visitaId").equals("0")) {
				parameters.put("VISITAID", request.getParameter("visitaId"));
			}

			// RELATORIO UNIDADE
			if (request.getParameter("unidadeId") != null && !request.getParameter("unidadeId").equals("0")) {
				parameters.put("UNIDADEID", Integer.parseInt(request.getParameter("unidadeId")));
			}

			/*
			 * if (request.getParameter("instrutorId2") != null &&
			 * !request.getParameter("instrutorId2").equals("0")){
			 * //parameters.put("PSET_CODIGO", request.getParameter("set_codigo") != null?
			 * Integer.parseInt(request.getParameter("set_codigo")) : 0);
			 * parameters.put("IDINSTRUTOR2",
			 * Integer.parseInt(request.getParameter("instrutorId2"))); } if
			 * (request.getParameter("instrutorId3") != null &&
			 * !request.getParameter("instrutorId3").equals("0")){
			 * //parameters.put("PSET_CODIGO", request.getParameter("set_codigo") != null?
			 * Integer.parseInt(request.getParameter("set_codigo")) : 0);
			 * parameters.put("IDINSTRUTOR3",
			 * Integer.parseInt(request.getParameter("instrutorId3"))); }
			 */

			/*
			 * if (request.getParameter("usuarioId") != null &&
			 * !request.getParameter("usuarioId").equals("0")){
			 * //parameters.put("PSET_CODIGO", request.getParameter("set_codigo") != null?
			 * Integer.parseInt(request.getParameter("set_codigo")) : 0);
			 * parameters.put("USUARIOID",
			 * Integer.parseInt(request.getParameter("usuarioId"))); }
			 */

			// if (request.getParameter("set_codigo") != null &&
			// !request.getParameter("set_codigo").equals("0")){
			// parameters.put("PSET_CODIGO", request.getParameter("set_codigo")
			// != null? Integer.parseInt(request.getParameter("set_codigo")) :
			// 0);
			// parameters.put("PSET_CODIGO",
			// Integer.parseInt((request.getParameter("set_codigo"))));
			// }

			String jasperFileName = "C:/detran-resource/Relatorios/";

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFileName + report + ".jasper", parameters,
					con.getConexao());
			saida = response.getOutputStream();

			this.relatorioGerado = jasperPrint.getPages().size() > 0;

			response.setContentType("application/excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + report + ".xls");

			JRXlsExporter xls = new JRXlsExporter();

			xls.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	           xls.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,saida);
	           xls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
	Boolean.TRUE);
	           xls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);

	           xls.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET,Integer.decode("65000"));
	           xls.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
	Boolean.TRUE);
	           xls.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
	Boolean.FALSE);
	           xls.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
	Boolean.TRUE);

			System.out.println("Before Exporting to XLS");

			xls.exportReport();

			saida.flush();
			saida.close();
			parameters.clear();
			con.fechaConexao();
			con = null;

		} catch (JRException e) {
			e.printStackTrace();
		} finally {
			con = null;
		}

	}

	public boolean isRelatorioGerado() {
		return relatorioGerado;
	}
}
