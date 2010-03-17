<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@  page import="edu.tecsup.lms.util.Util"%>
<%@ page import="edu.tecsup.lms.util.Constante"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html version="-//W3C//DTD HTML 4.01 Transitional//EN">
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/index.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/login.js"></script>
		<style type="text/css">
</style>
	</head>
	<body onLoad="init();">
		<div id="contenedor">
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border: 1px solid #cccccc">
					<tr>
						<td width="6" Xclass="fon_login_iz" height="23" align="center" style="background-color: #dddddd">
							&nbsp;
						</td>
						<td Xclass="fon_login_cen" style="background-color: #dddddd">
							<strong style="color: #333333">Educa</strong>
						</td>
						<td width="6" Xclass="fon_login_de" style="background-color: #dddddd">
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<div id="cuerpo">
				<table width="100%" height="450" border="0" cellpadding="0"
					cellspacing="7">

					<tr>
						<td width="205" valign="top">
							<form id="form1" name="login" method="post"
								action="<%=request.getContextPath() %>/Logeo.action"
								onSubmit="return validar();">
								<table width="200" border="0" align="right" cellpadding="0"
									cellspacing="0">
									<tr>
										<td width="6" class="fon_login_iz" height="23" align="center">
											&nbsp;
										</td>
										<td width="201" class="fon_login_cen" align="center">
											<span class="ingr_curso">Ingreso</span>
										</td>
										<td width="6" class="fon_login_de">
											&nbsp;
										</td>
									</tr>
									<tr>
										<td colspan="3" class="fon_caja">
											<table width="198" align="right">
												<tr>
													<td width="37%" align="right">
														<strong>Usuario :</strong>
													</td>
													<td width="63%">
														<input name="idUsuario" type="text" class="form_text"
															autocomplete="off"
															value="<c:out value="${idUsuario}" default="ebenites"/>" />
													</td>
												</tr>
												<tr>
													<td align="right">
														<strong>Clave :</strong>
													</td>
													<td>
														<input name="clave" type="password" class="form_text"
															value="educa" />
													</td>
												</tr>
												<tr>
													<td colspan="2" style="padding-left: 20px;">
														<div id="login_fail" align="center"
															style="font-weight: bold; color: #FF0000; font-size: 8pt;">
															<c:out value="${message}"></c:out>
														</div>
													</td>
												</tr>
												<tr>
													<td align="right">
														&nbsp;
													</td>
													<td align="center">
														<input id="ingresar" type="submit" class="form_button"
															value="Ingresar" />
													</td>
												</tr>
											</table>
											<form id="form1" name="login" method="post"
												action="/campus/Logeo.action" onSubmit="return validar();">
										</td>
									</tr>
									<tr>
										<td colspan="3" style="padding-top: 5px;">
											&nbsp;
										</td>
									</tr>
									
									

								</table>
						</td>
						<td rowspan="4" valign="top" class="detalles_index">

							<table width="100%" height="438" border="0" cellpadding="0"
								cellspacing="0">
								<tr>
									<td height="50" align="center">
										<p class="bienvenida" style="text-align: center;">
											Bienvenido a Educa
										</p>
									</td>
								</tr>
								<tr>
									<td height="80" valign="top" style="padding-bottom: 4px;">
										<div style="overflow: auto; height: 390px; text-align: center;">

											<c:forEach items="${noticias}" var="n">
												<table width="96%" cellspacing="0" cellpadding="4" bordercolor="#ffffff" border="0"  style="table-layout: fixed;">
													<tbody>
														<tr>
															<td class="franja_index titulo" height="20" align="left">
																	<b><c:out value="${n.titular}" /> </b>
															</td>
															<td class="franja_index titulo"  height="20" align="right" width="100">
																	<b><f:DateToString fecha="${n.fecha}" /></b>
															</td>
														</tr>
														<tr>
															<td valign="middle" align="left" bordercolor="#CACACA"
																class="detalles_index" colspan="2">
																<table width="100%" cellspacing="0" cellpadding="0"
																	border="0" align="center">
																	<tbody>
																		<tr>
																			<td>
																				<a href="javascript:void(0);"
																					onclick="abrirImagenDeNoticia('<%=request.getContextPath()%>/VerImagen.action?filename=<c:out value="${n.imagen}"/>');"><img
																						width="180" vspace="4" hspace="4" border="0"
																						align="left" alt=""
																						src="<%=request.getContextPath()%>/VerImagenPrevia.action?filename=<c:out value="${n.imagen}" />" />
																				</a>
																			</td>
																			<td>
																				<p class="textonormal" align="justify">
																					<c:out value="${n.sumilla}" escapeXml="false" />
																					...
																				</p>
																				<p class="textonormal" align="right">
																					<a href="javascript:void(0)"
																						onclick="window.open('InicioNoticiaDetalle.action?idNoticia=<c:out value="${n.idnoticia}"/>','Noticia','toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=1,resizable=0,width=595,height=340');">Ver
																						Nota Completa </a>
																				</p>
																			</td>
																		</tr>
																	</tbody>
																</table>
															</td>
														</tr>
														<tr>
															<td height="5">
																&nbsp;
																<br>
															</td>
														</tr>
													</tbody>
												</table>
											</c:forEach>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
			<div id="pie" >
				<s:include value="/comun/pie_copyright.jsp" />
			</div>
		</div>
	</body>
</html>