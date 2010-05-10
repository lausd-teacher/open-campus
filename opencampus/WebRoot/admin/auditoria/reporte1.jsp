<%@ page import="java.sql.*"%>
<%@ page import="java.lang.Math.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.*"%>

<%
	response.setDateHeader("Expires", -1);
	response.setHeader("Pragma", "no-cache");
%>


<%
	final String URL = "jdbc:oracle:thin:@192.168.72.12:1521:bdopencampus";
	//final String URL = "jdbc:oracle:thin:@192.168.68.101:1521:bdopencampus";

	Connection con = null;
	java.sql.Statement stmt = null;
	ResultSet rs = null;

	java.sql.Statement stmt2 = null;
	ResultSet rs2 = null;
	java.sql.Statement stmt3 = null;
	ResultSet rs3 = null;
	java.sql.Statement stmt4 = null;
	ResultSet rs4 = null;
	java.sql.Statement stmt5 = null;
	ResultSet rs5 = null;

	String vrusuario = "";
	String vrficha = "";
	String vrsyllabus = "";
	String vrapto = "0";
	int vrcntu = 0;
	int vrcntn = 0;
	int vrtrc = 0;
	int vrtrs = 0;

	int filas = 0;

	//datos del formulario
	String grupo = "";
	String busca = "";
	String txtcod = "";
	String txtape = "";
	String selper = "";
	String selcur = "";
	String fecini = "";
	String fecfin = "";
	String actyear = "";

	String daybegin = "";
	String monthbegin = "";
	String yearbegin = "";
	String dayend = "";
	String monthend = "";
	String yearend = "";

	int iday = 0;
	int imonth = 0;
	int iyear = 0;
	int fday = 0;
	int fmonth = 0;
	int fyear = 0;

	if (con == null || con.isClosed()) {
		try {
			DriverManager
			.registerDriver(new oracle.jdbc.driver.OracleDriver());
			con = DriverManager.getConnection(URL, "campus", "virtual");
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());

		}
	}

	if (request.getParameter("busca") == null) {
		String dats = (new java.sql.Timestamp(System
		.currentTimeMillis())).toString();
		int dia_a = Integer.parseInt(dats.substring(8, 10));
		int mes_a = Integer.parseInt(dats.substring(5, 7));
		int anio_a = Integer.parseInt(dats.substring(0, 4));

		imonth = mes_a;
		iyear = anio_a;
		fday = dia_a;
		fmonth = mes_a;
		fyear = anio_a;
	}
