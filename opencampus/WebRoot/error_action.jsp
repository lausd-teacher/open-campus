<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<title>Campus Virtual de Tecsup</title>
		<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div id="contenedor">
			<div id="menu_principal">
				<table width="100%" border="0">
					<tr>
						<td width="4%">&nbsp;							
						</td>
						<td width="96%">
							<strong style="color:#333333">Campus Virtu@l Tecsup</strong>
						</td>
					</tr>
				</table>
			</div>
			<div id="cuerpo">
				<table width="100%" height="470" border="0" cellpadding="0" cellspacing="7">
					<tr>
					  <td valign="top" class="xbor_tabla" height="450">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0"  height="450">
                        <tr>
                          <td valign="top"><img src="<%=request.getContextPath()%>/img/logo_index.jpg" width="255" height="51"></td>
                          <td width="290">&nbsp;</td>
                        </tr>
                        <tr>
                          <td valign="top" align="right"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                            	<td colspan="4">
                            		<div align="right"><img src="<%=request.getContextPath()%>/img/interrogacion.jpg" width="117" height="105"></div>
                            	</td>
                            </tr>
                            <tr>
                              <td width="16%">&nbsp;</td>
                              <td width="58%"><div align="left" class="bienvenida">Ocurri&oacute; un error inesperado, </div></td>
                              <td width="23%">&nbsp;</td>
                              <td width="3%">&nbsp;</td>
                            </tr>
                            <tr>
                              <td height="40" colspan="3">&nbsp;</td>
                              <td>&nbsp;</td>
                            </tr>
                            <tr>
                              <td>&nbsp;</td>
                              <td colspan="2" class="bienvenida" style="padding-right:30px">
                              	<jsp:useBean id="now" class="java.util.Date" />
                              	<div align="left">Fecha: <fmt:formatDate  value="${now}" type="both" pattern="EEEE, dd 'de' MMMM 'de' yyyy, HH:mm" />
                              	</div>
                              </td>
                              <td>&nbsp;</td>
                            </tr>
                            <tr>
                              <td>&nbsp;</td>
                              <td colspan="2" class="bienvenida" style="padding-right:30px">
                              	<div align="left">Usuario: <c:out value="${usuario_actual}" default="No ha iniciado sesión"></c:out>
                              	</div>
                              </td>
                              <td>&nbsp;</td>
                            </tr>
                             <tr>
                              <td>&nbsp;</td>
                              <td colspan="2" class="bienvenida" style="padding-right:30px">
                              	<div align="left">
	                              	<u>Detalle:</u> 
	                              	<br/><c:out value="${message}" default="Se ha producido un error no clasificado en el servidor."/>
	                              	<br/><s:actionerror/>  <s:actionmessage/> <s:fielderror />
                              	</div>
                              </td>
                              <td>&nbsp;</td>
                            </tr>
                            <tr>
                              <td>&nbsp;</td>
                              <td colspan="2" class="bienvenida" style="padding-right:30px">
                              	<div align="left">
	                              	<u>Detalle T&eacute;cnico:</u> 
		                            <br/><s:property value="%{exception}" default="ActionException: Error indeterminado."/>
		                            <!-- s:property value="%{exceptionStack}"/-->
	                              	<%if(exception != null){ %>
	                              		<br/>JSPException: <%=exception %>
	                              	<%} %>
                              	</div>
                              </td>
                              <td>&nbsp;</td>
                            </tr>
                            <tr>
                              <td height="40" colspan="3">&nbsp;</td>
                              <td>&nbsp;</td>
                            </tr>
                            <tr>
                              <td>&nbsp;</td>
                              <td colspan="2" class="bienvenida" style="padding-right:30px"><div align="right">por favor contacte con soporte y env&iacute;e este mensaje. </div></td>
                              <td>&nbsp;</td>
                            </tr>
                            <tr>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td height="40">&nbsp;</td>
                              <td>&nbsp;</td>
                            </tr>
                            <tr>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td colspan="2"><div align="center" class="bienvenida">
                                <div align="right">Gracias.</div>
                              </div></td>
                            </tr>
                            <tr>
                              <td><a style="color: black; font-weight: bold;" href="javascript:history.back(1)">&lsaquo;&lsaquo; Regresar</a></td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                            </tr>
                          </table></td>
                          <td align="right" valign="bottom"><img src="<%=request.getContextPath()%>/img/tecsup.jpg" width="286" height="342"></td></tr>
                      </table>
				      </td>
				  </tr>
				</table>
			</div>
			<div id="pie">
					<%@include file="/comun/pie.jsp" %>
			</div>
		</div>
	</body>
</html>
