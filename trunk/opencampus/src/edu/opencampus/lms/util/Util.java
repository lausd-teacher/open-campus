package edu.opencampus.lms.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.opencampus.lms.modelo.Departamento;
import edu.opencampus.lms.modelo.Especialidad;
import edu.opencampus.lms.modelo.Sede;
import edu.opencampus.lms.modelo.ficha.Unidad;
import edu.opencampus.lms.modelo.usuario.Rol;

public class Util {

	public static final Map<String, String> getTestTipos() {
		Map<String, String> TEST_TIPOS = new HashMap<String, String>();
		TEST_TIPOS.put(String.valueOf(Constante.TEST_NUM_ASIMPLE),
				Constante.TEST_TIPO_ASIMPLE);
		TEST_TIPOS.put(String.valueOf(Constante.TEST_NUM_AMULTIPLE),
				Constante.TEST_TIPO_AMULTIPLE);
		TEST_TIPOS.put(String.valueOf(Constante.TEST_NUM_VF),
				Constante.TEST_TIPO_VF);
		TEST_TIPOS.put(String.valueOf(Constante.TEST_NUM_RELACIONAR),
				Constante.TEST_TIPO_RELACIONAR);
		TEST_TIPOS.put(String.valueOf(Constante.TEST_NUM_COMPLETAR),
				Constante.TEST_TIPO_COMPLETAR);
		TEST_TIPOS.put(String.valueOf(Constante.TEST_NUM_ORDENAR),
				Constante.TEST_TIPO_ORDENAR);
		return TEST_TIPOS;
	}

	public static String getNombreBrowser(String nombre) {
		nombre = nombre.toLowerCase();
		if (nombre.indexOf("opera") != -1) {
			return "Opera";
		} else if (nombre.indexOf("webkit") != -1) {
			return "Safari";
		} else if (nombre.indexOf("msie 6.0") != -1
				|| nombre.indexOf("msie 7.0") != -1) {
			return "Internet Explorer";
		} else if (nombre.indexOf("gecko") != -1) {
			if (nombre.indexOf("firefox") != -1) {
				return "Firefox";
			}
			if (nombre.indexOf("netscape") != -1) {
				return "Netscape";
			}
			return "Gecko";
		}
		return "Desconocido";
	}

