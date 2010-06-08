<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="edu.opencampus.lms.modelo.Usuario"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="java.util.*"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@  page import="edu.opencampus.lms.modelo.AulaVirtual"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}'/>
<%
	Enumeration enu = session.getAttributeNames();
	while (enu.hasMoreElements()) {

		String key = (String) enu.nextElement(); //obtengo llave
		Object value = (Object) session.getAttribute(key); //obtengo valor
		//System.out.println(key+"|"+value);

	}
%>
<%
AulaVirtual aula = ((Usuario)request.getSession().getAttribute(Constante.USUARIO_ACTUAL)).getAulaActual();
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
			src="<c:out value='${contextPath}'/>/docente/tgrupal/control_tgrupal.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/aula_virtual.js"></script>			
		<link rel="stylesheet" type="text/css"
			href="<c:out value='${contextPath}'/>/js/jscalendar/calendar-style.css" />
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-setup.js"></script>
	</head>
	

	<body onload="ocultarFlags();">
	<c:set var="tg" value='${requestScope.TRABAJO_GRUPAL}'/>
	<c:set var="aula" value='${sessionScope.aula_actual}' />

	
<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;"> 
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="90%">
							<strong>Curso : <%=aula.getCurso().getNombre()%> </strong>
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
	  	<c:when test="${tg.fechaActivacion == null}">
	  		<c:set var="display" value="block"></c:set>
	  	</c:when>
	  	<c:otherwise>
	  		<c:set var="display" value="none"></c:set>
	  		<div id="det_TGrupal">
			<table width="500" style="table-layout: fixed;" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" border="0">
	
				<tr class="tabla01_encabezado" onclick="xChangeDisplay('dTR_1'); xChangeDisplay('dTR_2'); xChangeDisplay('dTR_3');" style="cursor: pointer;">
					<th colspan="2">
						Trabajo Grupal: <c:out value="${tg.nombreUnidad}"></c:out>
					</th>
				</tr>
				
				<tr style="cursor: pointer;"  onclick="xChangeDisplay('dTR_1'); xChangeDisplay('dTR_2'); xChangeDisplay('dTR_3');"
				onmouseover="seleccionBlue(this,true);" onmouseout="seleccionBlue(this,false);">
					<td width="250" style="padding-left: 5px; padding-top: 5px; padding-bottom: 5px;">
						<strong>Fecha de activaci&oacute;n : </strong> &nbsp;
						<span id="DfActivacion"><f:DateToString fecha="${tg.fechaActivacion}" completo="true" /></span>
					</td>
					<td width="250">
						<strong>Fecha de entrega : </strong> &nbsp;
						<span id="DfEntrega"><f:DateToString fecha="${tg.fechaEntrega}" completo="true" /></span>
					</td>
				</tr>
				<tr id="dTR_1" style="Xdisplay: none;">
					<td colspan="2" style="padding: 5px;">
						<strong>Descripci&oacute;n adicional sobre el trabajo : </strong>
					</td>
				</tr>
	
				<tr id="dTR_2" style="Xdisplay: none;">
					<td colspan="2" style="padding: 5px;">
						<span id="Ddescripcion"><c:out value="${tg.descripcion}"></c:out></span>
					</td>
				</tr>
				<tr id="dTR_3" style="Xdisplay: none;">
					<td>
						&nbsp;
					</td>
					<td align="right" style="padding-right: 5px">
						<input type="button" class="form_button" value="Modificar Trabajo"
						onclick="xHideD('det_TGrupal'); xShowD('form_TGrupal');"/>
					</td>
				</tr>
			</table>
			</div>
	  	</c:otherwise>
	  </c:choose>
		
		<div id="form_TGrupal" style="display: <c:out value='${display}' />">
		<form  onsubmit="javascript:return validar(this.fechaActivacion.value,this.fechaEntrega.value,this.descripcion.value,'<f:DateToString fecha="${aula.fechaInicio}"/>','<f:DateToString fecha="${aula.fechaFin}"/>')"
		method="post" action="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/PublicarTrabajo.action">
			<table width="500" style="table-layout: fixed;" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" border="0">
	
				<tr class="tabla01_encabezado">
					<th colspan="4">
						Trabajo Grupal: <c:out value="${tg.nombreUnidad}"></c:out>
