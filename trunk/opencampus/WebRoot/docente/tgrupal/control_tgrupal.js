var gruposControl;
var intentoPublicarTG = false;
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

function seleccion(TR, valor) {
	if (valor) {
		TR.style.background = "#EAEBED";
	} else {
		TR.style.background = "";
	}
}

function seleccionBlue(TR, valor) {
	if (valor) {
		TR.style.background = "#64A5F4";
	} else {
		TR.style.background = "#FFFFFF";
	}
}

function cuentaCaracteres(formDesc){
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

function seleccionInput(Input, valor) {
	if (valor) {
		Input.style.background = "#FFFBE8";
		Input.style.borderColor = "#EFDA45";
	} else {
		Input.style.background = "#FFFFFF";
		Input.style.borderColor = "#AAABAF";
	}
}

function cancelarmodificarTrabajo(){

	xGetElementById('form_fechaActivacion').value = xInnerHtml('DfActivacion');
	xGetElementById('form_fechaEntrega').value = xInnerHtml('DfEntrega');
	xGetElementById('form_descripcion').value = xInnerHtml('Ddescripcion');
	xGetElementById('form_numCaracteres').value = xGetElementById('form_descripcion').value.length;
	
	xShowD('det_TGrupal');
	xHideD('form_TGrupal');
}

function ocultarFlags(){
	if(xDef(gruposControl)){
		for(var i=0; i<gruposControl.length; i=i+1){
			var hide = true;
			for(var j=0; j<gruposControl[i].length; j=j+1){
				var nota = xGetElementById('nota_NF_'+gruposControl[i][j]);
				if(nota.value == ""){
					hide = false;
				}
			}
			if(hide){
				xHideD('flag_'+(parseInt(i)+1));
			}else{
				xShowD('flag_'+(parseInt(i)+1));
			}
		}
	}
}
function catcalc(cal) {
	//alert("demo");
}

// AJAX
// *********************************************************************************************//
/*
function calificarTrabajo(fila, idMatricula){

	var nota_NO = xGetElementById("nota_NO_"+idMatricula);
	var nota_NC = xGetElementById("nota_NC_"+idMatricula);
	var nota_NI = xGetElementById("nota_NI_"+idMatricula);
	var nota_NP = xGetElementById("nota_NP_"+idMatricula);
	var nota_NF = xGetElementById("nota_NF_"+idMatricula);

	if(validarNota(nota_NO.value)){
		if(validarNota(nota_NC.value)){
			if(validarNota(nota_NI.value)){
				if(validarNota(nota_NP.value)){
	
				    //Entrada
				    if(nota_NO.value != "" && nota_NC.value != "" && nota_NI.value != "" && nota_NP.value != ""){
				    	nota_NF.value = parseInt(nota_NO.value)+parseInt(nota_NC.value)+parseInt(nota_NI.value)+parseInt(nota_NP.value);
				    	if(nota_NF.value<11){
				    		nota_NF.style.color = "#FF0000";
				    	}else{
				    		nota_NF.style.color = "#0997F7";
				    	}
				    }else{
				    	nota_NF.value = "";
				    }
				    var cadena = "idMatricula="+idMatricula+"&notaOportunidad="+nota_NO.value+"&notaCoherencia="+nota_NC.value+"&notaInnovacion="+nota_NI.value+"&notaParticipacion="+nota_NP.value+"&notaFinal="+nota_NF.value;
							
					//Salida
					
					ajax = nuevoAjax();
					
					ajax.open("POST", xGetContextPath()+"/aulavirtual/tgrupal/Calificar.action", true);
					ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
					ajax.send(cadena);
					ajax.onreadystatechange = function () {
						if (ajax.readyState === 4) {
							if(ajax.responseText === "OK"){
								ocultarFlags();
							}else{
								alert("Ocurrió un error inexplicable al momento de insertar la nota. Inténtelo nuevamente.");
								window.location.reload();
							}
						}
					}
					
				}else{
					nota_NP.value = "";
				}
			}else{
				nota_NI.value = "";
			}
		}else{
			nota_NC.value = "";
		}
	}else{
		nota_NO.value = "";
	}

}*/

function calificarTrabajo(fila, idMatricula){

	var nota_NF = xGetElementById("nota_NF_"+idMatricula);

	if(validarNota(nota_NF.value)){
	
	    //Entrada
	    if(nota_NF.value != ""){
	    	if(parseInt(nota_NF.value)<11){
	    		nota_NF.style.color = "#FF0000";
	    	}else{
	    		nota_NF.style.color = "#0997F7";
	    	}
	    }else{
	    	nota_NF.value = "";
	    }
	    var cadena = "idMatricula="+idMatricula+"&notaOportunidad=0&notaCoherencia=0&notaInnovacion=0&notaParticipacion=0&notaFinal="+nota_NF.value;
				
		//Salida
		
		ajax = nuevoAjax();
		
		ajax.open("POST", xGetContextPath()+"/aulavirtual/tgrupal/Calificar.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send(cadena);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				if(ajax.responseText === "OK"){
					ocultarFlags();
				}else{
					alert("Ocurrió un error inexplicable al momento de insertar la nota. Inténtelo nuevamente.");
					window.location.reload();
				}
			}
		}
				
	}else{
		nota_NF.value = "";
	}

}

