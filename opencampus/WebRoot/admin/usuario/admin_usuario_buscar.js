//Para cuando se quiere selecionar un tr
function selecionando(TR, valor) {
	if (valor) {
		TR.style.background = "#EAEBED";
	} else {
		TR.style.background = "";
	}
}
function enviarMensaje() {
	window.alert("enviarMensaje");
}
function enviarMensajeTodos() {
	window.alert("enviarMensajeTodos");
}
function enviar() {
}
function updateUser(){
	var xx = document.getElementsByName("cb");
	var destino = "";
	for(i=0;i<xx.length;i=i+1){
		if(xx[i].checked){
			destino += xx[i].value+",";
		}
	}
	document.email.destino.value=destino;
}    	
function todos(){
	var xx = document.getElementsByName("cb");
	for(i=0;i<xx.length;i=i+1){
		xx[i].checked=true;	    			
	}
	updateUser();
}

function nuevoMensaje(url){
	window.open(url, 'NuevoMensaje', 'height=350,width=510,scrollbars=no');
	return true;
}

// ********** Mostrar Imagen de Usuario ************//
var imagen;
function verImagen(img, archivo) {
	if (xStr(archivo)) {
		imagen = new Image();
		imagen.src = archivo;
		xMoveTo("ampliacion", xPageX(img), xPageY(img) + xHeight(img));
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
		//xLeft("ampliacion",xPageX("ampliacion")-xWidth("ampliacion")+136);
		xInnerHtml("c1", "<img src=\"" + imagen.src + "\" width=\"" + ancho + "\" height=\"" + alto + "\" border=\"0\">");
	} else {
		setTimeout("pruebaCarga()", 500);
	}
}
function ocultarImagen() {
	xHide("ampliacion");
}

// ********** Fin Mostrar Imagen de Usuario ***********//

function cambiarEstado(img,id){
	var estado = 0;
	if(img.alt == 'Activar') estado=1;
	
	var myConn = new XHConn();
	var query = function (oXML) { 
		if(oXML.responseText.strip() === 'OK'){
			if(estado == 1){
				img.alt = 'Desactivar';
				img.src = xGetContextPath()+"/img/activo.gif";
			}else{
				img.alt = 'Activar';
				img.src = xGetContextPath()+"/img/desactivo.gif";
			}
		}else{
			alert('Hubo un error al intentar guardar, se sugiere actualizar la página.');
		}
	};
	var cadena = "idusuario="+id+"&estado="+estado;
	myConn.connect(xGetContextPath()+"/admin/usuario/CambiarEstado.action", "POST", cadena, query);
}

//---------------------------------------------------------------------------------------------
function busqueda() {
	var ausuario = document.getElementById("id_usuario");
	var busuario = document.getElementById("form_usuario");
	busuario.value = ausuario.value;
	var arol = document.getElementById("id_rol");
	var brol = document.getElementById("form_rol");
	var selLength = arol.length;
	for (i = selLength - 1; i >= 0; i = i - 1) {
		if (arol.options[i].selected == true) {
			brol.value = arol.options[i].value;
			break;
		}
	}
	var anombre1 = document.getElementById("id_nombre1");
	var bnombre1 = document.getElementById("form_nombre1");
	bnombre1.value = anombre1.value;
	var anombre2 = document.getElementById("id_nombre2");
	var bnombre2 = document.getElementById("form_nombre2");
	bnombre2.value = anombre2.value;
	var apaterno = document.getElementById("id_paterno");
	var bpaterno = document.getElementById("form_paterno");
	bpaterno.value = apaterno.value;
	var amaterno = document.getElementById("id_materno");
	var bmaterno = document.getElementById("form_materno");
	bmaterno.value = amaterno.value;
	var pos = document.getElementById("form_posicionPagina");
	pos.value = "0";
	document.buscar.submit();
}
function enviarABuscar(e) {
	var key;
	if (window.event) {
		key = window.event.keyCode;     //IE
	} else {
		key = e.which;     //firefox
	}
	if ("13" == key) {
		busqueda();
	}
}
function cambiarPagina(cantidad) {
	var pos = document.getElementById("form_posicionPagina");
	pos.value = cantidad;
	document.buscar.submit();
}
function verDatosPersonales(idusuario) {
var popup_dato;
	var ancho = 780;
	var alto = 350;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	try {
		popup_dato = window.open(obtenerContext() + "/admin/usuario/DatosUsuario.action?id=" + idusuario, "DatosPersonales", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	}
	catch (e) {
		alert(e);
	}
	popup_dato.focus();
	return true;
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

