<%
	response.setContentType("application/vnd.ms-excel;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	Integer periodo = (Integer)request.getAttribute("idMatricula");;
	response.setHeader( "Content-Disposition", "inline;filename=\"Auditoria_de_docente_TV_"+periodo+".xls\"" );
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="edu.opencampus.lms.modelo.aulavirtual.reporte.ReporteDetalle"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<%@  page import="edu.opencampus.lms.util.Formato"%>
<%@  page import="java.util.GregorianCalendar"%>
<%@  page import="java.util.Calendar"%>
<%@  page import="java.text.DateFormatSymbols"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.modelo.Usuario"%>
<%@  page import="edu.opencampus.lms.modelo.aulavirtual.reporte.ReporteDetalle"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" />
		</title>
		<style type="text/css">
		.reporte_table{
	border: 1px solid;
	border-color:#cccccc;
}


.reporte_table label{
	margin-left: 20px;

}

.texto {
	color: #000000;
	background-color: #E5EFF8;
	border-right-width: 1px;
	border-right-style: solid;	
	border-right-color: #cccccc;
}


.textoAbajo1 {
	padding-left: 5px;
	background-color: #E5EFF8;
	border-top-width: 1px;
	border-top-style: solid;	
	border-top-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
}


.textoAbajo2 {
	
	border-top-width: 1px;
	border-top-style: solid;	
	border-top-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
	border-right-width: 1px;
	border-right-style: solid;	
	border-right-color: #cccccc;
}

.textoAbajo3 {
	
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
	border-right-width: 1px;
	border-right-style: solid;	
	border-right-color: #cccccc;
}

.textoAbajo {
	color: #000000;
	background-color: #E5EFF8;
	border-right-width: 1px;
	border-right-style: solid;	
	border-right-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
}

.texto1 {
	color: #000000;
	border-right-width: 1px;
	border-right-style: solid;	
	border-right-color: #cccccc;
}

.blanco {
background-color:#FFFFFF

}
.texto2 {
	color: #000000;
	border-right-width: 1px;
	border-right-style: solid;	
	border-right-color: #cccccc;
	border-top-width: 1px;
	border-top-style: solid;	
	border-top-color: #cccccc;
}

.unidades {
	color: #000000;
	background-color: #E5EFF8;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #cccccc;
	text-align: center;
}

.unidadesAbajo {
	color: #000000;
	background-color: #E5EFF8;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
	text-align: center;
}

.unidadesAbajo3 {
	color: #000000;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
	text-align: center;
}

.unidades1 {
	color: #000000;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #cccccc;
	text-align: center;
}

.notaParcial1 {
	color: #000000;
	background-color: #F8F8F8;
	text-align: center;
	
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
}

.notaParcial {
	color: #000000;
	background-color: #F8F8F8;
	text-align: center;
	
}

.notaParcialAbajo {
	color: #000000;
	background-color: #F8F8F8;
	text-align: center;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
}

.modulo {
	color: #000000;
	border-right-width: 1px;
	border-right-style: solid;	
	border-right-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
}
.moduloFondo {
	color: #000000;
	border-right-width: 1px;
	border-right-style: solid;	
	border-right-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
	background-color: #E5EFF8;
	
}
.moduloFondo4 {
	color: #000000;	
	border: 1px solid #cccccc;		
	background-color: #E5EFF8;	
}
.moduloFondo3 {
	color: #000000;	
	border-bottom: 1px solid #cccccc;	
	border-left: 1px solid #cccccc;	
	border-right: 1px solid #cccccc;		
	background-color: #E5EFF8;	
}
.modulo3 {
	color: #000000;	
	border-bottom: 1px solid #cccccc;	
	border-left: 1px solid #cccccc;	
	border-right: 1px solid #cccccc;		
		
}

.moduloAbajo {
	color: #000000;
	border-right-width: 1px;
	border-right-style: solid;	
	border-right-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
	text-align: center;
}

.moduloAbajo1 {
	
	color: #000000;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
	
}

.moduloFondoAbajo {
	
	color: #000000;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
	background-color: #E5EFF8;
}

.moduloArriba1 {
	
	color: #000000;
	border-top-width: 1px;
	border-top-style: solid;	
	border-top-color: #cccccc;
	
}

.moduloArriba2 {
padding-left:7px;
	color: #000000;
	border-top-width: 1px;
	border-top-style: solid;	
	border-top-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;	
	border-bottom-color: #cccccc;
}

.arribaAbajo{
   border-top-color:#cccccc;
   border-top-style:solid;
   border-top-width: 1px;
   border-bottom-color:#cccccc;
   border-bottom-style:solid;
   border-bottom-width: 1px;
}
.fon_cab_curso_de{
	background-image: url("../img/fon_cab_curso_de.gif");
	background-repeat: no-repeat;
}
.fon_cab_curso_derecha{
	background-image: url(../img/fon_cab_curso_derecha.jpg);
	background-repeat: no-repeat;
}
/***********************************************/
	/* Estilos Campus opencampus                       */
	/***********************************************/
.tabla00 {
	border-bottom-color: #0099FF;
	border-left-color: #0099FF;
	border-bottom-style: solid;
	border-left-style: solid;
	border-right-color: #0099FF;
	border-right-style: solid;
	border-top-color: #0099FF;
	border-top-style: solid;
	background-color: #ffffff;
}
.tabla_test{
	
	
	border: #E5EFF8 solid 3px;

}

.tablaFicha {
	border-right-width: 1px;
	border-left-width: 1px;
	border-top-width: 1px;
	border-bottom-width: 1px;
	border-bottom-color: #cccccc;
	border-left-color: #cccccc;
	border-bottom-style: solid;
	border-left-style: solid;
	border-right-color: #cccccc;
	border-right-style: solid;
	border-top-color: #cccccc;
	border-top-style: solid;
}

.titulo {
	color: #ffffff;
}

.titulo1 {
	color: #000000;
	padding-left: 5px;
	padding-top: 5px;
}

.titulo2 {
	color: #4268C9;
}

.publicaciones {
	color: #0099FF;
	font-weight: bold;
	padding-left: 10px;
}

.franja {
	background-color: #0099FF;
}

.alineacion {
	padding-left: 5px;
}

.datos {
	color: #4268C9;
	font-weight: bold;
	background-color: #E5EFF8;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #cccccc;
}

.datosAbajo {
	color: #4268C9;
	font-weight: bold;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #cccccc;
}

.derecha {
	background-color: #E5EFF8;
}

.datosIz {
	background-color: #F8F8F8;
}

.plan {
	padding-left: 10px;
}

.login {
	color: #cccccc;
	font-weight: bold;
}

.bienvenida {
	font-size: 14px;
	color: #0E6BC9;
	font-weight: bold;
}

.salir {
	color: #000000;
	text-decoration: none;
	font-weight: bold;
}

.pie {
	font-weight: bold;
	text-align: center;
	color: #FFFFFF;
	vertical-align: middle;
	
}

.menu {
	text-decoration: none;
	color: #000000;
	cursor: pointer;
}

.cuadros {
	width: 180px;
}

.tit_cab_cal {
	font-weight: bold;
	color: #FFFFFF;
	font-size: 0.9em;
	text-decoration: none;
}

.tit_cab {
	font-weight: bold;
	color: #FFFFFF;
	padding-left: 10px;
	text-decoration: none;
	vertical-align: middle;
	text-align: left;
}

.tit_caption {
	background-image: url(../img/fon_cab.jpg);
	background-repeat: repeat-x;
	font-weight: bold;
	color: #FFFFFF;
	text-align: left;
	padding-left: 10px;
	text-decoration: none;
	vertical-align: middle;
}

.usuario {
	text-align: center;
	padding-bottom: 5px;
	padding-top: 5px;
}

.tit_popup {
	font-weight: bold;
	padding-left: 10px;
	text-decoration: none;
	color: #003366;
}

.ingr_curso {
	text-decoration: none;
	color: #000000;
	font-weight: bold;
	padding-right: 3px;
}

.form_button {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	font-weight: bold;
	color: #cccccc;
	background-color: #FFFFFF;
	border-top: 1px solid #cccccc;
	border-left: 1px solid #cccccc;
	height: 20px;
	text-align: center;
}

.form_text {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
}

.form_text_selecionado {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	background-color: yellow;
}

.caja_nota {
	height: 18px;
	width: 22px;
}

.paddding_curso {
	padding-left: 3px;
}

.textstatic {
	padding-left: 10px;
}

/***********************************************/
	/* Textos y Links                              */
	/***********************************************/
.text_rojo {
	color: #8D322E;
}

.text_negro {
	color: #000000;
}

.text_amarillo {
	color: #C19834;
}

.text_blanco {
	color: #FFFFFF;
}

.text_verde {
	color: #308D2E;
}

.text_azul {
	color: #003399;
}

.link_verde {
	color: #3BAD39;
}

.link_rojo {
	color: #C95142;
}

.link_rojo_bold {
	color: #FF0000;
	font-weight: bold;
}

.link_amarillo {
	color: #CEAE31;
}

.link_azul {
	color: #4266C9;
}

.link_curso {
	color: #000000;
}

.links {
	color: #FF9900;
	font-weight: bold;
}

.links02 {
	color: #cccccc;
	font-weight: bold;
}

/***********************************************/
	/* LÃ­neas                                      */
	/***********************************************/
.lin_azul {
	border-bottom: 1px solid #4268C9;
}

.lin_rojo {
	border-bottom: 1px solid #8D322E;
}

.lin_verde {
	border-bottom: 1px solid #308D2E;
}

/***********************************************/
	/* Bordes                                      */
	/***********************************************/
.bor_cal {
	border-bottom: 1px solid #308D2E;
	border-left: 1px solid #308D2E;
}

.bor_fec {
	border-top: 1px solid #308D2E;
	border-right: 1px solid #308D2E;
}

.bor_inicio {
	border: 1px solid #AAABAF;
	height: 59px;
}

.bor_plomo {
	border: 1px solid #AAABAF;
}

.bor_verde {
	border-bottom: 1px solid #308D2E;
	border-left: 1px solid #308D2E;
	border-right: 1px solid #308D2E;
}

.bor_amarillo {
	border-bottom: 1px solid #C19834;
	border-left: 1px solid #C19834;
	border-right: 1px solid #C19834;
}

.bor_negro {
	border-bottom: 1px solid #AAABAF;
	border-left: 1px solid #AAABAF;
	border-right: 1px solid #AAABAF;
}

.bor_tabla {
	border: solid #cccccc 1px;
}
.bor_tabla_nobortop {
	
	border-right: solid #cccccc 1px;
	border-left: solid #cccccc 1px;
	border-bottom: solid #cccccc 1px;
}

.bor_der_unid {
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #cccccc;
	text-align: center;
}

.bor_der_cur {
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #cccccc;
}

.bor_caja_curso {
	border-bottom: 1px solid #ABABAB;
	border-left: 1px solid #ABABAB;
	border-right: 1px solid #ABABAB;
	background-color: #F8F8F8;
	padding-bottom: 3px;
}

/***********************************************/
	/* Fondos                                      */
	/***********************************************/
.fon_login {
	background-image: url(../img/login_inf.jpg);
	background-repeat: no-repeat;
	background-position: bottom;
}

.fon_curso {
	background-image: url(../img/fon_cab_curso.jpg);
	background-repeat: no-repeat;
}

.fon_tit_curso {
	background-color: #cccccc;
	color: #cccccc;
}

.fon_cab_ini {
	background-image: url(../img/fon_cab_ini.jpg);
	background-repeat: no-repeat;
	height: 21px;
}

.fon_cab_ini_over {
	height: 18px;
	background-color: #ffffff;
	border-top-width: 1px;
	border-top-style: solid;
	border-top-color: #cccccc;
	border-bottom-width: 1px;
	border-bottom-style: solid;
	border-bottom-color: #cccccc;
	border-left-width: 1px;
	border-left-style: solid;
	border-left-color: #cccccc;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #cccccc;
}

.fon_cab {
	background-color: #cccccc;
	background-repeat: repeat-x;
}

.fon_cab_popup {
	background-image: url(../img/fon_popup.jpg);
	height: 20px;
}

.fon_cab_noticias_pfr {
	background-image: url(../img/fon_cab_noticias_pfr.jpg);
	background-repeat: no-repeat;
	height: 21px;
}

.fon_cab_noticias {
	background-image: url(../img/fon_cab_amarillo.jpg);
	background-repeat: no-repeat;
}

.fon_cab_verde {
	background-image: url(../img/fon_cab_verde.jpg);
	background-repeat: repeat-x;
}

.fon_cab_negro {
	background-image: url(../img/fon_cab_out.jpg);
	background-repeat: repeat-x;
}

.fon_cab_amarillo {
	background-image: url(../img/fon_cab_amarillo.jpg);
	background-repeat: repeat-x;
}

.fon_color01 {
	background-color: #E5EFF8;
}

.fon_color02 {
	background-color: #E0EAF3;
}

.fon_color03 {
	background-color: #F9F9F9;
}

.fon_fecha {
	background-image: url(../img/fon_fecha.jpg);
	background-repeat: no-repeat;
}

/***********************************************/
	/* Tablas                                      */
	/***********************************************/
.tabla_sin_layout {
	table-layout: fixed;
}

.tabla_sin_layout td {
	white-space: nowrap;
	overflow: hidden;
}

.tabla_sin_layout td label {
	white-space: nowrap;
	overflow: hidden;
}

.franja_index {
	background-color: #007ACA;
	
}
.detalles_index {
	
	border: 1px solid #007ACA;
}

/***/
/* ActionError */
/***/

.actionError {
	width: 100%;
	border: 1px solid #F55; 
	background-color: #FFFBE8; 
	color: #F33;
}

.actionError div {
	border-bottom: 1px solid #F55; 
	padding: 2px; 
	background-color: #F55; 
	color:#FFF; 
	font-weight: bold;
}
		
		</style>
<%
Usuario usuario = (Usuario) request.getSession().getAttribute(Constante.USUARIO_ACTUAL);	
ReporteDetalle rdetalle = (ReporteDetalle)request.getAttribute("REPORTE_DETALLE");
%>	
	</head>
	<body>
	
		<c:set var="aula" value='${sessionScope.aula_actual}' />
		<c:set var="reporteDetalle" value='${REPORTE_DETALLE}'></c:set>
		<c:set var="tipo" value='${ATRAS_PAGINA}'></c:set>
		<div id="pop_up" style="width: 100%;">
			<div id="pop_cuerpo" style="width: 96%;">
				<table width="100%" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" border="0">
					<tr class="fon_cab">
						<td height="20" class="tit_cab">
							Auditor&iacute;a
						</td>
					</tr>
					<tr>
						<td>
							<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%"
								class="reporte_table">
								<tr>
									<td height="23" colspan="8" class="datos">
										<strong style="margin-left: 5px;">Datos Generales</strong>
									</td>
									
								</tr>
								<tr>
									<td height="23" width="150" class="texto1">
										<label>
											<strong>Fecha Reporte :</strong>
										</label>
									</td>
									<td height="23"  colspan="3">
										<label>

											<%=Formato.getStringDeDateCompleto(new GregorianCalendar())%>

										</label>
									</td>
									<td class="texto1" colspan="1">
									<label>
											<strong>Carrera : </strong>
										</label>
									</td>
									<td  colspan="3">
									<label>
											<c:out value="${reporteDetalle.nombreEspecialidad}" default=""/>
										</label>
									</td>
									
								</tr>
								
								<tr>
									<td height="23" class="texto">
										<label>
											<strong>Nombre : </strong>
										</label>
									</td>
									<td height="23" class="derecha" colspan="3">
										<label>
											<c:out value="${reporteDetalle.nombreCompleto}"/>
										</label>
									</td>
									<td height="23" class="texto" colspan="1">
										<label>
											<strong>Curso : </strong>
										</label>
									</td>
									<td height="23" class="derecha" colspan="3">
										<label>
											<c:out value="${reporteDetalle.nombreCurso}"/>
											<c:out value="${reporteDetalle.formacionSeccion}"/>
										</label>
									</td>
									
								</tr>
					
							
								<tr>
									<td height="23" class="texto1">
										<label>
											<strong>Fecha Inicio : </strong>
										</label>
									</td>
									<td height="23" colspan="3" >
										<label>
											<c:out value="${reporteDetalle.stringFecha1}"/>
										</label>
									</td>
									<td height="23" class="texto1" colspan="1">
										<label>
											<strong>Fecha Fin : </strong>
										</label>
									</td>
									<td height="23" colspan="3">
										<label>
											<c:out value="${reporteDetalle.stringFecha2}"/>
										</label>
									</td>
									
								</tr>

								<tr>
									<td height="23" class="texto">
										<label>
											<strong>Avisos publicados : </strong>
										</label>
									</td>
									<td height="23" class="derecha" colspan="3">
										<label>
											<c:out value="${reporteDetalle.alerta}"/> / <c:out value="${reporteDetalle.totalAlerta}"/>&nbsp;
										</label>
									</td>
									<td height="23" class="texto" colspan="1">
										<label>
											<strong>Lecturas publicadas: </strong>
										</label>
									</td>
									<td height="23" class="derecha" colspan="3">
										<label>
											<c:out value="${reporteDetalle.lectura}"/> / <c:out value="${reporteDetalle.totalLectura}"/>&nbsp;
										</label>
									</td>	
																
								</tr>
								
								<tr>
									<td height="23" class="texto1">
										<label>
											<strong>Ingresos al curso : </strong>
										</label>
									</td>
									<td height="23"  colspan="3">
										<label>
											<c:out value="${reporteDetalle.cantidadIngreso}"/>
										</label>									
										
										</td>
									<td class="texto1" colspan="1">	
										&nbsp;								
									</td>
									<td colspan="3">
										&nbsp;
									</td>
								
									
								</tr>

								<c:if test="${busquedaFecha1 != null}">
									<tr>
										<td height="23" class="texto">
											<label>
												<strong>Auditor&iacute;a Inicio : </strong>
											</label>
										</td>
										<td height="23"  class="derecha" colspan="3">
											<label>
												<c:out value="${busquedaFecha1}"/>
											</label>
										</td>
										<td height="23" class="texto" colspan="1">
											<label>
												<strong>Auditor&iacute;a Fin : </strong>
											</label>
										</td>
										<td height="23" class="derecha" colspan="3">
											<label>
												<c:out value="${busquedaFecha2}"/>
											</label>
										</td>
										
									</tr>
								</c:if>

							</table>
						</td>
						
					</tr>
					<tr>
						<td height="10">&nbsp;
						</td>
					</tr>
					
					<tr>
						<td height="23">
							<table width="100%" border=0 height="100%" cellpadding="0" cellspacing="0" class="reporte_table" style="table-layout: fixed;">
								<tr>
									<td height="0" width="100">
										
									</td>
								</tr>
								
								
								
							<c:if test="${aula.cantidadTrabajos != 0}">
								
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;">Trabajos</strong>
									</td>
									
									<c:forEach begin="1" end="${reporteDetalle.cantidadUnidades}" step="1" var="n">
									
										<td align="center" class="moduloFondo">
											<strong><c:out value="${n}"></c:out> </strong>
										</td>
									
									</c:forEach>
									
<%--									*** columnas adicionales ***--%>
									
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Estado</strong>
										</label>
									</td>
									
									
									<c:forEach items="${reporteDetalle.recursoTrabajo}" var="trabajo" varStatus="fila">
									
										<th rowspan="4" valign="top">
										<c:choose>
											<c:when test="${trabajo.publicado != null}">
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1" colspan="7">
															<c:out value="${trabajo.publicado}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades" colspan="7">
															<c:out value="${trabajo.entregados}"/>/
															<c:out value="${trabajo.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1" colspan="7">
															<c:out value="${trabajo.calificados}"/>/
															<c:out value="${trabajo.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidadesAbajo" colspan="7">
															<c:out value="${trabajo.expirados}"/>/
															<c:out value="${trabajo.total}"/>&nbsp;
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;">--%>
<%--															<c:out value="${trabajo.fechaActivacion}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">--%>
<%--															<c:out value="${trabajo.fechaEntrega}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
												</table>
											</c:when>
											<c:otherwise>
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidadesAbajo" colspan="7">&nbsp;
															
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
												</table>
											</c:otherwise>
											</c:choose>
										</th>
										
									</c:forEach>
								
									
								</tr>

								<tr>
									<td class="texto" height="23">
										<label>
											<strong>Entregados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Calificados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="textoAbajo" height="23">
										<label>
											<strong>Extemp.</strong>
										</label>
									</td>
								</tr>
<%--								<tr>--%>
<%--									<td class="texto1" height="23">--%>
<%--										<label>--%>
<%--											<strong>Activaci&oacute;n</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td class="textoAbajo" height="23">--%>
<%--										<label>--%>
<%--											<strong>Entrega</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
								
							</c:if>
							<tr>
									<td class="datosAbajo" colspan="<c:out value="${reporteDetalle.cantidadUnidades+1}"/>" height="23">
										&nbsp;
									</td>
								</tr>
							
							<c:if test="${aula.cantidadLaboratoriosCalificados != 0}">
							
							
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;">Laboratorios</strong>
									</td>
									
									<c:forEach begin="1" end="${reporteDetalle.cantidadUnidades}" step="1" var="n">
									
										<td align="center" class="moduloFondo">
											<strong><c:out value="${n}"></c:out> </strong>
										</td>
									
									</c:forEach>
									
<%--									*** columnas adicionales ***--%>
									
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Estado</strong>
										</label>
									</td>
									
									
									<c:forEach items="${reporteDetalle.recursoLaboratorio}" var="laboratorio" varStatus="fila">
									
										<th rowspan="4" valign="top">
											<c:choose>
											<c:when test="${laboratorio.publicado != null}">
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1" colspan="7">
															<c:out value="${laboratorio.publicado}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades" colspan="7">
															<c:out value="${laboratorio.entregados}"/>/
															<c:out value="${laboratorio.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1" colspan="7">
															<c:out value="${laboratorio.calificados}"/>/
															<c:out value="${laboratorio.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidadesAbajo" colspan="7">
															<c:out value="${laboratorio.expirados}"/>/
															<c:out value="${laboratorio.total}"/>&nbsp;
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;">--%>
<%--															<c:out value="${laboratorio.fechaActivacion}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">--%>
<%--															<c:out value="${laboratorio.fechaEntrega}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
												</table>
											</c:when>
											<c:otherwise>
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidadesAbajo" colspan="7">&nbsp;
															
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
												</table>
											</c:otherwise>
											</c:choose>
										</th>
										
									</c:forEach>
								
									
								</tr>

								<tr>
									<td class="texto" height="23">
										<label>
											<strong>Entregados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Calificados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="textoAbajo" height="23">
										<label>
											<strong>Extemp.</strong>
										</label>
									</td>
								</tr>
<%--								<tr>--%>
<%--									<td class="texto1" height="23">--%>
<%--										<label>--%>
<%--											<strong>Activaci&oacute;n</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td class="textoAbajo" height="23">--%>
<%--										<label>--%>
<%--											<strong>Entrega</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
							
							</c:if>
							<tr>
									<td class="datosAbajo" colspan="<c:out value="${reporteDetalle.cantidadUnidades+1}"/>" height="23">
										&nbsp;
									</td>
								</tr>
							
							<c:if test="${aula.cantidadGrupales != 0}">
							
								
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;">Grupales</strong>
									</td>
									
									<c:forEach begin="1" end="${reporteDetalle.cantidadUnidades}" step="1" var="n">
									
										<td align="center" class="moduloFondo">
											<strong><c:out value="${n}"></c:out> </strong>
										</td>
									
									</c:forEach>
									
<%--									*** columnas adicionales ***--%>
									
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Estado</strong>
										</label>
									</td>
									
									
									<c:forEach items="${reporteDetalle.recursoTrabajoGrupal}" var="grupal" varStatus="fila">
									
										<th rowspan="6" valign="top">
											<c:choose>
											<c:when test="${grupal.publicado != null}">
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1" colspan="7">
															<c:out value="${grupal.publicado}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades" colspan="7">
															<c:out value="${grupal.entregados}"/>/
															<c:out value="${grupal.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1" colspan="7">
															<c:out value="${grupal.calificados}"/>/
															<c:out value="${grupal.estudianteTotal}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades" colspan="7">
															<c:out value="${grupal.asignados}"/>/
															<c:out value="${grupal.total}"/>&nbsp;
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1" colspan="7">
															<c:out value="${grupal.expirados}"/>/
															<c:out value="${grupal.total}"/>&nbsp;
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades" style="white-space: nowrap; overflow: hidden;">--%>
<%--															<c:out value="${grupal.fechaActivacion}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap;overflow: hidden;">--%>
<%--															<c:out value="${grupal.fechaEntrega}"/>&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
													<tr>
														<td height="23" class="unidadesAbajo" colspan="7">
															<c:out value="${grupal.intervencion}"/>/
															<c:out value="${grupal.intervencionTotal}"/>&nbsp;
														</td>
													</tr>
												</table>
											</c:when>
											<c:otherwise>
												<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
													<tr>
														<td width="100%" height="23" class="unidades1" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades" colspan="7">&nbsp;
															
														</td>
													</tr>
													<tr>
														<td height="23" class="unidades1" style="white-space: nowrap; overflow: hidden;" colspan="7">&nbsp;
															
														</td>
													</tr>
<%--													<tr>--%>
<%--														<td height="23" class="unidades" style="white-space: nowrap;overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
<%--													<tr>--%>
<%--														<td height="23" class="unidades1" style="white-space: nowrap;overflow: hidden;">--%>
<%--															&nbsp;--%>
<%--														</td>--%>
<%--													</tr>--%>
													<tr>
														<td height="23" class="unidadesAbajo" style="white-space: nowrap;overflow: hidden;">&nbsp;
															
														</td>
													</tr>
												</table>
											</c:otherwise>
											</c:choose>
										</th>
										
									</c:forEach>
								
									
								</tr>

								<tr>
									<td class="texto" height="23">
										<label>
											<strong>Entregados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Calificados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto" height="23">
										<label>
											<strong>Asignados</strong>
										</label>
									</td>
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Extemp.</strong>
										</label>
									</td>
								</tr>
<%--								<tr>--%>
<%--									<td class="texto" height="23">--%>
<%--										<label>--%>
<%--											<strong>Activaci&oacute;n</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--								<tr>--%>
<%--									<td class="texto1" height="23">--%>
<%--										<label>--%>
<%--											<strong>Entrega</strong>--%>
<%--										</label>--%>
<%--									</td>--%>
<%--								</tr>--%>
								<tr>
									<td class="textoAbajo" height="23">
										<label>
											<strong>Debates</strong>
										</label>
									</td>
								</tr>
							
							</c:if>
								<tr>
									<td class="datosAbajo" colspan="<c:out value="${reporteDetalle.cantidadUnidades+1}"/>" height="23">
										&nbsp;
									</td>
								</tr>
							
							<c:if test="${aula.cantidadDialogos != 0}">
								
								<tr>
									<td height="23" class="moduloFondo">
										<strong style="margin-left: 10px;">Diálogos</strong>
									</td>
									
									<c:forEach begin="1" end="${reporteDetalle.cantidadUnidades}" step="1" var="n">
									
										<td align="center" class="moduloFondo">
											<strong><c:out value="${n}"></c:out> </strong>
										</td>
									
									</c:forEach>
									
									
								</tr>
								<tr>
									<td class="texto1" height="23">
										<label>
											<strong>Intervenci&oacute;n:</strong>
										</label>
									</td>
									
									
									<c:forEach items="${reporteDetalle.recursoDialogo}" var="dialogo" varStatus="fila">
									
										<th  valign="top">
											<table cellpadding="0" cellspacing="0" border="0" width="100%" height="100%" style="table-layout: fixed;">
												<tr>
													<td height="23" class="unidades1" colspan="7">
														<c:out value="${dialogo.intervencion}"/>/
														<c:out value="${dialogo.intervencionTotal}"/>&nbsp;
													</td>
												</tr>
											</table>
										</th>
										
									</c:forEach>
								
								</tr>
								

							
							</c:if>
								
							</table>
						</td>
					</tr>
					
					<tr>
						<td height="5">
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" cellpadding="0" cellspacing="0" class="reporte_table" border="0">
								<tr>
									<td height="23" class="moduloFondo" colspan="8">
										<strong style="margin-left: 10px;">Calendario de Ingresos</strong>
									</td>
								</tr>
								<tr>
																			
										<%
										
											ReporteDetalle reporteDetalle = (ReporteDetalle) request.getAttribute("REPORTE_DETALLE");
											
											DateFormatSymbols dfs = new DateFormatSymbols();
											String[] monthNames = dfs.getMonths();
											String[] dayNames = dfs.getShortWeekdays();
										
											GregorianCalendar rightNow = (GregorianCalendar) Calendar.getInstance();
											
											int firstDay = Integer.parseInt(reporteDetalle.getStringFecha1().substring(0,2));
											int firstMonth = Integer.parseInt(reporteDetalle.getStringFecha1().substring(3,5)); 
											int firstYear = Integer.parseInt(reporteDetalle.getStringFecha1().substring(6,10));
											
											int lastDay = Integer.parseInt(reporteDetalle.getStringFecha2().substring(0,2));
											int lastMonth = Integer.parseInt(reporteDetalle.getStringFecha2().substring(3,5)); 
											int lastYear = Integer.parseInt(reporteDetalle.getStringFecha2().substring(6,10));  
											
											rightNow.set(2,firstMonth-1);
											rightNow.set(1,firstYear);
											
											int monthcount = (12-(firstMonth-1))+(lastYear-firstYear-1)*12+(lastMonth);
											
											//-- Fix para excel
											ReporteDetalle reporte = (ReporteDetalle)request.getAttribute("REPORTE_DETALLE");
											if(reporte != null && monthcount < reporte.getCantidadUnidades()+1)monthcount = reporte.getCantidadUnidades()+1;
											//--
											
											for(int c=1;c<= monthcount;c++){
										
												//int day = rightNow.get(5);
												int mon = rightNow.get(2);
												int year = rightNow.get(1);
											
												String monthName = monthNames[mon];
												rightNow.set(5, 1);
											%>
											<td align="center">
												<table border="0" style="border: 1px solid #cccccc;" cellspacing="0" cellpadding="2">
													<caption><strong  class="fon_tit_curso"><%=monthName.toUpperCase()%> <%=year %></strong></caption>
											<%
												out.println("<tr style='background-color: #E5EFF8;'>");
												for (int d = 1; d < 8; d++){
													out.print("<td><strong>" + String.valueOf(dayNames[d].charAt(0)).toUpperCase() + "</strong></td>");
												}
												out.println("</tr>");
												
												int maxDays = rightNow.getActualMaximum(5) + 1;
												int firstDayOfWeek = rightNow.get(Calendar.DAY_OF_WEEK);
												int curDay = 0;
												
												for(int week=1;week<=6;week++){
													out.println("<tr>");
													for(int dayOfWeek=1;dayOfWeek<=7;dayOfWeek++){
														if((week == 1 && dayOfWeek < firstDayOfWeek) || (curDay >= maxDays-1)){
															out.println("<td width='16' height='16'>&nbsp;</td>");
														}else{
															curDay++;
															if(reporteDetalle.isDiaDeIngreso(Formato.setBaseDatosDeDate(new GregorianCalendar(year,mon,curDay)))){
																out.println("<td width='16' height='16' style='background-color: #789BE0;'>");
															}else if((new GregorianCalendar(year,mon,curDay).after(new GregorianCalendar(firstYear,firstMonth-1,firstDay-1)))
																&& (new GregorianCalendar(year,mon,curDay).before(new GregorianCalendar(lastYear,lastMonth-1,lastDay+1)))){
																out.println("<td width='16' height='16' style='background-color: #F7FAFC;'>");
															}else{
																out.println("<td width='16' height='16'>");
															}
															if(dayOfWeek==1){
																out.println("<font color='red'>"+curDay+"</font>");
															}else{
																out.println(curDay);
															}
															out.println("</td>");
														}
														
													}
													out.println("</tr>");
												}
												
												rightNow.set(2,mon+1);
												%>
												</table>
											</td>
												<%
												
												if(c==8)out.print("</tr><tr>");
											}
										%>
										
								</tr>
							</table>
						</td>
					</tr>
					
					
					
				</table>
			</div>
			<div id="pie">
				<%@include file="../../comun/pie.jsp"%>
			</div>
		</div>
	</body>
</html>