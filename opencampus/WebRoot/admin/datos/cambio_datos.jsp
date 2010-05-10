<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="edu.opencampus.lms.util.Constante"%>
<%@page import="edu.opencampus.lms.modelo.Usuario"%>
<%@page import="edu.opencampus.lms.util.Formato"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Usuario usuario = (Usuario) request.getSession().getAttribute(
	Constante.USUARIO_ACTUAL);
	Usuario user = (Usuario) request.getAttribute("user");
	String idUsuario = "", email="", nombre1 = "", nombre2 = "", paterno = "", materno = "", direccion = "", sexo = "", fec_nac = "", telefono = "", celular = "";
	if (user != null) {
		idUsuario = user.getIdUsuario();
		nombre1 = Formato.formatoTexto(user.getUsuarioDato()
		.getNombre1());
		nombre2 = Formato.formatoTexto(user.getUsuarioDato()
		.getNombre2());
		paterno = Formato.formatoTexto(user.getUsuarioDato()
		.getPaterno());
		materno = Formato.formatoTexto(user.getUsuarioDato()
		.getMaterno());
		direccion = Formato.formatoTexto(user.getUsuarioDato()
		.getDireccion());
		sexo = user.getUsuarioDato().getSexo();
		telefono = Formato.formatoTextoNull(user.getUsuarioDato()
		.getTelefono());
		celular = Formato.formatoTextoNull(user.getUsuarioDato()
		.getCelular());		
		fec_nac = Formato.getStringDeDate(user.getUsuarioDato()
		.getFechaNacimiento());
		email = Formato.formatoTextoNull(user.getUsuarioDato()
		.getEmail());
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />		
		<link href="<%=request.getContextPath()%>/estilos/mod_datos.css"
			rel="stylesheet" type="text/css">
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>		
		
	</head>
	<body>
		<div id="contenedor">
		
			<s:include value="/comun/bienvenida.jsp"></s:include>
			
			<div id="cuerpo">
				<div id="principal">					
					

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
