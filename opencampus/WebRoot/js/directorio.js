function popupform(myform, windowname){
if (myform.destino.value === '')return false;
if (! window.focus)return true;
	window.open(xGetContextPath()+'/comun/buzon/nuevo.jsp', windowname, 'height=350,width=510,scrollbars=no');
	myform.target=windowname;
	return true;
}

function nuevoMensaje(url){
	window.open(url, 'NuevoMensaje', 'height=350,width=510,scrollbars=no');
	return true;
}

function validar(form){
	if(form.rol.selectedIndex == 0 
			|| (form.username.value.trim().length < 3 
			&& form.nombre1.value.trim().length < 3
			&& form.nombre2.value.trim().length < 3
			&& form.paterno.value.trim().length < 3
			&& form.materno.value.trim().length < 3)){
	alert("Debe indicar el grupo y como mínimo uno de los cinco campos del formulario \ncon una longitud no menor a 3 caracteres.");
	return false;
	}
	return true;
}

// ********** Mostrar Imagen de Usuario ************//
var imagen;
var x;
function verImagen(img, archivo) {
	if (xStr(archivo)) {
		x = xPageX(img);
		imagen = new Image();
		imagen.src = archivo;
		xMoveTo("ampliacion", xPageX(img) - xWidth("ampliacion"), xPageY(img) + xHeight(img));
		pruebaCarga(imagen);
		xShow("ampliacion");
		
		$(cerrarampliacion).update('ID: '+img.id);
	}
}
function pruebaCarga() {
	if (imagen.complete == true) {
		var ancho = imagen.width;
		var alto = imagen.height;
		xResizeTo("ampliacion", ancho + 6, alto + 6 + 20);
		xResizeTo("c1", ancho, alto);
		xWidth("cerrarampliacion", ancho); 
		xLeft("ampliacion",x-xWidth("ampliacion"));
		xInnerHtml("c1", "<img src=\"" + imagen.src + "\" width=\"" + ancho + "\" height=\"" + alto + "\" border=\"0\">");
	} else {
		setTimeout("pruebaCarga()", 500);
	}
}
function ocultarImagen() {
	xHide("ampliacion");
}