//********

var fic='99-99-9999';
var ffc='99-99-9999';

function validar(fechaActivacion, fechaEntrega, descripcion, fIni, fFin){
	if(!intentoPublicarTG){
		if(fIni != '')
			fic = fIni;
		if(fFin != '')	
			ffc = fFin;
		if(!vacio(fechaActivacion) && !vacio(fechaEntrega)){
			if(fechaHora1MayorFechaHora2(fechaEntrega,fechaActivacion)) {
				if(!vacio(descripcion)) {
					if(validarFechaEntrega(fechaEntrega)){
						if(validarFechaActivacion(fechaActivacion)){
							var buttonPublish = xGetElementById("form_button");
							buttonPublish.disabled = true;
							return true;										
						}
						return false;
					}
					return false;
				}
				alert("Ingrese una descripción adicional sobre el trabajo");
				return false;
			}
			alert("La fecha de entrega debe ser mayor a la fecha de activación");
			return false;
		}
		alert("Ingrese las fechas requeridas");
		return false;
	}
}

function validarCompacto(form, fIni, fFin) {
	if(fIni != '')fic = fIni;
	if(fFin != '')ffc = fFin;
	
	if(form.fechaActivacion.value.isEmpty() || form.fechaEntrega.value.isEmpty()){
		alert("Ingrese las fechas requeridas.");
		return false;
	};
	if(!fechaHora1MayorFechaHora2(form.fechaEntrega.value,form.fechaActivacion.value)) {
		alert("La fecha de entrega debe ser mayor a la fecha de activación.");
		return false;
	};
	if(!validarFechaEntrega(form.fechaEntrega.value)){
		return false;
	};
	if(!validarFechaActivacion(form.fechaActivacion.value)){
		return false;
	};
	if(form.descripcion.value.trim().isEmpty()){
		alert("Ingrese una descripción adicional sobre el trabajo.");
		return false;
	};
	if(form.ngrupos && form.file.value.trim().isEmpty()){
		alert("Debe subir el archivo correspondiente al presente trabajo grupal.");
		return false;
	}
	if(form.ngrupos && (form.ngrupos.value.isEmpty() || !form.ngrupos.value.trim().isInteger())){
		alert("Ingrese el número de grupos que desea formar.");
		return false;
	}
	var nstudents = xGetElementById('nstudents');
	if(form.ngrupos && (parseFloat(form.ngrupos.value) < 1 || parseFloat(form.ngrupos.value) > nstudents.value)){
		alert('Debe ingresar un valor numérico entre 1 a '+nstudents.value+' como número de grupos.');
		return false;
	}
	var buttonPublish = xGetElementById("form_button");
	buttonPublish.disabled = true;	
	return true; 
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