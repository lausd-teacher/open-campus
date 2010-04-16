<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true" %>
<% response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Error de programa. "+exception.getMessage());%>