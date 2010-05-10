<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.util.Formato"%>
<%@  page
	import="edu.opencampus.lms.modelo.Usuario,edu.opencampus.lms.modelo.Silabo,edu.opencampus.lms.modelo.ficha.*,java.util.*"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
			Usuario usuario = (Usuario) request.getSession().getAttribute(
			Constante.USUARIO_ACTUAL);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Unidades del Sílabo</title>
<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/admin/silabo/admin_silabo.js'></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/saludo.js"></script>
<style type="text/css">
#lista {
	position:absolute;
	width:480px;
	background-color:#EAEAEA; color:#000000;
	border:1px dotted; border-color:#000000;
	z-index:1;
	top:112px;
	display:none;
}
.resaltado {
	background-color:#FFFFFF; color:#000000;
	cursor: pointer;
}
.normal {
	background-color:#EAEAEA; color:#000000;
}
</style>

</head>
<body onLoad="asignaVariables();">
<div id="pop_up" style="width: 780px;">
  <div id="prin_popup">
    <table width="100%"  border="0" cellspacing="0" cellpadding="3">
      <tr class="fon_cab_popup">
        <td width="90%" class="tit_popup" align="left">Unidades </td>
        <td width="5%"><a href="#" class="salir" onClick="window.close()">Cerrar</a></td>
        <td width="5%">
        <a href="#" class="salir" onClick="window.close()">
        <img src="<%=request.getContextPath()%>/img/salir_x.gif" width="13" height="13" border="0" />
        </a></td>
      </tr>
    </table>
  </div>
  <div id="pop_cuerpo">
	<%
		Silabo silabo = (Silabo) request.getAttribute("silabo");
		if (silabo != null) {
	%>
    <table width="100%" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
      <tr class="fon_cab">
        <td colspan="4" class="tit_cab">B&uacute;squeda de Unidades </td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td width="15">&nbsp;</td>
        <td width="90"><strong>Nombre:</strong></td>
        <td><input name="text" type="text" id="input_2" autocomplete="off" style="width: 476px;" onFocus="if(document.getElementById('lista').childNodes[0]!=null && this.value!='') { filtraLista(this.value); formateaLista(this.value); reiniciaSeleccion(); document.getElementById('lista').style.display='block'; }" onBlur="if(v==1) document.getElementById('lista').style.display='none';"  onKeyUp="if(navegaTeclado(event)==1) { clearTimeout(ultimoIdentificador); ultimoIdentificador=setTimeout('rellenaLista()', 1000); }" class="form_text" size="90">
          <input type="hidden" id="idUnidad" value="">
          <input type="hidden" id="idSilabo" value="<%=silabo.getIdSilabo()%>"></td>
        <td width="100" align="left"><input type="button" value="Agregar" onClick="agregarUnidad()" class="form_button"></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td><div id="lista" onMouseOut="v=1;" onMouseOver="v=0;"></div></td>
        <td>&nbsp;</td>
      </tr>
    </table>
    <%}%>
    <div id="Unidades_Silabo" style="padding-top:10px">
      <jsp:include page="admin_silabo_unidad_respuesta.jsp" />
    </div>
  </div>
  <div id="pie">
      <%@include file="../../comun/pie.jsp" %>
  </div>
</div>
</body>
</html>
