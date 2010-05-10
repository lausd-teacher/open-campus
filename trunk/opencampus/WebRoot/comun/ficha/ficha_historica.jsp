<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@  page import="edu.opencampus.lms.modelo.ficha.FichaHistorica"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="java.util.Collection"%>
<%
	Collection<FichaHistorica> cursos = (Collection<FichaHistorica>) request
			.getAttribute("cursos");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" language="javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/comun/ficha/ficha_historica.js'>
		</script>
		<script>
			var context = '<%=request.getContextPath()%>';
		</script>
	</head>
	<body>
		<div id="contenedor">
			
			<%@include file="../bienvenida.jsp"%>

			<div id="cuerpo">
				<div id="principal">
					<%
						if (cursos.size() != 0) {
					%>
					<table border="0" cellspacing="0" cellpadding="0" width="970"
						bgcolor="#E5EFF8" class="tabla01">
						<tr class="fon_tit_curso">
							<td height="20" colspan="8" class="tit_cab">
								<s:text name="portal.menu.historicos"/>
							</td>
						</tr>
						<tr bgcolor="#ffffff">
							<td width="20" height="18" class="moduloAbajo">
								&nbsp;
							</td>
							<td class="moduloAbajo">
								<strong><s:text name="portal.historicos.nombre"/></strong>
							</td>
							<td width="100" class="moduloAbajo" >
								<strong><s:text name="portal.historicos.anio"/></strong>
							</td>
							<td width="55" class="moduloAbajo">
								<strong><s:text name="portal.historicos.familia"/></strong>
							</td>
							<td width="200" class="moduloAbajo">
								<strong><s:text name="portal.historicos.formacion"/></strong>
							</td>
							<td width="80" class="moduloAbajo">
								<strong><s:text name="portal.historicos.periodo"/></strong>
							</td>
							<td width="80" class="moduloAbajo">
								<strong><s:text name="portal.historicos.fechainicio"/></strong>
							</td>
							<td width="80" class="moduloAbajo1" align="center">
								<strong><s:text name="portal.historicos.fechafin"/></strong>
							</td>
						</tr>
						<%
							int cantidad = 1;
								for (FichaHistorica ficha : cursos) {
						%>
						<tr <%if(cantidad%2==0) {%> bgcolor="#ffffff" <%}%>
							style="color: black; cursor: pointer;"
							<%if(Constante.ROL_CAMPUS_AULAVIRTUAL_DOCENTE==ficha.getIdRol()  ){ %>
							onclick="javascript:abrirAuditoria('<%=request.getContextPath()%>','<%=ficha.getIdMatricula()%>');"
							<%}else{ %>
							onclick="javascript:abrirReporte('<%=request.getContextPath()%>','<%=ficha.getIdMatricula()%>');"
							<%} %>
							class="selecionando_tr">
							<td height="22" align="center" class="texto1">
								<%=cantidad++%>
								.
							</td>
							<td height="22" align="left" class="texto1">
								&nbsp;&nbsp;
								<%=ficha.getNombreFicha()%>
								
							</td>
							<td height="22" align="left" class="texto1">
								&nbsp;&nbsp;<%
									if (0 != ficha.getCiclo()) {
												out.print(ficha.getCiclo());
												if (0 < ficha.getSecciones().length()) {
													out.print(" - " + ficha.getSecciones());
												}												
									}
								%>
							</td>
							<td height="22" align="center" class="texto1">
								<%if("VIRTUAL".equals(ficha.getFamilia())){
									out.print("TV");
								}else{
									if("PINTGR".equals(ficha.getFamilia())){
									out.print("PI");
									}else{
										out.print(ficha.getFamilia());
									}
								} %>
							</td>
							<td height="22" align="left" class="texto1">
								&nbsp;&nbsp;<%=ficha.getFormacion() %>
							</td>
							<td height="22" align="center" class="texto1">
								<%=ficha.getNombrePeriodo()%>
							</td>
							<td height="22" align="center" class="texto1">
								<%=ficha.getFechaInicio()%>
							</td>
							<td height="22" align="center" class="texto1">
								<%=ficha.getFechaFin()%>
							</td>
						</tr>
						<%
							}
						%>
					</table>


					<%
						} else {
					%>
					<table width="750" border="0" cellpadding="3" cellspacing="0"
						class="bor_tabla">
						<tr class="fon_cab">
							<td height="15" class="tit_cab">
								<s:text name="portal.menu.historicos"/>
							</td>
						</tr>
						<tr>
							<td align="center" class="moduloAbajo1">
								<strong><s:text name="portal.historicos.vacio"/></strong>
							</td>
						</tr>
					</table>
					<%
						}
					%>
				</div>
			</div>
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
		<script type="text/javascript">
			resizeHeight();
		</script>
	</body>
</html>