%>
<html>
	<head>
		<%
		String sWS = request.getContextPath();
		%>
		<link rel="stylesheet" type="text/css" href="<%=sWS%>/css/opencampus.css">
		<meta http-equiv="Expires=-1" content="text/html; charset=iso-8859-1">
		<script src="fecha.js"></script>
		<script>
    function validar(form)  {

    if(form.daybegin.value=="--" && form.monthbegin.value=="--" && form.yearbegin.value=="--" && form.dayend.value=="--" && form.monthend.value=="--" && form.yearend.value=="--") {
    	form.submit();
    } else {
        if (form.daybegin.value!="--" && form.monthbegin.value!="--" && form.yearbegin.value!="--" && form.dayend.value!="--" && form.monthend.value!="--" && form.yearend.value!="--")
        {
        if (form.daybegin.options[form.daybegin.selectedIndex].text=="29" && form.monthbegin.options[form.monthbegin.selectedIndex].text=="Feb")
        {
        alert("Fecha NO valida para Febrero.");
        return false;
        }
        if (form.daybegin.options[form.daybegin.selectedIndex].text=="30" && form.monthbegin.options[form.monthbegin.selectedIndex].text=="Feb")
        {
        alert("Fecha NO valida para Febrero.");
        return false;
        }
        if (form.monthbegin.options[form.monthbegin.selectedIndex].text=="Feb" || form.monthbegin.options[form.monthbegin.selectedIndex].text=="Abr" || form.monthbegin.options[form.monthbegin.selectedIndex].text=="Jun" || form.monthbegin.options[form.monthbegin.selectedIndex].text=="Set" || form.monthbegin.options[form.monthbegin.selectedIndex].text=="Nov")
        {
        if (form.daybegin.options[form.daybegin.selectedIndex].text=="31") {
        alert("Fecha NO valida.");
        return false;
        } }
        
        if (form.dayend.options[form.dayend.selectedIndex].text=="29" && form.monthend.options[form.monthend.selectedIndex].text=="Feb")
        {
        alert("Fecha NO valida para Febrero.");
        return false;
        }
        if (form.dayend.options[form.dayend.selectedIndex].text=="30" && form.monthend.options[form.monthend.selectedIndex].text=="Feb")
        {
        alert("Fecha NO valida para Febrero.");
        return false;
        }
        if (form.monthend.options[form.monthend.selectedIndex].text=="Feb" || form.monthend.options[form.monthend.selectedIndex].text=="Abr" || form.monthend.options[form.monthend.selectedIndex].text=="Jun" || form.monthend.options[form.monthend.selectedIndex].text=="Set" || form.monthend.options[form.monthend.selectedIndex].text=="Nov")
        {
        if (form.dayend.options[form.dayend.selectedIndex].text=="31") {
        alert("Fecha NO valida.");
        return false;
        } }
        
        iniDate = new Date(form.yearbegin.value,form.monthbegin.value,form.daybegin.value);
        finDate = new Date(form.yearend.value,form.monthend.value, form.dayend.value);
		if(finDate<iniDate) {
           	alert("El fin no puede ser antes que el inicio");
	        return false;
		}

			form.submit();
		} else {
        alert("Fecha NO valida.");
        return false;
		}
    }
            
    }
    </script>
	</head>
	<%
			if (request.getParameter("grupo") != null) {
			grupo = request.getParameter("grupo");
		}
		if (request.getParameter("busca") != null) {
			busca = request.getParameter("busca");
		}
		if (request.getParameter("txtcod") != null) {
			txtcod = request.getParameter("txtcod");
		}
		if (request.getParameter("txtape") != null) {
			txtape = request.getParameter("txtape");
		}
		if (request.getParameter("selper") != null) {
			selper = request.getParameter("selper");
		}
		if (request.getParameter("selcur") != null) {
			selcur = request.getParameter("selcur");
		}

		if ((request.getParameter("daybegin") != null)
				&& !(request.getParameter("daybegin").equals("--"))
				&& (request.getParameter("monthbegin") != null)
				&& !(request.getParameter("monthbegin").equals("--"))
				&& (request.getParameter("yearbegin") != null)
				&& !(request.getParameter("yearbegin").equals("--"))) {
			iday = Integer.parseInt(request.getParameter("daybegin"));
			imonth = Integer.parseInt(request.getParameter("monthbegin"));
			iyear = Integer.parseInt(request.getParameter("yearbegin"));
			fecini = iyear + "-" + imonth + "-" + iday;
		}

		if ((request.getParameter("dayend") != null)
				&& !(request.getParameter("dayend").equals("--"))
				&& (request.getParameter("monthend") != null)
				&& !(request.getParameter("monthend").equals("--"))
				&& (request.getParameter("yearend") != null)
				&& !(request.getParameter("yearend").equals("--"))) {
			fday = Integer.parseInt(request.getParameter("dayend"));
			fmonth = Integer.parseInt(request.getParameter("monthend"));
			fyear = Integer.parseInt(request.getParameter("yearend"));
			fecfin = fyear + "-" + fmonth + "-" + fday;
		}
	%>
	<body>
		<div id="MAIN2">
			<!--  titulo -->
			<div id="title">
				<div id="titleText">
					Auditor&iacute;a
					-
					Usuario
				</div>
			</div>
			<!--  /titulo -->
			<table class="buttonT">
				<tr>
					<td align="right">
						<a href="javascript:void(0)"
							onClick="validar(document.forms[1]); return false;">Buscar
						</a>
					</td>
				</tr>
			</table>
			<table cellspacing="0" class="tb3">
				<form name="YearForm" method="post" action="reporte1.jsp">
					<input name="busca" type="hidden" value="0">
				<tr>
					<td height="6"></td>
				</tr>
				<tr>
					<th>
						A&ntilde;o
					</th>
					<td colspan="2">
						<select class="cfm40" name="yeareport"
							onChange="document.forms[0].submit()">
							<option value="--">
								Todos
							</option>
							<%
									if (request.getParameter("yeareport") != null) {
									actyear = request.getParameter("yeareport");
								}
								int a;
								String datets11 = (new Timestamp(System.currentTimeMillis()))
										.toString();
								int anioac = Integer.parseInt(datets11.substring(0, 4));
								for (a = 2003; a < anioac + 1; a++) {
							%>
							<option value="<%=a%>"
								<% if(actyear.equals(""+a)) { out.print("selected"); } %>><%=a%></option>
							<%
							}
							%>
						</select>
					</td>
					</form>
				</tr>

				<tr>
					<form name="UserForm" method="post" action="reporte1.jsp">
						<input name="busca" type="hidden" value="1">
						<input name="yeareport" type="hidden" value="<%=actyear%>">
					<th>
						Per&iacute;odo:
					</th>
					<td colspan="2">
						<select class="cfm40" name="selper">
							<option value="">
								Todos
								<%
								stmt = con.createStatement();
								String sqlper = "select idperiodo,nombre from periodo where to_char(fecha_inicio,'YYYY')='"
										+ actyear + "' order by nombre";
								rs = stmt.executeQuery(sqlper);
								while (rs.next()) {
							%>
							
							<option value="<%=rs.getString(1)%>"
								<% if(selper.equals(rs.getString(1))) { %> selected <% } %>><%=rs.getString(2)%>
								<%
									}
									stmt.close();
								%>
							
						</select>
					</td>
				<tr>
					<th>
						Tipo
					</th>
					<td colspan="2">
						<select class="cfm40" name="grupo">
							<option value="003" <% if(grupo.equals("003")) { %> selected
								<% } %>>
								Estudiante
							<option value="010" <% if(grupo.equals("010")) { %> selected
								<% } %>>
								Estudiante C7
							<option value="013" <% if(grupo.equals("013")) { %> selected
								<% } %>>
								Estudiante CEP
							<option value="002" <% if(grupo.equals("002")) { %> selected
								<% } %>>
								Consultor
						</select>
					</td>
				</tr>
				<tr>
					<th>
						Apellido
					</th>
					<td colspan="2">
						<input type="text" name="txtape" maxlength="50" class="cfm40"
							value="">
					</td>
				</tr>
				<tr>
					<th>
						Usuario
					</th>
					<td colspan="2">
						<input type="text" name="txtcod" maxlength="50" class="cfm40"
							value="">
					</td>
				</tr>
				<tr>
					<th>
						Curso
					</th>
					<td colspan="2">
						<select name="selcur" class="cfm40">
							<option value="">
								Todos
								<%
								stmt = con.createStatement();
								String sqlcur = "select idcurso,nombre from curso order by nombre";
								rs = stmt.executeQuery(sqlcur);
								while (rs.next()) {
							%>
							
							<option value="<%=rs.getString(1)%>"
								<% if(selcur.equals(rs.getString(1))) { %> selected <% } %>><%=rs.getString(2)%>
								<%
									}
									stmt.close();
								%>
							
						</select>
					</td>
				</tr>

				<tr>
					<th>
						F. Inicio
					</th>
					<td>
						<select class="cfm5" name="daybegin">
							<option value="--">
								--
							</option>
							<%
								int h;
								for (h = 1; h < 32; h++) {
							%>
							<option value="<%=h%>"
								<% if(iday==h) { out.print("selected"); } %>><%=h%></option>
							<%
							}
							%>
						</select>

						<select class="cfm5" name="monthbegin">
							<option value="--">
								--
							</option>
							<option value="01" <% if(imonth==1) { out.print("selected"); } %>>
								Ene
							</option>
							<option value="02" <% if(imonth==2) { out.print("selected"); } %>>
								Feb
							</option>
							<option value="03" <% if(imonth==3) { out.print("selected"); } %>>
								Mar
							</option>
							<option value="04" <% if(imonth==4) { out.print("selected"); } %>>
								Abr
							</option>
							<option value="05" <% if(imonth==5) { out.print("selected"); } %>>
								May
							</option>
							<option value="06" <% if(imonth==6) { out.print("selected"); } %>>
								Jun
							</option>
							<option value="07" <% if(imonth==7) { out.print("selected"); } %>>
								Jul
							</option>
							<option value="08" <% if(imonth==8) { out.print("selected"); } %>>
								Ago
							</option>
							<option value="09" <% if(imonth==9) { out.print("selected"); } %>>
								Set
							</option>
							<option value="10"
								<% if(imonth==10) { out.print("selected"); } %>>
								Oct
							</option>
							<option value="11"
								<% if(imonth==11) { out.print("selected"); } %>>
								Nov
							</option>
							<option value="12"
								<% if(imonth==12) { out.print("selected"); } %>>
								Dic
							</option>
						</select>

						<select class="cfm5" name="yearbegin">
							<option value="--">
								--
							</option>
							<%
								int j;
								String datets = (new Timestamp(System.currentTimeMillis()))
										.toString();
								int anioact = Integer.parseInt(datets.substring(0, 4));
								for (j = anioact - 5; j < anioact + 1; j++) {
							%>
							<option value="<%=j%>"
								<% if(iyear==j) { out.print("selected"); } %>><%=j%></option>
							<%
							}
							%>
						</select>
					</TD>
					<TD>
						F. Fin
						<select class="cfm5" name="dayend">
							<option value="--">
								--
							</option>
							<%
								int k;
								for (k = 1; k < 32; k++) {
							%>
							<option value="<%=k%>"
								<% if(fday==k) { out.print("selected"); } %>><%=k%></option>
							<%
							}
							%>
						</select>

						<select class="cfm5" name="monthend">
							<option value="--">
								--
							</option>
							<option value="01" <% if(fmonth==1) { out.print("selected"); } %>>
								Ene
							</option>
							<option value="02" <% if(fmonth==2) { out.print("selected"); } %>>
								Feb
							</option>
							<option value="03" <% if(fmonth==3) { out.print("selected"); } %>>
								Mar
							</option>
							<option value="04" <% if(fmonth==4) { out.print("selected"); } %>>
								Abr
							</option>
							<option value="05" <% if(fmonth==5) { out.print("selected"); } %>>
								May
							</option>
							<option value="06" <% if(fmonth==6) { out.print("selected"); } %>>
								Jun
							</option>
							<option value="07" <% if(fmonth==7) { out.print("selected"); } %>>
								Jul
							</option>
							<option value="08" <% if(fmonth==8) { out.print("selected"); } %>>
								Ago
							</option>
							<option value="09" <% if(fmonth==9) { out.print("selected"); } %>>
								Set
							</option>
							<option value="10"
								<% if(fmonth==10) { out.print("selected"); } %>>
								Oct
							</option>
							<option value="11"
								<% if(fmonth==11) { out.print("selected"); } %>>
								Nov
							</option>
							<option value="12"
								<% if(fmonth==12) { out.print("selected"); } %>>
								Dic
							</option>
						</select>

						<select class="cfm5" name="yearend">
							<option value="--">
								--
							</option>
							<%
								int l;
								for (l = anioact - 5; l < anioact + 1; l++) {
							%>
							<option value="<%=l%>"
								<% if(fyear==l) { out.print("selected"); } %>><%=l%></option>
							<%
							}
							%>
						</select>
					</td>
				</tr>
				<tr>
					<td height="6"></td>
				</tr>
				</form>
			</table>

			<%
			if (busca.equals("1")) {
			%>
			<br>
			<table cellspacing="0" class="tb2">
				<tr>
					<th width="25"></th>
					<th width="76">
						Usuario
					</th>
					<th>
						Nombre
					</th>
					<th>
						Curso
					</th>
					<th width="60">
						Per&iacute;odo
					</th>
					<th width="100">
						Contrase&ntilde;a
					</th>
					<th width="60">
						Examen
					</th>
					<th width="50">
						Estado
					</th>
					<th width="50"></th>
				</tr>
				<tr>
					<td height="6"></td>
				</tr>
				<%
						stmt = con.createStatement();

						String sql = "SELECT DISTINCT a.idusuario,b.paterno,b.materno,b.nombres,g.nombre,e.nombre,f.idcurso,d.idperiodo,d.idsyllabus,c.idficha,a.clave,d.observacion";

						if (grupo.equals("002")) {
							sql += ",c.tipo,e.fecha_inicio,ne.nota";
							sql += " FROM usuario a, datos_personales b,asignacion_curso c,ficha d,periodo e,syllabus f,curso g, nota_examen ne";
						} else {
							sql += ",c.estado,e.fecha_inicio,ne.nota";
							sql += " FROM usuario a, datos_personales b,matricula c,ficha d,periodo e,syllabus f,curso g, nota_examen ne";
						}
						sql += " WHERE a.idusuario = b.idusuario "
						+ " AND (upper(b.paterno) like upper('"
						+ txtape
						+ "%') OR upper(b.materno) like upper('"
						+ txtape
						+ "%'))"
						+ " AND upper(a.idusuario) like upper('"
						+ txtcod
						+ "%')"
						+ " AND a.idusuario in (select idusuario from grupo_usuario where idgrupo='"
						+ grupo
						+ "')"
						+ " AND a.idusuario = c.idusuario "
						+ " AND c.idficha = d.idficha "
						+ " AND d.idperiodo = e.idperiodo "
						+ " AND d.idperiodo like '"
						+ selper
						+ "%'"
						+ " AND d.idsyllabus = f.idsyllabus "
						+ " AND f.idcurso = g.idcurso "
						+ " AND f.idcurso like '"
						+ selcur
						+ "%'"
						+ " AND c.idusuario =  ne.idusuario AND d.idficha = ne.idficha ";

						if (grupo.equals("002")) {
							sql += " AND c.tipo = '1'";
						}
						//
						sql += " UNION ALL ";
						//
						sql += "SELECT DISTINCT a.idusuario,b.paterno,b.materno,b.nombres,g.nombre,e.nombre,f.idcurso,d.idperiodo,d.idsyllabus,c.idficha,a.clave,d.observacion";

						if (grupo.equals("002")) {
							sql += ",c.tipo,e.fecha_inicio,null";
							sql += " FROM usuario a, datos_personales b,asignacion_curso c,ficha d,periodo e,syllabus f,curso g, nota_examen ne";
						} else {
							sql += ",c.estado,e.fecha_inicio,null";
							sql += " FROM usuario a, datos_personales b,matricula c,ficha d,periodo e,syllabus f,curso g, nota_examen ne";
						}
						sql += " WHERE a.idusuario = b.idusuario "
						+ " AND (upper(b.paterno) like upper('"
						+ txtape
						+ "%') OR upper(b.materno) like upper('"
						+ txtape
						+ "%'))"
						+ " AND upper(a.idusuario) like upper('"
						+ txtcod
						+ "%')"
						+ " AND a.idusuario in (select idusuario from grupo_usuario where idgrupo='"
						+ grupo
						+ "')"
						+ " AND a.idusuario = c.idusuario "
						+ " AND c.idficha = d.idficha "
						+ " AND d.idperiodo = e.idperiodo "
						+ " AND d.idperiodo like '"
						+ selper
						+ "%'"
						+ " AND d.idsyllabus = f.idsyllabus "
						+ " AND f.idcurso = g.idcurso "
						+ " AND f.idcurso like '"
						+ selcur
						+ "%'"
						+ " AND ( c.idusuario <>  ne.idusuario  OR d.idficha <> ne.idficha ) "
						+ " AND ( select count(*) from nota_examen ) > 0 ";

						if (grupo.equals("002")) {
							sql += " AND c.tipo = '1'";
						}
						//
						sql += " UNION ALL ";
						//
						sql += "SELECT DISTINCT a.idusuario,b.paterno,b.materno,b.nombres,g.nombre,e.nombre,f.idcurso,d.idperiodo,d.idsyllabus,c.idficha,a.clave,d.observacion";

						if (grupo.equals("002")) {
							sql += ",c.tipo,e.fecha_inicio,null";
							sql += " FROM usuario a, datos_personales b,asignacion_curso c,ficha d,periodo e,syllabus f,curso g ";
						} else {
							sql += ",c.estado,e.fecha_inicio,null";
							sql += " FROM usuario a, datos_personales b,matricula c,ficha d,periodo e,syllabus f,curso g ";
						}
						sql += " WHERE a.idusuario = b.idusuario "
						+ " AND (upper(b.paterno) like upper('"
						+ txtape
						+ "%') OR upper(b.materno) like upper('"
						+ txtape
						+ "%'))"
						+ " AND upper(a.idusuario) like upper('"
						+ txtcod
						+ "%')"
						+ " AND a.idusuario in (select idusuario from grupo_usuario where idgrupo='"
						+ grupo
						+ "')"
						+ " AND a.idusuario = c.idusuario "
						+ " AND c.idficha = d.idficha "
						+ " AND d.idperiodo = e.idperiodo "
						+ " AND d.idperiodo like '"
						+ selper
						+ "%'"
						+ " AND d.idsyllabus = f.idsyllabus "
						+ " AND f.idcurso = g.idcurso "
						+ " AND f.idcurso like '"
						+ selcur
						+ "%'"
						+ " AND ( SELECT count(*) FROM nota_examen ) = 0 ";

						if (grupo.equals("002")) {
							sql += " AND c.tipo = '1'";
						}

						sql += " ORDER BY paterno,materno,nombres";

						//
						//out.println(sql);

						rs = stmt.executeQuery(sql);
						filas = 0;
						java.sql.Date perinicio;

						String dateString = "11/06/2003";
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						java.util.Date d;
						try {
							d = dateFormat.parse(dateString);
							dateFormat.applyPattern("yyyy-MM-dd");
							dateString = dateFormat.format(d);
						} catch (Exception e) {
							e.printStackTrace();
						}

						java.sql.Date limiteval = java.sql.Date.valueOf(dateString);

						String percodigo;
						while (rs.next()) {
							vrapto = "1";
							percodigo = rs.getString(8);
							perinicio = rs.getDate(14);
							vrusuario = rs.getString(1);
							vrficha = rs.getString(10);
							vrsyllabus = rs.getString(9);
							filas++;
				%>

				<%
							String estilo = "data_add";
							if (filas % 2 == 1)
						estilo = "data_even";
				%>

				<tr class="<%=estilo%>" onmouseover="this.className='uno';"
					onmouseout="this.className='<%=estilo%>';">
					<td align="center"><%=filas%></td>
					<td>
						<a
							href="reporte1_detalle.jsp?iduser=<%=rs.getString(1)%>&grp=<%=grupo%>&idcurso=<%=rs.getString(7)%>&idperiodo=<%=rs.getString(8)%>&idsyllabus=<%=rs.getString(9)%>&idficha=<%=rs.getString(10)%>&fecini=<%=fecini%>&fecfin=<%=fecfin%>&tiprep=1"
							title="Detalle"><%=rs.getString(1)%></a>
					</td>
					<td><%=rs.getString(2)%>&nbsp;<%=rs.getString(3)%>,&nbsp;<%=rs.getString(4)%></td>
					<td><%=rs.getString(5)%>&nbsp;(<%=rs.getString(12).trim()%>)
					</td>
					<td><%=rs.getString(6)%></td>
					<td><%=rs.getString(11)%></td>
					<td><%=(rs.getString(15) == null ? "SIN" : rs
									.getString(15))%></td>
					<td><%=rs.getString(13)%></td>
					<td>
						<%
								if (!grupo.equals("002")) {
								/*
								 *  Evaluacion A : Aprobado en cada unidad
								 *
								 stmt3=con.createStatement();
								 String sql3 = "SELECT count(idunidad) FROM unidad_syllabus WHERE idsyllabus='"+ vrsyllabus +"'";
								 rs3 = stmt3.executeQuery(sql3);
								 rs3.next();
								 vrcntu=rs3.getInt(1);
								 stmt3.close();
								
								 stmt2=con.createStatement();
								 String sql2 = "SELECT round(AVG(nota)) FROM nota_test WHERE idusuario='"+ vrusuario +"' AND idficha='"+ vrficha +"' GROUP BY idunidad ";
								 rs2 = stmt2.executeQuery(sql2);
								 vrcntn=0;
								 while(rs2.next()) {
								 if(rs2.getInt(1)<11) {
								 vrapto="0";
								 }	 
								 vrcntn++;
								 }
								 if(vrcntu!=vrcntn) { vrapto="0"; }	 
								 */
								/*
								 *  Evaluacion B Aprobado en promedio general
								 **/

								stmt3 = con.createStatement();
								String sql3 = "SELECT count(idunidad) FROM unidad_syllabus WHERE idsyllabus='"
										+ vrsyllabus + "'";
								rs3 = stmt3.executeQuery(sql3);
								rs3.next();
								vrcntu = rs3.getInt(1);
								stmt3.close();

								stmt2 = con.createStatement();
								String sql2 = "SELECT round(AVG(nota)) FROM nota_test WHERE idusuario='"
										+ vrusuario
										+ "' AND idficha='"
										+ vrficha
										+ "' GROUP BY idunidad";
								rs2 = stmt2.executeQuery(sql2);
								vrcntn = 0;

								/*
								 if(percodigo.equals("200306") || percodigo.equals("200307") || percodigo.equals("2003B7") || percodigo.equals("200308") || percodigo.equals("2003B8") || percodigo.equals("20039A")){ 
								 12/06/2003  - 17/08/2003
								 */

								if (perinicio.after(limiteval)) {

									while (rs2.next()) {
										vrcntn = vrcntn + rs2.getInt(1);
									}
									/*	int prom=Math.round(vrcntn/vrcntu);	*/

									double p_n = Double.valueOf("" + vrcntn)
									.doubleValue();
									double p_u = Double.valueOf("" + vrcntu)
									.doubleValue();

									double prom2 = p_n / p_u;
									if ((prom2 + 0.5) < 11) {
										vrapto = "0";
									}

								} else {

									while (rs2.next()) {
										if ((rs2.getInt(1)) < 11) {
									vrapto = "0";
										}
										vrcntn++;
									}
									if (vrcntu != vrcntn) {
										vrapto = "0";
									}

								}

								stmt4 = con.createStatement();
								String sql4 = "SELECT count(idtrabajo) FROM ficha_trabajo WHERE idsyllabus='"
										+ vrsyllabus
										+ "' AND idficha='"
										+ vrficha
										+ "' ";
								rs4 = stmt4.executeQuery(sql4);
								rs4.next();
								vrtrc = rs4.getInt(1);
								stmt4.close();

								if (vrtrc >= 1) {
									stmt5 = con.createStatement();
									String sql5 = "SELECT count(idtrabajo) FROM nota_trabajo WHERE idusuario='"
									+ vrusuario
									+ "' AND idsyllabus='"
									+ vrsyllabus
									+ "' AND idficha='"
									+ vrficha
									+ "' AND (estado='S' OR estado='A' OR estado='D' OR estado='R') ";
									rs5 = stmt5.executeQuery(sql5);
									rs5.next();
									vrtrs = rs5.getInt(1);
									stmt5.close();

									if (vrtrc != vrtrs) {
										vrapto = "0";
									}
								}

								if (vrapto.equals("1")) {
									out.print("APTO");
								} else {
									out.print("--");
								}
								stmt2.close();
									}
						%>
					</td>
				</tr>

				</logic:iterate>



				<%
				}
				%>
			</table>
			<%
			}
			%>

			<%
				stmt.close();
				con.close();
			%>
		</div>
	</BODY>
</html>
