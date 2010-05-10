package edu.opencampus.lms.dao;

import java.util.*;

import org.springframework.beans.factory.ListableBeanFactory;

import edu.opencampus.lms.util.Constante;
import edu.opencampus.lms.util.Formato;

public class TesterDAO {

	public static void main(String[] args) throws Exception {

		// UsuarioDAO dao = new UsuarioDAO();
		// Usuario user = dao.validar(new Usuario("ebenites", "opencampus"));
		// user = dao.obtenerPersona(user);
		// System.out.println(user.getTipo());
		// System.out.println(user.getNombreCompleto());

		JerarquiaDAO jdao = new JerarquiaDAO();
		// Collection<Jerarquia> js = jdao.obtenerHijos(0);
		//		
		// for (Jerarquia j : js) {
		// System.out.println(j.getNombre());
		// for (Jerarquia j2 : j.getHijos()) {
		// System.out.println("\t"+j2.getNombre());
		// }
		// }

		// Jerarquia padre = jdao.obtenerPadre(5);
		// System.out.println(padre.getNombre());
		// System.out.println(padre.getPadre().getNombre());
		// System.out.println(padre.getPadre().getPadre().getNombre());

		// Collection<Integer> n = new ArrayList<Integer>();
		// n.add(4);
		// n.add(5);
		// n.add(10);
		//		
		// System.out.println(n.contains(null));
		//		
		// GregorianCalendar g = new GregorianCalendar();
		// GregorianCalendar m = new GregorianCalendar();
		// m.setTime(g.getTime());
		// m.add(Calendar.DATE, 10);
		// System.out.println(new
		// SimpleDateFormat("d-MM-yyyy").format(m.getTime()));
		// System.out.println(new
		// SimpleDateFormat("d-MM-yyyy").format(g.getTime()));

		//		
		// GregorianCalendar fin = Formato.stringToCalendar("23-04-2009",
		// Constante.FECHA_DIA_MES_ANO);
		// System.out.println(fin.before(new GregorianCalendar()));
		// System.out.println(new GregorianCalendar().after(fin));
		//		
		// funcion q valide fechas

		// System.out.println("a" + (" ".trim()) + "b");

		// char[] chars = {241, 209, 225, 233, 237, 243, 250, 193, 201, 205,
		// 211, 218, 186, 252, 220, 182, 192, 196, 200, 203, 204, 207, 210, 214,
		// 217,
		// 220, 224, 228, 231, 232, 235, 236, 239, 242, 246, 249, 252, 160, 180,
		// 186};
		//		
		// for (int i = 0; i < chars.length; i++) {
		// System.out.println(chars[i]);
		// }

		// System.out.println(Formato.normalizarFrace("ñÑáéíóúÁÉÍÓÚºüÜ¶[]ÀÄÈ ËÌÏ)&/ÒÖÙÜàäçèëìïòöùü ´º"));

		// System.out.println(Pattern.matches("[a-zA-Z]+", "OJIgyiSJAAS"));

		// Pattern p = Pattern.compile("[^a-zA-Z ]+");
		// Matcher m = p.matcher("OJIgyiS4#%$%#$J AAÑñS");
		// StringBuffer sb = new StringBuffer();
		// while(m.find()) {
		// m.appendReplacement(sb, "");
		// }
		// m.appendTail(sb);
		//
		// System.out.println(sb.toString());

		// Usuario u = new Usuario();
		// Persona p = new Persona();
		// p.setNomuno("Erick|");
		// p.setApepaterno("Benitésñ");
		// u.setPersona(p);
		// System.out.println(u.generarUsuario());

		// Document doc= new DocumentImpl();
		// Element demo = doc.createElement("demo");
		// Node text = doc.createCDATASection("aér><<e");
		// demo.appendChild(text);
		// doc.appendChild(demo);
		//		
		//		
		// OutputFormat format = new OutputFormat(doc,"ISO-8859-1",true);
		// format.setLineSeparator(LineSeparator.Unix);
		// format.setIndenting(true);
		// format.setLineWidth(0);
		// format.setPreserveSpace(false);

		// CharArrayWriter salidaXMLParseado = new CharArrayWriter();
		// // Serializamos el arbol DOM
		// XMLSerializer serializerXMLParseado = new
		// XMLSerializer((Writer)salidaXMLParseado,format);
		// serializerXMLParseado.asDOMSerializer();
		// serializerXMLParseado.serialize(doc);
		//        
		// System.out.println("XML parseado: \n"+salidaXMLParseado.toString());

		// new
		// XMLSerializer(System.out,format).asDOMSerializer().serialize(doc);

		// TransformerFactory tFactory = TransformerFactory.newInstance();
		// Transformer transformer = tFactory.newTransformer();
		// transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		// transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
		// StringWriter sw=new StringWriter();
		// transformer.transform( new DOMSource(doc), new StreamResult(sw));
		// System.out.println(sw.toString());

		// GregorianCalendar g = new GregorianCalendar();
		// g.setTime(new
		// SimpleDateFormat(Constante.FECHA_DIA_MES_ANO).parse("02-05-1984"));
		// System.out.println(Formato.completarCeros(""+g.get(GregorianCalendar.DATE),2));
		// System.out.println(Formato.completarCeros(String.valueOf(g.get(GregorianCalendar.MONTH)+1),2));
		// System.out.println(String.valueOf(g.get(GregorianCalendar.YEAR)).substring(2));

		// UsuarioDAO dao = new UsuarioDAO();
		// Usuario user = dao.validar(new Usuario("ebenites", "opencampus"));
		// user = dao.obtenerPersona(user);
		// System.out.println(user.getTipo());
		// System.out.println(user.getNombreCompleto());

		// JerarquiaDAO jdao = new JerarquiaDAO();
		// Collection<Jerarquia> js = jdao.obtenerHijos(6);
		//		
		// for (Jerarquia j : js) {
		// System.out.println(j.getNombre());
		// for (Jerarquia j2 : j.getHijos()) {
		// System.out.println("\t"+j2.getNombre());
		// }
		// }
		// Jerarquia j = new Jerarquia(6);
		// j.setHijos(js);
		//		
		// System.out.println(j.isMyChild(6));

		// Jerarquia j = jdao.obtenerPadre(8);
		// System.out.println(j.getNombre());
		// System.out.println(j.getRutaCompleta());

		// System.out.println((char) 196);

		// Pattern patron = Pattern.compile("[^A-Za-z0-9]+");
		// Matcher encaja = patron.matcher("$&aabmano%&/lanooabmanolob·&");
		// System.out.println(encaja.find());
		//		
		// Pattern p = Pattern.compile("[^A-Za-z0-9\\.\\@_\\-~#]+");
		// Matcher m = p.matcher("demo13221sad");
		// StringBuffer sb = new StringBuffer();
		// boolean resultado = m.find();
		// System.out.println(resultado);
		// boolean caracteresIlegales = false;
		//
		// while(resultado) {
		// caracteresIlegales = true;
		// m.appendReplacement(sb, "");
		// resultado = m.find();
		// }
		//
		// // Añade el ultimo segmento de la entrada a la cadena
		// m.appendTail(sb);
		//
		// System.out.println( sb.toString());
		//
		// if (caracteresIlegales) {
		// System.out.println("La cadena contiene caracteres ilegales");
		// }

		// http://74.125.47.132/search?q=cache:MdcEin5NPbAJ:www.programacion.com/java/articulo/expresionesreg/+java+validar+alfanumerico&cd=1&hl=es&ct=clnk&gl=pe

		// Usuario u = new Usuario();
		// u.setId(10);
		// u.setUsuario("ebenites");
		// Persona p = new Persona();
		// p.setNomuno("Erick");
		// p.setNomdos("Saul");
		// p.setUsuario(u);
		// u.setPersona(p);
		//		
		// System.out.println(u.getPersona().getNomuno());
		//		
		// System.out.println(u.getPersona().getUsuario().getUsuario());
		//		
		// System.out.println(u.getPersona().getUsuario().getPersona().getUsuario().getPersona().getNomdos());

		// ******************* Ejemplo Oscae *****************

		// int nTablas = 10000;
		//		
		// Collection<Collection<Integer>> tablas = new
		// ArrayList<Collection<Integer>>();
		//		
		// List<Integer> nums = new ArrayList<Integer>();
		//		
		// for (int i = 1; i <= 25; i++) {
		// nums.add(i);
		// }
		//		
		// while (tablas.size() < nTablas) {
		//			
		// List<Integer> items = new ArrayList<Integer>();
		// List<Integer> numsTMP = new ArrayList<Integer>(nums);
		// for (int i = 1; i <= 15; i++) {
		// Random r = new Random();
		// int pos = r.nextInt(numsTMP.size());
		// items.add(numsTMP.get(pos));
		// numsTMP.remove(pos);
		// }
		//			
		// boolean yaContiene = false;
		// for (Collection<Integer> tabla : tablas) {
		// int i = 0;
		// yaContiene = true;
		// for (Integer n : tabla) {
		// if(n != items.get(i)){
		// yaContiene = false;
		// break;
		// }
		// i++;
		// }
		// if(yaContiene)break;
		// }
		// if(!yaContiene)
		// tablas.add(items);
		//			
		// }
		//		
		// for (Collection<Integer> items : tablas) {
		// for (Integer n : items) {
		// System.out.print(n+",");
		// }
		// System.out.println();
		// }
		//		
		//	
		// ***************************************************
		/*
		 * Set<String> lista = new TreeSet<String>(); lista.add("Juan");
		 * lista.add("Benjamin"); lista.add("Mario"); lista.add("Luis");
		 * 
		 * for (String string : lista) { System.out.println(string); }
		 */
//		String[] ls = new String[4];
//		ls[0] = "Juan";
//		ls[1] = "Benjamin";
//		ls[2] = "Mario";
//		ls[3] = "Luis";
//
//		boolean hayCambios = true;
//		for (int i = 0; hayCambios; i++) {
//			hayCambios = false;
//			for (int j = 0; j < ls.length - 1; j++) {
//				if (ls[j].compareToIgnoreCase(ls[j + 1]) > 0) {
//					String tmp = ls[j];
//					ls[j] = ls[j + 1];
//					ls[j + 1] = tmp;
//					hayCambios = true;
//				}
//			}
//		}
//		for (int i = 0; i < ls.length; i++) {
//			// System.out.println("|"+ls[i]+"|");
//		}
//
//		String[] Nombre = new String[5];
//		Nombre[0] = "Juan";
//		Nombre[1] = "Benjamin";
//		Nombre[2] = "Mario";
//		Nombre[3] = "Martin";
//		Nombre[4] = "Luis";
//
//		int pos = 0;
//
//		for (int i = 0; i < Nombre.length; i++) {
//			if (Nombre[i].charAt(0) == 'M') {
//				pos = i;
//				for (int j = pos; j < Nombre.length - 1; j++)
//					Nombre[j] = Nombre[j + 1];
//				Nombre[Nombre.length - 1] = " ";
//				i--;
//			}
//
//			else
//				System.out.println("No se encuentra en el vector");
//
//			System.out
//					.println("Los nombres que empiezan con la letra W han sido eliminados");
//		}
//
//		for (int k = 0; k < Nombre.length; k++) {
//			System.out.println("|" + Nombre[k] + "|");
//		}
//
//		Random r = new Random();
//		System.out.println((char) (r.nextInt(27) + 30));

//		
//		System.out.println("Ingrese Letra para listar : "); 
//		
//		char letra = 'M';
//		String[] Nombre = new String[4];
//		Nombre[0]="Moto"; 
//		Nombre[1]="Moto Acuatica"; 
//		Nombre[2]="Televisor";
//		Nombre[3]="Celular"; 
//		
//		int contador = 4;
//		boolean encontrado = false;
//            for(int i=0;i<contador;i++){ 
//                if(Nombre[i].toLowerCase().charAt(0) == letra || Nombre[i].charAt(0) == letra){ 
//                    System.out.println("|"+Nombre[i]);
//                    encontrado = true; 
//                }
//                   
//            } 
//            if(!encontrado)
//                    System.out.println("No se encuentran Productos que empiecen con la letra ingresada!!"); 
  	
		
		
//		GregorianCalendar date = Formato.stringToCalendar("19-06-2009", Constante.FECHA_DIA_MES_ANO);
//		
//		System.out.println(Formato.calendarToString(date, Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG));
//		
//		date.add(GregorianCalendar.SECOND, 86399);
//		
//		System.out.println(Formato.calendarToString(date, Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG));
//		
//date.add(GregorianCalendar.SECOND, 1);
//		
//		System.out.println(Formato.calendarToString(date, Constante.FECHA_DIA_MES_ANO_HORA_MI_SEG));
		
		System.out.println(GregorianCalendar.DAY_OF_WEEK_IN_MONTH);
	}

}
