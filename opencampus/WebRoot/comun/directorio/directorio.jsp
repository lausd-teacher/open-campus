<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<style>
#ampliacion {
	border-right: #666666 1px solid;
	padding-right: 2px;
	border-top: #666666 1px solid;
	background-color:#ffffff;
	padding-left: 2px;
	visibility: hidden;
	padding-bottom: 2px;
	border-left: #666666 1px solid;
	padding-top: 2px;
	border-bottom: #666666 1px solid;
	background-repeat: no-repeat;
	position: absolute;
	width: 130px;
	height: 54px;
	top: 0px;
	left: 0px;
}
#cerrarampliacion {
	background-color:#333333;
	font-family:arial,verdana;
	font-size:8pt;
	line-height:20px;
	text-align:right;
	float:right;
	height: 20px;
	width: 124px;
	padding-right:5px;
}
</style>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/directorio.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>	
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script language="javascript" type="text/javascript" 
			src="<%=request.getContextPath()%>/js/fastinit.js"></script>
		<script language="javascript" type="text/javascript" 
			src="<%=request.getContextPath()%>/js/tablesort.js"></script>	
	</head>
	<%@include file="../../comun/capas/reloj.jsp"%>
	<body onLoad="mostrarReloj();">
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo">			
				<div id="principal">
				<s:include value="/error_message.jsp"/> 
				<c:if test="${usuarios != null}">
					<table width="975" cellpadding="3" cellspacing="0" class="bor_tabla" border="0">
						<tr>
							<td>&nbsp;</td>
							<td width="20">
								<c:choose>
									<c:when test="${pagina >= 2}">
										<a class="opcion_selecionar" style="text-decoration: underline;font-weight: bold;"
											href="<%=request.getContextPath()%>/Directorio.action?pagina=0"> &lt;&lt; </a>
									</c:when>
									<c:otherwise>
										<span class="opcion_selecionar" style="color: silver;"> &lt;&lt; </span>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="20">
								<c:choose>
									<c:when test="${pagina >= 1}">
										<a class="opcion_selecionar" style="text-decoration: underline;font-weight: bold;"
											href="<%=request.getContextPath()%>/Directorio.action?pagina=<c:out value="${pagina-1}"/>">
											&lt; </a>
									</c:when>
									<c:otherwise>
										<span class="opcion_selecionar" style="color: silver;"> &lt; </span>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="160" align="center"> 
								<f:Constante campo="BUSQUEDA_CANTIDAD_DIRECTORIO" var="cantidad"/>
								<c:out value="${(pagina * cantidad) + 1}"/>
								-
								<c:out value="${(pagina * cantidad) + fn:length(usuarios)}"/>
								de
								<c:out value="${total}" default="0"/> <s:text name="portal.directorio.usuario"/>(s)
							</td>
							<td width="20">
								<c:choose>
									<c:when test="${paginas - pagina >= 1}">
										<a class="opcion_selecionar" style="text-decoration: underline;font-weight: bold;"
											href="<%=request.getContextPath()%>/Directorio.action?pagina=<c:out value="${pagina+1}"/>">
											&gt; </span>
									</c:when>
									<c:otherwise>
										<span class="opcion_selecionar" style="color: silver;"> &gt; </span>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="20">
								<c:choose>
									<c:when test="${paginas - pagina >= 2}">
										<a class="opcion_selecionar" style="text-decoration: underline;font-weight: bold;"
											href="<%=request.getContextPath()%>/Directorio.action?pagina=<c:out value="${paginas}"/>">
											&gt;&gt;
										</a>
									</c:when>
									<c:otherwise>
										<span class="opcion_selecionar" style="color: silver;">&gt;&gt;</span>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table> 
					</c:if>
					<form action="<%=request.getContextPath()%>/Directorio.action" method="post" onsubmit="return validar(this)">
					<table width="975" cellpadding="3" cellspacing="0"
						class="bor_tabla sortable" border="0" style="table-layout: fixed;"> 
						<caption class="fon_cab tit_cab"><s:text name="portal.menu.directorio"/></caption>
						<thead> 
						
						<tr class="fon_color01 sortablemenu">
							<td width="160" align="center" class="unidades">
								<strong><s:text name="portal.directorio.grupo"></s:text></strong>
							</td>
							<td width="120" align="center" class="unidades">
								<strong><s:text name="portal.directorio.usuario"></s:text> </strong>
							</td>
							<td align="center" class="unidades">
								<strong><s:text name="portal.directorio.paterno"></s:text> </strong>
							</td>
							<td align="center" class="unidades">
								<strong><s:text name="portal.directorio.materno"></s:text> </strong>
							</td>
							<td align="center" class="unidades">
								<strong><s:text name="portal.directorio.nombre1"></s:text> </strong>
							</td>
							<td align="center" class="unidades">
								<strong><s:text name="portal.directorio.nombre2"></s:text></strong>
							</td>
							<td width="70" align="center" class="nosort">
								&nbsp;
							</td>
						</tr>
						
						<tr>
							<td align="center" class="bor_der_unid">
								<select size="1" name="rol" class="form_text">
									<option value="0">
										<s:text name="portal.directorio.niguno_selecionado"/>
									</option>
									<c:forEach items="${roles}" var="r">
										<option value="<c:out value="${r.idrol}"/>" 
										<c:if test="${r.idrol == rol}">selected</c:if>><c:out value="${r.nombre}"/></option>
									</c:forEach>
								</select>
							</td>
							
							<td class="bor_der_unid">
								<input name="username" type="text" class="form_text" size="12"
									value="<c:out value="${username}"/>"  />
							</td>
							
							<td align="center" class="bor_der_unid">
								<input name="paterno" type="text" class="form_text"
									value="<c:out value="${paterno}"/>" size="12" />
							</td>
							<td align="center" class="bor_der_unid">
								<input name="materno" type="text" class="form_text"
									value="<c:out value="${materno}"/>" size="12" />
							</td>
							<td align="center" class="bor_der_unid">
								<input name="nombre1" type="text" class="form_text" size="12"
									value="<c:out value="${nombre1}"/>" />
							</td>
							<td align="center" class="bor_der_unid">
								<input name="nombre2" type="text" class="form_text" size="12"
									value="<c:out value="${nombre2}"/>" />
							</td>
							<td align="center">
								<input type="hidden" name="pagina" value="0" id="form_posicionPagina">
								<input value="<s:text name="portal.directorio.buscar"/>" type="submit" class="form_button" >
							</td>
						</tr>
						</thead>
						
						<c:if test="${usuarios != null}">
						
						<tbody>
							<c:choose>
								<c:when test="${fn:length(usuarios)>0}">
									<c:forEach items="${usuarios}" var="u" varStatus="fila">
										<tr onmouseover="verImagen(this,'<%=request.getContextPath()%>/VerFoto.action?id=<c:out value="${u.id}"/>');"
										onmouseout=" ocultarImagen();" id="<c:out value="${u.idToString}"/>" style="height: 16px;">
										
											<td align="center" class="bor_der_unid">
												<c:out value="${u.rolPredeterminado.nombre}" default="&nbsp;" escapeXml="false"></c:out>
											</td>
											<td align="center" class="bor_der_unid"  >
												<b><c:out value="${u.usuario}"/></b>
											</td>
											<td align="center" class="bor_der_unid" >
												<c:out value="${u.persona.apepaterno}" default="&nbsp;" escapeXml="false"/>
											</td>
											<td align="center" class="bor_der_unid">
												<c:out value="${u.persona.apematerno}" default="&nbsp;" escapeXml="false"/>
											</td>
											<td align="center" class="bor_der_unid">
												<c:out value="${u.persona.nomuno}" default="&nbsp;" escapeXml="false"/>
											</td>
											<td align="center" class="bor_der_unid">
												<c:out value="${u.persona.nomdos}" default="&nbsp;" escapeXml="false"/>
											</td>
											<td align="center" >
												<img src="<%=request.getContextPath()%>/img/icon_mail_send.gif" border="0"
														style="cursor:pointer" onclick="nuevoMensaje('<%=request.getContextPath()%>/comun/buzon/NuevoMensaje.action?destino=<c:out value="${u.usuario}"/>')">
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="7" style="padding: 20px" align="center" valign="middle"><i>No hubo resultados.</i></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</c:if>
					</table>
					</form>
					<br/>
				</div>				
			</div>
			<div id="pie">
				<%@include file="/comun/pie.jsp"%>
			</div>
			<div id="ampliacion">
				<div id="c1">
					<img src="<%=request.getContextPath()%>/img/cargando.gif"
						border="0">
				</div>
				<div id="cerrarampliacion" style="color:#ffffff;font-weight: bold;">
				</div>
			</div>
		</div>
	</body>
</html>
