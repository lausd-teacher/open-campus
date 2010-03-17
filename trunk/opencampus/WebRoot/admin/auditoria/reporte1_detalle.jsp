<%@ page import="java.sql.*" %>
<%@ page import="java.util.*"  %>
<%@ page import="java.text.*"  %>
<%@ page import="java.util.Date"  %>
<%@ page import="java.lang.Math"  %>
<%@ page language="java" %>


<%
response.setDateHeader("Expires",-1);
response.setHeader("Pragma","no-cache");
if(request.getProtocol().equals("HTTP/1.1"))
	response.setHeader("Cache-Control","no-cache");
%>
<%
	Connection con=null;
	java.sql.Statement stmt=null;
	ResultSet rs=null;
		
	int nrounidad=0;
	int nrotrabajo=0;	
	
//datos recibidos
	String cod = "";
	String codgrupo = "";
	String codcurso = "";
	String codperiodo = "";
	String codsyllabus = "";
	String codficha = "";
	String fec_ini = "";
	String fec_fin = "";
	String opc = "";
	String paso = "";

//datos
	String grupo = "";
	String curso = "";
	String periodo = "";
	String nombre = "";
	String fec_actual = "";
	String hor_actual = "";
	String per_ini = "";
	String per_fin = "";
	String nro_ing = "";
	String contenido = "";
	String contenido2 = "";	
	String contenido3 = "";		
	
	int dia_act;
	int mes_act;
	int anio_act;
	ArrayList lista = new ArrayList();				
		
	
    if (con == null || con.isClosed()) {
		try {
        	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        	con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.72.12:1521:bdtecsup","campus","virtual");
		} catch(SQLException e1) {
			System.out.println(e1.getMessage());

		}
	}
%>	
<html>

<HEAD>
<%  String sWS=request.getContextPath(); %>
<link rel="stylesheet" type="text/css" href="<%=sWS%>/css/tecsup.css"> 
       

</HEAD>

<BODY>

<%
	//recibiendo datos
	cod   = request.getParameter("iduser");
	codgrupo = request.getParameter("grp");
	codcurso = request.getParameter("idcurso");
	codperiodo = request.getParameter("idperiodo");
	codsyllabus = request.getParameter("idsyllabus");
	codficha   = request.getParameter("idficha");
	fec_ini = request.getParameter("fecini");
	fec_fin = request.getParameter("fecfin");
	String tiprep = request.getParameter("tiprep");
	
	
	if(request.getParameter("opc")!=null) {
		opc = request.getParameter("opc");
	}
	
    String datets = (new java.sql.Timestamp(System.currentTimeMillis())).toString();
    fec_actual= datets.substring(0,10);            
    hor_actual= datets.substring(11,19);            
	dia_act = Integer.parseInt(datets.substring(8,10));
	mes_act = Integer.parseInt(datets.substring(5,7));
	anio_act = Integer.parseInt(datets.substring(0,4));

if ((fec_ini.equals("")) || (fec_fin.equals(""))) { 
	fec_ini = "--------";
	fec_fin = "--------";
 }

if(codgrupo.equals("002")){ grupo="Consultor"; } else { grupo="Estudiante"; }	

%>
<div id="MAIN">
<!--  titulo -->
  <div id="title">
    <div id="titleText">Auditoria - <%=grupo%></div>
  </div>
<!--  /titulo -->
<%  
	//realizando consultas
	System.out.println("ssss");
 	stmt=con.createStatement();
	String sqlusu = "select idusuario,paterno,materno,nombres " +
				    "from datos_personales where idusuario = '"+ cod +"'";
	rs = stmt.executeQuery(sqlusu);
	
	if(rs.next()) { nombre = rs.getString(4) + " " + rs.getString(2) + " " + rs.getString(3); }
	stmt.close();

 	stmt=con.createStatement();
	String sqlcur = "select idcurso,nombre " +
				    "from curso where idcurso = '"+ codcurso +"'";
	rs = stmt.executeQuery(sqlcur);
	if(rs.next()) { curso = rs.getString(2); }
	stmt.close();

 	stmt=con.createStatement();
	String sqlper = "select fecha_inicio,fecha_fin " +
				    "from periodo where idperiodo = '"+ codperiodo +"'";
	rs = stmt.executeQuery(sqlper);
	if(rs.next()) { 
	per_ini = (rs.getString(1)).substring(0,10);	per_fin = (rs.getString(2)).substring(0,10);
	 }
	stmt.close();


 	stmt=con.createStatement();
	String sqling = "select count(*) from log_ingresos " +
	                " where idusuario='" + cod + "' "; 
 
 if (!(fec_ini.equals("--------")) && !(fec_fin.equals("--------")) && !(fec_ini.equals("")) && !(fec_fin.equals(""))) { 
	              sqling += " and fecha_ingreso>=to_date('" + fec_ini + "','YYYY-MM-DD') and fecha_ingreso<=to_date('" + fec_fin + "','YYYY-MM-DD') ";
 }
 	              sqling += " and idficha='" + codficha + "' ";				    

	rs = stmt.executeQuery(sqling);
	if(rs.next()) { nro_ing = (rs.getString(1)); }
	stmt.close();
	
