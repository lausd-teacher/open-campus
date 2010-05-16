package edu.opencampus.lms.action.matricula;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Matricula;
import edu.opencampus.lms.service.FichaService;
import edu.opencampus.lms.service.MatriculaService;
import edu.opencampus.lms.util.Constante;
@Deprecated
public class MatriculaAction extends BaseAction {

	private static final long serialVersionUID = 8619768212478061973L;

	private MatriculaService matriculaService;

	private FichaService fichaService;

	private String form_usuario;

	private String form_rol;

	private String form_seccion;

	private String form_formacion;

	private String id;

	private String estado;

	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getForm_usuario() {
		return form_usuario;
	}

	public void setForm_usuario(String form_usuario) {
		this.form_usuario = form_usuario;
	}

	public String getForm_formacion() {
		return form_formacion;
	}

	public void setForm_formacion(String form_formacion) {
		this.form_formacion = form_formacion;
	}

	public String getForm_rol() {
		return form_rol;
	}

	public void setForm_rol(String form_rol) {
		this.form_rol = form_rol;
	}

	public String getForm_seccion() {
		return form_seccion;
	}

	public void setForm_seccion(String form_seccion) {
		this.form_seccion = form_seccion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MatriculaService getMatriculaService() {
		return matriculaService;
	}

	public void setMatriculaService(MatriculaService matriculaService) {
		this.matriculaService = matriculaService;
	}

	public FichaService getFichaService() {
		return fichaService;
	}

	public void setFichaService(FichaService fichaService) {
		this.fichaService = fichaService;
	}

	public String buscar() { 
		AulaVirtual aula = getAulaVirtualSession();
		Collection<Matricula> col = new ArrayList<Matricula>();
		try {
			getResponse().setContentType("text/html; charset=UTF-8");
			PrintWriter pw = getResponse().getWriter();
			if (null != nombre && 0 < nombre.trim().length()) {
				col = getMatriculaService().obtenerUsuarioNoMatricula(
						nombre.trim(), aula.getIdFicha());
				if (col.isEmpty()) {
					pw.print("0&vacio");
				} else {
					if (col.size() >= Constante.BUSQUEDA_CANTIDAD_DIRECTORIO - 1) {
						pw.print("1&");
					} else {
						pw.print("0&");
					}
					int n = 0;
					for (Matricula usuario : col) {
						pw.print("<div id=\"" + usuario.getIdUsuario().trim()
								+ "\" onClick=\"clickLista(this);\" "
								+ "onMouseOver=\"mouseDentro(this,'" + (n++)
								+ "');\">" + usuario.getNombreCompletoJsp()
								+ " (" + usuario.getIdUsuario().trim() + ")"
								+ "</div>");
					}
				}
				if (col.size() >= Constante.BUSQUEDA_CANTIDAD_DIRECTORIO - 1)
					pw
							.print("<div class=\"ultimo_lista\">Existe muchas coincidencias, sea más específico.</div>");
				pw.flush();
			}
		} catch (ServiceException e) {
		} catch (IOException e) {
		}
		return NONE;
	}

	public String matricular() {
		AulaVirtual aula = getAulaVirtualSession();
		try {
			if (null != aula && null != form_usuario
					&& 0 < form_usuario.length() && null != form_rol
					&& 0 < form_rol.length()) {
				if (Constante.ROL_CAMPUS_AULAVIRTUAL_ESTUDIANTE != Integer
						.parseInt(form_rol)) {
					form_seccion = Constante.FICHA_SECCION_NULA;
				}
				if (Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE == Integer
						.parseInt(form_rol)) {
					form_formacion = "";
				}
				getMatriculaService().matricular(form_formacion, form_rol,
						form_seccion, aula.getIdFicha(), form_usuario,
						getUsuarioSession());
			}
		} catch (ServiceException e) {
		}
		return SUCCESS;
	}

	public String eliminar() {
		AulaVirtual aula = getAulaVirtualSession();
		try {
			getResponse().setContentType("text/html; charset=UTF-8");
			PrintWriter pw = getResponse().getWriter();
			if (null != aula && null != id && 0 < id.length()) {
				getMatriculaService().eliminar(id, aula.getIdFicha(),
						getUsuarioSession());
				pw.print("1");
			} else {
				pw.print("0");
			}
			pw.flush();
		} catch (ServiceException e) {
		} catch (IOException e) {
		}
		return NONE;
	}

	public String modificar() {
		AulaVirtual aula = getAulaVirtualSession();
		try {
			getResponse().setContentType("text/html; charset=UTF-8");
			PrintWriter pw = getResponse().getWriter();
			if (null != aula && null != id && 0 < id.length() && null != estado
					&& 0 < estado.length()) {
				getMatriculaService().modificar(id, estado, aula.getIdFicha(),
						getUsuarioSession());
				pw.print("1");
			} else {
				pw.print("0");
			}
			pw.flush();
		} catch (ServiceException e) {
		} catch (IOException e) {
		}
		return NONE;
	}

	public String principal() {
		AulaVirtual aula = getAulaVirtualSession();
		try {
			getResponse().setContentType("text/html; charset=UTF-8");
			PrintWriter pw = getResponse().getWriter();
			if (null != aula && null != id && 0 < id.length()) {
				getMatriculaService()
						.principal(id, estado, getUsuarioSession());
				pw.print("1");
			} else {
				pw.print("0");
			}
			pw.flush();
		} catch (ServiceException e) {
		} catch (IOException e) {
		}
		return NONE;
	}

}
