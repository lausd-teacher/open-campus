package edu.tecsup.lms.util;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tecsup.lms.excepcion.ServiceException;
import edu.tecsup.lms.modelo.correo.Mensaje;

public class SendMail {

	private static final long serialVersionUID = 7380320057074916904L;

	private static Log log = LogFactory.getLog(new SendMail().getClass());

	private Message mensaje;

	private static final String TITULOBUZON = "Mensaje del Campus Virtual de Tecsup";

	private static final String TITULOCURSO = "Inicio de Curso";

	public SendMail() {
		Properties propiedades = new Properties();
		propiedades.put("mail.transport.protocol", "smtp");
		propiedades.put("mail.smtp.host", "localhost");
		propiedades.put("mail.smtp.port", "25");
		//propiedades.put("mail.smtp.auth", "false");
		//Authenticator auth = new MiAuthenticator();
		//Session mailSession = Session.getInstance(propiedades, auth);
		Session mailSession = Session.getInstance(propiedades);
		mensaje = new MimeMessage(mailSession);
	}

	//******** Envio de correos desde el buzon del campus para la gente VIP *******//
	
	public void envioBuzon(String to, Mensaje mensaje) throws ServiceException {
		try {
			redireccionCorreoBuzon(to, TITULOBUZON, 
					cabezeraCorreo(mensaje.getUsuario()) 
					+ correoReenvio(mensaje.getUsuario_envio(), mensaje.getTitulo(), Formato.getStringDeDate(new GregorianCalendar()), mensaje.getContenido()) 
					+ finalCorreo());
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException(e.toString());
		}
	}
	
	//----------
	private void redireccionCorreoBuzon(String to, String subject,
			String contenido) throws Exception {
		mensaje.setFrom(new InternetAddress(Constante.DIRECCION_CORREO_SALIENTE));
		mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		mensaje.setSentDate(new Date());
		mensaje.setSubject(subject);
		mensaje.setDataHandler(new DataHandler(new ByteArrayDataSource(
				contenido.toString(), "text/html")));
		Transport.send(mensaje);
		log.info("SendMail: redireccionCorreo: Enviando correo a: " + to);
	}
	//-----------
	
	private String correoReenvio(String usuario, String asunto, String fecha,
			String contenido) {
		return "El Señor(ita) "
				+ usuario
				+ " le ha enviado un mensaje al buzón del Campus Virtual de"
				+ " Tecsup.</p></td></tr><tr><td colspan=\"3\"/></tr><tr>"
				+ "<td width=\"10%\" >&nbsp;</td><td width=\"80%\" ><table"
				+ " width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing"
				+ "=\"0\" style=\"border: solid #00A0E4 1px;\"><tr>"
				+ "<td width=\"20%\">&nbsp;</td><td width=\"3%\"/><td "
				+ "width=\"77%\" /></tr><tr><td align=\"right\"><strong style="
				+ "\"font-size: 13px; font-family: Verdana, Arial, "
				+ "Helvetica, sans-serif; \">Asunto</strong>:</td><td/>"
				+ "<td><strong style=\"font-size: 13px; font-family: Verdana,"
				+ " Arial,Helvetica, sans-serif; \">"
				+ asunto
				+ "</strong></td></tr><tr><td align=\"right\">"
				+ "<strong style=\"font-size: 13px; font-family: Verdana, Arial, "
				+ "Helvetica, sans-serif; \">Fecha</strong> :</td><td/><td><strong style="
				+ "\"font-size: 13px;font-family:Verdana,Arial,Helvetica,sans-serif;\">"
				+ fecha
				+ "</strong></td></tr><tr><td align=\"right\" valign=\"top\"><strong style="
				+ "\"font-size: 13px; font-family: Verdana, Arial,"
				+ "Helvetica, sans-serif; \">Contenido</strong> :</td><td/><td valign=\"top\">"
				+ contenido
				+ "</td></tr><tr><td>&nbsp;</td><td/><td/></tr></table></td>"
				+ "<td width=\"10%\" >&nbsp;</td></tr><tr><td colspan=\"3\" /></tr>"
				+ "<tr><td colspan=\"3\"><p align=\"left\">";

	}
	
	//*******************************************************************************************//
	
	//************************ CONSTANCIA ******************************************************//
	
