
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
function buscarSecDoc() {
	var busFam = document.getElementById("form_busquedaFamilia");
	var busFamInsert = document.getElementById("idbusquedaFamilia");
	var selLength = busFam.length;
	var i;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (busFam.options[i].selected == true) {
			busFamInsert.value = busFam.options[i].value;
		}
	}
	var busPer = document.getElementById("form_busquedaPeriodo");
	var busPerInsert = document.getElementById("idbusquedaPeriodo");
	selLength = busPer.length;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (busPer.options[i].selected == true) {
			busPerInsert.value = busPer.options[i].value;
		}
	}
	var busSede = document.getElementById("form_busquedaSede");
	var busSedeInsert = document.getElementById("idbusquedaSede");
	selLength = busSede.length;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (busSede.options[i].selected == true) {
			busSedeInsert.value = busSede.options[i].value;
		}
	}
	var busFecha = document.getElementById("form_fechaInicio1");
	var busFechaInsert = document.getElementById("idbusquedaFecha1");
	busFechaInsert.value = busFecha.value;
	busFecha = document.getElementById("form_fechaInicio2");
	busFechaInsert = document.getElementById("idbusquedaFecha2");
	busFechaInsert.value = busFecha.value;
	document.busquedaSecDoc.submit();
}
function limpiarCheckBox() {
	var items = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
	for (var i = 0; i < items.length; i = i + 1) {
		if (items[i].type == "checkbox") {
			items[i].checked = false;
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
			divs[i].style.backgroundColor = "#FFFFFF";
		}
	}
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
					tr_tds[k].style.backgroundColor = "#FFFFFF";
				}
			}
		}
	}
}
function selecionarMatriculas() {
	var array_matri = new Array();
	try {
		var divs = document.getElementById("todos_items_busqueda");
		divs = divs.getElementsByTagName("INPUT");
		var temp;
		for (var i = 0; i < divs.length; i = i + 1) {
			if (divs[i].type == "checkbox" && divs[i]["id"].substring(0, 6) == "ficha_" && divs[i].checked == true) {
				array_matri.push(divs[i].value);
			}
		}
	}
	catch (e) {
	}
	return array_matri;
}
var impresion;
var ver_excel;
var array_matriculas;
function imprimirFichas() {
	array_matriculas = selecionarMatriculas();
	if(array_matriculas.length !== 0){
		var select_matri = document.getElementById("matriculas_imprimir");
		select_matri.length = 0;
		for (var i = 0; i < array_matriculas.length; i = i + 1) {
			agregarOpcion(select_matri, array_matriculas[i], array_matriculas[i]);
		}
		selecionarTodos(select_matri);
		document.matricula_imprimir.submit();
	}else{
		alert('Recorcholis, debe de seleccionar al menos un registro.');
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
function imprimirExcel() {
	array_matriculas = selecionarMatriculas();
	if(array_matriculas.length !== 0){
		var select_matri = document.getElementById("matriculas_excel");
		select_matri.length = 0;
		for (var i = 0; i < array_matriculas.length; i = i + 1) {
			agregarOpcion(select_matri, array_matriculas[i], array_matriculas[i]);
		}
		selecionarTodos(select_matri);
		document.matricula_excel.submit();
	}else{
		alert('Recorcholis, debe de seleccionar al menos un registro.');
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
						js_td[j].style.backgroundColor = "#FFFFFF";
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

