<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><s:text name="titulo.campus.virtual" /></title>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<meta HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" />
		<meta HTTP-EQUIV="EXPIRES" CONTENT="-1" />
		<meta HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/estilos/glosario.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/util.js"></script>
	
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/glosario.js"></script>
	</head>

	<body onload="context='<%=request.getContextPath()%>'" >		
		<div id="pop_up" style="width: 340px">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong>Glosario</strong>
						</td>
						<td width="4%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"><img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>

			<div id="pop_cuerpo">
				<table align="center" cellpadding="3" cellspacing="0"
					class="bor_tabla" border="0" width="100%">
					<tr>
						<td>
							<input style="color: gray" id="glosario_buscar"
								onkeypress="dwr.util.onReturn(event, glosario.buscarAjax)"
								type="text" title="Buscar" size="44" value="<c:out value="${palabra}"/>"
								onfocus="this.value=''">
							<input class="form_button" type="button" value="Buscar" onclick="glosario.buscarAjax()">
						</td>
					</tr>
					
					<tr>
						<td>
							<div id="resultado_glosario">
							<c:forEach items="${palabras}" var="palabra">
								<b><c:out value="${palabra.palabra}" />
								</b>:&nbsp;
								<c:out value="${palabra.definicion}" />
								<br/>
							</c:forEach>
							</div>
						</td>
					</tr>
					
				</table>
				<br />

			</div>


			<div id="pie" style="width: 340px; bottom: 0px;">
				
			</div>
		</div>

	</body>
</html>