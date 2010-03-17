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
	var buttonPublish = xGetElementById("form_button");
	buttonPublish.disabled = true;
	return true;
	
}

function mostrarFormNuevoMensaje(){
	xChangeDisplay("mensaje");
}

function mostrarFormModMensaje(){
	xGetElementById("form_descripcion").value = xGetElementById("descripcionHTML").innerHTML
	xGetElementById('form_numCaracteres').value = xGetElementById("form_descripcion").value.length;
	xChangeDisplay("mensaje");
}
