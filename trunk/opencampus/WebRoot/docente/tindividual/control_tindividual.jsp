<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.modelo.Usuario"%>
<%@  page import="edu.opencampus.lms.modelo.AulaVirtual"%>
<%@page import="java.util.*"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}'/>
<%
Usuario usuario = (Usuario) session.getAttribute(Constante.USUARIO_ACTUAL);
AulaVirtual aula = usuario.getAulaActual();			

	//System.out.println("*********** Session Atribute | Value ***********");
	Enumeration enu = session.getAttributeNames();
	while (enu.hasMoreElements()) {

		String key = (String) enu.nextElement(); //obtengo llave
		Object value = (Object) session.getAttribute(key); //obtengo valor
		//System.out.println(key+"|"+value);

	}
	//System.out.println("************************************************");
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
			src="<c:out value='${contextPath}'/>/docente/tindividual/control_tindividual.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<c:out value='${contextPath}'/>/js/jscalendar/calendar-style.css" />
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-setup.js"></script>
	</head>
	

	<body>
	<c:set var="ti" value='${sessionScope.TRABAJO_INDIVIDUAL}'/>
	<c:set var="aula" value='${sessionScope.usuario_actual.aulaActual}' />
	<f:Constante campo="FLAG_INICIA_PENDIENTE_ESTUDIANTE" var="estadoIniEst" />
	<f:Constante campo="FLAG_PENDIENTE_DOCENTE" var="estadoDoc" />
	<f:Constante campo="FLAG_PENDIENTE_ESTUDIANTE" var="estadoEst" />
<%--	<f:Constante campo="FLAG_TRABAJO_NOEXPIRADO" var="expiradoNO" />--%>
	<f:Constante campo="FLAG_TRABAJO_EXPIRADO" var="expiradoSI" />
	
		