<%--						<input type="hidden" name="idUnidad" value="<c:out value='${tg.idUnidad}'/>"> --%>
					</th>
				</tr>
				
				<tr>
					<td width="125" style="padding-left: 5px;">
						<strong>Fecha de activaci&oacute;n : </strong>
					</td>
					<td width="125">
						<input type="text" id="form_fechaActivacion" size="16" class="form_text" readonly 
						name="fechaActivacion" value="<f:DateToString fecha="${tg.fechaActivacion}" completo="true" />"/>
					</td>
					<td width="125">
						<strong>Fecha de entrega : </strong>
					</td>
					<td width="125">
						<input type="text" id="form_fechaEntrega" class="form_text" size="16" readonly 
						name="fechaEntrega" value="<f:DateToString fecha="${tg.fechaEntrega}" completo="true" />"/>
					</td>
				</tr>
								
				<tr>
					<td colspan="4" style="padding-left: 5px;">
						<strong>Descripci&oacute;n adicional sobre el trabajo : </strong>
					</td>
				</tr>
	
				<tr>
					<td colspan="4" style="padding-left: 5px;">
						<textarea style="width: 480px;" rows="5" cols="58" id="form_descripcion" onkeydown="cuentaCaracteres(this)" onkeyup="cuentaCaracteres(this)" 
						name="descripcion"><c:out value="${tg.descripcion}"></c:out></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="padding-left: 5px;">
						<strong>L&iacute;mite de caracteres: </strong>
						<input type="text" id=form_numCaracteres size=4 class="form_text" value="<c:out value="${fn:length(tg.descripcion)}"></c:out>" readonly> / 500
					</td>
					<td align="right" colspan="2" style="padding-right: 6px">
						<c:if test="${tg.fechaActivacion != null}">
							<input type="button" class="form_button" value="Cancelar" 
							onclick="cancelarmodificarTrabajo();"/>
						</c:if>
						<input type="submit" class="form_button" value="Publicar Trabajo" id="form_button"/>
					</td>
				</tr>
	
			</table>
		</form>
		</div>
		<script language="JavaScript" type="text/javascript">
			Calendar.setup({inputField:"form_fechaActivacion", ifFormat:"%d-%m-%Y %H:%M", showsTime:true, singleClick:false, timeDefault:"07:00" ,onUpdate:catcalc});
			Calendar.setup({inputField:"form_fechaEntrega", ifFormat:"%d-%m-%Y %H:%M", showsTime:true, singleClick:false, timeDefault:"23:00" ,onUpdate:catcalc});
		</script>	
				
		<p></p>
		
