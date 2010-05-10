package edu.opencampus.lms.action.silabo;

import java.io.PrintWriter;
import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import edu.opencampus.lms.action.BaseAction;
import edu.opencampus.lms.excepcion.ActionException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.Silabo;
import edu.opencampus.lms.modelo.ficha.Unidad;
import edu.opencampus.lms.service.SilaboService;

public class SilaboAction extends BaseAction {

	private static final long serialVersionUID = -1710487907555149544L;

	private Integer idCurso;
	
	private Integer idUnidad;
	
	private Integer idSilabo;
	
	private String nombre;
	
	private Silabo silabo;
	
	private String cmd;
	
	private Collection<Silabo> silabos; 
	
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public Integer getIdUnidad() {
		return idUnidad;
	}
	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}
	public Silabo getSilabo() {
		return silabo;
	}
	public Integer getIdSilabo() {
		return idSilabo;
	}
	public void setIdSilabo(Integer idSilabo) {
		this.idSilabo = idSilabo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre.replaceAll("%", "");;
	}

	public Integer getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
	}

	public Collection<Silabo> getSilabos() {
		return silabos;
	}

	private SilaboService silaboService;

	public void setSilaboService(SilaboService silaboService) {
		this.silaboService = silaboService;
	}

	public String verSilaboXML() throws ActionException{
		log.info("verSilaboXML()");
		try {
			if(idCurso != null){
				Silabo silabo = null;
				Collection<Silabo> silabos = silaboService.buscarPorIDCurso(idCurso);
				for (Silabo s : silabos) {
					silabo = silaboService.obtener(s.getIdSilabo());
					log.info("Silabo para el presente curso: "+s.getDescripcion());
					break;
				}
							
				getResponse().setContentType("text/xml");
				getResponse().setHeader("Cache-Control", "no-cache");
				PrintWriter out = getResponse().getWriter();
				
				Document doc= new DocumentImpl();
				
				if(silabo != null){
					Element raiz = doc.createElement("silabo");
					
					Element idsilabo = doc.createElement("idsilabo");
					idsilabo.appendChild(doc.createTextNode(String.valueOf(silabo.getIdSilabo()))) ;
					
					raiz.appendChild(idsilabo);
					
					Element descripcion = doc.createElement("descripcion");
					descripcion.appendChild(doc.createTextNode(silabo.getDescripcion())) ;
					
					raiz.appendChild(descripcion);
					
					for (Unidad u : silabo.getUnidades()) {
						Element unidad = doc.createElement("unidad");
						
						Element codigo = doc.createElement("codigo");
						codigo.appendChild(doc.createTextNode(String.valueOf(u.getIdUnidad())));
						unidad.appendChild(codigo);
						
						Element nombre = doc.createElement("nombre");
						nombre.appendChild(doc.createTextNode(u.getNombreCompleto()));
						unidad.appendChild(nombre);
						
						Element jerarquia = doc.createElement("jerarquia");
						jerarquia.appendChild(doc.createTextNode(u.getJerarquia().getNombre()));
						unidad.appendChild(jerarquia);
						
						Element test = doc.createElement("test");
						test.appendChild(doc.createTextNode(String.valueOf(u.getCantidadTest())));
						unidad.appendChild(test);
						
						raiz.appendChild(unidad);
					}
					
					doc.appendChild(raiz);
				}
				
				OutputFormat format = new OutputFormat(doc,"ISO-8859-1",true);   
				new XMLSerializer(out,format).asDOMSerializer().serialize(doc); 
				//new XMLSerializer(System.out,format).asDOMSerializer().serialize(doc); 
				out.close();	
			}
		} catch (Exception e) {
			log.error(e);
			//throw new ActionException(e);
		}
		return NONE;
	}
	
	public String getUnidades() {
		log.info("getUnidades()"+ nombre);
		try {
			getResponse().setContentType("text/html; charset=ISO-8859-1");
			PrintWriter pw = getResponse().getWriter();

			if (nombre != null && nombre.trim().length() > 0) {
				Collection<Unidad> array = silaboService.buscarNuevasUnidades(
						nombre, idSilabo);
				if (array.isEmpty())
					pw.print("0&vacio");
				else {
					if (array.size() > 15)
						pw.print("1&");
					else
						pw.print("0&");
					int n = 0;
					for (Unidad unidad : array) {
						pw.print("<div id=\"" + unidad.getIdUnidad()
								+ "\" onClick=\"clickLista(this);\" "
								+ "onMouseOver=\"mouseDentro(this,'" + (n++)
								+ "');\">" + unidad.getNombreCompleto()
								+ "</div>");
					}
				}
				if (array.size() > 15)
					pw
							.print("<div><b><i>Existe muchas coincidencias, sea más específico.</i></b></div>");
				pw.flush();
			}
			pw.close();

		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}

		return NONE;
	}

	public String verUnidades() throws ActionException{
		log.info("verUnidades()");
		try {
			if (idSilabo != null) silabo = silaboService.obtener(idSilabo);
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}

	public String modificarIndiceUnidad()  throws ActionException{
		log.info("modificarIndiceUnidad()");
		try {
			if (idSilabo != null && idUnidad != null && cmd != null) {
				silaboService.modificarIndiceUnidad(("up".equals(cmd)), idSilabo, idUnidad);
				silabo = silaboService.obtener(idSilabo);
			}
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}

	public String agregarUnidad() throws ActionException{
		log.info("agregarUnidad()");
		try {
			if (idSilabo != null && idUnidad != null) {
				silaboService.agregarUnidad(idSilabo, idUnidad);
				silabo = silaboService.obtener(idSilabo);
			}
		} catch (Exception e) {
			log.error(e);
			throw new ActionException(e);
		}
		return SUCCESS;
	}

	public String eliminarUnidad() {
		try {
			if (idSilabo != null && idUnidad != null) {
				silaboService.eliminarUnidad(idSilabo,idUnidad);
				silabo = silaboService.obtener(idSilabo);
			}
		} catch (ServiceException e) {
			log.error(e);
		} catch (NumberFormatException e) {
			log.error(e);
		}
		return SUCCESS;
	}

	public String buscarPorIDCurso() throws ActionException{
		log.info("buscarPorIDCurso()");
		try {
			if (idCurso != null) silabos = silaboService.buscarPorIDCurso(idCurso);
		} catch (Exception e) {
			log.error(e);
			//throw new ActionException(e);
		}
		return SUCCESS;
	}

	public String crear() throws ActionException{
		log.info("crear()");
		try {
			if (idCurso != null && nombre != null) {
				Silabo silabo = new Silabo();
				silabo.setIdCurso(idCurso);
				silabo.setDescripcion(nombre);
				silabo.setUsuarioCreacion(String.valueOf(getUsuarioSession().getId()));
				silabo.setUsuarioModificacion(String.valueOf(getUsuarioSession().getId()));

				silaboService.crear(silabo);
				silabos = silaboService.buscarPorIDCurso(idCurso);
			}
		} catch (Exception e) {
			log.error(e);
		}

		return SUCCESS;
	}

	public String modificar() throws ActionException{
		log.info("modificar()");
		try {
			if (idSilabo != null && nombre != null
					&& nombre.trim().length() > 0) {

				Silabo s = new Silabo();
				s.setIdSilabo(idSilabo);
				s.setDescripcion(nombre);
				s.setUsuarioModificacion(String.valueOf(getUsuarioSession().getId()));

				silaboService.modificar(s);
				
				PrintWriter out = getResponse().getWriter();
				out.write("1");
				out.close();
			}
		} catch (Exception e) {
			log.error(e);
		}
		return NONE;
	}

	public String eliminar() throws ActionException{
		log.info("eliminar()");
		try {
			if (idSilabo != null) {
				silaboService.eliminar(idSilabo);
				silabos = silaboService.buscarPorIDCurso(idCurso);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return SUCCESS;
	}

}
