<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%	Usuario usuarioBienvenida = (Usuario) request.getSession().getAttribute(Constante.USUARIO_ACTUAL);
	//System.out.println(usuarioBienvenida.toStringFull());
%>

<div id="menu_principal">
	<table width="100%" border="0" cellpadding="3" cellspacing="0" height="20px;">
		<tr>
			<td width="1%">&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td width="65%">
				<script type="text/javascript">saludo('<s:text name="portal.saludo.dia"/>','<s:text name="portal.saludo.tarde"/>','<s:text name="portal.saludo.noche"/>')</script>:
			  <strong style="color:#44659B"><%=usuarioBienvenida.getNombreCompleto()%> </strong>
		  </td>
		  <td width="20%" align="right">
		  	<div id="reloj"></div>
		  </td>
		  <td width="5" height="10" align="center">
				|						</td>
			<td width="20">
				
				<img onclick="abrir_<%=Constante.SERVICIO_CURSO%>();" src="<%=request.getContextPath()%>/img/icon_libro.gif"
						 width="16" height="15" border="0" title="<s:text name="portal.bienvenida.cursos.alt"/>" style="cursor:pointer;" />						</td>	
						<td width="5" height="10" align="center">
				|						</td>			
			<td width="20">
			<img onclick="abrir_<%=Constante.SERVICIO_BUZON%>();"
						src="<%=request.getContextPath()%>/img/icon_mail.gif"
						 width="16" height="11" border="0" title="<s:text name="portal.bienvenida.buzon.alt"/>" style="cursor:pointer;" />
				
			</td>
				<td width="5" height="10" align="center">
				|						</td>	
			<td width="20" align="center">					
								
				<img onclick="abrir_<%=Constante.SERVICIO_AGENDA%>();"
										src="<%=request.getContextPath()%>/img/agenda_icon.gif"
										  border="0" style="cursor:pointer;" title="<s:text name="portal.bienvenida.agenda.alt"/>"/>	
												
			</td>
			</td>
				<td width="5" height="10" align="center">
				|						</td>	
			<td width="20" align="center">	
							<img onclick="abrir_<%=Constante.SERVICIO_CHAT%>();"
										src="<%=request.getContextPath()%>/img/icono_chat.gif"
										 title="<s:text name="portal.bienvenida.chat.alt"/>" border="0" style="cursor:pointer;"/>
			</td>
			
				<td width="5" height="10" align="center">
				|						</td>	
					
		
			<td width="20">
				
					<img onclick="abrir_<%=Constante.SERVICIO_APUNTES%>();"  src="<%=request.getContextPath()%>/img/apuntes_icon.gif" style="cursor:pointer;" title="<s:text name="portal.bienvenida.apuntes.alt"/>" width="15" height="16" border="0">
				
			</td> 
				<td width="5" height="10" align="center">
				|						</td>	
			<% if(false){ %>						
			<td width="3%">
				<a href="javascript:void(0)" onclick="javascript:abrirVentanaFlotante('<%=request.getContextPath() %>/glosario/Inicio.action','Glosario',390,500,'0','0')">
					<img src="<%=request.getContextPath()%>/img/notebook_icon.png" title="Glosario" width="15" height="16" border="0" style="cursor:pointer;">
				</img></a>
			</td> 
				<td width="1%" height="10" align="center">
				|						</td>	
			<%} %>
			<td width="20">
				<a href="#" onclick="abrirGuiaEstudiante()"><img
						src="<%=request.getContextPath()%>/img/icon_guia.gif"
						title="<s:text name="portal.bienvenida.guia.alt"/>" width="16" height="16"
						border="0" /> </a>
			</td>
			<td width="5" align="center">
				|
			</td>
			
			<td width="76" style="cursor: pointer; white-space: nowrap;" onclick="verIdiomas(this)">
				<img src="<%=request.getContextPath()%>/img/dow_text.gif" id="arrow_idioma"
				title=""/>&nbsp;<strong><s:text name="idioma.nombre"/></strong>&nbsp;<img 
				src="<%=request.getContextPath()%>/img/flag_<s:text name="idioma"/>.jpg" title="<s:text name="idioma.nombre"/>"/>
			</td>
			<td width="5" align="center">
				|
			</td>
			
			<td width="30">
				<span style="cursor:pointer;"
					onclick="window.location='<%=request.getContextPath()%>/Salir.action'; cerrarVentanas();" class="salir"><s:text name="portal.bienvenida.salir"/></span>
			</td>
			<td width="20" valign="top">
			<span  style="cursor:pointer;"
					onclick="window.location='<%=request.getContextPath()%>/Salir.action'; cerrarVentanas();" >
				<img src="<%=request.getContextPath()%>/img/icon_salir.gif"  onclick="cerrarVentanas();"
					title="<s:text name="portal.bienvenida.salir.alt"/>" width="16" height="16" border="0" /></span>
			</td>
			<td width="5">
				&nbsp;
			</td>
		</tr>
	</table>
</div>
<div id="banner">
</div>
<div id="bienvenida">
	<ct:MenuPortada><s:text name="idioma"/></ct:MenuPortada>
</div>
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
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/aviso.js"></script>
<div id="avisoback" class="avisoback_red" style="display: none;">
	<marquee behavior="scroll" scrolldelay="10" scrollamount="2"><span id="aviso">&nbsp;</span></marquee>
</div>
<script>initAviso();</script>
<%} %>