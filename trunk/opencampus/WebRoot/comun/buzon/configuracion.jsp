<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>
<s:text name="titulo.campus.virtual" />
</title>
<link href="<%=request.getContextPath()%>/estilos/estilos.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
			src='<%=request.getContextPath()%>/comun/buzon/buzonJS.js'></script>
<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/jComponente.js'></script>
<script type="text/javascript"
			src='<%=request.getContextPath()%>/js/util.js'></script>
</head>

<body>


<div id="pop_up" style="width: 510px;">

<div id="prin_01" style="width: 510px;"> 
				<table width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr>
						<td width="90%">
							<strong>Configuraci&oacute;n de Carpetas</strong>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()">Cerrar</a>
						</td>
						<td width="5%">
							<a href="#" class="salir" onClick="window.close()"> <img
									src="<%=request.getContextPath()%>/img/salir_x.gif" width="13" height="13" border="0" /> </a>
						</td>
					</tr>
				</table>
			</div>


<div id="pop_cuerpo" style="width: 500px;">
<table width="490" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
          <tr class="fon_cab">
            <td colspan="4" class="tit_cab">Crear carpeta</td>
          </tr>
                              
          <tr>
            <td align="left" class="textstatic"></td>
            <td height="10" colspan="2"></td>
            <td></td>
          </tr>
          
          <tr>
            <td align="left" class="textstatic"><strong>Nombre:</strong> </td>
            <td><input type="text" id="plt" value="" maxlength="17" class="form_text" size="50">
                <input onClick="javascript:crearCarpeta(plt)" type="submit" value="Crear Carpeta" class="form_button"  style="width: 100px;"></td>
            <td>
            </td>            
          </tr>
          <tr>
            <td align="left" class="textstatic"></td>
            <td height="10"></td>
            <td></td>
            <td align="right"></td>
          </tr>
        </table>
        
        <div id="resultado" style="margin-top:10px;">
        
        <table width="490" border="0" align="center" cellpadding="3" cellspacing="0" class="bor_tabla">
          <tr class="fon_cab">
            <td colspan="4" class="tit_cab">Carpetas personales</td>
          </tr>
                              
          <tr>
            <td align="left" class="textstatic"></td>
            <td height="10" colspan="2"></td>            
          </tr>
          <%int i=0; %>
          <c:forEach items="${CARPETAS}" var="carpeta">          
          <tr id="<c:out value="${carpeta.idCarpeta}"/>"  <%if(i%2==0){%> class="fon_color01" <%} %>>
            <td align="left" class="textstatic"><img src="<%=request.getContextPath()%>/img/carpeta.gif">
	            <strong>
	            	<span id="c<c:out value="${carpeta.idCarpeta}"/>"><c:out value="${carpeta.nombre}"/></span>
	            </strong>
            </td>
            <td align="right">
            <input type="button" class="form_button" onClick="renombrar('<c:out value="${carpeta.idCarpeta}"/>')" value="Renombrar">
            <input type="button" class="form_button" onClick="return elimimarCarpeta('<c:out value="${carpeta.idCarpeta}"/>')" value="Eliminar">
            </td>                       
          </tr>
          <%i++; %>          
          </c:forEach>
          <tr>
            <td align="left" class="textstatic"></td>
            <td height="10"></td>            
          </tr>
        </table>
        </div>
</div>
</div>
</body>

</html>
