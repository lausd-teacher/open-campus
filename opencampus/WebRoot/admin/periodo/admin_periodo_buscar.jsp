<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>
<s:text name="titulo.campus.virtual" />
</title>
<s:include value="/comun/jslibs.jsp"/>
   
</head>
<body>
<div id="contenedor">
<s:include value="/comun/bienvenida.jsp"></s:include>
<div id="cuerpo">
  <div id="pop_cuerpo">
  <form action="<%=request.getContextPath()%>/admin/periodo/Nuevo.action" method="get">
    <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" style="table-layout: fixed;">
      <caption class="fon_cab tit_cab">Per&iacute;odos</caption>
      <tr class="fon_color03">
        <th width="50" align="center" class="modulo">C&oacute;digo</th>
        <th width="50" align="center" class="modulo">A&ntilde;o</th>
        <th class="modulo">Nombre</th>
        <th width="100" align="center" class="modulo">Fecha de Acceso</th>
        <th width="100" align="center" class="modulo">Fecha de Inicio</th>
        <th width="100" align="center" class="modulo">Fecha de Fin</th>
        <th width="100" align="center" class="modulo">Fecha de Cierre</th>
        <th width="100" align="center" class="moduloAbajo1">Acciones</th>
      </tr>
      <%int indice=0; %>
      <c:forEach items='${periodos}' var='p'>
      <%indice++; %>
          <tr <%if(indice%2==0){ %> class="blanco" <%}else{ %> class="tabla01_fila1"	<%} %> >

          <td align="center" class="bor_der_cur"><a href="<%=request.getContextPath()%>/admin/periodo/Editar.action?idPeriodo=<c:out value='${p.idPeriodo}'/>" class="link_curso">
            <c:out value='${p.idPeriodo}' /></a></td>
          <td align="center" class="bor_der_cur"><c:out value='${p.fechaInicioAnio}' /></td>
          <td class="bor_der_cur"><c:out value='${p.nombre}'  /></td>
          <td align="center" class="bor_der_cur"><c:out value='${p.diasEdicion}' /> (<c:out value='${p.fechaEdicionToString}' />)</td>
          <td align="center" class="bor_der_cur"><c:out value='${p.fechaInicioToString}' /></td>
          <td align="center" class="bor_der_cur"><c:out value='${p.fechaFinToString}' /></td>
          <td align="center" class="bor_der_cur"><c:out value='${p.diasRevision}' /> (<c:out value='${p.fechaRevisionToString}' />)</td>
          <td align="center"><a onClick="return confirm('¿Seguro que desea eliminar?');" href="<%=request.getContextPath()%>/admin/periodo/Eliminar.action?idPeriodo=<c:out value='${p.idPeriodo}'/>"><img src="<%=request.getContextPath()%>/img/icon_trash.gif" border="0" width="13" height="15" /></a></td>
        </tr>
      </c:forEach>
      <tr>
        <td class="bor_der_cur">&nbsp;</td>
        <td class="bor_der_cur">&nbsp;</td>
        <td class="bor_der_cur">&nbsp;</td>
        <td class="bor_der_cur">&nbsp;</td>
        <td class="bor_der_cur">&nbsp;</td>
        <td class="bor_der_cur">&nbsp;</td>
        <td class="bor_der_cur">&nbsp;</td>
        <td align="center"><input type="submit" value="Nuevo" class="form_button"></td>
      </tr>
    </table>
  </form>
  </div>
</div>
<div id="pie">
  <%@include file="../../comun/pie.jsp" %>
</div>
</body>
</html>