%>

<% contenido="<table cellspacing=0 class=tb3><tr><td height=6></td></tr> "+
  " <tr> "+
  " <th>"+grupo+"</th> "+
  " <td colspan=4>"+nombre+"</td> "+
  " <td width=164 rowspan=6><img src=ver_foto.jsp?iduser="+cod+" width=110 height=130></td>"+
  "</tr>"+
  "<tr> "+
  "  <th>Curso</th>"+
  "  <td colspan=4>"+curso+"</td>"+
  "</tr>"+
  "<tr> "+
  "  <th>Reporte</th>"+
  "  <td width=40>Fecha</td>"+
  "  <td>"+fec_actual+"</td>"+
  "  <td width=40>Hora</td>"+
  "  <td>"+hor_actual+"</td>"+
  "</tr>"+
  "<tr> "+
  "  <th>Curso</th>"+
  "  <td>Inicio</td>"+
  "  <td>"+per_ini+"</td>"+
  "  <td>Fin</td>"+
  "  <td>"+per_fin+"</td>"+
  "</tr> ";
  
 if (!(fec_ini.equals("--------")) && !(fec_fin.equals("--------")) && !(fec_ini.equals("")) && !(fec_fin.equals(""))) {  
  contenido+= "<tr> "+
    "<th>Intervalo</th>"+
    "<td>Inicio</td>"+
    "<td>"+fec_ini+"</td>"+
    "<td>Fin</td>"+
    "<td>"+fec_fin+"</td>"+
  "</tr>";
 } 
  contenido+= "<tr> "+
  " <th>Nro de Ingresos</th>"+
  "  <td colspan=4>"+nro_ing+"</td>"+
  "</tr>"+
  "<tr><td height=6 colspan=6></td></tr>"+
  "</table><hr size=1>";
%>
<%=contenido%>      
<% 
 	stmt=con.createStatement();
	String sqluni = "select COUNT(*) " +
				    "from unidad_syllabus where idsyllabus = '"+ codsyllabus +"'";
	rs = stmt.executeQuery(sqluni);
	if(rs.next()) { nrounidad = Integer.parseInt(rs.getString(1)); }
	stmt.close();
	String[] codunidad = new String[nrounidad];	
	int tablesize = 80+(nrounidad*20);
	int ntmin=0;
	int ntmax=0;
	int ntpro=0;
	int ntvec=0;	
	int cont=0;
		
 	stmt=con.createStatement();
	String sqlcun = "select idunidad " +
				    "from unidad_syllabus where idsyllabus = '"+ codsyllabus +"' order by indice";
	rs = stmt.executeQuery(sqlcun);
	while(rs.next()) { codunidad[cont] = rs.getString(1); cont++; }
	stmt.close();
%>

