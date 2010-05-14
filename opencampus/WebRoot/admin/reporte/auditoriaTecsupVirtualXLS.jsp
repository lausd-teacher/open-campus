<%
	response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	edu.opencampus.lms.modelo.Periodo periodo = (edu.opencampus.lms.modelo.Periodo)request.getAttribute("PERIODO");;
	response.setHeader( "Content-Disposition", "inline;filename=\"Auditoria_TV_"+((periodo!=null)?periodo.getIdPeriodo():0)+".xls\"" );
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page language="java" contentType="application/vnd.ms-excel; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<title><s:text name="titulo.campus.virtual" />
</title>
<%
	Collection<opencampusVirtualReporte> auditoria = (Collection<opencampusVirtualReporte>)request.getAttribute("AUDITORIA");
 %>
</head>
	<body link="#000000" vlink="#000000" alink="#000000" >
		<div id="pop_up" style="width: 100%;">
			<div id="pop_cuerpo">
				<c:choose>
				<c:when test="${fn:length(AUDITORIA)>0}">
					<div style="text-align: center; padding: 20px;">
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
	                              <td align="center" class="foro_Modulo"><strong>Departamento</strong></td>
	                              <td align="center" class="foro_Modulo"><strong>Curso</strong> </td>
	                              <td align="center" class="foro_Modulo"><strong>Docente</strong></td>
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
	                            	int fila = 0;
	                            	for(opencampusVirtualReporte item : auditoria){
	                            	
	                            	%>
	                            	
	                            		<tr style='<%if(item.getDebates()==0){%>color: red; font-weight: bold;<%} %>'>
	                            			<td class="tabla01"><%=++fila %></td>
	                            			<td class="tabla01"><%=item.getDeparatamento() %></td>
	                            			<td class="tabla01"><%=item.getCurso() %></td>
	                            			<td class="tabla01"><%=item.getDocente() %></td>
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
