package edu.tecsup.lms.util;

/**
 * Esta clase representa una opcion para los usuarios del sistema.
 * 
 * Todos los menus tienen <br>
 * NOMBRE que se agrupa</br> este nombre se debe de tener en cuenta para no
 * duplicar menus.
 * 
 * 
 * 
 * @author Erick Benites
 * 
 */
public class MenuOpciones {

	public static class Menu {

		private String nombre;
		/**
		 * Para PopUps en rutaWeb: <a href="javascript:window.open(' popups.htm ',
		 * 'popup' ,'width= 300 ,height= 400 ');">
		 */
		private String rutaWeb;

		private String superMenu;

		public String getNombre() {
			return nombre;
		}

		public String getRutaWeb() {
			return rutaWeb;
		}

		public String getSuperMenu() {
			return superMenu;
		}

		private Menu(String nombre, String rutaWeb, String superMenu) {
			super();
			this.nombre = nombre;
			this.rutaWeb = rutaWeb;
			this.superMenu = superMenu;
		}

	}

	/**
	 * Numero de opciones como maximo en el menu que se pouede si tiene mas no
	 * se vera
	 */
	public static final int NUMERO_OPCIONES = 3;

	/**
	 * Opciones del Rol de administrador
	 */
	public static final Menu[] menuAdministrador = {
			new Menu("portal.menu.campana",
					"/admin/ficha/Campana.action",
					"portal.menu.grupo.esquema"),
			new Menu("portal.menu.auditoria_secdoc",
					"/reporte/AuditoriaSecDoc.action",
					"portal.menu.grupo.auditoria"),
			new Menu("portal.menu.auditoria_campusv3",
					"/admin/auditoria/reporte1.jsp",
					"portal.menu.grupo.auditoria"),
			new Menu("portal.menu.noticia", "/admin/noticia/Cargar.action",
					"portal.menu.grupo.servicio"),
			new Menu("portal.menu.auditoria_apto",
					"/reporte/AuditoriaDeCurso.action",
					"portal.menu.grupo.auditoria"),
			new Menu("portal.menu.auditoria_dialogo",
					"/reporte/AuditoriaDeDocente.action",
					"portal.menu.grupo.auditoria"),
			new Menu("portal.menu.jerarquia", "/admin/jerarquia/Buscar.action",
					"portal.menu.grupo.esquema"),
			new Menu("portal.menu.silabo", "/admin/curso/Cargar.action",
					"portal.menu.grupo.curso"),
			new Menu("portal.menu.periodo", "/admin/periodo/Buscar.action",
					"portal.menu.grupo.esquema"),
			new Menu("portal.menu.unidad", "/admin/unidad/Cargar.action",
					"portal.menu.grupo.curso"),
			new Menu("portal.menu.monitoreo",
					"/admin/usuario/Monitoreo.action",
					"portal.menu.grupo.usuario"),
			new Menu("portal.menu.curso", "/admin/ficha/Cargar.action",
					"portal.menu.grupo.curso"),
			new Menu("portal.menu.portal",
					"/admin/portal/ObtenerServicios.action",
					"portal.menu.grupo.usuario"),
			new Menu("portal.menu.usuario", "/admin/usuario/Buscar.action",
					"portal.menu.grupo.usuario"),
			new Menu("portal.menu.auditoria", "/reporte/Auditoria.action",
					"portal.menu.grupo.auditoria"),
			new Menu("portal.menu.foros", "/admin/foro/Cargar.action",
					"portal.menu.grupo.servicio"),
			new Menu("portal.menu.ingresarcomo",
					"/admin/IngresarComo.action",
					"portal.menu.grupo.usuario"),
			new Menu("portal.menu.buzon",
					"/admin/buzon/Cargar.action",
					"portal.menu.grupo.servicio") };

