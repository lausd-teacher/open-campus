<% if (usuario.getRolPredeterminado()==Constante.ROL_CAMPUS_ADMINISTRADOR) { %>
<%@ page import="javax.servlet.http.HttpSession,edu.tecsup.lms.modelo.Usuario,java.util.*"%>
					
					<br />
					
					<table width="150" border="0" align="center" cellpadding="0"
						cellspacing="0">
					
						<tr class="fon_cab_negro">
							<td height="21">
								<img src="<%=request.getContextPath()%>/img/icon_usuario.gif"
									alt="new" width="14" height="16" hspace="5" align="middle" />
								<b class="text_blanco">&iquest;Qui&eacute;n est&aacute; en
									l&iacute;nea?</b>
							</td>
						</tr>

						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									bgcolor="#FFFFFF" class="bor_negro">
									<tr>
										<td>
											<p><strong><span class="text_rojo">	
<%
LinkedList<Usuario> c = (LinkedList<Usuario>)session.getServletContext().getAttribute("USUARIOS_LOGEADOS");
int total_conectados = ((c.size()-1) <= 0) ? 0 : (c.size()-1);
out.println(total_conectados);
%>
									</span>usuario(s) en l&iacute;nea</strong>
								</td>
								
</tr>											
<%
int i = 1;
ListIterator it = c.listIterator();
while (it.hasNext()) {
	Usuario u1 = (Usuario)it.next();
	if (!u1.getIdUsuario().equals(((Usuario)session.getAttribute(edu.tecsup.lms.util.Constante.USUARIO_ACTUAL)).getIdUsuario())){
		out.println("<tr><td><span class=\"flecha_online\">"+u1.getUsuarioDato().getNombreApellidoCorto() + "</span></td></tr>");
		// Muestra hasta en número más uno
		if(i++ > 2 && it.hasNext()){
			out.println("<tr><td align=\"right\"><a id=\"txtConectados\" href=\"javascript:void(0);\" onclick=\"javascript:mostrarConectados();\">Ver m&aacute;s</a></span></td></tr>");
			break;
		}
	}
}
%>
											</td>
									</tr>
									<tr><td>&nbsp;</td></tr>
								</table>
							</td>
						</tr>
				
					</table>
					<% } %>
					<div align="center">
					</div>
<script>
function mostrarConectados(){
	xChangeVisibility("conectados");
	var txt = xGetElementById("txtConectados");
	txt.innerHTML=="Ver más" ? txt.innerHTML="Ocultar" : txt.innerHTML="Ver más";
}
</script>	
				
<div id="conectados" style="visibility: hidden; position:absolute; width:250; padding:5px; left:100">

	<table width="100%" border="1" cellpadding="0" cellspacing="0" class="bor_negro">
	<%
	LinkedList<Usuario> c2 = (LinkedList<Usuario>)session.getServletContext().getAttribute("USUARIOS_LOGEADOS");
	ListIterator it2 = c2.listIterator();
	while (it2.hasNext()) {
		out.println("<tr><td><span class=\"flecha_online\">"+((Usuario)it2.next()).getUsuarioDato().getNombreApellidoCorto() + "</span></td></tr>");
	}
	%>
	</table>
	
</div>					