<%--	****************************** Gestor de Grupos **********************************--%>
	
		<div id="miClase" style="display: block"/>
			<c:if test="${GRUPOS != null}" >
				<c:choose>
					<c:when test="${fn:length(GRUPOS)>0}">
						<f:Constante campo="FLAG_PENDIENTE_DOCENTE" var="pendDocente" />
						<f:Constante campo="FLAG_PENDIENTE_ESTUDIANTE" var="pendEstudiante" />
						<f:Constante campo="FLAG_NO_INICIADO" var="pendNinguno" />
						<f:Constante campo="FLAG_TRABAJO_EXPIRADO" var="expirado" />
						<table border="0" width="100%" class="tabla01" style="table-layout: fixed;" >
					        <caption>
					        	Grupos
					        </caption>
					        <tr>
					          <td width="20"></td>
					          <td width="320"></td>
					          <td width="10"></td>
					          <td width="30"></td>
					          <td width="10"></td>
					          <td width="10"></td>
					          <td width="30"></td>
					          <td width="10"></td>
					          <td width="23"></td>
					        </tr>
					        <tr class="tabla01_subEncabezado">
					          <td align="center"></td>
					          <td align="center"><strong>Grupos</strong></td>
					          <td align="center" colspan="3"><strong>Debate</strong></td>
					          <td align="center" colspan="3"><strong>Revisi&oacute;n</strong></td>
					          <td align="center">&nbsp;</td>
					        </tr>
					        
						<c:forEach items="${GRUPOS}" var="grupo" varStatus="fila">
							<tr onmouseover="javascript:seleccion(this,true);" style="cursor: pointer;" onmouseout="javascript:seleccion(this,false);">
					          <td onclick="xChangeDisplay('fila_<c:out value="${fila.count}" />')"
					          ><img src="<c:out value='${contextPath}'/>/img/icon_group.gif"/></td>
					          
					          <td onclick="xChangeDisplay('fila_<c:out value="${fila.count}" />')"
					          ><strong><c:out value="${fila.count}"></c:out>. <c:out value="${grupo.nombre}"></c:out></strong> (<c:out value="${fn:length(grupo.integrantes)}"></c:out> integrantes)</td>
					          
					          <td></td>
					          <td align="center">
					          	<img src="<c:out value='${contextPath}' />/img/icon_dialog.gif" onclick="javascript:abrirDebate('<c:out value='${contextPath}' />/aulavirtual/debate/Cargar.action?idGrupo=<c:out value='${grupo.idGrupo}' />'); xHide('deb_<c:out value="${fila.count}" />');"/>
					          </td>
					          <td>
					          	<div id="deb_<c:out value="${fila.count}" />">
						          	<c:if test="${grupo.banderaDebate != 0}">
						          		<img src="<c:out value='${contextPath}' />/img/flag.gif" width="8" height="11" />	
						          	</c:if>
						        </div>
					          	
					          </td>
					          <td></td>
					          <td align="center">
					          	<c:choose>
						          	<c:when test="${grupo.bandera != pendNinguno}">
						          		<a href="<c:out value='${contextPath}' />/aulavirtual/tgrupal/VerMensajes.action?idGrupo=<c:out value='${grupo.idGrupo}'/>">
						          			<img src="<c:out value='${contextPath}' />/img/icon_nota.gif" border="0"/>
						          		</a>
						          		<c:set var="readonly" value=""></c:set>
						          	</c:when>
						          	<c:otherwise>
						          		<img src="<c:out value='${contextPath}' />/img/icon_nota_inactivo.gif" alt="Debe publicar el trabajo para poder evaluar."/>
						          		<c:set var="readonly" value="readonly"></c:set>
						          	</c:otherwise>
					          	</c:choose>
					          </td>
					          <td>
					          	<div id="flag_<c:out value="${fila.count}" />">
						          	<c:if test="${grupo.bandera == pendDocente}">
						          		<img src="<c:out value='${contextPath}' />/img/flag.gif" width="8" height="11" />
						          	</c:if>
						         </div>
					          </td>
					          <td align="center">
					          	<c:if test="${grupo.expirado == expirado}">
						          	<img src="<c:out value='${contextPath}' />/img/icon_expirado.gif" alt="Extemporáneo"/>
					          	</c:if>
					          </td>
					        </tr>
					        <tr id="fila_<c:out value="${fila.count}" />" style="display:none">
					        	<td colspan="9">
					        	<c:if test="${fn:length(grupo.integrantes)>0}">
					        		<table width="100%" class="tabla01" style="table-layout: fixed; background-color: #E5EFF8">
							        	<tr class="tabla01_subEncabezado" style="background-color: #C5DAF6">
<%--						        			<td width="242">Integrantes</td>--%>
<%--						        			<td width="40">Debates</td>--%>
<%--						        			<td width="40">NO</td>--%>
<%--						        			<td width="40">NC</td>--%>
<%--						        			<td width="40">NI</td>--%>
<%--						        			<td width="40">NP</td>--%>
<%--						        			<td width="40">NF</td>--%>
											<td align="center" width="24">&nbsp;</td>
									        <td width="79">Paterno</td>
									        <td width="79">Materno</td>
									        <td width="140">Nombres</td>
									        <td width="50">Secci&oacute;n</td>
						        			<td width="50">Debates</td>
						        			<td width="50">Nota</td>
						        		</tr>
							        <c:forEach items="${grupo.integrantes}" var="integrante" varStatus="intg">
						        		<tr>
						        			<td align="center">
						        				<c:out value="${intg.count}" />
						        			</td>
						        			<td style="white-space: nowrap; padding-left: 5px;">
						        				<c:out value="${integrante.usuarioMatricula.paterno}"></c:out>
						        			</td>
						        			<td style="white-space: nowrap; padding-left: 5px;">
						        				<c:out value="${integrante.usuarioMatricula.materno}"></c:out>
						        			</td>
						        			<td style="white-space: nowrap; padding-left: 5px;">
						        				<c:out value="${integrante.usuarioMatricula.nombre1}"></c:out>
						        				<c:out value="${integrante.usuarioMatricula.nombre2}"></c:out>
						        			</td>
						        			<td align="center">
						        				<c:out value="${integrante.usuarioMatricula.seccion}"></c:out>
						        			</td>
						        			<td align="center">
						        				<c:out value="${integrante.debates}"></c:out> /
						        				<c:out value="${grupo.debates}"/>
						        			</td>
