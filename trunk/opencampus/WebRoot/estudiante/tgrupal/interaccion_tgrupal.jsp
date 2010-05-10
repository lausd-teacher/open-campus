<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@  page import="edu.opencampus.lms.modelo.AulaVirtual"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
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
			src="<c:out value='${contextPath}'/>/estudiante/tgrupal/interaccion_tgrupal.js"></script>
	   
	</head>
	
	<body onLoad="if(e = xGetElementById('sizeFilaDocente')){xShowD('filaDocente_'+xGetElementById('sizeFilaDocente').value);}">

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
			<c:when test="${tg.fechaActivacion != null && requestScope.NoActivate ==  null}">
				<table width="500" align="center" bgcolor="#FFFFFF" class="tabla01" style="table-layout: fixed;">
			        <tr class="fon_tit_curso"><td height="20" style="font-size: 12px;font-weight: bold;" colspan="2">
			          Trabajo: <c:out value="${tg.nombreUnidad}"></c:out>
			         </td>
			        </tr> 
			        <tr>
						<td width="244" style="padding-left: 5px; padding-top: 5px; padding-bottom: 5px; border-left: solid 1px #7EAAD1;">
							<strong>Fecha de Activaci&oacute;n : </strong> &nbsp;
							<f:DateToString fecha="${tg.fechaActivacion}" completo="true" />
						</td>
						<td width="243" style="border-right: solid 1px #7EAAD1;">
							<strong>Fecha de Entrega : </strong> &nbsp;
							<f:DateToString fecha="${tg.fechaEntrega}" completo="true" />
						</td>
					</tr>
					<tr  onmouseover="seleccionBlue(this,true)" onmouseout="seleccionBlue(this,false)">
						<td colspan="2" align="center" onclick="mostrarRenombrarGrupo();"
						style="border-bottom: solid 1px #7EAAD1; border-right: solid 1px #7EAAD1;; border-left: solid 1px #7EAAD1;; white-space: nowrap;">
							<div id="title" style="width:100%; font-size: 18px;"><c:out value="${grupo.nombre}"></c:out></div>
							<div id="titleForm" style="display: none;"><input type="text" id="titleText" maxlength="100"  style="width: 98%;"
							value="<c:out value="${grupo.nombre}"/>" onblur="renombrarGrupo(this);"/></div>
						</td>
					</tr>
				</table>
				<p></p>
				
			<c:choose>
				<c:when test="${grupo.idGrupo != -1}">
				<div id="transparency" style="position: absolute; top: 0px; left: 0px; background-color:#000000; width:0px;height:0px;
				filter:alpha(opacity=50); -moz-opacity:.50; opacity:.50;"></div>
				<div id="div_integrantes" style="position: absolute; top: -100px; left: 0px; width: 300px; visibility: hidden;">
					<table width="100%" align="center" class="tabla01" style="table-layout: fixed; border: solid 1px #7EAAD1; background-color: #FFFFFF;">
						<CAPTION>
				          	Integrantes
				        </CAPTION>
				        <tr>
				        	<td style="padding: 10px;">
				        		<div id="list_integrantes"></div>
				        	</td>
				        </tr>
				        <tr onmouseover="seleccionBlue(this,true)" onmouseout="seleccionBlue(this,false)"
				        	onclick="closeDivIntegrantes()" style="cursor: pointer;">
				        	<td align="center" style="border-top: solid 1px #7EAAD1;">
				        		<strong>Cerrar</strong>
				        	</td>
				        </tr>
					</table>
				</div>
				
				<table width="500" align="center" class="tabla01" style="table-layout: fixed; border: solid 1px #7EAAD1;">
				<tr>
					<td width="30%"></td>
					<td align="center" width="40%" style="padding-left: 10px; cursor: pointer;" onmouseover="seleccionBlue(this,true)" onmouseout="seleccionBlue(this,false)"
						 onclick="javascript:abrirDebate('<c:out value='${contextPath}' />/aulavirtual/debate/Cargar.action?idGrupo=<c:out value='${grupo.idGrupo}' />');">
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td>
									<strong>Debate grupal: &nbsp;</strong>
								</td>
								<td>
									<img src="<c:out value='${contextPath}' />/img/icon_dialog.gif"/>
								</td>
								<td>
									<div id="debate">
							          	<c:if test="${grupo.banderaDebate != 0}">
							          		<img src="<c:out value='${contextPath}' />/img/flag.gif" width="8" height="11" />	
							          	</c:if>
						        	</div>
								</td>
							</tr>
						</table>
					</td>
					<td width="30%" align="right" style="padding-right: 10px; cursor: pointer;" 
					 onclick="javascript:verIntegrantes();" onmouseover="seleccionBlue(this,true)" onmouseout="seleccionBlue(this,false)">
					
						<table cellpadding="0" cellspacing="0" border="0">
							<tr>
								<td>
									<strong>Ver integrantes: &nbsp;</strong>
								</td>
								<td>
									<img src="<c:out value='${contextPath}' />/img/icon_group.gif"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</table>
				<p></p>
				
				<table width="500" align="center" bgcolor="#FFFFFF" class="tabla01">
		        <tr class="fon_tit_curso"><td height="20" style="font-size: 12px;font-weight: bold;" colspan="2">
		          	Env&iacute;o y recepci&oacute;n de documentos
		        </td>
			    </tr> 
		        
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
				        	<tr style="background-color: #C5DAF6; cursor: pointer;" onClick="xChangeDisplay('filaDocente_<c:out value='${filaDocente}'/>')">
				        		<td align="center" width="20" style="border-bottom: solid 1px #B0BACB;">
				        			<c:choose>
					        			<c:when test="${size == 0 && grupo.estado != 0}">
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
							        		Enviado por: <c:out value="${tg.publicador.nombreCompletoJsp}" />
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
										<a href="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/DescargarTrabajoGrupal.action?filename=<c:out value="${grupo.archivoNombre}" />">
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
			        			<tr style="background-color: #C5DAF6; cursor: pointer;" onClick="xChangeDisplay('filaDocente_<c:out value='${filaDocente}'/>')">
			        		</c:when>
			        		<c:otherwise>
			        			<tr style="background-color: #E7EFF7;">
			        		</c:otherwise>
			        	</c:choose>
				        		<td align="center" width="20" style="border-bottom: solid 1px #B0BACB;">
					        		<c:choose>
						        		<c:when test="${status.count==size && grupo.bandera==2 && grupo.estado != 0}">
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
										<a href="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/DescargarTrabajoGrupal.action?filename=<c:out value="${mensaje.archivoNombre}" />">
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
					        			<c:set var="ultimoUsuario" value="${mensaje.usuarioEmisor.idMatricula}"></c:set>
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

		      <tr style="background-color: #9EC8F5;">
		        	<td>
		        		<span style="float:left;">
		        			<c:if test="${grupo.estado == 0}">
	              					<font color="#FFFFFF"><strong>El Presente Trabajo Grupal ya fue calificado.</strong></font>
	              			</c:if>
              			</span>
              			<span style="float:right; text-align:right;">
	              			
	              				<input type="button" class="form_button"
	              				<c:choose>
	              					<c:when test="${grupo.bandera==1 && aula.idMatricula==ultimoUsuario}">
	              						onclick="mostrarFormModMensaje()" value="Editar"
	              					</c:when>
	              					<c:otherwise>
	              						onclick="mostrarFormNuevoMensaje()"  value="Enviar Trabajo"
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
						<form method="post" enctype="multipart/form-data" onSubmit="return validar();"
						action="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/ResponderMensaje.action">
							<table width="495" class="tabla01" style="background-color: #e7efff">
								<tr>
									<td width="50" style="padding-left: 5px;">
										<strong>Archivo: </strong>
									</td>
									<td style="padding-right: 5px;" width="150">
										<input type="file" id="uploadForm" class="form_button" style="text-align: left;" name="file" size="30" />
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
										<textarea rows="4" cols="58" id="form_descripcion" onKeyDown="cuentaCaracteres(this)" onKeyUp="cuentaCaracteres(this)" name="descripcion"></textarea>
									</td>
								</tr>
								<tr>
									<td colspan="3" style="padding-right: 5px; padding-left: 5px;">
										<span style="float:left;">
											<input type="button" value="Cancelar" onClick="xHideD('mensaje')" class="form_button"/>
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
				<center><h5><i>Ud. no pertenece a ning&uacute;n grupo.</i></h5></center>
			</c:otherwise>
		</c:choose>
				
			</c:when>
			<c:otherwise>
				<center><h5>A&uacute;n no se ha publicado un trabajo para esta unidad.</h5></center>
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
