<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
			src="<%=request.getContextPath()%>/admin/usuario/admin_usuario_buscar.js"></script>
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
				
					<form action="<%=request.getContextPath()%>/admin/usuario/Buscar.action" method="post">
					<table width="100%" cellpadding="3" cellspacing="0"
						class="open_table with_hover sortable" border="0" style="table-layout: fixed;"> 
						<caption>
							<span style="float: left;"><s:text name="portal.menu.directorio"/></span>
							
							<c:if test="${usuarios != null}">
								<span style="float: right" class="pagination">
									<span>
									<c:choose>
										<c:when test="${pagina >= 2}">
											<a href="<%=request.getContextPath()%>/admin/usuario/Buscar.action?pagina=0"> &lt;&lt; </a>
										</c:when>
										<c:otherwise>
											<span style="color: silver;"> &lt;&lt; </span>
										</c:otherwise>
									</c:choose>
									</span>
									
									<span>
									<c:choose>
										<c:when test="${pagina >= 1}">
											<a href="<%=request.getContextPath()%>/admin/usuario/Buscar.action?pagina=<c:out value="${pagina-1}"/>">
												&lt; </a>
										</c:when>
										<c:otherwise>
											<span style="color: silver;"> &lt; </span>
										</c:otherwise>
									</c:choose>
									</span>
									
									<span style="width:140px;">
									<f:Constante campo="BUSQUEDA_CANTIDAD_DIRECTORIO" var="cantidad"/>
									<c:out value="${(pagina * cantidad) + 1}"/>
									-
									<c:out value="${(pagina * cantidad) + fn:length(usuarios)}"/>
									de
									<c:out value="${total}" default="0"/> <s:text name="portal.directorio.usuario"/>(s)
									</span>
									
									<span>
									<c:choose>
										<c:when test="${paginas - pagina >= 1}">
											<a href="<%=request.getContextPath()%>/admin/usuario/Buscar.action?pagina=<c:out value="${pagina+1}"/>">
												&gt; </a>
										</c:when>
										<c:otherwise>
											<span style="color: silver;"> &gt; </span>
										</c:otherwise>
									</c:choose>
									</span>
									
									<span>
									<c:choose>
										<c:when test="${paginas - pagina >= 2}">
											<a href="<%=request.getContextPath()%>/admin/usuario/Buscar.action?pagina=<c:out value="${paginas}"/>">
												&gt;&gt;</a>
										</c:when>
										<c:otherwise>
											<span style="color: silver;">&gt;&gt;</span>
										</c:otherwise>
									</c:choose>
									</span>
									
								</span>
							</c:if>
						</caption>
						<thead> 
						
						<tr class="sortablemenu">
							<td width="160" align="center">
								<strong><s:text name="portal.directorio.grupo"></s:text></strong>
							</td>
							<td width="120" align="center">
								<strong><s:text name="portal.directorio.usuario"></s:text> </strong>
							</td>
							<td align="center">
								<strong><s:text name="portal.directorio.paterno"></s:text> </strong>
							</td>
							<td align="center">
								<strong><s:text name="portal.directorio.materno"></s:text> </strong>
							</td>
							<td align="center">
								<strong><s:text name="portal.directorio.nombre1"></s:text> </strong>
							</td>
							<td align="center">
								<strong><s:text name="portal.directorio.nombre2"></s:text></strong>
							</td>
							<td colspan="4" width="80" align="center" class="nosort">
								&nbsp;
							</td>
						</tr>
						
						<tr>
							<td align="center">
								<select size="1" name="rol">
									<option value="0">
										<s:text name="portal.directorio.niguno_selecionado"/>
									</option>
									<c:forEach items="${roles}" var="r">
										<option value="<c:out value="${r.idrol}"/>" 
										<c:if test="${r.idrol == filtro.rol}">selected</c:if>><c:out value="${r.nombre}"/></option>
									</c:forEach>
								</select>
							</td>
							
							<td>
								<input name="username" onkeyup="enviarABuscar(event);" type="text" size="12"
									value="<c:out value="${filtro.usuario}"/>"  />
							</td>
							
							<td align="center">
								<input name="paterno" type="text" onkeyup="enviarABuscar(event);"
									value="<c:out value="${filtro.paterno}"/>" size="12" />
							</td>
							<td align="center">
								<input name="materno" type="text" onkeyup="enviarABuscar(event);"
									value="<c:out value="${filtro.materno}"/>" size="12" />
							</td>
							<td align="center">
								<input name="nombre1" type="text" onkeyup="enviarABuscar(event);" size="12"
									value="<c:out value="${filtro.nombre1}"/>" />
							</td>
							<td align="center">
								<input name="nombre2" type="text" onkeyup="enviarABuscar(event);" size="12"
									value="<c:out value="${filtro.nombre2}"/>" />
							</td>
							<td align="center" colspan="4">
								<input type="hidden" name="pagina" value="0" id="form_posicionPagina">
								<input value="<s:text name="portal.directorio.buscar"/>" type="submit" style="width: 80px;">
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
										
											<td align="center">
												<c:out value="${u.rolPredeterminado.nombre}" default="&nbsp;" escapeXml="false"></c:out>
											</td>
											<td align="center">
												<b><c:out value="${u.usuario}"/></b>
											</td>
											<td align="center">
												<c:out value="${u.persona.apepaterno}" default="&nbsp;" escapeXml="false"/>
											</td>
											<td align="center">
												<c:out value="${u.persona.apematerno}" default="&nbsp;" escapeXml="false"/>
											</td>
											<td align="center">
												<c:out value="${u.persona.nomuno}" default="&nbsp;" escapeXml="false"/>
											</td>
											<td align="center">
												<c:out value="${u.persona.nomdos}" default="&nbsp;" escapeXml="false"/>&nbsp;
											</td>
											
											<td align="center" >
												<c:choose>
											  		<c:when test="${u.estado==1}">
											  			<img src="<%=request.getContextPath() %>/img/activo.gif" border="0" alt="Desactivar" style="cursor: pointer;"
											  			onclick="cambiarEstado(this,'<c:out value="${u.id}"/>')" />
											  		</c:when>
											  		<c:otherwise>
											  			<img src="<%=request.getContextPath() %>/img/desactivo.gif" border="0" alt="Activar" style="cursor: pointer;"
											  			onclick="cambiarEstado(this,'<c:out value="${u.id}"/>')"/>
											  		</c:otherwise>
											  	</c:choose>
											</td>
											<td align="center" >
												<img src="<%=request.getContextPath()%>/img/icon_view.gif" border="0"
														style="cursor:pointer" onclick="verDatosPersonales('<c:out value="${u.id}"/>');">
											</td>
											<td align="center" >
												<img src="<%=request.getContextPath()%>/img/icon_edit.gif" border="0"
														style="cursor:pointer" onclick="go('<%=request.getContextPath()%>/admin/usuario/Editar.action?id=<c:out value="${u.id}"/>')">
											</td>
											<td align="center" >
												<img src="<%=request.getContextPath()%>/img/icon_trash.gif" border="0"
														style="cursor:pointer" onclick="if(confirm_delete())go('<%=request.getContextPath()%>/admin/usuario/Eliminar.action?idusuario=<c:out value="${u.id}"/>')">
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="10" style="padding: 20px" align="center" valign="middle"><i>No hubo resultados.</i></td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</c:if>
						<tfoot>
							<tr>
								<td colspan="6">&nbsp;</td>
								<td align="right" colspan="4">
									<input type="button" value="Nuevo" style="width: 80px;"
										onclick="document.location='<%=request.getContextPath()%>/admin/usuario/Nuevo.action'">									
								</td>
							</tr>
						</tfoot>
					</table>
					</form>
					<br/>
					
					<table width="100%" cellpadding="3" cellspacing="0"
						class="open_table" border="0">
						<caption>Recientes</caption>
						<thead>
						<tr>
							<th width="60">ID</th>
							<th width="120">Usuario</th>
							<th width="80">Clave</th>
							<th>Nombre Completo</th>
							<th width="160">Rol</th>
							<th width="120">Fecha de Creaci&oacute;n</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${ultimos}" var="user" varStatus="fila">
							<tr <c:if test="${fila.count%2==0}">class="line"</c:if> onclick="verDatosPersonales('<c:out value="${user.id}"/>');">
								<td align="center"><c:out value="${user.idToString}"/> </td>
								<td align="center"><b><c:out value="${user.usuario}"/></b> </td>
								<td align="center"><c:out value="${user.clave}"/> </td>
								<td><c:out value="${user.persona.nombreCompleto}"/> </td>
								<td align="center"><c:out value="${user.rolPredeterminado.nombre}"/> </td>
								<td align="center"><c:out value="${user.persona.fechaCreacionToString}"/></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
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
