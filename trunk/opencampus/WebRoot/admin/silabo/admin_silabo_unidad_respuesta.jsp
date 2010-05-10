<%@  page
	import="edu.opencampus.lms.modelo.Silabo,java.util.*,edu.opencampus.lms.modelo.ficha.Unidad,edu.opencampus.lms.util.Constante"%>
<%
	response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
	
	Silabo silabo = (Silabo) request.getAttribute("silabo");
	if (silabo != null) {
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/admin/aulavirtual/aula_virtual.js"></script>

<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" style="table-layout: fixed;">
	<caption class="tit_caption">
		 Unidades: <%=silabo.getDescripcion()%></td>
	</caption>
  <%
	List<Unidad> unidadSilabos = silabo.getUnidades();
	int numFilas = unidadSilabos.size();
	if (!unidadSilabos.isEmpty()) {
	%>
  <tr class="fon_color03">
    <td width="40" class="moduloAbajo">
		<input type="hidden" id="numFilas"  value="<%=numFilas%>"><b>ID</b>
    </td>
    <td class="moduloAbajo">
		<b>Nombre</b>
	</td>
	<td width="130" class="moduloAbajo">
		<strong>Jerarqu&iacute;a</strong>
	</td>
	<td align="center" width="40" class="moduloAbajo" colspan="2">
    	&nbsp;
    </td>
    <td align="center" width="25" class="moduloAbajo">
    	&nbsp;
    </td>
    <td align="center" width="25" class="moduloAbajo">
    	&nbsp;
    </td>
    <td align="center" width="25" class="moduloAbajo">
    	&nbsp;
    </td>
    <td colspan="3" width="105" align="center" class="moduloAbajo1"><b>Acciones</b></td>
  </tr>
  <%
	int numFila = 0;
    for(Unidad unidad : unidadSilabos){
	numFila++;
	%>
  <tr onMouseOver="javascript:seleccion(this,true);" onMouseOut="javascript:seleccion(this,false);" style="cursor: pointer;">
    
    <td  class="texto1" align="center"><%=unidad.getIdUnidad()%></td>
    <td  class="texto1" style="white-space: nowrap; padding-left: 5px;">
    	<%=unidad.getNombreCompleto()%>
    </td>
    <td class="bor_der_cur" align="center" style="white-space: nowrap;">
   		<%=unidad.getJerarquia().getNombre()%>
    </td>
    <td align="center">
	  	<img src="<%=request.getContextPath()%>/img/icon_test.gif" onclick="javascript:abrirTest('<%=request.getContextPath()%>/aulavirtual/test/Listar.action?idUnidad=<%=unidad.getIdUnidad()%>&unidad=<%=unidad.getNombreCompleto()%>')"/>
  	</td>
  	<td align="center" class="bor_der_cur">
		(<%=unidad.getCantidadTest()%>)
	</td>
    <td align="center" class="bor_der_unid">
    	<img src="<%=request.getContextPath() %>/img/icon_libro.gif" border="0" onclick="javascript:abrirTexto('<%=request.getContextPath()%>/admin/unidad/VerRecurso.action?ruta=1&id=<%=unidad.getIdUnidad()%>');"/>
    </td>
    <td align="center" class="bor_der_cur">
	  <img src="<%=request.getContextPath()%>/img/icon_curso.gif" onclick="javascript:abrirRepaso('<%=request.getContextPath()%>/admin/unidad/VerRecurso.action?ruta=3&id=<%=unidad.getIdUnidad()%>');"/>
 	 </td>
              <td align="center" class="bor_der_cur">
	  <img src="<%=request.getContextPath()%>/img/icon_lab.gif" onClick="javascript:abrirLaboratorioPdf('<%=request.getContextPath()%>/admin/unidad/VerRecurso.action?ruta=2&id=<%=unidad.getIdUnidad()%>');"/>
  	</td>
    <td align="center" width="35">
    <%if(numFila==1){ %>
      <img src="<%=request.getContextPath()%>/img/up_off.gif"/>
    <%}else{%>
      <a onClick="cambiarIndice('up','<%=silabo.getIdSilabo()%>','<%=unidad.getIdUnidad()%>')"> <img src="<%=request.getContextPath()%>/img/up.gif" border="0" style="cursor: pointer;"/></a>
    <%}%>    </td>
    <td align="center" width="35">
	<%if(numFila==numFilas){%>
      <img src="<%=request.getContextPath()%>/img/down_off.gif"/>
    <%}else{%>
      <a onClick="cambiarIndice('dw','<%=silabo.getIdSilabo()%>','<%=unidad.getIdUnidad()%>')"> <img src="<%=request.getContextPath()%>/img/down.gif" border="0" style="cursor: pointer;"/></a>
    <%}%>	</td>
    <td align="center" width="35" class="tabla02_bordeColumna3"><a onClick="eliminarUnidad('<%=silabo.getIdSilabo()%>','<%=unidad.getIdUnidad()%>','<%=unidad.getNombreCompleto()%>')"><img src="<%=request.getContextPath()%>/img/icon_trash.gif" border="0" alt="Eliminar"  style="cursor: pointer;"/></a></td>
  </tr>
  <%}
	} else {
  %>
  <tr>
    <td colspan="7" align="center"><b>Este S&iacute;labo a&uacute;n no contiene unidades.</b></td>
  </tr>
  <%}
  }
  %>
</table>
