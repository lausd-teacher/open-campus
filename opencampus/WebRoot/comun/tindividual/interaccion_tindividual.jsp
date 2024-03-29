<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="edu.opencampus.lms.modelo.tindividual.TrabajoIndividualMatricula"/>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@  page import="edu.opencampus.lms.modelo.AulaVirtual"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}'/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<c:out value='${contextPath}'/>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/comun/tindividual/interaccion_tindividual.js"></script>
	    
	</head>
	
	<body>


	<c:set var="ti" value='${sessionScope.TRABAJO_INDIVIDUAL}'/>
	<c:set var="aula" value='${sessionScope.usuario_actual.aulaActual}' />
	<c:set var="matricula" value='${requestScope.TRABAJO_MATRICULA_INTERACCIONES}'/>
	


<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;"> 
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="90%">
							<strong>Curso : <c:out value="${usuario_actual.aulaActual.curso.nombre}"></c:out> </strong>
						</td>
						<td width="5%"><a href="#" class="salir" onClick="window.print()">Imprimir</a> </td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>												
						<td width="2%">|</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13" height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
		<div id="pop_cuerpo" style="width: 500px;"> 
		
	  <c:choose>
		<c:when test="${ti.fechaActivacion != null}">
		<c:choose>
		<c:when test="${matricula != null && matricula.usuarioReceptor.usuario.persona.apepaterno != null}">
		
<%--		****** VALIDATION ********--%>
		
			<f:Constante campo="ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE" var="res" />
			<f:Constante campo="ROL_CAMPUS_AULAVIRTUAL_DOCENTE" var="doc" />
			<f:Constante campo="ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE" var="mon" />
			
			<c:if test="${aula.matriculaActual.rol.idRol == res || aula.matriculaActual.rol.idRol == doc || aula.matriculaActual.rol.idRol == mon}">
				<c:set var="rol" value="docente"></c:set>
			</c:if>
			
