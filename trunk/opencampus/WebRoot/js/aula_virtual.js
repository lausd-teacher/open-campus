// Todas las ventanas para luego cerrarlas
var aulaVirtualRepaso = null;
var aulaVirtualTexto = null;
var aulaVirtualMiClase = null;
var aulaVirtualVitrina = null;
var aulaVirtualLectura = null;
var aulaVirtualDialogo = null;
var aulaVirtualTrabajo = null;
var aulaVirtualLaboratorio = null;
var aulaVirtualActividadGrupal = null;
var aulaVirtualTest = null;
var aulaVirtualTestEjecucion = null;
var aulaVirtualPlanDocente = null;
var aulaVirtualReporte = null;
var aulaVirtualDebate = null;
var aulaVirtualLaboratorioPdf = null;
var aulaVirtualInforme = null;
function cerrarAulaVirtualVentanas() {
	if (aulaVirtualRepaso) {
		aulaVirtualRepaso.close();
	}
	if (aulaVirtualTexto) {
		aulaVirtualTexto.close();
	}
	if (aulaVirtualMiClase) {
		aulaVirtualMiClase.close();
	}
	if (aulaVirtualVitrina) {
		aulaVirtualVitrina.close();
	}
	if (aulaVirtualLectura) {
		aulaVirtualLectura.close();
	}
	if (aulaVirtualDialogo) {
		aulaVirtualDialogo.close();
	}
	if (aulaVirtualTrabajo) {
		aulaVirtualTrabajo.close();
	}
	if (aulaVirtualLaboratorio) {
		aulaVirtualLaboratorio.close();
	}
	if (aulaVirtualActividadGrupal) {
		aulaVirtualActividadGrupal.close();
	}
	if (aulaVirtualTest) {
		aulaVirtualTest.close();
	}
	if (aulaVirtualPlanDocente) {
		aulaVirtualPlanDocente.close();
	}
	if (aulaVirtualReporte) {
		aulaVirtualReporte.close();
	}
	if (aulaVirtualDebate) {
		aulaVirtualDebate.close();
	}
	if (aulaVirtualLaboratorioPdf) {
		aulaVirtualLaboratorioPdf.close();
	}
	if (aulaVirtualTestEjecucion) {
		aulaVirtualTestEjecucion.closeForced();
		aulaVirtualTestEjecucion.close();
	}
	if (aulaVirtualInforme){
		aulaVirtualInforme.closeForced();
		aulaVirtualInforme.close();
	}
}
function cerrarAula(){
	var ajax=nuevoAjax();
	ajax.open("GET", xGetContextPath()+"/aulavirtual/SalirDelAula.action", false);
	ajax.setRequestHeader( "If-Modified-Since", "Sat, 1 Jan 2000 00:00:00 GMT" );
	//ajax.send(null);
	if((ajax.readyState == 4))alert(ajax.responseText);
	
	cerrarAulaVirtualVentanas();
}
function showBlock(){
	xShowD('blocker');
	xMoveTo('blocker',xScrollLeft(),xScrollTop()-10);
	xResizeTo('blocker',xClientWidth(),xClientHeight()+10);
	this.exit = true;
}

function hideBlock(){
	aulaVirtualTestEjecucion = null;
	xHideD('blocker');
	this.exit = false;
}

function resize() {
	xResizeTo('blocker',xClientWidth(),xClientHeight()+10);
}

function scrolling() {
	xMoveTo('blocker', xScrollLeft(),xScrollTop()-10);
}

