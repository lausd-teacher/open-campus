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
			src="<%=request.getContextPath()%>/js/aula_virtual.js"></script>			
<script language="javascript" type="text/javascript"	
			src="<%=request.getContextPath()%>/docente/informe/informe.js"></script>

<body>
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
	    	<caption>Hist&oacute;rico de informes para el curso <c:out value="${aula_actual.nombreCurso}"></c:out></caption>
	    	
	    	<c:forEach items="${periodos}" var="periodo" varStatus="fila">
		    	<tr>
		    		<td class="tabla01_fila1" style="border-bottom: 1px solid #7EAAD1; border-top: 1px solid #7EAAD1;">
		    			<b><c:out value="${fila.count}"></c:out>.- <a href="<%=request.getContextPath()%>/aulavirtual/CargarDetalleHistorico.action?idperiodo=<c:out value="${periodo.idPeriodo}"/>" 
		    				style="color: black;"> <c:out value="${periodo.nombre}"></c:out></a></b>
		    		</td>
		    		<td align="center" class="tabla01_fila1" style="border-bottom: 1px solid #7EAAD1; border-top: 1px solid #7EAAD1;">
		    			<c:out value="${periodo.fechaInicioToString}"/>
		    		</td>
		    		<td align="center" class="tabla01_fila1" style="border-bottom: 1px solid #7EAAD1; border-top: 1px solid #7EAAD1;">
		    			<c:out value="${periodo.fechaFinToString}" />
		    		</td>
		    	</tr>
	    	</c:forEach>
	    	<tr>
	    		<td colspan="3">
	    			&nbsp;
	    		</td>
	    	</tr>
	    	
	    	<tr>
				<td colspan="3" class="tabla01_fila1" style="border-top: 1px solid #7EAAD1;">
					<span style="float:left;">
						<input type="button" class="form_button" value="Regresar" onclick="window.document.location =xGetContextPath() +'/aulavirtual/Informe.action';" style="width: 80px;"/> 
					</span>
				</td>
			</tr>
	    </table>
   
  </div>  
  <div id="pie">&nbsp;</div>
</div>
</body>
</html>
