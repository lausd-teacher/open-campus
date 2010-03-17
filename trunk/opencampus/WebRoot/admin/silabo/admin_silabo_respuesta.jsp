<%
	response.setContentType("text/plain;charset=ISO-8859-1"); //Para no tener problemas con ñs y tildes
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@  page
	import="edu.tecsup.lms.modelo.Curso,edu.tecsup.lms.modelo.Silabo,edu.tecsup.lms.util.Constante,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	Collection cursos = (Collection) request.getAttribute("cursos");
	Collection silabos = (Collection) request.getAttribute("silabos");
	
	if (cursos != null) {
%>


 
<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla sortable">
  <caption class="fon_cab tit_cab">Resultado de la b&uacute;squeda
        <input type="hidden" id="numFilas" value="<%=cursos.size()%>">
      </caption>
  
    <%
	if (!cursos.isEmpty()) {
	%>
	<thead>
    <tr class="fon_color03"> 
      <td align="center" width="40" class="moduloAbajo">ID</td>
      <td align="center" class="moduloAbajo" style="white-space: nowrap;"><b>Nombre</b> </td>
      <td align="center" class="moduloAbajo" style="white-space: nowrap;"><b>Jerarqu&iacute;a</b> </td>
      <td align="center" width="20" class="moduloAbajo1 nosort">&nbsp;</td>
    </tr>
    </thead>
    <tbody id="tResultado">
    <%
		Iterator i = cursos.iterator();
		int numFila = 0;
		while (i.hasNext()) {
			Curso curso = (Curso) i.next();
			numFila++;
			%>
    <%-- ................................. Registro ............................... --%>
    <tr onmouseover="javascript:seleccionClass(this,true);" style="cursor: pointer;" onmouseout="javascript:seleccionClass(this,false);" id="curso_<%=numFila%>">
      <td class="bor_der_cur" onclick="javascript:mostrarSilabos('<%=numFila%>','<%=curso.getIdCurso()%>','<%=curso.getNombre().replaceAll("\"","").replaceAll("'","")%>');"><span 
      	class="textstatic"><%=curso.getIdCurso()%></span></td>
      <td class="bor_der_cur" onclick="javascript:mostrarSilabos('<%=numFila%>','<%=curso.getIdCurso()%>','<%=curso.getNombre().replaceAll("\"","").replaceAll("'","")%>');"><span 
      	id="curso_nombre" class="textstatic"><%=curso.getNombre()%></span></td>
      <td class="bor_der_cur" onclick="javascript:mostrarSilabos('<%=numFila%>','<%=curso.getIdCurso()%>','<%=curso.getNombre().replaceAll("\"","").replaceAll("'","")%>');"><span 
      	class="textstatic"><%=curso.getJerarquia().getRutaCompleta()%></span></td>
      <td align="center"><img src="<%=request.getContextPath() %>/img/icon_trash.gif" alt="Eliminar" style="cursor: pointer;"
							onclick="eliminarCurso('<%=curso.getIdCurso()%>')"></td>
    </tr>
    <%--................................... FIN Registro .................................--%>
    <%
			}
		
	%>
		</tbody>
	<%
		} else {
	%>
    <tr>
      <td align="center" colspan="4"><b>Su búsqueda -
        <script language="JavaScript">
			document.write(document.getElementById("nombre").value); 
		</script>
        - No produjo ningún resultado.</b> </td>
    </tr>
    <%}%>
  
</table>

