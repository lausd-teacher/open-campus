<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><s:text name="titulo.campus.virtual" />
</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript" 
			src='<%=request.getContextPath()%>/js/util.js'></script>
		<script language="javascript" type="text/javascript" 
			src='<%=request.getContextPath()%>/comun/foro/foro_usuario.js'></script>

</head>
	<body onresize="resize()"  onscroll="scrolling()">
		<div id="pop_up" style="width: 840px;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%" style="color:black;">
							<strong>Foros</strong>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.print()">Imprimir</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif"
									width="13" height="13" border="0" />
							</a>
						</td>
						<td width="2%">
							|
						</td>
						<td width="4%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo">
				<c:choose>
				<c:when test="${fn:length(foros)>0}">
					<table width="810" border="0" cellpadding="0" cellspacing="0" >
				    <tr>
					    <td height="15" >
							  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	                            <tr>
	                            	<td align="left" width="145"><input type="button" value="Reglamento del Foro" class="form_button" 
	                              	onclick="verReglamento()" style="width: 140px;"></td>
	                              	<td align="left" bgcolor="#FFFBE8"><strong>Estas son las reglas b&aacute;sicas, por favor, t&oacute;mese un tiempo en leerlas.</strong></td>
	                              <td align="right" width="145"><input type="button" value="Búsqueda Avanzada" class="form_button" 
	                              	onclick="javascript:location.href='<%=request.getContextPath()%>/foro/Buscar.action'" style="width: 140px;"></td>
	                            </tr>
	                          </table>
                        </td>
					  </tr>
					<tr>
						<td>&nbsp;
						</td>
					</tr>
				    <tr>
						  <td height="15">
							  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="bor_tabla">	  
	                            <tr class="fon_color01">
	                              <td height="23" colspan="2" align="center" class="foro_Modulo"><strong>Foros</strong> </td>
	                              <td width="230" align="center" class="foro_Modulo"><strong>Ultimo Tema</strong></td>
	                              <td width="50" align="center" class="foro_Modulo"><strong>Temas</strong> </td>
	                              <td width="50" align="center" class="foro_ModuloAbajo1"><strong>Mensajes</strong> </td>
	                            </tr>
	                            <c:forEach items="${foros}" var="foro" varStatus="status">
	                            
		                            <tr>
		                              <td width="50" height="50" class="foro_bor_der_unid_col_img" align="center"><img src="<%=request.getContextPath()%>/img/foro/iconos_foros/foro_img_<c:out value="${foro.icono}"/>.png" width="48" height="48"></td>
		                              <td class="foro_bor_der_cur_col">
		                              	<c:if test="${foro.yoLoModero}"><div style="position: absolute; left:426px; text-align:right; border: 0px solid;"><img src="<%=request.getContextPath()%>/img/medal.png" alt="Soy el moderador"></div></c:if>
		                              	<table border="0" cellspacing="0" cellpadding="2" width="100%" height="100%">
		                              		<tr>
		                              			<td><strong><a style="color:#44659B;font-weight:bold;" href="<%=request.getContextPath()%>/foro/IngresarForo.action?idForo=<c:out value="${foro.idForo}"/>"><c:out value="${foro.titulo}"/></a></strong></td>
		                              		</tr>
		                              		<tr>
		                              			<td><c:out value="${foro.descripcion}"/></td>
		                              		</tr>
		                              		<tr>
		                              			<td>
		                              				<table border="0" cellspacing="0" cellpadding="0" width="100%">
		                              					<tr>
				                              				<td style="white-space: nowrap;"><strong>Moderador(es):</strong></td>
					                              			<c:forEach items="${foro.moderadores}" var="moderador">
					                              				<td style="white-space: nowrap;">&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${moderador.nombreCompleto}"></c:out>&nbsp;</td>
					                              				<td><img src="<%=request.getContextPath()%>/img/icon_mail_send.gif" border="0" 
					                              				onclick="enviarCorreo('<c:out value="${moderador.idUsuario}"/>','[Acerca del Foro]')" style="cursor: pointer;"></td> 
					                              			</c:forEach>
					                              			<c:if test="${fn:length(foro.moderadores)==0}">
					                              				<td style="white-space: nowrap;">&nbsp;&nbsp;&nbsp;&nbsp;Sin modereador.</td>
					                              			</c:if>
					                              			<td width="100%">&nbsp;</td>
					                              		</tr>
			                              			</table>
		                              			</td>
		                              		</tr>
		                              	</table>
		                              </td>
		                              <td width="230" align="left" class="foro_bor_der_cur_col">
		                              	<c:choose>
			                              	<c:when test="${foro.ultimoTema != null}">
				                              	<table border="0" cellspacing="0" cellpadding="2" width="100%" height="100%">
				                              		<tr>
				                              			<td colspan="2"><div style="overflow: hidden;width: 230px;height: 40px;"><strong><c:out value="${foro.ultimoTema.titulo}" escapeXml="false"/>&nbsp;</strong></div></td>
				                              		</tr>
				                              		<tr>
					                              		<td width="149" align="left" style="background-color: #E5EFF8"><strong><c:out value="${foro.ultimoTema.nombreUsuario}"/></strong>&nbsp;</td>
					                              		<td width="79" align="right" style="background-color: #E5EFF8"><c:out value="${foro.ultimoTema.fechaCreacionToString}"/>&nbsp;</td>
				                              		</tr>
				                              	</table>
			                              	</c:when>
			                              	<c:otherwise>
			                              		&nbsp;
			                              	</c:otherwise>
		                              	</c:choose>
		                              </td>
		                              <td align="center" class="foro_bor_der_cur_col"><c:out value="${foro.totalTemas}"/></td>
		                              <td align="center" class="foro_bor_der_cur_fe"><c:out value="${foro.totalMensajes}"/></td>
		                            </tr>
	                            </c:forEach>
	                          </table>
                          </td>
					  </tr>					
					  
			  	</table>
			  </c:when>
			  <c:otherwise>
			  	<center><h5>A&uacute;n no se le ha asignado ning&uacute;n foro.</h5></center>
			  </c:otherwise>
			  </c:choose>
			</div>			
			<div id="pie">
			  <%@include file="../pie.jsp"%>
			</div>	
	</div>
	
	<div id="blocker" style="display:none; position: absolute; top: 0px; left: 0px; background-color:#ccc; width:0px;height:0px; 
				filter:alpha(opacity=50); -moz-opacity:.50; opacity:.50;"></div>
	
	<div id="help" style="width: 500px; position:absolute; left:0px; top:0px; display: none;">
		<div id="prin_01">
			<table width="100%" border="0" cellspacing="0" cellpadding="3">
				<tr>
					<td width="93%">
						<strong>Reglamento del Foro</strong>
					</td>
					<td width="5%">
						<a href="#" class="salir" onClick="window.print()">Imprimir</a>
					</td>
					<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
								src="<%=request.getContextPath()%>/img/impresora.gif" width="13"
								height="13" border="0" /></a>
					</td>
					<td width="2%">|</td>
					<td width="4%"><a href="#" class="salir" onClick="xHideD('help');xHideD('blocker');">Cerrar</a></td>
					<td width="3%">
						<a href="#" class="salir" onClick="xHideD('help');xHideD('blocker');"> <img
								src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
								height="13" border="0" /> </a>
					</td>
				</tr>
			</table>
	  	</div>
		<div id="pop_cuerpo">
			<table width="100%" align="center" cellpadding="0" cellspacing="0"
				bgcolor="#FFFFFF" class="bor_tabla" border="0">
				<tr>
					<td valign="top" class="textstatic" style="padding: 10px;text-align: justify;" >
						<p>
							El <strong>Campus Virtual de opencampus</strong> les da la bienvenida a este Foro 
							para que puedan expresar sus consultas y dudas sobre temas relacionados
							a cada tópico.
						</p>
						<p>
							Estas son las reglas básicas, por favor, tómese un tiempo en leerlas. 
							Usted debe respetarlas para permitir un buen ambiente de trabajo y desarrollo personal. 
							Las personas que no cumplan con estas reglas pueden recibir sanciones que de acuerdo a la 
							gravedad de la falta que evalúa el Comité de Disciplina de <strong>opencampus</strong> ,  van desde la advertencia, 
							suspensiones de acceso, hasta la expulsión total de <strong>opencampus</strong>.
						</p>
						<p>
							<ol>
								<li>Se debe expresar con propiedad y respeto, NO SE ACEPTA en este foro bajo ningún concepto palabras groseras, vulgares ó insultos.</li> 
								<li>No se puede enviar mensajes Spam (Ejemplo: sólo poner caritas, mensajes sin sentido o mensajes repetidos).</li>
								<li>Está estrictamente prohibido cualquier mensaje o imagen que atente a la moral de las personas.</li>
								<li>El intercambio de opiniones debe llevarse con el debido respeto y tolerancia.</li>
								<li>Se permite la insercci&oacute;n de componentes multimedia (YouTube, Google Video, entre otros) siempre y cuando
								 sean de tamaño normal y no contenga mensajes obscenos o agresivos que hieran la susceptibilidad de los dem&aacute;s usuarios.</li> 
								<li>Se pueden publicar mensajes propios de un estudiante, no vulgares, ni con contenido amenazante o de sentimientos negativos.</li>
								<li>Aquel usuario que intente crear problemas de diversa índole será bloqueado.</li> 
								<li>Opinar acerca del tema que se trate el foro y no tocar temas totalmente ajenos a él.</li>
								<li>Colaborar para que los foros se realicen en armonía y no creando discrepancias que traiga como consecuencia una pelea.</li>
								<li>Poner los mensajes que correspondan en su respectivo foro, programas de otros canales no serán aceptados.</li>
							</ol> 
						</p>
					</td>
				</tr>
			</table>
		</div>
		<div id="pie">
			&nbsp;
		</div>
	</div>
	
	</body>
</html>
