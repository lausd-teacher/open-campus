<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<s:include value="/comun/jslibs.jsp"/>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/configuracion.js"></script>
			
	<STYLE type="text/css">
	
	#portal .portal-column {
  float: left;
  width: 30%;
}
#portal #portal-column-block-list {
  position: absolute;
  width: 200px;
  top: 180px;
  left: 10px;
  z-index: 10;
}
#portal .block .block-toggle {
  background-image: url(block-slide.png);
  float: right;
  cursor: pointer;
}
#portal .block .block-toggle span {
  display: none;
}
#portal .block-list-handle, #portal .handle {
  cursor: move;
}
	</STYLE>
	</head>
	<body>
	
		<div id="container">
		
			<s:include value="/comun/bienvenida.jsp"/>
			
			<div id="body">
			
			
				<table border="0" cellpadding="3" cellspacing="0" class="open_table" width="100%">
						<caption><s:text name="portal.menu.configuracion"/></caption>
						<tbody>
							<tr>
								
								<c:forEach items="${portal}" var="servicio" varStatus="status">
									<td>
										<table width="100%" border="0" cellpadding="3" cellspacing="0">
											<tr>
												<td width="20">
													<input type="checkbox" <c:if test="${servicio.estado == 1}">checked="checked"</c:if>
														<c:if test="${servicio.permisoEliminar != 1}">disabled="disabled"</c:if>
														onclick="anadirServicio(this,'<c:out value="${servicio.id}"/>')" />
												</td>
												<td width="20">
													<img src="<%=request.getContextPath()%>/img/icons/<c:out value="${servicio.id}"/>.gif" alt='' />
												</td>
												<td>
													<strong><fmt:message key="${servicio.nombre}"/></strong>
												</td>
											</tr>
										</table>
									</td>
									
									<c:if test="${status.count==5}">
										<c:out value="</tr><tr>" escapeXml="false"></c:out>
									</c:if>
									
								</c:forEach>
									
							</tr>
						</tbody>
					</table>
				
					<br>




				<div id="portal">
			        <a href="#" id="portal-block-list-link" 
					title= "Click to add portlets" name="portal-block-list-link">Add
			        content</a><br class="clear" />
			
			        <div class="portal-column" id="portal-column-0">
			          <h2>Column 0</h2>
			        </div>
			
			        <div class="portal-column" id="portal-column-1">
			          <h2>Column 1</h2>
			        </div>
			
			        <div class="portal-column" id="portal-column-2">
			          <h2>Column 2</h2>
			        </div>
			
			        <div class="portal-column" id="portal-column-block-list"
			        style="display: none;">
			          <h2 class="block-list-handle">Block List</h2>
			
			         
			
			          <div class="block block-user" id="block-user-3">
			            <h3 class="handle"><a class=
			            "block-toggle"><span>toggle</span></a> Who's
			            online</h3>
			
			            <div class="content">
			              <div>
			                There are currently 1 user and 0 guests online.
			
			                <div class="item-list">
			                  <h3>Online users</h3>
			
			                  <ul>
			                    <li><a href="#" title=
			                    "View user profile.">ayman</a></li>
			                  </ul>
			                </div>
			              </div>
			            </div>
			          </div>
			
			          <div class="block block-node" id="block-node-0">
			            <h3 class="handle"><a class=
			            "block-toggle"><span>toggle</span></a> Syndicate</h3>
			
			            <div class="content">
			              <div>
			                <a href="#" class="feed-icon"><img src="feed.png"
			                alt="Syndicate content" title="Syndicate content"
			                width="16" height="16" /></a>
			              </div>
			            </div>
			          </div>
			        </div>
			      </div><!-- end content -->





				<table border="0" cellpadding="3" cellspacing="0" class="open_grid" width="100%">
						<tbody>
							<tr>
								<td>
					
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
					
					
								<c:forEach var="servicio" items="${portal}">
					
									<div id="<c:out value='${servicio.id}' />"
										style="vertical-align: top; width: 241px; padding-bottom: 5px; float: left; padding-top: 5px;">
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
																				src="<c:out value='${contextPath}' /><c:out value='${servicio.id}' />"
																				alt="<fmt:message key="${servicio.nombre}"/>" style="cursor: move;" />
																		<br></td>
																		<td align="left">
																			<div
																				style="padding-left: 5px; padding-right: 10px; font-weight: bold;">
																				<fmt:setLocale value='${sessionScope["WW_TRANS_I18N_LOCALE"]}' scope="session"/>
																				<fmt:bundle basename="mensajes">
																					<fmt:message key="${servicio.nombre}"/>
																				</fmt:bundle>
																			</div>
																		<br></td>
																	</tr>
																</table>
															<br></td>
															<td width="15">
																<c:choose>
																	<c:when test="${servicio.permisoMinimizar == 1}">
																		<img
																			src="<c:out value='${contextPath}'/>/img/comprimir_portal.jpg"
																			alt="Minimizar" style="cursor: pointer;"
																			onClick="esconderServicio('<c:out value='${servicio.id}' />');">
																	</c:when>
																	<c:otherwise>
																		<img src="<c:out value='${contextPath}'/>/img/inac_comprimir_portal.jpg">
																	</c:otherwise>
																</c:choose>
															<br></td>
															<td width="15">
																<c:choose>
																	<c:when test="${servicio.permisoEliminar == 1}">
																		<img src="<c:out value='${contextPath}'/>/img/cerrar_portal.jpg"
																			alt="Cerrar" style="cursor: pointer;"
																			onClick="eliminarServicio('<c:out value='${servicio.id}' />');">
																	</c:when>
																	<c:otherwise>
																		<img src="<c:out value='${contextPath}'/>/img/inac_cerrar_portal.jpg">
																	</c:otherwise>
																</c:choose>
															<br></td>
														</tr>
													</table>
												<br></td>
											</tr>
											<tr id="<c:out value='${servicio.id}' />_tr" <c:if test='${servicio.visible == 0}'>style="display: none; visibility: hidden;"</c:if>>
												<td id="<c:out value='${servicio.id}' />_td" style="text-align: left; background-color:#FFF; border: 1px solid #cccccc;">
													<img src="<c:out value='${contextPath}'/>/img/cargando.gif" />
												<br></td>
											</tr>
											<tr height="20" style="background-color: #cccccc;">
												<td align="right"><br>
													<table width="100%" cellpadding="0" cellspacing="0">
														<tr>
															<td width="70%" align="left">
																	<label id="<c:out value='${servicio.id}' />_descripcion"
																		style="font-weight: bold; font-size: 100%; padding-left: 5px;">
																		&nbsp;
																	</label>
															<br></td>
															<td align="right">
																	<label
																		style="cursor: pointer; font-weight: bold; font-size: 100%; padding-right: 5px;"
																		onclick="abrir_<c:out value='${servicio.id}' />();"
																		class="portal_menu_selecionando">
																		<s:text name="portal.servicios.link"/>
																	</label>
															<br></td>
														</tr>
													</table>
												<br><br></td>
											</tr>
										</table>
									</div>		
					
								</c:forEach>
								
								</td>
							</tr>
						</tbody>
					</table>
					
					<script type="text/javascript">
		
						//var portal = new CampusVirtual.Portal("#principal div", {onOverWidget:onOverWidget, onOutWidget:onOutWidget, onChange:onChange, removeEffect:Effect.SwitchOff});
						
						/*function loadServices(){
						<c:forEach var="servicio" items="${portal}">
							portal.add(new CampusVirtual.Widget('<c:out value='${servicio.id}' />'), '<c:out value='${servicio.columna}' />');
							cargar_<c:out value='${servicio.id}' />();
						</c:forEach>
							portal._updateColumnsHeight();	
						
						}	*/
					</script>
					
					
			</div>
			
			<s:include value="/comun/pie.jsp"/>
			
		</div>
		
	</body>
</html>