<%
	}else if(silabos != null){
	
%>
<br/>
<table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
  <tbody id="tResultado">
    <caption class="fon_cab tit_cab">S&iacute;labo para el presente curso</caption>
    <%if (!silabos.isEmpty()) {%>
    <tr>
      <td align="center" width="30" class="moduloAbajo">&nbsp;</td>
      <td align="center" class="moduloAbajo"><strong>Descripci&oacute;n </strong></td>
      <td align="center" width="200" class="moduloAbajo"><strong>Creado</strong></td>
      <td align="center" width="200" class="moduloAbajo"><strong>Fichas Dependientes</strong></td>
      <td width="20" align="center" class="moduloAbajo">&nbsp;</td>
      <td width="20" align="center" class="moduloAbajo">&nbsp;</td>
      <td width="20" align="center" class="moduloAbajo1">&nbsp;</td>
    </tr>
    <%
	Iterator ite = silabos.iterator();
	boolean isSilaboCurrent = true;
	while (ite.hasNext()) {
	  Silabo silabo = (Silabo) ite.next();
		if(isSilaboCurrent){
		  
	%>
    <tr onmouseover="javascript:seleccionBlue(this,true);" onmouseout="javascript:seleccionBlue(this,false);" style="background-color:#9EC8F5;">
      <td align="center" class="bor_der_cur"><%
	}else{
	%>
    <tr onmouseover="javascript:seleccion(this,true);" onmouseout="javascript:seleccion(this,false);">
      <td align="center" class="bor_der_cur">
      <%}%>
          <img src="<%=request.getContextPath()%>/img/icon_paper.gif"  width="12" height="16"/> </td>
      <td class="bor_der_cur"><span id="silabo_nombre_<%=silabo.getIdSilabo()%>" class="textstatic"><%=silabo.getDescripcion()%></span>
      <%
      	if(isSilaboCurrent){
      	%>
      		<b><small>(Predeterminado)</small></b>
      	<%
      	}
       %>
      <br/>
          <div id="silabo_formMod_<%=silabo.getIdSilabo()%>" style="display: none; POSITION: absolute; WIDTH: 356px; background-color:#ffffff; 
				border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #90bada;
				border-left-width: 1px; border-left-style: solid; border-left-color: #90bada;
				border-right-width: 1px; border-right-style: solid; border-right-color: #90bada; 
				PADDING-LEFT: 5px; PADDING-RIGHT: 5px; PADDING-TOP: 5px; PADDING-BOTTOM: 5px;">
            <form onsubmit="modificarSilabo('<%=silabo.getIdSilabo()%>'); return false;">
              <input name="text" type="text" id="silabo_formMod_nombre_<%=silabo.getIdSilabo()%>" value="<%=silabo.getDescripcion()%>" class="form_text" size="40" maxlength="200" />
              <input name="submit" type="submit" class="form_button" value="Guardar" />
              <input name="button" type="button" class="form_button" onclick="javascript:ocultarModificarSilabo('<%=silabo.getIdSilabo()%>')" value="&nbsp;X&nbsp;" />
            </form>
          </div></td>
      <td class="bor_der_unid"><%=silabo.getFechaCreacionToString()%> <!-- por <%=silabo.getUsuarioCreacion()%> --></td>
      <td class="bor_der_unid"><%=silabo.getAulas().size() %> ficha(s) <!-- a>Ver Detalles</a--></td>
      <td align="center"><a onclick="verUnidadesSilabo('<%=silabo.getIdSilabo() %>')"><img src="<%=request.getContextPath()%>/img/icon_previo.gif" border="0" alt="Ver Unidades" width="17" height="14" style="cursor:pointer;"/></a> </td>
      <td align="center"><a onclick="mostrarModificarSilabo('<%=silabo.getIdSilabo()%>')"><img src="<%=request.getContextPath()%>/img/icon_trab.gif" border="0" alt="Modificar" style="cursor:pointer;"/></a> </td>
      <td align="center"><a onclick="eliminarSilabo('<%=silabo.getIdSilabo() %>','<%=silabo.getDescripcion() %>')"> <img src="<%=request.getContextPath()%>/img/icon_trash.gif" border="0" alt="Eliminar"  width="13" height="15" style="cursor:pointer;"/></a> </td>
    </tr>
    <%
   		 isSilaboCurrent = false;
			}
		} else {
	%>
    <tr>
      <td align="center" colspan="7"><b>El curso no tiene s&iacute;labos asignados.</b></td>
    </tr>
    <%
		}
			%>
  </tbody>
</table>
<%}else{%>
Acci&oacute;n no permitida.
<%}%>
