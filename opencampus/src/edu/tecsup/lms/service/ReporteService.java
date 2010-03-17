package edu.tecsup.lms.service;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.ReporteDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.aulavirtual.reporte.ReporteDetalle;
import edu.tecsup.lms.modelo.reporte.ReporteCollection;
import edu.tecsup.lms.modelo.reporte.ReporteFiltro;
import edu.tecsup.lms.modelo.reporte.TecsupVirtualReporte;
import edu.tecsup.lms.modelo.reportesecdoc.ReporteSecDocFiltro;
import edu.tecsup.lms.modelo.reportesecdoc.UsuarioReporteSecDoc;

public class ReporteService {

	private Log log = LogFactory.getLog(getClass());

	private ReporteDAO reporteDAO;

	public ReporteDAO getReporteDAO() {
		return reporteDAO;
	}

	public void setReporteDAO(ReporteDAO reporteDAO) {
		this.reporteDAO = reporteDAO;
	}

	public ReporteCollection buscar(ReporteFiltro filtro)
			throws ServiceException {
		ReporteCollection usuarios = null;
		try {
			usuarios = reporteDAO.buscar(filtro);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return usuarios;

	}
	
	public String obtenerRazSocial(String codigo)
			throws ServiceException {
		String nombre = null;
		try {
			nombre = reporteDAO.obtenerRazSocial(codigo);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return nombre;

	}

	public boolean esMiEmpleado(String codigo, int idMatricula) throws ServiceException {
		try {
			return reporteDAO.esMiEmpleado(codigo, idMatricula);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return false;

	}
	
	public Collection<UsuarioReporteSecDoc> busquedaSecDoc(
			ReporteSecDocFiltro filtro) throws ServiceException {
		Collection<UsuarioReporteSecDoc> usuarios = null;
		try {
			usuarios = reporteDAO.busquedaSecDoc(filtro);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return usuarios;
	}

	public Collection<UsuarioReporteSecDoc> busquedaSecDoc(
			String[] array_matriculas) throws ServiceException {
		Collection<UsuarioReporteSecDoc> usuarios = null;
		try {
			usuarios = reporteDAO.busquedaSecDoc(array_matriculas);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return usuarios;
	}

	public void busquedaSecDocExcel(String[] idMatricula, Usuario usuario,
			String imagen, String fileInput, String fileOut)
			throws ServiceException {
		try {
			reporteDAO.busquedaSecDocExcel(idMatricula, usuario, imagen,
					fileInput, fileOut);
		} catch (DAOException e) {
			log.error(e.toString());
		}
	}
	
	public Collection<TecsupVirtualReporte> obtenerAuditoriaTecsupVirtual(ReporteFiltro filtro)
			throws ServiceException {
		Collection<TecsupVirtualReporte> auditoria = null;
		try {
			auditoria = reporteDAO.obtenerAuditoriaTecsupVirtual(filtro);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return auditoria;
	}
	
	public Collection<TecsupVirtualReporte> obtenerReporteTecsupVirtual(ReporteFiltro filtro)
	throws ServiceException {
		Collection<TecsupVirtualReporte> reporte = null;
		try {
			reporte = reporteDAO.obtenerReporteTecsupVirtual(filtro);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return reporte;
	}
	
	public ReporteDetalle obtenerAuditoriaSemanal(int idMatricula, String fecha1, String fecha2)
	throws ServiceException {
		ReporteDetalle reporte = null;
		try {
			reporte = reporteDAO.obtenerAuditoriaSemanal(idMatricula, fecha1, fecha2);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return reporte;
	}

}