	public static String getFichaEstadoFecha(int codigo) {
		String resultado = "";
		switch (codigo) {
		case 0:
			resultado = Constante.FICHA_ESTADO_FECHA_0;
			break;
		case 1:
			resultado = Constante.FICHA_ESTADO_FECHA_1;
			break;
		case 2:
			resultado = Constante.FICHA_ESTADO_FECHA_2;
			break;
		}
		return resultado;

	}
/*
	public static Collection<Rol> getRoles() {
		Collection<Rol> col = new ArrayList<Rol>();
		col.add(new Rol(Constante.ROL_CAMPUS_ADMINISTRADOR,
				"Campus - Administrador"));
		col.add(new Rol(Constante.ROL_CAMPUS_SOPORTE, "Campus - Soporte"));
		col.add(new Rol(Constante.ROL_CAMPUS_DIRECTOR, "Campus - Director"));
		col.add(new Rol(Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO,
				"Campus - Jefe de Dpto."));
		col.add(new Rol(Constante.ROL_CAMPUS_DOCENTE, "Campus - Docente"));
		col.add(new Rol(Constante.ROL_CAMPUS_ESTUDIANTE, "Campus - Estudiante"));
		col.add(new Rol(Constante.ROL_CAMPUS_MONITOR_REPORTES,
				"Campus - Monitor de reportes"));
		col.add(new Rol(Constante.ROL_CAMPUS_MONITOR_EMPRESA,
				"Campus - Monitor de empresa"));
		col.add(new Rol(Constante.ROL_CAMPUS_SECDOC, "Campus - SECDOC"));
		col.add(new Rol(Constante.ROL_CAMPUS_THINK_BIG, "Campus - Think Big"));		
		col.add(new Rol(Constante.ROL_PERSONAL_DIRECCION, "Personal - Dirección"));
		col.add(new Rol(Constante.ROL_PERSONAL_JEFE_DEPARTAMENTO, "Personal - Jefe de Dpto."));
		col.add(new Rol(Constante.ROL_PERSONAL_USUARIO_FINAL, "Personal - Usuario Final"));
		col.add(new Rol(Constante.ROL_PERSONAL_ADMINISTRADOR_RRHH, "Personal - Administrador RRHH"));
		col.add(new Rol(Constante.ROL_PERSONAL_DIRECTOR_GENERAL, "Personal - Director General"));
		return col;
	}
*//*
	public static Collection<Rol> getRolesFiltrar(Map<Integer, Rol> roles) {
		Collection<Rol> rolCollection = new ArrayList<Rol>();
		Set<Integer> rolTemp = roles.keySet();
		for (Integer id : rolTemp) {
			switch (id.intValue()) {
			case Constante.ROL_CAMPUS_ADMINISTRADOR:
			case Constante.ROL_CAMPUS_SOPORTE:
			case Constante.ROL_CAMPUS_SECDOC:
			case Constante.ROL_CAMPUS_MONITOR_REPORTES:
			case Constante.ROL_CAMPUS_MONITOR_EMPRESA:
			case Constante.ROL_CAMPUS_DIRECTOR:
			case Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO:
			case Constante.ROL_CAMPUS_ESTUDIANTE:
			case Constante.ROL_CAMPUS_DOCENTE:
			
			case Constante.ROL_PERSONAL_DIRECCION:
			case Constante.ROL_PERSONAL_JEFE_DEPARTAMENTO:
			case Constante.ROL_PERSONAL_USUARIO_FINAL:
			case Constante.ROL_PERSONAL_ADMINISTRADOR_RRHH:
			case Constante.ROL_PERSONAL_DIRECTOR_GENERAL:
				
			case Constante.ROL_CAMPUS_THINK_BIG:	
				rolCollection.add(roles.get(id));
			}
		}
		return rolCollection;
	}
*/
	public static String getSucursalNombre(String nombre) {
		String sucursal = "";
		if (null != nombre && 0 < nombre.length()) {
			char letra = nombre.charAt(0);
			switch (letra) {
			case 'L':
				sucursal = Constante.SEDE_LIMA;
				break;
			case 'A':
				sucursal = Constante.SEDE_AREQUIPA;
				break;
			case 'T':
				sucursal = Constante.SEDE_TRUJILLO;
				break;
			}
		}
		return sucursal;
	}

	public static String getStringTurno(int numero) {
		String turno = "";
		switch (numero) {
		case 1:
			turno = Constante.TURNO_MANANA;
			break;
		case 2:
			turno = Constante.TURNO_TARDE;
			break;
		}
		return turno;
	}

	/**
	 * LAs carreras
	 */
	public static Collection<Especialidad> getFormacion() {
		Collection<Especialidad> formaciones = new ArrayList<Especialidad>();
		Especialidad formacion;
		try {
			formacion = new Especialidad();
			formacion.setCodigo("Procesos Quimicos y Metalurgicos");
			formacion.setNombre("Procesos Químicos y Metalúrgicos");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("Mantenimiento Maquinaria Pesada");
			formacion.setNombre("Mantenimiento Maquinaria Pesada");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("Mantenimiento Maquinaria de Planta");
			formacion.setNombre("Mantenimiento Maquinaria de Planta");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("Electrotecnia Industrial");
			formacion.setNombre("Electrotecnia Industrial");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("Electronica y Automatizacion Industrial");
			formacion.setNombre("Electrónica y Automatización Industrial");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("Redes y Comunicaciones de Datos");
			formacion.setNombre("Redes y Comunicaciones de Datos");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("Tecnologia de Sistemas Informaticos para la Productividad");
			formacion.setNombre("Tecnología de Sistemas Informaticos para la Productividad");
			formaciones.add(formacion);
			
			formacion = new Especialidad();
			formacion.setCodigo("Tecnología de la Producción");
			formacion.setNombre("Tecnología de la Producción");
			formaciones.add(formacion);

			formacion = new Especialidad();
			formacion.setCodigo("Tecnología Mecánica Eléctrica");
			formacion.setNombre("Tecnología Mecánica Eléctrica");
			formacion = new Especialidad();
			formacion.setCodigo("Tecnología Agrícola");
			formacion.setNombre("Tecnología Agrícola");
			formaciones.add(formacion);
		} catch (Exception e) {
		}
		return formaciones;
	}

