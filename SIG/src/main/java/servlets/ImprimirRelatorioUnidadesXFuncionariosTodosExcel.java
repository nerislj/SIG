package servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import bd.Conexao;

@WebServlet("/pages/ImprimirRelatorioUnidadesXFuncionariosTodosExcel")
public class ImprimirRelatorioUnidadesXFuncionariosTodosExcel extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = null;
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=FuncionariosAtivosGeral.xls");

			Conexao con = new Conexao();

			ResultSet rs = con.consulta("SELECT * FROM  ("
					+ "SELECT nomeCompleto, cpf, unidadeNome, setorNome, 'TERCEIRIZADO' as Tipo FROM controle_relacaocontratoterceirizado LEFT JOIN controle_funcionarioterceirizado ON controle_funcionarioterceirizado.codigo = controle_relacaocontratoterceirizado.funcionarioTerceirizado_codigo LEFT JOIN pessoafisica ON pessoafisica.codigo = controle_funcionarioterceirizado.pessoa_codigo LEFT JOIN unidade ON unidade.codigo = controle_funcionarioterceirizado.unidade_codigo LEFT JOIN setor ON setor.codigo = controle_funcionarioterceirizado.setor_codigo"
					+ " UNION ALL SELECT nomeCompleto, cpf, unidadeNome, setorNome, 'SERVIDOR' as Tipo FROM funcionariosuf_servidores LEFT JOIN pessoafisica ON pessoafisica.codigo = funcionariosuf_servidores.pessoa_codigo LEFT JOIN unidade ON unidade.codigo = funcionariosuf_servidores.unidade_codigo LEFT JOIN setor ON setor.codigo = funcionariosuf_servidores.setor_codigo"
					+ " UNION ALL SELECT nomeCompleto, cpf, unidadeNome, setorNome, 'ESTAGIÁRIO' as Tipo FROM funcionariosuf_estagiarios LEFT JOIN pessoafisica ON pessoafisica.codigo = funcionariosuf_estagiarios.pessoa_codigo LEFT JOIN unidade ON unidade.codigo = funcionariosuf_estagiarios.unidade_codigo LEFT JOIN setor ON setor.codigo = funcionariosuf_estagiarios.setor_codigo"
					+ ") as T order by unidadeNome asc"
					+ ";");

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("lawix10");
			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Unidade");
			rowhead.createCell((short) 1).setCellValue("Setor");
			rowhead.createCell((short) 2).setCellValue("Nome Completo");
			rowhead.createCell((short) 3).setCellValue("CPF");
			rowhead.createCell((short) 4).setCellValue("Tipo");

			int i = 1;
			while (rs.next()) {
				HSSFRow row = sheet.createRow((short) i);
				row.createCell((short) 0).setCellValue(rs.getString("unidadeNome"));
				row.createCell((short) 1).setCellValue(rs.getString("setorNome"));
				row.createCell((short) 2).setCellValue(rs.getString("nomeCompleto"));
				row.createCell((short) 3).setCellValue(rs.getString("cpf"));
				row.createCell((short) 4).setCellValue(rs.getString("Tipo"));

				i++;
			}

			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();

			// WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			// WritableSheet s = w.createSheet("Demo", 0);
			// s.addCell(new Label(0, 0, "Hello World"));

			workbook.close();
		} catch (Exception e) {
			throw new ServletException("Exception in Excel Sample Servlet", e);
		} finally {
			if (out != null)
				out.close();
		}
	}
}