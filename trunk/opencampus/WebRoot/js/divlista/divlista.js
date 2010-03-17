
var v;
var nuevaBusqueda;
var busqueda;
var ultimaBusquedaNula;
var divLista;
var inputLista;
var ultimoIdentificador;
var elementoSeleccionado;
var s_url;
var s_usuario;
/**
 * s_nombre_input = Input donde buscada
 * URL = Direccion donde buscada
 * s_usuario_input = Input donde saldra el Id
 */
instanceDivLista = function (s_nombre_input, URL, valorInicial) {
	s_div = "div_" + s_nombre_input;
	divLista = document.createElement("div");
	divLista.setAttribute("id", s_div);
	divLista.setAttribute("onmouseout", "v=1;");
	divLista.setAttribute("onmouseover", "v=0;");
	divLista.style.position = "absolute";
	divLista.style.backgroundColor = "white";
	divLista.style.border = "2px solid #c3d9ff";
	divLista.style.zIndex = "9999";
	divLista.style.display = "none";
	divLista.style.marginTop = "-5px";
	divLista.style.fontSize = "12px";
	divLista.style.textAlign = "left";
	s_usuario = s_nombre_input;
	var inputNuevo = document.getElementById(s_nombre_input);
	inputLista = inputNuevo.cloneNode(true);
	inputLista.setAttribute("id", "lista_" + s_nombre_input);
	inputLista.setAttribute("onfocus", "input_lista_onfocus();");
	inputLista.setAttribute("onblur", "input_lista_onblur();");	
	inputLista.setAttribute('onkeypress',inputNuevo.getAttribute("onkeyup"));
	inputLista.onkeyup = input_lista_onkeyup;
	if (!valorInicial) {
		valorInicial = "";
	}
	inputLista.setAttribute('value', valorInicial);
	inputNuevo.style.display = "none";
	var pop = inputNuevo.parentNode;
	pop.appendChild(inputLista);
	var s_body = document.getElementsByTagName("body").item(0);
	s_body.appendChild(divLista);
	elementoSeleccionado = -1;
	ultimoIdentificador = 0;
	s_url = URL;
	nuevaBusqueda = 1;
	busqueda = null;
	ultimaBusquedaNula = null;
	v = 1;
};
input_lista_onkeyup = function(e) {
	if (navegaTeclado(e) == 1) {
		clearTimeout(ultimoIdentificador);
		ultimoIdentificador = setTimeout("rellenaLista()", 1000);
	}
}
input_lista_onblur = function() {
	if (v == 1) {
		divLista.style.display = "none";
	}
}
input_lista_onfocus = function () {
	if (divLista.childNodes[0] != null && inputLista.value.length != 0) {
		filtraLista(inputLista.value);
		formateaLista(inputLista.value);
		reiniciaSeleccion();
		divLista.style.display = "block";
	}
}
formateaLista = function (valor) {
	try {
		var x = 0, verificaExpresion = new RegExp("(" + valor + ")", "i");
		while (divLista.childNodes[x] !== null) {
			divLista.childNodes[x].innerHTML = divLista.childNodes[x].innerHTML.replace(verificaExpresion, "<b>$1</b>");
			x = x + 1;
		}
	}
	catch (e) {
	}
}
limpiaPalabra = function (palabra) {
	palabra = palabra.replace(/<b>/i, "");
	palabra = palabra.replace(/<\/b>/i, "");
	return palabra;
}
coincideBusqueda = function (palabraEntera, primerasLetras) {
	if (primerasLetras === null) {
		return 0;
	}
	var verificaExpresion = new RegExp("(" + primerasLetras + ")", "i");
	if (verificaExpresion.test(palabraEntera)) {
		return 1;
	} else {
		return 0;
	}
}
nuevaCadenaNula = function (valor) {
	if (coincideBusqueda(valor, ultimaBusquedaNula) === 0) {
		ultimaBusquedaNula = valor;
	}
}
busquedaEnBD = function () {
	var valor = inputLista.value;
	if ((coincideBusqueda(valor, busqueda) === 1 && nuevaBusqueda === 0) || coincideBusqueda(valor, ultimaBusquedaNula) === 1) {
		return 0;
	} else {
		return 1;
	}
}
filtraLista =function (valor) {
	try {
		var x = 0;
		while (divLista.childNodes[x] !== null) {
			divLista.childNodes[x].innerHTML = limpiaPalabra(divLista.childNodes[x].innerHTML);
			if (coincideBusqueda(limpiaPalabra(divLista.childNodes[x].innerHTML), valor) === 0) {
				divLista.removeChild(divLista.childNodes[x]);
				x = x - 1;
			}
			x = x + 1;
		}
	}
	catch (e) {
	}
}
reiniciaSeleccion = function () {
	mouseFuera();
	elementoSeleccionado = -1;
}
navegaTeclado = function (evento) {
	var teclaPresionada;
	if (window.event) {
		teclaPresionada = window.event.keyCode;
	} else {
		teclaPresionada = evento.which;
	}
	switch (teclaPresionada) {
	  case 40:
		if (elementoSeleccionado < divLista.childNodes.length - 1 - nuevaBusqueda) {
			mouseDentro(divLista.childNodes[elementoSeleccionado + 1]);
			elementoSeleccionado = elementoSeleccionado + 1;
		}
		return 0;
	  case 38:
		if (elementoSeleccionado > 0) {
			mouseDentro(divLista.childNodes[elementoSeleccionado - 1]);
			elementoSeleccionado = elementoSeleccionado - 1;
		}
		return 0;	  
	  case 39:
		if (divLista.style.display == "block" && elementoSeleccionado != -1) {
			clickLista(divLista.childNodes[elementoSeleccionado]);
		}
		return 0;
	  default:
		return 1;
	}
}
rellenaLista = function () {
	divLista.style.left = xPageX(inputLista.id) + "px";
	divLista.style.top = (xPageY(inputLista.id) + xHeight(inputLista.id)) + "px";
	var valor = inputLista.value;
	var reg = /(^[a-zA-Z0-9.@\xF1\xE1\xE9\xED\xF3\xFA\xD1\xC1\xC9\xCD\xD3\xDA ]{2,40}$)/;
	if (!reg.test(valor)) {
		divLista.style.display = "none";
	} else {
		if (busquedaEnBD() === 0) {
			busqueda = valor;
			filtraLista(valor);
			if (divLista.childNodes[0] === null) {
				divLista.style.display = "none";
				nuevaCadenaNula(valor);
			} else {
				reiniciaSeleccion();
				formateaLista(valor);
			}
		} else {
			busqueda = valor;
			ajax = nuevoAjax();
			ajax.open("POST", s_url, true);
			ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			ajax.send("nombre=" + toHex(valor));
			ajax.onreadystatechange = function () {
				if (ajax.readyState == 4) {
					if (!ajax.responseText) {
						divLista.style.display = "none";
					} else {
						var respuesta = new Array(2);
						respuesta = ajax.responseText.split("&");
						nuevaBusqueda = respuesta[0];
						if (respuesta[1] != "vacio") {
							divLista.style.display = "block";
							divLista.innerHTML = respuesta[1];
							reiniciaSeleccion();
							formateaLista(valor);
						} else {
							nuevaCadenaNula(valor);
						}
					}
				}
			};
		}
	}
}
clickLista = function (elemento) {
	v = 1;
	valor = limpiaPalabra(elemento.innerHTML);
	busqueda = valor;
	inputLista.value = valor;
	divLista.style.display = "none";
	elemento.className = "item_normal";
	var inputUsuario = document.getElementById(s_usuario);
	inputUsuario.value = elemento.id;
}
mouseFuera = function () {
	if (elementoSeleccionado != -1 && divLista.childNodes[elementoSeleccionado]) {
		divLista.childNodes[elementoSeleccionado].className = "item_normal";
	}
}
mouseDentro= function (elemento, nElemento) {
	mouseFuera();
	elemento.className = "item_resaltado";
	if (nElemento) {
		elementoSeleccionado = nElemento;
	}
}
nuevoAjax = function () {
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