	/**
	 * LAs carreras
	 */
	public static Collection<Especialidad> getFormacionCodigo() {
		Collection<Especialidad> formaciones = new ArrayList<Especialidad>();
		Especialidad formacion;
		try {
			formacion = new Especialidad();
			formacion.setCodigo("11");
			formacion.setNombre("Procesos Químicos y Metalúrgicos");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("12");
			formacion.setNombre("Mantenimiento Maquinaria Pesada");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("13");
			formacion.setNombre("Mantenimiento Maquinaria de Planta");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("14");
			formacion.setNombre("Electrotecnia Industrial");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("15");
			formacion.setNombre("Electrónica y Automatización Industrial");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("16");
			formacion.setNombre("Redes y Comunicaciones de Datos");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("17");
			formacion.setNombre("Tecnología de Sistemas "
					+ "Informaticos para la Productividad");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("100000");
			formacion.setNombre("Estudios Generales G1");
			formaciones.add(formacion);
			formacion = new Especialidad();
			formacion.setCodigo("100001");
			formacion.setNombre("Estudios Generales G2");
			formaciones.add(formacion);
		} catch (Exception e) {
		}
		return formaciones;
	}

	/**
	 * Los Departamentos
	 */
	public static Collection<Departamento> getDepartamentos() {
		Collection<Departamento> formaciones = new ArrayList<Departamento>();
		Departamento formacion;
		try {
			formacion = new Departamento();
			formacion.setCodigo("1");
			formacion.setNombre("Electrónica");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("2");
			formacion.setNombre("Electrotecnia");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("3");
			formacion.setNombre("Estudios Generales");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("4");
			formacion.setNombre("Informática");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("5");
			formacion.setNombre("Mecánica");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("6");
			formacion.setNombre("Química y Metalurgia");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("7");
			formacion.setNombre("Servicios Educativos");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("8");
			formacion.setNombre("Agronomía");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("9");
			formacion.setNombre("Producción");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("10");
			formacion.setNombre("Maquinaria de Planta");
			formaciones.add(formacion);
			formacion = new Departamento();
			formacion.setCodigo("11");
			formacion.setNombre("Equipo Pesado");
			formaciones.add(formacion);
		} catch (Exception e) {
		}
		return formaciones;
	}

	/**
	 * Las Sedes
	 */
	public static Collection<Sede> getSedes() {
		Collection<Sede> formaciones = new ArrayList<Sede>();
		Sede sede = new Sede();
		sede.setCodigo("L");
		sede.setNombre("Lima");
		formaciones.add(sede);
		sede = new Sede();
		sede.setCodigo("A");
		sede.setNombre("Arequipa");
		formaciones.add(sede);
		sede = new Sede();
		sede.setCodigo("T");
		sede.setNombre("Trujillo");
		formaciones.add(sede);

		return formaciones;
	}

