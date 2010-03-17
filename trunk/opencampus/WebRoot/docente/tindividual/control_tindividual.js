// ***** Comun *****
function nuevoAjax()
{ 
	/* Crea el objeto AJAX. Esta funcion es generica para cualquier utilidad de este tipo, por
	lo que se puede copiar tal como esta aqui */
	var xmlhttp=false; 
	try 
	{ 
		// Creacion del objeto AJAX para navegadores no IE
		xmlhttp=new ActiveXObject("Msxml2.XMLHTTP"); 
	}
	catch(e)
	{ 
		try
		{ 
			// Creacion del objet AJAX para IE 
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
		} 
		catch(E) { xmlhttp=false; }
	}
	if (!xmlhttp && typeof XMLHttpRequest!="undefined") { xmlhttp=new XMLHttpRequest(); } 

	return xmlhttp; 
}

var cuenta=0;
function validacion(form) {
	if (cuenta == 0) {
		cuenta++;
		return true;
	} else {
		alert("Su petición ya está siendo procesada, por favor espere un instante.");
		return false;
	}
}
function seleccionBlue(TR, valor) {
	if (valor) {
		//TR.style.background = "#64A5F4";
		TR.style.background = "#9EC8F5";
	} else {
//		TR.style.background = "#9EC8F5";
		TR.style.background = "#FFFFFF";
	}
}
function seleccion(TR, valor) {
	if (valor) {
		TR.style.background = "#EAEBED";
	} else {
		TR.style.background = "";
	}
}

function seleccionInput(Input, valor) {
	if (valor) {
		Input.style.background = "#FFFBE8";
		Input.style.borderColor = "#EFDA45";
	} else {
		Input.style.background = "#FFFFFF";
		Input.style.borderColor = "#AAABAF";
	}
}

function cuentaCaracteres(formDesc){
	//var formDesc = xGetElementById('form_descripcion');
	if(formDesc.value.length > 500){
		alert("Llegó al límite de número de caracteres.");
		formDesc.value = formDesc.value.substring(0,500);
	}
	xGetElementById('form_numCaracteres').value = formDesc.value.length;
}

function validarNota(nota) {
	
	   var er_cp = /([0-9]|^)$/                 
	
	   if(!er_cp.test(nota) || nota<0||nota>20) {
	      alert('Debe ingresar números del 0 al 20');
	      return false
	   }
	   
	   return true;
}

function openExplorer(){
	xGetElementById("uploadForm").click();
	xGetElementById("txtUploadForm").value = xGetElementById("uploadForm").value;
}

function catcalc(cal) {
	//alert("demo");
}

// AJAX
// *********************************************************************************************//
function publicarTrabajo(){
	/*
	// Entrada
	var idFicha = xGetElementById("form_idFicha");
	var idUnidad = xGetElementById("form_idUnidad");
	var fechaActivacion = xGetElementById("form_fechaActivacion");
	var fechaEntrega = xGetElementById("form_fechaEntrega");
	var descripcion = xGetElementById("form_descripcion");
	var archivoNombre = xGetElementById("form_nomArchivo");
	var archivoTamanio = xGetElementById("form_tamArchivo");
	
	//Salida
	var salidaComp = xGetElementById("resultado_curso");
	
	if(trim(nombreComp.value) != ""){
	
		// Reset Interface
		primeraVez=true;
		xGetElementById("form_Silabo_idCurso").value = "";
		xHideD("form_Silabo");
		xHideD("form_Silabo_head");
		xHideD("resultado_silabo");
	
		var cadena = "nombre="+toHex(nombreComp.value)+"&idJerarquia="+jerarquiComp.value;
		
		salidaComp.innerHTML = "<DIV align='center' style='padding: 2px; background: rgb(204, 68, 68) none repeat scroll 0%; z-index: 3; -moz-background-clip: -moz-initial; -moz-background-origin: -moz-initial; -moz-background-inline-policy: -moz-initial; color: white; font-size: 95%; top: 1px; right: 16px; '> Cargando...</DIV>";
	
		ajax = nuevoAjax();
	
		ajax.open("POST", xGetContextPath()+"/admin/curso/Buscar.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send(cadena);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				salidaComp.innerHTML = ajax.responseText;
			}
		}
		
	}*/
}

