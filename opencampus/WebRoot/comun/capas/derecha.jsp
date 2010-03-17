<link href="<%=request.getContextPath()%>/estilos/chat.css"
			rel="stylesheet" type="text/css">
<script type='text/javascript'
	src='<%=request.getContextPath()%>/dwr/engine.js'> </script>
<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/util.js'> </script>
<script type='text/javascript'
			src='<%=request.getContextPath()%>/dwr/interface/UsuariosConectados.js'> </script>
<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/chat.js'></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/dragresize.js"></script>
<script type="text/javascript">
	var dragresize = new DragResize('dragresize', { minWidth: 170, minHeight: 215, minLeft: 20, minTop: 20, maxLeft: 980, maxTop: 1000 });
	
	dragresize.isElement = function(elm){
	 if (elm.className && elm.className.indexOf('drsElement') > -1) return true;
	};
	dragresize.isHandle = function(elm)	{
	 if (elm.className && elm.className.indexOf('drsMoveHandle') > -1) return true;
	};
		
	dragresize.ondragfocus = function() { };
	dragresize.ondragstart = function(isResize) { };
	dragresize.ondragmove = function(isResize) { };
	dragresize.ondragend = function(isResize) { };
	dragresize.ondragblur = function() { };
	
	dragresize.apply(document);