<table cellspacing="0" width="100%">
<TR>
<TD valign="top">
<% 
 if (codgrupo.equals("003") || codgrupo.equals("010") ) {       
int nounmu =nrounidad+1;
      contenido2+="<table border=0 cellspacing=1 class=tb1>"+
	  "<tr>"+
      "<th colspan="+nounmu+">Evaluaciones</th>"+
	  "</tr>"+
      "<tr>"+
      "   <th>Unidad</th>";
          	 int i;
          	for (i=1;i<nrounidad+1;i++) {           	
          	
				contenido2+="<th width=20> "+i+" </th>";
			 } 

        contenido2+="</tr><tr bgcolor=#FFFFFF><td>Min</td>";
          int x;
          	for (x=0;x<nrounidad;x++) { 
			contenido2+="<td width=20 align=center>";
			
        	if(codunidad[x]==null) { contenido2+="--"; } else {
 	stmt=con.createStatement();
	String sqlmin = "select MIN(nota) " +
				    "from nota_test where idficha = '"+ codficha +"' " +
				    "and idusuario = '"+ cod +"' and idunidad='" + codunidad[x] + "' ";
	rs = stmt.executeQuery(sqlmin);
	if(rs.next()) { ntmin = rs.getInt(1); }
	stmt.close(); 	
	contenido2+=ntmin;
				} 	
				contenido2+="</td>";
			 } 
		contenido2+="</tr><tr bgcolor=#FFFFFF><td>Max</td>";
          	int y;
          	for (y=0;y<nrounidad;y++) { 
			contenido2+="<td width=20 align=center>";
		  	if(codunidad[y]==null) { contenido2+="--"; } else {
 	stmt=con.createStatement();
	String sqlmax = "select MAX(nota) " +
				    "from nota_test where idficha = '"+ codficha +"' " +
				    "and idusuario = '"+ cod +"' and idunidad='" + codunidad[y] + "' ";
	rs = stmt.executeQuery(sqlmax);
	if(rs.next()) { ntmax = rs.getInt(1); }
	stmt.close(); 				 
	contenido2+=ntmax;
				} 
				contenido2+="</td>";
			 } 
		contenido2+="</tr><tr bgcolor=#FFFFFF><td>Prom</td>";
		
          int w;
          	for (w=0;w<nrounidad;w++) { 
			contenido2+="<td width=20 align=center>";
		  	if(codunidad[w]==null) { contenido2+="--"; } else {
 	stmt=con.createStatement();
	String sqlavg = "select round(AVG(nota)) " +
				    "from nota_test where idficha = '"+ codficha +"' " +
				    "and idusuario = '"+ cod +"' and idunidad='" + codunidad[w] + "' ";
	rs = stmt.executeQuery(sqlavg);
	if(rs.next()) { ntpro = rs.getInt(1); }
	stmt.close(); 				 
	contenido2+=ntpro;
				} 
				contenido2+="</td>";
			 } 
		contenido2+="</tr><tr bgcolor=#FFFFFF><td>Veces</td>";
          	 int z;
          	for (z=0;z<nrounidad;z++) { 
			contenido2+="<td width=20 align=center>";
		  	if(codunidad[z]==null) { contenido2+="--"; } else {
 	stmt=con.createStatement();
	String sqlvec = "select COUNT(nota) " +
				    "from nota_test where idficha = '"+ codficha +"' " +
				    "and idusuario = '"+ cod +"' and idunidad='" + codunidad[z] + "' ";
	rs = stmt.executeQuery(sqlvec);
	if(rs.next()) { ntvec = rs.getInt(1); }
	stmt.close(); 				 
	contenido2+=ntvec;
				} 
			contenido2+="</td>";
			 } 
	    contenido2+="</tr></table><br>";
%>      
<%=contenido2%>      
<%
 	stmt=con.createStatement();
	String sqltrac = "select COUNT(*) from trabajo where idsyllabus = '"+ codsyllabus +"'";
	rs = stmt.executeQuery(sqltrac);
	if(rs.next()) { nrotrabajo = Integer.parseInt(rs.getString(1)); }
	stmt.close();

 contenido3+="<table cellspacing=1 class=tb1> "+
              "<tr>" +
            "<th>Trabajo</th>" +
            "<th width=40>Nota</th>" +
            "<th width=60>Estado</th>" +
           "</tr>";
 
  if (nrotrabajo>0) {	

	String estrab="";
	String cetrab="";	
	int itr=0;
		
 	stmt=con.createStatement();
	String sqltru = " select a.idtrabajo,b.nombre,a.nota,a.estado from nota_trabajo a,trabajo b "+
                    "where a.idtrabajo=b.idtrabajo and a.idsyllabus=b.idsyllabus "+
                    "and a.idsyllabus = '"+ codsyllabus +"' "+
                    "and a.idusuario='" + cod + "' "+
                    "and a.idficha = '"+ codficha +"' "+                    
                    "order by a.idtrabajo";
	rs = stmt.executeQuery(sqltru);
	while(rs.next()) { 

contenido3+=" <tr bgcolor=#FFFFFF >"+
            "<td>" + rs.getString(2) +"</td>"+ 
            "<td>";
            
             if (rs.getInt(3)==-1) { contenido3+="SN"; } else { contenido3+=""+rs.getInt(3); } 
            
contenido3+="</td><td>";
            cetrab=rs.getString(4); 
            if (cetrab.equals("A")) { estrab="Aprobado"; }
            if (cetrab.equals("D")) { estrab="Desaprobado"; }
            if (cetrab.equals("N")) { estrab="No Entregado"; }
            if (cetrab.equals("P")) { estrab="Pendiente"; }
            if (cetrab.equals("R")) { estrab="Revisado"; }
            if (cetrab.equals("S")) { estrab="Enviado"; } 
contenido3+=""+estrab +"</td></tr>";

 itr++; }
	stmt.close();

} else {          
contenido3+="<tr bgcolor=#FFFFFF><td colspan=3>No hay Trabajos Asignados</td></tr>"; 
 } 
contenido3+="</table>";
    
 } else { 

contenido3+="<table width=300 cellspacing=1 class=tb1>"+
          "<tr>"+         
          "<td>Unidad</td>";
          
          	 int i;
          	for (i=1;i<nrounidad+1;i++) {           	
			contenido3+="<td width=30 align=center>&nbsp;"+i+"&nbsp;</td>";
			 }
			 
        contenido3+="</tr>"+        
        "<tr bgcolor=#FFFFFF>"+ 
        "<td>Tests</td>";
          	
          	 int x;
          	for (x=0;x<nrounidad;x++) { 
			contenido3+="<td align=center>";
		  	
		  	if(codunidad[x]==null) { contenido3+="--"; } else {
 	stmt=con.createStatement();
	String sqltes = "select COUNT(*) " +
				    "from test where idunidad='" + codunidad[x] + "' and estado='1' "; 
 if (!(fec_ini.equals("--------")) && !(fec_fin.equals("--------")) && !(fec_ini.equals("")) && !(fec_fin.equals(""))) { 
	              sqltes += " and fecha>=to_date('" + fec_ini + "','YYYY-MM-DD') and fecha<=to_date('" + fec_fin + "','YYYY-MM-DD') ";
 }		    
	rs = stmt.executeQuery(sqltes);
	if(rs.next()) { ntmin = rs.getInt(1); }
	stmt.close(); 	
	contenido3+=""+ntmin;
				} 
				contenido3+="</td>";
			 } 
		contenido3+="</tr>" +
        "<tr bgcolor=#FFFFFF>"+ 
        "<td>Debates</td>";
          	
          	 int y;
          	for (y=0;y<nrounidad;y++) { 
			contenido3+="<td align=center>";
			
		  	if(codunidad[y]==null) { contenido3+="--"; } else {
 	stmt=con.createStatement();
	String sqldeb = "select COUNT(*) " +
				    "from ficha_unidad_debate a,mensaje b where a.idficha = '"+ codficha +"' " +
				    "and a.idmensaje=b.idmensaje and b.idusuario = '"+ cod +"' and a.idunidad='" + codunidad[y] + "' ";
 if (!(fec_ini.equals("--------")) && !(fec_fin.equals("--------")) && !(fec_ini.equals("")) && !(fec_fin.equals(""))) { 
	      sqldeb += " and b.fecha>=to_date('" + fec_ini + "','YYYY-MM-DD') and b.fecha<=to_date('" + fec_fin + "','YYYY-MM-DD') ";
 }		    

	rs = stmt.executeQuery(sqldeb);
	if(rs.next()) { ntmax = rs.getInt(1); }
	stmt.close(); 				 
	contenido3+=""+ntmax;
				} 
			contenido3+="</td>";
			 } 
		contenido3+="</tr></table>";
 } %>
