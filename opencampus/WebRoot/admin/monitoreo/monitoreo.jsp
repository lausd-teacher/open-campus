<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.TreeMap"%>
<%@ page import="edu.tecsup.lms.modelo.Usuario"%>
<%@page import="edu.tecsup.lms.util.UsuariosConectados"%>
<%@page import="java.util.Collection"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
	Usuario usuario = (Usuario) request.getSession().getAttribute(Constante.USUARIO_ACTUAL);
	Map<String, Usuario> usuarios = (Map<String, Usuario>)request.getAttribute("conectados");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />	
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>		
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/tooltip/tooltip.js"></script>
	</head>
	<body>
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo">
				<div id="principal" style="z-index: 5">
					<div id="inicio">
						<table  border="0" align="left" cellpadding="0"
							cellspacing="0" width="99%">
							<tr>
								<td>
									<div id="otros">
										<table width="100%" border="0" cellspacing="0" cellpadding="0"
											bgcolor="#E5EFF8" class="tablaFicha">
											<tr class="fon_cab">
												<th height="20" colspan="6" class="tit_cab">
													Monitoreo de Usuarios
												</th>
											</tr>
											<tr bgcolor="#ffffff">
												<td width="25" height="23" class="moduloAbajo">
													&nbsp;

												</td>
												<td class="moduloAbajo">
													Nombre
												</td>
												<td width="152" class="moduloAbajo">
													Rol Predeterminado
												</td>
												<td width="152" class="moduloAbajo">
													Fecha de Ingreso
												</td>
												<td width="152" class="moduloAbajo">
													Direcci&oacute;n IP
												</td>
												<td width="152" class="moduloAbajo">
													Navegador
												</td>
											</tr>
											<%
																	Integer idUsuario=0;
																	int numer =0;
																	boolean igualesId=false;
																	Map<String, Usuario> sm = new TreeMap<String, Usuario>(usuarios);
																	
																	for(Usuario u1:sm.values()){
																	numer++;
																	igualesId=false; 
																	if(idUsuario == u1.getId()){ 
																		igualesId=true;
																	}
											%>
											<tr <%if(numer%2==0) {%> bgcolor="#ffffff" <%}%>>
												<td height="23" align="center" class="texto1">
													<% 
 if(!igualesId){out.print(numer+" .");}
 %>
												</td>
												<td id="td_nombre_<%=numer%>" class="texto1" align="left">
													&nbsp;&nbsp;
													<% 
 if(!igualesId){out.print(u1.getNombreCompleto());}
 %>
												</td>
												<td id="td_rol_<%=numer%>" class="texto1" align="center">
													<%  
 if(!igualesId){out.print(u1.getRolPredeterminado().getNombre());}
 %>&nbsp;
												</td>
												<td id="td_fecha_<%=numer%>" class="texto1" align="center">
													<%
 if(!igualesId){out.print(u1.getIngreso().getFechaIngresoAsString());}
 %>&nbsp;
												</td>
												<td align="center" class="texto1">
													<label
														<%if(u1.getIp().startsWith("192.168.")){out.print("class=\"text_verde\"");} %>><%=u1.getIp()%></label>
												</td>
												<td
													onMouseOver="verToolTip('<%=u1.getNavegador()%>', this);"
													onmouseout="ocultarToolTip()" class="texto1" align="center"
													style="cursor: pointer;">
													<%=u1.getNavegadorAsString()%>
												</td>
											</tr>
											<%
																	idUsuario = u1.getId();
																	}
											%>
										</table>
									</div>
								</td>
							</tr>
							
							<tr>
								<td>
									<br/>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							bgcolor="#E5EFF8" class="tablaFicha">
							<tr class="fon_cab">
								<th height="20" colspan="6" class="tit_cab">
									Monitoreo de Usuarios en Test
								</th>
							</tr>
							<tr bgcolor="#ffffff">
								<td width="25" height="23" class="moduloAbajo">
									&nbsp;

								</td>
								<td class="moduloAbajo">
									Nombre
								</td>
								<td width="152" class="moduloAbajo">
									Rol Predeterminado
								</td>
								<td width="152" class="moduloAbajo">
									Fecha de Ingreso
								</td>
								<td width="152" class="moduloAbajo">
									Direcci&oacute;n IP
								</td>
								<td width="152" class="moduloAbajo">
									Navegador
								</td>
							</tr>
							
							<%
								int count = 1;
								Collection<Integer> idtesters= UsuariosConectados.noActivos;
								for(Integer idusuario : idtesters){
									Usuario tester = null;
									for(Usuario userTMP : usuarios.values()){
										if(userTMP.getId() == idusuario){
											tester = userTMP;
											break;
										}
									}
									if(tester != null){
							 %>
							
									<tr <%if(count%2==0) {%> bgcolor="#ffffff" <%}%>>
										<td height="23" align="center" class="texto1">
											<%=count++ %>
										</td>
										<td align="left" class="texto1">
											&nbsp;&nbsp;<%=tester.getNombreCompleto() %>&nbsp;(<%=idusuario %>)
										</td>
										<td align="center" class="texto1">
											<%=tester.getRolPredeterminado().getNombre()%>&nbsp;
										</td>
										<td align="center" class="texto1">
											<%=tester.getIngreso().getFechaIngreso() %>&nbsp;
										</td>
										<td align="center" class="texto1">
											<label <%if(tester.getIp().startsWith("192.168.")){out.print("class=\"text_verde\"");} %>>
												<%=tester.getIp()%>
											</label>
										</td>
										<td
											onMouseOver="verToolTip('<%=tester.getNavegador()%>', this);"
											onmouseout="ocultarToolTip()" class="texto1" align="center"
											style="cursor: pointer;">
											<%=tester.getNavegadorAsString()%>
										</td>
									</tr>
							
							<%
									}
								}
							 %>
							
						</table>
								</td>
							</tr>
							
						</table>
						
					</div>
					<div id="foros">
					</div>
				</div>
			</div>
			<div id="tooltip"></div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>
		</div>
	</body>
</html>
