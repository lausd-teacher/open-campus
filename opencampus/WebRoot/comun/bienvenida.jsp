<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="top_menu">
	<table width="100%" border="0" cellpadding="2" cellspacing="0">
		<tr>
			<td width="20" align="center">
				<a href="<%=request.getContextPath()%>/Portal.action"><img src="<%=request.getContextPath()%>/img/icon_config.gif" 
					alt="<s:text name="portal.menu.inicio"/>" border="0"/></a>
			</td>
			<td width="80">
				<span id="top_campus" class="anatips" title="<s:text name="titulo.campus.virtual"/>"><s:text name="titulo.campus.virtual_corto"/></span>
			</td>
			<td width="5" align="center">|</td>
			<td>
				<c:choose>
					<c:when test="${param.aula==null}">
					
						<span id="top_saludo"></span>
						<script type="text/javascript">
							Event.observe(window, 'load', function() { 
									var hora=new Date().getHours();
									if(hora>19){
										$('top_saludo').update('<s:text name="portal.saludo.noche"/>');
									}else if(hora>=12){
										$('top_saludo').update('<s:text name="portal.saludo.tarde"/>');
									}else{
										$('top_saludo').update('<s:text name="portal.saludo.dia"/>');
									}
							});
						</script>:
					  <span id="top_usuario"><c:out value="${usuario_actual.nombreCompleto}"></c:out> </span>
					  
				  </c:when>
				  <c:otherwise>
				  		<a href="<%=request.getContextPath() %>/Curso.action">
							<s:text name="portal.bienvenida.cursos.alt"/>
						</a>
						&gt;							
						<span id="top_usuario">
							<c:out value="${aula.curso.nombre}"></c:out>
						</span>
				  </c:otherwise>
			  </c:choose>
		  </td>
		  <td width="20%" align="right">
				<span id="top_reloj"></span>
				<jsp:useBean id="now" class="java.util.Date" />
				<script type="text/javascript">
					Event.observe(window, 'load', function() { 
							mostrarReloj(new Date(<fmt:formatDate  value="${now}" type="both" pattern="yyyy,MM,dd,HH,mm,ss" />));
					});
				</script>
		  </td>
		  <td width="5" align="center">|</td>
		  <td width="20" align="center">
				<img onclick="abrir_<%=Constante.SERVICIO_CURSO%>();" src="<%=request.getContextPath()%>/img/icon_libro.gif"
						 width="16" height="16" border="0" title="<s:text name="portal.bienvenida.cursos.alt"/>" style="cursor:pointer;" />
		  </td>	
		<td width="5" align="center">|</td>			
		<td width="20" align="center">
			<img onclick="abrir_<%=Constante.SERVICIO_BUZON%>();" src="<%=request.getContextPath()%>/img/icon_mail.gif"
				width="16" height="16" border="0" title="<s:text name="portal.bienvenida.buzon.alt"/>" style="cursor:pointer;" />
				
		</td>
		<td width="5" align="center">|</td>	
		<td width="20" align="center">					
			<img onclick="abrir_<%=Constante.SERVICIO_AGENDA%>();" src="<%=request.getContextPath()%>/img/agenda_icon.gif"
				width="16" height="16" border="0" style="cursor:pointer;" title="<s:text name="portal.bienvenida.agenda.alt"/>"/>								
		</td>
		<td width="5" align="center">|</td>	
		<td width="20" align="center">	
			<img onclick="abrir_<%=Constante.SERVICIO_CHAT%>();" src="<%=request.getContextPath()%>/img/icono_chat.gif"
				width="16" height="16" border="0" title="<s:text name="portal.bienvenida.chat.alt"/>" style="cursor:pointer;"/>
		</td>
		<td width="5" align="center">|</td>	
		<td width="20" align="center">
			<img onclick="abrir_<%=Constante.SERVICIO_APUNTES%>();"  src="<%=request.getContextPath()%>/img/apuntes_icon.gif" 
				width="16" height="16" border="0" style="cursor:pointer;" title="<s:text name="portal.bienvenida.apuntes.alt"/>" >
		</td> 
		<td width="5" align="center">|</td>	
		<td width="20" align="center">
			<img onclick="abrirGuiaEstudiante()" src="<%=request.getContextPath()%>/img/icon_guia.gif"
				width="16" height="16" border="0" title="<s:text name="portal.bienvenida.guia.alt"/>"  style="cursor:pointer;"/>
		</td>
		<td width="5" align="center">|</td>
		<td width="76" style="cursor: pointer; white-space: nowrap;" onclick="verIdiomas(this)">
			<img src="<%=request.getContextPath()%>/img/dow_text.gif" id="arrow_idioma"
				title=""/>&nbsp;<strong><s:text name="idioma.nombre"/></strong>&nbsp;<img 
				src="<%=request.getContextPath()%>/img/flag_<s:text name="idioma"/>.jpg" title="<s:text name="idioma.nombre"/>"/>
		</td>
		<td width="5" align="center">|</td>
		<td width="30" align="right">
				<span style="cursor:pointer;" onclick="window.location='<%=request.getContextPath()%>/Salir.action'; cerrarVentanas();" 
					class="salir"><s:text name="portal.bienvenida.salir"/></span>
		</td>
		<td width="20" align="left">
			<span  style="cursor:pointer;" onclick="window.location='<%=request.getContextPath()%>/Salir.action'; cerrarVentanas();" >
				<img src="<%=request.getContextPath()%>/img/icon_salir.gif"  onclick="cerrarVentanas();"
					title="<s:text name="portal.bienvenida.salir.alt"/>" width="16" height="16" border="0" /></span>
		</td>
		</tr>
	</table>
</div>

<c:if test="${param.aula==null}">
	<div id="banner">
	</div>
	<div id="main_menu">
		<ct:MenuPortada><s:text name="idioma"/></ct:MenuPortada>
	</div>
</c:if>

<div id="menu_idioma"  style="width: 84px; position:absolute; left:0px; top:0px; background-color:#FFF; padding:1px; display: none;">
	<table border="0" cellpadding="3" cellspacing="0" class="tabla01" width="100%">
		<tr style="cursor:pointer;"  onClick="cambiarIdioma('es');"
			onmouseover="seleccion(this, true)" onmouseout="seleccion(this, false)">
			<td>
				<strong><s:text name="idioma.espanol"></s:text></strong>
			</td>
			<td width="18"><img
				src="<%=request.getContextPath()%>/img/flag_es.jpg"
				title="Español"
				border="0" /></td>
		</tr>
		<tr style="cursor:pointer;"  onClick="cambiarIdioma('en');"
			onmouseover="seleccion(this, true)" onmouseout="seleccion(this, false)">
			<td>
				<strong><s:text name="idioma.ingles"></s:text></strong>
			</td>
			<td width="18"><img
				src="<%=request.getContextPath()%>/img/flag_en.jpg"
				title="English"
				border="0" /></td>
		</tr>
	</table>
</div>
<%if(false){ %>
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/aviso.js"></script>
<div id="avisoback" class="avisoback_red" style="display: none;">
	<marquee behavior="scroll" scrolldelay="10" scrollamount="2"><span id="aviso">&nbsp;</span></marquee>
</div>
<script>initAviso();</script>
<%} %>