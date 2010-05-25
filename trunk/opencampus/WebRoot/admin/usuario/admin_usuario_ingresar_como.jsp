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

<s:include value="/comun/jslibs.jsp"/>

</head>
<body>

<div id="contenedor">
  <s:include value="/comun/bienvenida.jsp"></s:include>
  
  <!-- *************** CONTENIDO ********************* -->
  
  <div id="cuerpo">
    <div id="pop_cuerpo">
		<s:include value="/error_message.jsp"/>
		<form method="post" action="<c:out value='${contextPath}'/>/admin/LoginAs.action" onsubmit="return !(this.idUsuario.value.trim() === '')">
			<table width="100%" border="0" cellpadding="3" cellspacing="0" class="open_table">
	          <caption>Ingresar como</caption>
		        <tr height="23">
		            <td>
		            	<strong>Usuario: </strong> <input name="idUsuario" type="text" class="form_text" value="" maxlength="20" size="20" /> <input type="submit" value="Ingresar" class="form_button"/>
		        	</td>
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
