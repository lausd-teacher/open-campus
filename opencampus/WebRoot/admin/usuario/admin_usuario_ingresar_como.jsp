<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:set var="contextPath" value='${pageContext.request.contextPath}'/>

<title><s:text name="titulo.campus.virtual" /></title>

<link href="<c:out value='${contextPath}'/>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/saludo.js"></script>
<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
<script language="javascript" type="text/javascript" 
			src="<c:out value='${contextPath}'/>/js/util.js"></script>

</head>
<body>

<div id="contenedor">
  <s:include value="/comun/bienvenida.jsp"></s:include>
  
  <!-- *************** CONTENIDO ********************* -->
  
  <div id="cuerpo">
    <div id="pop_cuerpo">
		<s:include value="/error_message.jsp"/>
		<form method="post" action="<c:out value='${contextPath}'/>/admin/LoginAs.action" onsubmit="return !(this.idUsuario.value.trim() === '')">
			<table width="950" border="0" align="center" cellpadding="0" cellspacing="0">
	          <tr>
	            <td class="foro_fon_tit_iz" width="3">&nbsp;</td>
	            <td class="foro_fon_tit" width="100" height="21">Ingresar como</td>
	            <td class="foro_fon_tit_de" width="20">&nbsp;</td>
	            <td>&nbsp;</td>
	          </tr>
            </table>
			<table width="950" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
		        <tr height="23">
		            <td style="padding-left: 5px;">
		            	<strong>Usuario: </strong> <input name="idUsuario" type="text" class="form_text" value="" maxlength="20" size="20" /> <input type="submit" value="Ingresar" class="form_button"/>
		        </tr>
			</table>
		</form>
	
    </div>
  </div>
  <div id="pie">
    <%@include file="../../comun/pie.jsp" %>
  </div>
  
  <!-- *************** FIN CONTENIDO ********************* -->
  
</div>
</body>
</html>
