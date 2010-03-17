
function buscarSimpleKey(e) {
	var key;
	if (window.event) {
		key = window.event.keyCode;     //IE
	} else {
		key = e.which;     //firefox
	}
	if ("13" == key) {
		busqueda_simple();
	}
}

function buscarDosKey(e) {
	var key;
	if (window.event) {
		key = window.event.keyCode;     //IE
	} else {
		key = e.which;     //firefox
	}
	if ("13" == key) {
		busquedaDos();
	}
}

function seleccionarOtraForma(s_select){
	var selLength = s_select.length;
	var i;
	var cantidadIngreso=0;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (s_select.options[i].value =='-1' && s_select.options[i].selected == true) {
			cantidadIngreso++;
			
		}
	}
	if(0==cantidadIngreso){
		
	}
}

function verFichaMenu(e, idMenu) {
	if (ETIQUETAUDS != TIPOETIQUETA && ETIQUETAUDS_HISTORICO != TIPOETIQUETA) {
		ver_menu(idMenu);
		var panel = document.getElementById(idMenu);
		if (navegador) {
			panel.style.marginLeft = "-25px";
		} else {
			panel.style.marginLeft = "50px";
			panel.style.marginTop = "3px";
		}
	}
}
function ingresarFecha() {
	var date2 = new Date();
	var field = document.getElementById("form_fechaInicio1");
	field.value = date2.print("%d-%m-%Y");
	var time = date2.getTime();
	time += (4) * Date.WEEK;
	date2 = new Date(time);
	field = document.getElementById("form_fechaInicio2");
	field.value = date2.print("%d-%m-%Y");
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
function selecionando(TR, valor) {
	if (valor) {
		TR.style.background = "#EAEBED";
	} else {
		TR.style.background = "";
	}
}
function obtenerContextPath() {
	var context = "";
	var loct = document.location;
	var array = loct.href.split("/");
	try {
		context = array[0] + "/" + array[1] + "/" + array[2] + "/" + array[3];
	}
	catch (e) {
	}
	return context;
}
function obtenerContext() {
	var context = "";
	var loct = document.location;
	var array = loct.href.split("/");
	try {
		context = "/" + array[3];
	}
	catch (e) {
	}
	return context;
}
var S_CANTIDAD_SELECIONADO = 0;
function seleccionarDIV(contenedor, check) {
	var divs = document.getElementById(contenedor).getElementsByTagName("TD");
	for (var i = 0; i < divs.length; i = i + 1) {
		if (check.checked == true) {
			divs[i].style.backgroundColor = "#fce57c";
		} else {
			divs[i].style.backgroundColor = "#FFFFFF";
		}
	}
	if (check.checked == true) {
		S_CANTIDAD_SELECIONADO = S_CANTIDAD_SELECIONADO + 1;
	} else {
		S_CANTIDAD_SELECIONADO = S_CANTIDAD_SELECIONADO - 1;
	}
	if (S_CANTIDAD_SELECIONADO <= 0) {
		S_CANTIDAD_SELECIONADO = 0;
		mostrarMensajeErrorCompleto("Ha dejado de seleccionar alg&uacute;n item de esta pesta\xf1a.", 0);
	} else {
		var s_cant = 0;
		var items = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
		for (var i = 0; i < items.length; i = i + 1) {
			if (items[i].type == "checkbox") {
				s_cant = s_cant + 1;
			}
		}
		if (S_CANTIDAD_SELECIONADO == s_cant) {
			mostrarMensajeErrorCompleto("Ha seleccionado todos los items de esta pesta\xf1a.", 0);
		} else {
			mostrarMensajeErrorCompleto("Ha seleccionado " + S_CANTIDAD_SELECIONADO + " item(s) de esta pesta\xf1a.", 0);
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
	var divs = document.getElementById("todos_items_busqueda").getElementsByTagName("TD");
	for (var i = 0; i < divs.length; i = i + 1) {
		if (valor == true) {
			divs[i].style.backgroundColor = "#fce57c";
		} else {
			divs[i].style.backgroundColor = "#FFFFFF";
		}
	}
	var s_cant = 0;
	var items = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
	for (var i = 0; i < items.length; i = i + 1) {
		if (items[i].type == "checkbox") {
			items[i].checked = valor;
			s_cant = s_cant + 1;
		}
	}
	if (valor == true) {
		S_CANTIDAD_SELECIONADO = s_cant;
		mostrarMensajeErrorCompleto("Ha seleccionado todos los items de esta pesta\xf1a.", 0);
	} else {
		S_CANTIDAD_SELECIONADO = 0;
		mostrarMensajeErrorCompleto("Ha dejado de seleccionar los items de esta pesta\xf1a.", 0);
	}
}
function crearVirtual() {
	if (ETIQUETAUDS == TIPOETIQUETA) {
		document.getElementById("input_crear").value = "";
		document.getElementById("select_crear").options.length = 0;
		var cantidad = 0;
		var inputs = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
		for (var i = 0; i < inputs.length; i = i + 1) {
			if (inputs[i].type == "checkbox" && inputs[i].checked == true) {
				agregarOpcion(document.getElementById("select_crear"), inputs[i].value, inputs[i].value);
				cantidad = cantidad + 1;
			}
		}
		var mensaje = null;
		if (cantidad == 0) {
			mostrarMensajeErrorCompleto("Upss debe de seleccionar un item como m&iacute;nimo.", 1);
			return;
		} else {
			if (cantidad == 1) {
				mensaje = window.confirm("Desea crear un curso virtual?");
			} else {
				mensaje = window.confirm("Desea crear " + cantidad + " cursos virtuales?");
			}
		}
		if (mensaje == true) {
			selecionarTodos(document.getElementById("select_crear"));
			document.getElementById("input_crear").value = "1";
			document.crear.submit();
		} else {
			mostrarMensajeErrorCompleto("Ha cancelado la creaci&oacute;n del curso.", 1);
			document.getElementById("select_crear").options.length = 0;
		}
	}
}
function crearVirtualAgrupado() {
	if (ETIQUETAUDS == TIPOETIQUETA) {
		document.getElementById("input_crear").value = "";
		document.getElementById("select_crear").options.length = 0;
		var cantidad = 0;
		var inputs = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
		for (var i = 0; i < inputs.length; i = i + 1) {
			if (inputs[i].type == "checkbox" && inputs[i].checked == true) {
				agregarOpcion(document.getElementById("select_crear"), inputs[i].value, inputs[i].value);
				cantidad = cantidad + 1;
			}
		}
		var mensaje = null;
		if (cantidad == 0) {
			mostrarMensajeErrorCompleto("Upss debe de seleccionar m&acute;s de un item.", 1);
			return;
		} else {
			mensaje = window.confirm("Desea crear un curso virtual con " + cantidad + " cursos?");
		}
		if (mensaje == true) {
			mostrarMensajeErrorCompleto(".", 0);
			selecionarTodos(document.getElementById("select_crear"));
			document.getElementById("input_crear").value = "2";
			mostrarMensajeErrorCompleto("Se atendiendo su petici&oacute;n.", 0);
			document.crear.submit();
		} else {
			mostrarMensajeErrorCompleto("Ha cancelado la creaci&oacute;n del curso.", 1);
			document.getElementById("select_crear").options.length = 0;
		}
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
function entrarAulaVirtual(id, nombre) {
	if(isDirector && ETIQUETA_HISTORICO == TIPOETIQUETA) {
		mostrarMensajeErrorCompleto("Ha ingresado al aula virtual de " + nombre + ".", 0);
		document.location = obtenerContext() + "/admin/AulaVirtual.action?id=" + id;
	}
	else if (isADMIN || isSoporte) {
		if (ETIQUETAUDS != TIPOETIQUETA && ETIQUETAUDS_HISTORICO != TIPOETIQUETA) {
			mostrarMensajeErrorCompleto("Ha ingresado al aula virtual de " + nombre + ".", 0);
			document.location = obtenerContext() + "/admin/AulaVirtual.action?id=" + id;
		}
	} else {
		mostrarMensajeErrorCompleto("Ha ingresado al aula virtual de " + nombre + ".", 0);
		document.location = obtenerContext() + "/aulavirtual/AulaVirtual.action?id=" + id;
	}
}
function entrarNuevoAulaVirtual(id, nombre) {
	if(isDirector && ETIQUETA_HISTORICO == TIPOETIQUETA) {
		mostrarMensajeErrorCompleto("Ha ingresado al aula virtual de " + nombre + ".", 0);
		window.open(obtenerContext() + "/admin/AulaVirtual.action?id=" + id).focus();
	}
	else if (isADMIN || isSoporte) {
		if (ETIQUETAUDS != TIPOETIQUETA && ETIQUETAUDS_HISTORICO != TIPOETIQUETA) {
			mostrarMensajeErrorCompleto("Ha abierto una nueva ventana para el aula virtual de " + nombre + ".", 0);
			window.open(obtenerContext() + "/admin/AulaVirtual.action?id=" + id).focus();
		}
	} else {
		mostrarMensajeErrorCompleto("Ha abierto una nueva ventana para el aula virtual de " + nombre + ".", 0);
		window.open(obtenerContext() + "/aulavirtual/AulaVirtual.action?id=" + id).focus();
	}
}
function ocultarPanel(panelUNO, panelDOS) {
	try {
		panelUNO.style.display = "none";
		panelDOS.style.display = "block";
		var tipo = document.getElementById("idbusquedaTipo");
		tipo = tipo.value;
		mostrarMensajeErrorCompleto((tipo != 1) ? "Bien ahora puede hacer una busqueda m&aacute;s compleja." : "Su busqueda se ha reducido a un criterio.", (tipo != 1) ? 0 : 1);
		document.getElementById("idbusquedaTipo").value = (tipo != 0) ? 0 : 1;
	}
	catch (e) {
	}
}
function verNombres(nombre, img) {
	xInnerHtml("nombres", nombre);
	xMoveTo("nombres", xPageX(img) + 5, xPageY(img) + 25);
	document.getElementById("nombres").style.display = "block";
	xShow("nombres");
}
function ocultarNombres() {
	xHide("nombres");
}
function setImportante(img, valor, ficha, nombre) {
	var valorinput = document.getElementById(valor);
	var posiblevalor = (valorinput.value != "0") ? "0" : "1";
	var ajax = nuevoAjax();
	ajax.open("POST", obtenerContext() + "/admin/ficha/Importante.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("estado=" + posiblevalor + "&idFicha=" + ficha);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			var numero = ajax.responseText;
			if ("0" === numero) {
				window.alert("Umm.. error al enviar la ultima peticion");
			} else {
				valorinput = document.getElementById(valor);
				valorinput.value = posiblevalor;
				img.src = (posiblevalor != "0") ? obtenerContext() + "/img/icon_importante_y.gif" : obtenerContext() + "/img/icon_importante_n.gif";
				mostrarMensajeErrorCompleto((posiblevalor != "0") ? "Se ha etiquetado el curso " + nombre + " como importante." : "Se ha removido como importante el curso " + nombre + ".", (posiblevalor != "0") ? 0 : 1);
			}
		}
	};
}
function setEstado(img, valor, ficha, nombre) {
	var valorinput = document.getElementById(valor);
	var posiblevalor = (valorinput.value != "0") ? "0" : "1";
	var ajax = nuevoAjax();
	ajax.open("POST", obtenerContext() + "/admin/ficha/Estado.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("estado=" + posiblevalor + "&idFicha=" + ficha);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			var numero = ajax.responseText;
			if ("0" === numero) {
				window.alert("Umm.. error al enviar la ultima peticion");
			} else {
				valorinput = document.getElementById(valor);
				valorinput.value = posiblevalor;
				img.src = (posiblevalor != "0") ? obtenerContext() + "/img/activo.gif" : obtenerContext() + "/img/desactivo.gif";
				mostrarMensajeErrorCompleto((posiblevalor != "0") ? "Se ha activado el curso " + nombre + "." : "Se ha desactivado el curso " + nombre + ".", (posiblevalor != "0") ? 0 : 1);
			}
		}
	};
}
function setPapelera() {
	document.getElementById("select_papelera").options.length = 0;
	var cantidad = 0;
	var inputs = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
	for (var i = 0; i < inputs.length; i = i + 1) {
		if (inputs[i].type == "checkbox" && inputs[i].checked == true) {
			agregarOpcion(document.getElementById("select_papelera"), inputs[i].value, inputs[i].value);
			cantidad = cantidad + 1;
		}
	}
	var mensaje = null;
	if (cantidad == 0) {
		mensaje = window.alert("No ha seleccionado ning\xfan item");
		return;
	} else {
		mensaje = window.confirm("Desea enviar a papelera " + cantidad + " cursos?");
	}
	if (mensaje == true) {
		selecionarTodos(document.getElementById("select_papelera"));
		document.papelera.submit();
	} else {
		document.getElementById("select_papelera").options.length = 0;
	}
}
function setSincronizar() {
	document.getElementById("select_sincronizar").options.length = 0;
	var cantidad = 0;
	var inputs = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
	for (var i = 0; i < inputs.length; i = i + 1) {
		if (inputs[i].type == "checkbox" && inputs[i].checked == true) {
			agregarOpcion(document.getElementById("select_sincronizar"), inputs[i].value, inputs[i].value);
			cantidad = cantidad + 1;
		}
	}
	var mensaje = null;
	if (cantidad == 0) {
		mensaje = window.alert("No ha seleccionado ning\xfan item");
		return;
	} else {
		mensaje = window.confirm("Desea sincronizar " + cantidad + " cursos?");
	}
	if (mensaje == true) {
		selecionarTodos(document.getElementById("select_sincronizar"));
		document.sincronizar.submit();
	} else {
		document.getElementById("select_sincronizar").options.length = 0;
	}
}
function setConstancia() {
	/*document.getElementById("select_constancia").options.length = 0;
	var cantidad = 0;
	var inputs = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
	for (var i = 0; i < inputs.length; i = i + 1) {
		if (inputs[i].type == "checkbox" && inputs[i].checked == true) {
			agregarOpcion(document.getElementById("select_constancia"), inputs[i].value, inputs[i].value);
			cantidad = cantidad + 1;
		}
	}
	var mensaje = null;
	if (cantidad == 0) {
		mensaje = window.alert("No ha seleccionado ning\xfan item");
		return;
	} else {
		mensaje = window.confirm("Desea enviar constancia a los participantes de " + cantidad + " cursos ?");
	}
	if (mensaje == true) {
		selecionarTodos(document.getElementById("select_constancia"));
		document.constancia.submit();
	} else {
		document.getElementById("select_constancia").options.length = 0;
	}*/
	
	agregarOpcion(document.getElementById("select_constancia"), ' 1', '1');
	selecionarTodos(document.getElementById("select_constancia"));
	
	mensaje = window.confirm("Desea enviar constancia a los participantes de todos los  cursos ?");
	if (mensaje == true) {
		document.constancia.submit();
	} 
}
function setRestaurar() {
	document.getElementById("select_restaurar").options.length = 0;
	var cantidad = 0;
	var inputs = document.getElementById("todos_items_busqueda").getElementsByTagName("INPUT");
	for (var i = 0; i < inputs.length; i = i + 1) {
		if (inputs[i].type == "checkbox" && inputs[i].checked == true) {
			agregarOpcion(document.getElementById("select_restaurar"), inputs[i].value, inputs[i].value);
			cantidad = cantidad + 1;
		}
	}
	var mensaje = null;
	if (cantidad == 0) {
		mensaje = window.alert("No ha seleccionado ning\xfan item");
		return;
	} else {
		mensaje = window.confirm("Desea enviar restaurar " + cantidad + " cursos?");
	}
	if (mensaje == true) {
		selecionarTodos(document.getElementById("select_restaurar"));
		document.restaurar.submit();
	} else {
		document.getElementById("select_restaurar").options.length = 0;
	}
}
function busqueda_simple() {
	limpiarBusqueda();
	var busNom = document.getElementById("form_busquedaNombre1");
	var busNomInsert = document.getElementById("idbusquedaNombre");
	busNomInsert.value = busNom.value;
	var tipo = document.getElementById("idbusquedaTipo");
	tipo.value = "0";
	document.busquedaTipo.submit();
}
function busquedaDos() {
	limpiarBusqueda();
	var busNom = document.getElementById("form_busquedaNombre2");
	var busNomInsert = document.getElementById("idbusquedaNombre");
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
		var busStringFor = document.getElementById("form_busquedaStringFormacion");
		var busStringForInsert = document.getElementById("idbusquedaStringFormacion");
		busStringForInsert.value = busStringFor.value;
	}
	catch (e) {
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
	var tipo = document.getElementById("idbusquedaTipo");
	tipo.value = "1";
	document.busquedaTipo.submit();
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
function limpiarBusqueda() {
	var varib = document.getElementById("idbusquedaNombre");
	varib.value = "";
	varib = document.getElementById("idbusquedaFormacion");
	varib.value = "0";
	varib = document.getElementById("idbusquedaStringFormacion");
	varib.value = "";
	varib = document.getElementById("idbusquedaDepartamento");
	varib.value = "0";
	varib = document.getElementById("idbusquedaSede");
	varib.value = "0";
	varib = document.getElementById("idbusquedaPeriodo");
	varib.value = "0";
	varib = document.getElementById("idbusquedaFecha1");
	varib.value = "";
	varib = document.getElementById("idbusquedaFecha2");
	varib.value = "";
	varib = document.getElementById("idposicionPagina");
	varib.value = "0";
	varib = document.getElementById("idbusquedaTipo");
	varib.value = "0";
	varib = document.getElementById("idbusquedaExacta");
	varib.value = "0";
	varib = document.getElementById("idbusquedaCiclo");
	varib.value = "0";
}
function cambiarEtiqueta(idetiqueta) {
	limpiarBusqueda();
	var id = document.getElementById("idEtiqueta");
	id.value = idetiqueta;
	document.busquedaTipo.submit();
}
function resaltar(tipo) {
	tipo.style.background = "#EAEBED";
}
function desresaltar(tipo) {
	tipo.style.background = "";
}
function mostrarMensajeActivar(idficha) {
	var topo = document.getElementById(idficha + "estado");
	if (topo.value == "1") {
		return "Desactivar Curso";
	} else {
		return "Activar Curso";
	}
}
function mostrarMensajeErrorCompleto(texto_mensaje, tipo_mensaje) {
	var b_mensaje = document.getElementById("tr_mensajes_mensaje");
	b_mensaje.innerHTML = texto_mensaje;
	mostrarMensajeError(tipo_mensaje);
}
function ocultarMensajeError() {
	var tr_contenedor = document.getElementById("tr_mensajes_advertencia");
	tr_contenedor.style.visibility = "hidden";
	tr_contenedor.style.display = "none";
}
function mostrarMensajeError(tipo_mensaje) {
	var tr_contenedor = document.getElementById("tr_mensajes_advertencia");
	tr_contenedor.style.visibility = "visible";
	tr_contenedor.style.display = "";
	var b_mensaje = document.getElementById("tr_mensajes_mensaje");
	if (tipo_mensaje == "0") {
		b_mensaje.style.color = "black";
		document.getElementById("td_1_mensaje").style.backgroundImage = "url('../../img/punto_amarillo.png')";
		document.getElementById("td_3_mensaje").style.backgroundImage = "url('../../img/punto_amarillo.png')";
		document.getElementById("td_7_mensaje").style.backgroundImage = "url('../../img/punto_amarillo.png')";
		document.getElementById("td_9_mensaje").style.backgroundImage = "url('../../img/punto_amarillo.png')";
		document.getElementById("td_2_mensaje").style.backgroundColor = "#FFF1A8";
		document.getElementById("td_4_mensaje").style.backgroundColor = "#FFF1A8";
		document.getElementById("td_5_mensaje").style.backgroundColor = "#FFF1A8";
		document.getElementById("td_6_mensaje").style.backgroundColor = "#FFF1A8";
		document.getElementById("td_8_mensaje").style.backgroundColor = "#FFF1A8";
	} else {
		b_mensaje.style.color = "white";
		document.getElementById("td_1_mensaje").style.backgroundImage = "url('../../img/punto_rojo.png')";
		document.getElementById("td_3_mensaje").style.backgroundImage = "url('../../img/punto_rojo.png')";
		document.getElementById("td_7_mensaje").style.backgroundImage = "url('../../img/punto_rojo.png')";
		document.getElementById("td_9_mensaje").style.backgroundImage = "url('../../img/punto_rojo.png')";
		document.getElementById("td_2_mensaje").style.backgroundColor = "#CC0000";
		document.getElementById("td_4_mensaje").style.backgroundColor = "#CC0000";
		document.getElementById("td_5_mensaje").style.backgroundColor = "#CC0000";
		document.getElementById("td_6_mensaje").style.backgroundColor = "#CC0000";
		document.getElementById("td_8_mensaje").style.backgroundColor = "#CC0000";
	}
}

