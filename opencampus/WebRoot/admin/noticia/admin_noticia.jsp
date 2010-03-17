<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="../../error_action.jsp" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="/WEB-INF/FormatoTags"%>
<%@ taglib prefix="ct" uri="/WEB-INF/CampusTags"%>
<%@  page import="edu.tecsup.lms.util.Constante"%>
<%@  page import="edu.tecsup.lms.modelo.Usuario"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:set var="contextPath" value='${pageContext.request.contextPath}'/>

<%
Usuario usuario = (Usuario)request.getSession().getAttribute(Constante.USUARIO_ACTUAL);
 %>
 
<title><s:text name="titulo.campus.virtual" /></title>

<link href="<c:out value='${contextPath}'/>/estilos/estilos.css"
			rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/saludo.js"></script>
<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jComponente.js"></script>
<script language="javascript" type="text/javascript"
			src="<%=request.getContextPath()%>/js/jPrototype.js"></script>
<script language="javascript" type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/InnerDiv.js"></script>
<script language="javascript" type="text/javascript"
			src='<c:out value='${contextPath}'/>/admin/noticia/admin_noticia.js'></script>
<script language="javascript" type="text/javascript" 
			src="<%=request.getContextPath()%>/js/util.js"></script>
<link rel="stylesheet" type="text/css"
			href="<c:out value='${contextPath}'/>/js/jscalendar/calendar-style.css" >
<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar.js"></script>
<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-es.js"></script>
<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/jscalendar/calendar-setup.js"></script>

<script type="text/javascript">
		var imagesDir = "<c:out value='${contextPath}'/>/js/openwysiwyg/icons/";
		var cssDir = "<c:out value='${contextPath}'/>/js/openwysiwyg/styles/";
		var popupsDir = "<c:out value='${contextPath}'/>/js/openwysiwyg/popups/";
</script>
<script type="text/javascript"
			src="<c:out value='${contextPath}'/>/js/openwysiwyg/wysiwyg.js"></script>

</head>
<%@include file="/comun/capas/reloj.jsp" %>

<body onload="mostrarReloj()">

