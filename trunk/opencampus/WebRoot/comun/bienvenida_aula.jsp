<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%Usuario usuario = (Usuario)session.getAttribute(Constante.USUARIO_ACTUAL); %>
<div id="menu_principal_alumno">
<table width="99%" border="0" cellpadding="1" cellspacing="0" class="tabla_sin_layout">
					<tr valign="middle" height="20">
						<td width="5px">
							&nbsp;
						</td>
						<td width="53%" height="10">
								<strong>
									<a class="link_curso" href="<%=request.getContextPath()%>/Portal.action">
										Educa
									</a>
								</strong> 
								<strong>
&gt;								</strong>
								<strong>
									<a class="link_curso"  href="<%=request.getContextPath() %>/Curso.action" onclick="javascript:cerrarAulaVirtualVentanas();">
										<s:text name="portal.bienvenida.cursos.alt"/>
									</a>
&gt;							</strong>
								<strong>
									
									<%=usuario.getAulaActual().getCurso().getNombre()%><%=usuario.getAulaActual().getCurso().getJerarquia().getNombre() %>					  </strong>
					  </td>
						<td width="27%" height="10" align="right">
							<div id="reloj" ></div>
					  </td>
						<td width="5" height="10" align="center">
							|					</td>
						<td width="20" height="10" valign="middle" align="center">
							<a href="<%=request.getContextPath() %>/Curso.action" onclick="javascript:cerrarAulaVirtualVentanas();"><img
									src="<%=request.getContextPath()%>/img/icon_libro.gif"
									 border="0"  style="vertical-align:top" title="<s:text name="portal.bienvenida.cursos.alt"/>"/></a>						</td>
						<td width="5" height="10" align="center">
							|						</td>
						<td width="20" height="10" valign="middle" align="center">
							<a href="javascript:void(0)" onClick="abrir_servicio_buzon()"><img
									src="<%=request.getContextPath()%>/img/icon_mail.gif"
									 width="16" height="11" border="0" title="<s:text name="portal.bienvenida.buzon.alt"/>" />							</a>						</td>
						<td width="5" height="10" align="center">
							|						</td>	
						<td width="20" valign="middle" align="center">	
						<a href="javascript:void(0)" onclick="abrir_servicio_agenda()">						
							<img
													src="<%=request.getContextPath()%>/img/agenda_icon.gif"
													 alt="<s:text name="portal.bienvenida.agenda.alt"/>" border="0"/>
													</a>				
						</td>						
						<td width="5" height="10" align="center">
							|						</td>
						<td width="20" align="center">	
									<a href="javascript:void(0)" onclick="abrir_servicio_chat()">						
										<img
													src="<%=request.getContextPath()%>/img/icono_chat.gif"
													 alt="<s:text name="portal.bienvenida.chat.alt"/>" border="0"/>			
													</a>	
						</td>
						<td>
							<td width="5" height="10" align="center">
							|						</td>			
											
						 <td width="20" height="10"  valign="middle" align="center"><a href="javascript:void(0)" onclick="abrir_servicio_apuntes()">
								<img src="<%=request.getContextPath()%>/img/apuntes_icon.gif" title="<s:text name="portal.bienvenida.apuntes.alt"/>"  border="0">
						</img></a></td>						
						<td width="5" height="10" align="center">
							|						</td>
							 <% if(false){ %>
							 <td width="20" height="10"  valign="middle" align="center"><a href="javascript:void(0)" onclick="javascript:abrirVentanaFlotante('<%=request.getContextPath() %>/glosario/Inicio.action','Glosario',400,500,'0','0')">
								<img src="<%=request.getContextPath()%>/img/notebook_icon.png" title="Glosario" width="12" height="13" border="0">
						</img></a></td>						
						<td width="5" height="10" align="center">
							|						</td>
							<%} %>
						<td width="20" height="10"  align="center">
							<a href="#" onclick="abrirGuiaEstudiante()"><img
									src="<%=request.getContextPath()%>/img/icon_guia.gif"
									
									border="0" align="top"
									title="<s:text name="portal.bienvenida.guia.alt"/>" valign="top" /> </a>						</td>
						<td width="5" height="10" align="center">
							|						</td>
						<td width="30" height="10" align="center">
							<a href="javascript:void(0);"
								class="salir" onclick="window.location='<%=request.getContextPath()%>/Salir.action';javascript:cerrarAulaVirtualVentanas();
								cerrarVentanas();"><s:text name="portal.bienvenida.salir"/></a>						</td>
						<td width="20" height="10" valign="bottom" align="left">
						<a href="javascript:void(0);"
								class="salir" onclick="window.location='<%=request.getContextPath()%>/Salir.action';cerrarVentanas(); cerrarAulaVirtualVentanas();">
							<img src="<%=request.getContextPath()%>/img/icon_salir.gif"
								alt="Salir" onclick="cerrarVentanas(); cerrarAulaVirtualVentanas();"
								 title="Salir" width="16" height="16" border="0" /></a>						</td>
							<td width="5">
								&nbsp;
							</td>
					</tr>
</table>
</div>
<%if(false){ %>
<div id="avisoback" class="avisoback_red" style="display: none;">
	<marquee behavior="scroll" scrolldelay="10" scrollamount="2"><span id="aviso">&nbsp;</span></marquee>
</div>
<script>initAviso();</script>
<%} %>