<%=contenido3%> 
</TD>

<% if (codgrupo.equals("003") || codgrupo.equals("010") ) { %> 
<form action="<%=sWS%>/core/messagemanager.do" name="reportu" method="post">
<input type="hidden" name="nextaction" value="messageviewinsert">
<input type="hidden" name="destinatary" value="<%=cod%>">
<input type="hidden" name="title" value="Reporte de Curso <%=curso%>">
<input type="hidden" name="content" value="<P>&lt;-- Reporte del Curso: <%=curso%> -- <%=fec_actual%> --&gt;</P><center><%=contenido%><%=contenido2%><%=contenido3%></center>">
<% } %>

<TD width="150" valign="top" align="right">
<p><div id="button"><a href="reporte1_detalle.jsp?iduser=<%=cod%>&grp=<%=codgrupo%>&idcurso=<%=codcurso%>&idperiodo=<%=codperiodo%>&idsyllabus=<%=codsyllabus%>&idficha=<%=codficha%>&fecini=<%=fec_ini%>&fecfin=<%=fec_fin%>&opc=H" >Historico</a></div></p>
<p><div id="button"><a href="reporte1_detalle.jsp?iduser=<%=cod%>&grp=<%=codgrupo%>&idcurso=<%=codcurso%>&idperiodo=<%=codperiodo%>&idsyllabus=<%=codsyllabus%>&idficha=<%=codficha%>&fecini=<%=fec_ini%>&fecfin=<%=fec_fin%>&opc=C" >Calendario</a></div></p>
<p><div id="button"><a href="javascript:void(0);" onClick="javascript:history.back();">Regresar</a></div></p>

