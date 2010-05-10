package edu.opencampus.lms.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.AulaVirtualDAO;
import edu.opencampus.lms.dao.FichaDAO;
import edu.opencampus.lms.dao.JerarquiaDAO;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.aulavirtual.OraganizationComparator;

public class FichaService {

	Log log = LogFactory.getLog(getClass());

	private FichaDAO fichaDAO;
	
	private JerarquiaDAO jerarquiaDAO; 
	
	private AulaVirtualDAO aulaVirtualDAO;

	public void setAulaVirtualDAO(AulaVirtualDAO aulaVirtualDAO) {
		this.aulaVirtualDAO = aulaVirtualDAO;
	}

	public void setJerarquiaDAO(JerarquiaDAO jerarquiaDAO) {
		this.jerarquiaDAO = jerarquiaDAO;
	}

	public void setFichaDAO(FichaDAO fichaDAO) {
		this.fichaDAO = fichaDAO;
	}
	
	public Integer crear(AulaVirtual ficha) throws ServiceException {
		try {
			return fichaDAO.crear(ficha);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}

	}
	
	public void modificar(Integer idficha, Integer idperiodo) throws ServiceException {
		try {
			fichaDAO.modificar(idficha, idperiodo);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}

	}
	
	public Collection<AulaVirtual> cargarPortada(Integer idusuario) throws ServiceException {
		try {
			return  fichaDAO.cargarPortada(idusuario);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	
	public List<AulaVirtual> verCursos(Integer idusuario) throws ServiceException {
		try {
			List<AulaVirtual> aulas = fichaDAO.verCursos(idusuario);
			for (AulaVirtual aulaVirtual : aulas) {
				aulaVirtual.getCurso().setJerarquia(jerarquiaDAO.obtenerPadre(aulaVirtual.getCurso().getJerarquia().getIdJerarquia()));
				aulaVirtual.setDocentes(aulaVirtualDAO.listarDocentes(aulaVirtual.getIdFicha()));//Esto puedes hacerlo en el mismo dao y asi retiras aulaVirtualDAO del spring fichaService
				System.out.println(aulaVirtual);
			}
			Collections.sort(aulas, new OraganizationComparator());
			
			for (AulaVirtual aulaVirtual2 : aulas) {
				
				System.out.println(aulaVirtual2);
			}

			return aulas;
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
		
	}
	
	public List<AulaVirtual> buscarFichas(AulaVirtual aulaFiltro) throws ServiceException {
		try {
			return fichaDAO.buscarFichas(aulaFiltro);
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}
	

/*
	

	public FichaCollection obtenerFichas(FichaFiltro filtro, Usuario usuario)
			throws ServiceException {
		FichaCollection col = new FichaCollection();
		try {
			col = fichaDAO.obtenerFichas(filtro, usuario);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return col;
	}

	public FichaCollection obtenerFichasUds(FichaFiltro filtro, String etiqueta)
			throws ServiceException {
		FichaCollection col = new FichaCollection();
		try {
			col = fichaDAO.obtenerFichasUDS(filtro, etiqueta);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return col;
	}

	public int crearExterno(Usuario usuario, String tipo, String[] valores)
			throws ServiceException {
		try {
			return fichaDAO.crearExternos(usuario, tipo, valores);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}

	}

	public int papelera(Usuario usuario, String[] valores)
			throws ServiceException {
		try {
			return fichaDAO.setPapelera(usuario, valores);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public int[] sincronizar(Usuario usuario, String[] valores)
			throws ServiceException {
		try {
			return fichaDAO.setSincronizar(usuario, valores);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public int[] constancia(Usuario usuario, String[] valores)
			throws ServiceException {
		try {
			return fichaDAO.setConstancia(usuario, valores);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	public int resturar(Usuario usuario, String[] valores)
			throws ServiceException {
		try {
			return fichaDAO.setRestaurar(usuario, valores);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
	}

	

	public Collection<FichaHistorica> obtenerFichasPorUsuarioHistorica(
			Usuario usuario) throws ServiceException {
		Collection<FichaHistorica> col;
		try {
			col = fichaDAO.obtenerFichasPorUsuarioHistorica(usuario);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return col;
	}

	public Collection<String> obtenerSeccionesFicha(int idFicha)
			throws ServiceException {
		Collection<String> secciones = new ArrayList<String>();
		try {
			secciones = fichaDAO.obtenerSeccionPorFicha(idFicha);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return secciones;
	}

	public Collection<Especialidad> obtenerFormacionFicha(int idFicha)
			throws ServiceException {
		Collection<Especialidad> formaciones = new ArrayList<Especialidad>();
		try {
			formaciones = fichaDAO.obtenerFormacionPorFicha(idFicha);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return formaciones;
	}

	public Collection<Curso> obtenerFichaCurso(String nombre)
			throws ServiceException {
		Collection<Curso> cursos;
		try {
			cursos = fichaDAO.obtenerCursoDeFicha(nombre);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}
		return cursos;
	}

	public void setImportanteFicha(Usuario usuario, int idFicha, int importante)
			throws ServiceException {
		try {
			fichaDAO.setImportanteFicha(usuario, idFicha, importante);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}

	}

	public void setEstadoFicha(Usuario usuario, int idFicha, int estado)
			throws ServiceException {
		try {
			fichaDAO.setEstadoFicha(usuario, idFicha, estado);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e);
		}

	}
*/
}
