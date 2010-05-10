package edu.opencampus.lms.action;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.opencampus.lms.modelo.Usuario;
import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.MenuOpciones;
import edu.opencampus.lms.util.MenuOpciones.Menu;

public class OpcionesAction extends BaseAction {

	private static final long serialVersionUID = -7282481271251539750L;

	private Map<String, Map<String, Menu>> opciones;
	
	public Map<String, Map<String, Menu>> getOpciones() {
		return opciones;
	}

	public void setOpciones(Map<String, Map<String, Menu>> opciones) {
		this.opciones = opciones;
	}

	public String obtenerOpciones() {
		log.info("obtenerOpciones()");
		Map<String, Map<String, Menu>> menus = new HashMap<String, Map<String, Menu>>();
		Map<String, Menu> tempMenus = null;
		int u;

		Usuario usuario = getUsuarioSession();
		
		if (usuario.hasRol(Constante.ROL_CAMPUS_ADMINISTRADOR)) {
			for (u = 0; u < MenuOpciones.menuAdministrador.length; u++) {
				if (!menus.containsKey(MenuOpciones.menuAdministrador[u].getSuperMenu())) {
					tempMenus = new HashMap<String, Menu>();
					tempMenus.put(MenuOpciones.menuAdministrador[u].getNombre(), MenuOpciones.menuAdministrador[u]);
					menus.put(MenuOpciones.menuAdministrador[u].getSuperMenu(),tempMenus);
				} else {
					tempMenus = menus.get(MenuOpciones.menuAdministrador[u].getSuperMenu());
					tempMenus.put(MenuOpciones.menuAdministrador[u].getNombre(), MenuOpciones.menuAdministrador[u]);
					menus.put(MenuOpciones.menuAdministrador[u].getSuperMenu(),tempMenus);
				}
			}
		}
		
		if (usuario.hasRol(Constante.ROL_CAMPUS_SOPORTE)) {
			for (u = 0; u < MenuOpciones.menuSoporte.length; u++) {
				if (!menus.containsKey(MenuOpciones.menuSoporte[u].getSuperMenu())) {
					tempMenus = new HashMap<String, Menu>();
					tempMenus.put(MenuOpciones.menuSoporte[u].getNombre(), MenuOpciones.menuSoporte[u]);
					menus.put(MenuOpciones.menuSoporte[u].getSuperMenu(),tempMenus);
				} else {
					tempMenus = menus.get(MenuOpciones.menuSoporte[u].getSuperMenu());
					tempMenus.put(MenuOpciones.menuSoporte[u].getNombre(), MenuOpciones.menuSoporte[u]);
					menus.put(MenuOpciones.menuSoporte[u].getSuperMenu(),tempMenus);
				}
			}
		}
		
		if (usuario.hasRol(Constante.ROL_CAMPUS_JEFE)) {
			for (u = 0; u < MenuOpciones.menuJefe.length; u++) {
				if (!menus.containsKey(MenuOpciones.menuJefe[u].getSuperMenu())) {
					tempMenus = new HashMap<String, Menu>();
					tempMenus.put(MenuOpciones.menuJefe[u].getNombre(), MenuOpciones.menuJefe[u]);
					menus.put(MenuOpciones.menuJefe[u].getSuperMenu(),tempMenus);
				} else {
					tempMenus = menus.get(MenuOpciones.menuJefe[u].getSuperMenu());
					tempMenus.put(MenuOpciones.menuJefe[u].getNombre(), MenuOpciones.menuJefe[u]);
					menus.put(MenuOpciones.menuJefe[u].getSuperMenu(),tempMenus);
				}
			}
		}
		
		if (usuario.hasRol(Constante.ROL_CAMPUS_MONITOR)) {
			for (u = 0; u < MenuOpciones.menuMonitor.length; u++) {
				if (!menus.containsKey(MenuOpciones.menuMonitor[u].getSuperMenu())) {
					tempMenus = new HashMap<String, Menu>();
					tempMenus.put(MenuOpciones.menuMonitor[u].getNombre(), MenuOpciones.menuMonitor[u]);
					menus.put(MenuOpciones.menuMonitor[u].getSuperMenu(),tempMenus);
				} else {
					tempMenus = menus.get(MenuOpciones.menuMonitor[u].getSuperMenu());
					tempMenus.put(MenuOpciones.menuMonitor[u].getNombre(), MenuOpciones.menuMonitor[u]);
					menus.put(MenuOpciones.menuMonitor[u].getSuperMenu(),tempMenus);
				}
			}
		}
		
		if (usuario.hasRol(Constante.ROL_CAMPUS_EMPRESA)) {
			for (u = 0; u < MenuOpciones.menuEmpresa.length; u++) {
				if (!menus.containsKey(MenuOpciones.menuEmpresa[u].getSuperMenu())) {
					tempMenus = new HashMap<String, Menu>();
					tempMenus.put(MenuOpciones.menuEmpresa[u].getNombre(), MenuOpciones.menuEmpresa[u]);
					menus.put(MenuOpciones.menuEmpresa[u].getSuperMenu(),tempMenus);
				} else {
					tempMenus = menus.get(MenuOpciones.menuEmpresa[u].getSuperMenu());
					tempMenus.put(MenuOpciones.menuEmpresa[u].getNombre(), MenuOpciones.menuEmpresa[u]);
					menus.put(MenuOpciones.menuEmpresa[u].getSuperMenu(),tempMenus);
				}
			}
		}
		
		if (usuario.hasRol(Constante.ROL_CAMPUS_DOCENTE)) {
			for (u = 0; u < MenuOpciones.menuDocente.length; u++) {
				if (!menus.containsKey(MenuOpciones.menuDocente[u].getSuperMenu())) {
					tempMenus = new HashMap<String, Menu>();
					tempMenus.put(MenuOpciones.menuDocente[u].getNombre(), MenuOpciones.menuDocente[u]);
					menus.put(MenuOpciones.menuDocente[u].getSuperMenu(),tempMenus);
				} else {
					tempMenus = menus.get(MenuOpciones.menuDocente[u].getSuperMenu());
					tempMenus.put(MenuOpciones.menuDocente[u].getNombre(), MenuOpciones.menuDocente[u]);
					menus.put(MenuOpciones.menuDocente[u].getSuperMenu(),tempMenus);
				}
			}
		}
		
		if (usuario.hasRol(Constante.ROL_CAMPUS_USUARIO)) {
			for (u = 0; u < MenuOpciones.menuUsuario.length; u++) {
				if (!menus.containsKey(MenuOpciones.menuUsuario[u].getSuperMenu())) {
					tempMenus = new HashMap<String, Menu>();
					tempMenus.put(MenuOpciones.menuUsuario[u].getNombre(), MenuOpciones.menuUsuario[u]);
					menus.put(MenuOpciones.menuUsuario[u].getSuperMenu(),tempMenus);
				} else {
					tempMenus = menus.get(MenuOpciones.menuUsuario[u].getSuperMenu());
					tempMenus.put(MenuOpciones.menuUsuario[u].getNombre(), MenuOpciones.menuUsuario[u]);
					menus.put(MenuOpciones.menuUsuario[u].getSuperMenu(),tempMenus);
				}
			}
		}
		
		
		opciones = new TreeMap<String, Map<String, Menu>>(menus);
		return SUCCESS;
	}

}
