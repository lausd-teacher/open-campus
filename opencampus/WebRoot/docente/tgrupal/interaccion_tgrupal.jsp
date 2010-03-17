<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@  page import="edu.tecsup.lms.modelo.AulaVirtual"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}'/>
<%
AulaVirtual aula = (AulaVirtual)request.getSession().getAttribute(Constante.AULA_ACTUAL);
 %>
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
			src="<c:out value='${contextPath}'/>/docente/tgrupal/interaccion_tgrupal.js"></script>
	</head>
	
	<body onload="xShowD('filaDocente_'+xGetElementById('sizeFilaDocente').value);">

	<c:set var="tg" value='${requestScope.TRABAJO_GRUPAL}'/>
	<c:set var="aula" value='${sessionScope.aula_actual}' />
	<c:set var="grupo" value='${requestScope.TRABAJO_GRUPAL_MENSAJES}'/>
	


<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;"> 
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="90%">
							<strong>Curso : <%=aula.getNombreCurso()%> </strong>
						</td>
						<td width="5%"><a href="#" class="salir" onClick="window.print()">Imprimir</a> </td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="2%">|</td>						
						<td width="5%">
							<a href="#" class="salir" onclick="window.close()">Cerrar</a>
						</td>
						<td width="5%">
							<a href="#" class="salir" onclick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13" height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
	  
	  <div id="pop_cuerpo">
		<c:choose>
			<c:when test="${tg.fechaActivacion != null}">
				<table width="500" align="center" bgcolor="#FFFFFF" class="tabla01">
			        <CAPTION>
			          Trabajo: <c:out value="${tg.nombreUnidad}"></c:out>
			         </CAPTION>
			        <tr>
						<td width="250" style="padding-left: 5px; padding-top: 5px; padding-bottom: 5px; border-bottom: solid 1px; border-left: solid 1px;">
							<strong>Fecha de activaci&oacute;n : </strong> &nbsp;
							<f:DateToString fecha="${tg.fechaActivacion}" completo="true" />
						</td>
						<td width="250" style="border-bottom: solid 1px; border-right: solid 1px;">
							<strong>Fecha de entrega : </strong> &nbsp;
							<f:DateToString fecha="${tg.fechaEntrega}" completo="true" />
						</td>
					</tr>
				</table>
				
				<p></p>
				
				
				<table width="500" align="center" bgcolor="#FFFFFF" class="tabla01">
		        <CAPTION>
		          	Env&iacute;o y recepci&oacute;n de documentos
		        </CAPTION>
		        
<%--		        ************* LISTA DE MENSAJES ***************--%>
				<f:Constante campo="ROL_CAMPUS_AULAVIRTUAL_RESPONSABLE" var="res" />
				<f:Constante campo="ROL_CAMPUS_AULAVIRTUAL_DOCENTE" var="doc" />
				<f:Constante campo="ROL_CAMPUS_AULAVIRTUAL_MONITOR_DOCENTE" var="mon" />
			
		        <c:set var="size" value="${fn:length(grupo.mensajes)}"></c:set>
		        <c:set var="fila_size" value="${size/2}" ></c:set>
		        <c:set var="filaDocente" value="${1}"></c:set>
		        
		        <tr>
		        	<td>
<%--		        		********** TABLA MENSAJE *************--%>
						
			        	<table width="100%" style="table-layout: fixed;" class="tabla01">
				        	<tr style="background-color: #C5DAF6; cursor: pointer;" onclick="xChangeDisplay('filaDocente_<c:out value='${filaDocente}'/>')">
				        		<td align="center" width="20" style="border-bottom: solid 1px #B0BACB;">
					        		<img src="<c:out value='${contextPath}'/>/img/icon_email.gif" />
				        		</td>
				        		<td style="padding: 5px; white-space: nowrap; border-bottom: solid 1px #B0BACB;">
					        		<span style="float:left;">
						        		<strong>
							        		Enviado por : <c:out value="${tg.publicador.nombreCompletoJsp}" />
						        		</strong>
						        	</span>
					        		<span style="width: 120px; float:right; text-align:right;">
					        			<strong><f:DateToString fecha="${tg.fechaActivacion}" completo="true" /></strong>
					        		</span>
				        		</td>
				        	</tr>
				        	<tr id="filaDocente_<c:out value='${filaDocente}'/>" style="display:none;">
								<td align="center" valign="top" width="20">
									<c:choose>
									<c:when test="${grupo.archivoNombre != null && grupo.archivoNombre != ''}">
										<a href="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/DescargarTrabajo.action?idGrupo=<c:out value="${grupo.idGrupo}" />&filename=<c:out value="${grupo.archivoNombre}" />">
											<img src="<c:out value='${contextPath}'/>/img/icon_download.gif" border="0" alt="<c:out value="${grupo.archivoNombre}" />" />
										</a>
									</c:when>
									<c:otherwise>
										<img src="<c:out value='${contextPath}'/>/img/icon_warning.gif" border="0" alt="Está pendiente el envío del documento" />
									</c:otherwise>
									</c:choose>
				        		</td>
				        		<td style="padding: 5px;">
				        			<div style="text-align: justify; width: 100%;"><c:out value="${tg.descripcion}" /></div>
				        			<br/>
