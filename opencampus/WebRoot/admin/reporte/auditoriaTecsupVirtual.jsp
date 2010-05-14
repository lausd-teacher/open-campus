<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="edu.opencampus.lms.util.Formato"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@  page import="java.util.Collection"%>
<%@  page import="edu.opencampus.lms.modelo.reporte.opencampusVirtualReporte"%>

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
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/aula_virtual.js'></script>
<%
	Collection<opencampusVirtualReporte> auditoria = (Collection<opencampusVirtualReporte>)request.getAttribute("AUDITORIA");
 %>
</head>
	<body link="#000000" vlink="#000000" alink="#000000" >
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
				<c:when test="${fn:length(AUDITORIA)>0}">
					<div style="text-align: center; padding: 20px;">
						<div><img src="<%=request.getContextPath()%>/img/logoopencampusVirtual.jpg" alt="O opencampus Virtual"></div>
						<div>
							<span style="font-size: 12px;"><strong>Auditoria <c:out value="${PERIODO.nombre}"/></strong></span>
							<br/>
							<span><strong>Fecha de Inicio: <c:out value="${PERIODO.fechaInicioToString}"/> - Fecha de Cierre: <c:out value="${PERIODO.fechaFinToString}"/></strong></span>
							<br/>&nbsp;<br/>
							<span>Auditoria de <c:out value="${busquedaFecha1}"/> a <c:out value="${busquedaFecha2}"/></span>
						</div>
					</div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" >
				    	<tr>
						  <td height="15">
							  <table width="100%" border="1" cellpadding="3" cellspacing="0" class="tabla01">	  
	                            <tr class="fon_color01">
	                              <td width="16" align="center" class="foro_Modulo">&nbsp;</td>
	                              <td align="center" class="foro_Modulo"><strong>Curso</strong> </td>
	                              <td align="center" class="foro_Modulo"><strong>Docente</strong></td>
	                              <td align="center" class="foro_Modulo" width="16">&nbsp;</td>
	                              <td width="50" align="center" class="foro_Modulo"><strong>Debates</strong> </td>
	                              <%
                           				GregorianCalendar inicio = Formato.setDateDeJSP((String)request.getAttribute("busquedaFecha1"));
                           				GregorianCalendar fin = Formato.setDateDeJSP((String)request.getAttribute("busquedaFecha2"));
                           				fin.add(Calendar.DATE,1);
                           				
                           				for(int i=inicio.get(Calendar.DATE); inicio.before(fin);inicio.add(Calendar.DATE,1)){

											String nomDia = Formato.formatoTexto((new SimpleDateFormat("EEE")).format(inicio.getTime()));
											int numDia = inicio.get(Calendar.DATE);
									%>
											<td width="20"  align="center" class="foro_Modulo" style="padding: 0px; font-size: 9px;">
												<div class="foro_ModuloAbajo1" style="padding: 1px; width: 100%;"><%=nomDia %></div>
												<div><%=numDia %></div>
											</td>
									<%
										}
                           			%>
	                              
	                            </tr>
	                            <%
	                            	for(opencampusVirtualReporte item : auditoria){
	                            	
	                            	%>
	                            	
	                            		<tr style='<%if(item.getDebates()==0){%>color: red; font-weight: bold;<%} %>'>
	                            			<td class="tabla01"><img src="<%=request.getContextPath()%>/img/icon_libro.gif" alt="IdFicha: <%=item.getIdFicha() %>"></td>
	                            			<td class="tabla01"><%=item.getCurso() %></td>
	                            			<td class="tabla01"><a style="text-decoration: none; <%if(item.getDebates()==0){%>color: red; font-weight: bold;<%} %>" href="javascript:void(0)"
	                            				onclick="javascript:abrirReporte('<%=request.getContextPath()%>/reporte/AuditoriaSemanal.action?idMatricula=<%=item.getIdDocente() %>&busquedaFecha1=<c:out value="${busquedaFecha1}"/>&busquedaFecha2=<c:out value="${busquedaFecha2}"/>')" title="ID Matricula:<%=item.getIdDocente() %>"><%=item.getDocente() %></a></td>
	                            			<td class="tabla01"><a href="<%=request.getContextPath()%>/reporte/AuditoriaSemanalXLS.action?idMatricula=<%=item.getIdDocente() %>&busquedaFecha1=<c:out value="${busquedaFecha1}"/>&busquedaFecha2=<c:out value="${busquedaFecha2}"/>" title="Descargar Excel"><img src="<%=request.getContextPath()%>/img/icon_download.gif" border="0" alt="Descargar Auditoria"></a></td>
	                            			<td align="center" class="tabla01"><%=item.getDebates() %></td>
	                            			<%
	                            				inicio = Formato.setDateDeJSP((String)request.getAttribute("busquedaFecha1"));
	                            				
	                            				for(int i=inicio.get(Calendar.DATE); inicio.before(fin);inicio.add(Calendar.DATE,1)){
	                            					if(item.isDiaDeIngreso(Formato.setBaseDatosDeDate(inicio))){
														out.print("<td style=\"background-color:#cccccc;\" class=\"tabla01\">&nbsp;</td>");
													}else{
														out.print("<td class=\"tabla01\">&nbsp;</td>");
													}
												}
	                            			 %>
	                            			
	                            		</tr>
	                            	
	                            	<%
	                            	
	                            	}
	                             %>
	                          </table>
                          </td>
					  </tr>					
					  <tr>
					  	<td height="15"><a href="<%=request.getContextPath()%>/reporte/AuditoriaopencampusVirtualXLS.action?busquedaPeriodo=<c:out value="${busquedaPeriodo}"/>&busquedaFecha1=<c:out value="${busquedaFecha1}"/>&busquedaFecha2=<c:out value="${busquedaFecha2}"/>"><img src="<%=request.getContextPath()%>/img/icon_download.gif" border="0" alt="Descargar Auditoria"> Descargar Excel</a></td>
					  </tr>
			  		</table>
			  </c:when>
			  <c:otherwise>
			  	<center><h5>No hay cursos para este per&iacute;odo</h5></center>
			  </c:otherwise>
			  </c:choose>
			</div>			
			<div id="pie">
			  <%@include file="../../comun/pie.jsp"%>
			</div>	
		</div>
	
	</body>
</html>
