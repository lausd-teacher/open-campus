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
			src="<%=request.getContextPath()%>/js/wforms.js"></script>
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/wforms-localization-es.js"></script>
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

<body onload="expandir();  resize();" onresize="resize()" oonbeforeunload="return 'Recuerde que si sale de la ventana perderá los cambios que no haya guardado.';">
<div id="pop_up" style="width: 100%;">
		<div id="pop_up" style="width: 100%;">
			<div id="prin_01" style="width: 100%;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong><s:text name="aula.alumno.pop_up.curso"/> <c:out default="" value="${aula_actual.nombreCurso}" /> </strong>						</td>
						
						<td width="5%"><a href="#" class="salir" onClick="xPrintURL('<%=request.getContextPath()%>/aulavirtual/InformeReadOnly.action')" style="white-space: nowrap;"><s:text name="aula.alumno.pop_up.version_imprimir"/></a> </td>
						<td width="3%"><a href="#" class="salir" onClick="xPrintURL('<%=request.getContextPath()%>/aulavirtual/InformeReadOnly.action')"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="2%">|</td>
						
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
  <div id="pop_cuerpo" style="width: 96%; min-width: 500px;">
    
    <form method="post" action="<%=request.getContextPath()%>/aulavirtual/GuardarInforme.action" onsubmit="" > 
    
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
	    			<c:out value="${aula_actual.nombrePeriodo}"></c:out>
	    		</td>
	    		<td width="70" class="texto" style="background-color: #ffffff;">
	    			<b>Fecha:</b>
	    		</td>
	    		<td class="texto" style="background-color: #ffffff;"><jsp:useBean id="now" class="java.util.Date" />
	                <fmt:formatDate  value="${now}" type="both" pattern="dd-MM-yyyy" />
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan="5" style="text-align: justify; border-top: 1px solid #cccccc;">
	    			<p>
	    			El informe de ejecución de los cursos es una herramienta de medición, del Plan de Mejora Continua, cuyo objetivo es conocer las propuestas de mejora que plantea para el siguiente ciclo y aquellas que ha propuesto para el semestre que pasó.  
					</p>
					<p>
					<b>Ud. facilitará la recopilación siendo simple y directo al punto. Gracias.</b> 
	    			</p>
	    		</td>
	    	</tr>
	  
	    	<tr>
	    		<td colspan="5" class="tabla01_fila1" style="border-bottom: 1px solid #cccccc; border-top: 1px solid #cccccc;">
	    			<b>¿Qué mejoras, introdujo para mejorar el desarrollo del curso en el <c:out value="${aula_actual.nombrePeriodo}"></c:out>?</b>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan="5">
	    			<table cellpadding="3" cellspacing="0" border="0" width="100%" style="table-layout: fixed;" class="allrequired">
	    				<tr>
	    					<td>
			    				<ul>
			    					<c:if test="${fn:length(mejoras)==0}">
			    						<li id="div" class="repeat"><textarea rows="1" style="width: 90%; overflow:hidden;" id="mejora" name="mejora" class="form_text" onkeyup="pulsar(this)"></textarea></li>
			    					</c:if>
			    					<c:forEach var="i" items="${mejoras}" varStatus="fila">
			    						<c:choose>
			    							<c:when test="${fila.count == 1}">
			    								<li id="div" class="repeat"><textarea rows="1" style="width: 90%; overflow:hidden;" id="mejora" name="mejora" class="form_text" onkeyup="pulsar(this)"><c:out value="${i.texto}"/></textarea></li>
			    							</c:when>
			    							<c:otherwise>
			    								<li id="divClone" class="removeable"><textarea rows="1" style="width: 90%; overflow:hidden;" id="mejoraClone" name="mejoraClone" class="form_text" onkeyup="pulsar(this)"><c:out value="${i.texto}"/></textarea></li>
			    							</c:otherwise>
			    						</c:choose>
			    					</c:forEach>
			    				</ul>
			    				<c:if test="${fn:length(mejoras)!=0}">
    								<input id="div-RC" type="hidden" value="<c:out value="${fn:length(mejoras)}"/>" name="div-RC"/>
    							</c:if>
	    					</td>
	    				</tr>
	    			</table>
	    		</td>
	    	</tr>
	    	
	    	<tr>
	    		<td colspan="5" class="tabla01_fila1" style="border-bottom: 1px solid #cccccc; border-top: 1px solid #cccccc;">
	    			<b>¿Qué mejoras propone para mejorar el desarrollo del curso en el siguiente periodo?</b>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan="5">
	    			<table cellpadding="3" cellspacing="0" border="0" width="100%" style="table-layout: fixed;" class="allrequired">
	    				<tr>
	    					<td>
			    				<ul>
			    					<c:if test="${fn:length(propuestas)==0}">
			    						<li id="div2" class="repeat"><textarea rows="1" style="width: 90%; overflow:auto;" id="propuesta" name="propuesta" class="form_text" onkeyup="pulsar(this)"></textarea></li>
			    					</c:if>
			    					<c:forEach var="i" items="${propuestas}" varStatus="fila">
			    						<c:choose>
			    							<c:when test="${fila.count == 1}">
			    								<li id="div2" class="repeat"><textarea rows="1" style="width: 90%; overflow:auto;" id="propuesta" name="propuesta" class="form_text" onkeyup="pulsar(this)"><c:out value="${i.texto}"/></textarea></li>
			    							</c:when>
			    							<c:otherwise>
			    								<li id="div2Clone" class="removeable"><textarea rows="1" style="width: 90%; overflow:auto;" id="propuestaClone" name="propuestaClone" class="form_text" onkeyup="pulsar(this)"><c:out value="${i.texto}"/></textarea></li>
			    							</c:otherwise>
			    						</c:choose>
			    					</c:forEach>
			    				</ul>
			    				<c:if test="${fn:length(propuestas)!=0}">
    								<input id="div2-RC" type="hidden" value="<c:out value="${fn:length(propuestas)}"/>" name="div2-RC"/>
    							</c:if>
	    					</td>
	    				</tr>
	    			</table>
	    		</td>
	    	</tr>
	    	
	    	<tr>
	    		<td colspan="5" class="tabla01_fila1" style="border-bottom: 1px solid #cccccc; border-top: 1px solid #cccccc;">
	    			<b>Comentarios:</b>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td colspan="5">
	    			<table cellpadding="3" cellspacing="0" border="0" width="100%" style="table-layout: fixed;">
	    				<tr>
	    					<td>
			    				<textarea name="observacion" id="observacion" cols="92" rows="5" class="form_text required" style="width: 99%"><c:out value="${informe.observacion}"/></textarea>
			    				<script language="javascript1.2">
									
									var buttonName = new Array();
									buttonName[0]  = "bold";
									buttonName[1]  = "italic";
									buttonName[2]  = "underline";
									buttonName[3]  = "seperator";
									buttonName[4]  = "forecolor";
									buttonName[5]  = "backcolor";
									buttonName[6]  = "seperator";
									buttonName[7]  = "justifyleft";
									buttonName[8]  = "justifycenter";
									buttonName[9] = "justifyright";
									buttonName[10] = "seperator";
									buttonName[11] = "unorderedlist";
									buttonName[12] = "orderedlist";
									buttonName[13] = "outdent";
									buttonName[14] = "indent";
									
									generate_wysiwyg('observacion', '482', '140',true);
								</script>
	    					</td>
	    				</tr>
	    			</table>
	    		</td>
	    	</tr>
	    	<tr>
				<td colspan="5" class="tabla01_fila1" style="border-top: 1px solid #cccccc;">
					<span style="float:left;">
						<input type="button" class="form_button" value="Informes hist&oacute;ricos" onclick="window.document.location =xGetContextPath() +'/aulavirtual/CargarHistoricos.action';" style="width: 160px;"/> 
					</span>
					<span style="float:right;"> 
						&nbsp;<input type="submit" class="form_button" value="Guardar" style="width: 80px;" />
					</span>
					<span style="float:right;">
						<input type="button" class="form_button" value="Restaurar" onclick="window.document.location =xGetContextPath() +'/aulavirtual/Informe.action';" style="width: 80px;"/> 
					</span>
				</td>
			</tr>
	    </table>
    
    </form>
    
  </div>  
  <div id="pie">&nbsp;</div>
</div>
</body>
</html>
