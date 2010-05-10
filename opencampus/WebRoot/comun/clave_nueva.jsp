<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.modelo.Usuario"%>
<%Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
			if (Constante.SEGURIDAD_USUARIO_PASSWORD_DEBE_CAMBIAR == usuario
					.getEstadoSeguridad()) {
		%>
<div id="div_reinicio">
</div>
<div id="form_div_reinicio">
	<table cellpadding="0" cellspacing="0" border="0" width="300"
				style="background-color: #FFFFFF;" id="id_div_reinicio"
				background="#FFFFFF" class="bor_tabla">
		<tr class="fon_cab">
			<td align="center" valign="middle" width="10">
				&nbsp;
			</td>
			<td height="20" colspan="2" align="left" style="text-align: left"
						class="tit_cab">
				<label>
					Ingrese su nueva clave
				</label>
			</td>
		</tr>
		<tr>
			<td height="25" colspan="3" align="right">
				&nbsp;
				<label id="form_error"
					style="color: red; text-align: right; padding-right: 5px;"></label>
			</td>
		</tr>
		<tr>
			<td width="140" align="right" height="25" colspan="2">
				<span>Nueva clave:</span>
			</td>
			<td align="left">
				&nbsp;
				<input type="password" size="15" id="form_pass2_reinicio" value=""
							onKeyPress="return disableCtrlKeyCombination(this,event);"
							onKeyDown="return disableCtrlKeyCombination(this,event);"
							onkeyup="validarClave(this.value);">
			</td>
		</tr>
		<tr>
			<td width="140" align="right" height="25" colspan="2">
				<span>Nivel de Seguridad:</span>
			</td>
			<td align="left">
				&nbsp;&nbsp;
				<img
							src="<%=request.getContextPath()%>/img/icon_clave_dificultad.jpg"
							id="clave_nivel_img" alt="Nivel" width="120px">
			</td>
		</tr>
		<tr>
			<td align="right" valign="middle" height="25" colspan="2">
				<span>Confirmar nueva clave:</span>
			</td>
			<td align="left" valign="middle">
				&nbsp;
				<input type="password" size="15" id="form_pass1_reinicio" value=""
							onKeyPress="return disableCtrlKeyCombination(this,event);"
							onKeyDown="return disableCtrlKeyCombination(this,event);"
							onkeyup="enviarAGrabar(event,'<%=request.getContextPath()%>');">
			</td>
		</tr>
		<tr>
			<td align="right" height="28">
				&nbsp;
			</td>
			<td align="right" height="28" width="115">
				&nbsp;
			</td>
			<td align="right">
				<input type="button" value="Aceptar" class="form_button"
							id="form_button_reinicio" style="padding-left: 4px;"
							onClick="passwordreinicio('<%=request.getContextPath()%>');">
						&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</div>
<div id="clave_nivel_div"
	style="position: absolute; visibility: hidden; height: 10px; width: 120px; background-color: white; top: 0px; left: 0px; z-index: 99000;">
</div>		
<script type="text/javascript">
	reinicioClave();
</script>
<%
	} 
%>