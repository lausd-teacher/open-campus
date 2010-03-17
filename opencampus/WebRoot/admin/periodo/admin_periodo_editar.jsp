<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.tecsup.lms.util.Constante,edu.tecsup.lms.modelo.Jerarquia"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario,edu.tecsup.lms.modelo.Periodo"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
			Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/jscalendar/calendar-style.css" />
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jscalendar/calendar-setup.js"></script>
		<script>
function catcalc(cal) {
	//alert("demo");
}
</script>
	</head>
	<%@include file="/comun/capas/reloj.jsp"%>
	<body onLoad="mostrarReloj();">
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="pop_cuerpo">
			<s:include value="/error_message.jsp"/>
				<form name="form1" method="post"
					action="<%=request.getContextPath()%>/admin/periodo/Modificar.action">
					<table width="950" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
						<caption class="fon_cab tit_cab">Per&iacute;odo Editar</caption>
						<tr>
							<td width="5">
								&nbsp;
							</td>
							<td width="100" class="texto1">
								<strong>Id. Per&iacute;odo:</strong>
							</td>
							<td width="5">
								&nbsp;
							</td>
							<td>
								${periodo.idPeriodo}
								<input type="hidden" name="idPeriodo" value="${periodo.idPeriodo}" />
							</td>
						</tr>
						
						<tr class="tabla01_fila1">
							<td >
								&nbsp;
							</td>
							<td class="texto1">
								<strong>Nombre:</strong>
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<input id="nombre" name="nombre" type="text" size="20" class="form_text"
									value="${periodo.nombre}" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="texto1">
								<strong>D&iacute;as de edici&oacute;n:</strong>
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<select name="diasEdicion" class="form_text">
									<c:forEach begin="1" end="31" step="1" var="i">
										<option value="<c:out value='${i}'/>"  
											<c:if test="${periodo.diasEdicion == i}">selected</c:if>
										>
										<c:out value='${i}'/>
										</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr class="tabla01_fila1">
							<td >
								&nbsp;
							</td>
							<td class="texto1">
								<strong>Fecha de inicio:</strong>
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<input type="text" id="fechaInicio" name="fechaInicio" size="40" class="form_text"
									value="${periodo.fechaInicioToString}" />
							</td>
						</tr>
						<tr>
							<td >
								&nbsp;
							</td>
							<td class="texto1">
								<strong>Fecha de fin:</strong>
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<input type="text" id="fechaFin" name="fechaFin" size="40" class="form_text"
									value="${periodo.fechaFinToString}" />
							</td>
						</tr>
						<tr class="tabla01_fila1">
							<td >
								&nbsp;
							</td>
							<td class="texto1">
								<strong> D&iacute;as de revisi&oacute;n:</strong>
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<select name="diasRevision" class="form_text">
									<c:forEach begin="1" end="31" step="1" var="i">
										<option value="<c:out value='${i}'/>"  
											<c:if test="${periodo.diasRevision == i}">selected</c:if>
										>
										<c:out value='${i}'/>
										</option>
									</c:forEach>
								</select>
							</td>


						</tr>
						
						<tr>
							<td >
								&nbsp;
							</td>
							<td class="texto1">
								&nbsp;
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<input type="button" name="cancelar" value="Cancelar" class="form_button"
									onClick="document.location.href='<%=request.getContextPath()%>/admin/periodo/Buscar.action'">
								&nbsp;&nbsp;&nbsp;
								<input type="submit" value="Guardar" class="form_button">
							</td>
						</tr>
						
					</table>
				</form>
				<script language="JavaScript" type="text/javascript">
				Calendar.setup({inputField:"fechaInicio", ifFormat:"%d-%m-%Y", showsTime:true, singleClick:true, onUpdate:catcalc});
				Calendar.setup({inputField:"fechaFin", ifFormat:"%d-%m-%Y", showsTime:true, singleClick:true, onUpdate:catcalc});				
	</script>
			</div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>
		</div>
		<script type="text/javascript">
			resizeHeight();
		</script>
	</body>
</html>
