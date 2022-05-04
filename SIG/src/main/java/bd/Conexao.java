package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class Conexao {

	private Connection conectar() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/detran", "root", "root");			
		} catch (SQLException ex) {
			System.err.println("SQLState: " + ex.getSQLState());
			System.err.println("VendorError: " + ex.getErrorCode());
			System.err.println("SQLException: ");
			ex.printStackTrace();
			throw new RuntimeException(ex);								
		} catch (Exception e) {
			System.err.println("Problemas ao tentar conectar com o banco de dados: ");
			e.printStackTrace();			
			throw new RuntimeException(e);
		}		
	}
	
	/*public Conexao() {
		try {
		  DataSource ds = new DataSource();
	       con = ds.getConnection();
	       con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sysfiladetran","postgres","postgres");
	
			//con = ds.getConnection();
			//Class.forName("org.postgresql.Driver");
			//Conex�o Local
			//con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sysfiladetran", "postgres", "postgres");
		}catch (SQLException _e) {
			System.err.println("CONEXAO.JAVA: " + _e);
		} /*catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	Connection con = conectar();
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	public Connection getConexao(){
		return con;
	}

	public void fechaConexao() {
		try {
			if (con != null) {
				con.close();
			}
			
		} catch(SQLException _e) {
			System.err.println("CONEXAO.JAVA: " + _e);
		}
	}

	public ResultSet consulta(String _sql) throws SQLException {
		Statement stmt = con.createStatement();
		return stmt.executeQuery(_sql);
	}

	public int update(String _sql) throws SQLException {
		Statement stmt = con.createStatement();
		return stmt.executeUpdate(_sql);
	}

	protected void finalize() {
		con = null;
	}
}
