var ventanas = new Array();

function abrir_servicio_curso(){
	window.document.location ='Curso.action';
}

function abremelo(n,u,w,h){
	var x = (screen.width-w)/2;
	var y = (screen.height-h)/2;
	var win = window.open(u, n, "width=" + w + ",height=" + h + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
	ventanas.push(win);
	win.focus();
}

function abrir_servicio_noticia(){
	abremelo('PopUp_Noticia','noticia/Cargar.action',734,570);
}

function abrir_servicio_foros(params){
	abremelo('PopUp_Foro','foro/Foro.action',866,(screen.height-150>800)?800:screen.height-150);
}

function abrir_servicio_agenda(){
	abremelo('PopUp_Agenda','agenda/Cargar.action',400,400);
}

function abrir_servicio_biblioteca(){
	abremelo('PopUp_Biblioteca','http://www.tecsup.edu.pe/library/submitLoginExt.do?frame2=CB04CE07E106C703C602&frame3=D605D50EC502D704D00C&frame1=C502D704D00CD00CCD0E&id=demo&frame4=D00CCD0ECB04CE07E106',866,(screen.height-150>800)?800:screen.height-150);
}

function abrir_servicio_apuntes(){
	abremelo('PopUp_Apuntes','anotacion/Anotacion.action',420,(screen.height-150>800)?800:screen.height-150);
}

function abrir_servicio_buzon(){
	abremelo('PopUp_Buzon','comun/buzon/Buzon.action',730,(screen.height-150>650)?650:screen.height-150);
}

function abrir_servicio_chat(){
	try{
	abremelo('PopUp_Chat','chat/Cargar.action',730,560);
	}catch(e){}
}

function nuevoAjax() {
	var xmlhttp = false;
	try { 
		// Creacion del objeto AJAX para navegadores no IE
		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	}
	catch (e) {
		try { 
			// Creacion del objet AJAX para IE 
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

function abrirGuiaEstudiante() {	
	campusGuia = window.open("http://www.tecsup.edu.pe/webcampus/guia/index.swf", "Guia", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=0,width=780,height=580,left=" + (screen.availHeight - 780) / 2 + ",top=" + (screen.availWidth - 580) / 2 + "");
	campusGuia.focus();	
}
function cerrarVentanas() {
	for(var i=0; i<ventanas.length; i++) {
		if(ventanas[i])ventanas[i].close();
	}
}
function enviarCorreo(idUsuario,titulo) {
	window.open("comun/buzon/NuevoMensaje.action?destino=" + idUsuario+"&titulo="+titulo, "Saludos", "height=350,width=510,scrollbars=no");
	return true;
}