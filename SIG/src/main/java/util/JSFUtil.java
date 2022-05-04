package util;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class JSFUtil {
	
	public static ExternalContext getExternalContext(){
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public static FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public static Object getSessionAtribute(String atributo){
		return getExternalContext().getSessionMap().get(atributo);
	}
	
	public static void setSessionAtribute(String atributo, Object objeto){
		getExternalContext().getSessionMap().put(atributo, objeto);
	}
	
	public static Object getRequestParameter(String parametro){
		return getExternalContext().getRequestParameterMap().get(parametro);
	}
	
	public static void redirect(String url) throws IOException{
		getExternalContext().redirect(url);
	}
	
	/**
	 * retorna diretorio real
	 * @param diretorio
	 * @return
	 */
	public static String getDiretorioReal(String diretorio) { 
		return getExternalContext().getRealPath(diretorio);
	}
	
	/**
	 * Retorna Contexdo da Aplicao
	 * @return
	 */
	public static String getContexto() {
		return ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getContextPath(); 
	}

}
