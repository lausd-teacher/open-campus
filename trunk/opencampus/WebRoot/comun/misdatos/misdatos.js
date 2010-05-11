
function mostrarPassword() {
	var boton = document.getElementById("botonPassword");
	var panel = document.getElementById("panelPassword");
	var clave1 = document.getElementById("clave1");
	var clave2 = document.getElementById("clave2");
	var clave3 = document.getElementById("clave3");
	clave1.value = "";
	clave2.value = "";
	clave3.value = "";
	panel.style.display = (panel.style.display != "block") ? "block" : "none";
	boton.innerHTML = (panel.style.display != "block") ? "Cambiar" : "Cancelar";
}
function cambiarClave(URL, boton) {
	boton.disabled = true;
	var clave1 = document.getElementById("clave1");
	var clave2 = document.getElementById("clave2");
	var clave3 = document.getElementById("clave3");
		var respuesta = document.getElementById("respuestaClave");
		var key = hex_md5(clave1.value + clave2.value + clave3.value).toUpperCase();
		ajax = nuevoAjax();
		ajax.open("POST", URL, true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send("clave1=" + clave1.value + "&clave2=" + clave2.value + "&clave3=" + clave3.value + "&key=" + key);
		ajax.onreadystatechange = function () {
			if (ajax.readyState == 4) {
				var estado = ajax.responseText;
				if ("0" === estado) {
					respuesta.innerHTML = "Cambio correcto";
				} else {
					if ("-1" === estado) {
						respuesta.innerHTML = "Nuevo corto";
					} else {
						if ("-2" === estado) {
							respuesta.innerHTML = "Clave muy simple";
						} else {
							if ("-3" === estado) {
								respuesta.innerHTML = "Clave repetido";
							} else {
								respuesta.innerHTML = "Error";
							}
						}
					}
				}
				boton.disabled = false;
				metodo = true;
				mostrarPassword();
			}
		};
	
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
function nuevoCorreo() {
	var nuevo_correo = window.prompt("Ingrese el nuevo correo : ", "");
	var tabla = document.getElementById("correos_tabla").getElementsByTagName("TBODY")[0];
	var row = document.createElement("TR");
	var td1 = document.createElement("TD");
	td1.appendChild(document.createTextNode(nuevo_correo));
	var td2 = document.createElement("TD");
	td2.setAttribute("align", "right");
	td2.innerHTML = "<span style=\"cursor:pointer;text-decoration: underline;color: blue;  \">Editar</span>";
	var td3 = document.createElement("TD");
	td3.setAttribute("align", "right");
	td3.innerHTML = "<span style=\"cursor:pointer;color: blue; text-decoration: underline; \">Remover</span> ";
	row.appendChild(td1);
	row.appendChild(td2);
	row.appendChild(td3);
	tabla.appendChild(row);
}
function verPanelCambioPassword() {
	document.getElementById("div_reinicio").style.visibility = "visible";
	document.getElementById("form_div_reinicio").style.visibility = "visible";
	document.getElementById("clave_nivel_div").style.visibility = "visible";
	xMoveTo("clave_nivel_div", xPageX("clave_nivel_img"), xPageY("clave_nivel_img"));
}

function verPanelCambioCorreo() {
	document.getElementById("div_reinicio").style.visibility = "visible";
	document.getElementById("form_div_correo").style.visibility = "visible";	
}
function ocultarPanelCambioCorreo() {
	document.getElementById("div_reinicio").style.visibility = "hidden";
	document.getElementById("form_div_correo").style.visibility = "hidden";	
}
function ocultarPanelCambioPassword() {
	document.getElementById("div_reinicio").style.visibility = "hidden";
	document.getElementById("form_div_reinicio").style.visibility = "hidden";
	document.getElementById("clave_nivel_div").style.visibility = "hidden";
	document.getElementById("clave_nivel_div").style.width = "120px";
	xMoveTo("clave_nivel_div", xPageX("clave_nivel_img"), xPageY("clave_nivel_img"));
	var pass1 = document.getElementById("form_pass1_reinicio");
	pass1.value = "";
	var pass2 = document.getElementById("form_pass2_reinicio");
	pass2.value = "";
	var pass3 = document.getElementById("form_pass3_reinicio");
	pass3.value = "";
}
function setDIVs() {
	var s_reinicio = document.getElementById("div_reinicio");
	var x_width = xWidth("contenedor");
	try {
		s_reinicio.setAttribute("style", "width:" + xWidth("contenedor") + "px;height:" + xHeight("contenedor") + "px;-moz-opacity:.55;background-color:#EAEBED;position:absolute;top:" + xPageY("contenedor") + "px");
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
	s_reinicio.style.height = (xHeight("contenedor") + 5) + "px";
	s_reinicio.style.position = "absolute";
	s_reinicio.style.top = xPageY("contenedor") + "px";
	s_reinicio.style.left = xPageX("contenedor") + "px";
	s_reinicio.style.zIndex = "1";
	var div_mensaje = document.getElementById("form_div_reinicio");
	var left_div_mensaje = xPageX("div_reinicio") + ((xWidth("div_reinicio") - 300) / 2);
	var top_div_mensaje = xPageY("div_reinicio") + ((xHeight("div_reinicio") - xHeight("form_div_reinicio")) / 2);
	try {
		div_mensaje.setAttribute("style", "-moz-opacity:1;background-color:#FFFFFF;border:0px solid #000000;position:absolute;z-index: 1;left:" + left_div_mensaje + "px;top:" + top_div_mensaje + "px;");
	}
	catch (e) {
	}
	div_mensaje.style.background = "#FFFFFF";
	div_mensaje.style.filter = "alpha(opacity=100)";
	div_mensaje.style.left = left_div_mensaje + "px";
	div_mensaje.style.top = top_div_mensaje + "px";
	div_mensaje.style.position = "absolute";
	div_mensaje.style.border = "0px solid #000000";
	div_mensaje.style.zIndex = "1";
	left_div_mensaje = xPageX("div_reinicio") + ((xWidth("div_reinicio") - 450) / 2);
	top_div_mensaje = xPageY("div_reinicio") + ((xHeight("div_reinicio") - xHeight("modificarDatos")) / 2);
	var cambiar_datos = document.getElementById("modificarDatos");
	cambiar_datos.style.left = left_div_mensaje + "px";
	cambiar_datos.style.top = top_div_mensaje + "px";
	left_div_mensaje = xPageX("div_reinicio") + ((xWidth("div_reinicio") - 350) / 2);
	top_div_mensaje = xPageY("div_reinicio") + ((xHeight("div_reinicio") - xHeight("form_div_correo")) / 2);
	var cambiar_correo = document.getElementById("form_div_correo");
	cambiar_correo.style.left = left_div_mensaje + "px";
	cambiar_correo.style.top = top_div_mensaje + "px";
	s_reinicio.style.visibility = "hidden";
	div_mensaje.style.visibility = "hidden";
}

function enviarAGrabarClave(e, urls) {
	var key;
	if (window.event) {
		key = window.event.keyCode;     //IE
	} else {
		key = e.which;     //firefox
	}
	if ("13" == key) {
		passwordnuevo(urls);
	}
}
/*
function validarClave(texto) {
	var numero = passwordLevel(texto.replace(/\s+$|^\s+/g, ""));
	if (numero > 100) {
		numero = 100;
	}
	document.getElementById("clave_nivel_div").style.width = 120 - ((numero * 6) / 5) + "px";
	xMoveTo("clave_nivel_div", xPageX("clave_nivel_img") + ((numero * 6) / 5), xPageY("clave_nivel_img"));
}*/
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
/**
 * Modificar Mis Datos
 */
var old_value;
var urls;
var mail = {};
mail.editar = function (ele, url) {
	var old_value = ele.innerHTML;
	ele.innerHTML = "<textarea>" + old_value + "</textarea><br><button class=\"form_button\" onclick=\"grabar()\">Guardar</button><button class=\"form_button\" onclick=\"mail.cancelar()\">Cancelar</button>";
	$("correo").setAttribute("onclick", "");
};
mail.cancelar = function () {
	$("correo").setAttribute("onclick", "mail.editar(this,url)");
};
/*div transparente para cambiar datos*/
function verPanelCambioDatos(button) {
	//button.disabled = true;
	xShow("div_reinicio");
	xShow("modificarDatos");
}
function noVerCambioDatos(button) {
	button.disabled = false;
	xHide("div_reinicio");
	xHide("modificarDatos");
}
var oldText;
function editar() {
	oldText = "<div id=\"theMail\">" + $("theMail").innerHTML + "</div>";
	var mail = trim($("theMail").innerHTML);
	mail = mail.replace(/<BR>/gi, ", ");
	var lastComa = mail.lastIndexOf(",");
	if (trim(mail.substr(lastComa + 1)) == "") {
		mail = mail.substr(0, lastComa);
	}
	$("theMail").innerHTML = "<textarea id=\"newMail\" rows='3' cols=\"35\">" + mail + "</textarea><br><b>Separe los correos por comas.</b>";
	xShowD("button_grabar");
	xShowD("button_cancelar");
}
function cancelar() {
	$("correo").innerHTML = oldText;
	xShowD("button_editar");
	xHideD("button_grabar");
	xHideD("button_cancelar");
}


function grabar(url) {
	var email = $("newMail").value;
	old_value = email;
	var ajax = nuevoAjax();
	ajax.open("POST", url + "/CambioEmail.action", true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("email=" + email);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			var numero = ajax.responseText;
			if (numero == 1) {
				email = email.replace(/,/gi, "<BR>");
				$("theMail").innerHTML = email;
				//window.location.reload()
			}else{
				$("newMail").innerHTML = old_value;
			}
			 ocultarPanelCambioCorreo();
		}
	};
}
function borrar(eleId) {
	dwr.util._temp = dwr.util.byId(eleId);
	if (dwr.util._temp) {
		dwr.util._temp.parentNode.removeChild(dwr.util._temp);
		dwr.util._temp = null;
	}
}