	public void envioInicioClases(String to, String nombre, String usuario,
			String curso, GregorianCalendar fecha1, GregorianCalendar fecha2,
			int cantidad) throws ServiceException {
		log.info("envioBuzon(String to, String subject, String contenido"
				+ ",UsuarioCorreo usuario)");
		try {
			redireccionCorreoCopia(to, TITULOCURSO, 
					cabezeraCorreo(nombre)
					+ correoUsuarioClases(usuario, curso, Formato.getStringDeDate(fecha1), Formato.getStringDeDate(fecha2), String.valueOf(cantidad))
					+ finalCorreo());
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException(e.toString());
		}
	}

	//------------
	private void redireccionCorreoCopia(String to, String subject,
			String contenido) throws Exception {
		mensaje.setFrom(new InternetAddress(Constante.DIRECCION_CORREO_SALIENTE));
		mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		mensaje.setRecipients(Message.RecipientType.CC, InternetAddress.parse(Constante.DIRECCION_CORREO_SALIENTE));
		//mensaje.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("drodriguez@tecsup.edu.pe"));
		
//		mensaje.setFrom(new InternetAddress("ebenites@tecsup.edu.pe"/*Constante.DIRECCION_CORREO_SALIENTE*/));
//		mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse("jflomad@gmail.pe"/*to*/));
//		mensaje.setRecipients(Message.RecipientType.CC, InternetAddress.parse("jflomad@gmail.com"/*Constante.DIRECCION_CORREO_SALIENTE*/));
//		mensaje.setRecipients(Message.RecipientType.BCC, InternetAddress.parse("arsjajrajezkrzk@tecsup.edu.pe"/*"drodriguez@tecsup.edu.pe"*/));
		
		mensaje.setSentDate(new Date());
		mensaje.setSubject(subject);
		mensaje.setDataHandler(new DataHandler(new ByteArrayDataSource(
				contenido.toString(), "text/html")));
		Transport.send(mensaje);
		log.info("SendMail: redireccionCorreo: Enviando correo a: " + to);
	}
	//-----------
	
	public void envioInicioClasesNuevo(String to, String nombre,
			String usuario, String clave, String curso,
			GregorianCalendar fecha1, GregorianCalendar fecha2, int cantidad)
			throws ServiceException {
		log.info("envioInicioClasesNuevo(String to, String nombre,"
				+ "String usuario, String clave, String curso,"
				+ "GregorianCalendar fecha1, GregorianCalendar fecha2)");
		try {
			redireccionCorreoCopia(to, TITULOCURSO, 
					cabezeraCorreo(nombre)
					+ correoUsuarioClasesNuevo(usuario, clave, curso, Formato.getStringDeDate(fecha1), Formato.getStringDeDate(fecha2), String.valueOf(cantidad))
					+ finalCorreo());
		} catch (Exception e) {
			log.error(e.toString());
			throw new ServiceException(e.toString());
		}
	}
	
