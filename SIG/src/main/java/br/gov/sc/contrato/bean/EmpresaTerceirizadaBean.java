/* Decompiler 44ms, total 198ms, lines 174 */package br.gov.sc.contrato.bean;import br.gov.sc.contrato.dao.EmpresaTerceirizadaDAO;import br.gov.sc.contrato.domain.EmpresaTerceirizada;import br.gov.sc.sgi.dao.CidadeDAO;import br.gov.sc.sgi.dao.EstadoDAO;import br.gov.sc.sgi.dao.PessoaJuridicaDAO;import br.gov.sc.sgi.domain.Cidade;import br.gov.sc.sgi.domain.Estado;import br.gov.sc.sgi.domain.PessoaJuridica;import br.gov.sc.sgi.domain.Usuario;import java.io.Serializable;import java.util.ArrayList;import java.util.Date;import java.util.List;import javax.annotation.PostConstruct;import javax.faces.bean.ManagedBean;import javax.faces.bean.ViewScoped;import javax.faces.context.FacesContext;import javax.faces.event.ActionEvent;import javax.servlet.http.HttpSession;import org.omnifaces.util.Messages;@SuppressWarnings("serial")@ManagedBean@ViewScopedpublic class EmpresaTerceirizadaBean implements Serializable {   private EmpresaTerceirizada empresaTerceirizada;   private List<EmpresaTerceirizada> listaEmpresasTerceirizadas;   private PessoaJuridica pessoaJuridica;   private List<Estado> Estados;   private Estado estado;   private List<Cidade> Cidades;   private Usuario usuarioLogado;   @PostConstruct   public void listar() {      try {         EmpresaTerceirizadaDAO empresaDAO = new EmpresaTerceirizadaDAO();         this.listaEmpresasTerceirizadas = empresaDAO.listar();         this.estado = new Estado();         this.empresaTerceirizada = new EmpresaTerceirizada();         this.pessoaJuridica = new PessoaJuridica();         EstadoDAO estadoDAO = new EstadoDAO();         this.Estados = estadoDAO.listar("sigla");      } catch (RuntimeException var3) {         Messages.addGlobalError("Ocorreu um erro ao tentar listar as Empresas.", new Object[0]);         var3.printStackTrace();      }   }   public void salvar() {      try {         HttpSession sessao = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);         this.usuarioLogado = (Usuario)sessao.getAttribute("usuario");         EmpresaTerceirizadaDAO empresaDAO = new EmpresaTerceirizadaDAO();         PessoaJuridicaDAO pessoaDAO = new PessoaJuridicaDAO();         pessoaDAO.merge(this.pessoaJuridica);         this.pessoaJuridica = PessoaJuridicaDAO.carregarCnpj(this.pessoaJuridica.getCnpj());         this.empresaTerceirizada.setDataCadastro(new Date());         this.empresaTerceirizada.setUsuarioCadastro(this.usuarioLogado);         this.empresaTerceirizada.setPessoaJuridica(this.pessoaJuridica);         empresaDAO.merge(this.empresaTerceirizada);         this.empresaTerceirizada = new EmpresaTerceirizada();         this.listaEmpresasTerceirizadas = empresaDAO.listar();         Messages.addGlobalInfo("Empresa cadastrada com Sucesso!", new Object[0]);      } catch (RuntimeException var4) {         Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Empresa.", new Object[0]);         var4.printStackTrace();      }   }   public void excluir(ActionEvent evento) {      try {         this.empresaTerceirizada = (EmpresaTerceirizada)evento.getComponent().getAttributes().get("empresaSelecionada");         EmpresaTerceirizadaDAO empresaDAO = new EmpresaTerceirizadaDAO();         empresaDAO.excluir(this.empresaTerceirizada);         this.empresaTerceirizada = new EmpresaTerceirizada();         this.listaEmpresasTerceirizadas = empresaDAO.listar();         Messages.addGlobalInfo("Empresa removida com sucesso.", new Object[0]);      } catch (RuntimeException var3) {         Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Empresa.", new Object[0]);         var3.printStackTrace();      }   }   public void editar(ActionEvent evento) {      this.empresaTerceirizada = (EmpresaTerceirizada)evento.getComponent().getAttributes().get("empresaSelecionada");      new EmpresaTerceirizadaDAO();      this.pessoaJuridica = EmpresaTerceirizadaDAO.carregarCnpj(this.empresaTerceirizada.getPessoaJuridica().getCnpj());      CidadeDAO municipioDAO = new CidadeDAO();      this.Cidades = municipioDAO.buscarPorEstado(this.empresaTerceirizada.getPessoaJuridica().getEstadoEndereco().getCodigo());   }   public void popular() {      try {         if (this.pessoaJuridica.getEstadoEndereco() != null) {            CidadeDAO municipioDAO = new CidadeDAO();            this.Cidades = municipioDAO.buscarPorEstado(this.pessoaJuridica.getEstadoEndereco().getCodigo());         } else {            this.Cidades = new ArrayList();         }      } catch (NullPointerException var2) {         Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades", new Object[0]);      }   }   public void buscarCNPJ() {      new EmpresaTerceirizadaDAO();      this.pessoaJuridica = EmpresaTerceirizadaDAO.carregarCnpj(this.pessoaJuridica.getCnpj());      CidadeDAO municipioDAO = new CidadeDAO();      this.Cidades = municipioDAO.buscarPorEstado(this.pessoaJuridica.getEstadoEndereco().getCodigo());   }   public EmpresaTerceirizada getEmpresaTerceirizada() {      return this.empresaTerceirizada;   }   public void setEmpresaTerceirizada(EmpresaTerceirizada empresaTerceirizada) {      this.empresaTerceirizada = empresaTerceirizada;   }   public List<EmpresaTerceirizada> getListaEmpresasTerceirizadas() {      return this.listaEmpresasTerceirizadas;   }   public void setListaEmpresasTerceirizadas(List<EmpresaTerceirizada> listaEmpresasTerceirizadas) {      this.listaEmpresasTerceirizadas = listaEmpresasTerceirizadas;   }   public PessoaJuridica getPessoaJuridica() {      return this.pessoaJuridica;   }   public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {      this.pessoaJuridica = pessoaJuridica;   }   public List<Estado> getEstados() {      return this.Estados;   }   public void setEstados(List<Estado> estados) {      this.Estados = estados;   }   public Estado getEstado() {      return this.estado;   }   public void setEstado(Estado estado) {      this.estado = estado;   }   public List<Cidade> getCidades() {      return this.Cidades;   }   public void setCidades(List<Cidade> cidades) {      this.Cidades = cidades;   }   public Usuario getUsuarioLogado() {      return this.usuarioLogado;   }   public void setUsuarioLogado(Usuario usuarioLogado) {      this.usuarioLogado = usuarioLogado;   }}