<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;"> 
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="90%">
							<strong>Cursos : <%=aula.getCurso().getNombre()%> </strong>
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
		<div id="pop_cuerpo" style="width: 500px;">   
	  
	  
	  
	  
	  
	  <c:choose>
	  	<c:when test="${ti.fechaActivacion == null}">
	  		<c:set var="display" value="block"></c:set>
	  	</c:when>
	  	<c:otherwise>
	  		<c:set var="display" value="none"></c:set>
	  		<div id="det_TGrupal">
			<table width="500" align="center" style="table-layout: fixed;" cellpadding="3" border="0" cellspacing="0" bgcolor="#FFFFFF" class="bor_tabla">
	
				<tr class="tabla01_encabezado" onclick="xChangeDisplay('dTR_1'); xChangeDisplay('dTR_2'); xChangeDisplay('dTR_3');" style="cursor: pointer;">
					<th colspan="2">
						Trabajo: <c:out value="${ti.nombreUnidad}"></c:out>
					</th>
				</tr>
				
				<tr style="cursor: pointer;"  onclick="xChangeDisplay('dTR_1'); xChangeDisplay('dTR_2'); xChangeDisplay('dTR_3');"
				onmouseover="seleccionBlue(this,true);" onmouseout="seleccionBlue(this,false);">
					<td width="250" style="padding-left: 5px; padding-top: 5px; padding-bottom: 5px;">
						<strong>Fecha de Activaci&oacute;n : </strong> &nbsp;
						<span id="DfActivacion"><f:DateToString fecha="${ti.fechaActivacion}" completo="true" /></span>
					</td>
					<td width="250">
						<strong>Fecha de Entrega : </strong> &nbsp;
						<span id="DfEntrega"><f:DateToString fecha="${ti.fechaEntrega}" completo="true" /></span>
					</td>
				</tr>
				<tr id="dTR_1" style="display: none;">
					<td colspan="2" style="padding: 5px;">
						<strong>Descripci&oacute;n adicional : </strong>
					</td>
				</tr>
	
				<tr id="dTR_2" style="display: none;">
					<td colspan="2" style="padding: 5px;">
						<span id="Ddescripcion"><c:out value="${ti.interaccion.descripcion}"></c:out></span>
					</td>
				</tr>
				<tr id="dTR_3" style="display: none;">
					<td>
						<c:choose>
							<c:when test="${ti.interaccion.archivoNombre == null}">
								<img src="<c:out value='${contextPath}'/>/img/icon_download_inactivo.gif" alt="Recurso no disponible" border="0"/>
								<small> (No disponible)</small>
							</c:when>
							<c:when test="${ti.interaccion.archivoNombre == 'DEFAULT'}">
								<a href="<c:out value='${contextPath}'/>/aulavirtual/tindividual/DescargarTrabajoPredeterminado.action?idUnidad=<c:out value='${ti.idUnidad}'/>"
								><img src="<c:out value='${contextPath}'/>/img/icon_download.gif" alt="Descargar" border="0"/></a>
								<small> (Predefinido)</small>
							</c:when>
							<c:otherwise>
								<a href="<c:out value='${contextPath}'/>/aulavirtual/tindividual/DescargarTrabajo.action?filename=<c:out value='${ti.interaccion.archivoNombre}'/>"
								><img src="<c:out value='${contextPath}'/>/img/icon_download.gif" alt="Descargar" border="0"/></a>
								<small> (<fmt:formatNumber value="${ti.interaccion.archivoTamanio/1024}" maxFractionDigits="2"/> KB)</small>
							</c:otherwise>
						</c:choose>
					</td>
					<td align="right" style="padding-right: 10px">
						<input type="button" class="form_button" value="Modificar Trabajo"
						onclick="xHideD('det_TGrupal'); xShowD('form_TGrupal');"/>
					</td>
				</tr>
			</table>
			</div>
	  	</c:otherwise>
	  </c:choose>
	  
	  		
		<p></p>
		
	  
	  <div id="form_TGrupal" style="display: <c:out value='${display}' />">
		<form onsubmit="javascript:return validar(this.fechaActivacion.value,this.fechaEntrega.value,this.file.value,this.descripcion.value,'<f:DateToString fecha="${aula.periodo.fechaInicio}"/>','<f:DateToString fecha="${aula.periodo.fechaFin}"/>')"
		method="post" 
		action="<c:out value='${contextPath}'/>/aulavirtual/tindividual/PublicarTrabajoX.action" enctype="multipart/form-data">
			<table width="500" align="center" style="table-layout: fixed;" cellpadding="3" border="0" cellspacing="0" bgcolor="#FFFFFF" class="bor_tabla">
	
				<tr class="tabla01_encabezado">
					<th colspan="4">
						Trabajo: <c:out value="${ti.nombreUnidad}"></c:out>
						<input type="hidden" name="idUnidad" value="<c:out value='${ti.idUnidad}'/>"> 
					</th>
				</tr>
				<c:if test="${msg=='error'}">
				<tr id="alert">
					<td colspan="4" height="24" align="center">
						<script language='JavaScript'>
							xHideD('det_TGrupal');
							xShowD('form_TGrupal');
						</script>
						<FONT color="#FF0000"><b>Error al intentar subir el archivo.</b></FONT>
					</td>
				</tr>
				</c:if>
				<c:if test="${msg=='No existe un documento predefinido.'}">
				<tr id="alert">
					<td colspan="4" height="24" align="center">
						<script language='JavaScript'>
							xHideD('det_TGrupal');
							xShowD('form_TGrupal');
						</script>
						<FONT color="#FF0000"><b><c:out value="${msg}"/></b></FONT>
					</td>
				</tr>
				</c:if>
				<tr>
					<td width="125">
						<strong>Fecha de activaci&oacute;n : </strong>
					</td>
					<td width="125">
						<input type="text" id="form_fechaActivacion" size="16" class="form_text" readonly 
						name="fechaActivacion" value="<f:DateToString fecha="${ti.fechaActivacion}" completo="true" />"/>
					</td>
					<td width="125">
						<strong>Fecha de entrega : </strong>
					</td>
					<td width="125">
						<input type="text" id="form_fechaEntrega" class="form_text" size="16" readonly 
						name="fechaEntrega" value="<f:DateToString fecha="${ti.fechaEntrega}" completo="true" />"/>
					</td>
				</tr>
	
				<tr>
					<td width="125" style="padding-right: 10px;">
						<span style="float: left;">
						<strong>Archivo : &nbsp;<font color="red">(*)</font></strong>
						</span>
						<span style="float: right;">
							<img src="<c:out value='${contextPath}'/>/img/icon_guia.gif" border="0" onclick="alert('Si no adjunta un archivo, se usará el archivo predeterminado por la unidad.')" style="cursor: pointer;"/>
						</span>
					</td>
					<td colspan="2" width="275">
					