	private String correoUsuarioClasesNuevo(String usuario, String clave,
			String curso, String fecha1, String fecha2, String cantidad) {
		return "Nos es grato darle la bienvenida a TECSUP VIRTUAL e informarle que ya es integrante de nuestra comunidad virtual. Le recordamos que, al ser miembro de ella, usted puede participar en nuestras diversas áreas como Foros, Cafetería, Chat, Noticias y el Buzón de correo interno. </p> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> A la vez confirmamos su inscripci&oacute;n al curso virtual de: <strong> "+curso+" </strong>. </p> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> Para acceder al Campus, debe ingresar a nuestra p&aacute;gina Web: <strong><a href=\"http://www.tecsup.edu.pe\"> www.tecsup.edu.pe</a> </strong>, diríjase a la opción COMUNIDADES/Alumnos, donde encontrar&aacute; una ventana de acceso que le solicitar&aacute; los siguientes datos:\" <br /> </p> </td> </tr> <tr> <td width=\"25%\"> &nbsp; </td> <td width=\"50%\" style=\"padding: 10px;\"> <table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" style=\"border: solid #00A0E4 1px;\"> <tr> <td style=\"font-size: 14px; font -family: Verdana, Arial, Helvetica, sans-serif;\"> <div align=\"right\"> <strong>Usuario</strong>: </div> </td> <td> &nbsp; </td> <td style=\"font-size: 14px; font -family: Verdana, Arial, Helvetica, sans-serif;\"> <strong> "+usuario+" </strong> </td> </tr> <tr> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <div align=\"right\"> <strong>Clave</strong> : </div> </td> <td> <div align=\"center\"></div> </td> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <strong> "+clave+" </strong> </td> </tr></tr><tr><td colspan=\"3\"><h6>Ingresar datos de acceso en min&uacute;sculas y sin espacios en blanco.</h6></td></tr> </table> </td> <td width=\"25%\"> &nbsp; </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> La indicamos la programación del curso: </p> </td> </tr> <tr> <td> &nbsp; </td> <td style=\"padding: 10px;\"> <table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" style=\"border: solid #00A0E4 1px;\"> <tr> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <div align=\"right\"> <strong>Inicio de Curso</strong>: </div> </td> <td> &nbsp; </td> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <strong> "+fecha1+" </strong> </td> </tr> <tr> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <div align=\"right\"> <strong>Fin de Curso</strong>: </div> </td> <td> <div align=\"center\"></div> </td> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <strong> "+fecha2+" </strong> </td> </tr> <tr> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <div align=\"right\"> <strong>Duraci&oacute;n</strong>: </div> </td> <td> &nbsp; </td> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-se rif;\"> <strong> "+cantidad+" semanas</strong> </td> </tr> </table> </td> <td> &nbsp; </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> Al ingresar a su aula virtual, encontrar&aacute; el material de la siguiente manera: </p> </td> </tr> <tr> <td colspan=\"3\"> <ul style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\"> <li type=\"square\"> Los textos en formato PDF (Ver con Adobe Acrobat Reader, si no lo tuviera instalado, ir a la opci&oacute;n Utilitarios) <li type=\"square\"> Los repasos en formato HTML. </ul> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> Esperamos que el desarrollo del curso sea de su agrado y lo invitamos a interactuar en los diálogos de su curso. </p> </td> </tr> <tr> <td colspan=\"3\"> <ul style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\"> <li type=\"square\"> La certificación del curso ser&aacute; enviada a su domicilio si Ud. termina con la condición de APTO. Para ello debe tener un promedio aprobatorio en sus evaluaciones y sus trabajos (si el curso los tuviera). <li type=\"square\"> Si el curso que lleva tiene programado trabajos individuales y/o grupales debe de presentarlos en la <b>fecha indicada</b>. </ul> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> Para que conozca las opciones del Campus Virtual, le recomendamos que lea la Gu&iacute;a del Estudiante(la encontrar&aacute; en la p&aacute;gina inicial del Campus) </p> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> (Pedimos nos confirme la recepci&oacute;n de los datos por este medio) ";
	}

	private String correoUsuarioClases(String usuario, String curso,
			String fecha1, String fecha2, String cantidad) {
		return "Nos es grato darle la bienvenida a TECSUP VIRTUAL e informarle que ya es integrante de nuestra comunidad virtual. Le recordamos que, al ser miembro de ella, usted puede participar en nuestras diversas áreas como Foros, Cafetería, Chat, Noticias y el Buzón de correo interno. </p> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> A la vez confirmamos su inscripci&oacute;n al curso virtual de: <strong> "+curso+" </strong>. </p> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> Para acceder al Campus, debe ingresar a nuestra p&aacute;gina Web: <strong><a href=\"http://www.tecsup.edu.pe\"> www.tecsup.edu.pe</a> </strong>, diríjase a la opción COMUNIDADES/Alumnos, donde encontrar&aacute; una ventana de acceso que le solicitar&aacute; los siguientes datos:\" <br /> </p> </td> </tr> <tr> <td width=\"25%\"> &nbsp; </td> <td width=\"50%\" style=\"padding: 10px;\"> <table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" style=\"border: solid #00A0E4 1px;\"> <tr> <td style=\"font-size: 14px; font -family: Verdana, Arial, Helvetica, sans-serif;\"> <div align=\"right\"> <strong>Usuario</strong>: </div> </td> <td> &nbsp; </td> <td style=\"font-size: 14px; font -family: Verdana, Arial, Helvetica, sans-serif;\"> <strong> "+usuario+" </strong> </td> </tr><tr><td colspan=\"3\"><h6>Ingresar datos de acceso en min&uacute;sculas y sin espacios en blanco.</h6></td></tr> </table> </td> <td width=\"25%\"> &nbsp; </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> La indicamos la programación del curso: </p> </td> </tr> <tr> <td> &nbsp; </td> <td style=\"padding: 10px;\"> <table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" style=\"border: solid #00A0E4 1px;\"> <tr> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <div align=\"right\"> <strong>Inicio de Curso</strong>: </div> </td> <td> &nbsp; </td> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <strong> "+fecha1+" </strong> </td> </tr> <tr> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <div align=\"right\"> <strong>Fin de Curso</strong>: </div> </td> <td> <div align=\"center\"></div> </td> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <strong> "+fecha2+" </strong> </td> </tr> <tr> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <div align=\"right\"> <strong>Duraci&oacute;n</strong>: </div> </td> <td> &nbsp; </td> <td style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-se rif;\"> <strong> "+cantidad+" semanas</strong> </td> </tr> </table> </td> <td> &nbsp; </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> Al ingresar a su aula virtual, encontrar&aacute; el material de la siguiente manera: </p> </td> </tr> <tr> <td colspan=\"3\"> <ul style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\"> <li type=\"square\"> Los textos en formato PDF (Ver con Adobe Acrobat Reader, si no lo tuviera instalado, ir a la opci&oacute;n Utilitarios) <li type=\"square\"> Los repasos en formato HTML. </ul> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> Esperamos que el desarrollo del curso sea de su agrado y lo invitamos a interactuar en los diálogos de su curso. </p> </td> </tr> <tr> <td colspan=\"3\"> <ul style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\"> <li type=\"square\"> La certificación del curso ser&aacute; enviada a su domicilio si Ud. termina con la condición de APTO. Para ello debe tener un promedio aprobatorio en sus evaluaciones y sus trabajos (si el curso los tuviera). <li type=\"square\"> Si el curso que lleva tiene programado trabajos individuales y/o grupales debe de presentarlos en la <b>fecha indicada</b>. </ul> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> Para que conozca las opciones del Campus Virtual, le recomendamos que lea la Gu&iacute;a del Estudiante(la encontrar&aacute; en la p&aacute;gina inicial del Campus) </p> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> (Pedimos nos confirme la recepci&oacute;n de los datos por este medio) ";
	}
	
