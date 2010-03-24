<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<% //System.out.println(((edu.tecsup.lms.modelo.Usuario)session.getAttribute(edu.tecsup.lms.util.Constante.USUARIO_ACTUAL)).toStringFull()); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" /></title>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/protostaculus.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/portal.js"></script>
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/tooltip/tooltip.js"></script>
		
		<link href="<c:out value='${contextPath}'/>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
	</head>
	<s:include value="capas/reloj.jsp" />
	<body onLoad="mostrarReloj();loadServices();" onResize="resize();">
		<div id="contenedor">
			
			<s:include value="bienvenida.jsp" />
			
			<div id="cuerpo" style="z-index: 0;">
				<div id="principal" style="z-index: -1;">
					<div id="principal_col_0"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
					<div id="principal_col_1"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
					<div id="principal_col_2"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
					<div id="principal_col_3"
						style="width: 242px; float: left; min-height: 50px; padding-bottom: 10px;">
					</div>
				</div>
			</div>
			<div id="pie">
				<s:include value="pie_copyright.jsp" />
			</div>
		</div>
		
		<c:forEach var="servicio" items="${portal}">
		
		<div id="<c:out value='${servicio.id}' />"
			style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px; display: none;">
			<table width="230" border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
				<tr>
					<td height="18" valign="top"  style="background-image: url(img/fon_menu.jpg); background-position-x:center; border-left: 1px solid #cccccc; border-right: 1px solid #cccccc;">
						<table cellpadding="0" cellspacing="0" width="100%" border="0" >
							<tr>
								<td valign="bottom" style="cursor: move;" id="tabla_<c:out value='${servicio.id}' />">									
									<table border="0" align="left" cellpadding="3" cellspacing="0" width="100%" >
										<tr>
											<td align="left" width="20">
												<img height="15"
													src="<c:out value='${contextPath}' /><c:out value='${servicio.imagen}' />"
													alt="<fmt:message key="${servicio.comentario}"/>" style="cursor: move;" />
											</td>
											<td align="left">
												<div
													style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
													<fmt:setLocale value='${sessionScope["WW_TRANS_I18N_LOCALE"]}' scope="session"/>
													<fmt:bundle basename="mensajes">
														<fmt:message key="${servicio.nombre}"/>
													</fmt:bundle>
												</div>
											</td>
										</tr>
									</table>
								</td>
								<td width="15">
									<c:choose>
										<c:when test="${servicio.usuarioMinimizar == 1}">
											<img
												src="<c:out value='${contextPath}'/>/img/comprimir_portal.jpg"
												alt="Minimizar" style="cursor: pointer;"
												onClick="esconderServicio('<c:out value='${servicio.id}' />');">
										</c:when>
										<c:otherwise>
											<img src="<c:out value='${contextPath}'/>/img/inac_comprimir_portal.jpg">
										</c:otherwise>
									</c:choose>
								</td>
								<td width="15">
									<c:choose>
										<c:when test="${servicio.usuarioEliminar == 1}">
											<img src="<c:out value='${contextPath}'/>/img/cerrar_portal.jpg"
												alt="Cerrar" style="cursor: pointer;"
												onClick="eliminarServicio('<c:out value='${servicio.id}' />');">
										</c:when>
										<c:otherwise>
											<img src="<c:out value='${contextPath}'/>/img/inac_cerrar_portal.jpg">
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr id="<c:out value='${servicio.id}' />_tr" <c:if test='${servicio.visible == 0}'>style="display: none; visibility: hidden;"</c:if>>
					<td id="<c:out value='${servicio.id}' />_td" style="text-align: left; background-color:#FFF; border: 1px solid #cccccc;">
						<img src="<c:out value='${contextPath}'/>/img/cargando.gif" />
					</td>
				</tr>
				<tr height="20" style="background-color: #cccccc;">
					<td align="right">
						<table width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td width="70%" align="left">
									<c:if test="${servicio.verDescripcion == 1}">
										<label id="<c:out value='${servicio.id}' />_descripcion"
											style="font-weight: bold; font-size: 100%; padding-left: 5px;">
											&nbsp;
										</label>
									</c:if>
								</td>
								<td align="right">
									<c:if test="${servicio.verIngreso == 1}">
										<label
											style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
											onclick="abrir_<c:out value='${servicio.id}' />();"
											class="portal_menu_selecionando">
											<s:text name="portal.servicios.link"/>
										</label>
									</c:if>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>		
		
		</c:forEach>
		
	</body>
	<head>
		<script type="text/javascript">
		
			var portal = new CampusVirtual.Portal("#principal div", {onOverWidget:onOverWidget, onOutWidget:onOutWidget, onChange:onChange, removeEffect:Effect.SwitchOff});
			
			function loadServices(){
			<c:forEach var="servicio" items="${portal}">
				portal.add(new CampusVirtual.Widget('<c:out value='${servicio.id}' />'), '<c:out value='${servicio.columna}' />');
				cargar_<c:out value='${servicio.id}' />();
			</c:forEach>
				portal._updateColumnsHeight();	
			
			}	
		</script>
	</head>
</html>

