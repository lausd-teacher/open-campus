<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%String contexto = request.getContextPath();%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
  <link href="<%=contexto%>/comun/buzon/buzonCSS.css" rel="stylesheet" type="text/css"/>
  <script	type="text/javascript" src="<%=contexto%>/comun/buzon/buzonJS.js"></script>
	<script type="text/javascript" src="<%=contexto%>/js/jComponente.js"></script>
	<script type="text/javascript" src="<%=contexto%>/js/util.js"></script>
	<script type="text/javascript" src="<%=contexto%>/dwr/util.js"></script>
  <title><s:text name="titulo.campus.virtual" /></title>
  <link href="/comun/buzon/buzonCSS.css" rel="stylesheet" type="text/css">
  <style type="text/css">
<!--
.style1 {font-size: 12px}
-->
  </style>
</head>
  <script>
	function init(){
		var main = document.getElementById('aB');
	    main.focus();
    }
  </script>
  <body leftmargin="0" onLoad="init();">
  <table width="39%" border="0" align="left" cellpadding="7" cellspacing="0">
    <tr>
      <td width="513"><table width="200" border="0" cellpadding="0" cellspacing="0" class="bor_tabla">
        <tr>
          <td><table width="468" border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td colspan="5" align="center" valign="middle">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                      <tr class="fon_tit_buzon">
                        <td height="20" colspan="3" class="tit_cab">Directorio</td>
                      </tr>
                      <tr>
                        <td width="435"  style="padding-left:5px; padding-top:7px;" valign="middle">
                          <span class="style1">Buscar:  </span>        
                          <input  name="aBuscar" type="text" id="aB" size="70" onkeypress="dwr.util.onReturn(event,buscarContacto)">
                        </td>
                        <td width="98" valign="middle" align="left" style="padding-left:5px; padding-top:5px; vertical-align:baseline">
						  <input onClick="buscarContacto()" type="button" class="form_button" name="button2" id="button2" value="Buscar">
						</td>
                        <td width="46" valign="middle" align="left"></td>
                      </tr>
					  <tr><td height="5"></td></tr>
                    </table>
                </td>
              </tr>
              <tr>
                <td width="12" rowspan="2">&nbsp;</td>
                <td width="114" rowspan="2" >
                    <select name="select" multiple="multiple" class="lista" id="matches" >
                    </select>
                </td>
                <td width="70" rowspan="2" valign="middle"><table width="67" border="0">
                    <tr>
                      <td width="57" height="30"  align="center" valign="left">
                        <input onClick="toWhere(to)" class="form_button" type="button" name="para" id="para" value="Para &gt;&gt;">
                     
                      </td>
                    </tr>
                    <tr>
                      <td height="30" valign="middle" align="left">
                        <input onClick="eliToWhere()" class="form_button" type="button" name="less" id="less" value="Eliminar">
                   
                      </td>
                    </tr>
                    <tr>
                      <td height="30" valign="middle" align="left">
                        <input class="form_button" onClick="toWhere(ccList)" type="button" name="cc" id="cc" value="Cc &gt;&gt;">
                      </td>
                    </tr>
                </table></td>
                <td width="268" style="border:0px;">
                    <select name="select" multiple="multiple" class="listas" id="to">
                    </select>
                </td>
                <td width="4" height="54" style="border:0px;">&nbsp;</td>
              </tr>
              <tr>
                <td style="border:0px;">
                    <select name="select" multiple="multiple" class="listas" id="ccList">
                    </select>
                </td>
                <td style="border:0px;">&nbsp;</td>
              </tr>
              <tr>
                <td height="40" colspan="5" align="center" valign="middle"><table width="200" border="0">
                    <tr>
                      <td align="center" valign="middle">
                        <input type="button" onClick="window.close()" class="form_button" name="button3" id="button3" value="Cancelar">
                      </td>
                      <td align="center" valign="middle">
                        <input onClick="pasalo()" type="button" class="form_button" name="button2" id="button2" value="Aceptar">
                      </td>
                    </tr>
                </table></td>
              </tr>
          </table></td>
        </tr>
      </table></td>
    </tr>
  </table>
  </body>
</html>
