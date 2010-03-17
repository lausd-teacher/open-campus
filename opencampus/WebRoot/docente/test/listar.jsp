<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.modelo.test.TestPregunta"%>
<%@  page import="edu.tecsup.lms.modelo.test.TestAlternativa"%>
<%@  page import="java.util.Collection"%>
<%@  page import="java.util.Iterator"%>
<%@  page import="java.util.Map"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<c:set var="aula" value='${sessionScope.aula_actual}' />
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<link href="<c:out value='${contextPath}'/>/estilos/estilos.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
		<script type="text/javascript"
			src='<c:out value='${contextPath}'/>/docente/test/admin_test.js'></script>
			<script type="text/javascript"
			src='<c:out value='${contextPath}'/>/js/aula_virtual.js'></script>
	       
	</head>
	<body>
		<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;"> 
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="90%">
							<c:if test="${aula != null}">
								<strong>Curso : <c:out value="${aula.nombreCurso}"/> </strong>
							</c:if>
						</td>
						<td width="5%"><a href="#" class="salir" onClick="window.print()">Imprimir</a> </td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="2%">|</td>						
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<c:out value='${contextPath}'/>/img/salir_x.gif" width="13" height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="width: 500px;"> 

		<table width="100%" class="tabla01" border="0" cellspacing="0" cellpadding="3">
			<tr>
				<th colspan="2"  class="tabla01_encabezado" style="padding-left: 5px;">Unidad: <%=(String)request.getAttribute("unidad") %></th>
			</tr>
			<tr>
				<td>
					<input type="button" value="Nueva pregunta" class="form_button" onClick="xChangeDisplay('SeleccionarTipo')"/>
				</td><td align="right">
					<c:if test="${aux1 == null}">
						<input type="button" value="Ejecutar Evaluaci&oacute;n" class="form_button" onClick="javascript:abrirTestEjecucion('<c:out value='${contextPath}'/>/aulavirtual/test/Cargar.action?idUnidad=<%=request.getSession().getAttribute("TEST_IDUNIDAD_TMP")%>')"/>
					</c:if>
				</td>
			</tr>
			<tr id="SeleccionarTipo" style="display: none;">
				<td colspan="2">
					<table class="tabla01" width="100%" cellspacing="0" cellpadding="3">
						<tr>
							<td align="center" class="tabla01" width="33%" onMouseOver="javascript:seleccionBlue(this,true);" style="cursor: pointer;" onMouseOut="javascript:seleccionBlue(this,false);"
							onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/test/Nuevo.action?tipo=<%=Constante.TEST_NUM_ASIMPLE %>'">
								<strong><%=Constante.TEST_TIPO_ASIMPLE %></strong>
							</td>
							<td align="center" class="tabla01" width="33%" onMouseOver="javascript:seleccionBlue(this,true);" style="cursor: pointer;" onMouseOut="javascript:seleccionBlue(this,false);"
							onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/test/Nuevo.action?tipo=<%=Constante.TEST_NUM_AMULTIPLE %>'">
								<strong><%=Constante.TEST_TIPO_AMULTIPLE %></strong>
							</td>
							<td align="center" class="tabla01" width="33%" onMouseOver="javascript:seleccionBlue(this,true);" style="cursor: pointer;" onMouseOut="javascript:seleccionBlue(this,false);"
							onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/test/Nuevo.action?tipo=<%=Constante.TEST_NUM_VF %>'">
								<strong><%=Constante.TEST_TIPO_VF %></strong>
							</td>
						</tr>
						<tr>
							<td align="center" class="tabla01" width="33%" onMouseOver="javascript:seleccionBlue(this,true);" style="cursor: pointer;" onMouseOut="javascript:seleccionBlue(this,false);"
							onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/test/Nuevo.action?tipo=<%=Constante.TEST_NUM_RELACIONAR %>'">
								<strong><%=Constante.TEST_TIPO_RELACIONAR %></strong>
							</td>
							<td align="center" class="tabla01" width="33%" onMouseOver="javascript:seleccionBlue(this,true);" style="cursor: pointer;" onMouseOut="javascript:seleccionBlue(this,false);"
							onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/test/Nuevo.action?tipo=<%=Constante.TEST_NUM_COMPLETAR %>'">
								<strong><%=Constante.TEST_TIPO_COMPLETAR %></strong>
							</td>
							<td align="center" class="tabla01" width="33%" onMouseOver="javascript:seleccionBlue(this,true);" style="cursor: pointer;" onMouseOut="javascript:seleccionBlue(this,false);"
							onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/test/Nuevo.action?tipo=<%=Constante.TEST_NUM_ORDENAR %>'">
								<strong><%=Constante.TEST_TIPO_ORDENAR%></strong>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
		<br/>
			<%		
			Collection tests = (Collection)request.getAttribute("TESTS");
			//out.println(tests);
			if(tests != null){
			
				Iterator i = tests.iterator();
			
			%>
				<table width="100%" class="tabla01" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<th colspan="7" class="tabla01_encabezado" style="padding-left: 5px;">Unidad: <%=(String)request.getAttribute("unidad") %></th>
					</tr>
					<tr class="tabla01_subEncabezado">
						<td width="20">ID</td>
						<td>Pregunta</td>
						<td width="60">Tipo</td>
						<td width="60" colspan="3">Acciones</td>
					
					</tr>
				<%
				int fila=1;
				while(i.hasNext()){
				
					TestPregunta t = (TestPregunta)i.next();
					%>
					<tr bgcolor="#e5eff8">
						<td align="center" valign="top" class="tabla02_bordeColumna"><a name="<%=t.getIdTest() %>"></a><%=fila++ %></td>
						<td valign="top" class="tabla02_bordeColumna"><%=t.getPregunta() %></td>
						<td align="center" valign="top" class="tabla02_bordeColumna"><%=t.getTipoToString() %></td>
						<td align="center" valign="top" class="tabla02_bordeColumna">
							<% if(t.getGrafico() == 1){
							 %>
							 	<img src="<c:out value='${contextPath}'/>/img/icon_previo.gif" alt="Ver imagen" style="cursor: pointer;"
							 	onclick="javascript:verImagen('<c:out value='${contextPath}'/>/aulavirtual/test/DescargarImagen.action?idUnidad=<%=t.getIdUnidad() %>&filename=<%=t.getArchivoNombre() %>&alt1=<%Math.random(); %>','<%=t.getArchivoTamanio()%>',this);">
							 <%
							 }
							 %>
						</td>
						<td align="center" valign="top" class="tabla02_bordeColumna">
							<img src="<c:out value='${contextPath}'/>/img/icon_trab.gif" alt="Modificar" style="cursor: pointer;"
							onclick="document.location.href='<c:out value='${contextPath}'/>/aulavirtual/test/Modificado.action?idUnidad=<%=t.getIdUnidad() %>&idTest=<%=t.getIdTest() %>'">
						</td>
						<td align="center" valign="top" class="tabla02_bordeColumna">
							<img src="<c:out value='${contextPath}'/>/img/icon_trash.gif" alt="Eliminar" style="cursor: pointer;"
							onclick="if(window.confirm('¿Esta seguro de eliminar el elemento? ')) document.location.href='<c:out value='${contextPath}'/>/aulavirtual/test/Eliminar.action?idUnidad=<%=t.getIdUnidad() %>&idTest=<%=t.getIdTest() %>'">
						</td>
					
					</tr>
					
<%--						--------------------------- Alternativas ---------------------------------%>
					<tr>
						<td>&nbsp;</td>
						<td colspan="5">
							<table border="0" cellpadding="0" cellspacing="0">
						<%
						
						Map<String,TestAlternativa> alternativas = t.getAlternativas();
						
						if(alternativas != null){
						
							if(t.getTipo() == Constante.TEST_NUM_ASIMPLE){
					
								for (Map.Entry<String,TestAlternativa> alternativaMap : alternativas.entrySet()){
										
									TestAlternativa tA = alternativaMap.getValue();
									String numAlt = alternativaMap.getKey();
										
										%>
										<tr>
											<td height="20" width="20" align="center" valign="middle">
												<%
													if(Constante.TEST_ITEM_CORRECTO == tA.getRespuesta()){
												 %>
													<img src="<c:out value='${contextPath}'/>/img/icon_checked.gif" width="14">
												<%
													}
												 %>
											</td>
												
											<td width="25" valign="middle" style="padding-left: 5px;">
												<%=numAlt %>) 
											</td>
											
											<td valign="middle">
												<%=tA.getTexto() %>
											</td>
										</tr>
										<%
									}
								
							}else if(t.getTipo() == Constante.TEST_NUM_AMULTIPLE){
					
								for (Map.Entry<String,TestAlternativa> alternativaMap : alternativas.entrySet()){
										
									TestAlternativa tA = alternativaMap.getValue();
									String numAlt = alternativaMap.getKey();
										
										%>
										<tr>
											<td height="20" width="20" align="center" valign="middle">
												<%
													if(Constante.TEST_ITEM_CORRECTO == tA.getRespuesta()){
												 %>
													<img src="<c:out value='${contextPath}'/>/img/icon_checked.gif" width="14">
												<%
													}
												 %>
											</td>
												
											<td height="20" width="25" valign="middle" style="padding-left: 5px;">
												<%=numAlt %>) 
											</td>
											
											<td valign="middle">
												<%=tA.getTextoAux() %>
											</td>
										</tr>
										<%
									}
								
							}else if(t.getTipo() == Constante.TEST_NUM_RELACIONAR){
					
								for (Map.Entry<String,TestAlternativa> alternativaMap : alternativas.entrySet()){
										
									TestAlternativa tA = alternativaMap.getValue();
									String numAlt = alternativaMap.getKey();
										
										%>
										<tr>
											<td height="20" width="25" valign="middle" style="padding-left: 5px;">
												<%=numAlt %>) 
											</td>
											
											<td valign="middle">
												<%=tA.getTexto() %>
											</td>
											
											<td valign="middle" style="padding-left: 20px;">
												<b><%=tA.getRespuesta() %></b>
											</td>
											
											<td valign="middle">
												-&gt; &nbsp;&nbsp;<%=tA.getTextoAux() %>
											</td>
										</tr>
										<%
									}
								
							}else if(t.getTipo() == Constante.TEST_NUM_ORDENAR){
					
								for (Map.Entry<String,TestAlternativa> alternativaMap : alternativas.entrySet()){
										
									TestAlternativa tA = alternativaMap.getValue();
									String numAlt = alternativaMap.getKey();
										
										%>
										<tr>
											<td height="20" width="25" valign="middle" style="padding-left: 5px;">
												<%=numAlt %>) 
											</td>
											
											<td valign="middle">
												<%=tA.getTextoAux() %>
											</td>
											
											<td valign="middle" style="padding-left: 20px;">
												<b><%=tA.getRespuesta() %></b>
											</td>
										</tr>
										<%
									}
								
							}else if(t.getTipo() == Constante.TEST_NUM_COMPLETAR){
					
								%>
									<tr><td>&nbsp;</td></tr>
								<%
								
							}else if(t.getTipo() == Constante.TEST_NUM_VF){
							%>
								<tr><td style="padding-left: 5px;">
							<%
									if(Constante.TEST_ITEM_CORRECTO == alternativas.get("A").getRespuesta()){
								 		out.print(Constante.TEST_ITEM_VF_V);
								 	}else{
								 		out.print(Constante.TEST_ITEM_VF_F);
								 	}
							%>
								</td></tr>
							<%
							}
							
						}
							%>
							</table>
						</td>
					</tr>
							
					<%
							
					if(t.getExplicacion() != null){
					%>
						<tr bgcolor="#f3f3f3">
							<td bgcolor="#ffffff">&nbsp;</td>
							<td>
								<b>Explicación:</b> &nbsp;&nbsp;<%=t.getExplicacion() %>
							</td>
							<td colspan=4>&nbsp;</td>
						</tr>
					<%
					}
				}
				 
			 	%>
						 
					<tr style="background-color: #FFFFFF;">
						<td style="height: 10px;">
						</td>
					</tr>
					