<%--						<iframe src="<c:out value='${contextPath}'/>/aulavirtual/tindividual/CargarForm.action" id="uploadForm" frameborder="0" marginwidth="0" width="360" marginheight="0" height="25" scrolling='no'></iframe>--%>
						<input type="file" id="uploadForm" class="form_text" name="file" size="26" />
					</td>
					<td width="120" style="padding-right: 10px">
						<span style="float: left;">
						<c:choose>
							<c:when test="${ti.interaccion.archivoNombre == null}">
								<img src="<c:out value='${contextPath}'/>/img/icon_download_inactivo.gif" alt="Recurso no disponible" border="0"/>
								<!--  small> (No disponible)</small -->
								<input type="hidden" id="docExist" value="false"/>
							</c:when>
							<c:when test="${ti.interaccion.archivoNombre == 'DEFAULT'}">
								<a href="<c:out value='${contextPath}'/>/aulavirtual/tindividual/DescargarTrabajoPredeterminado.action?idUnidad=<c:out value='${ti.idUnidad}'/>"
								><img src="<c:out value='${contextPath}'/>/img/icon_download.gif" alt="Descargar" border="0"/></a>
								<small> (Predefinido)</small>
								<input type="hidden" id="docExist" value="true"/>
							</c:when>
							<c:otherwise>
								<a href="<c:out value='${contextPath}'/>/aulavirtual/tindividual/DescargarTrabajo.action?filename=<c:out value='${ti.interaccion.archivoNombre}'/>"
								><img src="<c:out value='${contextPath}'/>/img/icon_download.gif" alt="Descargar" border="0"/></a>
								<input type="hidden" id="docExist" value="true"/>
								<small> (<fmt:formatNumber value="${ti.interaccion.archivoTamanio/1024}" maxFractionDigits="2"/> KB)</small>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="<c:out value='${contextPath}'/>/aulavirtual/tindividual/EliminarTrabajo.action?filename=<c:out value='${ti.interaccion.archivoNombre}'/>"
								onclick="return window.confirm('¿Realmente desea eliminar el documento presente para usar el predeterminado por la unidad?');"
								><img src="<c:out value='${contextPath}'/>/img/icon_trash.gif" alt="Eliminar y usar el trabajo predefinido" border="0"/></a>
							</c:otherwise>
						</c:choose>
						</span>
