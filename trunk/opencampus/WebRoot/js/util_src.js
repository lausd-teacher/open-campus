// Todas las ventanas para luego cerrarlas
var campusApuntes = null;
var campusNoticia = null;
var campusForo = null;
var campusAgenda = null;
var campusBiblioteca = null;
var campusBuzon = null;
var campusChat = null;
var campusGuia = null;
function cerrarVentanas() {
	if (campusApuntes) {
		campusApuntes.close();
	}
	if (campusNoticia) {
		campusNoticia.close();
	}
	if (campusForo) {
		campusForo.close();
	}
	if (campusAgenda) {
		campusAgenda.close();
	}
	if (campusBiblioteca) {
		campusBiblioteca.close();
	}
	if (campusBuzon) {
		campusBuzon.close();
	}
	if (campusChat) {
		campusChat.close();
	}
	if (campusGuia) {
		campusGuia.close();
	}
	cerrarBuzon();
}

/* Abrir Servicios */

function abrir_servicio_curso(){
		window.document.location =xGetContextPath() +'/Curso.action';
}

function abrir_servicio_noticia(){
	var name = 'PopUp_Noticia';
	var width = 730;
	var height = 550;
	//height = (height>650)?650:height;
	var x = (screen.width-width)/2;
	var y = (screen.height-height)/2;
	
	campusNoticia = window.open(xGetContextPath() +'/noticia/Cargar.action', name, "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=0,resizable=1,top=" + y + ",left=" + x + "");
	campusNoticia.focus();
}