<%--						--------------------------- FIN Alternativas ---------------------------------%>		 
			 <%
			}
			 %>
				</table>
			
			<div id="ampliacion" style="BORDER-RIGHT: #666666 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #666666 1px solid; 
			PADDING-LEFT: 2px; VISIBILITY: hidden; PADDING-BOTTOM: 2px; BORDER-LEFT: #666666 1px solid; PADDING-TOP: 2px; 
			BORDER-BOTTOM: #666666 1px solid; BACKGROUND-REPEAT: no-repeat; POSITION: absolute; WIDTH: 130px; HEIGHT: 54px; TOP: 100px; LEFT: 200px;"> 
			<span id="txtampliacion" style="POSITION: absolute; background-color:#ffffff;"></span> 
			<div id="c1">
				<img src="<c:out value='${contextPath}'/>/img/cargando.gif" border="0">
			</div> 
			<div id="cerrarampliacion" style="background-color:#333333; font-family:arial,verdana; font-size:8pt; line-height:20px; 
			text-align:right;float:right; height: 20px; WIDTH: 124px; padding-right:5px;"> 
			<a href="javascript:ocultarImagen()" style="color:#ffffff;">[X] Cerrar</a> 
			</div> 
			</div>
			
			<div id="auditoria" style="BORDER-RIGHT: #666666 1px solid; BORDER-TOP: #666666 1px solid; 
			BORDER-LEFT: #666666 1px solid; BORDER-BOTTOM: #666666 1px solid; background-color:#ffffff;  
			PADDING-LEFT: 5px; PADDING-BOTTOM: 5px; PADDING-TOP: 5px; PADDING-RIGHT: 5px;
			VISIBILITY: hidden; POSITION: absolute; WIDTH: 150px; HEIGHT: 60px;"> 
			
			</div> 
			
			</div>


			<!-- *************** FIN CONTENIDO ********************* -->
			
			<div id="pie">
				<p class="pie">
					<%@include file="../../comun/pie.jsp" %>
				</p>
			</div>
			<!-- *************** FIN CONTENIDO ********************* -->
		</div>
	</body>
</html>
