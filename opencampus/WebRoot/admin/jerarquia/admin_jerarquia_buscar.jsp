<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
Usuario usuario = (Usuario) request.getSession().getAttribute(
Constante.USUARIO_ACTUAL);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
<title>
<s:text name="titulo.campus.virtual" />
</title>
<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/saludo.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
	<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
    
</head>
<body>
<div id="contenedor">
  <s:include value="/comun/bienvenida.jsp"></s:include>
  <div id="cuerpo">
    <div id="pop_cuerpo">
	<form action="<%=request.getContextPath()%>/admin/jerarquia/Nuevo.action" method="get">
      <table width="950" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
        <tr class="fon_cab">
          <td colspan="5" class="tit_cab"> Jerarqu&iacute;a </td>
        </tr>
        <tr>
          <td width="45" align="center" class="moduloAbajo"><strong>Id</strong> </td>
          <td width="465" align="center" class="moduloAbajo"><strong>Nombre </strong> </td>
          <td width="60" align="center" class="moduloAbajo1"><strong>Acciones</strong> </td>
        </tr>
        <%int indice=0; %>
        <c:forEach items='${jerarquias}' var='j'>
        <%indice++; %>
          <tr <%if(indice%2==0){ %> class="blanco" <%}else{ %> class="tabla01_fila1"	<%} %> >
            <td align="center" class="bor_der_unid">
			  <a href="<%=request.getContextPath()%>/admin/jerarquia/Editar.action?idJerarquia=<c:out value='${j.idJerarquia}'/>" class="link_curso">
                <c:out value='${j.idJerarquia}' />
              </a>
			</td>
            <td class="bor_der_cur"><c:out value='${j.rutaCompleta}' escapeXml="false" /></td>
            <%--<td><c:out value='${j.nombre}'/></td>--%>
            <td align="center"><a onClick="return confirm('&iquest;Seguro que desea eliminar?');" href="<%=request.getContextPath()%>/admin/jerarquia/Eliminar.action?idJerarquia=<c:out value='${j.idJerarquia}'/>"><img src="<%=request.getContextPath()%>/img/icon_trash.gif" border="0" width="13" height="15" /></a></td>
          </tr>
        </c:forEach>
        <tr>
          <td class="bor_der_cur">&nbsp;</td>
          <td class="bor_der_cur">&nbsp;</td> 
          <td align="center"><input type="submit" name="Submit" value="Nuevo" class="form_button"></td>
        </tr>
      </table>
    </form>
	</div>
  </div>
  <div id="pie">
    <%@include file="../../comun/pie.jsp" %>
  </div>
</div>
<script type="text/javascript">
	resizeHeight();
</script>
</body>
</html>