<div id="contenedor">
  <s:include value="/comun/bienvenida.jsp"></s:include>
  
  <!-- *************** CONTENIDO ********************* -->
  <div id="loading" style="display: none;">*****   demo   *****</div>
  <div id="cuerpo">
    <div id="pop_cuerpo">

		<form>
			<table width="950" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla" style="border-bottom: 0px;">
		        <tr class="fon_cab">
		          <td colspan="2" class="tit_cab">Publicar noticia</td>
		        </tr>
		        <tr class="fon_color03" height="23">
		            <td align="left" class="moduloAbajo1" style="padding-left: 10px;">
		            	<input type="button" value="Nueva noticia" class="form_button" onClick="mostrarNuevo();"/>
		            <td align="right" class="moduloAbajo1">
						<input id="close_form_nuevo" type="button" value="X" class="form_button"  style="width: 24px; display: none;" onClick="xHideD('form_nuevo'); xHideD('close_form_nuevo');"/>
					</td>
		        </tr>
			</table>
		</form>
		
		<div id="form_nuevo">
		
		    <div id="divMod">
				<form id="form_mod_noticia" onSubmit="return crearnoticia(this)" method="post" enctype="multipart/form-data" action="<c:out value='${contextPath}'/>/admin/noticia/Crear.action">	
					<table width="950" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
				        <tr height="32">
				          <td width="75" style="padding-left: 10px;">
				          	<strong>Titular :</strong>
				          	<input type="hidden" name="idnoticia" value="">
				          </td>
				          <td width="320">
				          	<input name="titular" type="text" class="form_text" value="" maxlength="100" size="54">
				          </td>
				          <td width="100">
				          	<strong>Fecha de pub.:</strong>
				          </td>
				          <td width="155">
				          	<input type="text" size="22" class="form_text" readonly name="fecha" id="fecha"/>
				          </td>
				          <td width="70" style="padding-left: 8px;" class="tabla01_border_left tabla01_border_bottom">
				          	<strong>Secci&oacute;n:</strong>
				          </td>
				          <td width="230" class="tabla01_border_bottom">
					         <select id="sc_newSeccion" name="idseccion" class="form_text" style="width: 150px;">
					         	<option value="0">Elija una sección</option>
					         	<c:forEach items="${secciones}" var="seccion">
					         		<option value="<c:out value="${seccion.idSeccion}"/>"><c:out value="${seccion.nombre}"/></option>
					         	</c:forEach>
					         </select>
					         &nbsp; <input type="button" value="Editar" style="cursor: pointer;" class="form_button"
					         onclick="INNERDIV.newInnerDiv('pUp_Seccion',(xClientWidth()-300)/2,(xClientHeight()-400)/2,310,400,'<c:out value='${contextPath}'/>/admin/noticia/seccion/Cargar.action','Gestor de secciones')" />
				          </td>
				        </tr>
				        
				        <tr>
				          <td rowspan="6" style="padding-left: 10px;" valign="top">
				          	<strong>Cuerpo :</strong>
				          </td>
				          <td rowspan="6" colspan="3" valign="top" style="padding-bottom: 10px;" >
				          	<textarea rows="5" cols="58" name="contenido" id="contenido"></textarea>
				          </td>
				         <td style="padding-left: 8px;" class="tabla01_border_left">
				          	<strong>Imagen:</strong>
				          </td>
				          <td>
					         <input type="file" name="imagen" class="form_text">
				          </td>
				        </tr>
				        
				        <tr>
				          <td style="padding-left: 8px;" class="tabla01_border_left tabla01_border_bottom">
				          	<strong>Formato:</strong>
				          </td>
				          <td class="tabla01_border_bottom">
				          	 <table width="100%" border="0" cellpadding="0" cellspacing="0">
				          	 	<tr>
				          	 		<td>
										<input type="radio" name="formato" value="0" checked="checked"> 
									</td>
									<td>
										<img src="<c:out value='${contextPath}'/>/img/noti_centrado.jpg" alt="Centrado">
									</td>
									<td>
										<input type="radio" name="formato" value="1"> 
									</td>
									<td>
										<img src="<c:out value='${contextPath}'/>/img/noti_cuadro.jpg" alt="Cuadro">
									</td>
				          	 	</tr>
				          	 </table>
				          	 
				          </td>
				        </tr>
				        
				        <tr>
				          <td style="padding-left: 5px; background-color: #F9F9F9;" colspan="2" class="tabla01_border_left" align="center">
				          	<strong>Dirigido a:</strong>
				          </td>
				        </tr>
				        
				        <tr>
				          <td style="padding-left: 5px;" colspan="2" class="tabla01_border_left">
				          	<table width="100%" border="0" cellpadding="0" cellspacing="2" style="table-layout: fixed;">
				          		<tr>
				          			<td width="70"><b>Jerarqu&iacute;a:</b></td>
				          			<td>&nbsp;</td>
				          		</tr>
				          		<tr>
				          			<td colspan="2">
							          	<select class="form_text" id="s_jerarquia" style="width:264px;">
								         	<option value="0">Todos</option>
								         	<c:forEach items="${jerarquias}" var="j">
								         		<option value="<c:out value="${j.idJerarquia}"/>"><c:out value="${j.rutaCompleta}"/></option>
								         	</c:forEach>
								         </select>
				          			</td>
				          		</tr>
				          		<tr>
				          			<td><b>Periodo:</b></td>
				          			<td>
							          	<select class="form_text" id="s_periodo" style="width:192px;">
								         	<option value="0">Todos</option>
								         	<c:forEach items="${periodos}" var="p">
								         		<option value="<c:out value="${p.idPeriodo}"/>"><c:out value="${p.nombre}"/></option>
								         	</c:forEach>
								         </select>
								    </td>
								</tr>
								<tr>
				          			<td><b>Rol:</b></td>
				          			<td>
							          	<select class="form_text" id="s_rol" style="width:192px;">
								         	<option value="0">Todos</option>
								         	<c:forEach items="${roles}" var="r">
								         		<option value="<c:out value="${r.idrol}"/>"><c:out value="${r.nombre}"/></option>
								         	</c:forEach>
								         </select>
								    </td>
								</tr>
					         </table>
				          </td>
				        </tr>
				        
				        <tr>
				          <td style="padding-left: 5px;" colspan="2" class="tabla01_border_left">
				          	<input type="button" value="Agregar Regla" class="form_button" onclick="agregarRegla()"  style="width: 100%;"/>
						  </td>
				        </tr>
				        
				        <tr>
				          <td style="padding-left: 5px;" colspan="2" class="tabla01_border_left">
				          	<select size="5" id="SelectGrupos" multiple="multiple" style="width: 100%;" ondblclick="quitarRegla()">
							</select>
							<input type="hidden" name="reglas" value="">
				          </td>
				        </tr>
				        
				        <tr>
				          <td style="padding-left: 10px;" valign="top">
				          	<strong>Sumilla :</strong>
				          </td>
				          <td colspan="3" valign="top" style="padding-bottom: 10px;">
				          	<input name="sumilla" type="text" class="form_text" value="" maxlength="100" size="106">
				          </td> 
				          <td valign="top" style="padding-left: 5px;" colspan="2" class="tabla01_border_left">
				          	<input type="button" value="Quitar Regla" class="form_button" onclick="quitarRegla()"  style="width: 100%;"/>
						  </td>
				        </tr>
				        
				        <tr>
				        	<td colspan="6" align="center" class="tabla01_border_top" height="30">
								<input type="submit" value="Guardar" class="form_button" style="width: 100px;">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="reset" value="Cancelar" class="form_button" style="width: 100px;" onClick="cerrarNuevo()">
							</td>
				        </tr>
				        
				        <!-- tr class="fon_color03">
				          <td colspan="7" class="tabla03_bordeColumna" style="color: #90bada;"><strong>Recursos multimedia</strong></td>
				        </tr>
				        
				        <tr>
				          <td colspan="7"><strong>Recursos multimedia</strong></td>
				        </tr-->
		
				    </table>
				</form>
			</div>
			
		</div>
   
   		<script language="JavaScript" type="text/javascript">
			Calendar.setup({inputField:"fecha", ifFormat:"%d-%m-%Y", singleClick:true});
			generate_wysiwyg('contenido','560','230'); xHideD('form_nuevo');
		</script>
		
		
		<br/>
		
		
		<table width="950" border="0" align="center" cellpadding="3" cellspacing="0" class="tabla01" style="table-layout: fixed;">
	        <caption style="width:  950px;">
	          Noticias
	        </caption>
	        <tr class="tabla01_subEncabezado">
	          <td align="center" class="moduloAbajo" width="20"></td>
	          <td align="center" class="moduloAbajo" width="600"><strong>Titular</strong></td>
	          <td align="center" class="moduloAbajo" width="120"><strong>Secci&oacute;n</strong></td>
	          <td align="center" class="moduloAbajo" width="100"><strong>Fecha</strong></td>
	          <td align="center" class="moduloAbajo" width="80" colspan="3"><strong>Tareas</strong></td>
	        </tr>
	        
	        <c:forEach items="${noticias}" var="noticia" varStatus="fila">
	        	
	        	<c:choose>
	        		<c:when test="${fila.count%2==0}">
	        			<c:set var="filaStyle" value="tabla01_fila2"/>
	        		</c:when>
	        		<c:otherwise>
	        			<c:set var="filaStyle" value="tabla01_fila1"/>
	        		</c:otherwise>
	        	</c:choose>
	        	
		        <tr class="<c:out value="${filaStyle}"/>">
		          <td align="center" class="bor_der_cur">
		          	<c:choose>
		          		<c:when test="${noticia.estado == 1}">
		          			<img src="<%=request.getContextPath()%>/img/activo.gif" style="cursor: pointer;" onclick="cambiarEstado(this,'<c:out value="${noticia.idnoticia}"/>')"/>
		          		</c:when>
		          		<c:otherwise>
		          			<img src="<%=request.getContextPath()%>/img/desactivo.gif" style="cursor: pointer;" onclick="cambiarEstado(this,'<c:out value="${noticia.idnoticia}"/>')" />
		          		</c:otherwise>
		          	</c:choose>
		          	<input type="hidden" id="estado_<c:out value="${noticia.idnoticia}"/>" value="<c:out value="${noticia.estado}"/>">
		          </td>
		          <td style="padding-left: 6px;" class="bor_der_cur">
		          	<c:out value="${noticia.titular}"/>
		          </td>
		          <td align="center" class="bor_der_cur">
		          	<c:out value="${noticia.seccion.nombre}"/>
		          </td>
		          <td align="center" class="bor_der_cur">
		          	<f:DateToString fecha="${noticia.fecha}" />
		          </td>
		          <td align="center" class="bor_der_cur">
		          	<img src="<%=request.getContextPath() %>/img/icon_previo.gif" border="0" alt="Mostrar" style="cursor: pointer;"
		          	onclick="verNoticia('<c:out value="${noticia.idnoticia}"/>')"/>
		          </td>
		          <td align="center" class="bor_der_cur">
		          	<img src="<%=request.getContextPath() %>/img/icon_trab.gif" border="0" alt="Modificar" style="cursor: pointer;"
		          	onclick="mostrarModificar('<c:out value="${noticia.idnoticia}"/>')"/>
		          </td>
		          <td align="center">
		          	<img src="<%=request.getContextPath() %>/img/icon_trash.gif" border="0" alt="Eliminar" style="cursor: pointer;"
		          	 onClick="eliminarNoticia('<c:out value="${noticia.idnoticia}"/>')"/>
		          </td>
		        </tr>
		        
		        <tr id="TR_<c:out value="${noticia.idnoticia}"/>" style="display: none;">
		        	<td colspan="7" align="center">
		        		<div id="DIV_<c:out value="${noticia.idnoticia}"/>">
		        		<c:if test="${fila.count==1}">
		        			<!--  img src="<%=request.getContextPath() %>/img/cargando_logo.gif" border="0" -->
		        		</c:if>
		        		</div>
		        	</td>
		        </tr>
		        
	         </c:forEach>
	         
	      </table>
   
    </div>
  </div>
  <div id="pie">
    <%@include file="../../comun/pie.jsp" %>
  </div>
  
  <!-- *************** FIN CONTENIDO ********************* -->
  
</div>
</body>
</html>
