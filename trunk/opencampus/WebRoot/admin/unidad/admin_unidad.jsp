<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>
<s:text name="titulo.campus.virtual" />
</title>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jComponente.js"></script>
<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
<style>
.ocultar {
	margin-top: 10px;
	margin-bottom: 10px;
	display: none;
}
</style>
<script type="text/javascript" src='<%=request.getContextPath()%>/admin/unidad/admin_unidad.js'></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/saludo.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/util.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/admin/aulavirtual/aula_virtual.js"></script>

</head>
<!-- <body> -->
<!-- ***************************** ENCABEZADO PARA INCLUDE ***************************************** -->

<body>
<div id="contenedor">
  <s:include value="/comun/bienvenida.jsp"></s:include>
  <div id="cuerpo">
    <div id="pop_cuerpo">
      <!-- Busqueda -->
      <s:include value="/error_message.jsp"/>
      <form action="<%=request.getContextPath()%>/admin/unidad/Buscar.action" method="POST" name="buscar" 
      onSubmit="return validarBuscar(this)">
        <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" >  
          <caption class="fon_cab tit_cab">Unidades</caption>
          <tr>
          	<td align="left" width=60 class="textstatic">
          		<strong>Jerarqu&iacute;a:</strong>          	
          	</td>
          	<td width="100">
          		<select class="form_text" name="ruta">
		         	<c:forEach items="${jerarquias}" var="j">
		         		<option value="<c:out value="${j.idJerarquia}"/>" <c:if test="${sessionScope.UNIDAD_RUTA == j.idJerarquia}">selected</c:if>><c:out value="${j.rutaCompleta}"/></option>
		         	</c:forEach>
		         </select> 	
          	</td>
            <td width="60" align="left" class="textstatic">
            	<strong>Nombre:</strong> 
            </td>
            <td>
            	<input type="text" name="unidad" value="<c:out value="${sessionScope.UNIDAD_NOMBRE}"></c:out>" maxlength="100" class="form_text" size="32">
            	<input type="checkbox" name="exacto" value="1" <c:if test="${sessionScope.UNIDAD_EXACTO==1}">checked="checked"</c:if>>Exacto
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="submit" value="Buscar" class="form_button">
            </td>
            </tr>
            <tr style="background-color: #cccccc">
            <td align="right" colspan="4">&nbsp; 
                <input type="button" value="Nuevo" id="btNuevo" onClick="mostrarNuevo(this);" class="form_button">
            </td>
          </tr>
        </table>
      </form>
      <!-- FIN Busqueda -->
      <!-- Nuevo-->
      <div id="unidad_nuevo" class="ocultar">
        <form action="<%=request.getContextPath()%>/admin/unidad/Crear.action" method="POST" name="crear" onSubmit="return validacion(this)">
          <table width="100%" cellpadding="3" cellspacing="0" border="0" align="center" class="bor_tabla">
            <caption class="fon_cab tit_cab">Nueva Unidad</caption>
            <tr>
              <td width="60"><strong class="textstatic">Jerarquía:</strong></td>
              <td width="100">  
              	<select class="form_text" name="ruta"> 
		         	<c:forEach items="${jerarquias}" var="j">
		         		<option value="<c:out value="${j.idJerarquia}"/>"><c:out value="${j.rutaCompleta}"/></option>
		         	</c:forEach>
		         </select> 	
              </td>
              <td width="60"><strong class="textstatic">Nombre:</strong></td>
              <td><input type="text" name="unidad" class="form_text" maxlength="100" size="32">  
                <input type="submit" value="Guardar" class="form_button">
              </td>
            </tr>
          </table>
        </form>
      </div>
      <!-- FIN Nuevo -->
      <!-- Tabla Resultado -->
      <div id="resultado" style="margin-top:10px;">
        <c:if test="${unidades != null}">
        <table width="100%" align="center" cellpadding="0" cellspacing="0" border="0">
          <tr>
            <td valign="top">
			  <!-- Resultado de Busqueda -->
              <table width="100%" border="0" align="center"	 cellpadding="3" cellspacing="0" class="bor_tabla" style="table-layout: fixed;">
                <caption class="fon_cab tit_cab"> Resultado de la búsqueda
                    <input type="hidden" id="numFilas" value="<c:out value="${fn:length(unidades)}"/>"></caption>
                <c:choose>
					<c:when test="${fn:length(unidades)>0}">
	                <tr >
	                  <td width="40" align="center" class="moduloAbajo" ><b>Id</b> </td>
	                  <td class="moduloAbajo"><b>Nombre completo</b> </td>
	                  <td width="200" align="center" class="moduloAbajo"><b>Jerarqu&iacute;a</b> </td>
	                  <td width="50" align="center" class="moduloAbajo" colspan="2"><b>Evaluaci&oacute;n</b> </td>
	                  <td width="30" align="center" class="moduloAbajo"><b>Texto</b> </td>
	                  <td width="30" align="center" class="moduloAbajo"><b>Repaso</b> </td>
	                  <td width="30" align="center" class="moduloAbajo"><b>Lab.</b> </td>
	                  <td width="80" colspan="3" align="center" class="moduloAbajo1"><b>Acciones</b> </td>
	                </tr>
	                
	                
					<c:forEach items="${unidades}" var="u" varStatus="fila">
	                <tr onMouseOver="javascript:seleccion(this,true);" onMouseOut="javascript:seleccion(this,false);" style="cursor: pointer;">
	                  <td width="25" align="center" class="bor_der_cur" onClick="javascript:mostrar('row<c:out value="${fila.count}"/>');">
	                  	<input type="hidden" id="id_<c:out value="${fila.count}"/>" value="<c:out value="${u.idUnidad}"/>">
	                    <input type="hidden" id="nCompleto_<c:out value="${fila.count}"/>" value="<c:out value="${u.nombreCompleto}"/>">
	                    <input type="hidden" id="jerarquia_<c:out value="${fila.count}"/>" value="<c:out value="${u.jerarquia.idJerarquia}"/>">
	                  	<c:out value="${u.idUnidad}"/>
	                  </td>
	                  <td onClick="javascript:mostrar('row<c:out value="${fila.count}"/>');" class="bor_der_cur">
	                  	<c:out value="${u.nombreCompleto}"/>
	                  </td>
	                  <td onClick="javascript:mostrar('row<c:out value="${fila.count}"/>');" align="center" class="bor_der_cur">
	                  		<c:out value="${u.jerarquia.nombre}"/> 
					   </td>
	                  <td align="center">
						  <img src="<%=request.getContextPath()%>/img/icon_test.gif" onclick="javascript:abrirTest('<%=request.getContextPath()%>/aulavirtual/test/Listar.action?idUnidad=<c:out value="${u.idUnidad}"/>&unidad=<c:out value="${u.nombreCompleto}"/>')"/>
					  </td>
					  <td align="center" class="bor_der_cur">
						  (<c:out value="${u.cantidadTest}"/>)
					  </td>
	                  <td align="center" class="bor_der_cur">
						  <img src="<%=request.getContextPath()%>/img/icon_libro.gif" onclick="javascript:abrirTexto('<%=request.getContextPath()%>/admin/unidad/VerRecurso.action?ruta=1&id=<c:out value="${u.idUnidad}"/>');"/>
					  </td>
	                  <td align="center" class="bor_der_cur">
						  <img src="<%=request.getContextPath()%>/img/icon_curso.gif" onclick="javascript:abrirRepaso('<%=request.getContextPath()%>/admin/unidad/VerRecurso.action?ruta=3&id=<c:out value="${u.idUnidad}"/>');">
					  </td>
	                  <td align="center" class="bor_der_cur">
						  <img src="<%=request.getContextPath()%>/img/icon_lab.gif" onClick="javascript:abrirLaboratorioPdf('<%=request.getContextPath()%>/admin/unidad/VerRecurso.action?ruta=2&id=<c:out value="${u.idUnidad}"/>');"/>
					  </td>
					  <td align="center">
					  	<c:choose>
					  		<c:when test="${u.estado==1}">
					  			<img src="<%=request.getContextPath() %>/img/activo.gif" border="0" alt="Desactivar" style="cursor: pointer;"
					  			onclick="cambiarEstado(this,<c:out value="${u.idUnidad}"/>)" />
					  		</c:when>
					  		<c:otherwise>
					  			<img src="<%=request.getContextPath() %>/img/desactivo.gif" border="0" alt="Activar" style="cursor: pointer;"
					  			onclick="cambiarEstado(this,<c:out value="${u.idUnidad}"/>)"/>
					  		</c:otherwise>
					  	</c:choose>
					  	
					  </td>
	                  <td align="center"><a onClick="mostrarModificar('<c:out value="${fila.count}"/>');"> <img src="<%=request.getContextPath() %>/img/icon_trab.gif" border="0" alt="Modificar"/></a></td>
	                  <td align="center"><a href="<%=request.getContextPath()%>/admin/unidad/Eliminar.action?id=<c:out value="${u.idUnidad}"/>" onClick="return(eliminar('<c:out value="${u.nombreCompleto}"/>'));"> <img src="<%=request.getContextPath() %>/img/icon_trash.gif" border="0" width="13"  height="15" alt="Eliminar"/></a></td>
	                </tr> 
	                
					<!-- DETALLES DE UNIDAD -->
					<c:choose>
						<c:when test="${sessionScope.ULTIMA_UNIDAD != null && sessionScope.ULTIMA_UNIDAD == u.idUnidad}">
	                		<tr id="row<c:out value="${fila.count}"/>">
	                 		 <td colspan="11">
	                   </c:when>
	                   <c:otherwise>
							<tr id="row<c:out value="${fila.count}"/>" style="display:none">
	                    		<td colspan="11">
	                   </c:otherwise>
	                </c:choose>
	                        <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" bgcolor="#FFFFFF" class="bor_tabla" style="table-layout: fixed;">
							  <caption class="fon_cab tit_cab">Detalles de la Unidad : <c:out value="${u.nombreCompleto}"/></caption>
	                          <tr>
	                            <td width="105" height="18" align="right" class="texto" style="padding-right: 10px;"><b>Jerarquía: </b></td>
	                            <td bgcolor="#E5EFF8"  class="texto"><c:out value="${u.jerarquia.rutaCompleta}"/> </td>
	                          </tr>
	                          <!-- tr onclick="verFichas('<c:out value="${fila.count}"/>')" onMouseOver="javascript:seleccion(this,true);" onMouseOut="javascript:seleccion(this,false);" style="cursor: pointer;">
	                          	<td height="18" align="right" class="texto1" style="padding-right: 10px;">
	                          		<strong>Cursos:</strong>
	                          	</td>
	                          	<td colspan="3">
	                          		Ver los cursos donde esta unidad se est&aacute; ejecutando o fue ejecutado
	                          	</td>
	                          </tr>
	                          <tr>
	                          	<td colspan="4">
	                          		<div id="div<c:out value="${fila.count}"/>">
	                      		
	                      			</div>
	                          	</td>
	                          </tr-->
	
	                      </table>
	                      <!-- FIN DETALLES DE UNIDAD -->
						</td>
	                  </tr>
					  <tr id="modificar<c:out value="${fila.count}"/>" style="display:none">
	                    <td colspan="11">
						<!-- Modificar UNIDAD -->
	                      <div id="unidad_mod_<c:out value="${fila.count}"/>"> </div>
	                    <!-- FIN Modificar UNIDAD -->
						</td>
	                  </tr>
	                  </c:forEach>
				  </c:when>
					<c:otherwise>
					    <tr>
					      <td align="center" colspan="11"><b>Su b&uacute;squeda - <c:out value="${sessionScope.CLAVE_BUSCAR}"/> - no produjo ning&uacute;n resultado.</b></td>
	                  </tr> 
					</c:otherwise>
				</c:choose>
              </table>
              <!-- FIN Resultado de Busqueda -->
            </td>
          </tr>
        </table>
		</c:if>
		<!-- FIN Tabla Resultado -->
        <!-- Modificar -->
        <div id="unidad_modificar" class="ocultar">
          <form action="<%=request.getContextPath()%>/admin/unidad/Modificar.action" method="POST" name="modificar" onSubmit="return validacion(this)">
            <table width="100%" cellpadding="3" cellspacing="0" border="0" align="center" class="bor_tabla">
            <caption class="fon_cab tit_cab">Modificar Unidad: <span id="unidad_title"></span></caption>
              <tr>
                <td width="60">
                	<strong class="textstatic">Jerarquía:</strong>
                </td>
                <td width="100">
					<select class="form_text" name="ruta"> 
		         	<c:forEach items="${jerarquias}" var="j">
		         		<option value="<c:out value="${j.idJerarquia}"/>"><c:out value="${j.rutaCompleta}"/></option>
		         	</c:forEach>
		         </select> 	
				</td>
                <td width="60">
                	<strong class="textstatic">Nombre: </strong>
                </td>
                <td width="100">
                	<input type="text" name="unidad" class="form_text" maxlength="100" size="32">  
                	<input type="hidden" name="id" value="">
                </td>
                <td><input type="submit" value="Guardar" class="form_button">&nbsp; &nbsp;
                  <!-- input type="button" value="Cerrar" class="form_button" onClick="cerrarModificar();"-->
                </td>
              </tr>
            </table>
          </form>
        </div>
        <!-- FIN Modificar -->
      </div>
      <!-- ULTIMAS UNIDADES -->
      
      <table width="100%" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" style="margin-top:10px; table-layout: fixed;">
        <caption class="fon_cab tit_cab">&Uacute;ltimas Unidades</caption>
        <c:choose>
			<c:when test="${fn:length(ultimas)>0}">
				<tr>
		          <td width="40" align="center" class="moduloAbajo"><b>&nbsp;</b></td>
		          <td align="center" class="moduloAbajo"><b>Nombre Completo</b></td>
		          <td width="200" align="center" class="moduloAbajo"><b>Jerarqu&iacute;a</b></td>
		          <td width="130" align="center" class="moduloAbajo"><b>Fecha de creaci&oacute;n:</b></td>
		          <td width="40" align="center" class="moduloAbajo1" colspan="2"><b>&nbsp;</b></td>
		        </tr>
				<c:forEach items="${ultimas}" var="u" varStatus="fila">
			        <tr onMouseOver="javascript:seleccion(this,true);" onMouseOut="javascript:seleccion(this,false);" style="cursor: pointer;" >
			          <td class="bor_der_cur"><c:out value="${u.idUnidad}"></c:out> </td>
			          <td class="bor_der_cur"><c:out value="${u.nombreCompleto}"></c:out> </td>
			          <td align="center" class="bor_der_cur"><c:out value="${u.jerarquia.nombre}"/> </td>
			          <td align="center" class="bor_der_cur"><c:out value="${u.fechaCreacionToString}"/></td>
			          <td align="center"><a href="<%=request.getContextPath()%>/admin/unidad/Buscar.action?unidad=<c:out value="${u.nombreCompleto}"></c:out>&ruta=<c:out value="${u.jerarquia.idJerarquia}"/>&exacto=1"> <img src="<%=request.getContextPath() %>/img/icon_previo.gif" border="0" width="17" height="14" alt="Buscar"/></a></td>
			          <td align="center"><a href="<%=request.getContextPath()%>/admin/unidad/Eliminar.action?id=<c:out value="${u.idUnidad}"/>" onClick="return(eliminar('<c:out value='<c:out value="${u.nombreCompleto}">'/>'));"> <img src="<%=request.getContextPath() %>/img/icon_trash.gif" border="0" width="13" height="15" alt="Eliminar"/></a></td>
			        </tr>
		        </c:forEach>
        	</c:when>
        	<c:otherwise>
        <tr>
          <td align="center" colspan="6"><b><i>¡Incre&iacute;ble! no se encontraron unidades en la Base de Datos.</i></b></td>
        </tr>
        	</c:otherwise>
        </c:choose>
      </table>
      <!-- FIN ULTIMAS UNIDADES -->
    </div>
  </div>
  <div id="pie">
    <%@include file="../../comun/pie.jsp" %>
  </div>
</div>
</body>
</html> 