</script>
		<br />

		<table width="150" border="0" align="right" cellpadding="0"	cellspacing="0">
			<tr class="fon_cab_negro">
				<td height="21">
					<img src="<%=request.getContextPath()%>/img/icon_usuario.gif" alt="new" width="14" height="16" hspace="5" align="middle" />
					<b class="text_blanco">&iquest;Qui&eacute;n est&aacute; en l&iacute;nea?</b>
				</td>
			</tr>
			
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="bor_negro">
						<tbody id="contactos">
						
							<tr><td><span id="loading">Cargando...</span></td></tr>
							
							<tr id="ncabzado"><td><strong>
								<span id="online" class="text_rojo"></span><span>&nbsp;</span><span	id="s"></span>
							</strong></td></tr>
							
							<tr>
								<td>
									<div id="conectados">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="tablaConectados">
											<tbody id="contactos">
												<tr id="modelo"><td>
													<span id="modelo_" class="flecha_online" onMouseOver="showMe(this);" onMouseOut="hideMe(this);">
													&nbsp;</span><a class="aChat" href="javascript:void(0)" id="name_" onClick="javascript:newChat(this.id)"></a>
													<span id="rol_" style="display:none"></span>
													<span id="hora_" style="display:none"></span>
													<span id="full_" style="display:none"></span>
												</td></tr>
											</tbody>
										</table>
									</div>
								</td>
							</tr>
							
							<tr>
								<td style="padding-left:4px;padding-bottom:2px"><input id="buscamelo" size="23" maxlength="30"
								onkeyup="usuario.buscalo()" value="Buscar usuario" style="color:gray;font-size:9pt"
								onfocus="this.className='ChacSeleccionado';HideBeginText();"
								onblur="this.className='ChacDesSeleccionado';ShowBeginText();"></input></td>
							</tr>
							
						</tbody>
					</table>
				</td>
			</tr>
			
		</table>
		
		
			
		<div id="reja" style="display:none;background-color:#EEE;opacity:.5;-moz-opacity:.55;filter:alpha(opacity=50);position:absolute;right:0px;height:230px;top:0px;width:160px">
		</div>
		

		<div id="masInfo_" style="visibility: hidden; position: absolute; z-index: 99">
			<table width="205" border="0" cellspacing="0" cellpadding="0">
			    <tr>
			      <td class="sup_izq">&nbsp;</td>
			      <td class="sup_cen" width="45" height="18" background="<%=request.getContextPath()%>/img/c2.gif">&nbsp;</td>
			      <td class="sup_cen1" width="150">&nbsp;</td>
			      <td class="sup_der" width="5" background="<%=request.getContextPath()%>/img/c4.gif">&nbsp;</td>
			    </tr>
			    <tr>
			      <td class="cen_sup" background="<%=request.getContextPath()%>/img/c5.gif">&nbsp;</td>
			      <td class="cen_cen" colspan="2" bgcolor="#FFFFFF"><span id="fullName_"></span><br><span id="roles_"></span><br id="rolBr_"><span id="hor_"></span></td>
			      <td class="cen_sup" background="<%=request.getContextPath()%>/img/c6.gif">&nbsp;</td>
			    </tr>
			    <tr>
			      <td class="inf_der" height="6" background="<%=request.getContextPath()%>/img/c7.gif">&nbsp;</td>
			      <td class="inf_cen" colspan="2"></td>
			      <td class="inf_der" background="<%=request.getContextPath()%>/img/c9.gif">&nbsp;</td>
			    </tr>
			  </table>
		</div>

		<div id="masInfo2_"	style="visibility: hidden; position: absolute; z-index: 99">
			<table width="155" border="0" cellspacing="0" cellpadding="0">
			    <tr>
			      <td class="sup_cen" width="5" background="<%=request.getContextPath()%>/img/c1.gif">&nbsp;</td>
			      <td class="sup_cen2" width="45" height="18" background="<%=request.getContextPath()%>/img/c3.gif">&nbsp;</td>
			      <td class="sup_cen2" width="100">&nbsp;</td>
			      <td width="5" background="<%=request.getContextPath()%>/img/c4.gif" style="background-repeat:no-repeat; background-position:left;">&nbsp;</td>
			    </tr>
			    <tr>
			      <td class="cen_sup" background="<%=request.getContextPath()%>/img/c5.gif">&nbsp;</td>
			      <td class="cen_cen" colspan="2" bgcolor="#FFFFFF"><span id="fullName2_"></span><br><span id="roles2_"></span><br id="rolBr2_"><span id="hor2_"></span></td>
			      <td class="cen_inf" background="<%=request.getContextPath()%>/img/c6.gif">&nbsp;</td>
			    </tr>
			    <tr>
			      <td class="cen_sup" background="<%=request.getContextPath()%>/img/c7.gif" style="background-position:bottom;background-repeat:no-repeat; background-position:top;">&nbsp;</td>
			      <td class="cen_sup" height="18" background="<%=request.getContextPath()%>/img/c2_reves.gif" style="background-repeat: no-repeat; background-position:top;">&nbsp;</td>
			      <td class="cen_sup" style="background-image: url(<%=request.getContextPath()%>/img/c8.gif); background-repeat:repeat-x; background-position: top;">&nbsp;</td>
			      <td class="cen_sup" background="<%=request.getContextPath()%>/img/c9.gif" style="background-repeat:no-repeat; background-position:tot;">&nbsp;</td>
			    </tr>
			</table>
		</div>
				
		<div id="avisito" style="position:absolute; bottom: 20px; right: 5px; visibility: hidden;z-index:999999">
			<table width="187" border="0" cellspacing="0" cellpadding="0">    
			    <tr>
			      <td class="av_sup_iz" width="5"   background="<%=request.getContextPath()%>/img/c1.gif">&nbsp;</td>
			      <td class="av_sup_cen" width="55" height="17">&nbsp;</td>
			      <td class="av_sup_cen1" width="107"><p>&nbsp;</p>      </td>
			      <td class="av_sup_cen2" width="15">&nbsp;</td>
			      <td class="av_sup_der" width="5" background="<%=request.getContextPath()%>/img/c4.gif">&nbsp;</td>
			    </tr>
			    <tr>
			      <td class="av_cen_1" background="<%=request.getContextPath()%>/img/c5.gif">&nbsp;</td>
			      <td class="av_cen_2" colspan="2" bgcolor="#FFFFFF"><span id="newOnline"></span></td>
			      <td class="av_cen_3" bgcolor="#FFFFFF"  align="c"><div align="center"><img onClick="xHide('avisito')" style="cursor: pointer" src="<%=request.getContextPath()%>/img/sesion_x.png" width="11" height="11" /></div></td>
			      <td class="av_cen_4" background="<%=request.getContextPath()%>/img/c6.gif">&nbsp;</td>
			    </tr>
			    <tr>
			      <td class="av_inf_iz" height="17" background="<%=request.getContextPath()%>/img/c7.gif" style="background-repeat:no-repeat;">&nbsp;</td>
			      <td class="av_inf_cen" style="background-image: url(<%=request.getContextPath()%>/img/c8.gif); background-repeat:repeat-x;">&nbsp;</td>
			      <td class="av_inf_cen1" width="108" style="background-image: url(<%=request.getContextPath()%>/img/c8.gif); background-repeat:repeat-x;">&nbsp;</td>
			      <td class="av_inf_cen2" style="background-image: url(<%=request.getContextPath()%>/img/c8.gif); background-repeat:repeat-x;">&nbsp;</td>
			      <td class="av_inf_der" background="<%=request.getContextPath()%>/img/c9.gif" style="background-repeat:no-repeat;">&nbsp;</td>
			    </tr>
			</table>
		</div>
		
  		<div class="drsElement elChat" id="modelo_chat_" style="visibility: hidden; position: absolute;" 
			onClick="javascript:chatActivo(this.id);" onKeyPress="dwr.util.onEscape(event,escapalo);">
			<div class="drsMoveHandle">
				<table id="table_" width="100%" border="0" cellpadding="0" cellspacing="0" class="fon_cab_chat">
					<tbody>
						<tr id="draggable_">
							<td width="126" height="21" id="x_" style="padding-left:5px"></td>
							<td id="der1_">
								<a id="x7_" class="aChat" href="javascript:void(0)" onMouseOver="showMe(this);" onMouseOut="hideMe(this)">
								&nbsp;<img src="<%=request.getContextPath()%>/img/i2.gif" style="border: 0;"/>&nbsp;
								</a>
								<a id="xx_" href="javascript:void(0)" onClick="escapalo();">
								<img src="<%=request.getContextPath()%>/img/salir_chat.gif" style="border: 0;" width="13" height="13"/>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="chat" id="chat_">
				<span id="mensaje_"></span>				
			</div>
			<span id="lastMessage_" class="lastMessage"></span>		
			<br/>			
			<div id="chat1_" class="area_texto">
				<textarea id="text_" rows="2" cols="17"  onKeyPress="dwr.util.onReturn(event, sendMessage)"
				onblur="this.className='ChacDesSeleccionado';" onfocus="this.className='ChacSeleccionado';"></textarea>
			</div>			
		</div>
		<script type="text/javascript">
	UsuariosConectados.nuevoConectado();
	initChat();
</script>