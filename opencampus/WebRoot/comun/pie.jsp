<%@page import="edu.tecsup.lms.util.Constante"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="pie">
	<div>
		Copyright &copy; 2010 <a href="#"><s:text name="titulo.campus.virtual"/></a><br/>
		<s:text name="portal.pie.derechos"/>
	</div>
	<div class="right">
		<a href="#"><%=Constante.DIRECCION_CORREO_SALIENTE %></a><br/>
		<s:text name="portal.pie.requerimientos"/>
	</div>
</div>

