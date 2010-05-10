<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  page import="edu.opencampus.lms.util.Constante"%>
<%@  page import="edu.opencampus.lms.modelo.Usuario"%>
<%@  page import="edu.opencampus.lms.modelo.AulaVirtual"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%
	Usuario usuario = (Usuario) request.getSession().getAttribute(
	Constante.USUARIO_ACTUAL);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><s:text name="titulo.campus.virtual" /></title>
<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/aula_virtual.js"></script>

<script>
	var imagesDir = "<%=request.getContextPath()%>/js/openwysiwyg/icons/";
	var cssDir = "<%=request.getContextPath()%>/js/openwysiwyg/styles/";
	var popupsDir = "<%=request.getContextPath()%>/js/openwysiwyg/popups/";
</script>
<script language="JavaScript" type="text/javascript" src="<%=request.getContextPath()%>/js/openwysiwyg/wysiwyg.js">
</script>

</head>

<body>
<div id="pop_up">
		<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong>Campa&ntilde;a <c:out value="${param.busquedaPeriodo}"/></strong>						</td>
						<td width="5%"><a href="#" class="salir" onClick="window.print()"><s:text name="aula.alumno.pop_up.imprimir"/></a> </td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="2%">|</td>
						<td width="4%"><a href="#" class="salir" onClick="window.close()"><s:text name="aula.alumno.pop_up.cerrar"/></a></td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13"
									height="13" border="0" /> </a>						</td>
					</tr>
				</table>
		  </div>
  <div id="pop_cuerpo" style="width: 500px;">	
  	<c:out value="${mensaje_texto}"></c:out><c:out value="${param.mensaje_texto}"></c:out>
    <form id="form1" name="form1" method="post"  onSubmit="return enviarPublicacion('titulo','mensaje','1')" action="<%=request.getContextPath()%>/admin/ficha/PublicarMensaje.action">
      <table width="500" align="center" cellpadding="3" cellspacing="0" bgcolor="#FFFFFF" class="bor_tabla" border="0">
        <tr align="left" class="fon_cab">
          <td height="23" colspan="2" class="tit_cab"><s:text name="aula.alumno.pop_up.avisos.nuevo_aviso"/><br> </td>
        </tr>
        <tr>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr align="left">
          <td width="66" align="left" class="textstatic"><strong><s:text name="aula.alumno.pop_up.avisos.titulo"/></strong></td>
          <td width="420" align="left"><input name="titulo" id="titulo" type="text" class="form_text" size="70" value="<c:out value="${titulo}"/>" maxlength="255"/></td>
        </tr>
        <tr align="left">
          <td align="left" valign="top" class="textstatic"><strong><s:text name="aula.alumno.pop_up.avisos.aviso"/></strong></td>
          <td align="left" valign="top">
            <span class="links01">
            <textarea name="contenido" id="mensaje" cols="72" rows="5" class="form_text">
            </textarea>

<script language="javascript1.2">
  generate_wysiwyg('mensaje', '400', '150');
</script>
<input type="hidden" name="busquedaPeriodo" value="<c:out value="${param.busquedaPeriodo}"></c:out>">

</span></td>
        </tr>
        <tr align="left">
          <td colspan="2"><table width="100%">
            <tr>
              <td align="center">
                <input name="Submit2222" type="submit" class="form_button" value="<s:text name="aula.alumno.pop_up.avisos.boton.guardar"/>"               </td>
            </tr>
          </table></td>
        </tr>
      </table>
    </form>
  </div>  
  <div id="pie">&nbsp;</div>
</div>
</body>
</html>