function abrir_servicio_foros(params){
	var name = 'PopUp_Foro';
	var width = 866;
	var height = screen.height-150;
	height = (height>800)?800:height;
	var x = (screen.width-width)/2;
	var y = (screen.height-height)/2;
	if(!params) params = xGetContextPath() +'/foro/Foro.action';
	campusForo = window.open(params, name, "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
	campusForo.focus();
}

function abrir_servicio_agenda(){
	var name = 'PopUp_Agenda';
	var width = 400;
	var height = 400;
	var x = (screen.width-width)/2;
	var y = (screen.height-height)/2;
	
	campusAgenda = window.open(xGetContextPath() +'/agenda/Cargar.action', name, "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
	campusAgenda.focus();
}

function abrir_servicio_biblioteca(){
	var name = 'PopUp_Biblioteca';
	var width = 866;
	var height = screen.height-150;
	height = (height>800)?800:height;
	var x = (screen.width-width)/2;
	var y = (screen.height-height)/2;	
	campusBiblioteca = window.open('http://www.tecsup.edu.pe/library/submitLoginExt.do?frame2=CB04CE07E106C703C602&frame3=D605D50EC502D704D00C&frame1=C502D704D00CD00CCD0E&id=demo&frame4=D00CCD0ECB04CE07E106', name, "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
	campusBiblioteca.focus();	
}

function abrir_servicio_apuntes(){
	var name = 'PopUp_Apuntes';
	var width = 420;
	var height = screen.height-150;
	height = (height>650)?650:height;
	var x = (screen.width-width)/2;
	var y = (screen.height-height)/2;
	
	campusApuntes = window.open(xGetContextPath() +'/anotacion/Anotacion.action', name, "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
	campusApuntes.focus();
}

function abrir_servicio_buzon(){
	var name = 'PopUp_Buzon';
	var width = 730;
	var height = screen.height-150;
	height = (height>650)?650:height;
	var x = (screen.width-width)/2;
	var y = (screen.height-height)/2;	
	campusBuzon = window.open(xGetContextPath() +'/comun/buzon/Buzon.action', name, "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
	campusBuzon.focus();
}

function verIdiomas(e){
	xMoveTo('menu_idioma',xPageX(e)-4,xPageY(e)+22);
	xChangeDisplay('menu_idioma');	
	changeImage(xGetElementById('arrow_idioma'));
}

function cambiarIdioma(idioma){
	var myConn = new XHConn();
	myConn.connect(xGetContextPath()+"/GuardarIdioma.action?request_locale="+idioma, "POST", null, function (oXML) {location.reload();});
}

function changeImage(img){
	var str = img.src;
	while (str.indexOf("/") != -1)
	str = str.slice(str.indexOf("/") + 1);
	
	var file = str.toLowerCase();
	str = str.slice(file.indexOf(".") + 1);
	if(file.indexOf('_out.'+str) === -1){
		img.src=img.src.replace('.'+str,'_out.'+str);
	}else{
		img.src=img.src.replace('_out.'+str,'.'+str); 
	}
}

function abrir_servicio_cumpleanos(){
}

function abrir_servicio_chat(){
	var name = 'PopUp_Chat';
	var width = 730;
	var height = 560;
	var x = (screen.width-width)/2;
	var y = (screen.height-height)/2;
	try{
		campusChat = window.open(xGetContextPath() +'/chat/Cargar.action', name, "width=" + width + ",height=" + height + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + y + ",left=" + x + "");
		campusChat.focus();
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

function obtenerAbsolutoX(objeto) {
	var curtop = 0;
	if (objeto.offsetParent) {
		curtop = objeto.offsetTop;
		while (objeto = objeto.offsetParent) {
			curtop += objeto.offsetTop;
		}
	}
	return curtop;
}
function obtenerAbsolutoY(objeto) {
	var curleft = 0;
	if (objeto.offsetParent) {
		curleft = objeto.offsetLeft;
		while (objeto = objeto.offsetParent) {
			curleft += objeto.offsetLeft;
		}
	}
	return curleft;
}
function obtenerContextPath() {
	var context = "";
	var array = document.location.href.split("/");
	try {
		var context = array[0] + "/" + array[1] + "/" + array[2] + "/" + array[3];
	}
	catch (e) {
	}
	return context;
}
var ventanas = new Array();
function abrirVentanaFlotante(url, nombreVentana, pAncho, pAlto,X,Y){
	var ancho = pAncho;
	var alto = pAlto;
    if(!X) X = (screen.availWidth - ancho)/2;
    if(!Y) Y = (screen.availHeight - alto)/2; 
  	nombreVentana = window.open(url, nombreVentana, 'width='+ancho+',height='+alto+',toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,top='+Y+',left='+X+''); 
	nombreVentana.focus();
	ventanas.push(nombreVentana);
	return true;
}
function portalAbrirBuzon(contexto) {
	abrirVentanaFlotante(contexto+'/comun/buzon/Buzon.action','Buzon',720,520,'null','null');
}

function seleccion(TR, valor) {
	if (valor) {
		TR.style.background = "#FFFBE8";
	} else {
		TR.style.background = "";
	}
}

function resizeHeight() {
	var s_h = xHeight('cuerpo');
	//alert(s_h);
	if (s_h<350){
		xHeight('cuerpo',350);
	}
}

var dirWin = null;
var confWin = null;

function abrirDirectorio() {	
	dirWin = window.open(xGetContextPath() + "/comun/buzon/DirectorioBuzon.action", "DirectorioBuzon", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=no,resizable=0,width=595,height=340,left=" + (screen.availHeight - 340) / 2 + ",top=" + (screen.availWidth - 595) / 2 + "");
	dirWin.focus();	
}

function abrirConfiguration() {	
	confWin = window.open(xGetContextPath() + "/comun/buzon/Configuration.action", "ConfigurationBuzon", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=0,width=540,height=300,left=" + (screen.availHeight - 300) / 2 + ",top=" + (screen.availWidth - 540) / 2 + "");
	confWin.focus();	
}

function go(url,win){	
	window.open(url,win,"toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes");
}

function enviarCorreo(idUsuario,titulo) {
	if(!titulo)titulo='';
	window.open(xGetContextPath() + "/comun/buzon/NuevoMensaje.action?destino=" + idUsuario+"&titulo="+titulo, "Saludos", "height=350,width=510,scrollbars=no");
	return true;
}

function cerrarBuzon() {
	if(dirWin != null) {
	   dirWin.close();
	}
	if(confWin != null) {
	   confWin.close();
	}
	for(var i=0;i<ventanas.length;i=i+1){
		ventanas[i].close();
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

function abrirGuiaEstudiante() {	
	campusGuia = window.open("http://www.tecsup.edu.pe/webcampus/guia/index.swf", "Guia", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=0,width=780,height=580,left=" + (screen.availHeight - 780) / 2 + ",top=" + (screen.availWidth - 580) / 2 + "");
	campusGuia.focus();	
}