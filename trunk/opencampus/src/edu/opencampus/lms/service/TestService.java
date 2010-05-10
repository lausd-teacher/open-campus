package edu.opencampus.lms.service;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.opencampus.lms.dao.TestDAO;
import edu.opencampus.lms.excepcion.DAOException;
import edu.opencampus.lms.excepcion.ServiceException;
import edu.opencampus.lms.modelo.AulaVirtual;
import edu.opencampus.lms.modelo.Test;
import edu.opencampus.lms.modelo.test.TestPregunta;

public class TestService {

	Log log = LogFactory.getLog(getClass());

	private TestDAO testDAO;

	public void setTestDAO(TestDAO testDAO) {
		this.testDAO = testDAO;
	} 

	public Collection<TestPregunta> listarTestsPorUnidad(int idUnidad)
			throws ServiceException {
		log.info("listarTestsPorUnidad(int idUnidad)");
		Collection<TestPregunta> test;
		try {
			test = testDAO.listarTestsPorUnidad(idUnidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return test;
	}

	public TestPregunta obtenerTest(int idUnidad, int idTest) throws ServiceException {
		log.info("obtenerTest(int idUnidad, int idTest)");
		TestPregunta test = null;
		try {
			test = testDAO.obtenerTest(idUnidad, idTest);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return test;
	}

	public void crearTest(TestPregunta test) throws ServiceException {
		log.info("crearTest(Test test)");
		try {
			testDAO.crearTest(test);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}

	public void modificarTest(TestPregunta test) throws ServiceException {
		log.info("modificarTest(Test test)");
		try {
			testDAO.modificarTest(test);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}

	public int getMaxIdTest(int idUnidad) throws ServiceException {
		log.info("getMaxIdTest(int idUnidad)");
		try {
			return testDAO.getMaxIdTest(idUnidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}

	public void eliminar(TestPregunta test) throws ServiceException {
		log.info("eliminar(Test test)");
		try {
			testDAO.eliminar(test);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}

	public void guardaInteraccion(Test test, int idTest, int idDetalle, int estado) throws ServiceException {
		try {
			testDAO.guardaInteraccion(test, idTest, idDetalle, estado);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}

	public void calificar(Test test, float nota) throws ServiceException {
		try {
			testDAO.calificar(test, nota);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public Test ejecutarTest(Test test) throws ServiceException {
		try {
			test = testDAO.ejecutarTest(test);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return test;
	}

	public boolean isEnabledTest(AulaVirtual aula, int idUnidad) throws ServiceException {
		boolean isEnabled = false;
		try {
			isEnabled = testDAO.isEnabledTest(aula, idUnidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return isEnabled;
	}

	public Collection<TestPregunta> listarTestsParaEjecucion(int idUnidad) throws ServiceException {
		Collection<TestPregunta> tests = null;
		try {
			tests = testDAO.listarTestsParaEjecucion(idUnidad);
		} catch (DAOException e) {
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return tests;
	}
}