<%--						        			<td align="center">--%>
<%--						        				<input id="nota_NO_<c:out value="${integrante.usuarioMatricula.idMatricula}"/>" type="text" class="caja_nota" maxlength="1"--%>
<%--			          							value="<c:out value='${integrante.notaOportunidad}'/>" onmouseover="seleccionInput(this, true)" onmouseout="seleccionInput(this, false)"--%>
<%--			          							onchange="calificarTrabajo('<c:out value="${fila.count}" />','<c:out value="${integrante.usuarioMatricula.idMatricula}"/>')" <c:out value="${readonly}" /> style="text-align: center; border: 1px;">--%>
<%--						        			</td>--%>
<%--						        			<td align="center">--%>
<%--						        				<input id="nota_NC_<c:out value="${integrante.usuarioMatricula.idMatricula}"/>" type="text" class="caja_nota" maxlength="1"--%>
<%--			          							value="<c:out value='${integrante.notaCoherencia}'/>" onmouseover="seleccionInput(this, true)" onmouseout="seleccionInput(this, false)"--%>
<%--			          							onchange="calificarTrabajo('<c:out value="${fila.count}" />','<c:out value="${integrante.usuarioMatricula.idMatricula}"/>')" <c:out value="${readonly}" /> style="text-align: center; border: 1px;">--%>
<%--						        			</td>--%>
<%--						        			<td align="center">--%>
<%--						        				<input id="nota_NI_<c:out value="${integrante.usuarioMatricula.idMatricula}"/>" type="text" class="caja_nota" maxlength="1"--%>
<%--			          							value="<c:out value='${integrante.notaInnovacion}'/>" onmouseover="seleccionInput(this, true)" onmouseout="seleccionInput(this, false)"--%>
<%--			          							onchange="calificarTrabajo('<c:out value="${fila.count}" />','<c:out value="${integrante.usuarioMatricula.idMatricula}"/>')" <c:out value="${readonly}" /> style="text-align: center; border: 1px;" >--%>
<%--						        			</td>--%>
<%--						        			<td align="center">--%>
<%--						        				<input id="nota_NP_<c:out value="${integrante.usuarioMatricula.idMatricula}"/>" type="text" class="caja_nota" maxlength="1"--%>
<%--			          							value="<c:out value='${integrante.notaParticipacion}'/>" onmouseover="seleccionInput(this, true)" onmouseout="seleccionInput(this, false)"--%>
<%--			          							onchange="calificarTrabajo('<c:out value="${fila.count}" />','<c:out value="${integrante.usuarioMatricula.idMatricula}"/>')" <c:out value="${readonly}" /> style="text-align: center; border: 1px;">--%>
<%--						        			</td>--%>
						        			<td align="center">
						        				<c:choose>
						        					<c:when test="${integrante.notaPromedio < 11}">
						        						<c:set var="notaColor" value="#CE2A29"></c:set>
						        					</c:when>
						        					<c:otherwise>
						        						<c:set var="notaColor" value="#638DD8"></c:set>
						        					</c:otherwise>
						        				</c:choose>