<% if (codgrupo.equals("003") || codgrupo.equals("010") ) { %> 
<p><div id="button"><a href="javascript:void(0);" onClick="document.forms['reportu'].submit();return false;">Enviar</a></div></p>
<% } %>

<% if (codgrupo.equals("003")|| codgrupo.equals("010") ) { %> 
</form>
<% } %>
</TD></TR>

</TABLE>


<% if(opc.equals("H")) { %>
<hr size="1">
<table cellspacing="0" class="tb1">
	  <tr> 
      <th colspan="4" align="center">Historial</th>
	  </tr>
      <tr class="data_title">         
		<th width="30"></th>
		<th>Fecha</th>
		<th>IP</th>
		<th>Browser</th>
	 </tr>		
<% 	stmt=con.createStatement();
	String sqlhis = "select to_char(fecha_ingreso,'YYYY-MM-DD HH24:MI:SS'),ip_remoto,agente from log_ingresos " +
	                " where idusuario='" + cod + "' "; 
 if (!(fec_ini.equals("--------")) && !(fec_fin.equals("--------")) && !(fec_ini.equals("")) && !(fec_fin.equals(""))) { 
	              sqlhis += " and fecha_ingreso>=to_date('" + fec_ini + "','YYYY-MM-DD') and fecha_ingreso<=to_date('" + fec_fin + "','YYYY-MM-DD') ";
 }
 	              sqlhis += " and idficha='" + codficha + "' order by to_char(fecha_ingreso,'YYYY-MM-DD HH24:MI:SS') ";				    

	rs = stmt.executeQuery(sqlhis);
	int filah=0;
	String estilo="data_even";
	while(rs.next()) {
	filah++;
    estilo="data_even"; if((filah % 2)==0) estilo="data_add"; 	
%>		 
<tr class="<%=estilo%>">         
		<td align="center"><%=filah%></td>
		<td><%=rs.getString(1)%></td>
		<td><%=rs.getString(2)%></td>
		<td><%=rs.getString(3)%></td>
</tr>		

<%	}
stmt.close(); %>
      </table> 
<% } %>

