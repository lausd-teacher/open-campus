package edu.tecsup.lms.action.aulavirtual;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.GregorianCalendar;

import edu.tecsup.lms.action.BaseAction;
import edu.tecsup.lms.excepcion.ActionException;
import edu.tecsup.lms.modelo.AulaVirtual;
import edu.tecsup.lms.modelo.Matricula;
import edu.tecsup.lms.modelo.Periodo;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.aulavirtual.MatriculaRol;
import edu.tecsup.lms.service.AulaVirtualService;
import edu.tecsup.lms.service.FichaService;
import edu.tecsup.lms.service.PeriodoService;
import edu.tecsup.lms.util.Constante;
import edu.tecsup.lms.util.Formato;

public class GestionarAulaVirtualAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private AulaVirtual aula;
	
	private Integer idperiodo;
	
	private String nombre;
	
	private Integer idusuario;
	
	private Integer idmatricula;
	
	private Integer flag;
	
	private String cmd;
	
	private Integer idrol;
	
	private String finicio;
	
	private Integer dedicion;
	
	private Integer drevision;
	
	private String ffin;
	
	private Collection<Periodo> periodos;
	
	private Collection<MatriculaRol> grupos;
	
	private AulaVirtualService aulaVirtualService;
	
	private FichaService fichaService;
	
	private PeriodoService periodoService; 

	public Integer getIdmatricula() {
		return idmatricula;
	}

	public void setIdmatricula(Integer idmatricula) {
		this.idmatricula = idmatricula;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Collection<MatriculaRol> getGrupos() {
		return grupos;
	}

	public void setFichaService(FichaService fichaService) {
		this.fichaService = fichaService;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = Formato.matizarFrace(nombre.replaceAll("%", "")).trim();
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public Integer getIdrol() {
		return idrol;
	}

	public void setIdrol(Integer idrol) {
		this.idrol = idrol;
	}

	public Integer getIdperiodo() {
		return idperiodo;
	}

	public void setIdperiodo(Integer idperiodo) {
		this.idperiodo = idperiodo;
	}

	public String getFinicio() {
		return finicio;
	}

	public void setFinicio(String finicio) {
		this.finicio = finicio;
	}

	public Integer getDedicion() {
		return dedicion;
	}

	public void setDedicion(Integer dedicion) {
		this.dedicion = dedicion;
	}

	public Integer getDrevision() {
		return drevision;
	}

	public void setDrevision(Integer drevision) {
		this.drevision = drevision;
	}

	public String getFfin() {
		return ffin;
	}

	public void setFfin(String ffin) {
		this.ffin = ffin;
	}

	public AulaVirtual getAula() {
		return aula;
	}

	public Collection<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodoService(PeriodoService periodoService) {
		this.periodoService = periodoService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setAulaVirtualService(AulaVirtualService aulaVirtualService) {
		this.aulaVirtualService = aulaVirtualService;
	}

	public String ingresar() throws ActionException{
		log.info("ingresar(): "+id);
		try {
			if (null != id) {
				aula = aulaVirtualService.obtenerAulaVirtualAdministrar(id, getUsuarioSession());
				if (null == aula) 
					throw new ActionException("No tiene permiso para acceder al aula de id "+id);
//				if (aula.getFechaRevision().before(hoy)) {
//					return HISTORICO;
//				}
			}else{
				throw new ActionException("No se ha especificado el parámetro necesario.");
			}
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String configurar() throws ActionException{
		log.info("configurar()");
		try {
			if(idperiodo != null){
				
				AulaVirtual aula = getUsuarioSession().getAulaActual();
				if(idperiodo != 0){
					//Cambiar a un periodo normal
					Periodo newPeriodo = periodoService.obtener(idperiodo);
					
					if(newPeriodo != null){
						fichaService.modificar(aula.getIdFicha(), idperiodo);
						
						if(aula.getPeriodo().getPersonalizado() != Constante.ESTADO_ACTIVO){
							//Ficha actual provienen de un periodo normal 
						}else{
							//Ficha actual provienen de un periodo personalizado
							periodoService.eliminar(aula.getPeriodo().getIdPeriodo());
						}
						
						getUsuarioSession().getAulaActual().setPeriodo(newPeriodo);
					}else{
						throw new ActionException("El código de periodo ingresado no existe: "+idperiodo);
					}
				}else{
					//Cambiar a un periodo personalizado
					GregorianCalendar fInicio = Formato.stringToCalendar(finicio, Constante.FECHA_DIA_MES_ANO);
					GregorianCalendar fFin = Formato.stringToCalendar(ffin, Constante.FECHA_DIA_MES_ANO);
					if(fFin != null) fFin.add(GregorianCalendar.SECOND, 86399); //hasta las 23:59:59
					
					if(fInicio != null && fFin != null && dedicion != null && drevision != null){
						
						Periodo newPeriodo = new Periodo();
						newPeriodo.setFechaInicio(fInicio);
						newPeriodo.setFechaFin(fFin);
						newPeriodo.setDiasEdicion(dedicion);
						newPeriodo.setDiasRevision(drevision);
						newPeriodo.setNombre("CUSTOM_"+newPeriodo.getFechaInicioToString());
						newPeriodo.setPersonalizado(Constante.ESTADO_ACTIVO);
						
						if(aula.getPeriodo().getPersonalizado() != Constante.ESTADO_ACTIVO){
							//Ficha actual provienen de un periodo normal 
							newPeriodo.setIdPeriodo(periodoService.crear(newPeriodo));
							fichaService.modificar(aula.getIdFicha(), newPeriodo.getIdPeriodo());
						}else{
							//Ficha actual provienen de un periodo personalizado
							newPeriodo.setIdPeriodo(aula.getPeriodo().getIdPeriodo());
							periodoService.modificar(newPeriodo);
						}
						
						getUsuarioSession().getAulaActual().setPeriodo(newPeriodo);
					}else{
						throw new ActionException("Se esperaba algunos parámetros requeridos para el nuevo periodo personalizado. "+fInicio +"-"+ fFin +"-"+ dedicion +"-"+ drevision);
					}
				}
				
			}
			
			periodos = periodoService.listarVigentes(false);
			log.info(getUsuarioSession().getAulaActual());
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String miClase() throws ActionException{
		log.info("miClase()");
		try {
			grupos = aulaVirtualService.obtenerMatriculasAdministrar(getUsuarioSession().getAulaActual().getIdFicha());
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String buscarNoMatriculadosAutocompletar() throws ActionException{
		log.info("buscarNoMatriculadosAutocompletar() "+nombre);
		try {
			if (nombre != null && nombre.trim().length() > 2) {
				
				Collection<Usuario> usuarios = aulaVirtualService.buscarNoMatriculados(nombre.split(" "), getUsuarioSession().getAulaActual().getIdFicha());
				
				getResponse().setHeader("Cache-Control", "no-cache");
				getResponse().setContentType("text/html; charset=ISO-8859-1");
				PrintWriter out = getResponse().getWriter();
				for (Usuario usuario : usuarios) {
					out.print("<li onselect=\"this.text.value = '"+usuario.getPersona().getNombreCompleto()+"'; $('idusuario').value = '"+usuario.getId()+"';\">");
					out.print("<span>"+usuario.getId()+"</span> "+usuario.getPersona().getNombreCompleto().replaceAll(nombre, "<b>"+nombre+"</b>"));
					out.print("</li>");
				}
				
				out.close();
			}
		} catch (Exception e) {
			log.error(e);
			//throw new ActionException(e);
		}
		return NONE;
	}
	
	public String matricular() throws ActionException{
		log.info("matricular() " + idusuario + " - " + idrol);
		try {
			if (null != idusuario && null != idrol) {
				Matricula matricula = new Matricula();
				matricula.setRol(new MatriculaRol(idrol));
				matricula.setUsuario(new Usuario(idusuario));
				matricula.setUsuarioCreacion(String.valueOf(getUsuarioSession().getId()));
				matricula.setPrincipal(Constante.ESTADO_INACTIVO);
				aulaVirtualService.matricularUsuario(getUsuarioSession().getAulaActual().getIdFicha(), matricula);
			}else{
				throw new ActionException("Se esperaba algunos parámetros requeridos: "+idusuario +"-"+ idrol );
			}
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String cambiarEstadoMatricula() throws ActionException{
		log.info("cambiarEstadoMatricula() " + idmatricula + " - " + flag);
		try {
			if (null != idmatricula && 0 != idmatricula && null != cmd) {
				
				Matricula matricula = new Matricula();
				matricula.setIdMatricula(idmatricula);
				matricula.setUsuarioModificacion(String.valueOf(getUsuarioSession().getId()));
				
				if("Activar".equals(cmd))
					matricula.setEstado(Constante.ESTADO_ACTIVO);
				else
					matricula.setEstado(Constante.ESTADO_INACTIVO);
				
				aulaVirtualService.cambiarEstadoMatricula(matricula);
				
				PrintWriter out = getResponse().getWriter();
				out.print("OK");
				out.close();
			}else{
				throw new ActionException("Se esperaba algunos parámetros requeridos: "+idmatricula +"-"+ cmd );
			}
		} catch (Exception e) {
			log.error(e);
			//throw new ActionException(e);
		}
		return NONE;
	}
	
	public String eliminarMatricula() throws ActionException{
		log.info("eliminarMatricula() " + idmatricula);
		try {
			if (null != idmatricula && 0 != idmatricula) {
				
				Matricula matricula = new Matricula();
				matricula.setIdMatricula(idmatricula);
				matricula.setUsuarioModificacion(String.valueOf(getUsuarioSession().getId()));
				
				aulaVirtualService.eliminarMatricula(getUsuarioSession().getAulaActual().getIdFicha(), matricula);
			}else{
				throw new ActionException("Se esperaba algunos parámetros requeridos: "+idmatricula );
			}
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String cambiarPrincipalMatricula() throws ActionException{
		log.info("cambiarPrincipalMatricula() " + idmatricula);
		try {
			if (null != idmatricula && 0 != idmatricula) {
				
				Matricula matricula = new Matricula();
				matricula.setIdMatricula(idmatricula);
				matricula.setUsuarioModificacion(String.valueOf(getUsuarioSession().getId()));
				
				aulaVirtualService.principal(getUsuarioSession().getAulaActual().getIdFicha(), matricula);
				
				PrintWriter out = getResponse().getWriter();
				out.print("OK");
				out.close();
			}else{
				throw new ActionException("Se esperaba algunos parámetros requeridos: "+idmatricula );
			}
		} catch (Exception e) {
			log.error(e);
			//throw new ActionException(e);
		}
		return NONE;
	}
	
}