<%--				        		</td>--%>
<%--				        	</tr>--%>
<%--			        	</table>--%>
				        	
<%--		        		********** FIN TABLA MENSAJE *************--%>

		        <c:forEach items="${grupo.mensajes}" var="mensaje" varStatus="status">
		       	
		        <c:choose>
        			<c:when test="${mensaje.usuarioEmisor.rol == res || 
        							mensaje.usuarioEmisor.rol == doc ||
        							mensaje.usuarioEmisor.rol == mon}">
		        		<c:set var="docente" value="true"></c:set>
		        	</c:when>
		        	<c:otherwise>
		        		<c:set var="docente" value="false"></c:set>
		        	</c:otherwise>
        		</c:choose>
        		
<%--		        		********** TABLA MENSAJE *************--%>
					
					<c:choose>
			        	<c:when test="${docente}">
				        		</td>
				        	</tr>
				        </table>
			        	</c:when>
					</c:choose>
					
			        	<table width="100%" style="table-layout: fixed; border-color: #FFFFFF" class="tabla01">
			        	<c:choose>
			        		<c:when test="${docente}">
			        			<c:set var="filaDocente" value="${filaDocente+1}"></c:set>
			        			<tr style="background-color: #C5DAF6; cursor: pointer;" onclick="xChangeDisplay('filaDocente_<c:out value='${filaDocente}'/>')">
			        		</c:when>
			        		<c:otherwise>
			        			<tr style="background-color: #E7EFF7;">
			        		</c:otherwise>
			        	</c:choose>
				        		<td align="center" width="20" style="border-bottom: solid 1px #B0BACB;">
					        		<c:choose>
						        		<c:when test="${status.count==size && grupo.bandera==1 && grupo.estado != 0}">
						        			<img src="<c:out value='${contextPath}'/>/img/flag.gif" />
						        		</c:when>
						        		<c:otherwise>
						        			<img src="<c:out value='${contextPath}'/>/img/icon_email.gif" />
						        		</c:otherwise>
					        		</c:choose>
				        		</td>
				        		<td style="padding: 5px; white-space: nowrap; border-bottom: solid 1px #B0BACB;">
					        		<span style="float:left;">
						        		<strong>
							        		Enviado por: 
							        		<c:out value="${mensaje.usuarioEmisor.nombreCompletoJsp}" />
						        		</strong>
						        	</span>
					        		<span style="width: 120px; float:right; text-align:right;">
					        			<strong><f:DateToString fecha="${mensaje.fechaCreacion}" completo="true" /></strong>
					        		</span>
				        		</td>
				        	</tr>
				        	<c:choose>
				        		<c:when test="${docente}">
				        			<tr id="filaDocente_<c:out value='${filaDocente}'/>" style="display:none;">
				        		</c:when>
				        		<c:otherwise>
				        			<tr>
				        		</c:otherwise>
			        		</c:choose>
								<td align="center" valign="top" width="20">
									<c:if test="${mensaje.archivoNombre != null && mensaje.archivoNombre != ''}">
										<a href="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/DescargarTrabajo.action?idGrupo=<c:out value="${grupo.idGrupo}" />&filename=<c:out value="${mensaje.archivoNombre}" />">
											<img src="<c:out value='${contextPath}'/>/img/icon_download.gif" border="0" alt="<c:out value='${mensaje.archivoNombre}' />"/>
										</a>
									</c:if>
				        		</td>
				        		<td style="padding: 5px;">
				        			<div style="text-align: justify; width: 100%;"
				        			<c:if test="${status.count==size}">
				        				id="descripcionHTML"
				        			</c:if>
				        			><c:out value="${mensaje.descripcion}" /></div>
