package edu.opencampus.lms.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.EncuestaDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.Encuesta;
@Deprecated 
public class EncuestaService {

	Log log = LogFactory.getLog(getClass());
	

	 private EncuestaDAO encuestaDAO;
	
	 public void setEncuestaDAO(EncuestaDAO encuestaDAO) {
	 this.encuestaDAO = encuestaDAO;
	 }
	 
	 public boolean verEncuesta(int idMatricula) throws ServiceException {
		 try{
			 if(encuestaDAO.esParticipante(idMatricula)){
				 log.info("El usuario participa de la encuesta. - id="+idMatricula);
				if(encuestaDAO.debeEncuesta(idMatricula)){
					log.info("El usuario debe encuesta.");
					return true;
				}
				log.info("El usuario ya participů de la encuesta. - id="+idMatricula);
			 }else{
				 log.info("El usuario No participa de la encuesta. - id="+idMatricula);
			 }
		 } catch (DAOException e) {
				log.error(e.getMessage() +" - id="+idMatricula);
				throw new ServiceException(e.getMessage());
		 }
		 return false;
	 }
	 
	 public void insertarEncuesta(Encuesta encuesta) throws ServiceException {
		try{
			encuestaDAO.insertarEncuesta(encuesta);
	 	} catch (DAOException e) {
			log.error(e.getMessage());
			throw new ServiceException(e.getMessage());
		}
	}
		
	
	
}