function calificarTrabajo(idMatricula,estado){

	var notaComp = xGetElementById("nota_"+idMatricula);
	
	if(validarNota(notaComp.value)){
	    //Entrada
		var nota = notaComp.value;
		var cadena = "idMatricula="+idMatricula+"&nota="+nota;
				
		//Salida
		var flagComp = xGetElementById("flag_"+idMatricula);
		notaComp.style.color = "#0997F7";
		
		ajax = nuevoAjax();
		
		ajax.open("POST", xGetContextPath()+"/aulavirtual/tindividual/Calificar.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send(cadena);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				if(ajax.responseText === "1"){
					xHide(flagComp);
				}else if(ajax.responseText === "0"){
					if(estado === "1")
						xShow(flagComp);
				}else
					alert("ERROR");
			}
		}
	}

}


//********

var fic='99-99-9999';
var ffc='99-99-9999';

function validar(fechaActivacion, fechaEntrega, file, descripcion, fIni, fFin){
	//if(!intentoPublicarTG){
		if(fIni != '')
			fic = fIni;
		if(fFin != '')	
			ffc = fFin;
		if(!vacio(fechaActivacion) && !vacio(fechaEntrega)){
			if(fechaHora1MayorFechaHora2(fechaEntrega,fechaActivacion)) {
				if(!vacio(descripcion)) {
					if(validarFechaEntrega(fechaEntrega)){
						if(validarFechaActivacion(fechaActivacion)){
							if(xGetElementById('docExist').value == "true" || !vacio(file)){
								var buttonPublish = xGetElementById("form_button");
								buttonPublish.disabled = true;
								return true;
							}
							alert("No existe un documento predefinido, debe subir uno nuevo.");
							return false;
						}
						return false;
					}
					return false;
				}
				alert("Ingrese una descripción adicional sobre el trabajo");
				return false;
			}
			alert("La fecha de entrega debe ser mayor que la fecha de activación");			
			return false;
		}
		alert("Ingrese las fechas requeridas");
		return false;
	//}
}

function vacio(cadena) {                                    // DECLARACION DE CONSTANTES
	var blanco = " \n\t" + String.fromCharCode(13); // blancos
                                        // DECLARACION DE VARIABLES
	var i;                             // indice en cadena
	var esVacio;                      // cadena es vacio o no
	for (i = 0, esVacio = true; (i < cadena.length) && esVacio; i++) { // INICIO
		esVacio = blanco.indexOf(cadena.charAt(i)) != -1;
	}
	return (esVacio);
}

function validarFechaEntrega(fechaEntrega){
	fe = fechaEntrega.substring(0,fechaEntrega.indexOf(" "));
	if(!fecha1MayorIgualFecha2(ffc,fe)){
		window.document.forms[0].fechaEntrega.value="";
		window.alert("La fecha de entrega de trabajo debe ser antes de la fecha de fin de clases ("+ffc+")");		
		return false;
	}
	return true;
}

function validarFechaActivacion(fechaActivacion){
	fa = fechaActivacion.substring(0,fechaActivacion.indexOf(" "));
	if(!fecha1MayorIgualFecha2(fa,fic)){
		window.document.forms[0].fechaEntrega.value="";
		window.document.forms[0].fechaActivacion.value="";
		window.alert("La fecha de activación del trabajo debe ser posterior a la fecha de inicio de clases ("+fic+").");		
		return false;
	}
	return true;
}
	
