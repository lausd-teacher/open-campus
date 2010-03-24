package edu.tecsup.lms.action.usuario;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ActionException;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.usuario.Rol;
import edu.tecsup.lms.modelo.usuario.UsuarioFiltro;
import edu.tecsup.lms.service.UsuarioService;
import edu.tecsup.lms.util.Archivo;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;
import edu.tecsup.lms.util.UsuariosConectados;

public class UsuarioAction extends BaseAction {

	private static final long serialVersionUID = -87535671999268263L;

	protected UsuarioService usuarioService;

	private Integer id;
	
	private Integer rol;

	private String email;

	private String username;

	private String nombre1;

	private String nombre2;

	private String paterno;

	private String materno;

	private Integer pagina;
	
	private Integer total;
	
	private Integer paginas;
	
	private String clave;

	private String claveTemp;

	private Usuario usuario;
	
	private UsuarioFiltro filtro;
	
	private Collection<Usuario> cumples;
	
	protected List<Usuario> usuarios;
	
	private Map<String,Usuario> conectados;
	
	private Map<Integer,Usuario> conectadosEnChat;
	
	protected Collection<Rol> roles;
	
	public Collection<Rol> getRoles() {
		return roles;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public Map<String, Usuario> getConectados() {
		return conectados;
	}

	public Map<Integer, Usuario> getConectadosEnChat() {
		return conectadosEnChat;
	}

	public Collection<Usuario> getCumples() {
		return cumples;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPaginas() {
		return paginas;
	}

	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public String getClaveTemp() {
		return claveTemp;
	}

	public void setClaveTemp(String claveTemp) {
		this.claveTemp = claveTemp;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username.replaceAll("%", "");
	}

	public String getNombre1() {
		return nombre1;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1.replaceAll("%", "");
	}

	public String getNombre2() {
		return nombre2;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2.replaceAll("%", "");
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno.replaceAll("%", "");
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno.replaceAll("%", "");
	}

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}
	
	public UsuarioFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(UsuarioFiltro filtro) {
		this.filtro = filtro;
	}

	public String cargarPortada() throws Exception{
		log.info("cargarPortada()");
		try {
			cumples = usuarioService.verCumpleanieros();
		} catch (Exception e) {
			log.error(e.toString());
			return NONE;
		}
		return SUCCESS;
	}
	
	public String cargarConectadosEnChat() throws Exception{
		//log.info("cargarConectadosEnChat()");
		conectadosEnChat = UsuariosConectados.c; 
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String cargarConectados() throws Exception{
		//log.info("cargarConectados()");
		conectados = (Map<String, Usuario>)getApplication().get(Constante.USUARIOS_ACTUAL);
		return SUCCESS;
	}

	public String buscar() throws ActionException{
		log.info("buscar()");
		try{
			
			roles = usuarioService.listarRoles();
			roles.removeAll(Usuario.getRolesPrivados());
			
			log.info("Determinando filtro...");
			
			if(this.username != null){
				
				if(!Usuario.getRolesPrivados().contains(new Rol(rol)) && rol != 0
						&& ((username != null && username.trim().length()>2)
						|| (nombre1 != null && nombre1.trim().length()>2)
						|| (nombre2 != null && nombre2.trim().length()>2)
						|| (paterno != null && paterno.trim().length()>2)
						|| (materno != null && materno.trim().length()>2))){
				
					this.filtro = new UsuarioFiltro();
					filtro.setUsuario(Formato.matizarFrace(username));
					filtro.setRol(rol);
					filtro.setNombre1(Formato.matizarFrace(nombre1));
					filtro.setNombre2(Formato.matizarFrace(nombre2));
					filtro.setPaterno(Formato.matizarFrace(paterno));
					filtro.setMaterno(Formato.matizarFrace(materno));
										
					getUsuarioSession().getFiltros().setFiltroDirectorio(filtro);
				
				}else{
					addActionError("Debe indicar el rol y como mínimo uno de los cinco campos del formulario con una longitud no menor a 3 caracteres.");
					log.error("Faltan parametros del formulario.");
				}
				
			}else if(getUsuarioSession().getFiltros().getFiltroDirectorio() != null){
				
				this.filtro = getUsuarioSession().getFiltros().getFiltroDirectorio();
								
			}/*else{
				this.filtro = new UsuarioFiltro(); //Solo para el admin
			}*/
			
			log.info("Consultando resultados...");
			
			if(this.filtro != null){
				this.usuarios = usuarioService.buscar(filtro); 
			}
			
			log.info("Paginando resultados...");
			
			if (this.usuarios != null) {
				
				total = usuarios.size();
				paginas = total / Constante.BUSQUEDA_CANTIDAD_DIRECTORIO;
				
				if(pagina==null) pagina=0;
				if(pagina>paginas) pagina=paginas;
				
				int inicio = pagina * Constante.BUSQUEDA_CANTIDAD_DIRECTORIO;
				int fin = inicio + Constante.BUSQUEDA_CANTIDAD_DIRECTORIO;
				this.usuarios = usuarios.subList(inicio, (fin>usuarios.size())?usuarios.size():fin);
					
			}
			
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String verFoto()  throws Exception{
		log.info("verFoto() "+id);
		String filename = id + ".jpg";
		String source = Constante.RUTA_FOTOS + filename;
		Archivo.downloadResizedFile(filename, source, 85, 0, getResponse());
		return NONE;
	}
	
	public String miFoto()  throws Exception{
		log.info("miFoto()");
		String filename = getUsuarioSession().getId() + ".jpg";
		String source = Constante.RUTA_FOTOS + filename;
		Archivo.downloadResizedFile(filename, source, 85, 0, getResponse());
		return NONE;
	}
	
	public String misDatos()  throws Exception{
		log.info("misDatos()");
		//IF es empresa manda a otro jsp y si es persona al q ya tienes
		return SUCCESS;
	}
	
	public String cambiarEmail()  throws Exception{
		log.info("cambiarEmail()");
		try {
			
			if(email != null){
				log.info("Correo antes de validar: "+email);
				String [] emails = email.trim().replaceAll("  ", " ").replaceAll(" ", ",").replaceAll(";", ",").split(",");
				email = "";
				for (int i = 0; i < emails.length; i++) {
					if(Formato.isEmail(emails[i]))
						email += emails[i]+","; 
				}
				log.info("Correo despues de validar: "+email);
				
				Usuario usuario = getUsuarioSession();
				int rpt = usuarioService.cambiarEmail(usuario.getId(), email);
				
				PrintWriter out = getResponse().getWriter();
				if (rpt == 1){
					usuario.getPersona().setEmail(email);
					setUsuarioSession(usuario);
				}
				out.print(rpt);
				out.close();
			}
		} catch (Exception e) {
			log.error(e.toString());
		}
		return NONE;
	}
	
	public String cambiarClave() {
		log.info("cambiarClave()");
		try {
			PrintWriter out = getResponse().getWriter();
			if (null != clave && null != claveTemp && Constante.PASSWORD_LONGITUD_MINIMA <= claveTemp.trim().length()) {
				int r = usuarioService.cambiarClaveUsuario(getUsuarioSession(), clave, claveTemp);
				out.print(r);
			}else{
				out.print(Constante.PASSWORD_CORTO);
			}
			out.close();
		} catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}

}
