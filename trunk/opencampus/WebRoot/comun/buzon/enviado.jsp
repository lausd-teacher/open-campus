<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%String contexto = request.getContextPath();%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    
    <title><s:text name="titulo.campus.virtual" /></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=contexto %>/estilos/estilos.css" rel="stylesheet" type="text/css"/>
<%--    <link href="/estilos/estilos.css" rel="stylesheet" type="text/css">--%>
  </head>
  
  <body>
  <table width="100%" border="0" cellpadding="7" cellspacing="0">
    <tr>
      <td><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"class="bor_tabla">
						<tr class="fon_cab">
							<td height="20" colspan="5" class="tit_cab">Enviado</td>
        </tr>
        <tr>
          <td height="80"><div align="center">Su mensaje ha sido enviado satisfactoriamente.<br>
            <br>
            <input class="form_button" name="button" type="button" onClick="window.close()" value="Aceptar">
          </div></td>
        </tr>
        <tr>
          <td   id="pie" ><%@include file="../pie.jsp"%></td>
        </tr>
      </table></td>
    </tr>
  </table>
  </body>
  
  
</html>