function fecha1MayorFecha2(fecha1, fecha2) {
	if (fecha1 == null || fecha2 == null) {
		return false;
	} else {
		f1 = fecha1.split("-");
		f2 = fecha2.split("-");
		d1 = parseInt(f1[0], 10);
		m1 = parseInt(f1[1], 10);
		a1 = parseInt(f1[2], 10);
		d2 = parseInt(f2[0], 10);
		m2 = parseInt(f2[1], 10);
		a2 = parseInt(f2[2], 10);
		if (a1 > a2) {
			return true;
		}//año2 es menor que año 1
		if (a1 == a2) {
			if (m1 > m2) {
				return true;
			}//mismo año m1 mayor que m2
			if ((m1 == m2) && (d1 > d2)) {
				return true;
			}//mismo año, mismo mes , d1 mayor que d2
			return false;//mismo año , m1 menor que m2
		}
		return false;//año menor
	}return false;
}

function fecha1MayorIgualFecha2(fecha1, fecha2) {
	if (fecha1 == null || fecha2 == null) {
		return false;
	} else {
		f1 = fecha1.split("-");
		f2 = fecha2.split("-");
		d1 = parseInt(f1[0], 10);
		m1 = parseInt(f1[1], 10);
		a1 = parseInt(f1[2], 10);
		d2 = parseInt(f2[0], 10);
		m2 = parseInt(f2[1], 10);
		a2 = parseInt(f2[2], 10);
		if (a1 > a2) {
			return true;
		}//año2 es menor que año 1
		if (a1 == a2) {
			if (m1 > m2) {
				return true;
			}//mismo año m1 mayor que m2
			if ((m1 == m2) && (d1 >= d2)) {
				return true;
			}//mismo año, mismo mes , d1 mayor que d2
			return false;//mismo año , m1 menor que m2
		}
		return false;//año menor
	}return false;
}


function fechaHora1MayorFechaHora2(fecha1, fecha2) {
	if (fecha1 == null || fecha2 == null) {
		return false;
	} else {
		fecH1 = fecha1.split(" ");
		fecH2 = fecha2.split(" ");
		fec1 = fecH1[0].split("-");
		fec2 = fecH2[0].split("-");
		tim1 = fecH1[1].split(":");
		tim2 = fecH2[1].split(":");
		
		dia1 = parseInt(fec1[0], 10);
		mes1 = parseInt(fec1[1], 10);
		ano1 = parseInt(fec1[2], 10);		
		hor1 = parseInt(tim1[0], 10);
		min1 = parseInt(tim1[1], 10);		
		ctr1 = fecH1[2];
		
		dia2 = parseInt(fec2[0], 10);
		mes2 = parseInt(fec2[1], 10);		
		ano2 = parseInt(fec2[2], 10);		
		hor2 = parseInt(tim2[0], 10);
		min2 = parseInt(tim2[1], 10);		
		ctr2 = fecH2[2];
		
		if (ano1 > ano2) {
			return true;
		}//año2 es menor que año 1
		if (ano1 == ano2) {
			if (mes1 > mes2) {
				return true;
			}//mismo año mes1 mayor que mes2
			if (mes1 == mes2) {
				if(dia1 > dia2){					
					return true;
				}				
				if(dia1 == dia2){
				//TRABAJO CON AM Y PM
//					if((ctr1 == "PM") && (ctr2 == "AM")){
//						return true;
//					}
//					if(((ctr1 == "AM") && (ctr2 == "AM")) || ((ctr1 == "PM") && (ctr2 == "PM"))){
						if(hor1 > hor2){
							return true;
						}
						if((hor1 == hor2) && (min1 > min2)){
							return true;
						}
						return false;
//					}
//					return false;
					//mismo año, mismo mes, mismo dia ctr1 mayor que ctr2
				}
				return false;
				//mismo año, mismo mes, dia1 mayor que dia2
			}
			return false;//mismo año, mes1 menor que mes2
		}
		return false;//año menor
	}
	return false;//valores nulos
}