<% if(opc.equals("C")) { %>
<hr size="1">
<% 

	String sqlficha="";
	stmt=con.createStatement();
	sqlficha = "select to_char(fecha_ingreso,'YYYY-MM-DD'),count(*) from log_ingresos " +
	           " where idusuario='" + cod + "' " + 
    	       " and idficha='"+ codficha + "' ";	
 if (!(fec_ini.equals("--------")) && !(fec_fin.equals("--------")) && !(fec_ini.equals("")) && !(fec_fin.equals(""))) { 
	      sqlficha += " and fecha_ingreso>=to_date('" + fec_ini + "','YYYY-MM-DD') and fecha_ingreso<=to_date('" + fec_fin + "','YYYY-MM-DD') ";
 }
	      sqlficha += " group by to_char(fecha_ingreso,'YYYY-MM-DD') ";
 
	rs = stmt.executeQuery(sqlficha);
	while(rs.next()) {
	lista.add(rs.getString(1));
    }
    stmt.close();
    
    String ini_dia=""; 	int ds;	int ndia=0;	String ncal="01"; String nomes ="";	
	int nweek;	int c=0;	int xx=0;	int s=0;	int dom=0;	int nrom=0;	int lex=0;
	int imes=0;	int fmes=0;	String mcal="";	String sqlcal; int colcal=0; int nimes=0; int nfmes=0;
	
	//colors
	String vacio="#BCD2EE";
	String vacio2="#FFFFFF";
	String claro="#FFFFFF";
	String activo="#00CCFF";
	String percolor="#CCFFFF";
	
	int dia_iper = Integer.parseInt(per_ini.substring(8,10));
	int mes_iper = Integer.parseInt(per_ini.substring(5,7));
	int anio_iper = Integer.parseInt(per_ini.substring(0,4));
	int dia_fper = Integer.parseInt(per_fin.substring(8,10));
	int mes_fper = Integer.parseInt(per_fin.substring(5,7));
	int anio_fper = Integer.parseInt(per_fin.substring(0,4));
	
    Date date_act;    Date date_temp;    SimpleDateFormat convertemp;
	Date date_pini;	Date date_pfin; String bgtemp;
	
	convertemp = new SimpleDateFormat("yyyy-MM-dd"); 
	date_act= convertemp.parse(anio_act+"-"+mes_act+"-"+dia_act);
	dia_iper--;	dia_fper++;	
	date_pini= convertemp.parse(anio_iper+"-"+mes_iper+"-"+dia_iper);
	date_pfin= convertemp.parse(anio_fper+"-"+mes_fper+"-"+dia_fper);

	Calendar calendar;	String ini_mes="";
	Date fecha;			SimpleDateFormat convertDate;
	
	if (anio_iper==anio_fper) {
		nrom= mes_fper-mes_iper;
		imes=mes_iper;	fmes=mes_fper;
	} else {
		nrom= (12-mes_iper)+mes_fper;
		imes=mes_iper;	fmes=12;
		nimes=1;	nfmes=mes_fper;
	}
%>	
<table class="body_table" cellspacing="5" cellpadding="0">
<%	
	for (xx=imes;xx<fmes+1;xx++) {

	ndia=0;	
 	calendar = new GregorianCalendar(anio_iper,xx,01);
    if(xx<10) { mcal="0"+xx; } else { mcal=""+xx; } 	
    ini_mes = anio_iper + "-" + mcal + "-01 " + hor_actual; 
	convertDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	fecha = convertDate.parse(ini_mes); 
 	calendar.setTime(fecha);
 	ds=calendar.get(Calendar.DAY_OF_WEEK);
%>		 

<% if (colcal==0) { %><TR><% } colcal++; %>

<TD align="center" valign="top">
<table border="0" cellspacing="1" bgcolor="#BCD2EE" width="120">
<% 	
	switch(xx){
	case 1: dom=31; nomes="Enero"; break;
	case 2: dom=28; nomes="Febrero"; break;
	case 3: dom=31; nomes="Marzo"; break;
	case 4: dom=30; nomes="Abril"; break;
	case 5: dom=31; nomes="Mayo"; break;
	case 6: dom=30; nomes="Junio"; break;
	case 7: dom=31; nomes="Julio"; break;
	case 8: dom=31; nomes="Agosto"; break;
	case 9: dom=30; nomes="Setiembre"; break;
	case 10: dom=31; nomes="Octubre"; break;
	case 11: dom=30; nomes="Noviembre"; break;
	case 12: dom=31; nomes="Diciembre"; break;	
	}	
%>
<tr bgcolor="<%=vacio%>">
<th colspan="7" align="center"><b><%=nomes%></b></th>
</tr>
<tr align="center" bgcolor="<%=vacio2%>">
<td>D</td><td>L</td><td>M</td><td>M</td><td>J</td><td>V</td><td>S</td>
</tr>
<%	
//	nweek=(int)Math.floor((ds+dom)/7);
//	nweek++;
	nweek=6;
	
for (s=1;s<nweek+1;s++) { %>
<tr>
 <% for (c=1;c<8;c++) { ndia++; %>

 	<% if (s==1 && c<ds) { ndia=0; %>
		<td bgcolor="<%=vacio2%>">&nbsp;</td>
 	<% } else { %> 		
	 	<% if (ndia>dom) { %>
			<td bgcolor="<%=vacio2%>">&nbsp;</td>
		<% } else { %>
<td align="right" bordercolor="<%=vacio2%>" <%	
    if(ndia<10) { ncal="0"+ndia; } else { ncal=""+ndia; }
    ini_dia = ini_mes.substring(0,8) + ncal; 
	date_temp = convertemp.parse(ini_dia); 	
	paso="0";

	Iterator ite1 = lista.iterator();
	while(ite1.hasNext()){
		String lexdia = (String)ite1.next();	
		if(ini_dia.equals(lexdia)) { paso="1"; }
	}


	if(paso.equals("1")) {
		bgtemp=activo;
	} else {
		if (date_temp.after(date_pini) &&  date_temp.before(date_pfin)) {
			bgtemp=percolor;
		} else {
			bgtemp=claro;	
		}
	}
    out.print(" bgcolor="+bgtemp);		
	 %> ><%=ndia%></td> 	
 	
 		<% } %> 	
 	<% } %> 	

 <% } %>
</tr>
<% } %>
<tr bgcolor="<%=vacio%>">
<td colspan="7" align="center"><b><%=anio_iper%></b></td>
</tr>

<% // stmt.close(); %>
      </table></TD> 
<% if (colcal==4) { colcal=0; %></TR><% } %>
<% } %>

</table>


<% if (anio_iper!=anio_fper) { %>

<table class="body_table" cellspacing="5" cellpadding="0">
<%	
	for (xx=nimes;xx<nfmes+1;xx++) {

	ndia=0;	
 	calendar = new GregorianCalendar(anio_fper,xx,01);
    if(xx<10) { mcal="0"+xx; } else { mcal=""+xx; } 	
    ini_mes = anio_fper + "-" + mcal + "-01 " + hor_actual; 
	convertDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	fecha = convertDate.parse(ini_mes); 
 	calendar.setTime(fecha);
 	ds=calendar.get(Calendar.DAY_OF_WEEK);
%>		 

<% if (colcal==0) { %><TR><% } colcal++; %>

<TD align="center" valign="top">
<table border="0" cellspacing="1" bgcolor="#666666" width="120">
<% 	
	switch(xx){
	case 1: dom=31; nomes="Enero"; break;
	case 2: dom=28; nomes="Febrero"; break;
	case 3: dom=31; nomes="Marzo"; break;
	case 4: dom=30; nomes="Abril"; break;
	case 5: dom=31; nomes="Mayo"; break;
	case 6: dom=30; nomes="Junio"; break;
	case 7: dom=31; nomes="Julio"; break;
	case 8: dom=31; nomes="Agosto"; break;
	case 9: dom=30; nomes="Setiembre"; break;
	case 10: dom=31; nomes="Octubre"; break;
	case 11: dom=30; nomes="Noviembre"; break;
	case 12: dom=31; nomes="Diciembre"; break;	
	}	
%>
<tr bgcolor="<%=vacio%>">
<th colspan="7" align="center"><b><%=nomes%></b></th>
</tr>
<tr align="center" bgcolor="<%=vacio%>">
<td>D</td><td>L</td><td>M</td><td>M</td><td>J</td><td>V</td><td>S</td>
</tr>
<%	
	nweek=6;
	
for (s=1;s<nweek+1;s++) { %>
<tr>
 <% for (c=1;c<8;c++) { ndia++; %>

 	<% if (s==1 && c<ds) { ndia=0; %>
		<td bgcolor="<%=vacio%>">&nbsp;</td>
 	<% } else { %> 		
	 	<% if (ndia>dom) { %>
			<td bgcolor="<%=vacio%>">&nbsp;</td>
		<% } else { %>
<td align="right" <%stmt=con.createStatement();
    if(ndia<10) { ncal="0"+ndia; } else { ncal=""+ndia; }
    ini_dia = ini_mes.substring(0,8) + ncal; 
	date_temp = convertemp.parse(ini_dia); 	
	paso="0";

	Iterator ite2 = lista.iterator();
	while(ite2.hasNext()){
		String lexdia = (String)ite2.next();	
		if(ini_dia.equals(lexdia)) { paso="1"; }
	}

	if(paso.equals("1")) {
		bgtemp=activo;
	} else {
		if (date_temp.after(date_pini) &&  date_temp.before(date_pfin)) {
			bgtemp=percolor;
		} else {
			bgtemp=claro;	
		}
	}
    out.print(" bgcolor="+bgtemp);		
	 %> ><%=ndia%></td> 	
	
 		<% } %> 	
 	<% } %> 	

 <% } %>
</tr>
<% } %>
<tr bgcolor="<%=vacio%>">
<td colspan="7" align="center"><b><%=anio_fper%></b></td>
</tr>
<% stmt.close(); %>
      </table></TD> 
<% if (colcal==4) { colcal=0; %></TR><% } %>
<% } %>
</table>
<% } %>


<% } %>
</div>
</BODY>
</html>
