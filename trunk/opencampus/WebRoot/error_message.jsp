<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<s:if test="hasActionErrors()">
	<div class="actionErrorCSS">
		<span>Error encontrado:</span>
		<div><s:actionerror/></div>
	</div>
	<br/>
</s:if>
<s:if test="hasFieldErrors()">
	<div class="actionErrorCSS">
		<span>Error encontrado:</span>
		<div><s:fielderror/></div>
	</div>
	<br/>
</s:if>
<s:if test="hasActionMessages()">
	<div class="actionMessageCSS">
		<span>Informaci&oacute;n del sistema:</span>
		<div><s:actionmessage/></div>
	</div>
	<br/>
</s:if>
<c:if test="${message != null}">
	<div class="actionMessageCSS">
		<span>Informaci&oacute;n del sistema:</span>
		<div><c:out value="${message}"></c:out></div>
	</div>
	<br/>
</c:if>


<%/*
Changing default style of <s:actionerror/> / <s:actionmessage/> tag
 
In struts 2 when you put <s:actionerror /> tag it displays the errors in the following way:


<ul>
<li>error 1</li>
<li>error 2</li>
</ul>


But sometimes it seems to be very ugly when displaying the dot (.) in the action errors or action messages

Below is a normal code that displays the tags in your customized way.
You can specify your own css for this


<s:if test="hasActionErrors()">
<s:iterator value="actionErrors">
<span class="errorMessage"><s:property escape="false" />
</span>
</s:iterator>
</s:if>
Alternatively you can change the file in the "/template/simple/actionerror.ftl" and put it in the /web-directory/struts/simple if using simple theme

Similar for <s:actionmessage /> tag.

Enjoy Struts 
*/%>