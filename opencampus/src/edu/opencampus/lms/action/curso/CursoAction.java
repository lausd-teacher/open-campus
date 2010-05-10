package edu.opencampus.lms.action.curso;

import java.io.PrintWriter;
import java.util.Collection;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ActionException;
import edu.opencampus.lms.modelo.Curso;
import edu.opencampus.lms.modelo.Jerarquia;
import edu.opencampus.lms.service.CursoService;
import edu.opencampus.lms.service.JerarquiaService;

public class CursoAction extends BaseAction {

	private static final long serialVersionUID = -1710487907555149544L;

	private CursoService cursoService;

	private JerarquiaService jerarquiaService;

	private Integer idJerarquia;

	private String nombre;

	private Integer idCurso;
	
	private Collection<Jerarquia> jerarquias;
	
	private Collection<Curso> cursos;

	public Collection<Curso> getCursos() {
		return cursos;
	}

	public Collection<Jerarquia> getJerarquias() {
		return jerarquias;
	}

	public Integer getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public Integer getIdJerarquia() {
		return idJerarquia;
	}

	public void setIdJerarquia(Integer idJerarquia) {
		this.idJerarquia = idJerarquia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.replaceAll("%", "");;
	}

	public CursoService getCursoService() {
		return cursoService;
	}

	public JerarquiaService getJerarquiaService() {
		return jerarquiaService;
	}

	public void setJerarquiaService(JerarquiaService jerarquiaService) {
		this.jerarquiaService = jerarquiaService;
	}

	public void setCursoService(CursoService cursoService) {
		this.cursoService = cursoService;
	}

	public String cargar() throws ActionException{
		log.info("cargar()");
		try {
			jerarquias = jerarquiaService.listarJerarquias();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new ActionException(e);
		}
		return SUCCESS;
	}

	public String buscar() {
		log.info("buscar()");
		try {
			if (idJerarquia != null && nombre != null && nombre.trim().length() > 2) {
				cursos = cursoService.buscar(nombre, idJerarquia);
			}else{
				addActionError("Ha ingresado un nombre de unidad no válida.");
				log.error("Faltan parametros del formulario.");
			}
		} catch (Exception e) {
			log.error(e);
			//throw new ActionException(e);
		}
		return SUCCESS;
	}
	
	public String buscarAutocompletar() {
		log.info("buscarAutocompletar() " + nombre);
		try {
			if (nombre != null && nombre.trim().length() > 2) {
				Collection<Curso> cursos = cursoService.buscarSinJerarquia(nombre);
				getResponse().setHeader("Cache-Control", "no-cache");
				getResponse().setContentType("text/html; charset=ISO-8859-1");
				PrintWriter out = getResponse().getWriter();
				for (Curso curso : cursos) {
					out.print("<li onselect=\"this.text.value = '"+curso.getNombre()+"'; $('idCurso').value = '"+curso.getIdCurso()+"'; loadSylabus('"+curso.getIdCurso()+"');\">"); 
					out.print("<span>"+curso.getJerarquia().getNombre()+"</span> "+curso.getNombre().replaceAll(nombre, "<b>"+nombre+"</b>")+" ("+curso.getIdCurso()+")");
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

	public String buscarPorID() {
		log.info("buscarPorID()");
		try {
			if (idCurso != null) cursos = cursoService.buscarPorID(idCurso);
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	public String crear() throws ActionException{
		log.info("crear() "+nombre);
		try {
			if (idJerarquia != null && nombre != null && nombre.trim().length() > 2) {
				Curso curso = new Curso();
				curso.setNombre(nombre);
				curso.setJerarquia(new Jerarquia(idJerarquia));
				cursoService.crear(curso);
				PrintWriter out = getResponse().getWriter();
				out.println("OK");
				out.close();
			}else{
				log.error("Faltan parametros del formulario.");
			}
		} catch (Exception e) {
			log.error(e);
			//throw new ActionException(e);
		}
		return NONE;
	}
	
	public String eliminar() throws ActionException{
		log.info("eliminar() "+idCurso);
		try {
			if (idCurso != null) {
				cursoService.eliminar(idCurso);
				PrintWriter out = getResponse().getWriter();
				out.println("OK");
				out.close();
			}
		} catch (Exception e) {
			log.error(e);
			//throw new ActionException(e);
		}
		return NONE;
	}
	
}