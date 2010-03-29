<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1">
<title><s:text name="titulo.campus.virtual" /></title>
<style>
.silabo_formMod {
	display: none;
	position: absolute;
	width: 402px;
	background-color:#ffffff;
	padding-left: 8px;
	margin: -15px 0px 0px 0px;
}
.ocultar {
	margin-top: 10px;
	margin-bottom: 10px;
	display: none;
}
</style>
<link href="<%=request.getContextPath()%>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/saludo.js"></script>
			<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/util.js"></script>
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<script language="javascript" type="text/javascript"
			src='<%=request.getContextPath()%>/admin/silabo/admin_silabo.js'></script>
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
		<script language="javascript" type="text/javascript" 
			src="<%=request.getContextPath()%>/js/fastinit.js"></script>
		<script language="javascript" type="text/javascript" 
			src="<%=request.getContextPath()%>/js/tablesort.js"></script>

</head>
<%	
	String idCurso = request.getParameter("idCurso");
	
	if(idCurso != null){
%>
<body onLoad="buscarCursoPorID('<%=idCurso %>','')">
<%
		}else{
		%>
<body>
<%
		}
	 %>
<div id="contenedor">
  <s:include value="/comun/bienvenida.jsp"></s:include>
  <!-- *************** CONTENIDO ********************* -->
  <div id="cuerpo">
    <div id="pop_cuerpo">
    <%
    	if(idCurso == null){
	%>
	<s:include value="/error_message.jsp"/>
	<div id="message"></div>
	  <form onSubmit="buscarCursos(); return false;">	
      <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
        <caption class="fon_cab tit_cab">Cursos</caption>	
        <tr>
          <td align="left" width=60 class="textstatic">
          	<strong>Jerarqu&iacute;a:</strong>          	
          </td>
          <td width="100">
			<select class="form_text" name="idJerarquia">
	         	<c:forEach items="${jerarquias}" var="j">
	         		<option value="<c:out value="${j.idJerarquia}"/>"><c:out value="${j.rutaCompleta}"/></option>
	         	</c:forEach>
	         </select> 	
		  </td>
         <td width="60" align="left" class="textstatic">
            <strong>Nombre:</strong> 
          </td>
          <td>
          <input name="text" type="text" class="form_text" id="nombre" value="" maxlength="100" size="32">
          <input name="submit" type="submit" class="form_button" value="Buscar"></td>
        </tr>
        <tr style="background-color: #7EAAD1">
            <td align="right" colspan="4">&nbsp; 
                <input type="button" value="Nuevo" id="btNuevo" onClick="mostrarNuevo(this);" class="form_button">
            </td>
          </tr>
      </table>
      </form>
      
      <!-- Nuevo-->
      <div id="unidad_nuevo" class="ocultar">
        <form method="POST" name="crear" onSubmit="return crearCurso(this)">
          <table width="100%" cellpadding="3" cellspacing="0" border="0" align="center" class="bor_tabla">
            <caption class="fon_cab tit_cab">Nuevo Curso</caption>
            <tr>
              <td width="60"><strong class="textstatic">Jerarquía:</strong></td>
              <td width="100">  
              	<select class="form_text" name="idJerarquia"> 
		         	<c:forEach items="${jerarquias}" var="j">
		         		<option value="<c:out value="${j.idJerarquia}"/>"><c:out value="${j.rutaCompleta}"/></option>
		         	</c:forEach>
		         </select> 	
              </td>
              <td width="60"><strong class="textstatic">Nombre:</strong></td>
              <td><input type="text" name="nombre" class="form_text" maxlength="100" size="32">
                <input type="submit" value="Guardar" class="form_button">
              </td>
            </tr>
          </table>
        </form>
      </div>
      <!-- FIN Nuevo -->
       <br/>
     <%
    	}
	%>
      <!-- Resultado de Busqueda Curso -->
      <div><div id="resultado_curso"> </div></div>
      <!-- FIN Resultado de Busqueda Curso -->
      <br/>
      <!-- FORM SILABO -->
      <div id="form_Silabo" style="display:none;">
        <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
          <caption class="fon_cab tit_cab">Crear Nuevo S&iacute;labo</caption>
          <tr>
            <td><form onSubmit="crearSilabo(); return false;">
                &nbsp;&nbsp;&nbsp;&nbsp;<b>Nombre:</b>&nbsp;&nbsp;
                <input type="text" id="form_Silabo_nombre" maxlength="200" class="form_text" size="60">
                &nbsp;&nbsp;
                <input type="submit" value="Crear s&iacute;labo" class="form_button">
                <input type="hidden" id="form_Silabo_idCurso" class="form_text" value="">
              </form></td>
          </tr>
        </table>
      </div>
      <!-- FIN FORM SILABO -->
      <!-- Resultado de Busqueda Silabo -->
      <div id="resultado_silabo" style="display:block;"></div>
      <!-- FIN Resultado de Busqueda Silabo -->
      <%	
		if(idCurso != null){
	  %>
      <br/><input name="button" type="button" class="form_button" onClick="javascript:document.location='<%=request.getContextPath()%>/admin/ficha/Nuevo.action';" value="Regresar a Fichas">
      <%}%>
    </div>
  </div>
  <div id="pie">
    <%@include file="../../comun/pie.jsp" %>
  </div>
  <!-- *************** FIN CONTENIDO ********************* -->
</div>

</body>
</html>
