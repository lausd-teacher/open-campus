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

function validar(){
	if(trim(xGetElementById('form_descripcion').value)===''){ 
		alert('Debe ingresar una descripción adicional.');
		return false;
	}
	var buttonPublish = xGetElementById("form_button2");
	buttonPublish.disabled = true;
	return true;
	
}

function validarNota(nota) {
	   var er_cp = /([0-9]|^)$/  
	   if(!er_cp.test(nota) || nota<0||nota>20) {
	      alert('Solo debe ingresar numeros del 0 al 20');
	      return false
	   }
	   return true;
}

function ocultarInteraccion(fila){	
	xChangeDisplay("celda_"+fila);
	xChangeDisplay("fila_"+fila);	
}

function mostrarFormNuevoMensaje(){
	//xGetElementById("form_descripcion").value = "";
	xChangeDisplay("mensaje");
}

function mostrarFormModMensaje(){
	xGetElementById("form_descripcion").value = xGetElementById("descripcionHTML").innerHTML
	xGetElementById('form_numCaracteres').value = xGetElementById("form_descripcion").value.length;
	xChangeDisplay("mensaje");
}

// AJAX
// *********************************************************************************************//

function calificarTrabajoEnMatricula(){

	var notaComp = xGetElementById("nota");
	
	if(validarNota(notaComp.value)){
	    //Entrada
		var idMatricula = xGetElementById("idMatricula").value;
		var nota = notaComp.value;
		
		var cadena = "idMatricula="+idMatricula+"&nota="+nota;
				
		//Salida
		notaComp.style.color = "#0997F7";
		
		ajax = nuevoAjax();
		
		ajax.open("POST", xGetContextPath()+"/aulavirtual/tindividual/Calificar.action", true);
		ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		ajax.send(cadena);
		ajax.onreadystatechange = function () {
			if (ajax.readyState === 4) {
				if(ajax.responseText === "1"){
					xHideD("form_button");
					xHideD("mensaje");
				}else if(ajax.responseText === "0"){
					xShowD("form_button");
				}else
					alert("ERROR");
			}
		}
	}

}