<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@  page import="edu.opencampus.lms.modelo.AulaVirtual"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.modelo.ficha.Unidad"%>
<%@  page import="edu.opencampus.lms.modelo.Usuario"%>
<%
	AulaVirtual aula = (AulaVirtual) request.getSession().getAttribute(
			Constante.AULA_ACTUAL);
	Usuario usuario = (Usuario) request.getSession().getAttribute(Constante.USUARIO_ACTUAL);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />
		<title><s:text name="Campus Virtual de opencampus" />
		</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />

		<script type="text/javascript">
			var otro_array = new Array(10);
			function crearArray(position,tamanio){
				otro_array[position]=new Array(tamanio);
			}
			
			function asignarArray(pregunta,position,valor){
				var is_pregunta=otro_array[pregunta];
				is_pregunta[position]=valor;
			}
			
			function obtenerArray(pregunta,position){
				var is_pregunta=otro_array[pregunta];
				return is_pregunta[position];
			}
		</script>
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/engine.js'> </script>		
		<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/interface/UsuariosConectados.js'> </script>
	</head>
	<body onunload="try{UsuariosConectados.activarChat('<%=usuario.getIdUsuario()%>');}catch(e){}">
		<div id="pop_up" style="width: 100%; height: 100%">
			<div id="prin_01" style="width: 100%;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong>Curso : <%=aula.getNombreCurso()%> </strong>
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
				<table width="100%" align="center" cellpadding="3" cellspacing="0"
					class="tabla01" border="0">
					<tr class="fon_tit_curso">
						<td height="23" colspan="2" class="tit_cab" valign="top">
							Evaluaci&oacute;n	 :
							<%=request.getAttribute("UNIDAD_NOMBRE_TEMP")%>
							
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div id="idGWT"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;

						</td>
					</tr>
				</table>
			</div>
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
		<script language='javascript'
			src='<%=request.getContextPath()%>/js/test/edu.opencampus.gwt.test.Test.nocache.js'>
		</script>
	</body>
</html>
