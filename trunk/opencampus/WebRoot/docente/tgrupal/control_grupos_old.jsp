<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
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
			src="<c:out value='${contextPath}'/>/docente/tgrupal/control_grupos.js"></script>
			
<%--		********************  Creacion de Arreglos ********************--%>
			
		<c:set var="size" value="${fn:length(GRUPOS)}"></c:set>
		<c:out value="<script language='JavaScript'>" escapeXml="false"></c:out>
			var estudianteTmp;
			var grupoTmp;
			var integrantesTmp;
			var integranteTmp;
		<c:forEach items="${GRUPOS}" var="grupo" varStatus="fila">
		<c:out value="// ${fila.count} *******************************//"></c:out>
			<c:choose>
				<c:when test="${size != fila.count}">
			grupoTmp = new Object();
			grupoTmp.id='<c:out value="${grupo.idGrupo}"/>';
			grupoTmp.name='<c:out value="${grupo.nombre}"/>';
			integrantesTmp = new Array();
					
					<c:forEach items="${grupo.integrantes}" var="integrante">
				integranteTmp = new Object();
				integranteTmp.value='<c:out value="${integrante.usuarioMatricula.idMatricula}"/>';
				integranteTmp.text='<c:out value="${integrante.usuarioMatricula.idUsuario}"/> - <c:out value="${integrante.usuarioMatricula.nombreCompletoJsp}"/>';
				integrantesTmp.push(integranteTmp);
					</c:forEach>
					
			grupoTmp.array=integrantesTmp;
			grupos.push(grupoTmp);
				</c:when>
				<c:otherwise>
				
					<c:forEach items="${grupo.integrantes}" var="integrante">
			estudianteTmp = new Object();
			estudianteTmp.value='<c:out value="${integrante.usuarioMatricula.idMatricula}"/>';
			estudianteTmp.text='<c:out value="${integrante.usuarioMatricula.idUsuario}"/> - <c:out value="${integrante.usuarioMatricula.nombreCompletoJsp}"/>';
			estudiantes.push(estudianteTmp);
					</c:forEach>
					
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:out value="</script>" escapeXml="false"></c:out>
			
<%--		********************  FIN Creacion de Arreglos ********************--%>
			
	</head>
	

	<body onload="cargar();">

	
	<c:set var="aula" value='${sessionScope.aula_actual}' />

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
			
<%--	****************************** Gestor de Grupos **********************************--%>
	
		<div style="height: 25px; vertical-align: top">
			<input type="button" class="form_button" style="width: 150px; float: left;" value="Regresar"
			onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/tgrupal/Cargar.action?cmd=return'" style="cursor: pointer;">
			<input type="button" class="form_button" style="width: 160px; float: right;" value="Formar Automáticamente"
			onclick="formarAutomaticamente()" style="cursor: pointer;">
		</div>
		<div id="Estudiantes" style="display: block"/>
		
			<table border="0" width="495" class="tabla01" style="table-layout: fixed;" >
		        <caption>
		        	Estudiantes
		        </caption>
		        <tr>
		          <td width="495" align="center">
		          	        
					<select size="8" id="SelectEstudiantes" multiple="multiple"
						style="width: 490px;" ondblclick="asignar()">
					</select>
		      
		          </td>
		         </tr>
		         <tr>
			        <td style="padding-left: 5px;">
				        <span id="mensaje1" style="width: 150px; float:left; text-align: left;">
				        		Total: <c:out value="${fn:length(ESTUDIANTES)}"></c:out> Estudiante(s)
				        </span>
				        <span style="width: 330px; float:right; text-align: right;">
				        	<input type="button" onclick="asignar()" value="&nbsp;&nbsp;&nbsp;Asignar&nbsp;&nbsp;&nbsp;" class="form_button">
				        </span>
			        </td>
		        </tr>
			</table>
			
		</div>
		
		<div style="height: 5px; vertical-align: middle">
		</div>
		
		<div id="Grupos" style="display: block"/>
		
			<table border="0" width="495" class="tabla01" style="table-layout: fixed;" >
		        <caption>
		        	Grupos
		        </caption>
		        <tr>
			        <td width="495" align="right">
				        <form onsubmit="crearGrupo(); return false;" method="post">
				        	Nombre: <input type="text" id="nuevoGrupo_txt" class="form_text" maxlength="100" size="62">
				        	<input type="submit" value="Crear Grupo" class="form_button">
				        </form>
			        </td>
		        </tr>
		        <tr>
		          <td align="center">   
					<select size="8" id="SelectGrupos" multiple="multiple"
						style="width: 490px;"  onchange="listarIntegrantes(); xHide('rename_txt');">
						
					</select>
		          </td>
		         </tr>
		         <tr>
			        <td style="padding-left: 5px;">
			        	<form onsubmit="renombrarGrupo(); return false;" method="post">
							<input type="text" id="rename_txt" class="form_text" maxlength="100" size="60"
			        			style="position: absolute; visibility: hidden;"  onblur="if(flag)xHide('rename_txt');">
			        	</form>
			        	<span id="mensaje2" style="width: 280px; float:left; text-align: left;">
			        			Total: 0 grupo(s)
			        	</span>
			        	<span style="width: 200px; float:right; text-align: right;">
			        		<input type="button" onclick="renombrarGrupo()" value="Renombrar" class="form_button"
			        		onmouseover="flag=false" onmouseout="flag=true">
			        		<input type="button" onclick="eliminarGrupo()" value="Eliminar" class="form_button">
			        	</span>
			        </td>
		        </tr>
			</table>
			
		</div>
		
		<div style="height: 5px; vertical-align: middle">
		</div>
		
		<div id="Integrantes"/>
		
			<table id="table_integrantes" border="0" width="495" class="tabla01" style="table-layout: fixed;" >
		        <caption>
		        	Integrantes
		        </caption>
		        <tr>
		          <td width="495" align="center">
		          	        
					<select size="8" id="SelectIntegrantes" multiple="multiple"
						style="width: 490px;" ondblclick="desasignar()">
						
					</select>
		      
		          </td>
		         </tr>
		         <tr>
			        <td style="padding-left: 5px;">
				        <span id="mensaje3" style="width: 150px; float:left; text-align: left;">
				        		Total: 0 Integrante(s)
				        </span>
				        <span style="width: 330px; float:right; text-align: right;">
   							<input type="button" onclick="desasignar()" value="Desasignar" class="form_button">
				        </span>
			        </td>
		        </tr>
			</table>
			
		</div>

<%--	****************************** FIN Gestor de Grupos **********************************--%>	
	
  </div>  
	<div id="pie">
		<p class="pie">
			<%@include file="../../comun/pie.jsp" %>
		</p>
	</div>
</div>
</body>
</html>