<%--						        				<input id="nota_NF_<c:out value="${integrante.usuarioMatricula.idMatricula}"/>" type="text" class="caja_nota" maxlength="2"--%>
<%--			          							value="<c:out value='${integrante.notaPromedio}'/>" onmouseover="seleccionInput(this, true)" onmouseout="seleccionInput(this, false)"--%>
<%--			          							onchange="" readonly="readonly" style="font-weight: bold; color: <c:out value="${notaColor}" />; text-align: center; border: 1px;">--%>
			          							
			          							<input id="nota_NF_<c:out value="${integrante.usuarioMatricula.idMatricula}"/>" type="text" class="caja_nota" maxlength="2"
			          							value="<c:out value='${integrante.notaPromedio}'/>" onmouseover="seleccionInput(this, true)" onmouseout="seleccionInput(this, false)"
			          							onchange="calificarTrabajo('<c:out value="${fila.count}" />','<c:out value="${integrante.usuarioMatricula.idMatricula}"/>')" style="font-weight: bold; color: <c:out value="${notaColor}" />; text-align: center; border: 1px;">
						        			</td>
						        		</tr>
							        </c:forEach>
							        	<tr>
						        			<td colspan="7" style="background-color: #C5DAF6;">
						        					<iframe src="<c:out value='${contextPath}'/>/docente/tgrupal/control_subirTrabajo.jsp?idGrupo=<c:out value='${grupo.idGrupo}' />" id="uploadForm" frameborder="0" marginwidth="0" width="100%" marginheight="0" height="20" scrolling='no'></iframe>
						        			</td>
						        		</tr>
						        	</table>
						        </c:if>
					        	</td>
					        </tr>
						</c:forEach>
						<% if(false) { %>
				      		<tr>
				      			<td colspan="9" style="border-top-color: #90BADA; border-top-width: 1px; border-top-style: solid;">
				      			<table>
				      				<tr>
				      					<td width="25"><small><b>NO : </b></small></td><td width="140"><small>Nota de oportunidad</small></td>
				      					<td width="25"><small><b>NC : </b></small></td><td width="140"><small>Nota de coherencia</small></td>
				      					<td width="25"><small><b>NI : </b></small></td><td width="140"><small>Nota de innovaci&oacute;n</small></td>
				      				</tr>
				      				<tr>
				      					<td><small><b>NP : </b></small></td><td><small>Nota de participaci&oacute;n</small></td>				      				
										<td><small><b>NF : </b></small></td><td><small>Nota Promedio</small></td>
				      					<td></td><td></td>
				      				</tr>
				      				<tr>
				      					<td colspan="6" width="495"><small>(Las notas de NO, NC, NI y NP van de 0 a 5)</small></td>
				      				</tr>
				      			</table>
					      				
				      			</td>
				      		</tr>
				      		<% } %>
						</table>
<%--		********************  Creacion de Arreglos ********************--%>
			
		<c:out value="<script language='JavaScript'>" escapeXml="false"></c:out>
			var gruposControl = new Array();
			var integrantesControl;
		<c:forEach items="${GRUPOS}" var="grupo" varStatus="fila">
			
			integrantesControl = new Array();
			
				<c:forEach items="${grupo.integrantes}" var="integrante">
					integrantesControl.push('<c:out value="${integrante.usuarioMatricula.idMatricula}"/>');
				</c:forEach>
			
			gruposControl.push(integrantesControl);
			
		</c:forEach>
		<c:out value="</script>" escapeXml="false"></c:out>
			
<%--		********************  FIN Creacion de Arreglos ********************--%>
					</c:when>
					<c:otherwise>
						<table border="0" width="500" class="tabla01" style="table-layout: fixed;" >
				        <caption>
				        	Grupos
				        </caption>
				        <tr>
				        <td align="center" height="30">
				        	<b><i>A&uacute;n no se ha definido grupos.</i></b>
				        </td>
				        </tr>
				        </table>
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>

<%--	****************************** FIN Gestor de Grupos **********************************--%>	
		<p></p>

		<div style="height: 25px; float: left;">
			<input type="button" class="form_button" style="width: 180px; display: none;" value="Modo Automático"
				onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/tgrupal/CambiarModo.action?idUnidad=<c:out value='${idUnidad}'/>&cmd=0'" style="cursor: pointer;">
  		</div>
		<div style="height: 25px; float: right;">
			<input type="button" class="form_button" style="width: 180px;" value="Formar y/o Reasignar grupos"
			onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/tgrupal/GestionDeGrupos.action'" style="cursor: pointer;">
		</div>
		
  </div>  
	<div id="pie">
		<p class="pie">
			<%@include file="../../comun/pie.jsp" %>
		</p>
	</div>
</div>

<script type="text/javascript">
	window.resizeTo(550,window.screen.availHeight*0.9);
</script>

</body>
</html>