	/**
	 * Las Familias
	 *//*
	public static Collection<Sede> getFamilias() {
		Collection<Sede> familias = new ArrayList<Sede>();
		Sede familia = new Sede();
		familia.setCodigo("100");
		familia.setNombre("PFR");
		familias.add(familia);
		familia = new Sede();
		familia.setCodigo("3002");
		familia.setNombre("TVirtual");
		familias.add(familia);
		familia = new Sede();
		familia.setCodigo("3003");
		familia.setNombre("PCC");
		familias.add(familia);

		familia = new Sede();
		familia.setCodigo("50");
		familia.setNombre("Personal");
		familias.add(familia);
		// familia = new Sede();
		// familia.setCodigo(String.valueOf(Constante.ROL_CAMPUS_ADMINISTRADOR));
		// familia.setNombre("Administrador");
		// familias.add(familia);
		// familia = new Sede();
		// familia.setCodigo(String.valueOf(Constante.ROL_CAMPUS_SOPORTE));
		// familia.setNombre("Soporte");
		// familias.add(familia);
		familia = new Sede();
		familia.setCodigo(String.valueOf(Constante.ROL_CAMPUS_DIRECTOR));
		familia.setNombre("Director");
		familias.add(familia);
		familia = new Sede();
		familia.setCodigo(String
				.valueOf(Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO));
		familia.setNombre("Jefe Dept.");
		familias.add(familia);
		familia = new Sede();
		familia.setCodigo(String.valueOf(Constante.ROL_CAMPUS_DOCENTE));
		familia.setNombre("Docente");
		familias.add(familia);
		familia = new Sede();
		familia.setCodigo(String.valueOf(Constante.ROL_CAMPUS_ESTUDIANTE));
		familia.setNombre("Estudiante");
		familias.add(familia);
		familia = new Sede();
		familia.setCodigo(String.valueOf(Constante.ROL_CAMPUS_THINK_BIG));
		familia.setNombre("Think Big");
		familias.add(familia);

		return familias;
	}
	*/
	public static Collection<Sede> getSoloFamilias() {
		Collection<Sede> familias = new ArrayList<Sede>();
		Sede familia = new Sede();
		familia.setCodigo("100");
		familia.setNombre("PFR");
		familias.add(familia);
		familia = new Sede();
		familia.setCodigo("3002");
		familia.setNombre("TVirtual");
		familias.add(familia);
		familia = new Sede();
		familia.setCodigo("3003");
		familia.setNombre("PCC");
		familias.add(familia);
		return familias;
	}

	/**
	 * Las Formaciones
	 */
	public static Collection<Sede> getFormaciones() {
		Collection<Sede> formaciones = new ArrayList<Sede>();
		Sede formacion = new Sede();
		formacion.setCodigo("11");
		formacion.setNombre("C11");
		formaciones.add(formacion);
		formacion = new Sede();
		formacion.setCodigo("12");
		formacion.setNombre("C12");
		formaciones.add(formacion);
		formacion = new Sede();
		formacion.setCodigo("13");
		formacion.setNombre("C13");
		formaciones.add(formacion);
		formacion = new Sede();
		formacion.setCodigo("14");
		formacion.setNombre("C14");
		formaciones.add(formacion);
		formacion = new Sede();
		formacion.setCodigo("15");
		formacion.setNombre("C15");
		formaciones.add(formacion);
		formacion = new Sede();
		formacion.setCodigo("16");
		formacion.setNombre("C16");
		formaciones.add(formacion);
		formacion = new Sede();
		formacion.setCodigo("17");
		formacion.setNombre("C17");
		formaciones.add(formacion);
		formacion = new Sede();
		formacion.setCodigo("100000");
		formacion.setNombre("G1");
		formaciones.add(formacion);
		formacion = new Sede();
		formacion.setCodigo("100001");
		formacion.setNombre("G2");
		formaciones.add(formacion);
		return formaciones;
	}

	/**
	 * Las Secciones
	 */
	public static Collection<Sede> getSecciones() {
		Collection<Sede> secciones = new ArrayList<Sede>();
		Sede seccion = new Sede();
		seccion.setCodigo("1");
		seccion.setNombre("A");
		secciones.add(seccion);
		seccion = new Sede();
		seccion.setCodigo("2");
		seccion.setNombre("B");
		secciones.add(seccion);
		seccion = new Sede();
		seccion.setCodigo("3");
		seccion.setNombre("C");
		secciones.add(seccion);
		seccion = new Sede();
		seccion.setCodigo("4");
		seccion.setNombre("D");
		secciones.add(seccion);
		seccion = new Sede();
		seccion.setCodigo("5");
		seccion.setNombre("E");
		secciones.add(seccion);
		seccion = new Sede();
		seccion.setCodigo("6");
		seccion.setNombre("F");
		secciones.add(seccion);
		seccion = new Sede();
		seccion.setCodigo("7");
		seccion.setNombre("G");
		secciones.add(seccion);

		return secciones;
	}

	public static boolean esMiUnidad(Collection<Unidad> unidades,
			String idUnidad) {
		if (unidades != null && idUnidad != null) {
			for (Unidad uni : unidades) {
				if (uni.getIdUnidad() == Integer.parseInt(idUnidad)) {
					return true;
				}
			}
		}
		return false;
	}

