<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		
	</head>
	<body>
		<div id="pop_up" style="width: 100%;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong><s:text name="aula.alumno.pop_up.curso"/> <c:out value="${silabo.curso.nombre}"/></strong></td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.print()"><s:text name="aula.alumno.pop_up.imprimir"/></a></td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13"
									height="13" border="0" /></a></td>
						<td width="2%">|</td>
						<td width="4%"><a href="#" class="salir" onClick="window.close()"><s:text name="aula.alumno.pop_up.cerrar"/></a></td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>						</td>
					</tr>
				</table>
		  </div>
			<div id="pop_cuerpo">
				<table width="100%" align="center" cellpadding="3" cellspacing="0"
					bgcolor="#FFFFFF" class="bor_tabla" border="0">
					<tr align="left" class="fon_cab">
						<td  class="tit_cab">
							<s:text name="aula.alumno.plan_docente.titulo"/>
						</td>
					</tr>
					
					<tr>
						<td  valign="top" class="fon_color01">
							<strong class="textstatic"> <s:text name="aula.alumno.plan_docente.silabo"/></strong>
						</td>
					</tr>
					<tr>
						<td valign="top" class="textstatic" style="padding-left: 20px;" >
								<c:out value="${silabo.descripcion}"></c:out>
						</td>
					</tr>
					
					<tr>
						<td  valign="top" class="fon_color01">
							<strong class="textstatic"> <s:text name="aula.alumno.plan_docente.curso"/></strong>
						</td>
					</tr>
					<tr>
						<td valign="top" class="textstatic" style="padding-left: 20px;" >
								<c:out value="${silabo.curso.nombre}"></c:out>
						</td>
					</tr>
					
					<tr>
						<td  valign="top" class="fon_color01">
							<strong class="textstatic"> <s:text name="aula.alumno.plan_docente.programa"/></strong>
						</td>
					</tr>
					<tr>
						<td valign="top" class="textstatic" style="padding-left: 20px;" >
								<c:out value="${silabo.curso.jerarquia.nombre}"></c:out>
						</td>
					</tr>
					
					<c:if test="${silabo.introduccion != null && silabo.introduccion != ''}">
						<tr>
							<td  valign="top" class="fon_color01">
								<strong class="textstatic"> <s:text name="aula.alumno.plan_docente.introduccion"/></strong>
							</td>
						</tr>
						<tr>
							<td valign="top" class="textstatic" style="padding-left: 20px;" >
									<c:out value="${silabo.introduccion}"></c:out>
							</td>
						</tr>
					</c:if>
					<%
							if(false){
									//if(null!=plan.getObjetivoGeneral() && 0<plan.getObjetivoGeneral().length()){
					%>

					<tr class="fon_color01">
						<td  align="left">
							<strong class="textstatic"><s:text name="aula.alumno.plan_docente.objetivos_generales"/></strong>
						</td>
					</tr>
					<tr>
						<td height="10" align="left"></td>
					</tr>
					<tr>
						<td align="center" style="padding-left: 20px;">
							<table width="95%" height="95%" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td style="text-align:justify">
										<%//=plan.getObjetivoGeneral()%>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td height="10" align="left"></td>
					</tr>
					<%
									//}
					%>
					<tr class="fon_color01">
						<td align="left">
							<strong class="textstatic"><s:text name="aula.alumno.plan_docente.objetivos_especificos"/></strong>
						</td>
					</tr>
					<tr>
						<td height="10" align="left"></td>
					</tr>
					<tr>
						<td align="center">
							<table width="95%" height="95%" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td style="text-align:justify">
										<ol>
											<li>
												<%//=plan.getObjetivo().replaceAll("\n","</li><br><li>")%>
											</li>
										</ol>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<%} %>


					<c:if test="${fn:length(silabo.unidades) != 0}">
						<tr class="fon_color01">
							<td align="left">
								<strong class="textstatic"><s:text name="aula.alumno.plan_docente.unidades"/></strong>
							</td>
						</tr>
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="3" cellspacing="0">
									<c:forEach items="${silabo.unidades}" var="unidad" varStatus="status">
										<tr <c:if test="${status.count%2==0}"> class="fon_color01" </c:if>>
											<td width="31" class="plan" align="right"><c:out value="${status.count}"></c:out>.</td>
											<td width="455" height="17" align="left"><c:out value="${unidad.nombreCompleto}"></c:out> </td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</tr>
					</c:if>
					
				</table>
			</div>
			<div id="pie">
				&nbsp;
			</div>
		</div>
	</body>
</html>
