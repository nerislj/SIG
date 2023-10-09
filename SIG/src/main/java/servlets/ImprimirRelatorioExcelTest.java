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




@WebServlet("/pages/ImprimirRelatorioExcelTest")
public class ImprimirRelatorioExcelTest extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = null;
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=sampleName.xls");
			
			
			 Conexao con = new Conexao();
			    
			    ResultSet rs =  con.consulta("SELECT * FROM controle_funcionarioterceirizado inner join pessoafisica on controle_funcionarioterceirizado.pessoa_codigo = pessoafisica.codigo;");
			
			    HSSFWorkbook workbook = new HSSFWorkbook();
			    HSSFSheet sheet = workbook.createSheet("lawix10");
			    HSSFRow rowhead = sheet.createRow((short) 0);
			    rowhead.createCell((short) 0).setCellValue("codigo");
			    rowhead.createCell((short) 1).setCellValue("Unidade");
			    rowhead.createCell((short) 2).setCellValue("nomeCompleto");
			    int i = 1;
			    while (rs.next()){
			        HSSFRow row = sheet.createRow((short) i);
			        row.createCell((short) 0).setCellValue(Integer.toString(rs.getInt("codigo")));
			        row.createCell((short) 1).setCellValue(rs.getString("unidade_codigo"));
			        row.createCell((short) 2).setCellValue(rs.getString("nomeCompleto"));
			        i++;
			    }
		    
			    workbook.write(response.getOutputStream());
			    response.getOutputStream().flush();
			    
			//WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
		//	WritableSheet s = w.createSheet("Demo", 0);
			//s.addCell(new Label(0, 0, "Hello World"));
		   
		    workbook.close();
		} catch (Exception e) {
			throw new ServletException("Exception in Excel Sample Servlet", e);
		} finally {
			if (out != null)
				out.close();
		}
	}
}