	//*******************************************************************************************//
		
	private String cabezeraCorreo(String nombre) {
		return "<html xmlns=\"http://www.w3.org/1999/xhtml\"> <head> <meta http-equiv=\"Content-Type\" content=\"text/html;charset=ISO-8859-1\" /> <title>Tecsup Virtual</title> </head> <body bgcolor=\"#EAEBED\" style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <table width=\"750\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td> <table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"> <tr> <td colspan=\"4\" height=\"50\" align=\"right\" bgcolor=\"#00A2E3\"> <img src=\"http://www.tecsup.edu.pe/campus/img/logo_index.jpg\" width=\"255\" height=\"51\" /> </td> </tr> <tr> <td colspan=\"4\" style=\"background- color: #FFFFFF\"> <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"2\" bgcolor=\"#FFFFFF\"> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: left;\" align=\"left\"> <strong>Estimado(a)&nbsp; "+nombre+" :</strong> </p> </td> </tr> <tr> <td colspan=\"3\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\" align=\"left\"> ";

	}
	
	private String finalCorreo() {
		return "</p> </td> </tr> <tr> <td colspan=\"3\"> <div align=\"left\"> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif; text-align: justify;\"> Cualquier otra consulta, no dude en comunicarse por este medio o por v&iacute;a telef&oacute;nica al 317-3907 anexo 4005. </p> <p style=\"font-size: 14px; font-family: Verdana, Arial, Helvetica, sans-serif;\"> <br /> Saludos cordiales <br /> Milagros Vicente <br /> Administrador del Campus Virtual </p> </div> </td> </tr> </table> </td> </tr> <tr> <td colspan=\"4\"> <img src=\"http://www.tecsup.edu.pe/campus/img/abajo_mensaje.jpg\" width=\"750\" height=\"25\" /> </td> </tr> <tr> <td colspan=\"4\" height=\"13\" bgcolor=\"#00A2E3\" /> </tr> <tr> <td height=\"4\" bgcolor=\"#00A0E4\"> <div align=\"center\"> <span style=\"color: #FFFFFF; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 14px;\"><strong>TECSUP N°1&nbsp;&nbsp;&nbsp;&nbsp;Av.Cascanueces 2221 - Santa Anita. Tel&eacute;fono: 317-3900</strong> </span> </div> </td> </tr> <tr> <td height=\"5\" colspan=\"4\" bgcolor=\"#00A0E4\"> &nbsp; </td> </tr> </table> </td> </tr> </table> </body> </html> ";
	}
}