<%--		******* FIN VALIDATION *******--%>
		
			<c:choose>
			<c:when test="${rol == 'docente'}">
			<table width="500" align="center" bgcolor="#FFFFFF" class="tabla01">
		        <CAPTION style="color: #000000; font-size: 11px;">
		          Trabajo: <c:out value="${ti.nombreUnidad}"></c:out>
		          	<input type="hidden" id="idMatricula" value="<c:out value='${matricula.usuarioReceptor.idMatricula}'/>" />
		         </CAPTION>
		        <tr>
	                <td width="40" align="left"><strong>Expirado: </strong></td>
	                <td width="20" align="center">
						<c:choose>
							<c:when test="${matricula.expirado == 2}">
								<img src="<c:out value='${contextPath}' />/img/expirado.gif" />
							</c:when>
							<c:otherwise>
								--
							</c:otherwise>
						</c:choose>
					</td>
					<td width="310" align="center"> 
						<c:out value='${matricula.usuarioReceptor.usuario.nombreCompleto}'/>
					</td>
	                <td width="40" align="left"><strong>Nota : </strong></td>
	                <td width="40" align="center">
						<input id="nota" value="<c:out value='${matricula.nota}'/>" onMouseOver="seleccionInput(this, true)" onMouseOut="seleccionInput(this, false)"
			          	type="text" class="caja_nota" maxlength="2" onChange="calificarTrabajoEnMatricula()" />
					</td>

		        </tr>
			</table>
			
			<p></p>
			</c:when>
			<c:otherwise>
				<table width="500" align="center" bgcolor="#FFFFFF" class="tabla01">
		        <tr class="fon_tit_curso"><td height="20" style="font-size: 12px;font-weight: bold;padding-left: 5px;  color: #000000; font-size: 11px;" colspan="2">
		          Trabajo: <c:out value="${ti.nombreUnidad}"></c:out>
		         </td></tr>
		        <tr>
	                <td width="250" align="center">
						<b>Fecha de Activaci&oacute;n:</b> <f:DateToString fecha="${ti.fechaActivacion}" completo="true" />
					</td>
	                <td width="250" align="center">
						<b>Fecha de Entrega:</b> <f:DateToString fecha="${ti.fechaEntrega}" completo="true" />
					</td>
		        </tr>
			</table>
			
			<p></p>
			</c:otherwise>
			</c:choose>
			
			<table width="500" align="center" bgcolor="#FFFFFF" class="tabla01">
		        <tr class="fon_tit_curso"><td height="20" style="font-size: 12px;font-weight: bold; padding-left: 5px; color: #000000; font-size: 11px;" colspan="2">
		          	Env&iacute;o y Recepci&oacute;n de Documentos
		        </td></tr>
		        
		        <tr class="tabla01_subencabezado">
		        	<td colspan="2">
		        		<c:if test="${rol == 'docente'}">
			        		<span style="width: 100px; float:left;">
			        			<input type="button" onClick="document.location.href='<c:out value='${contextPath}' />/aulavirtual/tindividual/Cargar.action?idUnidad=<c:out value="${ti.idUnidad}"/>'" class="form_button" value="Regresar" />
	              			</span>
	              		</c:if>	
              			<span style="width: 100px; float:right; text-align:right;">
	              			
	              				<input type="button" class="form_button" id="form_button"
	              				<c:choose>
	              					<c:when test="${rol=='docente' && matricula.estado=='1' || rol != 'docente' && matricula.estado!='1'}">
	              						onclick="mostrarFormNuevoMensaje()"  value="Responder"	
	              					</c:when>
	              					<c:when test="${rol=='docente' && matricula.estado=='2' || rol != 'docente' && matricula.estado=='1'}">
	              						onclick="mostrarFormModMensaje()" value="Editar"
	              					</c:when>
	              				</c:choose>
	              				<c:if test="${matricula.nota != null}">
	              					style="display:none;"
	              				</c:if>
	              				/>
	              			
              			</span>
              		</td>
		        </tr>
		        
		        <tr>
		        	<td colspan="2">
		        		<div id="mensaje" style="display:none;">
							<form method="post" enctype="multipart/form-data" onsubmit="return validar();"
							<c:choose>
              					<c:when test="${rol=='docente'}">
              						action="<c:out value='${contextPath}'/>/aulavirtual/tindividual/EnviarMensajeDeDocente.action"
              					</c:when>
              					<c:otherwise>
              						action="<c:out value='${contextPath}'/>/aulavirtual/tindividual/EnviarMensajeDeEstudiante.action"
              					</c:otherwise>
              				</c:choose>
							>
								<table width="100%" class="tabla01" style="background-color: #e7efff" cellpadding="2">
									<tr>
										<td width="18%">
											<strong>Archivo: &nbsp;<font color="red">(*)</font></strong>
										</td>
										<td>
											<span style="width: 350px; float:left;">
<%--												<iframe src="<c:out value='${contextPath}'/>/estudiante/tindividual/CargarForm.action" id="uploadForm" frameborder="0" marginwidth="0" width="200" marginheight="0" height="22" scrolling='no'></iframe>--%>
													<input type="file" id="uploadForm" class="form_text" name="file" size="40" />
<%--													<input type="text" size="40" id="txtUploadForm" readonly class="form_text" />--%>
<%--													<input type="button" onclick="openExplorer()" value="Examinar" class="form_button" />--%>
											</span>
											<input type="hidden" name=idMatricula value="<c:out value='${matricula.usuarioReceptor.idMatricula}'/>">
											<input type="hidden" name=idUnidad value="<c:out value='${ti.idUnidad}'/>">
											<input type="hidden" name=idMensaje value="<c:out value='${fn:length(matricula.interacciones)}'/>">
											<span style="width: 30px; float:right; text-align:right;">
						        				<input type="button" value="X" onClick="xHideD('mensaje')" style="width:20px; height:20px;" class="form_button" />
						        			</span>
										</td>
									</tr>
						
									<tr>
										<td colspan="2">
											<strong>Descripci&oacute;n adicional : </strong>
										</td>
									</tr>
						
									<tr>
										<td colspan="2">
											<textarea rows="4" cols="93" id="form_descripcion" onKeyDown="cuentaCaracteres(this)" onKeyUp="cuentaCaracteres(this)" name="descripcion"></textarea>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<span style="width: 230px; float:left;">
												<b><font color="red">(*)</font> El archivo debe ser menor a 5 MiB.</b>
											</span>
											<span style="width: 230px; float:right; text-align:right;">
												<input type="text" id=form_numCaracteres size=4 class="form_text"> / 500&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="submit" id="form_button2" class="form_button" value="&nbsp;Enviar&nbsp;"/>
											</span>
										</td>
									</tr>
								</table>
							</form>
						</div>
		        	</td>
		        </tr>
		        