<%--						<input type="hidden" id=form_nomArchivo value="" name="aNombre">--%>
<%--						<input type="hidden" id=form_tamArchivo value="" name="aTamanio">--%>
					</td>
				</tr>
	
				<tr>
					<td colspan="4">
						<strong>Descripci&oacute;n adicional: </strong>
					</td>
				</tr>
	
				<tr>
					<td colspan="4">
						<textarea rows="6" cols="58" id="form_descripcion" onkeydown="cuentaCaracteres(this)" onkeyup="cuentaCaracteres(this)" 
						name="descripcion" style="width: 99%"><c:out value="${ti.interaccion.descripcion}"></c:out></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<b><font color="red">(*)</font> El archivo debe ser menor a 5 MiB.</b>
					</td>
					<td align="right" colspan="2" style="padding-right: 10px">
						<input type="text" id=form_numCaracteres size=4 class="form_text" value="<c:out value="${fn:length(ti.interaccion.descripcion)}"></c:out>" readonly> / 500&nbsp;&nbsp;&nbsp;&nbsp;
						<c:choose>
	  						<c:when test="${ti.fechaActivacion == null}">
								<input type="submit" id="form_button" class="form_button" value="Publicar Trabajo"/>
							</c:when>
							<c:otherwise>
								<input type="hidden" name="cmd" value="mod"/>
								<input type="submit" id="form_button" class="form_button" value="Guardar"/>
								<input type="reset" class="form_button" value="Cancelar" onclick="xShowD('det_TGrupal'); xHideD('form_TGrupal'); xHideD('alert');"/>
							</c:otherwise>
						</c:choose>
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
		
		<c:if test="${TRABAJO_MATRICULAS != null}">
		
		<div id="miClase" style="display: block; width: 500px;"/>
	      <table width="500" cellpadding="3" border="0" cellspacing="0" bgcolor="#FFFFFF" class="bor_tabla">

			<tr class="tabla01_encabezado">
					<th colspan="9">
						Trabajo: <c:out value="${ti.nombreUnidad}"></c:out>
					</th>
			</tr>
	        <tr class="tabla01_subEncabezado">
	          <td align="center" width="5%"></td>
	          <td width="20%">Paterno</td>
	          <td width="20%">Materno</td>
	          <td width="20%">Nombre</td>
   	          <td width="10%">&nbsp;</td>	          
	          <td colspan="2" width="5%">Revisi&oacute;n</td>
	          <td width="10%">Expirado</td>
	          <td width="10%">Nota</td>
	        </tr>
	        
	      <c:set var="estiloFila" value="true" />
	      <c:forEach items="${TRABAJO_MATRICULAS}" var="matricula" varStatus="fila">
	      <tr 
	      <c:choose>
		      <c:when test="${estiloFila}">
		        class="tabla01_fila1"
		      </c:when>
		      <c:otherwise>
		      	class="tabla01_fila2"
		      </c:otherwise>
	      </c:choose>
	      >
	      	<c:set var="estiloFila" value="${!estiloFila}" />
	 		  <td align="center" class="bor_der_unid">
	 			<b><c:out value="${fila.count}"></c:out></b>
	          </td>
	          <td class="bor_der_unid"><c:out value='${matricula.usuarioReceptor.usuario.persona.apepaterno}'/></td>
	          <td class="bor_der_unid"><c:out value='${matricula.usuarioReceptor.usuario.persona.apematerno}'/></td>
	          <td class="bor_der_unid"><c:out value='${matricula.usuarioReceptor.usuario.persona.nomuno}'/>&nbsp;</td>
	          <td class="bor_der_unid"><c:out value='${matricula.usuarioReceptor.usuario.persona.nomdos}'/>&nbsp;</td>

	          <td align="center">
	          	<c:if test='${matricula.estado != estadoIniEst}'>
	          		<a href="<c:out value='${contextPath}' />/aulavirtual/tindividual/VerMensajesDeDocente.action?idMatricula=<c:out value='${matricula.usuarioReceptor.idMatricula}'/>">
	          			<img src="<c:out value='${contextPath}' />/img/nota.gif" border="0" />
	          		</a>
	          	</c:if>
	          </td>
	          <td align="center">
	         	<div id="flag_<c:out value='${matricula.usuarioReceptor.idMatricula}'/>"
		          <c:if test='${matricula.estado != estadoDoc || matricula.nota != null}'>
		          	style="visibility: hidden"
		          </c:if>
		         >
		          <img src="<c:out value='${contextPath}' />/img/flag.gif" width="8" height="11" />
		         </div>
	          </td>
	          <td align="center">
		          	<c:if test='${matricula.expirado == expiradoSI}'>
		          		<img src="<c:out value='${contextPath}' />/img/expirado.gif" />
		          	</c:if>
	          </td>
	          <td align="center">
	          	<input id="nota_<c:out value='${matricula.usuarioReceptor.idMatricula}'/>" 
	          	value="<c:out value='${matricula.nota}'/>" onmouseover="seleccionInput(this, true)" onmouseout="seleccionInput(this, false)"
	          	type="text" class="caja_nota" maxlength="2"
	          	onchange="calificarTrabajo('<c:out value='${matricula.usuarioReceptor.idMatricula}'/>','<c:out value='${matricula.estado}'/>')"
	          	<%// c:if test='${matricula.estado == estadoIniEst}'>readonly</c:if %> />
	          </td>
	        </tr>
	    </c:forEach>	    
      </table>      
     </div>
    </c:if>
  </div>  
	<div id="pie">
		<p class="pie">
			<%@include file="../../comun/pie.jsp" %>
		</p>
	</div>
</div>
</body>
</html>
