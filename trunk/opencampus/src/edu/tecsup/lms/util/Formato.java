package edu.tecsup.lms.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class Formato {
	
	static Log log = LogFactory.getLog(Formato.class);
	
	/**
	 * Validador de correo
	 */
	public static boolean isEmail(String correo) {
		Pattern pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
		Matcher mat = pat.matcher(correo);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Cuando se desea utilizar el calendar en la logica desde Base de Datos
	 * para JAVA
	 * 
	 * @param String
	 * @return GregorianCalendar
	 */
	public static GregorianCalendar dateToCalendar(String fecha) {
		GregorianCalendar gc = null;
		try {
			if (fecha != null) {
				gc = new GregorianCalendar(
						Integer.parseInt(fecha.substring(0,4)), 
						Integer.parseInt(fecha.substring(5, 7))-1,
						Integer.parseInt(fecha.substring(8, 10)));
			}
		} catch (Exception e) {
			log.error("No se puede convertir dateToCalendar " + e);
		}
		return gc;
	}
	
	/**
	 * Cuando se desea utilizar el calendar en la logica desde Base de Datos
	 * para JAVA
	 * 
	 * @param String
	 * @return GregorianCalendar
	 */
	public static GregorianCalendar timestampToCalendar(String fecha) {
		GregorianCalendar gc = null;
		try {
			if (fecha != null) {
				gc = new GregorianCalendar(
						Integer.parseInt(fecha.substring(0,4)), 
						Integer.parseInt(fecha.substring(5, 7))-1,
						Integer.parseInt(fecha.substring(8, 10)), 
						Integer.parseInt(fecha.substring(11, 13)), 
						Integer.parseInt(fecha.substring(14, 16)), 
						Integer.parseInt(fecha.substring(17, 19)));
			}
		} catch (Exception e) {
			log.error("No se puede convertir timestampToCalendar" + e);
		}
		return gc;
	}
	
	/**
	 * Convierte un calendar a string para la bd en date
	 * para JAVA
	 * 
	 * @param GregorianCalendar
	 * @return String
	 */
	public static String calendarToDate(GregorianCalendar calendar) {
		String fecha = null;
		try {
			if (calendar != null) {
				fecha = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
			}
		} catch (Exception e) {
			log.error("No se puede convertir calendarToDate " + e);
		}
		return fecha;
	}
	
	/**
	 * Convierte un calendar a string para la bd en timestamp
	 * para JAVA
	 * 
	 * @param String
	 * @return GregorianCalendar
	 */
	public static String calendarToTimestamp(GregorianCalendar calendar) {
		String fecha = null;
		try {
			if (calendar != null) {
				fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
			}
		} catch (Exception e) {
			log.error("No se puede convertir calendarToTimestamp " + e);
		}
		return fecha;
	}
	
	/**
	 * Cuando se desee mostrar una fecha para JSP desde JAVA completo
	 * 
	 * @param Calendar
	 * @return String
	 */
	public static String calendarToString(GregorianCalendar calendar, String formato) {
		String fecha = null;
		if (calendar != null && formato != null && formato.trim().length()>0) {
			try {
				fecha = new SimpleDateFormat(formato).format(calendar.getTime());
			} catch (Exception e) {
				log.error("No se puede convertir calendarToString" + e);
			}
		}
		return fecha;
	}
	
	/**
	 * Cuando se desea utilizar el calendar en la logica desde JSP para JAVA
	 * 
	 * @param String
	 * @return GregorianCalendar
	 */
	public static GregorianCalendar stringToCalendar(String string, String formato) {
		GregorianCalendar calendar = null;
		if (string != null && formato != null && formato.trim().length()>0) {
			try {
				calendar = new GregorianCalendar();
				calendar.setTime(new SimpleDateFormat(formato).parse(string));
			} catch (Exception e) {
				log.error("No se puede convertir stringToCalendar" + e);
				calendar = null;
			}
		}
		return calendar;
	}
	
	/**
	 * Cuando se desee una frase o palabra con letras iniciales en mayuscula.
	 * 
	 * @param name
	 * @return String
	 */
	public static String formatoTexto(String name) {
		if (null == name || name.length() == 0) {
			return "";
		}
		if (name.contains("@")) {
			return name.toLowerCase();
		}
		String temp = name;
		try {
			name = name.toLowerCase();
			String[] name_parte = name.split(" ");
			name = "";
			for (int u = 0; u < name_parte.length; u++) {
				if (!("null".equals(name_parte[u]))) {
					try {
						name += name_parte[u].substring(0, 1).toUpperCase()
								+ name_parte[u].substring(1, name_parte[u]
										.length()) + " ";
					} catch (Exception e) {
						name += name_parte[u];
					}
				}
			}
		} catch (Exception r) {
			return temp;
		}
		return name.trim();
	}
	
	/**
	 * Elimina espacios sobrantes y reemplaza tildes (para una busqueda)
	 * @param frace
	 * @return
	 */
	public static String matizarFrace(String frace) {
		frace = frace.trim().toUpperCase().replace('Á', 'A').replace('É', 'E')
				.replace('Í', 'I').replace('Ó', 'O').replace('Ú', 'U').replace('Ñ', 'N');
		while (frace.indexOf("  ") != -1) {
			frace = frace.replaceAll("  ", " ");
		}
		return frace.trim();
	}
	
	/**
	 * Filtra caracteres de un texto que no sea alfabetico
	 * http://java.sun.com/javase/6/docs/api/java/util/regex/Pattern.html
	 * @param text
	 * @return
	 */
	public static String normalizarFrace(String text) {
		if(text != null && text.length()>0){
			
			text = Formato.matizarFrace(text)
			.replace((char) 220, 'U')
			.replace((char) 192, 'A')
			.replace((char) 196, 'A')
			.replace((char) 200, 'E')
			.replace((char) 203, 'E')
			.replace((char) 204, 'I')
			.replace((char) 207, 'I')
			.replace((char) 210, 'O')
			.replace((char) 214, 'O')
			.replace((char) 217, 'U')
			.replace((char) 220, 'U');
			
			Pattern p = Pattern.compile("[^a-zA-Z ]+"); //Caracter no alfabetico
	        Matcher m = p.matcher(text);
	        StringBuffer sb = new StringBuffer();
	        while(m.find()) {
	            m.appendReplacement(sb, "");
	        }
	        m.appendTail(sb);

	        return sb.toString().toLowerCase();
			
		}
		return text;
	}
	
	/**
	 * Completa ceros a la izquierda de un string
	 * @param cadena
	 * @param longFinal
	 * @return
	 */
	public static String completarCeros(String cadena, int longFinal) {
		StringBuffer ceros = new StringBuffer();
		while (cadena.length()+ceros.length() < longFinal) {
			ceros.append("0");
		}
		return ceros.append(cadena).toString();
	}
	
	public static String completarCeros(Integer cadena, int longFinal) {
		return Formato.completarCeros(String.valueOf(cadena),longFinal);
	}
	
	/**
	 * Origina encriptacion md5 de una palabra
	 * @param message
	 * @return
	 */
	public static String md5(String message) {
		try {
			if(message != null){
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] array = md.digest(message.getBytes("CP1252"));
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < array.length; ++i) {
					sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
				}
				return sb.toString();
			}
		} catch (Exception e) {
			
		} 
		return null;
	}
	
	
	/***********************************************************************************/
	
	/**
	 * Cuando se desea utilizar el calendar en la logica desde Base de Datos
	 * para JAVA
	 * 
	 * @param String
	 * @return GregorianCalendar
	 */
	public static final GregorianCalendar getDateDeBaseDatos(String fecha) {
		GregorianCalendar gc = null;
		try {
			if (fecha != null) {
				gc = new GregorianCalendar(Integer.parseInt(fecha.substring(0,
						4)), Integer.parseInt(fecha.substring(5, 7)) - 1,
						Integer.parseInt(fecha.substring(8, 10)), Integer
								.parseInt(fecha.substring(11, 13)), Integer
								.parseInt(fecha.substring(14, 16)), Integer
								.parseInt(fecha.substring(17, 19)));
			}
		} catch (Exception e) {
			Log log = LogFactory.getLog(Formato.class);
			log.error("No se puede convertir " + e);
		}
		return gc;
	}
	
	/**
	 * Cuando se obtiene un valor float en el momento de redondiar
	 * 
	 * @param numer
	 * @return
	 */
	public static float redondeaFloat(float numer) {
		return ((float) (Math.round(numer * 100)) / 100);
	}

	/**
	 * Ordena la primera letra de la palabra.
	 * 
	 * @param palabra
	 * @return
	 */
	public static String numeroOrdenAlfabetico(String palabra) {

		String resultado = "";
		try {
			if (palabra != null && !"".equals(palabra.trim())) {
				char c = palabra.toLowerCase().charAt(0);
				switch (c) {
				case 'a':
					resultado = "11";
					break;
				case 'b':
					resultado = "12";
					break;
				case 'c':
					resultado = "13";
					break;
				case 'd':
					resultado = "14";
					break;
				case 'e':
					resultado = "15";
					break;
				case 'f':
					resultado = "16";
					break;
				case 'g':
					resultado = "17";
					break;
				case 'h':
					resultado = "18";
					break;
				case 'i':
					resultado = "19";
					break;
				case 'j':
					resultado = "20";
					break;
				case 'k':
					resultado = "21";
					break;
				case 'l':
					resultado = "22";
					break;
				case 'm':
					resultado = "23";
					break;
				case 'n':
					resultado = "24";
					break;
				case 'ñ':
					resultado = "25";
					break;
				case 'o':
					resultado = "26";
					break;
				case 'p':
					resultado = "27";
					break;
				case 'q':
					resultado = "28";
					break;
				case 'r':
					resultado = "29";
					break;
				case 's':
					resultado = "30";
					break;
				case 't':
					resultado = "31";
					break;
				case 'u':
					resultado = "32";
					break;
				case 'v':
					resultado = "33";
					break;
				case 'w':
					resultado = "34";
					break;
				case 'x':
					resultado = "35";
					break;
				case 'y':
					resultado = "36";
					break;
				case 'z':
					resultado = "37";
					break;
				}
			}
		} catch (Exception e) {
			Log log = LogFactory.getLog(Formato.class);
			log.error("No se puede convertir " + palabra + " - "+e);
		}
		return resultado;
	}

	/**
	 * Deacuerdo al numero te obtiene la letra.
	 * 
	 * @param palabra
	 * @return
	 */
	public static String numeroAAlfabetico(String numero) {
		String resultado = "";
		try {
			if (numero != null) {

				int numero1 = Integer.parseInt(numero);
				switch (numero1) {
				case 0:
					resultado = "";
					break;
				case 1:
					resultado = "A";
					break;
				case 2:
					resultado = "B";
					break;
				case 3:
					resultado = "C";
					break;
				case 4:
					resultado = "D";
					break;
				case 5:
					resultado = "E";
					break;
				case 6:
					resultado = "F";
					break;
				case 7:
					resultado = "G";
					break;
				case 8:
					resultado = "H";
					break;
				case 9:
					resultado = "I";
					break;
				case 10:
					resultado = "J";
					break;
				case 11:
					resultado = "K";
					break;
				case 12:
					resultado = "L";
					break;
				case 13:
					resultado = "M";
					break;
				case 14:
					resultado = "N";
					break;
				case 15:
					resultado = "Ñ";
					break;
				case 16:
					resultado = "O";
					break;
				case 17:
					resultado = "P";
					break;
				case 18:
					resultado = "Q";
					break;
				case 19:
					resultado = "R";
					break;
				case 20:
					resultado = "S";
					break;
				case 21:
					resultado = "T";
					break;
				case 22:
					resultado = "U";
					break;
				case 23:
					resultado = "V";
					break;
				case 24:
					resultado = "W";
					break;
				case 25:
					resultado = "X";
					break;
				case 26:
					resultado = "Y";
					break;
				case 27:
					resultado = "Z";
					break;
				default:
					resultado = "";
				}
			}
		} catch (Exception e) {
			Log log = LogFactory.getLog(Formato.class);
			log.error("No se puede convertir " + numero);
		}
		return resultado;
	}

	/**
	 * Formato del nombre a mostrar
	 * 
	 * @param Nombre1
	 *            Nombre2 Paterno Materno
	 * @return String
	 */
	public static String formatoNombreCompletoJSP(String nombre1,
			String nombre2, String paterno, String materno) {
		String nombreCompleto = "";
		if (null == nombre1) {
			nombre1 = "";
		}
		if (null == nombre2) {
			nombre2 = "";
		}
		if (null == paterno) {
			paterno = "";
		}
		if (null == materno) {
			materno = "";
		}
		nombreCompleto = formatoTexto(paterno + " " + materno + ", " + nombre1
				+ " " + nombre2);
		return nombreCompleto;
	}

	/**
	 * Obtener Formato para mostrar en el Ubigeo
	 * 
	 * @param nombreCompleto
	 * @param provincia
	 * @param distrito
	 * @param departamento
	 * @param pais
	 * @return
	 */
	public static String formatoUbigeo(String provincia, String distrito,
			String departamento, String pais) {
		String nombreCompleto = "";
		if (null != distrito && 0 != distrito.length()) {
			nombreCompleto += distrito + " / ";
		}
		if (null != provincia && 0 != provincia.length()) {
			nombreCompleto += provincia + " / ";
		}
		if (null != departamento && 0 != departamento.length()) {
			nombreCompleto += departamento + " / ";
		}
		if (null != pais && 0 != pais.length()) {
			nombreCompleto += pais;
		}
		return nombreCompleto;
	}

	/**
	 * Cuando se desee una frase o palabra con letras iniciales en mayuscula.
	 * 
	 * @param name
	 * @return String
	 */
	public static String formatoTextoNull(String name) {
		if (null == name) {
			return "";
		}
		return name;
	}

	/**
	 * Longitud de la cadena
	 * 
	 * @param valor
	 * @return
	 */
	public static Integer longitud(Object valor) {
		try {
			if (valor instanceof String) {
				String temp = String.valueOf(valor);
				return temp.trim().length();
			}
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * Cuando se desee una frase o palabra con letras iniciales en mayuscula.
	 * 
	 * @param name
	 * @return String
	 */
	public static String formatoTextoNull(String name, String posible) {
		if (null == name) {
			return posible;
		}
		return name;
	}

	

	/**
	 * Cuando se desee mostrar una fecha para JSP desde JAVA
	 * 
	 * @param Calendar
	 * @return String
	 */
	public static String getStringDeDate(Object obj) {
		if (obj != null) {
			SimpleDateFormat formatter = new SimpleDateFormat();
			java.util.Date date = new java.util.Date();
			try {
				Calendar calendario = (Calendar) obj;
				date.setTime(calendario.getTime().getTime());
				formatter = new SimpleDateFormat(Constante.FECHA_DIA_MES_ANO);
				return formatter.format(date);
			} catch (Exception e) {
				try {
					java.util.Date date2 = (java.util.Date) obj;
					date.setTime(date2.getTime());
					formatter = new SimpleDateFormat(
							Constante.FECHA_DIA_MES_ANO);
					return formatter.format(date);
				} catch (Exception f) {
					Log log = LogFactory.getLog(Formato.class);
					log.info("No se puede convertir " + obj);
				}
			}
		}
		return "";
	}

	/**
	 * Cuando se desee mostrar una fecha para JSP desde JAVA
	 * 
	 * @param Calendar
	 * @return String
	 */
	public static String getStringDeDateNull(Object obj) {

		SimpleDateFormat formatter = new SimpleDateFormat();
		java.util.Date date = new java.util.Date();
		try {
			Calendar calendario = (Calendar) obj;
			date.setTime(calendario.getTime().getTime());
			formatter = new SimpleDateFormat(Constante.FECHA_DIA_MES_ANO);
			return formatter.format(date);
		} catch (Exception e) {
			try {
				java.util.Date date2 = (java.util.Date) obj;
				date.setTime(date2.getTime());
				formatter = new SimpleDateFormat(Constante.FECHA_DIA_MES_ANO);
				return formatter.format(date);
			} catch (Exception f) {
				Log log = LogFactory.getLog(Formato.class);
				log.debug("No se puede convertir " + obj);
				Calendar calendario = new GregorianCalendar();
				date.setTime(calendario.getTime().getTime());
				formatter = new SimpleDateFormat(Constante.FECHA_DIA_MES_ANO);
				return formatter.format(date);
			}
		}		
	}

	public static String getHora(Object obj) {
		if (obj != null) {
			SimpleDateFormat formatter = new SimpleDateFormat();
			java.util.Date date = new java.util.Date();
			try {
				Calendar calendario = (Calendar) obj;
				date.setTime(calendario.getTime().getTime());
				formatter = new SimpleDateFormat(Constante.FECHA_HORA_MI);
				return formatter.format(date);
			} catch (Exception e) {
				Log log = LogFactory.getLog(Formato.class);
				log.info("No se puede convertir " + obj);
			}
		}
		return "";
	}

	public static String getStringDeDatePaBKP() {

		SimpleDateFormat formatter = new SimpleDateFormat();
		java.util.Date date = new java.util.Date();
		try {
			formatter = new SimpleDateFormat(
					Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG_FILE);
			return formatter.format(date);
		} catch (Exception e) {
			Log log = LogFactory.getLog(Formato.class);
			log.info("No se puede convertir");
		}

		return "";
	}

	/**
	 * Cuando se desee obtener int de un atribute
	 * 
	 * @param Calendar
	 * @return String
	 */
	public static int getIntofObject(Object obj) {
		try {
			if (obj != null) {
				int numer = Integer.parseInt(String.valueOf(obj));
				return numer;
			}
		} catch (Exception e) {
			Log log = LogFactory.getLog(Formato.class);
			log.info("No se puede convertir en int - " + obj);
		}
		return 0;
	}

	public static String getStringDeDateArchivo(Object obj) {
		if (obj != null) {
			SimpleDateFormat formatter = new SimpleDateFormat();
			java.util.Date date = new java.util.Date();
			try {
				Calendar calendario = (Calendar) obj;
				date.setTime(calendario.getTime().getTime());
				formatter = new SimpleDateFormat(
						Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG_FILE);
				return formatter.format(date);
			} catch (Exception e) {
				Log log = LogFactory.getLog(Formato.class);
				log.info("No se puede convertir - "
						+ "getStringDeDateCompleto(Calendar calendario)");
			}
		}
		return "";
	}

	public static String getStringDeDateArchivoReporte(Object obj) {
		if (obj != null) {
			SimpleDateFormat formatter = new SimpleDateFormat();
			java.util.Date date = new java.util.Date();
			try {
				Calendar calendario = (Calendar) obj;
				date.setTime(calendario.getTime().getTime());
				formatter = new SimpleDateFormat(
						Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG_FILE);
				return formatter.format(date);
			} catch (Exception e) {
				Log log = LogFactory.getLog(Formato.class);
				log.info("No se puede convertir - "
						+ "getStringDeDateCompleto(Calendar calendario)");
			}
		}
		return "";
	}
/*
	public static String getStringEspacioReporte(String obj) {
		return (obj == null) ? "" : Formato.getStringSinEspacio(Formato
				.filtroCaracter(obj));
	}
*/
	public static String getStringSinEspacio(String obj) {
		return (obj == null) ? "" : obj.replaceAll(" ", "").trim();
	}

	/**
	 * Cuando se desea obtener un Date para insertar para Base de datos
	 * TO_DATE(?,'DD/MM/YY HH24:MI:SS') desde JAVA
	 * 
	 * @param Calendar
	 * @return
	 */
	public static final String setBaseDatosDeDate(GregorianCalendar calendario) {
		SimpleDateFormat formatter = new SimpleDateFormat();
		java.util.Date date = new java.util.Date();
		try {
			date.setTime(calendario.getTime().getTime());
			formatter = new SimpleDateFormat(Constante.FECHA_DIA_MES_ANO);
			return formatter.format(date);
		} catch (Exception e) {
			Log log = LogFactory.getLog(Formato.class);
			log
					.info("No se puede convertir - setBaseDatosDeDate(Calendar calendario)");
		}
		return "";
	}

	/**
	 * Cuando se desea obtener un Date para insertar para Base de datos desde
	 * JAVA
	 * 
	 * @param Calendar
	 * @return
	 */
	public static final String setBaseDatosDeDateCompleto(
			GregorianCalendar calendario) {
		SimpleDateFormat formatter = new SimpleDateFormat();
		java.util.Date date = new java.util.Date();
		try {
			date.setTime(calendario.getTime().getTime());
			formatter = new SimpleDateFormat(
					Constante.FECHA_DIA_MES_ANO_HORA_MI);
			return formatter.format(date);
		} catch (Exception e) {
			Log log = LogFactory.getLog(Formato.class);
			log.info("No se puede convertir "
					+ "- setBaseDatosDeDate(Calendar calendario)");
		}
		return "";
	}

	/**
	 * Cuando se desea obtener un Date para insertar para Base de datos desde
	 * JAVA
	 * 
	 * @param Calendar
	 * @return
	 */
	public static final String setBaseDatosDeDateSuperCompleto(
			GregorianCalendar calendario) {
		SimpleDateFormat formatter = new SimpleDateFormat();
		java.util.Date date = new java.util.Date();
		try {
			date.setTime(calendario.getTime().getTime());
			formatter = new SimpleDateFormat(
					Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG);
			return formatter.format(date);
		} catch (Exception e) {
			Log log = LogFactory.getLog(Formato.class);
			log.info("No se puede convertir "
					+ "- setBaseDatosDeDate(Calendar calendario)");
		}
		return "";
	}



	// public static final String getField(Object o, String field) {
	// try {
	// if (null != o) {
	// String temp = String.valueOf(o.getClass().getMethod(
	// "get" + field.substring(0, 1).toUpperCase()
	// + field.substring(1, field.length()), null)
	// .invoke(o, null));
	// if (!"null".equals(temp)) {
	// return temp;
	// }
	// }
	// } catch (Exception e) {
	// Log log = LogFactory.getLog(Formato.class);
	// log.error("No se puede convertir -"
	// + " getField(Object o, String field)");
	// }
	// return "";
	// }
	//
	// public static final String getField(Object o, String[] field) {
	// try {
	// if (null != o) {
	// Method metodo = null;
	// Object object = o;
	// for (int u = 0; u < field.length; u++) {
	// metodo = object.getClass().getMethod(
	// "get" + field[u].substring(0, 1).toUpperCase()
	// + field[u].substring(1, field[u].length()),
	// null);
	// object = metodo.invoke(object, null);
	// }
	// if (!"null".equals(String.valueOf(object))) {
	// return String.valueOf(object);
	// }
	// }
	// } catch (Exception e) {
	// Log log = LogFactory.getLog(Formato.class);
	// log.error("No se puede convertir - "
	// + "getField(Object o, String field)");
	// }
	// return "";
	// }
	//
	// public static final String getFieldDate(Object o, String[] field) {
	// try {
	// if (null != o) {
	// Method metodo = null;
	// Object object = o;
	// for (int u = 0; u < field.length; u++) {
	// metodo = object.getClass().getMethod(
	// "get" + field[u].substring(0, 1).toUpperCase()
	// + field[u].substring(1, field[u].length()),
	// null);
	// object = metodo.invoke(object, null);
	// }
	// if (!"null".equals(String.valueOf(object))) {
	// GregorianCalendar calen = (GregorianCalendar) object;
	// return Formato.getStringDeDate(calen);
	// }
	// }
	// } catch (Exception e) {
	// Log log = LogFactory.getLog(Formato.class);
	// log.error("No se puede convertir -"
	// + " getFieldDate(Object o, String[] field)");
	// }
	// return "";
	// }
	//
	// public static final String getFieldDateCompleto(Object o, String[] field)
	// {
	// try {
	// if (null != o) {
	// Method metodo = null;
	// Object object = o;
	// for (int u = 0; u < field.length; u++) {
	// metodo = object.getClass().getMethod(
	// "get" + field[u].substring(0, 1).toUpperCase()
	// + field[u].substring(1, field[u].length()),
	// null);
	// object = metodo.invoke(object, null);
	// }
	// if (!"null".equals(String.valueOf(object))) {
	// GregorianCalendar calen = (GregorianCalendar) object;
	// return Formato.getStringDeDateCompleto(calen);
	// }
	// }
	// } catch (Exception e) {
	// Log log = LogFactory.getLog(Formato.class);
	// log.error("getFieldDateCompleto(Object o, String[] field)");
	// }
	// return "";
	// }

	public static final String[] dividirStringPor(String pajar, String criterio) {
		StringTokenizer st = new StringTokenizer(pajar, criterio);
		int total = st.countTokens();
		String[] resultado = new String[total];
		int i = 0;
		while (st.hasMoreTokens()) {
			resultado[i++] = st.nextToken();
		}
		return resultado;
	}

	// public static void main(String[] args) {
	// System.out.println(completarCerosALaIzquierda("97",8));
	// }

	


}
