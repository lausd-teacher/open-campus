/*** Modificar Datos ***/
function verPanelCambioDatos() {
	DarkPanel.show('form_div_datos',{duration	: 0.0});
}

function ocultarPanelCambioDatos() {
	DarkPanel.hide('form_div_datos');
}

/*** Modificar Clave ***/
function verPanelCambioPassword() {
	DarkPanel.show('form_div_clave',{duration	: 0.0});
}

function ocultarPanelCambioPassword() {
	DarkPanel.hide('form_div_clave');
	$("password_level").style.backgroundPosition= '200px';
	$("password_level").style.backgroundColor= '#ffffff';
}

function validarClave(texto) {
	if(!texto.empty()){
		var password = new PasswordTest(texto);
		//$("password_level").style.width = (password.value * 2.24) + "px";
		var _x = (112 - password.value * 2.24 - 40);
		$("password_level").style.backgroundPosition= _x + 'px';
		if(_x > 0){
			$("password_level").style.backgroundColor= '#ff0000';
		}else{
			$("password_level").style.backgroundColor= '#00ff00';
		}
		$('password_level').update(password.level);
	}else{
		$("password_level").style.backgroundPosition= '200px';
		$("password_level").style.backgroundColor= '#ffffff';
	}
}

wf.functionName_formValidation = "myCustomValidation";
function myCustomValidation (evt) {
	try{
		var form =wf.helpers.getSourceElement(evt);
		if (wf.formValidation(evt) && form.id == 'form_clave'){
			
			var isValidate = true;
				
			//Validar clave
			if(form.claveTemp.value != form.claveTemp2.value ){
				showErrorForm(form.claveTemp2,"Clave no coincide.");
				isValidate =  false;
			}else{
				hideErrorForm(form.claveTemp2);
			}
			if(form.claveTemp.value.length < form.claveTemp.alt){
				showErrorForm(form.claveTemp,"La clave debe tener una longitud no menor a 6.");
				isValidate =  false;
			}else{
				hideErrorForm(form.claveTemp);
			}
			
			if(!isValidate)
				return wf.utilities.XBrowserPreventEventDefault(evt);
			
			//$('form_clave_btn').disable();
			//loading();
			//return wf.utilities.XBrowserPreventEventDefault(evt);
			
		}else if (wf.formValidation(evt) && form.id == 'form_datos'){
			
			var isValidate = true;
				
			//Validar ...
			if(!validarExtAdjunto(form.doc.value,false,true)){
				isValidate = false;
			}
			
			if(!isValidate)
				return wf.utilities.XBrowserPreventEventDefault(evt);
				
		}
		
	}catch(e){
		alert('Ocurrió un error. Actualice la página.\nError:'+((e.description)?e.description:e));
		return wf.utilities.XBrowserPreventEventDefault(evt);
	}
}

/*** Modificar Correo ***/
function verPanelCambioCorreo() {
	DarkPanel.show('form_div_correo',{duration	: 0.0});
	$("newMail").value = $("theMail").innerHTML.replace(/<BR>/gi, ",")
}
function ocultarPanelCambioCorreo() {
	DarkPanel.hide('form_div_correo');
}

function grabarCorreo() {
	new Ajax.Request(xGetContextPath() +"/CambioEmail.action", 
	{
		method: 'post',
		parameters: 'email=' + $F("newMail"),
		onSuccess: function(transport) {
			if (transport.responseText.strip() == 'OK'){
				$("theMail").update($F("newMail").replace(/,/gi, "<BR>"));
				ocultarPanelCambioCorreo();
			}else{
				alert('Error al intentar actualizar. Inténtelo nuevamente.');
				window.location.reload();
			}
		}
	});	
}
