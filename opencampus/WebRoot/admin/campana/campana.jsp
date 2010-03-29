<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:set var="contextPath" value='${pageContext.request.contextPath}'/>

<%
Usuario usuario = (Usuario)request.getSession().getAttribute(Constante.USUARIO_ACTUAL);
 %>
 
<title><s:text name="titulo.campus.virtual" /></title>

<link href="<c:out value='${contextPath}'/>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/saludo.js"></script>
<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
<script language="javascript" type="text/javascript" 
			src="<c:out value='${contextPath}'/>/js/util.js"></script>
<script language="javascript" type="text/javascript" 
			src="<c:out value='${contextPath}'/>/admin/campana/campana.js"></script>

</head>

<body>

<div id="contenedor">
  <s:include value="/comun/bienvenida.jsp"></s:include>
  
  <!-- *************** CONTENIDO ********************* -->
  
  <div id="cuerpo" style="background-color: white;">
	<div id="pop_cuerpo">
	
	<form method="post" onsubmit="return false">
		<table width="950" border="0" align="center" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
			<tr id="tr_mensajes_advertencia"
				style="visibility: hidden; display: none;">
				<td align="center" height="35" style="background-color: white;">
					<table cellpadding="0" cellspacing="0" bgcolor="white">
						<tr>
							<td width="5" height="5" id="td_1_mensaje"
								style="background-color: white ;background-image: url('../../img/punto_rojo.png'); background-position: left top;" />
							<td style="background-color: #CC0000;" id="td_2_mensaje" />
							<td id="td_3_mensaje"
								style="background-image: url('../../img/punto_rojo.png'); background-position: right top;" />
						</tr>
						<tr>
							<td style="background-color: #CC0000;" width="5"
								id="td_4_mensaje" />
							<td style="background-color: #CC0000;" id="td_5_mensaje"
								align="center">
								<b id="tr_mensajes_mensaje"
									style="color: white; padding-left: 10px; text-align: center;">&nbsp;
								</b>
							</td>
							<td style="background-color: #CC0000;" width="5"
								id="td_6_mensaje" />
						</tr>
						<tr>
							<td height="5" id="td_7_mensaje"
								style="background-image: url('../../img/punto_rojo.png'); background-position: left bottom;" />
							<td style="background-color: #CC0000;" id="td_8_mensaje" />
							<td id="td_9_mensaje"
								style="background-image: url('../../img/punto_rojo.png'); background-position: right bottom;" />
						</tr>
					</table>
					<table height="5px">
						<tr>
							<td />
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	
	<script type="text/javascript">
		<c:if test="${mensaje_texto != null}">
			mostrarMensajeErrorCompleto('<c:out value="${mensaje_texto}"/>','<c:out value="${mensaje_tipo}"/>');
		</c:if>	
	</script>

	<form method="post">
		<table width="950" border="0" align="center" cellpadding="3" cellspacing="0" class="tabla01" style="table-layout: fixed;">
	      <caption><s:text name="portal.menu.campana"></s:text></caption>
	      <tr class="tabla01_subEncabezado">
	        <th width="20" class="modulo">&nbsp;</th>
	        <th class="moduloAbajo">Periodo</th>
	        <th width="85" align="center" class="moduloAbajo">Fecha Inicio</th>
	        <th width="85" align="center" class="moduloAbajo">Fecha Fin</th>
	        <th width="100" align="center" class="moduloAbajo">&nbsp;</th>
	        <th width="100" align="center" class="moduloAbajo">&nbsp;</th>
	        <th width="100" align="center" class="moduloAbajo">&nbsp;</th>
	        <th width="100" align="center" class="moduloAbajo">&nbsp;</th>
	      </tr>
		<c:forEach var="periodo" items='${PERIODO_PREDETERMINADA}' varStatus="fila">
			<tr <c:choose><c:when test="${fila.count%2==0}"> class="tabla01_fila2" </c:when><c:otherwise>class="tabla01_fila1"</c:otherwise> </c:choose> >
				<td align="center" class="bor_der_cur"><c:out value='${fila.count}' /></td>
				<td class="bor_der_cur"><c:out value='${periodo.idPeriodo}'/> - <c:out value='${periodo.nombre}'/></td>
				<td align="center" class="bor_der_cur"><c:out value='${periodo.fechaInicioToString}'/></td>
				<td align="center" class="bor_der_cur"><c:out value='${periodo.fechaFinToString}'/></td>
				<td align="center" class="bor_der_cur">
					<input type="hidden" name="select_cursos" value="100">
					<input type="submit" value="Sincronizar" class="form_button" style="width: 90px;"
					onclick="this.form.action='<%=request.getContextPath()%>/admin/ficha/Sincronizar.action'; return true;"/>
				</td>
				<td align="center" class="bor_der_cur">
					<input type="submit" value="Enviar Correo" class="form_button" style="width: 90px;"
					onclick="this.form.action='<%=request.getContextPath()%>/admin/ficha/ConstanciaCorreo.action'; return true;"/>
				</td>
				<td align="center" class="bor_der_cur">
					<input type="submit" value="Enviar Aviso" class="form_button" style="width: 90px;"
					onclick="abrirVentanaFlotante('<%=request.getContextPath()%>/admin/ficha/NuevoMensaje.action?busquedaPeriodo=<c:out value='${periodo.idPeriodo}'/>','AulaVirtualVitrina',550,500)">
				</td>
				<td align="center" class="bor_der_cur">
					<input type="submit" value="Publicar" class="form_button" style="width: 90px;"
					onclick="abrirVentanaFlotante('<%=request.getContextPath()%>/admin/ficha/NuevoRecurso.action?busquedaPeriodo=<c:out value='${periodo.idPeriodo}'/>','AulaVirtualLectura',550,500)">
				</td>
			</tr>
		</c:forEach>
		</table>
	</form>
	</div>
</div>
  <div id="pie">
    <%@include file="../../comun/pie.jsp" %>
  </div>
  
  <!-- *************** FIN CONTENIDO ********************* -->
  
</div>
</body>
</html>
