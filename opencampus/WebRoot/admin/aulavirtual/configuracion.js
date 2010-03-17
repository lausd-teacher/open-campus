
function displayModificarPeriodo(btn){
	xChangeDisplay('form_periodo');
	if($(btn).getValue() == "Ocultar")
		$(btn).setValue("Modificar");
	else
		$(btn).setValue("Ocultar");
}

function displayModificarPeriodoPersonalizado(sel){
	if(sel.value == "0"){
		$('form_nuevo_periodo1').show();
		$('form_nuevo_periodo2').show();
	}else{
		$('form_nuevo_periodo1').hide();
		$('form_nuevo_periodo2').hide();
	}
}

function validarConfiguracion(form){
	if(form.idperiodo.value == '0'){
		if(form.finicio.value == '' || form.ffin.value == ''){
			alert('Debe establecer las fechas para para la presente ficha');
			return false;
		}
		if(fecha1MayorFecha2(form.finicio.value,form.ffin.value)){
			alert('La fecha de fin debe ser posteior a la fecha de inicio.');
			return false;
		}
	}
	if(!window.confirm("¿Está seguro de los cambios que desea realizar?")){
		return false;
	}
	return true;
}


//----------------- COMMON ------------------------------------------------------------

function fecha1MayorFecha2(fecha1, fecha2) {
	if (fecha1 == null || fecha2 == null) {
		return false;
	} else {
		f1 = fecha1.split("-");
		f2 = fecha2.split("-");
		d1 = parseInt(f1[0], 10);
		m1 = parseInt(f1[1], 10);
		a1 = parseInt(f1[2], 10);
		d2 = parseInt(f2[0], 10);
		m2 = parseInt(f2[1], 10);
		a2 = parseInt(f2[2], 10);
		if (a1 > a2) {
			return true;
		}
		if (a1 == a2) {
			if (m1 > m2) {
				return true;
			}
			if ((m1 == m2) && (d1 > d2)) {
				return true;
			}
			return false;
		}
		return false;
	}
	return false;
}




//-------------------------------------------------------------- OLD ------------------------------------------------------------

