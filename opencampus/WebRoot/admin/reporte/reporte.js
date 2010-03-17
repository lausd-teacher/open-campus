
function buscarPorKey(e) {
	var key;
	if (window.event) {
		key = window.event.keyCode;     //IE
	} else {
		key = e.which;     //firefox
	}
	if ("13" == key) {
		busquedaReporte();
	}
}
function abrirTDS(s_id) {
	var divs = document.getElementById("todos_items_busqueda").getElementsByTagName("TR");
	for (var i = 0; i < divs.length; i = i + 1) {
		if (divs[i]["id"].substring(0, s_id.length) == (s_id)) {
			divs[i].style["visibility"] = (divs[i].style["visibility"] != "hidden") ? "hidden" : "visible";
			divs[i].style["display"] = (divs[i].style["display"] != "none") ? "none" : "";
		}
	}
}
function catcalc(cal) {
	var date = cal.date;
	var time = date.getTime();
	var field = document.getElementById("form_fechaInicio2");
	if (field == cal.params.inputField) {
		field = document.getElementById("form_fechaInicio1");
		time -= (4) * Date.WEEK;
	} else {
		time += (4) * Date.WEEK;
	}
	var date2 = new Date(time);
	field.value = date2.print("%d-%m-%Y");
}
function seleccionarDIVGrupo(contenedor, check) {
	var divs = document.getElementById("todos_items_busqueda").getElementsByTagName("TR");
	for (var i = 0; i < divs.length; i = i + 1) {
		if (divs[i]["id"].substring(0, contenedor.length + 1) == (contenedor + "_")) {
			var tr_tds = divs[i].getElementsByTagName("TD");
			var tr_check = divs[i].getElementsByTagName("INPUT");
			for (var j = 0; j < tr_check.length; j = j + 1) {
				if (tr_check[j].type == "checkbox") {
					tr_check[j].checked = check.checked;
				}
			}
			for (var k = 0; k < tr_tds.length; k = k + 1) {
				if (check.checked == true) {
					tr_tds[k].style.backgroundColor = "#fce57c";
				} else {
					tr_tds[k].style.backgroundColor = "#E0EAF3";
				}
			}
		}
	}
}
function seleccionarDIV(tr_contenedor, contenedor, check) {
	var js_cantidad = 0;
	var divs = document.getElementById("todos_items_busqueda").getElementsByTagName("TR");
	for (var i = 0; i < divs.length; i = i + 1) {
		if (divs[i]["id"].substring(0, tr_contenedor.length + 1) == (tr_contenedor + "_")) {
			var tr_check = divs[i].getElementsByTagName("INPUT");
			for (var j = 0; j < tr_check.length; j = j + 1) {
				if (tr_check[j].type == "checkbox" && true == tr_check[j].checked) {
					js_cantidad++;
				}
			}
		}
	}
	var items = document.getElementById(tr_contenedor).getElementsByTagName("INPUT");
	for (var i = 0; i < items.length; i = i + 1) {
		if (items[i].type == "checkbox") {
			if (0 < js_cantidad) {
				items[i].checked = true;
			} else {
				items[i].checked = false;
			}
		}
	}
	var divs = document.getElementById(contenedor).getElementsByTagName("TD");
	for (var i = 0; i < divs.length; i = i + 1) {
		if (check.checked == true) {
			divs[i].style.backgroundColor = "#fce57c";
		} else {
			divs[i].style.backgroundColor = "#E0EAF3";
		}
	}
}
function limpiarCheckBox() {
	var items = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
	for (var i = 0; i < items.length; i = i + 1) {
		if (items[i].type == "checkbox") {
			items[i].checked = false;
		}
	}
}
function checkCheckBox(valor) {
	try {
		var divs = document.getElementById("todos_items_busqueda").getElementsByTagName("TR");
		for (var i = 0; i < divs.length; i = i + 1) {
			if (divs[i]["id"].substring(0, 3) == "tr_" && divs[i]["className"] == "fon_color02") {
				var js_td = divs[i].getElementsByTagName("TD");
				for (var j = 0; j < js_td.length; j = j + 1) {
					if (valor == true) {
						js_td[j].style.backgroundColor = "#fce57c";
					} else {
						js_td[j].style.backgroundColor = "#E0EAF3";
					}
				}
			}
		}
		var items = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
		for (var i = 0; i < items.length; i = i + 1) {
			if (items[i].type == "checkbox") {
				items[i].checked = valor;
			}
		}
	}
	catch (e) {
	}
}
function desplegarTRS(valor) {
	try {
		var divs = document.getElementById("todos_items_busqueda").getElementsByTagName("TR");
		for (var i = 0; i < divs.length; i = i + 1) {
			if (divs[i]["id"].substring(0, 3) == "tr_" && divs[i]["className"] == "fon_color02") {
				if (valor == true) {
					divs[i].style["visibility"] = "visible";
					divs[i].style["display"] = "";
				} else {
					divs[i].style["visibility"] = "hidden";
					divs[i].style["display"] = "none";
				}
			}
		}
	}
	catch (e) {
		alert(e);
	}
}
function busquedaReporte() {
	limpiarBusqueda();
	var busNom = document.getElementById("form_busquedaUsuario");
	var busNomInsert = document.getElementById("idbusquedaUsuario");
	busNomInsert.value = busNom.value;
	/////////////////////////////////////////////////////////////
	busNom = document.getElementById("form_busquedaEmpresa");
	busNomInsert = document.getElementById("idbusquedaEmpresa");
	busNomInsert.value = busNom.value;
//	/////////////////////////////////////////////////////////////
//	busNom = document.getElementById("form_busquedaContacto");
//	busNomInsert = document.getElementById("idbusquedaContacto");
//	busNomInsert.value = busNom.value;
	/////////////////////////////////////////////////////////////
	
	
	/////////////////////////////////////////////////////////////
	busNom = document.getElementById("lista_form_busquedaCurso");
	busNomInsert = document.getElementById("idbusquedaCursoNombre");
	busNomInsert.value = busNom.value;
	busNomInsert = document.getElementById("idbusquedaCurso");
	if (0 < busNom.value.length) {
		busNom = document.getElementById("form_busquedaCurso");
		busNomInsert.value = busNom.value;
	} else {
		busNomInsert.value = "";
	}
	
	/////////////////////////////////////////////////////////////
	busNom = document.getElementById("form_busquedaGrupo");
	busNomInsert = document.getElementById("idbusquedaGrupo");
	busNomInsert.value = busNom.value;
	
		/////////////////////////////////////////////////////////////
	busNom = document.getElementById("form_busquedaUsuarioNombre");
	busNomInsert = document.getElementById("idbusquedaUsuarioNombre");
	busNomInsert.value = busNom.value;
	
	
	
	//////////////////////////////////////////////////////////////
	var busFor = document.getElementById("form_busquedaFormacion");
	var busForInsert = document.getElementById("idbusquedaFormacion");
	var selLength = busFor.length;
	var i;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (busFor.options[i].selected == true) {
			busForInsert.value = busFor.options[i].value;
		}
	}
	/////////////////////////////////////////////////////////////
	try {
		var busStringExacta = document.getElementById("form_busquedaExacta");
		var busStringForExacta = document.getElementById("idbusquedaExacta");
		busStringForExacta.value = (busStringExacta.checked == true) ? "1" : "0";
	}
	catch (e) {
	}	
	////////////////////////////////////////////////////////////
	var busDep = document.getElementById("form_busquedaDepartamento");
	var busDepInsert = document.getElementById("idbusquedaDepartamento");
	selLength = busDep.length;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (busDep.options[i].selected == true) {
			busDepInsert.value = busDep.options[i].value;
			break;
		}
	}
	///////////////////////////////////////////////////////////////
	var busCic = document.getElementById("form_busquedaCiclo");
	var busCicInsert = document.getElementById("idbusquedaCiclo");
	selLength = busCic.length;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (busCic.options[i].selected == true) {
			busCicInsert.value = busCic.options[i].value;
			break;
		}
	}
	///////////////////////////////////////////////////////////////
	var busSed = document.getElementById("form_busquedaSede");
	var busSedInsert = document.getElementById("idbusquedaSede");
	selLength = busSed.length;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (busSed.options[i].selected == true) {
			busSedInsert.value = busSed.options[i].value;
			break;
		}
	}
	///////////////////////////////////////////////////////////////
	var busIng = document.getElementById("form_busquedaIngreso");
	var busIngInsert = document.getElementById("idbusquedaIngreso");
	selLength = busIng.length;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (busIng.options[i].selected == true) {
			busIngInsert.value = busIng.options[i].value;
			break;
		}
	}
	/////////////////////////////////////////////////////////////
	var busPer = document.getElementById("form_busquedaPeriodo");
	var busPerInsert = document.getElementById("idbusquedaPeriodo");
	selLength = busPer.length;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (busPer.options[i].selected == true) {
			busPerInsert.value = busPer.options[i].value;
			break;
		}
	}
	/////////////////////////////////////////////////////////////
	var busFecha = document.getElementById("form_fechaInicio1");
	var busFechaInsert = document.getElementById("idbusquedaFecha1");
	busFechaInsert.value = busFecha.value;
	busFecha = document.getElementById("form_fechaInicio2");
	busFechaInsert = document.getElementById("idbusquedaFecha2");
	busFechaInsert.value = busFecha.value;
	/////////////////////////////////////////////////////////////
	document.busquedaTipo.submit();
	return false;
}
function limpiarBusqueda() {
	var varib = document.getElementById("idbusquedaUsuario");
	varib.value = "";
	varib = document.getElementById("idbusquedaUsuarioNombre");
	varib.value = "";
	varib = document.getElementById("idbusquedaEmpresa");
	varib.value = "";
	varib = document.getElementById("idbusquedaContacto");
	varib.value = "";
	varib = document.getElementById("idbusquedaCursoNombre");
	varib.value = "";
	varib = document.getElementById("idbusquedaGrupo");
	varib.value = "";
	varib = document.getElementById("idbusquedaCurso");
	varib.value = "";
	varib = document.getElementById("idbusquedaExacta");
	varib.value = "";
	varib = document.getElementById("idbusquedaPeriodo");
	varib.value = "0";
	varib = document.getElementById("idbusquedaFormacion");
	varib.value = "0";
	varib = document.getElementById("idbusquedaSede");
	varib.value = "0";
	varib = document.getElementById("idbusquedaFecha1");
	varib.value = "";
	varib = document.getElementById("idbusquedaIngreso");
	varib.value = "";
	varib = document.getElementById("idbusquedaFecha2");
	varib.value = "";
	varib = document.getElementById("idbusquedaCiclo");
	varib.value = "0";
	varib = document.getElementById("idbusquedaDepartamento");
	varib.value = "0";
	varib = document.getElementById("idposicionPagina");
	varib.value = "0";
}
function cambiarCantidadRegistro(cantidadregistro1) {
	if (cantidadregistro1 != CANTIDADREGISTRO) {
		var pos = document.getElementById("idposicionPagina");
		pos.value = "0";
		var pos1 = document.getElementById("id_CantidadRegistro");
		document.busquedaTipo.cantidadRegistro.value = cantidadregistro1;
		document.busquedaTipo.submit();
	}
}
function cambiarPagina(cantidad) {
	var pos = document.getElementById("idposicionPagina");
	pos.value = cantidad;
	document.busquedaTipo.submit();
}
function abrirReporte(context, id) {
	var pop_reporte;
	var ancho = screen.availWidth - 100;
	var alto = screen.availHeight - 200;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	pop_reporte = window.open(context + "/aulavirtual/ReporteDetalle.action?idMatricula=" + id, "AulaVirtualReporte", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	pop_reporte.focus();
	return true;
}
function abrirAuditoria(context, id) {
	var pop_reporte;
	var ancho = screen.availWidth - 100;
	var alto = screen.availHeight - 200;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	pop_reporte = window.open(context + "/aulavirtual/Auditoria.action?idMatricula=" + id, "AulaVirtualReporte", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	pop_reporte.focus();
	return true;
}
function crearReportePDF() {
	var sel_matri = document.getElementById("reportePDF_matri");
	var sel_roles = document.getElementById("reportePDF_roles");
	sel_matri.length=0;
	sel_roles.length=0;
	var divs = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
	for (var u = 0; u < divs.length; u = u + 1) {
		if (divs[u].type == "checkbox" && divs[u].checked == true && divs[u].id != "abcdefgh") {
			agregarOpcion(sel_matri, divs[u].value, divs[u].value);
			agregarOpcion(sel_roles, divs[u].name, divs[u].name);
		}
	}
	selecionarTodos(sel_matri);
	selecionarTodos(sel_roles);
	if(sel_matri.length > 0){
		document.reportePDF.submit();
	}else{
		alert("Debe seleccionar al menos un estudiante");
	}
}
function agregarOpcion(theSel, theText, theValue) {
	var newOpt = new Option(theText, theValue);
	var selLength = theSel.length;
	theSel.options[selLength] = newOpt;
}
function selecionarTodos(theSel) {
	var selLength = theSel.length;
	var i;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		theSel.options[i].selected = true;
	}
}
//Auditoria TV
function searchAuditoria(url){
	var name = 'TecsupVirtual';
	var width = 800;
	var height = screen.height-200;
	var x = (screen.width-width)/2;
	var y = (screen.height-height)/2;	
	var PopUp =window.open('', name, "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
	var form = xGetElementById('tecsupVirtual');
	form.target=name;
	form.action = url;
	form.submit();
	PopUp.focus();
}

function validarAuditoria(){
	var form = xGetElementById('tecsupVirtual');
	if(form.busquedaFecha1.value.trim() === '')
		return false;
	if(form.busquedaFecha2.value.trim() === '')
		return false;
	if(form.busquedaPeriodo.selectedIndex === 0){
		alert("Selecione un periodo.");
		return false;
	}
	return true;
}

function validarReporte(){
	var form = xGetElementById('tecsupVirtual');
	if(form.busquedaPeriodo.selectedIndex === 0){
		alert("Selecione un periodo.");
		return false;
	}
	return true;
}