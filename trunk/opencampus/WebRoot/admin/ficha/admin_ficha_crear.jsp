<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@  page import="edu.tecsup.lms.util.Constante"%>
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
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>	
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/autocomplete/autocomplete.css" />
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/autocomplete/prototype.js"></script>	
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/autocomplete/autocomplete.js"></script>	
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/jscalendar/calendar-style.css" />
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-setup.js"></script>
		<script language="javascript" type="text/javascript" 
			src="<%=request.getContextPath()%>/admin/aulavirtual/aula_virtual.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/admin/ficha/admin_ficha.js"></script>
	</head>
	<body>
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo">			
				<div id="pop_cuerpo">
				<s:include value="/error_message.jsp"/>
				
				<c:if test="${idFicha != null}">
					<div id="message" style="display: block;">La ficha ha sido creada de forma satisfactoria. <a href="<%=request.getContextPath()%>/admin/AulaVirtual.action?id=<c:out value="${idFicha}"/>">Ingresar...</a></div>
				</c:if>
				
					<form action="<%=request.getContextPath()%>/admin/ficha/Crear.action" method="post" onsubmit="return validar(this);">
					<table width="100%" cellpadding="3" cellspacing="0"
						class="bor_tabla" border="0" style="table-layout: fixed;"> 
						<caption class="fon_cab tit_cab">Lanzar Ficha Nueva</caption>
						<tbody>
							<tr>
								<td>
									<table width="100%" cellpadding="3" cellspacing="0" border="0">
										<tr>
											<td width="60"><strong class="textstatic">Nombre:</strong></td>
											<td width="100"><input type="text" name="nombre" value="<c:out value="${nombre}"></c:out>" maxlength="255" class="form_text" size="48" onkeyup="$('idCurso').value =  0;">
											<input type="hidden" name="idCurso" value="<c:out value="${idCurso}" default="0"/>"><input type="hidden" name="idSilabo" value="<c:out value="${idSilabo}" default="0"/>"></td>
											<td width="60"><strong class="textstatic">Periodo:</strong></td>
											<td width="100"> 
												<select class="form_text" name="idPeriodo" onchange="clearDateInputs()">
													<option value="0">Periodo personalizado</option>
												<c:forEach items="${periodos}" var="p">
													<option value="<c:out value="${p.idPeriodo}"/>" <c:if test="${p.idPeriodo == idPeriodo}">selected</c:if>><c:out value="${p.nombre}"/></option>
												</c:forEach>
												</select> 	
											</td> 
											<td width="60"><strong class="textstatic">F. Inicio :</strong></td>
											<td width="80">
												<input type="text" id="fechaInicio" size="10" class="form_text" readonly="readonly" name="fechaInicio" value="<c:out value="${fechaInicio}"/>" onchange="clearPhaseInput()"/>
											</td>
											<td width="60"><strong class="textstatic">F. Fin :</strong></td>
											<td width="80">
												<input type="text" id="fechaFin" size="10" class="form_text" readonly="readonly" name="fechaFin" value="<c:out value="${fechaFin}"/>" onchange="clearPhaseInput()"/>
											</td>
											<td><input type="submit" value="Lanzar Curso" class="form_button"></td>
										</tr>
									</table>
								</td>
							</tr>
						</tbody>
						<tfoot>
							<tr style="background-color: #7EAAD1">
								<td align="right">
									&nbsp;			
								</td>
							</tr>
						</tfoot>
					</table>
					</form>
					<script language="JavaScript" type="text/javascript">
							Calendar.setup({inputField:"fechaInicio", ifFormat:"%d-%m-%Y",  singleClick:true});
							Calendar.setup({inputField:"fechaFin", ifFormat:"%d-%m-%Y",  singleClick:true});	
							new Autocomplete('nombre', function() { 
								return '<%=request.getContextPath()%>/admin/curso/BuscarAutocompletar.action?nombre=' + this.value; 
							});
					</script>
					<br/>
					
					<div id="sylabus"></div>
					
					<br/>
				</div>				
			</div>
			<div id="pie">
				<%@include file="/comun/pie.jsp"%>
			</div>
		</div>
	</body>
</html>
