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