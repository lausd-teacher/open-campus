<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.tecsup.lms.modelo.AulaVirtual"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script LANGUAGE="JavaScript"><!--
function do_err(){return true}onerror=do_err;function no_cp(){clipboardData.clearData();setTimeout("no_cp()",100);}
//no_cp();
//--></SCRIPT>
<head>
<NOSCRIPT>
Su navegador no soporta Javascript, puede habilitarlo y refrescar la p&aacute;gina.
<META HTTP-EQUIV="refresh" content="5;URL=<%=request.getContextPath()%>/error_recurso.jsp">
</NOSCRIPT>
<link href="noprint.txt" rel="alternate" media="print">
<STYLE media="print">
	BODY {display:none}
</STYLE>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:text name="titulo.campus.virtual" /></title>
<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/comun/test/test.js"></script>

</head>
<body onload="deshabilitarBotonDerecho(); deshabilitarCopiarPegar();" onunload="if(this.exit !== true)cerrarTest();">
	<div id="pop_up" style="width: 640px; height: 100%">
		<div id="prin_01" style="width: 100%;">
			<table width="100%" border="0" cellspacing="0" cellpadding="3">
				<tr>
					<td width="93%">
						<strong>Curso : <c:out value="${usuario_actual.aulaActual.curso.nombre}"/> </strong>
					</td>
					<td width="5%">
						
					</td>
					<td width="3%">
						
					</td>
					<td width="2%">
						
					</td>
					<td width="4%">
						<a href="#" class="salir" onClick="window.close()">Cerrar</a>
					</td>
					<td width="3%">
						<a href="#" class="salir" onClick="window.close()"> <img
								src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
								height="13" border="0" /> </a>
					</td>
				</tr>
			</table>
		</div>
		<div id="pop_cuerpo" style="width: 96%; height: 100%">
			<table width="100%" align="center" cellpadding="0" cellspacing="0"
					class="tabla01" border="0" style="table-layout: fixed;">
				<tr class="fon_tit_curso">
					<td height="20" colspan="2" class="tit_cab">
						Evaluación : <c:out value="${usuario_actual.aulaActual.testActual.nombreUnidad}"/>
					</td>
				</tr>
				<tr>
					<td height="20" colspan="2" align="center">
						<p>
							&nbsp;
						</p>
						<p>
							&nbsp;
						</p>
						<p>
							&nbsp;
						</p>
						<table width="73%" border="0">
							<tr>
								<td class="tabla01" colspan="2">
									<table width="100%" cellpadding="5" cellspacing="0">
										<tr>
											<td width="12%" align="center" valign="top">
												<img src="<%=request.getContextPath()%>/img/1.gif" width="16" height="16" />
											</td>
											<td width="88%">
												<p>
													Ud. va ingresar a la evaluaci&oacute;n. Una vez que ha ingresado, deberá
													finalizarlo. Si Ud. abandona la evaluaci&oacute;n en plena ejecución, el
													sistema almacenar&aacute; como nota la obtenida hasta ese momento.
												</p>
												<p>
													La evaluaci&oacute;n deber&aacute; ser finalizadas en menos de <b>15 minutos</b>. Pasado ese tiempo, el sistema no registrar&aacute; la nota de la evaluaci&oacute;n. 
												</p>
												<p>
													Mientras rinda la evaluaci&oacute;n, no abra otra ventana del navegador Web porque la evaluaci&oacute;n <b>tampoco</b> se registrar&aacute;.
												</p>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<td width="50%" height="25" align="center">
									<button type="button" class="form_button" style="width:80px" onclick="window.close();">
										Cancelar
									</button>
								</td>
								<td height="25" width="50%" align="center">
									<form method="post" onsubmit="return confirmarIngreso('<%=request.getAttribute("idUnidad") %>');">
										<input type="submit" value="Aceptar" class="form_button" style="width:80px" />
									</form>
								</td>
							</tr>
						</table>
						<p>
							&nbsp;
						</p>
						<p>
							&nbsp;
						</p>
					</td>
				</tr>
				<tr>
					<td width="30%" valign="top">
						&nbsp;
					</td>
					<td width="70%" valign="top">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
		
			</table>
		</div>
		<div id="pie">
			<%@include file="../pie.jsp"%>
		</div>
	</div>
</body>
</html>