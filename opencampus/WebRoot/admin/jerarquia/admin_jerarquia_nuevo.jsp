<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
			Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
		<META HTTP-EQUIV="EXPIRES" CONTENT="-1">
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
	</head>
	<%@include file="../../comun/capas/reloj.jsp"%>
	<body onLoad="mostrarReloj()">
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo">
				<div id="pop_cuerpo">
					<s:include value="/error_message.jsp"/>
					<form name="form1" method="post"
						action="<%=request.getContextPath()%>/admin/jerarquia/Crear.action">
						<table width="950" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
							<tr class="fon_cab">
								<td colspan="6" class="tit_cab">
									Jerarqu&iacute;a Nuevo
								</td>
							</tr>
							<tr height="23px;">
								<td width="15" >
									&nbsp;
								</td>
								<td width="95"  class="texto1">
									&nbsp;
								</td>
								<td width="10">
									&nbsp;
								</td>
								<td width="656">
									&nbsp;
								</td>
							</tr>
							<tr class="tabla01_fila1">
								<td width="15" >
									&nbsp;
								</td>
								<td width="95" class="texto1">
									<strong class="textstatic">Predecesor:</strong>
								</td>
								<td width="10">
									&nbsp;
								</td>
								<td width="656">
									<ct:JerarquiaRutaCompleta aguja="0" nombre="idPredecesor" />
								</td>
							</tr>
							<tr class="tabla01_fila1">
								<td class="fon_color01">
									&nbsp;
								</td>
								<td class="texto1">
									<strong class="textstatic">Nombre:</strong>
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<input type="text" name="nombre" maxlength="64" class="form_text" size="50">
								</td>
							</tr>
							<!-- tr height="23px;">
								<td >
									&nbsp;
								</td>
								<td class="texto1">
									<strong class="textstatic">Tipo </strong>
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<select name="tipo" class="form_text">
										<option value="G">General</option>
										<option value="N">Nodo</option>
									</select>
								</td>
							</tr-->
							<tr class="tabla01_fila1">
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
									<input type="submit"  value="Enviar" class="form_button">
									&nbsp;
									<input type="button" name="cancelar" value="Cancelar" class="form_button"
										onClick="document.location.href='<%=request.getContextPath()%>/admin/jerarquia/Buscar.action'">
								</td>
							</tr>
						</table>
					</form>
				</div>
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
