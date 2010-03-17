<%
	response.setContentType("text/html;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">

<title>Upload</title>
		
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/docente/tindividual/control_tindividual.js"></script>
			
<%
	//System.out.println("ContentType: "+request.getContentType());
 %>
<script language="JavaScript">
var filename = '<c:out value='${FILENAME}' default=""/>';
var filesize = '<c:out value='${FILESIZE}' default=""/>';

if(filename != '' && filesize != ''){
	window.top.document.getElementById('form_nomArchivo').value = filename;
	window.top.document.getElementById('form_tamArchivo').value = filesize;
}

function enviarArchivo() {
	document.file.submit();
}

function openExplorer(){
	var form = document.file;
	form.file.click();
}

</script>

</head>
<body leftmargin="0" bgcolor="#e7efff">

<%--	<s:form action="/docente/tindividual/SubirTrabajo.action" enctype="multipart/form-data" method="post">--%>
<%--		<s:file name="file" size="56" />--%>
<%--		<s:submit value="O"/>--%>
<%--	</s:form>--%>

<table border="0" cellpadding="0" cellspacing="0">
<tr>
	<td valign="top">
	<form name="file" action="<%=request.getContextPath()%>/docente/tindividual/SubirTrabajo.action" enctype="multipart/form-data" method="post" >
		<input name="file" type="file" size="0" onChange ="enviarArchivo()" 
		style="border-width:0px; WIDTH:0px;"/>
<%--		<input type="button" onclick="openExplorer()" />--%>
	</form>
	</td><td valign="top">
	<img src="<%=request.getContextPath()%>/img/icon_download.gif" alt="Descargar" border="0" />
	</td>
</tr>
</table>

</body>
</html>