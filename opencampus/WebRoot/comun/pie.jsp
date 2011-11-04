<%@page import="edu.opencampus.lms.util.Constante"%>
<%@page import="java.util.GregorianCalendar"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="pie">
	<div>
		Copyright &copy; <%=new GregorianCalendar().get(GregorianCalendar.YEAR) %> <a href="#" target="_blank"><s:text name="titulo.campus.virtual"/></a><br/>
		<s:text name="portal.pie.derechos"/>
	</div>
	<div class="right">
		<a href="#"><%=Constante.DIRECCION_CORREO_SALIENTE %></a><br/>
		<s:text name="portal.pie.requerimientos"/>
	</div>
</div>

