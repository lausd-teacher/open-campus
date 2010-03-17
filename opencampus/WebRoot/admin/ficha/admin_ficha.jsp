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
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>	
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script language="javascript" type="text/javascript" 
			src="<%=request.getContextPath()%>/js/fastinit.js"></script>
		<script language="javascript" type="text/javascript" 
			src="<%=request.getContextPath()%>/js/tablesort.js"></script>	
		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/js/jscalendar/calendar-style.css" />
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-es.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/jscalendar/calendar-setup.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/admin/ficha/admin_ficha.js"></script>
	</head>
	<%@include file="../../comun/capas/reloj.jsp"%>
	<body onLoad="mostrarReloj();">
		<div id="contenedor">
			<s:include value="/comun/bienvenida.jsp"></s:include>
			<div id="cuerpo">			
				<div id="pop_cuerpo">
				<s:include value="/error_message.jsp"/>
				<form action="<%=request.getContextPath()%>/admin/ficha/Buscar.action" method="post">
				<table width="100%" cellpadding="3" cellspacing="0"
					class="bor_tabla" border="0" style="table-layout: fixed;"> 
					<caption class="fon_cab tit_cab"><s:text name="portal.menu.curso"/></caption>
					<tbody>
						<tr>
							<td>
								<table width="100%" cellpadding="3" cellspacing="0" border="0">
									<tr>
										<td width="60"><strong class="textstatic">Nombre:</strong></td>
										<td width="300" align="left"><input type="text" name="nombre" value="<c:out value="${nombre}"></c:out>" maxlength="255" class="form_text" size="48"></td>
										<td width="60"><strong class="textstatic">Jerarquía:</strong></td>
										<td width="100">
											<select class="form_text" name="idJerarquia"> 
												<option value="">Seleccione una opción</option>
												<c:forEach items="${jerarquias}" var="j">
													<option value="<c:out value="${j.idJerarquia}"/>"><c:out value="${j.rutaCompleta}"/></option>
												</c:forEach>
											</select> 	 
											<input type="hidden" name="pagina" value="0">
										</td>
										<td rowspan="2" valign="bottom" align="right"><input type="submit" value="Buscar" class="form_button"></td>
									</tr>
									<tr>
										<td><strong class="textstatic">F. Inicio :</strong></td>
										<td align="left">
											<input type="text" id="fechaInicio" size="10" class="form_text" readonly="readonly" name="fechaInicio" value="<c:out value="${fechaInicio}"/>" onchange="clearPhaseInput()"/>
											 &nbsp; &nbsp; <b>hasta</b> &nbsp; &nbsp;
											<input type="text" id="fechaFin" size="10" class="form_text" readonly="readonly" name="fechaFin" value="<c:out value="${fechaFin}"/>" onchange="clearPhaseInput()"/>
										</td>
										<td><strong class="textstatic">Periodo:</strong></td>
										<td> 
											<select class="form_text" name="idPeriodo" onchange="clearDateInputs()"> 
												<option value="">Seleccione una opción</option>
												<c:forEach items="${periodos}" var="p">
													<option value="<c:out value="${p.idPeriodo}"/>"><c:out value="${p.nombre}"/></option>
												</c:forEach>
											</select> 	
										</td> 
									</tr>
								</table>
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr style="background-color: #7EAAD1">
							<td align="right">
								<input type="button" value="En Ejecución" class="form_button" 
									onclick="document.location=''">
								<input type="button" value="Históricos" class="form_button" 
									onclick="document.location=''">	
								<input type="button" value="Lanzar Curso" class="form_button" 
									onclick="document.location='<%=request.getContextPath()%>/admin/ficha/Nuevo.action'">									
							</td>
						</tr>
					</tfoot>
				</table>
				</form>
				<script language="JavaScript" type="text/javascript">
					Calendar.setup({inputField:"fechaInicio", ifFormat:"%d-%m-%Y",  singleClick:true});
					Calendar.setup({inputField:"fechaFin", ifFormat:"%d-%m-%Y",  singleClick:true});	
				</script>
				<br/>
					
					<c:if test="${cursos != null}">
					<table width="100%" cellpadding="3" cellspacing="0" class="bor_tabla" border="0" style="display: none;">
						<tr>
							<td>&nbsp;</td>
							<td width="20">
								<c:choose>
									<c:when test="${pagina >= 2}">
										<a class="opcion_selecionar" style="text-decoration: underline;font-weight: bold;"
											href="<%=request.getContextPath()%>/admin/usuario/Buscar.action?pagina=0"> &lt;&lt; </a>
									</c:when>
									<c:otherwise>
										<span class="opcion_selecionar" style="color: silver;"> &lt;&lt; </span>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="20">
								<c:choose>
									<c:when test="${pagina >= 1}">
										<a class="opcion_selecionar" style="text-decoration: underline;font-weight: bold;"
											href="<%=request.getContextPath()%>/admin/usuario/Buscar.action?pagina=<c:out value="${pagina-1}"/>">
											&lt; </a>
									</c:when>
									<c:otherwise>
										<span class="opcion_selecionar" style="color: silver;"> &lt; </span>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="160" align="center"> 
								<f:Constante campo="BUSQUEDA_CANTIDAD_DIRECTORIO" var="cantidad"/>
								<c:out value="${(pagina * cantidad) + 1}"/>
								-
								<c:out value="${(pagina * cantidad) + fn:length(usuarios)}"/>
								de
								<c:out value="${total}" default="0"/> <s:text name="portal.directorio.usuario"/>(s)
							</td>
							<td width="20">
								<c:choose>
									<c:when test="${paginas - pagina >= 1}">
										<a class="opcion_selecionar" style="text-decoration: underline;font-weight: bold;"
											href="<%=request.getContextPath()%>/admin/usuario/Buscar.action?pagina=<c:out value="${pagina+1}"/>">
											&gt; </span>
									</c:when>
									<c:otherwise>
										<span class="opcion_selecionar" style="color: silver;"> &gt; </span>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="20">
								<c:choose>
									<c:when test="${paginas - pagina >= 2}">
										<a class="opcion_selecionar" style="text-decoration: underline;font-weight: bold;"
											href="<%=request.getContextPath()%>/admin/usuario/Buscar.action?pagina=<c:out value="${paginas}"/>">
											&gt;&gt;
										</a>
									</c:when>
									<c:otherwise>
										<span class="opcion_selecionar" style="color: silver;">&gt;&gt;</span>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</table>
					
					<table width="100%" cellpadding="3" cellspacing="0"
						class="bor_tabla sortable" border="0" style="table-layout: fixed;"> 
						<caption class="fon_cab tit_cab">Fichas</caption>
						<thead>
						<tr>
							<th class="moduloAbajo" align="center" width="40">ID</th>
							<th class="moduloAbajo" align="center">Nombre de Curso</th>
							<th class="moduloAbajo" align="center" width="100">Periodo</th>
							<th class="moduloAbajo" align="center" width="100">Fec. Inicio</th>
							<th class="moduloAbajo" align="center" width="100">Fec. Fin</th>
							<th class="moduloAbajo" align="center" width="180">Jerarqu&iacute;a</th>
							<th class="moduloAbajo1" align="center" width="20">&nbsp;</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${cursos}" var="curso" varStatus="fila">
							<c:choose>
								<c:when test="${fila.count%2==0}">
									<c:set var="color" value="blanco"></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="color" value="tabla01_fila1"></c:set>
								</c:otherwise>
							</c:choose>
							<tr class="<c:out value="${color}"/>" >
								<td align="center" class="bor_der_unid"><c:out value="${curso.idFicha}"/> </td>
								<td class="bor_der_unid"><b><c:out value="${curso.curso.nombre}"/></b> </td>
								<td align="center" class="bor_der_unid"><c:out value="${curso.periodo.nombre}"/> </td>
								<td align="center" class="bor_der_unid"><c:out value="${curso.periodo.fechaInicioToString}"/> </td>
								<td align="center" class="bor_der_unid"><c:out value="${curso.periodo.fechaFinToString}"/> </td>
								<td align="center" class="bor_der_unid"><c:out value="${curso.curso.jerarquia.nombre}"/> </td>
								<td align="center">
									<c:choose>
								  		<c:when test="${curso.estado==1}">
								  			<img src="<%=request.getContextPath() %>/img/activo.gif" border="0" alt="Hacer No Disponible" style="cursor: pointer;"
								  			Xonclick="cambiarEstado(this,<c:out value="${unidad.idUnidad}"/>)"
								  			onclick="window.location.href='<%=request.getContextPath() %>/admin/aulavirtual/AulaVirtual.action?id=<c:out value="${curso.idFicha}"/>'" />
								  		</c:when>
								  		<c:otherwise>
								  			<img src="<%=request.getContextPath() %>/img/desactivo.gif" border="0" alt="Hacer Disponible" style="cursor: pointer;"
								  			Xonclick="cambiarEstado(this,<c:out value="${unidad.idUnidad}"/>)"/>
								  		</c:otherwise>
								  	</c:choose>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					
					</c:if>
				</div>				
			</div>
			<div id="pie">
				<%@include file="/comun/pie.jsp"%>
			</div>
		</div>
	</body>
</html>
