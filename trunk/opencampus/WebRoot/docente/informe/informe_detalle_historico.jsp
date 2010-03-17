<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><s:text name="titulo.campus.virtual" /></title>
<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
<script language="javascript" type="text/javascript"	
			src="<%=request.getContextPath()%>/js/aula_virtual.js"></script>			
<script language="javascript" type="text/javascript"	
			src="<%=request.getContextPath()%>/docente/informe/informe.js"></script>
						
<script>	   
	var imagesDir = "<%=request.getContextPath()%>/js/openwysiwyg/icons/";
	var cssDir = "<%=request.getContextPath()%>/js/openwysiwyg/styles/";
	var popupsDir = "<%=request.getContextPath()%>/js/openwysiwyg/popups/";			
</script>
<script language="JavaScript" type="text/javascript" 
		src="<%=request.getContextPath()%>/js/openwysiwyg/wysiwyg.js"></script>
<style type="text/css">
.removeLink {
	background-image: url(../img/icon_trash.gif);
	background-repeat: no-repeat;
	text-decoration: none;
	font-size: 12px;
	margin-left:3px;
}
.removeLink span {
	visibility: hidden;
	font-size: 12px;
}
.duplicateLink {
	background-image: url(../img/icon_new.png);
	background-repeat: no-repeat;
	text-decoration: none;
	font-size: 16px;
}
.duplicateLink span {
	visibility: hidden;
	font-size: 16px;
}
.errMsg {
	color: #FF0000;
	font-weight: bold;
}
.errFld {
	border: 1px solid #FF5D3F;
}

</style>

</head>

<body onload="expandir()" oonbeforeunload="return 'Recuerde que si sale de la ventana perderá los cambios que no haya guardado.';">
<div id="pop_up">
		<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong><s:text name="aula.alumno.pop_up.curso"/> <c:out default="" value="${aula_actual.nombreCurso}" /> </strong>						</td>
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
   
	    <table class="tabla01" cellpadding="3" cellspacing="0" border="0" width="100%" style="table-layout: fixed;">
	    	<caption>Informe de ejecución del curso</caption>
	    	<tr class="tabla01_fila1">
	    		<td width="70" height="18" class="texto1">
	    			<b>Docente:</b>
	    		</td>
	    		<td class="texto1" colspan="3">
	    			<c:out value="${aula_actual.docenteNombre}"></c:out>
	    		</td>
	    		<td width="86" align="right" rowspan="4">
	    			<ct:Foto idUsuario="${aula_actual.docenteCodigo}" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<td height="18" class="texto" style="background-color: #ffffff;">
	    			<b>Programa:</b>
	    		</td>
	    		<td class="texto" colspan="3" style="background-color: #ffffff;">
	    			<c:out value="${aula_actual.nombreFormacion}" />
	    		</td>
	    	</tr>
	    	<tr class="tabla01_fila1">
	    		<td height="18" class="texto1">
	    			<b>Ciclo:</b>
	    		</td>
	    		<td class="texto1" colspan="3">
	    			<c:out value="${fn:substringAfter(fn:substringAfter(aula_actual.formacionSeccion, '-'), '-')}" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<td height="18" class="texto" style="background-color: #ffffff;">
	    			<b>Semestre:</b>
	    		</td>
	    		<td class="texto" style="background-color: #ffffff;">
	    			<c:out value="${periodo.nombre}"></c:out>
	    		</td>
	    		<td width="70" class="texto" style="background-color: #ffffff;">
	    			<b>Fecha:</b>
	    		</td>
	    		<td class="texto" style="background-color: #ffffff;"><jsp:useBean id="now" class="java.util.Date" />
	                <fmt:formatDate  value="${now}" type="both" pattern="dd-MM-yyyy" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan="5" style="text-align: justify; border-top: 1px solid #7EAAD1;">
	    			<p>
	    			El informe de ejecución de los cursos es una herramienta de medición, del Plan de Mejora Continua, cuyo objetivo es conocer las propuestas de mejora que plantea para el siguiente ciclo y aquellas que ha propuesto para el semestre que pasó.  
					</p>
					<p>
					<b>Ud. facilitará la recopilación siendo simple y directo al punto. Gracias.</b> 
	    			</p>
	    		</td>
	    	</tr>
	  
	    	<tr>
	    		<td colspan="5" class="tabla01_fila1" style="border-bottom: 1px solid #7EAAD1; border-top: 1px solid #7EAAD1;">
	    			<b>¿Qué mejoras, introdujo para mejorar el desarrollo del curso en el <c:out value="${aula_actual.nombrePeriodo}"></c:out>?</b>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan="5">
	    			<table cellpadding="3" cellspacing="0" border="0" width="100%" style="table-layout: fixed;">
	    				<tr>
	    					<td>
			    				<ul>
			    					<c:if test="${fn:length(mejoras)==0}">
			    						<li><i>Ninguna mejora</i></li>
			    					</c:if>
			    					<c:forEach var="i" items="${mejoras}" varStatus="fila">
			    						<li><c:out value="${i.texto}"/></li>
			    					</c:forEach>
			    				</ul>
	    					</td>
	    				</tr>
	    			</table>
	    		</td>
	    	</tr>
	    	
	    	<tr>
	    		<td colspan="5" class="tabla01_fila1" style="border-bottom: 1px solid #7EAAD1; border-top: 1px solid #7EAAD1;">
	    			<b>¿Qué mejoras propone para mejorar el desarrollo del curso en el siguiente periodo?</b>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan="5">
	    			<table cellpadding="3" cellspacing="0" border="0" width="100%" style="table-layout: fixed;">
	    				<tr>
	    					<td>
			    				<ul>
			    					<c:if test="${fn:length(propuestas)==0}">
			    						<li><i>Ninguna propuesta</i></li>
			    					</c:if>
			    					<c:forEach var="i" items="${propuestas}" varStatus="fila">
			    						<li><c:out value="${i.texto}"/></li>
			    					</c:forEach>
			    				</ul>
	    					</td>
	    				</tr>
	    			</table>
	    		</td>
	    	</tr>
	    	
	    	<tr>
	    		<td colspan="5" class="tabla01_fila1" style="border-bottom: 1px solid #7EAAD1; border-top: 1px solid #7EAAD1;">
	    			<b>Comentarios:</b>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan="5">
	    			<table cellpadding="3" cellspacing="0" border="0" width="100%" style="table-layout: fixed;">
	    				<tr>
	    					<td>
			    				<c:out value="${informe.observacion}" escapeXml="false"/>
	    					</td>
	    				</tr>
	    			</table>
	    		</td>
	    	</tr>
	    	<tr>
				<td colspan="5" class="tabla01_fila1" style="border-top: 1px solid #7EAAD1;">
					<span style="float:left;">
						<input type="button" class="form_button" value="Regresar" onclick="window.document.location =xGetContextPath() +'/aulavirtual/CargarHistoricos.action';" style="width: 80px;"/> 
					</span>
				</td>
			</tr>
	    </table>
  
  </div>  
  <div id="pie">&nbsp;</div>
</div>
</body>
</html>