<%--		        ************* LISTA DE MENSAJES ***************--%>
		        <c:set var="fila" value="1" ></c:set>
		        <c:set var="size" value="${fn:length(matricula.interacciones)}"></c:set>
		        <c:set var="fila_size" value="${size/2}" ></c:set>
<%--		        <fmt:formatNumber value="${(size+1)/2}" maxFractionDigits="0" var="fila_size" />--%>
		        <c:forEach items="${matricula.interacciones}" var="interaccion" varStatus="status">
		       
		        <c:choose>
        			<c:when test="${matricula.usuarioReceptor.idMatricula == interaccion.usuarioEmisor.idMatricula}">
		        		<c:set var="docente" value="false"></c:set>
		        	</c:when>
		        	<c:otherwise>
		        		<c:set var="docente" value="true"></c:set>
        			</c:otherwise>
        		</c:choose>
		        
		        <tr>
		        	<td

		       			 <c:choose>
		        			<c:when test="${!docente}">
				        		width="20"></td>
				        		 <td
				        	</c:when>
				        	<c:otherwise>
				        		colspan="2"
		        			</c:otherwise>
		        		</c:choose>
		        
		        	>
<%--		        		********** TABLA MENSAJE *************--%>
						<div <c:if test='${fila<fila_size && !docente}'>style="display:none;"</c:if>
						<c:if test='${!docente}'>id="fila_<c:out value='${fila}'/>" <c:set var="fila" value='${fila+1}' /></c:if> >
				        	<table width="100%" style="table-layout: fixed; border-color: #FFFFFF" class="tabla01">
					        	<tr
									<c:choose>
										<c:when test="${docente}">
											style="background-color: #E7EFF7;cursor: pointer;" onclick="ocultarInteraccion(<c:out value="${fila}"/>)"
										</c:when>
										<c:otherwise>
							        		style="background-color: #E7EFF7;"
					        			</c:otherwise>
									</c:choose>
								>
					        		<td align="center" width="20">
					        		<c:if test="${matricula.nota == null && status.count==size && ((matricula.estado==1 && rol=='docente') || ((matricula.estado==2 || matricula.estado==0) && rol==null))}">
					        			<img src="<c:out value='${contextPath}'/>/img/flag.gif" />
					        		</c:if>
					        		</td>
					        		<td style="padding: 5px; white-space: nowrap;"
					        		<c:choose>
					        			<c:when test="${!docente}">
							        		width="430"
							        	</c:when>
							        	<c:otherwise>
							        		width="453"
					        			</c:otherwise>
					        		</c:choose>
					        		>
						        		<span style="width: 300px; float:left;">
							        		<strong>
								        		Enviado por: 
								        		<c:out value="${interaccion.usuarioEmisor.usuario.persona.apepaterno}" />
								        		<c:out value="${interaccion.usuarioEmisor.usuario.persona.apematerno}" />,
								        		<c:out value="${interaccion.usuarioEmisor.usuario.persona.nomuno}" />
								        		<c:out value="${interaccion.usuarioEmisor.usuario.persona.nomdos}" />
							        		</strong>
							        	</span>
						        		<span style="width: 120px; float:right; text-align:right;">
						        			<strong><f:DateToString fecha="${interaccion.fechaCreacion}" completo="true" /></strong>
						        		</span>
					        		</td>
					        	</tr>
					        	<tr>
					        		<td colspan="2">
					        			<div <c:if test='${fila<fila_size && docente}'>style="display:none;"</c:if>
					        			<c:if test="${docente}">id="celda_<c:out value="${fila}"/>" </c:if> >
								        	<table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;" >
								        	<tr>
												<td align="center" valign="top" width="20">
													<c:choose>
														<c:when test="${status.count==1}">
															<c:choose>
																<c:when test="${ti.interaccion.archivoNombre == null}">
																	<img src="<c:out value='${contextPath}'/>/img/icon_download_inactivo.gif" alt="Recurso no disponible" border="0"/>
																</c:when>
																<c:when test="${ti.interaccion.archivoNombre == 'DEFAULT'}">
																	<a href="<c:out value='${contextPath}'/>/aulavirtual/tindividual/DescargarTrabajoPredeterminado.action?idUnidad=<c:out value='${ti.idUnidad}'/>"
																	><img src="<c:out value='${contextPath}'/>/img/icon_download.gif" alt="Descargar" border="0"/></a>
																</c:when>
																<c:otherwise>
																	<a href="<c:out value='${contextPath}'/>/aulavirtual/tindividual/DescargarTrabajo.action?filename=<c:out value='${ti.interaccion.archivoNombre}'/>"
																	><img src="<c:out value='${contextPath}'/>/img/icon_download.gif" alt="Descargar" border="0"/></a>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:if test="${interaccion.archivoNombre != null}">
																<a href="<c:out value='${contextPath}'/>/aulavirtual/tindividual/DescargarTrabajo.action?filename=<c:out value='${interaccion.archivoNombre}'/>"
																><img src="<c:out value='${contextPath}'/>/img/icon_download.gif" alt="Descargar" border="0"/></a>
															</c:if>
														</c:otherwise>
													</c:choose>
								        		</td>
								        		<td style="padding: 5px;">
								        			<div style="text-align: justify"
								        			<c:if test="${status.count==size}">
								        				id="descripcionHTML"
								        			</c:if>
								        			><c:if test="${status.count==1 && ti.interaccion.archivoNombre != null}"><b style="cursor: pointer;"
								        				<c:choose>
															<c:when test="${ti.interaccion.archivoNombre == 'DEFAULT'}">
																onclick="window.document.location ='<c:out value='${contextPath}'/>/aulavirtual/tindividual/DescargarTrabajoPredeterminado.action?idUnidad=<c:out value='${ti.idUnidad}'/>'"
															</c:when>
															<c:otherwise>
																onclick="window.document.location ='<c:out value='${contextPath}'/>/aulavirtual/tindividual/DescargarTrabajo.action?filename=<c:out value='${ti.interaccion.archivoNombre}'/>'"
															</c:otherwise>
														</c:choose>
								        			>: <u>Descargar Trabajo</u>. </b>&nbsp;</c:if>
								        			<% pageContext.setAttribute("carriagereturn","\n"); %>
								        			<c:set var="interaccion" value='${fn:replace(interaccion.descripcion, "<", "&lt;")}'></c:set>
								        			<c:set var="interaccion" value='${fn:replace(interaccion, ">", "&gt;")}'></c:set>
								        			<c:out value='${fn:replace(interaccion, carriagereturn, "<br/>")}' escapeXml="false" /></div>
								        		</td>
								        	</tr>
								        	</table>
							        	</div>
					        		</td>
					        	</tr>
					        	
				        	</table>
				        </div>
<%--		        		********** FIN TABLA MENSAJE *************--%>
				        	
				        	
		        	</td>
		        </tr>
		        </c:forEach>
		        
			</table>
			</c:when>
		<c:otherwise>
			<center><h5>El Trabajo se activar&aacute; el <f:DateToString fecha="${ti.fechaActivacion}" completo="true" /></h5></center>
		</c:otherwise>
		</c:choose>
		</c:when>
		<c:otherwise>
			</br><center><h5>A&uacute;n no se ha publicado un trabajo para esta unidad.</h5></center>
		</c:otherwise>
		</c:choose>
  	</div>  
		<div id="pie">
			<p class="pie">&nbsp;
				
			</p>
		</div>
	</div>
</body>
</html>