function conf_div_periodo(valorTF) {
	var js_tr_periodo_cambiar = document.getElementById("tr_periodo_cambiar");
	var js_tr_periodo_accion1 = document.getElementById("tr_periodo_accion1");
	var js_tr_periodo_accion2 = document.getElementById("tr_periodo_accion2");
	var js_tr_periodo_boton = document.getElementById("tr_periodo_boton");
	if (valorTF) {
		js_tr_periodo_cambiar.style.display = "none";
		js_tr_periodo_accion1.style.display = "";
		js_tr_periodo_accion2.style.display = "";
		js_tr_periodo_boton.style.display = "";
		js_tr_periodo_cambiar.style.visibility = "hidden";
		js_tr_periodo_accion1.style.visibility = "visible";
		js_tr_periodo_accion2.style.visibility = "visible";
		js_tr_periodo_boton.style.visibility = "visible";
		js_tr_periodo_accion2.getElementsByTagName("select")[0].options[0].selected = true;
	} else {
		var span_error = document.getElementById("span_error");
		span_error.innerHTML = "";
		document.getElementById("span_error").className = "";
		js_tr_periodo_cambiar.style.display = "";
		js_tr_periodo_accion1.style.display = "none";
		js_tr_periodo_accion2.style.display = "none";
		js_tr_periodo_boton.style.display = "none";
		js_tr_periodo_cambiar.style.visibility = "visible";
		js_tr_periodo_accion1.style.visibility = "hidden";
		js_tr_periodo_accion2.style.visibility = "hidden";
		js_tr_periodo_boton.style.visibility = "hidden";
		var js_sede = document.getElementById("span_sede");
		var js_fecha1 = document.getElementById("span_fecha1");
		var js_fecha2 = document.getElementById("span_fecha2");
		var js_edicion = document.getElementById("span_edicion");
		var js_revision = document.getElementById("span_revision");
		js_sede.innerHTML = sessionSEDE;
		js_fecha1.innerHTML = sessionFECHA1;
		js_fecha2.innerHTML = sessionFECHA2;
		js_edicion.innerHTML = sessionDIA1;
		js_revision.innerHTML = sessionDIA2;
		js_sede.className = "";
		js_fecha1.className = "";
		js_fecha2.className = "";
		js_edicion.className = "";
		js_revision.className = "";
	}
}
function conf_cambiar_periodo(tip_selec) {
	var span_error = document.getElementById("span_error");
	span_error.innerHTML = "";
	var nuevo_ver_periodo;
	var selLength = tip_selec.length;
	var i;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (tip_selec.options[i].selected === true) {
			nuevo_ver_periodo = tip_selec.options[i].value;
		}
	}
	var js_sede = document.getElementById("span_sede");
	var js_fecha1 = document.getElementById("span_fecha1");
	var js_fecha2 = document.getElementById("span_fecha2");
	var js_edicion = document.getElementById("span_edicion");
	var js_revision = document.getElementById("span_revision");
	if (nuevo_ver_periodo != 0) {
		for (i = 0; i < array_periodo.length; i++) {
			if (array_periodo[i].id == nuevo_ver_periodo) {
				js_sede.innerHTML = array_periodo[i].sede;
				js_fecha1.innerHTML = array_periodo[i].fecha1;
				js_fecha2.innerHTML = array_periodo[i].fecha2;
				js_edicion.innerHTML = array_periodo[i].diaEdiccion;
				js_revision.innerHTML = array_periodo[i].diaRevision;
				js_sede.className = "link_rojo_bold";
				js_fecha1.className = "link_rojo_bold";
				js_fecha2.className = "link_rojo_bold";
				js_edicion.className = "link_rojo_bold";
				js_revision.className = "link_rojo_bold";
				break;
			}
		}
	} else {
		js_sede.innerHTML = "<select id=\"form_sede\"><option selected=\"selected\" value=\"L\">Lima</option><option value=\"A\">Arequipa</option><option value=\"T\">Trujillo</option></select>";
		js_fecha1.innerHTML = "<input id=\"form_fechaInicio\"  size=\"8\" readonly value=\"\" class=\"form_text\" onchange=\"nuevosValoresPeriodo(this);\">";
		js_fecha2.innerHTML = "<input id=\"form_fechaFin\" size=\"8\" readonly value=\"\" class=\"form_text\" onchange=\"nuevosValoresPeriodo(this);\">";
		js_edicion.innerHTML = "<input id=\"form_ediccion\" size=\"2\" maxlength=\"2\" value=\"\" class=\"form_text\" onchange=\"nuevosValoresPeriodo(this);\">";
		js_revision.innerHTML = "<input id=\"form_revision\" size=\"2\" maxlength=\"2\" value=\"\" class=\"form_text\" onchange=\"nuevosValoresPeriodo(this);\">";
		Calendar.setup({inputField:"form_fechaInicio", ifFormat:"%d-%m-%Y", showsTime:false, singleClick:false, onUpdate:catcalc});
		Calendar.setup({inputField:"form_fechaFin", ifFormat:"%d-%m-%Y", showsTime:false, singleClick:false, onUpdate:catcalc});
	}
}
function nuevosValoresPeriodo(input_nuevo) {
	if (input_nuevo.value.length > 0) {
		input_nuevo.className = "form_text";
	}
}
function guardarPeriodo() {
	var messages_error = "Ingrese";
	document.getElementById("span_error").className = "";
	var periodo_nombre = document.getElementById("strong_nombre_periodo");
	periodo_nombre = periodo_nombre.innerHTML;
	var nuevo_ver_periodo;
	var nuevo_ver_periodo_nombre;
	var js_tr_periodo_accion2 = document.getElementById("tr_periodo_accion2");
	var tip_selec = js_tr_periodo_accion2.getElementsByTagName("select")[0];
	var selLength = tip_selec.length;
	var i;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (tip_selec.options[i].selected === true) {
			nuevo_ver_periodo = tip_selec.options[i].value;
			nuevo_ver_periodo_nombre = tip_selec.options[i].innerHTML.trim();
		}
	}
	var cantidadErrores = 0;
	if (nuevo_ver_periodo == 0) {
		var er_cp = /([0-9]|^)$/;
		var input_ahora = document.getElementById("form_fechaInicio");
		input_ahora.className = "form_text";
		if (input_ahora.value.length == 0) {
			cantidadErrores++;
			input_ahora.className = "form_text_selecionado";
		}
		var input_ahora1 = document.getElementById("form_fechaFin");
		input_ahora1.className = "form_text";
		if (input_ahora1.value.length == 0) {
			cantidadErrores++;
			input_ahora1.className = "form_text_selecionado";
		}
		if (cantidadErrores == 0 && !fecha1MayorFecha2(input_ahora1.value, input_ahora.value)) {
			cantidadErrores++;
			input_ahora1.className = "form_text_selecionado";
			input_ahora.className = "form_text_selecionado";
		}
		input_ahora = document.getElementById("form_ediccion");
		input_ahora.className = "form_text";
		if (input_ahora.value.length == 0 || !er_cp.test(input_ahora.value) || input_ahora.value == 0) {
			cantidadErrores++;
			input_ahora.className = "form_text_selecionado";
		}
		input_ahora = document.getElementById("form_revision");
		input_ahora.className = "form_text";
		if (input_ahora.value.length == 0 || !er_cp.test(input_ahora.value) || input_ahora.value == 0) {
			cantidadErrores++;
			input_ahora.className = "form_text_selecionado";
		}
		if (cantidadErrores != 0) {
			document.getElementById("span_error").innerHTML = "Verifique los campos amarillos.";
			document.getElementById("span_error").className = "configuracion_error";
		}
	}
	if (cantidadErrores == 0 && window.confirm("Ud. va a cambiar un periodo " + periodo_nombre.trim() + " por un " + nuevo_ver_periodo_nombre + ".")) {
		var cadena;
		if (nuevo_ver_periodo != 0) {
			cadena = "tipo=0&idPeriodo=" + nuevo_ver_periodo;
		} else {
			var var_sede;
			var tip_selec = document.getElementById("span_sede").getElementsByTagName("select")[0];
			var selLength = tip_selec.length;
			var i;
			for (i = selLength - 1; i >= 0; i = i - 1) {
				if (tip_selec.options[i].selected === true) {
					var_sede = tip_selec.options[i].value;
					break;
				}
			}
			cadena = "sede=" + var_sede + "&tipo=1&fecha1=" + document.getElementById("form_fechaInicio").value + "&fecha2=" + document.getElementById("form_fechaFin").value + "&diasEdiccion=" + document.getElementById("form_ediccion").value + "&diasRevision=" + document.getElementById("form_revision").value;
		}
		ajax = nuevoAjax();
		ajax.open("POST", xGetContextPath() + "/admin/aulavirtual/GuardarConfiguracion.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send(cadena);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				if (ajax.responseText === "1") {
					document.location = xGetContextPath() + "/admin/aulavirtual/Configurar.action";
				} else {
					document.getElementById("span_error").innerHTML = "Intentelo en breves minutos.";
					document.getElementById("span_error").className = "configuracion_error";
				}
			}
		};
	}
}

function nuevoAjax() {
	var xmlhttp = false;
	try {
		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	}
	catch (e) {
		try {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		catch (E) {
			xmlhttp = false;
		}
	}
	if (!xmlhttp && typeof XMLHttpRequest != "undefined") {
		xmlhttp = new XMLHttpRequest();
	}
	return xmlhttp;
}