	/**
	 * Opciones del Rol de jefe de departamento
	 */
	public static final Menu[] menuJefe = {
			new Menu("portal.menu.directorio", "/admin/usuario/Buscar.action",
					"portal.menu.grupo.usuario"),
			new Menu("portal.menu.grupo.auditoria", "/reporte/CargarAuditoriaDepartamento.action",
					"portal.menu.grupo.auditoria"),
			new Menu("portal.menu.auditoria_dialogo", "/reporte/AuditoriaDeDocente.action",
					"portal.menu.grupo.auditoria"),
			new Menu("portal.menu.auditoria_apto", "/reporte/AuditoriaDeCurso.action",
					"portal.menu.grupo.auditoria"),
			new Menu("portal.menu.auditoria_secdoc", "/reporte/AuditoriaSecDoc.action",
					"portal.menu.grupo.auditoria"),
			new Menu("portal.menu.curso", "/admin/ficha/Cargar.action",
					"portal.menu.grupo.curso") };
	/**
	 * Opciones del Rol de soporte
	 */
	public static final Menu[] menuSoporte = { 
		new Menu("portal.menu.campana", "/admin/ficha/Campana.action",
				"portal.menu.grupo.esquema"),
		new Menu("portal.menu.auditoria_secdoc", "/reporte/AuditoriaSecDoc.action",
				"portal.menu.grupo.auditoria"),
		new Menu("portal.menu.noticia", "/admin/noticia/Cargar.action",
				"portal.menu.grupo.servicio"),
		new Menu("portal.menu.auditoria_apto", "/reporte/AuditoriaDeCurso.action",
				"portal.menu.grupo.auditoria"),
		new Menu("portal.menu.auditoria_dialogo", "/reporte/AuditoriaDeDocente.action",
				"portal.menu.grupo.auditoria"),
		new Menu("portal.menu.silabo", "/admin/curso/Cargar.action",
				"portal.menu.grupo.curso"),
		new Menu("portal.menu.unidad", "/admin/unidad/Cargar.action",
				"portal.menu.grupo.curso"),
		new Menu("portal.menu.curso", "/admin/ficha/Cargar.action",
				"portal.menu.grupo.curso"),
		new Menu("portal.menu.directorio", "/admin/usuario/Buscar.action",
				"portal.menu.grupo.usuario"),
		new Menu("portal.menu.auditoria", "/reporte/Auditoria.action",
				"portal.menu.grupo.auditoria"),
		new Menu("portal.menu.foros", "/admin/foro/Cargar.action",
				"portal.menu.grupo.servicio") };
	/**
	 * Opciones del Rol de docente
	 */
	public static final Menu[] menuDocente = { new Menu(
			"portal.menu.directorio", "/Directorio.action",
			"portal.menu.grupo.usuario") };
	/**
	 * Opciones del Rol de estudiante
	 */
	public static final Menu[] menuUsuario = { new Menu(
			"portal.menu.directorio", "/Directorio.action",
			"portal.menu.grupo.usuario") };

	/**
	 * Opciones del Rol de Monitor
	 */
	public static final Menu[] menuMonitor = {
			new Menu("portal.menu.directorio", "/admin/usuario/Buscar.action",
					"portal.menu.grupo.usuario") };
	
	/**
	 * Opciones del Rol de Monitor
	 */
	public static final Menu[] menuEmpresa = { 
			new Menu("portal.menu.auditoria", "/reporte/Auditoria.action",
					"portal.menu.grupo.auditoria") };

	/**
	 * Menus que tienen todos
	 */
	public static final Menu[] menuGlobalInicio = {
			new Menu("portal.menu.inicio", "/Portal.action", ""),
			new Menu("portal.menu.perfil", "/MisDatos.action", ""), };

	/**
	 * Menus que tienen todos
	 */
	public static final Menu[] menuGlobalFin = {
			new Menu("portal.menu.soporte", "/Soporte.action", ""),
			new Menu("portal.menu.utilitarios", "/Utilitarios.action", ""),
			new Menu("portal.menu.historicos", "/CursoHistorico.action", ""),
			new Menu("portal.menu.configuracion", "/portal/ConfigurarPortal.action", "") };

	public static final Menu menuOpciones = new Menu("portal.menu.opciones",
			"/Opciones.action", "");

}
