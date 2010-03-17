<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.modelo.AulaVirtual"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
	Usuario usuario = (Usuario) session.getAttribute(Constante.USUARIO_ACTUAL);
	AulaVirtual aula = usuario.getAulaActual();
	int rol = aula.getMatriculaActual().getRol().getIdRol();
	String contexto = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title>Campus Virtual de Tecsup</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/estilos/estilos.css" type="text/css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
		<script type="text/javascript" src='<%=request.getContextPath()%>/js/vitrina.js'></script>
		<script type="text/javascript" src='<%=request.getContextPath()%>/js/jComponente.js'></script>
		<script type="text/javascript">	
		function publicarAgain(idPublicacion){
			if (confirm("¿Desea volver a publicar el aviso?")){
				var ajax = nuevoAjax();
				var url = xGetContextPath()+"/aulavirtual/PublicarAgain.action";				
				ajax.open("POST", url, true);
				ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				ajax.send("idPublicacion="+idPublicacion);
				return true;
			}else{
				return false;
			}			
		}		
	</script>
		
	</head>

	<body>
		<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong><s:text name="aula.alumno.pop_up.curso"/> <c:out value="${usuario_actual.aulaActual.curso.nombre}"></c:out> </strong></td>
						<td width="5%"><a href="#" class="salir" onClick="window.print()"><s:text name="aula.alumno.pop_up.imprimir"/></a> </td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="2%">|</td>
						<td width="4%"><a href="#" class="salir" onClick="window.close()"><s:text name="aula.alumno.pop_up.cerrar"/></a></td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>						</td>
					</tr>
				</table>
		  </div>
			<div id="pop_cuerpo" style="width: 500px;">
				<table width="500" align="center" cellpadding="0" border="0"
					cellspacing="0" bgcolor="#FFFFFF" class="bor_tabla">
					<tr align="left" class="fon_cab">
						<td height="23" colspan="2" class="tit_cab">
							<s:text name="aula.alumno.pop_up.avisos"/>
						</td>
					</tr>
					<tr>
						<%
									if (rol == Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE
									|| rol == Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE) {
						%>
						<td align="right" colspan="2">
							<input name="Submit2" type="button" class="form_button"
								value="<s:text name="aula.alumno.pop_up.avisos.nuevo"/>"
								onClick="document.location.href='<%=contexto%>/aulavirtual/NuevoMensaje.action'" />
						</td>
						<%
						}
						%>
					</tr>

					<%
					int i = 0;
					%>
					<c:forEach items="${PUBLICACIONES}" var="publicacion">
						<tr class="textoAbajo1" >
						  <td width="373" height="23" class="textoAbajo1">
								<a href="#" onClick="expandcontent('sc<%=i%>')"
									class="link_curso"> <strong> 
							  <c:out
											value="${publicacion.titulo}" />
</strong> </a>
						  </td>
							<td width="113" class="textoAbajo1">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td height="17" class="textstatic"
											style="text-decoration:none;">
											<%
														if (rol == Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE
														|| rol == Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE) {
											%>
											<a href="#" class="link_curso"
												onClick="return publicarAgain('<c:out value="${publicacion.idPublicacion}"/>');">
												<s:text name="aula.alumno.pop_up.avisos.volver_publicar"/> <%
											}
											%>
											
										</td>
										<td align="left">
											<%
														if (rol == Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE
														|| rol == Constante.ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE) {
											%>
											<a onClick="return confirmar();"
												href="<%=contexto%>/aulavirtual/EliminarMensaje.action?idPublicacion=<c:out value="${publicacion.idPublicacion}"/>">
												<img src="<%=contexto%>/img/icon_trash.gif" width="13"
													height="15" border="0" /> </a>
											<%
											}
											%>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div id="sc<%=i%>" class="switchcontent"
									style="display: compact;">
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="left" class="titulo2">
										  <td height="5"  bgcolor="#ffffff" colspan="2" valign="top" class="alineacion">&nbsp;</td>
									  </tr>
										<tr align="left" class="titulo2">
											<td height="18"  bgcolor="#ffffff"colspan="2" valign="top" class="alineacion">
												<strong ><s:text name="aula.alumno.pop_up.avisos.enviado_por"/>
												<c:out
														value="${publicacion.usuario.persona.nombreCompleto}" />
											  </strong>										  </td>
										</tr>
										<tr>
										  <td height="18" class="titulo2" bgcolor="#ffffff">
										  <strong  class="alineacion"><s:text name="aula.alumno.pop_up.avisos.fecha_publicacion"/>
													<c:out
														value="${publicacion.fechaToString}" />
											  </strong>	</td>
									  </tr>
										<tr>
											<td class="datosAbajo" bgcolor="#ffffff">&nbsp;
											
																					  </td>
										</tr>
										<tr align="left">
											<td align="left">
												<p>
													<c:out value="${publicacion.contenido}" escapeXml="false" />
												</p>											</td>
										</tr>
								  </table>
								</div>
							</td>
						</tr>
						<%
						i++;
						%>
					</c:forEach>
					<%
					if (i == 0) {
					%>
					<tr>
						<td height="8" colspan="2"></td>
					</tr>
					<tr align="center">
						<td colspan="2">
							<table border="0" class="bor_tabla_unid" width="95%">
								<tr>
									<td align="center" >
										<s:text name="aula.alumno.pop_up.avisos.vacio"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<%
					}
					%>
					<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			  </table>
			</div>
			<div id="pie">
				&nbsp;
			</div>
		</div>
	</body>
</html>
