
function init() {
	try {
		document.login.idUsuario.focus();
	}
	catch (e) {
	}
}
function validar() {
	var n = document.login.idUsuario.value;
	var c = document.login.clave.value;
	if (n == "") {
		alert("Ingrese usuario");
		document.login.idUsuario.focus();
		return false;
	} else {
		if (c == "") {
			alert("Ingrese clave");
			document.login.clave.focus();
			return false;
		}
	}
	document.login.idUsuario.readonly = true;
	document.login.clave.readonly = true;
	document.login.ingresar.disabled = true;
	return true;
}


function abrirImagenDeNoticia(ruta){
	var newImg = new Image();
	newImg.src = ruta;
	var height = newImg.height + 20;
	var width = newImg.width + 20;
	window.open(ruta,'Noticia','width='+width+',height='+height+',status=no,toolbar=no,menubar=no,location=no,resizable=no'); 
}
