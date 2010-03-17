package edu.tecsup.lms.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.dao.ForoDAO;
import edu.tecsup.lms.dao.ForoUsuarioDAO;
import edu.tecsup.lms.excepcion.DAOException;
import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.Foro;
import edu.tecsup.lms.modelo.ReglaDeServicio;
import edu.tecsup.lms.modelo.Usuario;
import edu.tecsup.lms.modelo.foro.Mensaje;
import edu.tecsup.lms.modelo.foro.OpcionesBusqueda;
import edu.tecsup.lms.modelo.foro.Tema;

public class ForoService {

	Log log = LogFactory.getLog(getClass());

	private ForoDAO foroDAO;

	public void setForoDAO(ForoDAO foroDAO) {
		this.foroDAO = foroDAO;
	}
	
	/*private BuzonDAO buzonDAO;*/
	
	private ForoUsuarioDAO foroUsuarioDAO;

	public void setForoUsuarioDAO(ForoUsuarioDAO foroUsuarioDAO) {
		this.foroUsuarioDAO = foroUsuarioDAO;
	}
/*
 * Pasar las funciones de forousuariodao a forodao
 * 
	public Collection<Foro> cargarPortadaAdmin(String idUsuario, Set<Integer> misForos) throws ServiceException{
		Collection<Foro> foros;
		try{
			foros = foroDAO.cargarPortadaAdmin(idUsuario, misForos);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return foros;
	}
	
	public Collection<Foro> cargarPortada(String idUsuario, Set<Integer> misForos) throws ServiceException{
		Collection<Foro> foros;
		try{
			foros = foroDAO.cargarPortada(idUsuario,misForos);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return foros;
	}
	
	public Collection<Foro> listarForos() throws ServiceException{
		Collection<Foro> foros;
		try{
			foros = foroDAO.listarForos();
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return foros;
	}
	
	public Collection<Usuario> listarPotencialesMod(Collection<ReglaDeServicio> reglas) throws ServiceException{
		Collection<Usuario> usuarios;
		try{
			usuarios = foroDAO.listarPotencialesMod(reglas);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return usuarios;
	}
	
	public Foro obtenerForoPaModificar(int idForo) throws ServiceException{
		Foro foro;
		try{
			foro = foroDAO.obtenerForoPaModificar(idForo);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return foro;
	}
	
	public  Collection<Tema> buscarPorTema(OpcionesBusqueda opciones) throws ServiceException{
		Collection<Tema> temas;
		try{
			temas = foroDAO.buscarPorTema(opciones);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return temas;
	}
	
	public Collection<Usuario> buscarUsuarios(String usuario) throws ServiceException{
		Collection<Usuario> usuarios;
		try{
			usuarios = foroDAO.buscarUsuarios(usuario);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return usuarios;
	}
	
	public Collection<Foro> listarTitulosDeForos(Collection<Integer> forosIDs) throws ServiceException{
		Collection<Foro> foros;
		try{
			if(forosIDs.isEmpty()){
				return new ArrayList<Foro>();
			}
			foros = foroDAO.listarTitulosDeForos(forosIDs);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
		return foros;
	}
	
	public void crearForo(Foro foro) throws ServiceException{
		try{
			foroDAO.crearForo(foro);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void modificarForo(Foro foro) throws ServiceException{
		try{
			foroDAO.modificarForo(foro);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void cambiarEstado(int idForo,int estado) throws ServiceException{
		try{
			foroDAO.cambiarEstado(idForo, estado);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void cambiarCerrado(int idForo,int cerrado) throws ServiceException{
		try{
			foroDAO.cambiarCerrado(idForo, cerrado);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void eliminar(int idForo) throws ServiceException {		
		try {
			foroDAO.eliminar(idForo);
		} catch (DAOException e) {
			log.error(e.toString());
		}		
	}
	
	//-*************************** FOROUSUARIODAO ***********************************-/
	
	public Collection<Foro> obtenerForos(Set<Integer> misForos) throws ServiceException {
		Collection<Foro> foros = null;
		try {
			foros = foroUsuarioDAO.obtenerForos(misForos);			
		} catch (DAOException e) {
			log.error(e.toString());
		}		
		return foros;
	}
	
	public Collection<Foro> obtenerForos(String idUsuario, Set<Integer> misForos) throws ServiceException {
		Collection<Foro> foros = null;
		try {
			foros = foroUsuarioDAO.obtenerForos(idUsuario, misForos);			
		} catch (DAOException e) {
			log.error(e.toString());
		}		
		return foros;
	}
	
	public List<Tema> obtenerTemas(int idForo) throws ServiceException {
		List<Tema> temas = new ArrayList<Tema>();
		try {
			temas = foroUsuarioDAO.obtenerTemas(idForo);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return temas;
	}
	
	public List<Mensaje> obtenerMensajes(int idForo, int idTema) throws ServiceException {
		List<Mensaje> mensajes = new ArrayList<Mensaje>();
		try {
			mensajes = foroUsuarioDAO.obtenerMensajes(idForo, idTema);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return mensajes;
	}
	
	public void cambiarEstadoDeTema(int idForo, int idTema, int estado) throws ServiceException{
		try{
			foroUsuarioDAO.cambiarEstado(idForo, idTema, estado);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void eliminarTema(int idForo, int idTema) throws ServiceException{
		try{
			foroUsuarioDAO.eliminarTema(idForo, idTema);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void eliminarMensaje(int idForo, int idTema, int idMensaje) throws ServiceException{
		try{
			foroUsuarioDAO.eliminarMensaje(idForo, idTema, idMensaje);
		}catch(DAOException e){
			log.error(e);
			throw new ServiceException(e.toString());
		}
	}
	
	public void nuevoTema(Tema tema) throws ServiceException {
		try {
			foroUsuarioDAO.nuevoTema(tema);
		} catch (DAOException e) {
			log.error(e.toString());
		}
	}

	public void nuevoMensaje(Mensaje mensaje) throws ServiceException {
		try {
			foroUsuarioDAO.nuevoMensaje(mensaje);
		} catch (DAOException e) {
			log.error(e.toString());
		}
	}
	
	public Tema obtenerTema(int idForo, int idTema) throws ServiceException {		
		Tema tema = null;
		try {
			tema = foroUsuarioDAO.obtenerTema(idForo, idTema);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return tema;
	}
	
	public Foro obtenerForo(int idForo, String idUsuario) throws ServiceException {
		Foro foro = new Foro();
		try {
			foro = foroUsuarioDAO.obtenerForo(idForo,idUsuario);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return foro;
	}
	
	public void calificar(Tema tema) throws ServiceException {		
		try {
			foroUsuarioDAO.calificarTema(tema);
		} catch (DAOException e) {
			log.error(e.toString());
		}		
	}
	
	public String obtenerEmisor(int idMensaje) throws ServiceException {		
		try {
			return foroUsuarioDAO.obtenerEmisor(idMensaje);
		} catch (DAOException e) {
			log.error(e.toString());
		}
		return null;
	}
	*/
}