	public static String getNombreUnidad(Collection<Unidad> unidades,
			String idUnidad) {
		if (unidades != null && idUnidad != null) {
			for (Unidad uni : unidades) {
				if (uni.getIdUnidad().equals(idUnidad)) {
					return uni.getNombreCompleto();
				}
			}
		}
		return "";
	}



	/**
	 * Redireccion de correo
	 */
	public static Map<String, String> obtenerUsuariosRedireccionCorreo() {
		Map<String, String> usuarios = new HashMap<String, String>();
		usuarios.put("abejarano", "abejarano@opencampus.edu.pe");
		usuarios.put("drodriguez", "drodriguez@opencampus.edu.pe");
		usuarios.put("jbastante", "jbastante@opencampus.edu.pe");
		usuarios.put("lmaza", "lmaza@opencampus.edu.pe");
		usuarios.put("mchuquiruna", "mchuquiruna@opencampus.edu.pe");
		usuarios.put("amarchese", "amarchese@opencampus.edu.pe");
		usuarios.put("amorales", "amorales@opencampus.edu.pe");
		usuarios.put("jmusayon", "jmusayon@opencampus.edu.pe");
		usuarios.put("eramirez", "eramirez@opencampus.edu.pe");
		usuarios.put("mrivera", "mrivera@opencampus.edu.pe");		
		usuarios.put("ofrech", " ofrech@opencampus.edu.pe");
		usuarios.put("dmunoz", " dmunoz@opencampus.edu.pe");
		usuarios.put("mvicente", "mvicente@opencampus.edu.pe");
		usuarios.put("ebenites", "ebenites@opencampus.edu.pe");
		return usuarios;
	}
/*
	public static String getNombreRol(int idRol) {
		String rol = "";
		switch (idRol) {
		case Constante.ROL_CAMPUS_ADMINISTRADOR:
			rol = "Administrador";
			break;
		case Constante.ROL_CAMPUS_DIRECTOR:
			rol = "Director";
			break;
		case Constante.ROL_CAMPUS_DOCENTE:
			rol = "Docente";
			break;
		case Constante.ROL_CAMPUS_ESTUDIANTE:
			rol = "Estudiante";
			break;
		case Constante.ROL_CAMPUS_JEFE_DEPARTAMENTO:
			rol = "Jefe de departamento";
			break;
		case Constante.ROL_CAMPUS_MONITOR_EMPRESA:
			rol = "Monitor de Empresa";
			break;
		case Constante.ROL_CAMPUS_MONITOR_REPORTES:
			rol = "Monitor de Reportes";
			break;
		case Constante.ROL_CAMPUS_SECDOC:
			rol = "Secretaria Docente";
			break;
		case Constante.ROL_CAMPUS_SOPORTE:
			rol = "Soporte";
			break;
		case Constante.ROL_PERSONAL_USUARIO_FINAL:
			rol = "Empleado (VIP)";
			break;
		}
		return rol;
	}
	*/
	public static Collection<Sede> getMeses() {
		Collection<Sede> meses = new ArrayList<Sede>();
		Sede mes = new Sede();
		mes.setCodigo("01");
		mes.setNombre("Enero");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("02");
		mes.setNombre("Febrero");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("03");
		mes.setNombre("Marzo");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("04");
		mes.setNombre("Abril");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("05");
		mes.setNombre("Mayo");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("06");
		mes.setNombre("Junio");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("07");
		mes.setNombre("Julio");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("08");
		mes.setNombre("Agosto");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("09");
		mes.setNombre("Setiembre");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("10");
		mes.setNombre("Octubre");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("11");
		mes.setNombre("Noviembre");
		meses.add(mes);
		
		mes = new Sede();
		mes.setCodigo("12");
		mes.setNombre("Diciembre");
		meses.add(mes);
		
		return meses;
	}

