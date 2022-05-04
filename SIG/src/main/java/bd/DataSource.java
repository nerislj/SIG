/*
 * PoolConnectionManager.java
 *
 * Created on 31 de Janeiro de 2005, 17:40
 */

package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DataSource {
    
    protected static javax.sql.DataSource dataSource;
    
    public DataSource() {    	
    	    	
        if (dataSource == null) {
        	try {
        		Properties env = new Properties();
        		
        		//env.setProperty("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        		//env.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        	    env.setProperty("java.naming.provider.url", "jnp://localhost:1099");
        		
        		InitialContext ic = new InitialContext(env);
        		//ic.addToEnvironment("java.naming.factory.initial", "org.jboss.as.naming.InitialContextFactory");
        		//ic.addToEnvironment("java.naming.factory.url.pkgs","org.jboss.naming"); 
        		//ic.addToEnvironment("java.naming.factory.url.pkgs.prefixes","org.apache.naming" );
        		//ic.addToEnvironment("java.naming.provider.url","org.apache.naming ");
        		
        		
        		dataSource =(javax.sql.DataSource) ic.lookup("java:/jsfDS");
        		Context initCtx = new InitialContext();
        		Context envCtx = (Context) initCtx.lookup("java:comp/env");
        		dataSource =  (javax.sql.DataSource) envCtx.lookup("jdbc/jsfDS");
        		
			} catch (NamingException _e) {
				System.out.println("DataSource: " + _e.getMessage());
			}
        }
    }
    
    public Connection getConnection() {
		
		return null;
	}

    
    
    public static void main(String[] args){
		Conexao con = new Conexao();
		try {
			ResultSet rs = con.consulta("select * from usuario");
			if (rs.next()){
				System.out.println(rs.getString(1));
			}
			rs.close();
			rs = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
   
}
