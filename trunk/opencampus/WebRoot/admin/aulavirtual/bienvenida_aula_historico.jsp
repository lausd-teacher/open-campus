<div id="avisoback" class="avisoback_red" style="display: none;">
	<marquee behavior="scroll" scrolldelay="10" scrollamount="2"><span id="aviso">Aviso de prueba</span></marquee>
</div>
<script>initAviso();</script>
<div id="menu_principal_alumno"  style="padding-left:5px;">
<table width="99%" border="0" cellpadding="0" cellspacing="0" >
					<tr   height="20" >
					<td width="5px">
							&nbsp;
						</td>
						<td width="53%" height="20" >
								<strong>
									<a class="link_curso" href="<%=request.getContextPath()%>/Portal.action">
										opencampus Virtu@l									</a>								</strong> 
								<strong>
&gt;								</strong>
								<strong>
									<a class="link_curso"  href="<%=request.getContextPath()%>/admin/ficha/Buscar.action?etiqueta=<%=Constante.ETIQUETA_HISTORICO %>" onclick="javascript:cerrarAulaVirtualVentanas();">
										Todos los cursos									</a>
								<strong>
&gt;								</strong>
								<strong>
									<%=aulaVirtual.getNombreCurso()%><%=aulaVirtual.getFormacionSeccion() %>						  </strong>
					  </td>
						<td width="27%">
							<div id="reloj"></div>
					  </td>
						<td width="5" align="center">
							|						</td>
						<td width="20" valign="top">
							<a href="<%=request.getContextPath() %>/Curso.action"><img
									src="<%=request.getContextPath()%>/img/icon_libro_admin.gif"
									alt="Cursos"  border="0" title="Cursos"  />							</a>						</td>
						<td width="5" align="center">
							|						</td>
						<td width="20" valign="middle">
							<a href="javascript:void(0)" onClick="javascript:portalAbrirBuzon('<%=request.getContextPath() %>');"><img
									src="<%=request.getContextPath()%>/img/icon_mail.gif"
									alt="Correo" width="16" height="11" border="0" title="Correo" />							</a>						</td>
						<td width="5" align="center">
							|						</td>
							<td width="20" valign="middle" align="center">	
						<a href="javascript:void(0)" onclick="abrirAgenda('<%=request.getContextPath()%>/agenda/Cargar.action');">						
							<img
													src="<%=request.getContextPath()%>/img/agenda_icon.gif"
													 alt="Agenda" border="0"/>
													</a>				
						</td>						
						<td width="5" height="10" align="center">
							|						</td>
						<td width="20" align="center">	
									<a href="javascript:void(0)" onclick="javascript:abrirVentanaFlotante('<%=request.getContextPath() %>/anotacion/Anotacion.action','BlocdeNotas',420,500,'0','0')">						
										<img
													src="<%=request.getContextPath()%>/img/icono_chat.gif"
													 alt="Chat" border="0"/>			
													</a>	
						</td>
						<td>
							<td width="5" height="10" align="center">
							|						</td>			
												
						 <td width="20" height="10"  valign="middle" align="center"><a href="javascript:void(0)" onclick="abrirApuntes('<%=request.getContextPath()%>/anotacion/Anotacion.action')">
								<img src="<%=request.getContextPath()%>/img/apuntes_icon.gif" title="Apuntes"  border="0">
						</img></a></td>						
						<td width="5" height="10" align="center">
							|						</td>
						<td width="20">
							<a href="#"><img
									src="<%=request.getContextPath()%>/img/icon_guia.gif"
									alt="Gu&iacute;a del Estudiante"
									title="Gu&iacute;a del Estudiante" width="16" height="16"
									border="0" /> </a>						</td>
						<td width="5" align="center">
							|						</td>
						<td width="30">
							<a href="<%=request.getContextPath()%>/Salir.action"
								class="salir" onclick="javascript:cerrarAulaVirtualVentanas();">Salir</a>						</td>
						<td width="20" align="left">
						<a href="<%=request.getContextPath()%>/Salir.action"
								class="salir" onclick="javascript:cerrarAulaVirtualVentanas();">
							<img src="<%=request.getContextPath()%>/img/icon_salir.gif"
								alt="Salir" title="Salir" width="16" height="16" border="0" /></a>						</td>
					</tr>
				</table>
		</div>