// Inicio de las funciones para abrir ventanas
function abrirTexto(url) {
	var ancho = 770;
	var alto = 500;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualTexto = window.open(url, "AulaVirtualTexto", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualTexto.focus();
	return true;
}
function abrirRepaso(url, ancho, alto) {
	if (!alto || !ancho) {
		ancho = 775;
		alto = 505;
	}
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualRepaso = window.open(' ', "AulaVirtualRepaso", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=0,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualRepaso.focus();
	var html_version = '<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">';
	var css = '<style type="text/css"> <!-- body { margin-left: 0px; margin-top: 0px; margin-right: 0px; margin-bottom: 0px; } --> </style> ';
	var body = '<object classid="" codebase="" width="770" height="500"> <param name="movie" value="'+url+'"> <param name="quality" value="high"> <embed src="'+url+'" quality="high" type="application/x-shockwave-flash" width="770" height="500"></embed> </object> ';
	aulaVirtualRepaso.document.write( html_version+css+body );
	aulaVirtualRepaso.document.close();
	return true;
}
function abrirLaboratorio(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualLaboratorio = window.open(url, "AulaVirtualLaboratorio", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualLaboratorio.focus();
	return true;
}
function abrirMiClase(url, titulo, ancho, alto) {
	var ancho = 550;
	var alto = 500;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualMiClase = window.open(url, "AulaVirtualMiClase", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualMiClase.focus();
	return true;
}
function abrirInforme(url) {
	var ancho = 550;
	var alto = 600;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualInforme = window.open(url, "AulaVirtualInforme", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualInforme.focus();
	return true;
}
function abrirVitrina(url, titulo, ancho, alto) {
	var ancho = 550;
	var alto = 500;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualVitrina = window.open(url, "AulaVirtualVitrina", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualVitrina.focus();
	return true;
}
function abrirLectura(url, titulo, ancho, alto) {
	var ancho = 550;
	var alto = 500;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualLectura = window.open(url, "AulaVirtualLectura", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualLectura.focus();
	return true;
}
function abrirDialogo(url) {
	var ancho = 720;
	var alto = 550;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualDialogo = window.open(url, "AulaVirtualDialogo", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualDialogo.focus();
	return true;
}
function abrirTrabajo(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualTrabajo = window.open(url, "AulaVirtualTrabajo", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualTrabajo.focus();
	return true;
}
function abrirLaboratorio(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualLaboratorio = window.open(url, "AulaVirtualLaboratorio", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualLaboratorio.focus();
	return true;
}
function abrirLaboratorioPdf(url) {
	var ancho = 770;
	var alto = 500;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualLaboratorioPdf = window.open(url, "AulaVirtualLaboratorioPdf", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualLaboratorioPdf.focus();
	return true;
}
function abrirActividadGrupal(url) {
	var ancho = 550;
	var alto = screen.availHeight*0.8;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualActividadGrupal = window.open(url, "AulaVirtualActividadGrupal", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=0,top=" + Y + ",left=" + X + "");
	aulaVirtualActividadGrupal.focus();
	return true;
}
function abrirTest(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualTest = window.open(url, "AulaVirtualTest", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualTest.focus();
	return true;
}
function abrirTestEjecucion(url) {
	var ancho = 660;
	var alto = 460;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	
	aulaVirtualTestEjecucion = window.open(url, "sade", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=0,fullscreen=yes,top=" + Y + ",left=" + X + "");
	aulaVirtualTestEjecucion.focus();
	return true;
}
function abrirPlanDocente(url) {
	var ancho = 550;
	var alto = 540;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualPlanDocente = window.open(url, "AulaVirtualPlanDocente", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualPlanDocente.focus();
	return true;
}
function abrirReporte(url) {
	var ancho = screen.availWidth-100;
	var alto = screen.availHeight-200;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualReporte = window.open(url, "AulaVirtualReporte", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualReporte.focus();
	return true;
}
function abrirDebate(url) {
	var ancho = 720;
	var alto = 550;
	var X = (screen.availWidth - ancho) / 2;
	var Y = (screen.availHeight - alto) / 2;
	aulaVirtualDebate = window.open(url, "AulaVirtualDebate", "width=" + ancho + ",height=" + alto + ",toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=1,resizable=1,top=" + Y + ",left=" + X + "");
	aulaVirtualDebate.focus();
	return true;
}

// Fin de las funciones para abrir ventanas
function mensajes(n) {
	if (n > 0) {
		var X = (screen.availWidth - 300) / 2;
		var trans = document.getElementById("transparency");
		trans.setAttribute("style", "width:1280px;height:650px;-moz-opacity:.55;background-color:#EEE;position:absolute;top:1px;left:1px;");
		trans.style.filter = "alpha(opacity=55)";
		trans.style.width = 1280;
		trans.style.height = 650;
		trans.style.position = "absolute";
		trans.style.top = 1;
		trans.style.left = 1;
		trans.style.background = "EEE";		
		var mensajes = document.getElementById("mensajes");
		var left = (xClientWidth()-xWidth('mensajes'))/2;
		mensajes.setAttribute("style", "position:absolute;top:50px;left:"+left+"px;");
		mensajes.style.position = "absolute";
		mensajes.style.top = 50;
		mensajes.style.left = left;
	}
}
function enviarPublicacion(titulo, contenido, idP) {
	try {
		if (document.getElementById(titulo).value.trim() == "") {
			alert("Ingrese un t\xedtulo");
			return false;
		} else {
			if (document.getElementById(contenido).value.trim() == "" && idP == "2") {
				alert("Seleccione un archivo");
				return false;
			}
		}
		if(idP=='1'){
			if (document.getElementById(contenido).value.trim() == "&lt;Ingrese aqu&iacute; el mensaje&gt;"  || document.getElementById(contenido).value.trim() == "<Ingrese aquí el mensaje>"  || document.getElementById(contenido).value.trim() == "&lt;Ingrese aquí el mensaje&gt;") {
				alert("Debe introducir algún contenido.");
				return false;
			}
			var con = xGetElementById("wysiwyg" + "mensaje").contentWindow.document.body.innerHTML;
			var conClear = con.stripTags().stripScripts().strip().replace(/&nbsp;/g,"").stripNewLine().strip();
			if(conClear.length===0){
				alert("Debe introducir algún contenido.");
				return false;
			}
		}
		
	}
	catch (e) {
		if (confirm("Ha ocurrido un error:("+((e.description)?e.description:e)+"). Desea reiniciar?")) {
			return false;
			window.location.reload();
		}
	}
}
function docente_cambiar_estado_estudiante(unidad, contexto) {
	var url = contexto + "/aulavirtual/FichaUnidadRecursoTestCambiarEstadoEstudiante.action";
	var ajax = nuevoAjax();
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("idUnidad=" + unidad);
}
function copiarRecurso(url) {
	var ajax = nuevoAjax();
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send(null);
}
function procesarRepaso(contexto, idUnidad, urlRepaso,ancho,alto) {
	copiarRecurso(contexto + "/aulavirtual/CopiarRepaso.action?idunidad=" + idUnidad);
	setTimeout("abrirRepaso('" + urlRepaso + "','"+ancho+"','"+alto+"')", 500);
}
function procesarTexto(contexto, idUnidad, indice, urlTexto) {
	copiarRecurso(contexto + "/aulavirtual/CopiarTexto.action?idunidad=" + idUnidad + "&indice=" + indice);
	setTimeout("abrirTexto('" + urlTexto + "')", 500);
}
function procesarLaboratorio(contexto, idUnidad, indice, urlTexto) {
	copiarRecurso(contexto + "/aulavirtual/CopiarLaboratorio.action?idunidad=" + idUnidad + "&indice=" + indice);
	setTimeout("abrirLaboratorioPdf('" + urlTexto + "')", 500);
}
function cambiarMensajeEstado(idPublicacion) {
	var ajax = nuevoAjax();
	var url = xGetContextPath() + "/aulavirtual/CambiarEstadoMensajeLeido.action?";
	ajax.open("POST", url, true);
	ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajax.send("idPublicacion=" + idPublicacion);
	xHideD("apub" + idPublicacion);
	xHideD("bpub" + idPublicacion);
	ajax.onreadystatechange = function () {
		if (ajax.readyState === 4) {
			var resultado = ajax.responseText;
			if (resultado == "0") {
				xHideD("transparency");
				/*document.getElementById('transparency').style.zIndex='-1';*/
				//xHideD("tituloPublis");
				//xHideD("mensajes");
				xHideD("tablaAvisos");
				/*document.getElementById('tablaAvisos').style.zIndex='-1';*/				
			}
		}
	};
}

//ENCUESTA *************************************************************************
function encuesta() {
	var s_reinicio = document.getElementById("div_back_encuesta");
	var x_width = xWidth("cuerpo");
	try {
		s_reinicio.setAttribute("style", "width:1300px; height:1200px; -moz-opacity:.55;background-color:#EAEBED;position:absolute;top:0px;left:0px;");
	}
	catch (e) {
	}
	try {
		s_reinicio.style.background = "#EAEBED";
	}
	catch (e) {
	}
	s_reinicio.style.filter = "alpha(opacity=55)";
	s_reinicio.style.width = 1300;
	s_reinicio.style.height = 1200;
	s_reinicio.style.position = "absolute";
	s_reinicio.style.top = 0;
	s_reinicio.style.left = 0;
	
	var div_mensaje = document.getElementById("div_encuesta");
	var left_div_mensaje = (xClientWidth() - xWidth("table_encuesta")) / 2;
	var top_div_mensaje =0;
	try {
		div_mensaje.setAttribute("style", "-moz-opacity:1;background-color:#FFFFFF;border:0px solid #000000;position:absolute;top:" + top_div_mensaje + "px;left:" + left_div_mensaje + "px;\tz-index: 1;");
	}
	catch (e) {
	}
	div_mensaje.style.background = "#FFFFFF";
	div_mensaje.style.filter = "alpha(opacity=100)";
	div_mensaje.style.top = top_div_mensaje;
	div_mensaje.style.left = left_div_mensaje;
	div_mensaje.style.position = "absolute";
	div_mensaje.style.border = "0px solid #000000";
}

function ocultarEncuesta(){
	xHideD('div_encuesta');
	xHideD('div_back_encuesta');
}

function validarEncuesta(form){
	
	if(form.p1_p2.value.trim() === ""){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(isNaN(parseFloat(form.p1_p2.value))){
		alert("El número de horas es un valor numérico, el Campus le exige seriedad en su respuesta.");
		return false;
	}
	form.p1_p2.value = parseFloat(form.p1_p2.value);
	if(parseFloat(form.p1_p2.value)> 168){
		alert("La semana tiene 168 horas, el Campus le exige seriedad en su respuesta.");
		return false;
	}
	if(!validarRadio(form.p2_p1)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(!validarRadio(form.p2_p2)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(!validarRadio(form.p3_p1)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(!validarRadio(form.p3_p2)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(!validarRadio(form.p3_p3)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(!validarRadio(form.p4_p1)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(!validarRadio(form.p5_p1)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(!validarRadio(form.p6_p1)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(!validarRadio(form.p6_p2)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	if(!validarRadio(form.p6_p3)){
		alert("Debe completar todo el formulario.");
		return false;
	}
	return true;
}

function validarRadio(radio){
	for (i=0;i<radio.length;i++){ 
       if (radio[i].checked) 
         return true;
    } 
    return false;
}

