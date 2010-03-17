<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="edu.tecsup.lms.etiqueta.NumeroOrdenAlfabetico"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@  page import="java.util.Collection"%>
<%@  page import="edu.tecsup.lms.modelo.aulavirtual.UsuarioMatricula"%>
<%@  page import="edu.tecsup.lms.modelo.aulavirtual.TipoMatriculaRol"%>
<%@  page import="edu.tecsup.lms.modelo.AulaVirtual"%>
<%@  page import="edu.tecsup.lms.util.Formato"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%
Collection<TipoMatriculaRol> usuarios = (Collection<TipoMatriculaRol>)  request.getAttribute("USUARIOS_MATRICULADOS");
AulaVirtual aula = (AulaVirtual)request.getSession().getAttribute(Constante.AULA_ACTUAL);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
	    <script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
	    <script language="javascript" type="text/javascript"
			src='<%=request.getContextPath()%>/js/jComponente.js'></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/ordenamiento.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/directorio.js"></script>
			<style>
			a{
				text-decoration: none;
				color: black;
				border-style: none;
			}
			img{
				border-style: none;
			}
			</style>
	</head>
	<body>
		<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;"> 
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong><s:text name="aula.alumno.pop_up.curso"/><%=aula.getNombreCurso()%><%=aula.getFormacionSeccion()%></strong>						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.print()"><s:text name="aula.alumno.pop_up.imprimir"/></a>						</td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="2%">|</td>
						<td width="4%"><a href="#" class="salir" onClick="window.close()"><s:text name="aula.alumno.pop_up.cerrar"/></a></td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13" height="13" border="0" /> </a>						</td>
					</tr>
				</table>
		  </div>
			<div id="pop_cuerpo" style="width: 500px;"> 
			<%int po=0,total=0;%>
			<%for(TipoMatriculaRol colTmp:usuarios){				
				if(Integer.parseInt(colTmp.getIdRol())==4){
					total = colTmp.getMatriculas().size();
				}
			}		
			%>
				<table width="500" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" border="0">
					<tr class="fon_cab" align="left">
						<td colspan="9" class="tit_cab" valign="top" >
							<s:text name="aula.alumno.mi_clase.titulo"/>
						</td>
					</tr>
					<tr><td height="18" colspan="7" align="right"><a href="#" onClick="todos()" style="color: blue;"><s:text name="aula.alumno.mi_clase.todos"/></a><br></td>
					</tr>
					<tr class="fon_color01">
						<td class="texto2">&nbsp;</td>
						<td align="center" class="texto2">
							<strong> <s:text name="aula.alumno.mi_clase.paterno"/> </strong>
							<a href="javascript:arraySort(1,<%=po++%>,<%=total%>,6)"><img src="<%=request.getContextPath()%>/img/up_down.gif"/></a>
						</td>
						<td  align="center" class="texto2">
							<strong><s:text name="aula.alumno.mi_clase.materno"/></strong>
							<a href="javascript:arraySort(1,<%=po++%>,<%=total%>,6)"><img src="<%=request.getContextPath()%>/img/up_down.gif"/></a>
						</td>
						<td align="center" class="texto2">
							<strong><s:text name="aula.alumno.mi_clase.1er_Nombre"/></strong>
							<a href="javascript:arraySort(1,<%=po++%>,<%=total%>,6)"><img src="<%=request.getContextPath()%>/img/up_down.gif"/></a>
						</td>
						<td align="center" class="texto2">
							<strong><s:text name="aula.alumno.mi_clase.2do_Nombre"/></strong>
							<a href="javascript:arraySort(1,<%=po++%>,<%=total%>,6)"><img src="<%=request.getContextPath()%>/img/up_down.gif"/></a>
						</td>
						<td  align="center" class="texto2">
							<strong><s:text name="aula.alumno.mi_clase.seccion"/></strong>
							<a href="javascript:arraySort(1,<%=po++%>,<%=total%>,6)"><img src="<%=request.getContextPath()%>/img/up_down.gif"/></a>
						</td>
						<td align="center"  class="moduloArriba1">
							<form onSubmit="return popupform(this, 'Nuevo');" name="email"
								action="<%=request.getContextPath()%>/comun/buzon/NuevoMensaje.action" method="post">
								<input type="image" src="<%=request.getContextPath()%>/img/icon_mail_send.gif" border="0"
									style="cursor:pointer" href="javascript:document.formName.submit()">
								<input type="hidden" id="destino" name="destino">
							</form>
						</td>
					</tr>

					<%
								      int y=0;
								      for(TipoMatriculaRol col:usuarios){
								     
								   if(!col.getMatriculas().isEmpty()){
								   y=0;
					%>
					<tr>
						<td height="18" colspan="9" align="left" class="arribaAbajo">
							<b><%=col.getNombre()%>(s)</b>
					  </td>
					</tr>
					<%int poo=0,tbl=0;if(Integer.parseInt(col.getIdRol())==4){poo=1;tbl=1;}%>
					<%
								      for(UsuarioMatricula temp:col.getMatriculas()){
								      y++;
					%>
					
					<tr <%if(y%2==1) {%> class="fon_color01" <%} %>>
						<td align="center" class="bor_der_unid">
							<strong><%if(1<col.getMatriculas().size()){out.print(y);}else{out.print("&nbsp;");} %></strong>
						</td>
						
						<td class="bor_der_unid" id="t<%=tbl%>l<%=poo%>c0">
							<!-- <%=Formato.numeroOrdenAlfabetico(temp.getPaterno())%> -->
							<%=Formato.formatoTexto(temp.getPaterno())%>
						</td>
						<td class="bor_der_unid" id="t<%=tbl%>l<%=poo%>c1">
							<!-- <%=Formato.numeroOrdenAlfabetico(temp.getMaterno())%> -->
							<%=Formato.formatoTexto(temp.getMaterno())%>
						</td>
						<td class="bor_der_unid" id="t<%=tbl%>l<%=poo%>c2">
							<!-- <%=Formato.numeroOrdenAlfabetico(temp.getNombre1())%> -->
							<%=Formato.formatoTexto(temp.getNombre1())%>
						</td>
						<td class="bor_der_unid" id="t<%=tbl%>l<%=poo%>c3">
							<!-- <%=Formato.numeroOrdenAlfabetico(temp.getNombre2())%> -->
							<%=Formato.formatoTexto(temp.getNombre2())%>&nbsp;
						</td>
						<td class="bor_der_unid" id="t<%=tbl%>l<%=poo%>c4">
							<!-- <%=Formato.numeroOrdenAlfabetico(temp.getStringSeccion())%> -->
							<%=Formato.formatoTexto(temp.getStringSeccion())%>&nbsp;
						</td>
						<td align="center" id="t<%=tbl%>l<%=poo%>c5">
						<!-- s -->
						<table cellpadding="0" cellspacing="0" border="0">
							<tr><td>
								<input type="checkbox" name="cb" value="<%=temp.getIdUsuario().trim()%>" onclick="updateUser()" />
							</td>
							<td>
								<form onSubmit="return popupform(this, 'Nuevo');" 
									action="<%=request.getContextPath()%>/comun/buzon/NuevoMensaje.action" method="post">
									<input type="image" src="<%=request.getContextPath()%>/img/icon_mail_send.gif" border="0"
										style="cursor:pointer" href="javascript:document.formName.submit()">
									<input type="hidden" id="destino" name="destino" value="<%=temp.getIdUsuario().trim()%>">
								</form>
							</td></tr>
						</table>
						</td>
					</tr>
					<%if(Integer.parseInt(col.getIdRol())==4){poo++;}%>
					<%
					}}}
					%>
				</table>
			</div>
			<div id="pie" style="width: 520px;">
				<%@include file="../pie.jsp" %>
			</div>
		</div>
	</body>
</html>
