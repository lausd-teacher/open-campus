package edu.tecsup.lms.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.AvisoDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Aviso;

public class AvisoService {

	Log log = LogFactory.getLog(getClass());
	
	private AvisoDAO avisoDAO;
	
	public void setAvisoDAO(AvisoDAO avisoDAO) {
		this.avisoDAO = avisoDAO;
	}

	public Aviso cargarAviso() throws ServiceException{
		Aviso aviso = null;
		try{
			aviso = avisoDAO.cargarAviso();
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return aviso;
	}

}