	/**
	 * Los roles
	 */
	// public static Collection<Rol> getRoles() {
	// Collection<Rol> col = new ArrayList<Rol>();
	// col.add(new Rol(ROL_REGISTRADOR, "REGISTRADOR"));
	// col.add(new Rol(ROL_REVISADOR, "REVISADOR"));
	// col.add(new Rol(ROL_ADMINISTRADOR, "ADMINISTRADOR"));
	// col.add(new Rol(ROL_AUDITOR, "AUDITOR"));
	// col.add(new Rol(ROL_DIRECTOR, "DIRECTOR"));
	// col.add(new Rol(ROL_PRESIDENTE, "PRESIDENTE"));
	// col.add(new Rol(ROL_INSPECTOR, "INSPECTOR"));
	// col.add(new Rol(ROL_ADMINISTRADOR_WEB, "ADMINISTRADOR WEB"));
	// col
	// .add(new Rol(ROL_ADMINISTRADOR_SEGURIDAD,
	// "ADMINISTRADOR SEGURIDAD"));
	// col.add(new Rol(ROL_OPERADOR_SEGURIDAD, "OPERADOR SEGURIDAD"));
	// col.add(new Rol(ROL_OPERADOR_INTRANET, "OPERADOR INTRANET"));
	// col.add(new Rol(ROL_ADMINISTRADOR_SISTEMA, "ADMINISTRADOR SISTEMA"));
	// col.add(new Rol(ROL_OPERADOR_COMERCIAL, "OPERADOR COMERCIAL"));
	// col
	// .add(new Rol(ROL_ADMINISTRADOR_COMERCIAL,
	// "ADMINISTRADOR COMERCIAL"));
	// col.add(new Rol(ROL_PLANIFICADOR_SERVICIO, "PLANIFICADOR SERVICIO"));
	// col.add(new Rol(ROL_ADMINISTRADOR_INSCRIPCIONES,
	// "ADMINISTRADOR INSCRIPCIONES"));
	// col.add(new Rol(ROL_REGISTRADOR_INSCRIPCIONES,
	// "REGISTRADOR INSCRIPCIONES"));
	// col.add(new Rol(ROL_JEFE_DEPARTAMENTO, "JEFE DEPARTAMENTO"));
	// col.add(new Rol(ROL_DIRECTOR_DOCENTE, "DIRECTOR DOCENTE"));
	// col.add(new Rol(ROL_ATENCION_CLIENTE, "ATENCION CLIENTE"));
	// col.add(new Rol(ROL_DIRECTOR_PROMOCION, "DIRECTOR PROMOCION"));
	// col.add(new Rol(ROL_JEFE_PLANEAMIENTO_MARKETING,
	// "JEFE PLANEAMIENTO MARKETING"));
	// col.add(new Rol(ROL_JEFE_CUENTA, "JEFE CUENTA"));
	// col.add(new Rol(ROL_ASISTENTE_PLANEAMIENTO, "ASISTENTE PLANEAMIENTO"));
	// col.add(new Rol(ROL_SMAT_ADMINISTRADOR_COMERCIAL,
	// "SMAT ADMINISTRADOR COMERCIAL"));
	// col.add(new Rol(ROL_ADMINISTRADOR_WEB_LIMA, "ADMINISTRADOR WEB LIMA"));
	// col.add(new Rol(ROL_ADMINISTRADOR_WEB_AREQUIPA,
	// "ADMINISTRADOR WEB AREQUIPA"));
	// col.add(new Rol(ROL_SAPR_CONSULTA, "SAPR CONSULTA"));
	// col.add(new Rol(ROL_SMAT_ADMINISTRADOR_ALUMNO,
	// "SMAT ADMINISTRADOR ALUMNO"));
	// col.add(new Rol(ROL_SMAT_ADMINISTRADOR_DOCENTE,
	// "SMAT ADMINISTRADOR DOCENTE"));
	// col.add(new Rol(ROL_SMAT_ADMINISTRADOR_ESPECIALIDAD_PERIODO,
	// "SMAT ADMINISTRADOR ESPECIALIDAD PERIODO"));
	// col.add(new Rol(ROL_SMAT_ADMINISTRADOR_SECCIONES,
	// "SMAT ADMINISTRADOR SECCIONES"));
	// col.add(new Rol(ROL_SMAT_OPERADOR, "SMAT OPERADOR"));
	// col.add(new Rol(ROL_SAPR_ADMINISTRADOR, "SAPR ADMINISTRADOR"));
	// col.add(new Rol(ROL_ADMINISTRADOR_WEB_INTERES,
	// "ADMINISTRADOR WEB INTERES"));
	// col
	// .add(new Rol(ROL_SAPR_ACTUALIZAR_QUIMICA,
	// "SAPR ACTUALIZAR QUIMICA"));
	// col.add(new Rol(ROL_SAPR_ACTUALIZAR_ELECTROTECNIA,
	// "SAPR ACTUALIZAR ELECTROTECNIA"));
	// col.add(new Rol(ROL_SAPR_ACTUALIZAR_EE_GG, "SAPR ACTUALIZAR EE GG"));
	// col.add(new Rol(ROL_SAPR_ACTUALIZAR_ELECTRONICA,
	// "SAPR ACTUALIZAR ELECTRONICA"));
	// col.add(new Rol(ROL_SAPR_ACTUALIZAR_INFORMATICA,
	// "SAPR ACTUALIZAR INFORMATICA"));
	// col.add(new Rol(ROL_SAPR_ACTUALIZAR_MECANICA,
	// "SAPR ACTUALIZAR MECANICA"));
	// col
	// .add(new Rol(ROL_SAPR_ACTUALIZAR_SS_EE,
	// "ROL_SAPR_ACTUALIZAR_SS_EE"));
	// col.add(new Rol(ROL_ASISTENTE_DEPARTAMENTO, "ASISTENTE DEPARTAMENTO"));
	// col.add(new Rol(ROL_ALUMNO_PFR, "ALUMNO PFR"));
	// col.add(new Rol(ROL_ASISTENTE_VENTAS, "ASISTENTE VENTAS"));
	// col.add(new Rol(ROL_ASISTENTE_MARKETING, "ASISTENTE MARKETING"));
	// col.add(new Rol(ROL_PERSONAL_DIRECCION, "PERSONAL DIRECCION"));
	// col.add(new Rol(ROL_PERSONAL_JEFE_DEPARTAMENTO,
	// "PERSONAL JEFE DEPARTAMENTO"));
	// col.add(new Rol(ROL_PERSONAL_USUARIO_FINAL, "PERSONAL USUARIO FINAL"));
	// col.add(new Rol(ROL_PERSONAL_ADMINISTRADOR_RRHH, "PERSONAL ADMINISTRADOR RRHH"));
	// col.add(new Rol(ROL_PERSONAL_DIRECTOR_GENERAL,
	// "PERSONAL DIRECTOR GENERAL"));
	// col.add(new Rol(ROL_SCOB_PROGRAMADOR, "SCOB PROGRAMADOR"));
	// col.add(new Rol(ROL_PERSONAL_CONTABLE, "PERSONAL CONTABLE"));
	// col.add(new Rol(ROL_RESPONSABLE_CAJA, "RESPONSABLE CAJA"));
	// col.add(new Rol(ROL_APOYO_TESORERIA, "APOYO TESORERIA"));
	// col.add(new Rol(ROL_ASISTENCIA_TESORERIA, "ASISTENCIA TESORERIA"));
	// col.add(new Rol(ROL_JEFE_TESORERIA, "JEFE TESORERIA"));
	// col
	// .add(new Rol(ROL_DIRECTOR_ADMINISTRATIVO,
	// "DIRECTOR ADMINISTRATIVO"));
	// col.add(new Rol(ROL_SCOB_REPORTE_VENTA, "SCOB REPORTE VENTA"));
	// col.add(new Rol(ROL_CAMPUS_ADMINISTRADOR, "CAMPUS ADMINISTRADOR"));
	// col.add(new Rol(ROL_CAMPUS_SOPORTE, "CAMPUS SOPORTE"));
	// col.add(new Rol(ROL_CAMPUS_DIRECTOR, "CAMPUS DIRECTOR"));
	// col.add(new Rol(ROL_CAMPUS_JEFE_DEPARTAMENTO,
	// "CAMPUS JEFE DEPARTAMENTO"));
	// return col;
	// }
	//	
}
