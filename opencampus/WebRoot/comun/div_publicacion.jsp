			<%-- capa transparente --%>
			<%
			if (!n.equals("0")) {
			%>
			
			<div id="transparency" style="z-index:1"></div>
			<%
			}
			%>
<%--			</div>--%>
			
			<%--publicaciones--%>
			<%
			int w = 0;
			%>
			<script type="text/javascript">
				function expand(){
					try{
						xGetElementById('sc0').className="";					
					}catch(Exception){
					}
				}
			</script>
			<div id="mensajes" style="position:absolute;z-index:1; display: none;">
				<table width="600"  border="0" align="center" class="tabla00" id="tablaAvisos">
					<%
							if (!n.equals("0")) {
							
							out
							.print("<td align=\"left\" width=\"100%\" height=\"23\" class=\"publicaciones\">Nuevos avisos y lecturas</td>");
							out.print("</tr>");
						}
					%>
					<c:forEach items="${publicaciones}" var="publicacion">					
						<form action="" method="post">
						<tr    id="apub<c:out value="${publicacion.idPublicacion}"/>">
							<td colspan="3">
							<table border="0"  width="100%"class="franja">
								<tr>
									<td  >
									<a href="#" onClick="expandcontent('sc<%=w%>')" style="text-decoration:none;"><strong
									class="titulo"> 
								<c:out
											value="${publicacion.titulo}" /> </strong> </a>
									</td>
								</tr>
							</table>
							</td>
						</tr>
						<tr id="bpub<c:out value="${publicacion.idPublicacion}"/>">
							<td colspan="3" bgcolor="#8AD0FF">
								<div id="sc<%=w%>" class="switchcontent"
									style="display: compact;">
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<tr align="left" class="titulo">
											<td valign="top" width="25%">
												<strong class="alineacion">Enviado por : <c:out
														value="${publicacion.nombreCompleto}" />
												</strong>
											</td>
										</tr>
										<tr class="titulo" align="left">
											<td>
												<strong class="alineacion">Fecha de
													publicaci&oacute;n: <c:out
														value="${publicacion.fechaToString}" />
												</strong>
											</td>
										</tr>
										<tr>
										  <td class="titulo" height="5" valign="top"></td>
										</tr>
										<tr align="left" >
											<td colspan="3" align="left" bgcolor="#FFFFFF" class="titulo1" >
												<c:out value="${publicacion.contenido}" escapeXml="false" />
												<c:out value="${publicacion.archivoNombre}"	escapeXml="false" />
												<c:if test="${publicacion.idHerramienta=='2'}">
													<a href="<%=request.getContextPath()%>/aulavirtual/DescargarRecurso.action?archivoNombre=<c:out value="${publicacion.archivoNombre}"/>&idPublicacion=<c:out value="${publicacion.idPublicacion}"/>">
									          		<img src="<%=request.getContextPath()%>/img/icon_download.gif" alt="Descargar" width="17" height="18" border="0"/>
									          	</a>
												</c:if>									          	
											</td>
										</tr>
										<tr><td bgcolor="#FFFFFF" height="5"></td></tr>
										<tr>
											<td align="center">
											<input type="button" class="form_button"
													onclick="cambiarMensajeEstado(<c:out value="${publicacion.idPublicacion}"/>)"
													value="Continuar" />
											</td>
						</tr>
				</table>
								</div>
							</td>
						</tr>
						</form>
						<%
						w++;
						%>
					</c:forEach>
			  </table>
				
			</div>
			<%-- fin publicaciones --%>