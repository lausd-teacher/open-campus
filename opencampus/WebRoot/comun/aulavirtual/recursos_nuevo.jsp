<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="titulo.campus.virtual" /></title>
<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/js/aula_virtual.js"></script>
</head>

<body>
<div id="pop_up">
		<div id="pop_up" style="width: 520px;">
			<div id="prin_01" style="width: 520px;"> 
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="93%">
							<strong><s:text name="aula.alumno.pop_up.curso"/> <c:out value="${usuario_actual.aulaActual.curso.nombre}"></c:out> </strong></td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.print()"><s:text name="aula.alumno.pop_up.imprimir"/></a></td>
						<td width="3%"><a href="#" class="salir" onClick="window.print()"><img
									src="<%=request.getContextPath()%>/img/impresora.gif" width="13" height="13" border="0" /></a></td>
						<td width="2%">|</td>
						<td width="4%"><a href="#" class="salir" onClick="window.close()"><s:text name="aula.alumno.pop_up.cerrar"/></a></td>
						<td width="3%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13" height="13" border="0" /> </a></td>
					</tr>
				</table>
		  </div>
  <div id="pop_cuerpo" style="width: 500px;">
    <form name="form1" onSubmit="return enviarPublicacion('titulo','doc','2')" action="<%=request.getContextPath()%>/aulavirtual/PublicarRecurso.action" enctype="multipart/form-data" method="post" >
      <table width="500" align="center" cellpadding="3" cellspacing="0" bgcolor="#FFFFFF" class="bor_tabla">
        <tr align="left" class="fon_cab">
          <td height="23" colspan="2" class="tit_cab"><s:text name="aula.alumno.lecturas.nueva_lectura"/></td>
        </tr>
        <tr>
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr align="left">
          <td width="66" align="left" class="textstatic"><strong><s:text name="aula.alumno.lecturas.titulo"/></strong></td>
          <td width="420" align="left"><input name="titulo" id="titulo" type="text" class="form_text" size="73" maxlength="255"/></td>
        </tr>
        <tr align="left">
          <td align="left" class="textstatic"><strong><s:text name="aula.alumno.lecturas.archivo"/></strong></td>
          <td align="left" valign="top">
          
<input id="doc" name="doc" type="file" class="form_button" size="55"/>
        
              <span style="color:#FF0000"><strong> (*) </strong></span></td>
        </tr>
        <tr align="left">
          <td colspan="2" class="links01"><p>&nbsp;</p>
              <strong><span style="color:#FF0000">(*)</span><s:text name="aula.alumno.lecturas.nota"/></strong>
              <br /></td>
        </tr>
        <tr align="left">
          <td colspan="2" align="center"><input name="Submit222" type="button" class="form_button" value="<s:text name="aula.alumno.lecturas.boton.regresar"/>" 
          	onClick="document.location.href='<%=request.getContextPath()%>/aulavirtual/Recursos.action'"/>
            &nbsp;&nbsp;&nbsp;
            <input type="submit" class="form_button" value="<s:text name="aula.alumno.lecturas.boton.enviar"/>" /></td>
        </tr>
      </table>
    </form>
  </div>  
  <div id="pie">&nbsp;</div>
</div>
</body>
</html>
