<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String contexto = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=ISO-8859-1" />

<script>
	var imagesDir = "<%=contexto%>/js/openwysiwyg/icons/";
	var cssDir = "<%=contexto%>/js/openwysiwyg/styles/";
	var popupsDir = "<%=contexto%>/js/openwysiwyg/popups/";
</script>
	<link href="<%=request.getContextPath()%>/comun/buzon/buzonCSS.css"
		rel="stylesheet" type="text/css" />
<script type="text/javascript"	src="<%=contexto%>/js/openwysiwyg/wysiwyg.js"></script>
<script	type="text/javascript" src="<%=contexto%>/comun/buzon/buzonJS.js"></script>
<script type="text/javascript" src="<%=contexto%>/js/jComponente.js"></script>
<script	type="text/javascript" src="<%=contexto%>/js/util.js"></script>
<script language="javascript">
/* ---------------------------------------------------------------------- *\
  ENVIAR MENSAJE
\* ---------------------------------------------------------------------- */
	function enviarMensaje() {
	if (trim(xGetElementById("destino").value) == "") {
		alert("Ingresar destino");
		xGetElementById("destino").focus();
		return false;
	} else {
		if (trim(xGetElementById("titulo").value) == "") {
			if (confirm("\xbfDesea enviar el mensaje sin t\xedtulo?")) {
				xGetElementById("enviar").disabled = true;
				return true;
			} else {
				xGetElementById("titulo").focus();
				return false;
			}
		}
	}
	xGetElementById("enviar").disabled = true;
}
</script>
<style type="text/css">
body {
	text-align: center;
	vertical-align: middle;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;	
	margin-right: 0px;
	margin-left: 0px;
}
td {
	font-family: Tahoma;
	font-size: 11px;
}
</style>

</head>
<body>
<form	enctype="multipart/form-data"  onSubmit="return enviarMensaje()" action="<%=contexto%>/comun/buzon/EnviarMensajeM.action" method="post">

<table width="56%" border="0" cellpadding="7" cellspacing="0">
  <tr>
    <td><table width="51%" border="0" cellpadding="0" cellspacing="0" class="bor_tabla">
      <tr class="xxx">
        <td height="20" class="tit_cab">Mensaje</td>
      </tr>
      <tr>
        <td>
        
        <table width="480" border="0" bgcolor="#E5EFF8" class="thc">
            <tr>
              <td width="89">
                Para:
              </td>
              <td colspan="2" align="left">
                <input type="text" name="nombres" id="nombres" size="50" value="<c:out value="${nombres}"/>" readonly/>
                 </td>
            </tr>
            
  
            <tr>
              <td>Asunto:</td>
              <td	colspan="2" align="left">
                <input name="titulo" type="text" id="titulo" size="50" maxlength="100" value="<c:out value="${titulo}" escapeXml="true"/>">
              </td>
            </tr>
            
            <tr>
				<td>
					Adjunto:
				</td>
				<td	colspan="2" align="left">
					<input name="doc" type="file" id="doc" size="40">
					&nbsp;M&aacute;ximo 5 MiB.
				</td>
			</tr>

            <tr>
              <td colspan="3" style="background-color: white">
                <textarea	name="contenido" id="contenido" type="_moz">
        <br/>
        </textarea>
                <script	language="javascript1.2">			
			generate_wysiwyg('contenido', '480', '150');			
		</script>
              </td>
            </tr>
            <tr>
              <td colspan="3"><table width=" " border="0">
                  <tr>
                    <td width="200">
                    </td>
                    <td width="200"></td>
                    <td	align="" width="30"><input class="form_button" type="submit"	name="Submit" id="enviar" value="Enviar"></td>
                  </tr>
              </table></td>
            </tr>
        </table>
        
        </td>
      </tr>
      
    </table></td>
  </tr>
</table>
<input type="text" name="cc" id="cc" size="50" style="visibility: hidden" />
<input type="hidden" name="destino" id="destino" size="50" value="<c:out value="${destino}"/>"/>

</form>
</body>
</html>
