<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><s:text name="titulo.campus.virtual" />
</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript" 
			src='<%=request.getContextPath()%>/js/util.js'></script>

</head>
	<body>
		<div id="pop_up" style="width: 100%;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%" style="color:black;">
							<strong>Auditoria opencampus Virtual </strong>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.print()">Imprimir</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif"
									width="13" height="13" border="0" />
							</a>
						</td>
						<td width="2%">
							|
						</td>
						<td width="4%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo">
				<c:choose>
				<c:when test="${fn:length(REPORTE)>0}">
					<div style="text-align: center; padding: 20px;">
						<div><img src="<%=request.getContextPath()%>/img/logoopencampusVirtual.jpg" alt="O opencampus Virtual"></div>
						<div>
							<span style="font-size: 12px;"><strong>Reporte <c:out value="${PERIODO.nombre}"/></strong></span>
							<br/>
							<span><strong>Fecha de Inicio: <c:out value="${PERIODO.fechaInicioToString}"/> - Fecha de Cierre: <c:out value="${PERIODO.fechaFinToString}"/></strong></span>
						</div>
					</div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				    <tr>
						  <td height="15">
							  <table width="100%" border="1" cellpadding="3" cellspacing="0" class="tabla01">	  
	                            <tr class="fon_color01">
	                              <td width="16" align="center" class="foro_Modulo">&nbsp;</td>
	                              <td align="center" class="foro_Modulo"><strong>Estudiante</strong> </td>
	                              <td align="center" class="foro_Modulo"><strong>Curso</strong></td>
	                              <td width="50" align="center" class="foro_Modulo"><strong>Estado</strong> </td>
	                            </tr>
	                            <c:forEach items="${REPORTE}" var="reporte">
                            		<tr>
                            			<td class="tabla01"><img src="<%=request.getContextPath()%>/img/icon_user.jpg" alt="IdMatricula: <c:out value="${reporte.idFicha}"/>"></td>
                            			<td class="tabla01"><c:out value="${reporte.docente}"/></td>
                            			<td class="tabla01"><c:out value="${reporte.curso}"/></td>
                            			<td align="center" class="tabla01" style="font-weight: bold;"><c:choose> <c:when test="${reporte.estado}">APTO</c:when><c:otherwise><span style="color:#FF0000;">NO APTO</span></c:otherwise> </c:choose></td>
                            		</tr>
                            	</c:forEach>
	                          </table>
                          </td>
					  </tr>					
					  
			  	</table>
			  </c:when>
			  <c:otherwise>
			  	<center><h5>No hay estudiantes en este periodo.</h5></center>
			  </c:otherwise>
			  </c:choose>
			</div>			
			<div id="pie">
			  <%@include file="../../comun/pie.jsp"%>
			</div>	
		</div>
	
	</body>
</html>
