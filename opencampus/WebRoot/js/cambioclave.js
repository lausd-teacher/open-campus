function reinicioClave() {
	var s_reinicio = document.getElementById("div_reinicio");
	var x_width = xWidth("cuerpo");
	try {
		s_reinicio.setAttribute("style", "width:" + xWidth("contenedor") + "px;height:" + (xClientHeight() ) + "px;-moz-opacity:.55;background-color:#EAEBED;position:absolute;top:" + (xPageY("contenedor") - 105) + "px");
	}
	catch (e) {
	}
	try {
		s_reinicio.style.background = "#EAEBED";
	}
	catch (e) {
	}
	s_reinicio.style.filter = "alpha(opacity=55)";
	s_reinicio.style.width = (xWidth("contenedor") + 5) + "px";
	s_reinicio.style.height = (xClientHeight() -3) + "px";
	s_reinicio.style.position = "absolute";
	s_reinicio.style.top = xPageY("contenedor") + "px";
	s_reinicio.style.left = xPageX("contenedor") + "px";
	var div_mensaje = document.getElementById("form_div_reinicio");
	var left_div_mensaje = xPageX("div_reinicio") + ((xWidth("div_reinicio") - 300) / 2);
	var top_div_mensaje = xPageY("div_reinicio") + ((xHeight("div_reinicio") - xHeight("form_div_reinicio")) / 2);
	try {
		div_mensaje.setAttribute("style", "-moz-opacity:1;background-color:#FFFFFF;border:0px solid #000000;position:absolute;top:" + top_div_mensaje + "px;left:" + left_div_mensaje + "px;\tz-index: 1;");
	}
	catch (e) {
	}
	div_mensaje.style.background = "#FFFFFF";
	div_mensaje.style.filter = "alpha(opacity=100)";
	div_mensaje.style.left = left_div_mensaje + "px";
	div_mensaje.style.top = top_div_mensaje + "px";
	div_mensaje.style.position = "absolute";
	div_mensaje.style.border = "0px outset #000000";
	var pass1 = document.getElementById("form_pass1_reinicio");
	pass1.setAttribute("onKeyPress", "");
	var pass2 = document.getElementById("form_pass2_reinicio");
	pass2.setAttribute("onKeyPress", "");
	xMoveTo("clave_nivel_div", xPageX("clave_nivel_img"), xPageY("clave_nivel_img"));
	var barrita_sup = document.getElementById("clave_nivel_div");
	barrita_sup.style.visibility = "visible";
}
function passwordreinicio(urls) {
	var s_button = document.getElementById("form_button_reinicio");
	s_button.disabled = true;
	var pass1 = document.getElementById("form_pass1_reinicio");
	pass1.disabled = true;
	var pass2 = document.getElementById("form_pass2_reinicio");
	pass2.disabled = true;
	if (trim(pass1.value).length != 0 && trim(pass2.value).length != 0 && trim(pass1.value) == trim(pass2.value)) {
		var ajax = nuevoAjax();
		ajax.open("POST", urls + "/DatosClaveReinicio.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("clave=" + trim(pass1.value));
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				var numero = ajax.responseText;
				if ("0" === numero) {
					pass1.value = "";
					pass2.value = "";
					document.getElementById("div_reinicio").style.visibility = "hidden";
					document.getElementById("form_div_reinicio").style.visibility = "hidden";
				} else {
					pass1.value = "";
					pass1.disabled = false;
					pass2.disabled = false;
					s_button.disabled = false;
					var s_err = document.getElementById("form_error");
					switch (numero) {
					  case "-1":
						s_err.innerHTML = "La clave ingresada es muy corta";
						break;
					  case "-2":
						s_err.innerHTML = "La clave ingresada es muy simple";
						break;
					  case "-3":
						s_err.innerHTML = "La clave ingresada ya fue usada";
						break;
					  case "-4":
						s_err.innerHTML = "La clave no pudo ser modificada";
						break;
					}
				}
			}
		};
	} else {
		pass1.value = "";
		var s_err = document.getElementById("form_error");
		s_err.innerHTML = "Las claves ingresadas no coinciden";
		pass1.disabled = false;
		pass2.disabled = false;
		s_button.disabled = false;
	}
}
function enviarAGrabar(e, urls) {
	var key;
	if (window.event) {
		key = window.event.keyCode;     //IE
	} else {
		key = e.which;     //firefox
	}
	if ("13" == key) {
		passwordreinicio(urls);
	}
}
function disableCtrlKeyCombination(s_input, e) {
	var key;
	var isCtrl;
	if (window.event) {
		key = window.event.keyCode;     //IE
		if (window.event.ctrlKey) {
			isCtrl = true;
		} else {
			isCtrl = false;
		}
	} else {
		key = e.which;     //firefox
		if (e.ctrlKey) {
			isCtrl = true;
		} else {
			isCtrl = false;
		}
	}
	if (isCtrl) {
		if ("c" == String.fromCharCode(key).toLowerCase()) {
			return false;
		}
		if ("v" == String.fromCharCode(key).toLowerCase()) {
			s_input.value = "";
			return false;
		}
	}
	return true;
}
function validarClave(texto) {
	var numero = passwordLevel(texto.replace(/\s+$|^\s+/g, ""));
	if (numero > 100) {
		numero = 100;
	}
	document.getElementById("clave_nivel_div").style.width = 120 - ((numero * 6) / 5) + "px";
	xMoveTo("clave_nivel_div", xPageX("clave_nivel_img") + ((numero * 6) / 5), xPageY("clave_nivel_img"));
}
function passwordLevel(p) {
	var cantidad = 0;
	v1 = "aeiou1234567890";
	v2 = "AEIOUbcdfghjklmnpqrst";
	v3 = "vxyzBCDFGHJKLMNPQRST";
	v4 = "VXYZ$@#";
	for (i = 0; i < p.length; i++) {
		if (v1.indexOf(p[i]) != -1) {
			cantidad += 1;
		} else {
			if (v2.indexOf(p[i]) != -1) {
				cantidad += 2;
			} else {
				if (v3.indexOf(p[i]) != -1) {
					cantidad += 3;
				} else {
					if (v4.indexOf(p[i]) != -1) {
						cantidad += 4;
					} else {
						cantidad += 5;
					}
				}
			}
		}
	}
	cantidad *= 3;
	if (cantidad > 100) {
		l = 100;
	}
	return cantidad;
}
