<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">
<c:set var="contextPath" value='${pageContext.request.contextPath}'/>

<title>Upload</title>
<link href="<%=request.getContextPath()%>/estilos/estilos.css"
	rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=request.getContextPath()%>/docente/tindividual/control_tindividual.js"></script>
<style>
body {
	text-align: left;
	vertical-align: top;
	margin-top: 0px;
	margin-bottom: 0px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	background-color: #C5DAF6;
	margin-right: 0px;
	margin-left: 0px;
}
</style>
<script language="JavaScript">

function enviarArchivo() {
	var form = xGetElementById("formUpload");
	form.submit();
}

<%--function openExplorer(){--%>
<%--	var file = xGetElementById("file");--%>
<%--	var text = xGetElementById("file_text");--%>
<%--	file.click();--%>
<%--	text.value = file.value.substring(file.value.lastIndexOf("\\")+1);--%>
<%--}--%>

</script>

</head>
<body leftmargin="0" bgcolor="#C5DAF6">

<c:choose>
	<c:when test="${GRUPO != null}">
		<table border="0" cellpadding="0" cellspacing="0" width="500">
		<tr>
			<c:choose>
				<c:when test="${GRUPO.archivoNombre != null}">
				<td width="20" valign="middle">
					<a href="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/DescargarTrabajo.action?idGrupo=<c:out value="${idGrupo}" />&filename=<c:out value="${GRUPO.archivoNombre}" />">
						<img src="<%=request.getContextPath()%>/img/icon_download.gif" alt="Descargar" border="0" />
					</a>
				</td>
				<td width="160" valign="middle">
					<a href="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/DescargarTrabajo.action?idGrupo=<c:out value="${idGrupo}" />&filename=<c:out value="${GRUPO.archivoNombre}" />" style="color: black;">
						<strong><c:out value="${GRUPO.archivoNombre}"></c:out></strong>
					</a> 
					&nbsp; <small> (<fmt:formatNumber value="${GRUPO.archivoTamanio/1024}" maxFractionDigits="2"/> KB)</small>
				</td>
				</c:when>
				<c:otherwise>
					<td width="180" valign="middle">
					<b><i>Documento pendiente</i></b>
				</td>
				</c:otherwise>
			</c:choose>
			<td align="right" style="padding-right: 5px">
				<small><b>Max. 5MB</b></small>
			</td>
			<td valign="middle" align="right" width="100">
			<form action="<c:out value='${contextPath}'/>/aulavirtual/tgrupal/SubirTrabajo.action" 
			id="formUpload" enctype="multipart/form-data" method="post" >
				<input type="hidden" value="<c:out value="${idGrupo}"></c:out>" name="idGrupo" />
				<input id="file" name="file" type="file" onChange ="enviarArchivo()" class="form_button" style="text-align: left;"/>
			</form>
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test="${param.idGrupo != null}">
		<input type="button" value="Actualizar Trabajo" onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/tgrupal/VerTrabajo.action?idGrupo=<c:out value="${param.idGrupo}" />'" class="form_button"/>
	</c:when>
	<c:otherwise>
		<font color="#FF0000">Error al subir el archivo</font> &nbsp;&nbsp;&nbsp;
		<a href="javascript:history.back(1)" style="color: black;">Reintentar</a>
<%--		<s:actionerror/>--%>
	</c:otherwise>
</c:choose>

</body>
</html>