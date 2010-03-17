<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:set var="contextPath" value='${pageContext.request.contextPath}'/>

<title>Sección</title>

</head>

<body>

<div style="background-color: #FFFFFF; min-height: 94%; padding: 10px;">
	
	<table width="100%" border="0" align="center" cellpadding="4" cellspacing="0" class="tabla01">
		<tr>
			<td>
				<form method="post" onsubmit="crearSeccion(this); return false;" >
					<strong>Nuevo: </strong> &nbsp;
					<input name="nombre" type="text" class="form_text" maxlength="20" size="22">
					<input type="submit" value="Guardar" class="form_button">
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%" border="0" align="center" cellpadding="2" cellspacing="0" class="tabla01" style="table-layout: fixed;">
					<caption>
						Lista de Secciones
					</caption>
					
					<c:set var="size" value="${fn:length(secciones)}"></c:set>
					<c:forEach items="${secciones}" var="seccion" varStatus="fila">
					
						<tr onMouseOver="javascript:seleccion(this,true);" onMouseOut="javascript:seleccion(this,false);" height="24">
							<td width="20" align="center">
								<c:out value="${fila.count}"/>
							</td>
							<td>
								<span id="nombre_<c:out value="${fila.count}"/>" style="padding-left: 2px;"><c:out value="${seccion.nombre}"/></span>
								<input id="nombre_mod_<c:out value="${fila.count}"/>" type="text" class="form_text" maxlength="20" size="20" style="display: none;"
								onblur="modificarSeccion('<c:out value="${fila.count}"/>',this,'<c:out value="${seccion.idSeccion}"/>')">
							</td>
							<td width="20" align="center">
								<img src="<%=request.getContextPath() %>/img/redactar.gif" border="0" style="cursor: pointer;" alt="Renombrar"
								onclick="abrirModificarSeccion('<c:out value="${fila.count}"/>')" id="nombre_btn_<c:out value="${fila.count}"/>"/>
							</td>
							<td width="20" align="center">
								<c:choose>
									<c:when test="${fila.count==1}">
										 <img src="<%=request.getContextPath()%>/img/up_off.gif"/>
									</c:when>
									<c:otherwise>
										<img src="<%=request.getContextPath()%>/img/up.gif" border="0" style="cursor: pointer;" alt="Subir"
										onclick="reordenar('up','<c:out value="${seccion.idSeccion}"/>')"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="20" align="center">
								<c:choose>
									<c:when test="${fila.count==size}">
										 <img src="<%=request.getContextPath()%>/img/down_off.gif"/>
									</c:when>
									<c:otherwise>
										<img src="<%=request.getContextPath()%>/img/down.gif" border="0" style="cursor: pointer;" alt="Bajar"
										onclick="reordenar('dw','<c:out value="${seccion.idSeccion}"/>')"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="20" align="center">
								<img src="<%=request.getContextPath() %>/img/icon_trash.gif" border="0" style="cursor: pointer;" alt="Eliminar"
								onclick="eliminarSeccion('<c:out value="${seccion.idSeccion}"/>','<c:out value="${seccion.nombre}"/>')"/>
							</td>
						</tr>
					
					</c:forEach>
					
				</table>
			</td>
		</tr>
		<tr>
			<td align="center">
				<input type="button" onclick="INNERDIV.cerrar('pUp_Seccion')" value="Cerrar"
					class="form_button" style="width: 100%;">
			</td>
		</tr>
	</table>
	
</div>

</body>
</html>
