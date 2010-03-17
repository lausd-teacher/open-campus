<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ page import="java.util.Collection"%>
<%@ page import="edu.tecsup.lms.modelo.Usuario"%>
<c:set var="contextPath" value='${pageContext.request.contextPath}' />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title><s:text name="titulo.campus.virtual" /></title>
		<!--[if lt IE 8]>
		<script src="http://ie7-js.googlecode.com/svn/version/2.0(beta3)/IE8.js" type="text/javascript"></script>
		<![endif]--> 
		<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"
			type="text/javascript"></script>
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
					
		<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/dragresize.js"></script>

		<script type="text/javascript">
			var dragresize = new DragResize('dragresize', { minLeft: 10, minTop: 10, maxLeft: 710, maxTop: 500 });
			
			dragresize.isElement = function(elm){
			 if (elm.className && elm.className.indexOf('drsElement') > -1) return true;
			};
			dragresize.isHandle = function(elm)	{
			 if (elm.className && elm.className.indexOf('drsMoveHandle') > -1) return true;
			};
			
			dragresize.apply(document);
		</script>
		
	</head>
	<body onLoad="UsuariosConectados.nuevoConectado(); initChat(); cargar_usuarios_offLine();" onunload="UsuariosConectados.cerrarSession(); this.exit=1; window.close();" onbeforeunload="if(this.exit!==1) return 'Su sesión en el chat será finalizada.';" style="margin-top: 8px;">
		<div id="pop_up" style="width: 100%;">
			<div id="prin_01">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong>Chat</strong>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.print()">Imprimir</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif"
									width="13" height="13" border="0" />
							</a>
						</td>
						<td width="2%">
							|
						</td>
						<td width="4%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>
			<div id="pop_cuerpo" style="background-image: url(../img/foro/fondos/backgroung_01.jpg);">
				<div id="portada" style="width: 100%; height:460px;">
	
		<div style="float: right;">
		<table width="200" border="0"  cellpadding="0" cellspacing="0">
			
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="tablaFicha">
						<tbody id="contactos">
							<tr class="fon_cab">
								<th height="20" colspan="4" class="tit_cab">
									Usuarios en L&iacute;nea
								</th>
							</tr>
							<tr><td><span id="loading">Cargando...</span></td></tr>
								<td>
									<div id="conectados" style="padding: 4px;">
										<table width="100%" border="0" cellpadding="2" cellspacing="0" id="tablaConectados">
											<tbody id="contactos">
												<tr id="modelo"><td>
													<span id="modelo_" class="flecha_online" onMouseOver="showMe(this);" onMouseOut="hideMe(this);">
													&nbsp;</span><a class="aChat" id="name_" onClick="javascript:newChat(this.id)" style="cursor: pointer;"></a>
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
								<td style="padding-left:4px;padding-bottom:2px">
									<strong>Buscar:</strong> &nbsp;
									<input id="buscamelo" size="22" maxlength="30"
										onkeyup="usuario.buscalo()" value="Nombres y/o Apellidos" style="color:gray;font-size:8pt"
										onfocus="this.className='ChacSeleccionado';HideBeginText();"
										onblur="this.className='ChacDesSeleccionado';ShowBeginText();">
								</td>
							</tr>
							<tr id="ncabzado" style="background-color: #E0EAF3;" height="20">
								<td style="padding-left: 3px;">
								<strong>
									<span id="online" class="text_rojo"></span><span>&nbsp;</span><span	id="s"></span>
								</strong>
								</td>
							</tr>
							
							<tr>
						</tbody>
					</table>
				</td>
			</tr>
			
		</table>
		
		<br/>
		
		<table width="200" border="0"  cellpadding="0" cellspacing="0">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="border: 1px solid #9B9CA1;">
						<tbody>
							<tr class="fon_cab_negro">
								<th height="20" colspan="4" class="tit_cab">
									Usuarios en el Campus
								</th>
							</tr>
								<td style="padding: 4px;">
									<div id="noconectados">
									&nbsp;
									</div>
								</td>
							</tr>
							
							<tr style="display: none;">
								<td style="padding-left:4px;padding-bottom:2px">
									<strong>Buscar:</strong> &nbsp;
									<input xxxid="buscamelo" size="22" maxlength="30"
										onkeyup="usuario.buscalo()" value="Nombres y/o Apellidos" style="color:gray;font-size:8pt"
										onfocus="this.className='ChacSeleccionado';HideBeginText();"
										onblur="this.className='ChacDesSeleccionado';ShowBeginText();">
								</td>
							</tr>
							<tr style="background-color: #E0E0E3;" height="20">
								<td style="padding-left: 3px;">
								<strong>
									<span id="offline" class="text_rojo"></span> usuario(s) en el Campus
								</strong>
								</td>
							</tr>
							
							<tr>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		
		</div>	
		<div id="reja" style="display:none;background-color:#EEE;opacity:.5;-moz-opacity:.55;filter:alpha(opacity=50);position:absolute;right:0px;height:100%;top:0px;width:100%">
		</div>
		
		<div id="masInfo_" style="visibility:hidden; position: absolute; z-index: 99;">
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

		<div id="masInfo2_"	style="visibility: hidden; position: absolute; z-index: 99;">
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
			<div class="drsMoveHandle" style="height: 20px; border-bottom: 1px solid #666;">
				<table id="table_" width="101%" border="0" cellpadding="0" cellspacing="0" class="fon_cab_chat">
					<tbody>
						<tr id="draggable_">
							<td width="110" height="21" id="x_" style="padding-left:5px; white-space: nowrap;"></td>
							<td id="der1_">
								<a id="zum_" href="#" onClick="mandarZumbido(this)"><img 
								src="<%=request.getContextPath()%>/img/icon_zumbido_blanco.png" style="border: 0;"/></a>
								<a id="x7_" class="aChat" href="#" onMouseOver="showMe(this);" onMouseOut="hideMe(this)"><img 
								src="<%=request.getContextPath()%>/img/i2.gif" style="border: 0;"/></a>
								<a id="xx_" href="#" onClick="setTimeout('escapalo();',10);"><img 
								src="<%=request.getContextPath()%>/img/salir_chat.gif" style="border: 0;" width="13" height="13"/>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="chat" id="chat_" style="text-align:left;padding-left:4px;padding-top:2px;">
				<span id="mensaje_"></span>				
			</div>
			<span id="lastMessage_" class="lastMessage"></span>		
			<br/>			
			<div id="chat1_" class="area_texto">
				<textarea id="text_" rows="2" cols="17"  onKeyPress="dwr.util.onReturn(event, sendMessage)" 
				onblur="this.className='ChacDesSeleccionado';" onFocus="this.className='ChacSeleccionado';"></textarea>
			</div>
		</div>
		<div>
			<iframe id="alert" src="" frameborder="0" marginwidth="0" width="0" marginheight="0" height="0" scrolling='no'></iframe>
			<iframe id="zumbido" src="" frameborder="0" marginwidth="0" width="0" marginheight="0" height="0" scrolling='no'></iframe>
			<!-- input type="button" value="CLICK" onclick="zumbido()" -->
		</div>	
		</div>
			<div id="pie">
				<%@include file="../pie.jsp"%>
			</div>
		</div>
			
	</body>
</html>