<%--				        		</td>--%>
<%--				        	</tr>--%>
<%--					      </table>--%>
							<c:choose>
					        	<c:when test="${!docente}">
						        		</td>
						        	</tr>
						        </table>
					        	</c:when>
					        	<c:otherwise>
			        				<br/>
			        			</c:otherwise>
							</c:choose>
					        
					        	<c:if test="${status.count==size}">
					        			<c:set var="ultimoDocente" value="${mensaje.usuarioEmisor.idMatricula}"></c:set>
					        	</c:if>
			        	
<%--		        		********** FIN TABLA MENSAJE *************--%>
			
		        </c:forEach>
		        				<input type="hidden" id="sizeFilaDocente" value="<c:out value='${filaDocente}'/>">
					        	</td>
					        </tr>	
					   </table>
		        </td>
		      </tr>
		      
<%--		        		********** FILA BOTONES *************--%>    


		      <tr>
		        	<td>
		        		<span style="float:left;">
		        			<input type="button" onclick="document.location.href='<c:out value='${contextPath}' />/aulavirtual/tgrupal/Cargar.action?cmd=return'" class="form_button" value="Regresar" />
              			</span>
              			<span style="float:right; text-align:right;">
	              			
	              				<input type="button" class="form_button"
	              				<c:choose>
	              					<c:when test="${grupo.bandera==2 && aula.idMatricula==ultimoDocente}">
	              						onclick="mostrarFormModMensaje()" value="Editar"
	              					</c:when>
	              					<c:otherwise>
	              						onclick="mostrarFormNuevoMensaje()"  value="Nuevo"
	              					</c:otherwise>
	              				</c:choose>
	              				<c:if test="${grupo.estado == 0}">
	              					style="display:none;"
	              				</c:if>
	              				/>
              			</span>
              		</td>
		        </tr>
		        
<%--		        		********** FIN FILA BOTONES *************--%>  
<%--		        		********** FORM MENSAJE *****************--%>  
		      <tr id="mensaje" style="display:none;">
		        	<td>
						<form method="post" enctype="multipart/form-data" onsubmit="return validar();"
						action="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/EnviarMensaje.action">
							<table width="495" class="tabla01" style="background-color: #e7efff">
								<tr>
									<td width="50" style="padding-left: 5px;">
										<strong>Archivo: </strong>
									</td>
									<td style="padding-right: 5px;" width="150">
										<input type="file" id="uploadForm" class="form_button" style="text-align: left;" name="file" size="30" />
										<input type="hidden" name=idGrupo value="<c:out value='${grupo.idGrupo}'/>">
									</td>
									<td width="295">
										<small><b>Tamaño Max.: 5 MB</b></small>
									</td>
								</tr>
					
								<tr>
									<td colspan="3" style="padding-left: 5px;">
										<strong>Comentario adicional : </strong>
									</td>
								</tr>
					
								<tr>
									<td colspan="3" style="padding-left: 5px;">
										<textarea rows="4" cols="58" id="form_descripcion" onkeydown="cuentaCaracteres(this)" onkeyup="cuentaCaracteres(this)" name="descripcion"></textarea>
									</td>
								</tr>
								<tr>
									<td colspan="3" style="padding-right: 5px; padding-left: 5px;">
										<span style="float:left;">
											<input type="button" value="Cancelar" onclick="xHideD('mensaje')" class="form_button"/>
										</span>
										<span style="float:right; text-align:right;">
											<input type="text" id=form_numCaracteres size=4 class="form_text" readonly> / 500&nbsp;&nbsp;&nbsp;&nbsp;
											<input type="submit" id="form_button" class="form_button" value="&nbsp;Enviar&nbsp;"/>
										</span>
									</td>
								</tr>
							</table>
						</form>
		        	</td>
		        </tr>
		      
<%--		        		********** FIN FORM MENSAJE *****************--%> 
   
			</table>
				
			</c:when>
			<c:otherwise>
				<center><i>A&uacute;n no se ha publicado un trabajo para esta unidad.</i></center>
			</c:otherwise>
		</c:choose>
		
  	</div>  
		<div id="pie">
			<p class="pie">
				&nbsp;
			</p>
		</div>
	</div>
</body>
</html>
